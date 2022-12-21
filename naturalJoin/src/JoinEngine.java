import java.util.HashMap;
import java.util.Scanner;


/**
 * @author Penny Rowe
 * @date 2022/12/08
 */
public class JoinEngine {
    /**
     * Perform natural join on two relations
     * 
     * @throws IllegalArgumentException
     */
    public static void main(String args[])  {

        // Available natural join methods
        HashMap<String,String> methods = new HashMap<String,String>();
        methods.put("1", "Nested Loop Join");
        methods.put("2", "Hash Join");
        methods.put("3", "Sort Merge Join");

        // The directory containing the files (relative)
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);

        // Prompt the user to enter the relations across which they'd like to perform the natural join. 
        Scanner myObj = new Scanner(System.in);  // Create a Scanner object
        System.out.println("\nAvailable relations:");
        System.out.println(relations.keySet());

        System.out.println("\nEnter the first relation for the natural join:");
        String relation1name = myObj.nextLine();  // Read user input
        
        System.out.println("Enter the second relation for the natural join:");
        String relation2name = myObj.nextLine();  // Read user input
        
        // If any relations they entered do not exist (case insensitive), throw an IllegalArgumentException and exit. 
        // TODO: make case insensitive
        if (!(relations.containsKey(relation1name))) {
            myObj.close();  // closes the scanner
            throw new IllegalArgumentException("Relation " + relation1name + " does not exist");
        }
        if (!(relations.containsKey(relation2name))) {
            myObj.close();  // closes the scanner
            throw new IllegalArgumentException("Relation " + relation2name + " does not exist");
        }
        
        // Present the user with a menu, allowing the user to select from one of the three join algorithms.
        System.out.println("\nWhich natural join method would you like to use? (Enter 1, 2, or 3)");
        for (int i=1;i<=methods.size(); i++){
            System.out.println(i + ") " + methods.get(String.valueOf(i)));
        }

        String mergetype = myObj.nextLine();  // Read user input
        myObj.close();  // closes the scanner
        
        System.out.println("\nPerforming: " + relation1name + " natural join " + relation2name);

        // Get the method to do the natural join
        Relation rel1 = relations.get(relation1name);
        Relation rel2 = relations.get(relation2name);

        // Perform natural join for selected method
        RelationUtil.naturalJoin(rel1, rel2, methods.get(mergetype));
    }
}




