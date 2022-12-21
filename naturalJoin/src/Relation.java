
import java.io.*;
import java.util.ArrayList;
import java.io.IOException;


/**
 * This class creates a relation
 * 
 * @author Penny Rowe
 * @date 2022/12/08
 */
public class Relation {
    public ArrayList<String> attributes;
    public Tuple tuples; //ArrayList<String[]> tuples;
    public String sortedOn;

    /**
     * Constructor for case with no inputs
     */
    public Relation() {
        attributes = new ArrayList<String>();
        tuples = new Tuple(); //new ArrayList<String[]>();
        sortedOn = null;
    }

    /*
     * Construct Relation using list of attributes
     * 
     * @param atts  The attributes
     */
    public Relation(ArrayList<String> atts) {
        attributes = atts;
        tuples = new Tuple(); //ArrayList<String[]>();
        sortedOn = null;
    }

    /*
     * Construct Relation using Attribute instance
     * 
     * @param atts  The Attribute instance
     */
    public Relation(Attribute atts) {
        attributes = atts.get();
        tuples = new Tuple(); //new ArrayList<String[]>();
        sortedOn = null;
    }

    /**
     * Create a relation by copying another
     */
    public Relation(Relation rel) {
        attributes = new ArrayList<String>();
        tuples = new Tuple(); //new ArrayList<String[]>();
        sortedOn = rel.sortedOn;

        // Add the original attributes
        for (String att: rel.attributes) {
            attributes.add(att);
        }

        // TODO: Does this deep copy?
        // Add the tuples
        tuples.addall(rel.tuples);

    }

    /**
     * Load the relation from a file
     * 
     * @param infile  The name of the file to load in
     * @returns The relation
     */
    public Relation(String infile) {

        attributes = new ArrayList<String>();
        tuples = new Tuple();
        sortedOn = null;

        String line;
        char comment = '#';
        String[] tokens;
            
        try (
            BufferedReader ins = new BufferedReader(new FileReader(infile));
        ) {
            // Read the first (required) header line and make sure it begins with
            // the comment character
            line = ins.readLine();
            if (line.charAt(0) != comment) {
                throw new IllegalArgumentException("Headerline must begin with #, but does not!");
                }
            tokens = line.substring(1).split("\\|");

            for (String attname : tokens) {
                attributes.add(attname);
            }

            // Read the second (optional) header line. If it begins with the
            // comment character, this is the attribute the relation is sorted by
            line = ins.readLine();
            int i = 0;
            if (line.charAt(0) == comment) {
                sortedOn = line.substring(1);
            }
            else {
                tokens = line.split("\\|", -1);
                tuples.add(i, tokens);
                i++;
            }

            // Loop over remaining lines
            while ((line = ins.readLine()) != null) {
                tokens = line.split("\\|", -1);
                tuples.add(i, tokens);
                i++;
            }

        } catch (FileNotFoundException ex1) {
            System.err.println(ex1);
        } catch (IOException ex2) {
            System.err.println(ex2);
        }
    }

    public void addtuple(String[] tup) {
        this.tuples.add(tup);
    }

    /**
     * Get the size as the number of tuples in the relation
     * @return
     */
    public int size() {
        return this.tuples.size();
    }

    /**
     * Get the tuple at the index given
     * 
     * @param index  Desired index
     * @return  The tuple at the index
     */
    public String[] getTuple(int index) {
        return this.tuples.get(index);
    }

    /**
     * Get all tupless
     * 
     * @return  The tuples
     */
    public ArrayList<String[]> getTuples() {
        return this.tuples.getAll();
    }

    /**
     *  Get the tuples
     */
    public String getVal(int itup, int iatt) {
        return this.tuples.getVal(itup, iatt);
    }
    
    /*
     * Return true if the attribute exists in the relation
     * @param att  Name of attribute to check for
     */
    public boolean containsAtt(String att) {
        return this.attributes.contains(att);
    }

    /**
     * Get the attributes from the relation that are in another relation
     * Assuming only one will match
     * 
     * @param relation  The name of the relation to compare
     * @returns The attribute in common
     */
    public String getCommonAtts(Relation relation2) {
        for (String att: this.attributes) {
            for (String att2: relation2.attributes)
                if (att.equals(att2)) {
                return att;
            }
        }
        return null;
    }

    /**
     * Return a copy of the attribute names
     * @return
     */
    public ArrayList<String> copyAttributes() {
        ArrayList<String> outarr = new ArrayList<String>();
        for (String att: this.attributes) {
            outarr.add(att);
        }
        return outarr;
    }

    /**
     * Sort the tuples, if needed
     */
    public void sort(int iatt) {
        this.tuples.sort(iatt);
    }

    /**
     * Get a string with the attributes
     * 
     * @return  A string with the attributes
     */
    private String getAttString() {
        String attstring = "";
        // The attribute names
        for (String att : this.attributes) {
            attstring += att + "|";
        }
        attstring += "\n";
        return attstring;
    }

    /**
     * Print the attributes and the first n tuples of the relation
     *
     * @param numtoprint
     */
    public String toStringfirst(int numtoprint) {
        String[] tup;
        String outstring = getAttString();

        // The tuples
        String substring;
        for (int i=0; i<numtoprint; i++) { 
            tup = this.getTuple(i);
            substring = "";
            for (String attval: tup) {
                substring += "|";
                substring += attval;
            }
            outstring += substring.substring(1) + "\n";
        }
        return outstring;
    }

    /**
     * Return a string representation of this relation.
     *
     * @return a string representaation of this relation
     */
    public String toString() {
        String attstring = getAttString();

        // The tuples
        String substring;
        for (String[] t: this.getTuples()) {
            substring = "";
            for (String attval: t) {
                substring += "|";
                substring += attval;
            }
            attstring += substring.substring(1) + "\n";
        }
        return attstring;
    }
}

