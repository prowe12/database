
import java.util.HashMap;
import java.io.File;


/**
 * This class provide utilities for the Relation class,
 * including natural join methods
 * 
 * @author Penny Rowe
 * @date 2022/12/12
 */
public class RelationUtil {
    /**
     * Load in the relations from files in the given directory
     * @param direc  The directory containing the files
     * @return The relation names and the relations
    */
    public static HashMap<String, Relation> getRelationsFromFiles(String direc) {
        String relationname;
        //HashSet<String> relations = new HashSet<String>();
        HashMap<String, Relation> relations = new HashMap<String, Relation>();

        // Get a list of the files containing the relations
        String contents[] = getFileList(direc);

        if ((contents == null) || (contents.length==0)) {
            String msg = "The directory, " + direc + ", does not include any .txt files";
            throw new IllegalArgumentException(msg);
        }

        // Loop over file names and load in the relation if the file is the proper type
        // Store relations in hashmap with key,value = name,relation
        // where the name is always lower case
        for(int i=0; i<contents.length; i++) {
            if (contents[i].charAt(0) != '.' & contents[i].endsWith(".txt")) {
                relationname = contents[i];
                relationname = relationname.replace(".txt", "");
                relationname = relationname.toLowerCase();
                
                Relation tmprelation = new Relation(direc + contents[i]);
                relations.put(relationname, tmprelation);
            }
        }
        return relations;
    }

    /**
     * Get list of all files in the given directory
     * 
     * @param direc The directory containing the files
     * @return  A list of the file names
     */
    public static String[] getFileList(String direc) {
        File directoryPath = new File(direc);
        String contents[] = directoryPath.list();
        return contents;
    }

    /**
     * Perform natural join
     * 
     * @param rel1  Left hand relation
     * @param rel2  Right hand relation
     * @param method  The method to use
     * @returns Result of natural join
     * 
     */
    public static Relation naturalJoin(Relation rel1, Relation rel2, String method) {
        boolean verbose = true;  // Flag for whether to print output to console
        Relation joinedRel = null;

        // Throw error if relations do not exist
        if ((rel1 == null) || (rel2 == null)) {
            throw new IllegalArgumentException("One or more relation is missing");
        }

        // Get the common attribute between the tables
        // If a common attribute does not exist between the tables, we should produce the cartesian product. 
        // But for now we will throw an exception.
        String commonAtt = rel1.getCommonAtts(rel2);
        if (commonAtt == null) {
            // If there are no attributes in common, compute the cartesian product.
            // In the case of the cartesian product, might as well do the nested loop join, 
            // since no time savings are possible
            return cartesianProduct(rel1, rel2);
        }

        // Current time
        long starttime = System.nanoTime();

        switch(method) {
            case "Nested Loop Join":
                joinedRel = RelationUtil.nestedLoopJoin(rel1, rel2, commonAtt);
                break;
            case "Hash Join":
                joinedRel = RelationUtil.hashjoin(rel1, rel2, commonAtt);
            break;        
            case "Sort Merge Join":
                joinedRel = RelationUtil.sortmergejoin(rel1, rel2, commonAtt);
            break;
            default:
                String msg = "Selected method" + method +  " is not available.";
                throw new IllegalArgumentException(msg);
        }

        long endtime = System.nanoTime();

        // Print outputs as required by the assignment (only if verbose is true)
        if (verbose) {
            // Outputs:
            // 1) resulting relation 
            System.out.println(joinedRel);

            // 2) The time to do the natural join in milliseconds (excluding printing result)
            System.out.print("\nTime to compute natural join with " + method + " = ");
            System.out.println(String.valueOf((endtime - starttime)*1e-6) + " ms");

            // 3) the number of rows returned in the result set. 
            System.out.println("\nNumber of rows in result: " + joinedRel.tuples.size());
        }
        return joinedRel;
    }


