import java.util.ArrayList;

/**
 * @author Penny Rowe
 * @date 2022/12/13
 */
public class Attribute {
    ArrayList<String> attributes;
    String primaryKey;
    int index;

    /**
     * Attributes constructor with relation and pk input
     * 
     * @param rel  The relation
     * @param att  The primary key
     * @throws IllegalArgumentException
     */
    public Attribute(Relation rel, String att) {
        // QC
        if (!(rel.containsAtt(att))) {
            String msg = "The primary key, " + att + ", is missing from the relations.";
            throw new IllegalArgumentException(msg);
        }

        // Deep copy the attributes from relation 1, to avoid linking these attributes to the relation
        attributes = rel.copyAttributes();
        primaryKey = att;
        index = findIndexToAtt();
    }

    /**
     * Attributes constructor with relation input
     * 
     * @param rel  The relation
     */
    public Attribute(Relation rel1) {
        ArrayList<String> atts = rel1.copyAttributes();
        attributes = atts;
        primaryKey = null;
        index = -1;
    }

    /**
     * Append attributes except for the indicated one
     * 
     * @param allAtts  Attributes to add
     * @throws IllegalArgumentException
     */
    public void addAllButPrimaryKey(Attribute atts) {
        if (!(this.primaryKey.equals(atts.primaryKey))) {
            String msg = "Primary key '" + this.primaryKey + "'' missing.";
            throw new IllegalArgumentException(msg);
        }
        ArrayList<String> attsToAdd = atts.getAllButPrimaryKey();
        this.attributes.addAll(attsToAdd);
    }

    /**
     * Append attributes except for the indicated one
     * 
     * @param allAtts  Attributes to add
     * @throws IllegalArgumentException
     */
    public void addAll(Attribute atts) {
        ArrayList<String> attsToAdd = atts.get();
        this.attributes.addAll(attsToAdd);
    }

    /**
     * Get the index to the given attribute in the list of attributes
     * 
     * @param att  The desired attribute
     * @return  The index to the attribute (or -1 if missing)
     */
    private int findIndexToAtt() {
        int index = 0;
        for (String att0:this.attributes) {
            if (att0.equals(this.primaryKey)){
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Return all the attributes in the relation
     * 
     * @return  The attributes in the relation
     */
    public ArrayList<String> get() {
        return this.attributes;
    }

    /**
     * Return all the attributes in the relation
     * 
     * @param  i The index to the attribute of interest
     * @return  The attributes in the relation
     */
    public String get(int i) {
        return this.attributes.get(i);
    }

    /**
     * Return the index to the primary key
     * 
     * @return  The index to the primary key
     */
    public int getPrimaryKeyIndex() {
        return this.index;
    }

    /**
     * Return the number of attributes
     * 
     * @return  The index to the primary key
     */
    public int size() {
        return this.attributes.size();
    }

    /**
     * Return all the attributes in the relation except the primary key
     * 
     * @return  The attributes in the relation, excluding the pk
     */
    public ArrayList<String> getAllButPrimaryKey() {
        ArrayList<String> atts = get();
        atts.remove(this.primaryKey);
        return atts;
    }

    /**
     * Return string representation of attribute
     * 
     * @return  String representation of attribute
     */
    public String toString() {
        String outstring = "";
        for (String att: this.attributes) {
            outstring += att + "|";
        }
        return outstring;
    }
}

