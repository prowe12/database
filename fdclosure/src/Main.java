import java.util.*;

/**
 * This class demonstrates example runs and performs tests 
 * (which need to be replaced with jUnit tests)
 * 
 * @author Penny Rowe
 * @version 2022/10/26
 */
public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {

        // Trials and tests
        // testFDSet();
        // checkFDset();  // TODO What should outputs be?
        // testAugment(); // TODO What should outputs be?
        // testTrivial();    // TODO What should outputs be?
        testTransitive();  // TODO What should outputs be?  + add new test
        // testGetallmembers(); // TODO What should outputs be?

        // testfrombook1();
        // davidsTest();
        // examplefromclass();
        // examplefromclassIncorrect();
      
    }

    /**
     * Run test suite from an example
     */
    public static void examplefromclass() {
      // Test set from text p. 344:
      FD f1 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
      FD f2 = new FD(Arrays.asList("A", "E"), Arrays.asList("D")); // AE --> D
      FD f3 = new FD(Arrays.asList("D"), Arrays.asList("B")); // D --> B
      FDSet fdset = new FDSet(f1, f2, f3);

      FD g1 = new FD(Arrays.asList("D"), Arrays.asList("B", "D")); // D --> BD
      FD g2 = new FD(Arrays.asList("A", "E"), Arrays.asList("A", "D", "E")); // AE --> ADE
      FD g3 = new FD(Arrays.asList("A", "B"), Arrays.asList("B", "C")); // AB --> BC
      FDSet fdset2 = new FDSet(g1, g2, g3); 

      System.out.println("FD+ for fdset: " + FDUtil.fdSetClosure(fdset));
      System.out.println("FD+ for fdset2: " + FDUtil.fdSetClosure(fdset2));

      // Test FD equality: fdset and gdset are equal iff their closures are equal
      System.out.println("Equals? (should be true): " + (fdset.equals(fdset2)));
  }


    /**
     * Run test suite from class example, but with an incorrect second fdset
     * which thus should give false
     */
    public static void examplefromclassIncorrect() {
        // Test set from text p. 344:
        FD f1 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
        FD f2 = new FD(Arrays.asList("A", "E"), Arrays.asList("D")); // AE --> D
        FD f3 = new FD(Arrays.asList("D"), Arrays.asList("B")); // D -- B
        FDSet fdset = new FDSet(f1, f2, f3);

        FD g1 = new FD(Arrays.asList("A"), Arrays.asList("B")); // A --> B
        FD g2 = new FD(Arrays.asList("B"), Arrays.asList("C")); // B --> C
        FDSet fdset2 = new FDSet(g1, g2);

        System.out.println("FD+ for fdset: " + FDUtil.fdSetClosure(fdset));
        System.out.println("FD+ for fdset2: " + FDUtil.fdSetClosure(fdset2));

        // Test FD equality: fdset and gdset are equal iff their closures are equal
        System.out.println("Equals? (should be false): " + (fdset.equals(fdset2)));
    }

    /**
     * Run test suite from textbook pg. 344
     */
    public static void testfrombook1() {
      // Test set from text p. 344:
      FD f1 = new FD(Arrays.asList("A"), Arrays.asList("B","C")); // A --> BC
      FD f2 = new FD(Arrays.asList("B"), Arrays.asList("C")); // B --> C
      FD f3 = new FD(Arrays.asList("A"), Arrays.asList("B")); // A -- B
      FD f4 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
      FDSet fdset = new FDSet(f1, f2, f3, f4);

      FD g1 = new FD(Arrays.asList("A"), Arrays.asList("B")); // A --> B
      FD g2 = new FD(Arrays.asList("B"), Arrays.asList("C")); // B --> C
      FDSet fdset2 = new FDSet(g1, g2);

      System.out.println("FD+ for fdset: " + FDUtil.fdSetClosure(fdset));
      System.out.println("FD+ for fdset2: " + FDUtil.fdSetClosure(fdset2));

      // Test FD equality: fdset and gdset are equal iff their closures are equal
      System.out.println("Equals? " + (fdset.equals(fdset2)));
  }

    /** 
     * Run test suite provided by David
     */
    public static void davidsTest() {
        // One FD Set = A --> B, AB --> C
        FD f1 = new FD(Arrays.asList("A"), Arrays.asList("B")); // A --> B
        FD f2 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
        FDSet fdset = new FDSet(f1, f2);

        // // Another FD set: AB --> B, A --> BC, AB --> C
        FD g1 = new FD(Arrays.asList("A", "B"), Arrays.asList("B")); // AB --> B
        FD g2 = new FD(Arrays.asList("A"), Arrays.asList("C", "B")); // A --> BC
        FD g3 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
        FDSet fdset2 = new FDSet(g1, g2, g3);

        // // Output FD closures
        System.out.println("fdset: " + fdset);
        System.out.println("FD+ for fdset1: " + FDUtil.fdSetClosure(fdset));
        System.out.println("FD+ for fdset2: " + FDUtil.fdSetClosure(fdset2));

        // Test FD equality: fdset and gdset are equal iff their closures are equal
        System.out.println("Equals? " + (fdset.equals(fdset2)));
    }

    /**
    * Method for trying out the (deepcopy) copy constructor of FDSet   
    */
    public static void testFDSet() {    
      System.out.println("In testFDSet");

      FD f1 = new FD(Arrays.asList("A"), Arrays.asList("B")); // A --> B
      FD f2 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
      FDSet fdset = new FDSet(f1, f2);

      // Create a deepcopy of fdset and check that the old and new look the same
      final FDSet newfdset = new FDSet(fdset);
      System.out.println("fdset: " + fdset);
      System.out.println("newfdset" + newfdset);
 
      // Make sure they don't share a reference (i.e. the new is a deepcopy)
      boolean bool = fdset==newfdset;
      System.out.println("They share a reference (should be false): " + bool);

      // check that getSet works for each
      fdset.getSet();
      newfdset.getSet();
      
      fdset.equals(newfdset);
      System.out.println("Test that FD closures are same (should be true): " + newfdset.equals(fdset));
    }

    /**
     * Test getallmembers (get all members of functional dependency set)
     */
    public static void testGetallmembers() {
      System.out.println("In testGetallmembers");

      // Setup
      FD f1 = new FD(Arrays.asList("A"), Arrays.asList("B")); // A --> B
      FD f2 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
      FDSet fdset = new FDSet(f1, f2);

      // Expected value
      System.out.println(FDUtil.getallmembers(fdset));
      // [A, B, C]
    }

    /**
     * Method for trying out augment from FDUtil 
     * (This should be made into a proper unit test.)
     */
    public static void testAugment() {    
      System.out.println("In testAugment");

      // Setup
      FD f1 = new FD(Arrays.asList("A"), Arrays.asList("B"));       // A --> B
      FD f2 = new FD(Arrays.asList("A", "B"), Arrays.asList("C"));  // AB --> C
      FDSet fdset = new FDSet(f1, f2);
  
      // Act: augment with C
      Set<String> attrs = new HashSet<>(Arrays.asList("C"));

      // Expected output
      System.out.println("Starting fdset: " + fdset);
      System.out.println("Augmented fdset: " + FDUtil.augment(fdset, attrs));
      // [
      //   AC --> BC
      //   ABC --> C
      // ]
    }

    /**
     * Method for trying out trivial from FDUtil 
     * (This should be made into a proper unit test.)
     */
    public static void testTrivial() {    
      System.out.println("In testTrivial (Main)");

      // Setup
      FD f1 = new FD(Arrays.asList("A"), Arrays.asList("B")); // A-->B 
      FD f2 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C 
      FDSet fdset = new FDSet(f1, f2);
      
      // Act: Get the trival set
      FDSet trivalFDset = FDUtil.trivial(fdset);

      // Expected output
      System.out.println("Starting fdset: " + fdset);
      System.out.println("Trivial fdset: " + trivalFDset);
      // [
      // A --> A
      // AB --> A
      // AB --> B
      // AB --> AB
      // ]
    }

    /**
     * Method for trying out FSSet and FD 
     * @param fdset
     */
    public static void checkFDset() {    
        // Print everything
        System.out.println("In checkFDset (Main)");

        FD f1 = new FD(Arrays.asList("A"), Arrays.asList("B")); // A --> B
        FD f2 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
        FDSet fdset = new FDSet(f1, f2);
        System.out.println("fdset: " + fdset);

        // Try out the iterator
        Iterator<FD> iterator = fdset.iterator();
        System.out.println("Elements of fdset: ");
        while (iterator.hasNext())
          System.out.println(iterator.next());

        // Just return the left sides
        System.out.println("Left hand sides of fdset: ");
        iterator = fdset.iterator();
        while (iterator.hasNext()) {
          FD fd = iterator.next();
          System.out.println("set: " + fd.getLeft());
        }
    }

        
    /**
     * Method for trying out FSSet and FD 
     * @param fdset
     */
    public static void testTransitive() {    
      System.out.println("In testTransitive");

      // This method returns a new set of FDs after repeatedly applying the 
      // transitive rule until no more new FDs are detected. 
      
      // Setup
      // F = {A → AB, AB → C, C → D}:
      FD f1 = new FD(Arrays.asList("A"), Arrays.asList("A", "B")); // A --> AB
      FD f2 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
      FD f3 = new FD(Arrays.asList("C"), Arrays.asList("D"));      // C --> D
      FDSet fdset = new FDSet(f1, f2, f3);
      
      // Act
      System.out.println("Set of FDs: " + fdset);
      System.out.println("Applying transitive rule: ");
      System.out.println(FDUtil.transitive(fdset));

      System.out.println("Expected: ");
      System.out.println("[");
      System.out.println("     A --> C     // This comes from A --> AB and AB --> C ");
      System.out.println("     A --> D     // This comes from A --> C and C --> D ");
      System.out.println("     AB --> D    // This comes from AB --> C and C --> D");
      System.out.println("]");
      // Take particular note of the fact that A → D (via A → C and C → D) 
      // is also generated, even though it took an iteration to 
      // first generate A → C. Therefore, this method is exhaustive.

      // Tet 2
      // Setup
      // F = {A → AB, B → C, C → D}:
      f1 = new FD(Arrays.asList("A"), Arrays.asList("A", "B")); // A --> AB
      f2 = new FD(Arrays.asList("B"), Arrays.asList("C")); // B --> C
      f3 = new FD(Arrays.asList("C"), Arrays.asList("D"));      // C --> D
      fdset = new FDSet(f1, f2, f3);
      
      // Act
      System.out.println("Set of FDs: " + fdset);
      System.out.println("Applying transitive rule: ");
      System.out.println(FDUtil.transitive(fdset));

      System.out.println("Expected: ");
      System.out.println("[");
      System.out.println("     A --> C     // This comes from A --> AB and B --> C ");
      System.out.println("     A --> D     // This comes from A --> C and C --> D ");
      System.out.println("     B --> D     // This comes from B --> C and C --> D ");
      System.out.println("]");
      // TODO: Why is A-->AB repeated? Is that ok?

    }
}