    /**
     * Perform natural join using sort merge
     * 
     * @param rel1  Left hand relation
     * @param rel2  Right hand relation
     * @param att  The common attribute
     * @returns Result of natural join
     * 
     */
    public static Relation sortmergejoin(Relation rel1, Relation rel2, String att) {
        // Set up the attributes for the original relations
        Attribute atts1 = new Attribute(rel1, att);
        Attribute atts2 = new Attribute(rel2, att);
        int ipk1 = atts1.getPrimaryKeyIndex();
        int ipk2 = atts2.getPrimaryKeyIndex();

        // Set up the attributes and relation for the new relation
        Attribute atts3 = new Attribute(rel1, att);
        atts3.addAllButPrimaryKey(atts2);
        Relation rel3 = new Relation(atts3);

        // Sort tuples on attribute if needed
        if ((rel1.sortedOn==null) || !(rel1.sortedOn.equals(att)))  {
            rel1.sort(ipk1);
        }
        if  ((rel2.sortedOn==null) || !(rel2.sortedOn.equals(att)))  {
            rel2.sort(ipk2);
        }

        // Set up
        String[] tuple1;
        String[] tuple2;
        String[] tuple3;
        String pk1, pk2;
        int i = 0;
        int j = 0;
        while ((i < rel1.size()) && (j < rel2.size())) {
            // Initialize
            tuple1 = rel1.getTuple(i);
            tuple2 = rel2.getTuple(j);
            pk1 = tuple1[ipk1];
            pk2 = tuple2[ipk2];

            if (rel1.getVal(i,ipk1).equals(rel2.getVal(j,ipk2))) {
                // Match found, enter merge phase
                while ((i < rel1.size()) && (rel1.getVal(i,ipk1).equals(rel2.getVal(j,ipk2)))) {
                    tuple1 = rel1.getTuple(i);
                    pk1 = tuple1[ipk1];
        
                    int k = j;
                    while (k < rel2.size() && pk1.equals(rel2.getVal(k, ipk2))) {
                        tuple2 = rel2.getTuple(k);
                        // create new tuple (R[i], S[k]) and add it to T
                        tuple3 = joinTuple(tuple1, tuple2, ipk2);
                        rel3.addtuple(tuple3);

                        // Increment for next time around
                        k++;
                    }
                    i++;
                }
            }
            else if (pk1.compareTo(pk2) < 0) {
                // pk1 < pk2, so increment tuple1
                i++;
            }
            else {
                // pk1 > pk2, so increment tuple2
                j++;
            }
        }

        return rel3;
    }

    /**
     * Perform natural join using hash join
     * 
     * Precondition: The common attribute in R must be unique
     *
     * @param rel1  Left hand relation
     * @param rel2  Right hand relation
     * @param att  The common attribute
     * @returns Result of natural join
     * 
     */
    public static Relation hashjoin(Relation rel1, Relation rel2, String att) {

        // Set up the attributes and the indices to the primary key
        Attribute atts1 = new Attribute(rel1, att);
        Attribute atts2 = new Attribute(rel2, att);
        int ipk1 = atts1.getPrimaryKeyIndex();
        int ipk2 = atts2.getPrimaryKeyIndex();

        // New relation
        Attribute atts3 = new Attribute(rel1, att);
        atts3.addAllButPrimaryKey(atts2);
        Relation rel3 = new Relation(atts3.get());

        // Phase I: Hash every tuple of R by the value of the common attribute
        // TODO have we sufficiently checked if the common attribute values are unique?
        HashMap<String,String[]> map = new HashMap<String,String[]>();
        for (String[] tuple1: rel1.getTuples()) {
            if (!map.containsKey(tuple1[ipk1])) {
                map.put(tuple1[ipk1], tuple1);
            }
            else {
                String msg = "Hash join cannot be performed, possibly because common attribute not unique";
                throw new IllegalArgumentException(msg);
            }
        }
        
        // Phase II: Join up with S
        String[] tuple1;
        String[] tuple3;
        for(String[] tuple2: rel2.getTuples()) {
                if (map.containsKey(tuple2[ipk2])) {
                    // Get the tuple contianing the primary key value in the map
                    // And create the new tuple and add it to the new relation
                    tuple1 = map.get(tuple2[ipk2]);
                    tuple3 = joinTuple(tuple1, tuple2, ipk2);
                    rel3.addtuple(tuple3);
                }
        }
        return rel3;
    }

    
    /**
     * Join two tuples
     * 
     * @param t1  The first tuple
     * @param t2  The second tuple
     * @param ipk2  The index to the primary key in the second tuple
     * @return  The joined tuple
     */
    public static String[] joinTuple(String[] t1, String[] t2, int ipk2) {
        // Initialize the output tuple
        String[] t3 = new String[t1.length + t2.length -1];

        // Add the attribute values from t1
        int count = 0;
        for (String newval: t1) {
            t3[count] = newval;
            count++;
        }
        // Add the attribute values from t2
        int count2 = 0;
        for (String newval: t2) {
            if (!(count2==ipk2)) {
                t3[count] = newval;
                count++;
            }
            count2++;
        }
        return t3;
    }


