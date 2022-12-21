import java.util.*;

public class Main {
  /**
   * Suite of test codes for methods in Normalizer.java, ending with 
   * David Chiu's test of Normalizer.BCNFDecompose
   * Uses examples from the Theory (Part II) slides (page 29)
   */
  public static void main(String[] args) {
    // testAttributeClosure();
    // testRelationContainsFDsetAtts();
    // testFindSuperkeys();
    // testIsBCNF();
    // testBCNFDecompose1();
    // testBCNFDecompose2();
    // testBCNFDecompose3();
    // testBCNFDecompose4();
  }

  /**
   * Test code for BCNFDecompose: R(ssn, name, eyecolor); already in BCNF
   */
  public static void testBCNFDecompose1() {
    // Test a relation that already is in BCNF
    // Setup
    FD f1 = new FD(Arrays.asList("ssn"), Arrays.asList("name")); // ssn --> name
    FD f2 = new FD(Arrays.asList("ssn", "name"), Arrays.asList("eyecolor")); // ssn,name --> eyecolor
    FDSet fdset = new FDSet(f1, f2);
    Set<String> people = new HashSet<>(Arrays.asList("ssn", "name", "eyecolor")); // Relation people(ssn,name,eyecolor)
    // Run
    System.out.println();
    System.out.println("Starting relation: " + people);
    System.out.println("Set of relations: " + Normalizer.BCNFDecompose(people, fdset));
    System.out.println("Expected Result:  [[name, eyecolor, ssn]]");
    System.out.println();

    Set<String> peopleb = new HashSet<>(Arrays.asList("ssn", "name", "eyecolor")); // Relation people(ssn,name,eyecolor)
    FD f1b = new FD(Arrays.asList("ssn"), Arrays.asList("name")); // ssn --> name
    FD f2b = new FD(Arrays.asList("ssn", "name"), Arrays.asList("eyecolor")); // ssn,name --> eyecolor
    FDSet fdsetb = new FDSet(f1, f2);
    //
    System.out.println();
    System.out.println("Test for side-effects:");
    System.out.println("Start f1: " + f1b);
    System.out.println("Final f1: " + f1);

    System.out.println("Start f2: " + f2b);
    System.out.println("Final f2: " + f2);

    System.out.println("Start fdset: " + fdsetb);
    System.out.println("Final fdset: " + fdset);

    System.out.println("Start relation: " + peopleb);
    System.out.println("Final relation: " + people);
    System.out.println();
  }

  /**
   * Test code for BCNFDecompose: R(A,B,C,D,E)
   * - Returns a set of relational schemas that satisfy BCNF. 
   * - At every split point, outputs the schema being split, the violating FD, and
   *   the superkeys for that schema. 
   * - Also prints the same information for the two schemas after the split. 
   */
  public static void testBCNFDecompose2() {
    // Test a relation that is not in BCNF
    // U(A,B,C,D,E)
    Set<String> U = new HashSet<>(Arrays.asList("A", "B", "C", "D", "E"));
    FD f1 = new FD(Arrays.asList("A", "E"), Arrays.asList("D")); // AE --> D
    FD f2 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
    FD f3 = new FD(Arrays.asList("D"), Arrays.asList("B")); // D --> B
    FDSet fdset = new FDSet(f1, f2, f3);
    System.out.println("");
    System.out.println("BCNF start");
    System.out.println("Final BCNF Schemas: " + Normalizer.BCNFDecompose(U, fdset));
    System.out.println("Expected Result:    [[B, D], [A, C, D], [A, D, E]]");
    System.out.println();


    // Test for immutability and side-effects
    Set<String> Ub = new HashSet<>(Arrays.asList("A", "B", "C", "D", "E"));
    FD f1b = new FD(Arrays.asList("A", "E"), Arrays.asList("D")); // AE --> D
    FD f2b = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
    FD f3b = new FD(Arrays.asList("D"), Arrays.asList("B")); // D --> B
    FDSet fdsetb = new FDSet(f1, f2, f3);

    System.out.println();
    System.out.println("Test for side-effects:");
    System.out.println("Start f1: " + f1b);
    System.out.println("Final f1: " + f1);
    System.out.println("Start f2: " + f2b);
    System.out.println("Final f2: " + f2);
    System.out.println("Start f3: " + f3b);
    System.out.println("Final f3: " + f3);
    System.out.println("Start fdset: " + fdsetb);
    System.out.println("Final fdset: " + fdset);
    System.out.println("Start relation: " + Ub);
    System.out.println("Final relation: " + U);
    System.out.println();
  }

