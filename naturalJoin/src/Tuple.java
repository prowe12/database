import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * @author Penny Rowe
 * @date 2022/12/08
 */
class Tuple  {

    public ArrayList<String[]> tuples;

    /**
     * Tuple constructor with no inputs
     */
    public Tuple() {
        tuples = new ArrayList<String[]>();
    }

    /**
     * Tuple constructor with list of tuples as input
     */
    public Tuple(Tuple inputTuple) {
        tuples = new ArrayList<String[]>(inputTuple.getAll());
    }

    /**
     * Add list of tuples
     */
    // public void addall(Tuple newtuples) {
    //     for (String[] newtuple:newtuples.tuples) {
    //         tuples.add(newtuple);
    //     }
    // }

    /**
     * Add a tuple to the end
     */
    public void add(String[] tuple) {
        tuples.add(tuple);
    }

    /**
     * Add a tuple at the given index
     */
    public void add(int index, String[] tuple) {
        tuples.add(index, tuple);
    }

    /**
     * Get the size, as the number of tuples
     */
    public int size() {
        return this.tuples.size();
    }

    /**
     * Get all tuples
     */
    public ArrayList<String[]> getAll() {
        return this.tuples;
    }

    /**
     * Get the tuple at the index given
     * 
     * @param index  Desired index
     * @return  The tuple at the index
     */
    public String[] get(int index) {
        return this.tuples.get(index);
    }

    /**
     * Sort the tuples on the given attribute
     * 
     */
    public void sort(int iatt) {
        Collections.sort(this.tuples, new Comparator<String[]> () {
            @Override
            public int compare(String[] a, String[] b) {
                return a[iatt].compareTo(b[iatt]);
            }
        });
    
    }

    /**
     * 
     */
    public String getVal(int itup, int iatt) {
        String[] tup = this.tuples.get(itup);
        return tup[iatt];
    }

    /**
     * Return a string representation of the tuples
     *
     * @return a string representaation of the tuples
     */
    public String toString() {
        String tupstring = "";
        for (String[] t: this.tuples) {
            tupstring += t;
        }
        return tupstring;
    }
}

