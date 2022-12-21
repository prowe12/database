import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
/**
 * This utility class is not meant to be instantitated, it just provides some
 * useful methods on FD sets.
 * 
 * @author Penny Rowe
 * @version 2022/10/26
 */
public final class FDUtil {

  /**
   * Resolves all trivial FDs in the given set of FDs
   * 
   * @param fdset (Immutable) FD Set
   * @return a set of trivial FDs with respect to the given FDSet
   */
  public static FDSet trivial(final FDSet fdset) {
    // Obtain the power set of each FD's left-hand attributes. For each
    // element in the power set, create a new FD and add it to the new FDSet.

    // Iterate over FDset, get the left hand side, 
    // obtain the power set of the left-hand attributes via:
    //     <E> Set<Set<E>> powerSet(final Set<E> set),
    // Then, for each element in the powerset,
    // create a new FD and it add it to the new FDSet
    Set<String> left;
    Set<String> right;
    Set<Set<String>> pset;
    FDSet newfdset = new FDSet();
    Iterator<FD> iterator = fdset.iterator();
    while (iterator.hasNext()) {
      FD fd = iterator.next();
      left = fd.getLeft();
      pset = powerSet(left);

      // Create a new FDset that will hold the left attribute from the FD
      // and will sequentially add each powerset attribute to the right
      // Iterate over the powerset and augment the fdset
      Iterator<Set<String>> psetIterator = pset.iterator();
      while (psetIterator.hasNext()) {
        right = psetIterator.next();
        // Only add the right attributes if they are not null
        if (!right.isEmpty()) {
          FD newfd = new FD(left, right);
          newfdset.add(newfd);
        }
      }
    }
    return newfdset;
  }

  /**
   * Augments every FD in the given set of FDs with the given attributes
   * E.g. augment(fdset, attrs) 
   * where fdset = [A --> B, AB --> C] and
   * where Set<String> attrs = new HashSet<>(Arrays.asList("C")), gives
   *   [
   *    AC --> BC
   *    ABC --> C
   *    ]
   * 
   * @param fdset FD Set (Immutable)
   * @param attrs a set of attributes with which to augment FDs (Immutable)
   * @return a set of augmented FDs
   */
  public static FDSet augment(final FDSet fdset, final Set<String> attrs) {

    // Make a deepcopy so we can modify it without corrupting the original
    FDSet newfdset = new FDSet();

    Iterator<FD> iterator = fdset.iterator();
    while (iterator.hasNext()) {
      // Get a deepcopy of the current FD (so we don't modify the fd and therefore the fdset)
      FD fd = new FD(iterator.next());

      // Add the attrs to the right and then add the fd to the list
      fd.addToLeft(attrs);
      fd.addToRight(attrs);
      newfdset.add(fd);

    };
    return newfdset;
  }

  /**
   * Exhaustively resolves transitive FDs with respect to the given set of FDs
   * 
   * @param fdset (Immutable) FD Set
   * @return all transitive FDs with respect to the input FD set
   */
  public static FDSet transitive(final FDSet fdset) {
    // Examine each pair of FDs in the given set. If the transitive property
    // holds on the pair of FDs, then generate the new FD and add it to a new FDSet.
    FDSet fdset1 = new FDSet(fdset);
    FDSet fdset2 = new FDSet(fdset);
    FDSet newfdset = new FDSet();
    FDSet transitivefdset = new FDSet();

    // Repeat until no new transitive FDs are found
    // Compare fdset1 to fdset2. The first time around, we will compare fdset1 to itself.
    // Then we will add any new sets found to fdset1 and compare fdset1 to those new sets.
    // That should compare all the old sets to the new sets, and the new sets to themselves.
    int count = 0;
    boolean allfound = false;
    Iterator<FD> iterator1;
    Iterator<FD> iterator2;
    while ((!allfound) & (count <= 10)) {
      newfdset = new FDSet();
      count += 1;

      // Compare fdset1 and fdset2
      iterator1 = fdset1.iterator();
      while (iterator1.hasNext()) {
        FD fd1 = iterator1.next();
        iterator2 = fdset2.iterator();
        while (iterator2.hasNext()) {
          // Transitivity rule applies if fd1.right contains fd2.left
          // In which case add FD(fd1.left, fd2.right) to the fdset for the trivial rule
          // e.g. A --> AB, AB --> C     (AB contains AB)
          //  =>  A --> C
          // 
          // BUT we have to check both attributes on the right:
          // e.g. A --> AB, B --> C      (AB contains B)
          //  =>  A --> C
          // 
          // AND NOT FOR THIS: 
          // e.g. A --> B, AB --> C      (B does not contain AB)
          //  =>  A xx> C (not true)
          // 
          FD fd2 = iterator2.next();
          if (fd1.getRight().containsAll(fd2.getLeft())) {
            newfdset.add(new FD(fd1.getLeft(), fd2.getRight()));
          }
        }
      }

      // Get the size of the list of all
      int oldsize = fdset1.size();

      // Add the new fds to fdset1. Did it get bigger?
      fdset1.addAll(newfdset);
      int newsize = fdset1.size();
      if (newsize == oldsize) {
        // We are no longer adding more, so we are done
        allfound = true;
      }
      else {
        transitivefdset.addAll(newfdset);
        fdset2 = new FDSet(newfdset);
      }
    }

    return transitivefdset;
  }