  /**
   * Test code for BCNFDecompose: R(A,B,C)
   */
  public static void testBCNFDecompose3() {
    Set<String> R = new HashSet<>(Arrays.asList("A", "B", "C"));
    FD g1 = new FD(Arrays.asList("A"), Arrays.asList("B", "C")); // A --> BC
    FD g2 = new FD(Arrays.asList("B"), Arrays.asList("C")); // B --> C
    FD g3 = new FD(Arrays.asList("A"), Arrays.asList("B")); // A --> B
    FD g4 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
    FDSet fdsetR = new FDSet(g1, g2, g3, g4);
    System.out.println("Final BCNF Schemas: " + Normalizer.BCNFDecompose(R, fdsetR));
    System.out.println("Expected Result:    [[A, B], [B, C]]");
    System.out.println("");

    // Test for immutability and side-effects
    Set<String> Rb = new HashSet<>(Arrays.asList("A", "B", "C"));
    FD g1b = new FD(Arrays.asList("A"), Arrays.asList("B", "C")); // A --> BC
    FD g2b = new FD(Arrays.asList("B"), Arrays.asList("C")); // B --> C
    FD g3b = new FD(Arrays.asList("A"), Arrays.asList("B")); // A --> B
    FD g4b = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
    FDSet fdsetRb = new FDSet(g1, g2, g3, g4);
    System.out.println();
    System.out.println("Test for side-effects:");
    System.out.println("Start f1: " + g1b);
    System.out.println("Final f1: " + g1);
    System.out.println("Start f2: " + g2b);
    System.out.println("Final f2: " + g2);
    System.out.println("Start f3: " + g3b);
    System.out.println("Final f3: " + g3);
    System.out.println("Start f3: " + g4b);
    System.out.println("Final f3: " + g4);
    System.out.println("Start fdset: " + fdsetRb);
    System.out.println("Final fdset: " + fdsetR);
    System.out.println("Start relation: " + Rb);
    System.out.println("Final relation: " + R);
    System.out.println();
  }

  /**
   * Test code for BCNFDecompose: R(A,B,C,D,E)
   */
  public static void testBCNFDecompose4() {
    // Test a relation that is not in BCNF: U(A,B,C,D)
    // S(A,B,C,D,E)
    // Setup
    Set<String> S = new HashSet<>(Arrays.asList("A", "B", "C", "D")); // Relation S(A,B,C,D)
    FD s1 = new FD(Arrays.asList("A"), Arrays.asList("B")); // A --> B
    FD s2 = new FD(Arrays.asList("B"), Arrays.asList("C")); // B --> C
    FDSet fdsetS = new FDSet(s1, s2);
    // Run
    System.out.println("");
    System.out.println("BCNF start");
    System.out.println("Final BCNF Schemas: " + Normalizer.BCNFDecompose(S, fdsetS));
    System.out.println("Expected Result:    [[A, B], [A, C], [A, D]]");
    System.out.println();

    // Test for immutability and side-effects
    Set<String> Sb = new HashSet<>(Arrays.asList("A", "B", "C", "D")); // Relation S(A,B,C,D)
    FD s1b = new FD(Arrays.asList("A"), Arrays.asList("B")); // A --> B
    FD s2b = new FD(Arrays.asList("B"), Arrays.asList("C")); // B --> C
    FDSet fdsetSb = new FDSet(s1, s2);

    System.out.println();
    System.out.println("Test for side-effects:");
    System.out.println("Start f1: " + s1b);
    System.out.println("Final f1: " + s1);
    System.out.println("Start f2: " + s2b);
    System.out.println("Final f2: " + s2);
    System.out.println("Start fdset: " + fdsetSb);
    System.out.println("Final fdset: " + fdsetS);
    System.out.println("Start relation: " + Sb);
    System.out.println("Final relation: " + S);
    System.out.println();
  }


