import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 * This class provides static methods for performing normalization
 * 
 * @author Penny Rowe
 * @version 2022/11/9
 */
public class Normalizer {

  /**
   * Performs BCNF decomposition
   * where a relational schema is in BCNF iff the left-hand side of all non-trivial
   * FDs is a superkey
   * 
   * @param rel   A relation (as an attribute set)
   * @param fdset A functional dependency set
   * @return a set of relations (as attribute sets) that are in BCNF
   */
  public static Set<Set<String>> BCNFDecompose(Set<String> rel, FDSet fdset) {

    // Test if the given relation is already in BCNF with respect to
    // the provided FD set.
    System.out.println("Testing if in BCNF");
    if (isBCNF(rel, fdset)) {
      Set<Set<String>> out = new HashSet<>();
      out.add(rel);
      System.out.println("Relation is already in BCNF");
      return out;
    }

    FD splittingFD;
    FD fd;
    // Set<String> attset = new HashSet<>();
    Set<String> rel1 = new HashSet<>();
    Set<String> rel2 = new HashSet<>();
    Set<String> extras = new HashSet<>();
    FDSet fdset1 = new FDSet();
    FDSet fdset2 = new FDSet();
    FDSet unpreserved = new FDSet();
    Set<Set<String>>bcnf1; // = new HashSet<>();
    Set<Set<String>>bcnf2; // = new HashSet<>();
    Set<Set<String>>bcnf = new HashSet<>();

    // Get the closure of fdset
    // FDSet fdsetclose = FDUtil.fdSetClosure(fdset);
    //
    // For now, use mine instead
    FDSet fdsetclose = FDUtilRowe.fdSetClosure(fdset);

    // Print some information (the current relational schema, its FD Set, 
    // and its superkeys) at each decision point
    System.out.println("Current schema: " + rel);
    // System.out.println("FDset: " + fdsetclose);

    // Identify the first FD that violates BCNF. 
    splittingFD = findFirstNonBCNF(rel, fdsetclose);

    System.out.println("*** Splitting on " + splittingFD + " ***");
    
    // Split the relation's attributes (attset) using the bad FD
    // attset.addAll(rel);
    rel1.addAll(splittingFD.getLeft());
    rel1.addAll(splittingFD.getRight());
    
    rel2.addAll(splittingFD.getLeft());
    extras.addAll(rel);
    extras.removeAll(splittingFD.getRight());
    rel2.addAll(extras);

    // Repeat until all relations are in BCNF:
    // Redistribute the FDs in the closure of fdset to the two new relations (R1 and R2)
    // Iterate through closure of the given set of FDs
    boolean gotfd = false;
    Iterator<FD> iterator = fdsetclose.iterator();
    while (iterator.hasNext()) {
      fd = iterator.next();
      // Test if the union of all attributes in the FD is a subset of the R1 (or R2) relation. 
      // - If so, then the FD gets added to the R_Left's (or R_Right's) FD set
      // - If the union is not a subset of either new relation, then the FD is discarded
      if (relationContainsFDatts(rel1, fd)) {
        fdset1.add(fd);
        gotfd = true;
      }
      if (relationContainsFDatts(rel2, fd)) {
        fdset2.add(fd);
        gotfd = true;
      }
      if (!gotfd) {
        unpreserved.add(fd);
      }
    }

    // return BCNF_Decomp(R1, F1) U BCNF_Decomp(R2, F2)
    System.out.println("Left Schema = " + rel1);
    bcnf1 = BCNFDecompose(rel1, fdset1); 
    System.out.println("Right Schema = " + rel2);
    bcnf2 = BCNFDecompose(rel2, fdset2);
    bcnf.addAll(bcnf1);
    bcnf.addAll(bcnf2);

    return bcnf;
  }


  /**
   * For a given relation, finds the first functional dependency that is not in BCNF
   * If all FDs are in BCNF, returns an empty set
   * 
   * @param rel   A relation (as an attribute set)
   * @param fdset A functional dependency set
   * @return The first FD that is not in BCNF, or an empty FD if none
   */
  public static FD findFirstNonBCNF(Set<String> rel, FDSet fdset) {
    FD fd;
    Set<String> left;
    Set<String> right;

    // Get the superkeys for this relation based on fdset
    Set<Set<String>> superkeys = findSuperkeys(rel, fdset);
    System.out.println("Current schema's superkeys = " + superkeys);    

    // Loop over the FDs and test if they satisfy BCNF:
    //   - The FD is trivial
    //   - The left hand set of attributes is a superkey
    Iterator<FD> iterator = fdset.iterator();
    while (iterator.hasNext()) {
      fd = iterator.next();
      left = fd.getLeft();
      right = fd.getRight();
      if (!left.containsAll(right)) {
        // The FD is not trivial, so check if the LHS is a superkey
        if (!superkeys.contains(left)) {
          return fd;
        }
      }
    }

    // If no bad FDs found, return null
    return null;
  }