    /**
     * Perform Cartesian Product
     * 
     * @param rel1  Left hand relation
     * @param rel2  Right hand relation
     * @returns Result of cartesian product
     * 
     */
    public static Relation cartesianProduct(Relation rel1, Relation rel2) {
        // Set up the attributes
        Attribute atts2 = new Attribute(rel2);

        // Initialize new relation with all attributes from first relation
        Attribute atts3 = new Attribute(rel1);
        atts3.addAll(atts2);
        Relation joinedrel = new Relation(atts3.get());

        // Join the tuples. Start with the first relation
        int count;
        for (String[] t1: rel1.getTuples()){
            for (String[] t2: rel2.getTuples()) {
                String[] t3 = new String[atts3.size()];
                // Add the attribute values from t1
                count = 0;
                for (String newval: t1) {
                    t3[count] = newval;
                    count++;
                }
                // Add the attribute values from t2
                for (String newval: t2) {
                    t3[count] = newval;
                    count++;
                }
                // Add the new tuple to the new relation
                joinedrel.addtuple(t3);
            }
        }
        return joinedrel;
    }


    /**
     * Perform natural join using nested loop join
     * 
     * @param rel1  Left hand relation
     * @param rel2  Right hand relation
     * @param att  The common attribute
     * @returns Result of natural join
     * 
     */
    public static Relation nestedLoopJoin(Relation rel1, Relation rel2, String att) {
        // Set up the attributes
        Attribute atts1 = new Attribute(rel1, att);
        Attribute atts2 = new Attribute(rel2, att);
        Attribute atts3 = new Attribute(rel1, att);
        int ipk1 = atts1.getPrimaryKeyIndex();
        int ipk2 = atts2.getPrimaryKeyIndex();

        // Second relation
        atts3.addAllButPrimaryKey(atts2);

        // Initialize the relation with the attributes
        Relation joinedrel = new Relation(atts3.get());

        // Join the tuples. Start with the first relation
        int count;
        //boolean foundMatchingPkVals = false;
        for (String[] t1: rel1.getTuples()){
            for (String[] t2: rel2.getTuples()) {
                if (t1[ipk1].equals(t2[ipk2])) {
                    String[] t3 = new String[atts3.size()];
                    // Add the attribute values from t1
                    count = 0;
                    for (String newval: t1) {
                        t3[count] = newval;
                        count++;
                    }
                    // Add the attribute values from t2
                    int count2 = 0;
                    for (String newval: t2) {
                        if (!(count2==ipk2)) {
                            t3[count] = newval;
                            count++;
                        }
                        count2++;
                    }
                    // Add the new tuple to the new relation
                    joinedrel.addtuple(t3);
                }
            }
        }
        return joinedrel;
    }

}