  /**
   * Test code for isBCNF
   * - Returns true if the left-hand side of all non-trivial FDs is a superkey.
   * - Returns false if the left-hand side of all non-trivial FDs is not a superkey.
   */
  public static void testIsBCNF() {
    // Recall that a relational schema is in BCNF iff the left-hand side of all non-trivial
    // FDs is a superkey
    
    // Test 1: a case that is in BCNF: R(ssn, name, eyecolor)
    // Setup
    FD f1 = new FD(Arrays.asList("ssn"), Arrays.asList("name")); // ssn --> name
    FD f2 = new FD(Arrays.asList("ssn", "name"), Arrays.asList("eyecolor")); // ssn,name --> eyecolor
    FDSet fdset = new FDSet(f1, f2);
    Set<String> people = new HashSet<>(Arrays.asList("ssn", "name", "eyecolor")); // Relation people(ssn,name,eyecolor)

    // Run
    System.out.println("");
    System.out.println("Starting values: ");
    System.out.println("relation: " + people);
    System.out.println("FDSet: " + fdset);
    // Run
    System.out.println("BCNF? " + Normalizer.isBCNF(people, fdset) +  ": expect true");
    // 
    System.out.println("Ending values: ");
    System.out.println("relation: " + people);
    System.out.println("FDSet: " + fdset);

    // Test 2: a case that is not in BCNF
    // Setup
    f1 = new FD(Arrays.asList("ssn"), Arrays.asList("name")); // ssn --> name
    f2 = new FD(Arrays.asList("ssn", "name"), Arrays.asList("eyecolor")); // ssn,name --> eyecolor
    FD f3 = new FD(Arrays.asList("name"), Arrays.asList("eyecolor")); // name --> eyecolor (violates BCNF)
    fdset = new FDSet(f1, f2, f3);
    people = new HashSet<>(Arrays.asList("ssn", "name", "eyecolor")); // Relation people(ssn,name,eyecolor)
    
    // Run
    System.out.println("");
    System.out.println("Starting values: ");
    System.out.println("relation: " + people);
    System.out.println("FDSet: " + fdset);
    // Run
    System.out.println("BCNF? " + Normalizer.isBCNF(people, fdset) + ": expect false");
    //
    System.out.println("Ending values: ");
    System.out.println("relation: " + people);
    System.out.println("FDSet: " + fdset);
    System.out.println("");
  }

  /**
   * Test code for findSuperkeys
   * Test that it:
   * - Exhaustively generates all superkeys of a given relational schema and its FD set. 
   * - Throws an exception if there is an FD that refers to an unknown attribute in the given schema. 
   * - When the FD set is empty, all attributes in the given schema serves as the only superkey.
   */
  public static void testFindSuperkeys() {
    // Tests for:
    // Exhaustively generates all superkeys of a given relational schema and its FD set. 
    
    // Test 1 of findSuperkeys
    // Setup
    FD f1 = new FD(Arrays.asList("ssn"), Arrays.asList("name")); // ssn --> name
    FD f2 = new FD(Arrays.asList("ssn"), Arrays.asList("eyecolor")); // ssn --> eyecolor
    FDSet fdset = new FDSet(f1, f2);
    Set<String> rel = new HashSet<>(Arrays.asList("ssn", "name", "eyecolor")); 
    // Run
    System.out.println("\nTest1 of findSuperkeys");
    System.out.println("Relation: " + rel);
    System.out.println("FDset: " + fdset);
    System.out.println("Superkeys: " + Normalizer.findSuperkeys(rel, fdset));
    System.out.println("Should be: " + "[[name, ssn, eyecolor], [ssn, eyecolor], [name, ssn], [ssn]]");
    System.out.println("");
   
    // Test 2 of findSuperkeys: R(A,B,C,D)
    // Setup
    rel = new HashSet<>(Arrays.asList("A", "B", "C", "D"));
    f1 = new FD(Arrays.asList("A"), Arrays.asList("D")); // A --> D
    f2 = new FD(Arrays.asList("A", "B"), Arrays.asList("C")); // AB --> C
    FD f3 = new FD(Arrays.asList("D"), Arrays.asList("B")); // D --> B
    fdset = new FDSet(f1, f2, f3);
    // Run
    System.out.println("\nTest2 of findSuperkeys");
    System.out.println("Relation: " + rel);
    System.out.println("FDset: " + fdset);
    System.out.println("Superkeys: " + Normalizer.findSuperkeys(rel, fdset));
    System.out.println("Should be: " + "[[A], [A, B], [A, C], [D, A], [A, B, C], [D, A, B], [D, A, C], [A, B, C, D]]");
    System.out.println("");

    // Tests for:
    // When the FD set is empty, all attributes in the given schema serves as the only superkey.    

    // Test 3:
    // Setup
    rel = new HashSet<>(Arrays.asList("A", "B", "C", "D"));
    fdset = new FDSet();
    // Run
    System.out.println("\nTest3 of findSuperkeys");
    System.out.println("Relation: " + rel);
    System.out.println("FDset: " + fdset);
    System.out.println("Superkeys: " + Normalizer.findSuperkeys(rel, fdset));
    System.out.println("Should be: " + "[[A, B, C, D]]");
    System.out.println("");

    // Tests for:
    // Throws an exception if there is an FD that refers to an unknown attribute in the given schema.
    
    // Test 4 of findSuperkeys: Case with extraneous attribute - throws exception
    // Setup
    f1 = new FD(Arrays.asList("ssn"), Arrays.asList("name")); // ssn --> name
    f2 = new FD(Arrays.asList("ssn"), Arrays.asList("eyecolor")); // ssn --> eyecolor
    fdset = new FDSet(f1, f2);
    rel = new HashSet<>(Arrays.asList("ssn", "name")); // R(ssn, name)
    // Run
    System.out.println("\nTest4 of findSuperkeys");
    System.out.println("Should give: ");
    System.out.print("Exception in thread 'main' java.lang.IllegalArgumentException: ");
    System.out.println("Some members FD set are not in the relational schema.");
    System.out.println("Superkeys: " + Normalizer.findSuperkeys(rel, fdset)); 

  }