  /**
   * Tests whether the given relation is in BCNF. A relation is in BCNF iff the
   * left-hand attribute set of all nontrivial FDs is a super key.
   * 
   * @param rel   A relation (as an attribute set)
   * @param fdset A functional dependency set
   * @return true if the relation is in BCNF with respect to the specified FD set
   */
  public static boolean isBCNF(Set<String> rel, FDSet fdset) {

    FD badfd = findFirstNonBCNF(rel, fdset);
    if (badfd == null) {
      return true;
    }
    return false;
}

  /**
   * This method returns a set of super keys
   * 
   * @param rel A relation (as an attribute set)
   * @param fdset A functional dependency set
   * @return A set of super keys
   */
  public static Set<Set<String>> findSuperkeys(Set<String> rel, FDSet fdset) {
    // Sanity check: are all the attributes in the FD set even in the relation?
    // Throw an IllegalArgumentException if not.
    boolean sane = relationContainsFDsetAtts(rel, fdset);
    if (!sane){
      throw new IllegalArgumentException("Some members FD set are not in the relational schema.");
    }

    Set<Set<String>> keys = new HashSet<>();
    Set<String> alpha;
    Set<String> alphaplus;
    
    // Get the power set of the attributes
    final Set<Set<String>> pset = FDUtil.powerSet(rel);

    // Iterate through the power set of the attributes and test if it
    // is superkey by checking the attribute closure of each subset
    Iterator<Set<String>> psetIterator = pset.iterator();
    while (psetIterator.hasNext()) {
      // Get the set of attributes
      alpha = psetIterator.next();

      // Run attribute closure algorithm
      // Only if the attribute set is not the null set
      if (!alpha.isEmpty()) {
        alphaplus = attributeClosure(alpha, fdset);

        // If α+ includes all attributes in schema R (given in rel), then α is a superkey
        if (alphaplus.containsAll(rel)) {
          keys.add(alpha);
        }
      }
    }
    return keys;
  }

  /**
   * This method returns true if all attributes in the FD are in the
   * relation, else false
   * 
   * @param rel A relation (as an attribute set)
   * @param fds A functional dependency 
   * @return true if all attributes in the FD are in the relatioin
   */
  public static boolean relationContainsFDatts(Set<String> rel, FD fd) {

    Set<String> left;
    Set<String> right;

    left = fd.getLeft();
    if (!rel.containsAll(left)) {
      return false;
    }
    right = fd.getRight();
    if (!rel.containsAll(right)) {
      return false;
    }
    return true;
  }


  /**
   * This method returns true if all attributes in the FD set are in the
   * relation, else false
   * 
   * @param rel   A relation (as an attribute set)
   * @param fdset A functional dependency set
   * @return a set of super keys
   */
  public static boolean relationContainsFDsetAtts(Set<String> rel, FDSet fdset) {
    //Set<String> left;
    //Set<String> right;
    //boolean bool;
    Iterator<FD> iterator = fdset.iterator();
    while (iterator.hasNext()) {
      FD fd = iterator.next();
      if (!relationContainsFDatts(rel, fd)) {
        return false;
      }
      // left = fd.getLeft();
      // if (!rel.containsAll(left)) {
      //   return false;
      // }
      // right = fd.getRight();
      // if (!rel.containsAll(right)) {
      //   return false;
      // }
    }
    return true;
  }

  

   /**
   * This method computes the attribute closure (alpha+); that is 
   * the set of all attributes in schema R that an attribute set (alpha) 
   * can determine, under a set of functional dependencies (fdset)
   * (See notes 4a p. 48)
   * @param alpha A set of attributes
   * @return A set of attributes representing the closure of alpha
   */
  public static Set<String> attributeClosure(Set<String> alpha, FDSet fdset) {

    Set<String> alphaplus = new HashSet<>(alpha);
    Set<String> alphaplusprevious = new HashSet<>();
    
    // Search for an FD for which its left side is in alpha+,
    // but the right is not
    boolean notdone = true;
    Set<String> left;
    Set<String> right;
    while (notdone) {
      // for each FD beta --> gamma element of FD
      Iterator<FD> iterator = fdset.iterator();
      while (iterator.hasNext()) {
        FD fd = iterator.next();
        left = fd.getLeft();
        right = fd.getRight();
        if (alphaplus.containsAll(left)) { // if left in alphaplus
          alphaplus.addAll(right);
        }
      }

      if (alphaplus.equals(alphaplusprevious)) {
        notdone = false; // alphaplus not changing: done
      }
      alphaplusprevious = new HashSet<>(alphaplus);
    }
    return alphaplus;
  }

}