  /**
   * Generates the closure of the given FD Set
   * 
   * @param fdset (Immutable) FD Set
   * @return the closure of the input FD Set
   */
  public static FDSet fdSetClosure(final FDSet fdset) {
    // Use the FDSet copy constructor to deep copy the given FDSet
    FDSet fdplus = new FDSet(fdset);
    int count = 0;
    boolean allfound = false;
    // Stop when all results found or when 10^5 trials
    while ((!allfound) & (count <= 1e5)) {
      count += 1;
      int oldsize = fdplus.size();

      // Augment: Generate new FDs by applying Augmentation Rules
      // Assume that all the possible set members are given in fdset
      // Start by getting all unique set members A, B, C, etc
      Set<String> membSet = getallmembers(fdset);
      Set<Set<String>> membPset = powerSet(membSet);
      FDSet augmentset = new FDSet();
      Iterator<Set<String>> iterator = membPset.iterator();
      while (iterator.hasNext()) {
        Set<String> memb = iterator.next();
        if (!memb.isEmpty()) {
          FDSet newset = augment(fdplus, memb);
          augmentset.addAll(newset);
        }
      }
      fdplus.addAll(augmentset);

      // Trivial: Generate new FDs generated by applying Trivial Rules
      FDSet trivialset = trivial(fdplus);
      fdplus.addAll(trivialset);
      
      // Generate new FDs generated by applying Transitivity Rule.
      FDSet transitiveSet = transitive(fdplus);
      fdplus.addAll(transitiveSet);

      // Done if all FDs found - that is, if the size of fdplus did not change, 
      // in that case, the while loop ends.
      if (fdplus.size() == oldsize) {
        allfound = true;
      }

    }
    return fdplus;
  }

  /**
   * Get all members of functional dependency set
   * @param fdset FD Set (Immutable)
   * @return The unique members of the set
   */
  public static Set<String> getallmembers(FDSet fdset) {
    Set<String> membSet = new HashSet<String>();
    Iterator<FD> iterator = fdset.iterator();
    while (iterator.hasNext()) {
      FD fd = iterator.next();
      membSet.addAll(fd.getLeft());   
      membSet.addAll(fd.getRight());   
    }
    return membSet;
  }


  /**
   * Generates the power set of the given set (that is, all subsets of
   * the given set of elements)
   * 
   * @param set Any set of elements (Immutable)
   * @return the power set of the input set
   */
  @SuppressWarnings("unchecked")
  public static <E> Set<Set<E>> powerSet(final Set<E> set) {

    // base case: power set of the empty set is the set containing the empty set
    if (set.size() == 0) {
      Set<Set<E>> basePset = new HashSet<>();
      basePset.add(new HashSet<>());
      return basePset;
    }

    // remove the first element from the current set
    E[] attrs = (E[]) set.toArray();
    set.remove(attrs[0]);

    // recurse and obtain the power set of the reduced set of elements
    Set<Set<E>> currentPset = FDUtil.powerSet(set);

    // restore the element from input set
    set.add(attrs[0]);

    // iterate through all elements of current power set and union with first
    // element
    Set<Set<E>> otherPset = new HashSet<>();
    for (Set<E> attrSet : currentPset) {
      Set<E> otherAttrSet = new HashSet<>(attrSet);
      otherAttrSet.add(attrs[0]);
      otherPset.add(otherAttrSet);
    }
    currentPset.addAll(otherPset);
    return currentPset;
  }
}