  /**
   * Suite of test codes for methods in Normalizer.java, ending with 
   */
  public static void testRelationContainsFDsetAtts() {
    // Setup
    FD f1 = new FD(Arrays.asList("ssn"), Arrays.asList("name")); // ssn --> name
    FD f2 = new FD(Arrays.asList("ssn"), Arrays.asList("eyecolor")); // ssn --> eyecolor
    FDSet fdset = new FDSet(f1, f2);   
    Set<String> rel = new HashSet<>(Arrays.asList("ssn", "name", "eyecolor")); 
    boolean sane;
    System.out.println(" ");
    System.out.println("Tests for testRelationContainsFDsetAtts");
    System.out.println("FDset: " + fdset);

    // Test 1: sane
    rel = new HashSet<>(Arrays.asList("ssn", "name", "eyecolor")); 
    sane = Normalizer.relationContainsFDsetAtts(rel, fdset);
    System.out.println("test 1: relation=" + rel + ": " + sane + ", should be true");

    // Test 2: not sane
    rel = new HashSet<>(Arrays.asList("ssn", "name")); 
    sane = Normalizer.relationContainsFDsetAtts(rel, fdset);
    System.out.println("test 2: relation=" + rel + ": " + sane + ", should be false");

    // Test 3: not sane
    rel = new HashSet<>(Arrays.asList("name", "eyecolor")); 
    sane = Normalizer.relationContainsFDsetAtts(rel, fdset);
    System.out.println("test 3: relation=" + rel + ": " + sane + ", should be false");
  }



  /**
   * Suite of test codes for methods in Normalizer.java, ending with 
   */
  public static void testAttributeClosure() {
    // Test attributeClosure
  // Setup
  FD f1 = new FD(Arrays.asList("ssn"), Arrays.asList("name")); // ssn --> name
  FD f2 = new FD(Arrays.asList("ssn"), Arrays.asList("eyecolor")); // ssn --> eyecolor
  FDSet fdset = new FDSet(f1, f2);
  Set<String> alpha;
  Set<String> alphaplus;

  System.out.println("FD set: " + fdset);

  alpha = new HashSet<>(Arrays.asList("ssn", "name", "eyecolor")); 
  alphaplus = Normalizer.attributeClosure(alpha, fdset);
  System.out.println("alpha: " + alpha);
  System.out.println("alpha+: " + alphaplus);

  alpha = new HashSet<>(Arrays.asList("ssn")); 
  alphaplus = Normalizer.attributeClosure(alpha, fdset);
  System.out.println("alpha: " + alpha);
  System.out.println("alpha+: " + alphaplus);

  alpha = new HashSet<>(Arrays.asList("name")); 
  alphaplus = Normalizer.attributeClosure(alpha, fdset);
  System.out.println("alpha: " + alpha);
  System.out.println("alpha+: " + alphaplus);
  }

}
