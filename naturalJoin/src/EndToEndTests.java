import java.util.HashMap;


/**
 * @author Penny Rowe
 * @date 2022/12/08
 */
public class EndToEndTests {
    /**
     * Perform end-to-end tests of natural join on two relations
     * This cannot be done with JUnit because it involves reading
     * files from disk. It should be replaced with tests that do
     * not require reading from disk, but it is useful for now.
     * 
     * @throws IllegalArgumentException
     */
    public static void main(String args[])  {
        testNestedLoopJoinsWithSelf();  // Natural join with self
        testNestedLoopJoinsCartesian();  // Cases where no attributes are shared
        testNestedLoopJoinsEmpty();  // Cases where attributes are shared, but none match
        testNestedLoopJoins();  // Cases where attributes are shared with shared values
        testPkNotFirstHash();
        testPkNotFirstSortMerge();
        testPkNotFirstNestedLoop();
        testPkNotFirstUnsortedSortMerge();
        testPkNotFirstUnsortedNestedLoop();
        testPkNotFirstUnsortedHash();
        testNestedLoopJoin();
        testSortMergeJoin();
        testHashJoin();
    
        // The following throw errors, so they must be tested one at a time
        // testNestedLoopJoinsMissingRelation();
    }

    
    /**
     * Test toy file which does not have sorted att first
     */
    public static void testPkNotFirstSortMerge() {
        String method = "Sort Merge Join";
        // Setup
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        Relation rel1 = relations.get("test_pk_not_first1");
        Relation rel2 = relations.get( "test_pk_not_first2");
        String expected = """
        first|last|ssn|dep|
        P|R|0|D
        S|N|1|C
        S|N|1|A
        S|N|1|D
        L|D|2|C
        L|D|2|A
        K|R|3|De
        K|R|3|S
        """;

        // Act
        Relation joinedRelation = RelationUtil.naturalJoin(rel1, rel2, method);

        // Assert
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("\nFailed " + method); 
            System.out.println(joinedRelation);
        }
    }

    /**
     * Test toy file which does not have sorted att first
     */
    public static void testPkNotFirstHash() {
        String method = "Hash Join";

        // Setup
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        Relation rel1 = relations.get("test_pk_not_first1");
        Relation rel2 = relations.get( "test_pk_not_first2");
        String expected = """
        first|last|ssn|dep|
        P|R|0|D
        S|N|1|C
        S|N|1|A
        S|N|1|D
        L|D|2|C
        L|D|2|A
        K|R|3|De
        K|R|3|S
        """;

        // Act
        Relation joinedRelation = RelationUtil.naturalJoin(rel1, rel2, method);

        // Assert
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("\nFailed " + method );
            System.out.println(joinedRelation);
        }
    }

    /**
     * Test toy file which does not have sorted att first
     */
    public static void testPkNotFirstNestedLoop() {
        String method = "Nested Loop Join";

        // Setup
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        Relation rel1 = relations.get("test_pk_not_first1");
        Relation rel2 = relations.get( "test_pk_not_first2");
        String expected = """
        first|last|ssn|dep|
        P|R|0|D
        S|N|1|C
        S|N|1|A
        S|N|1|D
        L|D|2|C
        L|D|2|A
        K|R|3|De
        K|R|3|S
        """;

        // Act
        Relation joinedRelation = RelationUtil.naturalJoin(rel1, rel2, method);

        // Assert
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("\nFailed " + method );
            System.out.println(joinedRelation);
        }
    }

    /**
     * Test toy file which does not have sorted att first
     */
    public static void testPkNotFirstUnsortedSortMerge() {
        // Setup
        String method = "Sort Merge Join";
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        Relation rel1 = relations.get("test_pk_not_first_unsorted1b");
        Relation rel2 = relations.get("test_pk_not_first2");
        String expected = """
        first|last|ssn|dep|
        P|R|0|D
        S|N|1|C
        S|N|1|A
        S|N|1|D
        L|D|2|C
        L|D|2|A
        K|R|3|De
        K|R|3|S
        """;

        // Act
        Relation joinedRelation = RelationUtil.naturalJoin(rel1, rel2, method);

        // Assert
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("\nFailed " + method);
            System.out.println(joinedRelation);
        }
    }

    /**
     * Test toy file which does not have sorted att first
     */
    public static void testPkNotFirstUnsortedHash() {
        // Setup
        String method = "Hash Join";
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        Relation rel1 = relations.get("test_pk_not_first_unsorted1b");
        Relation rel2 = relations.get("test_pk_not_first2");
        String expected = """
        first|last|ssn|dep|
        P|R|0|D
        S|N|1|C
        S|N|1|A
        S|N|1|D
        L|D|2|C
        L|D|2|A
        K|R|3|De
        K|R|3|S
        """;

        // Act
        Relation joinedRelation = RelationUtil.naturalJoin(rel1, rel2, method);

        // Assert
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("\nFailed " + method);
            System.out.println(joinedRelation);
        }
    }

    /**
     * Test toy file which does not have sorted att first
     */
    public static void testPkNotFirstUnsortedNestedLoop() {
        // Setup
        String method = "Nested Loop Join";
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        Relation rel1 = relations.get("test_pk_not_first_unsorted1b");
        Relation rel2 = relations.get("test_pk_not_first2");
        String expected = """
        first|last|ssn|dep|
        L|D|2|C
        L|D|2|A
        K|R|3|De
        K|R|3|S
        P|R|0|D
        S|N|1|C
        S|N|1|A
        S|N|1|D
        """;

        // Act
        Relation joinedRelation = RelationUtil.naturalJoin(rel1, rel2, method);

        // Assert
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("\nFailed " + method);
            System.out.println(joinedRelation);
        }
    }

    /**
     * Test natural join using nested loop
     */
    public static void testNestedLoopJoin() {
        String method = "Nested Loop Join";
        // Setup
        String expected = """
        officeCode|city|phone|addressLine1|addressLine2|state|country|postalCode|territory|employeeNumber|lastName|firstName|extension|email|reportsTo|jobTitle|
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1002|Murphy|Diane|x5800|dmurphy@classicmodelcars.com|NULL|President
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1056|Patterson|Mary|x4611|mpatterso@classicmodelcars.com|1002|VP Sales
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1076|Firrelli|Jeff|x9273|jfirrelli@classicmodelcars.com|1002|VP Marketing
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1143|Bow|Anthony|x5428|abow@classicmodelcars.com|1056|Sales Manager (NA)
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1165|Jennings|Leslie|x3291|ljennings@classicmodelcars.com|1143|Sales Rep
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1166|Thompson|Leslie|x4065|lthompson@classicmodelcars.com|1143|Sales Rep
        2|Boston|+1 215 837 0825|1550 Court Place|Suite 102|MA|USA|02107|NA|1188|Firrelli|Julie|x2173|jfirrelli@classicmodelcars.com|1143|Sales Rep
        2|Boston|+1 215 837 0825|1550 Court Place|Suite 102|MA|USA|02107|NA|1216|Patterson|Steve|x4334|spatterson@classicmodelcars.com|1143|Sales Rep
        3|NYC|+1 212 555 3000|523 East 53rd Street|apt. 5A|NY|USA|10022|NA|1286|Tseng|Foon Yue|x2248|ftseng@classicmodelcars.com|1143|Sales Rep
        3|NYC|+1 212 555 3000|523 East 53rd Street|apt. 5A|NY|USA|10022|NA|1323|Vanauf|George|x4102|gvanauf@classicmodelcars.com|1143|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1102|Bondur|Gerard|x5408|gbondur@classicmodelcars.com|1056|Sale Manager (EMEA)
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1337|Bondur|Loui|x6493|lbondur@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1370|Hernandez|Gerard|x2028|ghernande@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1401|Castillo|Pamela|x2759|pcastillo@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1702|Gerard|Martin|x2312|mgerard@classicmodelcars.com|1102|Sales Rep
        5|Tokyo|+81 33 224 5000|4-1 Kioicho|NULL|Chiyoda-Ku|Japan|102-8578|Japan|1621|Nishi|Mami|x101|mnishi@classicmodelcars.com|1056|Sales Rep
        5|Tokyo|+81 33 224 5000|4-1 Kioicho|NULL|Chiyoda-Ku|Japan|102-8578|Japan|1625|Kato|Yoshimi|x102|ykato@classicmodelcars.com|1621|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1088|Patterson|William|x4871|wpatterson@classicmodelcars.com|1056|Sales Manager (APAC)
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1611|Fixter|Andy|x101|afixter@classicmodelcars.com|1088|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1612|Marsh|Peter|x102|pmarsh@classicmodelcars.com|1088|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1619|King|Tom|x103|tking@classicmodelcars.com|1088|Sales Rep
        7|London|+44 20 7877 2041|25 Old Broad Street|Level 7|NULL|UK|EC2N 1HN|EMEA|1501|Bott|Larry|x2311|lbott@classicmodelcars.com|1102|Sales Rep
        7|London|+44 20 7877 2041|25 Old Broad Street|Level 7|NULL|UK|EC2N 1HN|EMEA|1504|Jones|Barry|x102|bjones@classicmodelcars.com|1102|Sales Rep
        """;
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        String relation1name = "offices";
        String relation2name = "employees";
        Relation rel1 = relations.get(relation1name);
        Relation rel2 = relations.get(relation2name);

        // Act
        Relation joinedRelation = RelationUtil.naturalJoin(rel1, rel2, method);

        // Assert
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("\nFailed " + method);
            System.out.println(joinedRelation);
        }
    }

    /**
     * Test natural join using hash join
     */
    public static void testHashJoin() {

        // Setup
        String expected = """
        officeCode|city|phone|addressLine1|addressLine2|state|country|postalCode|territory|employeeNumber|lastName|firstName|extension|email|reportsTo|jobTitle|
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1002|Murphy|Diane|x5800|dmurphy@classicmodelcars.com|NULL|President
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1056|Patterson|Mary|x4611|mpatterso@classicmodelcars.com|1002|VP Sales
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1076|Firrelli|Jeff|x9273|jfirrelli@classicmodelcars.com|1002|VP Marketing
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1088|Patterson|William|x4871|wpatterson@classicmodelcars.com|1056|Sales Manager (APAC)
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1102|Bondur|Gerard|x5408|gbondur@classicmodelcars.com|1056|Sale Manager (EMEA)
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1143|Bow|Anthony|x5428|abow@classicmodelcars.com|1056|Sales Manager (NA)
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1165|Jennings|Leslie|x3291|ljennings@classicmodelcars.com|1143|Sales Rep
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1166|Thompson|Leslie|x4065|lthompson@classicmodelcars.com|1143|Sales Rep
        2|Boston|+1 215 837 0825|1550 Court Place|Suite 102|MA|USA|02107|NA|1188|Firrelli|Julie|x2173|jfirrelli@classicmodelcars.com|1143|Sales Rep
        2|Boston|+1 215 837 0825|1550 Court Place|Suite 102|MA|USA|02107|NA|1216|Patterson|Steve|x4334|spatterson@classicmodelcars.com|1143|Sales Rep
        3|NYC|+1 212 555 3000|523 East 53rd Street|apt. 5A|NY|USA|10022|NA|1286|Tseng|Foon Yue|x2248|ftseng@classicmodelcars.com|1143|Sales Rep
        3|NYC|+1 212 555 3000|523 East 53rd Street|apt. 5A|NY|USA|10022|NA|1323|Vanauf|George|x4102|gvanauf@classicmodelcars.com|1143|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1337|Bondur|Loui|x6493|lbondur@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1370|Hernandez|Gerard|x2028|ghernande@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1401|Castillo|Pamela|x2759|pcastillo@classicmodelcars.com|1102|Sales Rep
        7|London|+44 20 7877 2041|25 Old Broad Street|Level 7|NULL|UK|EC2N 1HN|EMEA|1501|Bott|Larry|x2311|lbott@classicmodelcars.com|1102|Sales Rep
        7|London|+44 20 7877 2041|25 Old Broad Street|Level 7|NULL|UK|EC2N 1HN|EMEA|1504|Jones|Barry|x102|bjones@classicmodelcars.com|1102|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1611|Fixter|Andy|x101|afixter@classicmodelcars.com|1088|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1612|Marsh|Peter|x102|pmarsh@classicmodelcars.com|1088|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1619|King|Tom|x103|tking@classicmodelcars.com|1088|Sales Rep
        5|Tokyo|+81 33 224 5000|4-1 Kioicho|NULL|Chiyoda-Ku|Japan|102-8578|Japan|1621|Nishi|Mami|x101|mnishi@classicmodelcars.com|1056|Sales Rep
        5|Tokyo|+81 33 224 5000|4-1 Kioicho|NULL|Chiyoda-Ku|Japan|102-8578|Japan|1625|Kato|Yoshimi|x102|ykato@classicmodelcars.com|1621|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1702|Gerard|Martin|x2312|mgerard@classicmodelcars.com|1102|Sales Rep
        """;
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        String relation1name = "offices";
        String relation2name = "employees";
        Relation rel1 = relations.get(relation1name);
        Relation rel2 = relations.get(relation2name);

        // Act
        Relation joinedRelation = RelationUtil.naturalJoin(rel1, rel2, "Hash Join");

        // Assert
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("\nFailed testHashJoin");
            System.out.println(joinedRelation);
        }
        //assert joinedRelation.toString().equals(expected);

    }

    /**
     * Test natural join using sort-merge join
     */
    public static void testSortMergeJoin() {

        // Setup
        String expected = """
        officeCode|city|phone|addressLine1|addressLine2|state|country|postalCode|territory|employeeNumber|lastName|firstName|extension|email|reportsTo|jobTitle|
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1002|Murphy|Diane|x5800|dmurphy@classicmodelcars.com|NULL|President
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1056|Patterson|Mary|x4611|mpatterso@classicmodelcars.com|1002|VP Sales
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1076|Firrelli|Jeff|x9273|jfirrelli@classicmodelcars.com|1002|VP Marketing
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1143|Bow|Anthony|x5428|abow@classicmodelcars.com|1056|Sales Manager (NA)
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1165|Jennings|Leslie|x3291|ljennings@classicmodelcars.com|1143|Sales Rep
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1166|Thompson|Leslie|x4065|lthompson@classicmodelcars.com|1143|Sales Rep
        2|Boston|+1 215 837 0825|1550 Court Place|Suite 102|MA|USA|02107|NA|1188|Firrelli|Julie|x2173|jfirrelli@classicmodelcars.com|1143|Sales Rep
        2|Boston|+1 215 837 0825|1550 Court Place|Suite 102|MA|USA|02107|NA|1216|Patterson|Steve|x4334|spatterson@classicmodelcars.com|1143|Sales Rep
        3|NYC|+1 212 555 3000|523 East 53rd Street|apt. 5A|NY|USA|10022|NA|1286|Tseng|Foon Yue|x2248|ftseng@classicmodelcars.com|1143|Sales Rep
        3|NYC|+1 212 555 3000|523 East 53rd Street|apt. 5A|NY|USA|10022|NA|1323|Vanauf|George|x4102|gvanauf@classicmodelcars.com|1143|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1102|Bondur|Gerard|x5408|gbondur@classicmodelcars.com|1056|Sale Manager (EMEA)
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1337|Bondur|Loui|x6493|lbondur@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1370|Hernandez|Gerard|x2028|ghernande@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1401|Castillo|Pamela|x2759|pcastillo@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1702|Gerard|Martin|x2312|mgerard@classicmodelcars.com|1102|Sales Rep
        5|Tokyo|+81 33 224 5000|4-1 Kioicho|NULL|Chiyoda-Ku|Japan|102-8578|Japan|1621|Nishi|Mami|x101|mnishi@classicmodelcars.com|1056|Sales Rep
        5|Tokyo|+81 33 224 5000|4-1 Kioicho|NULL|Chiyoda-Ku|Japan|102-8578|Japan|1625|Kato|Yoshimi|x102|ykato@classicmodelcars.com|1621|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1088|Patterson|William|x4871|wpatterson@classicmodelcars.com|1056|Sales Manager (APAC)
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1611|Fixter|Andy|x101|afixter@classicmodelcars.com|1088|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1612|Marsh|Peter|x102|pmarsh@classicmodelcars.com|1088|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1619|King|Tom|x103|tking@classicmodelcars.com|1088|Sales Rep
        7|London|+44 20 7877 2041|25 Old Broad Street|Level 7|NULL|UK|EC2N 1HN|EMEA|1501|Bott|Larry|x2311|lbott@classicmodelcars.com|1102|Sales Rep
        7|London|+44 20 7877 2041|25 Old Broad Street|Level 7|NULL|UK|EC2N 1HN|EMEA|1504|Jones|Barry|x102|bjones@classicmodelcars.com|1102|Sales Rep
        """;
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        String relation1name = "offices";
        String relation2name = "employees";
        Relation rel1 = relations.get(relation1name);
        Relation rel2 = relations.get(relation2name);

        // Act
        Relation joinedRelation = RelationUtil.naturalJoin(rel1, rel2, "Sort Merge Join");

        // Assert
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("\nFailed testSortMergeJoin");
            System.out.println(joinedRelation);
        }
        //assert joinedRelation.toString().equals(expected);

    }

    /**
     * Test natural join using nested loop for David's cases 
     * for relations with no common attributes, for which 
     * the Cartesian Product is performed].
     */
    public static void testNestedLoopJoinsCartesian() {
        // Setup
        String method = "Nested Loop Join";
        String direc = "data/";
        Relation joinedRelation;
        String key1;
        String key2;
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        System.out.println("\n");

        // customers.txt

        // Relations that do not share an attribute with customers
        // employees.txt - no common atts
        // orderdetails.txt - no common atts
        // productlines.txt - no common atts
        // products.txt - no common atts
        // offices.txt
        // orders.txt
        // payments.txt
        // ignoreme.rtf

        // Act
        key1 = "customers";
        Relation rel1 = relations.get("customers");

        key2 = "employees";
        joinedRelation = RelationUtil.naturalJoin(rel1, relations.get(key2), method);
        String expected = """
        customerNumber|customerName|contactLastName|contactFirstName|phone|addressLine1|addressLine2|city|state|postalCode|country|salesRepEmployeeNumber|creditLimit|employeeNumber|lastName|firstName|extension|email|officeCode|reportsTo|jobTitle|
        103|Atelier graphique|Schmitt|Carine |40.32.2555|54 rue Royale|NULL|Nantes|NULL|44000|France|1370|21000.0|1002|Murphy|Diane|x5800|dmurphy@classicmodelcars.com|1|NULL|President
        103|Atelier graphique|Schmitt|Carine |40.32.2555|54 rue Royale|NULL|Nantes|NULL|44000|France|1370|21000.0|1056|Patterson|Mary|x4611|mpatterso@classicmodelcars.com|1|1002|VP Sales
        103|Atelier graphique|Schmitt|Carine |40.32.2555|54 rue Royale|NULL|Nantes|NULL|44000|France|1370|21000.0|1076|Firrelli|Jeff|x9273|jfirrelli@classicmodelcars.com|1|1002|VP Marketing
        103|Atelier graphique|Schmitt|Carine |40.32.2555|54 rue Royale|NULL|Nantes|NULL|44000|France|1370|21000.0|1088|Patterson|William|x4871|wpatterson@classicmodelcars.com|6|1056|Sales Manager (APAC)
        """;
        if (!(joinedRelation.toStringfirst(4).equals(expected))) {
            System.out.println("Failed on " + method + " for " + key1 + " and " + key2);
            System.out.println(joinedRelation.toStringfirst(4));
        }

        String[] relationsWithNoMatch = {"orderdetails", "productlines", "products"};
        for (String rel2name: relationsWithNoMatch) {
            joinedRelation = RelationUtil.naturalJoin(rel1, relations.get(rel2name), method);
            if (joinedRelation == null) {
                System.out.println("Failed on " + method + " for " + key1 + " and " + rel2name);
            }
        }        
    }

    /**
     * Test natural join using nested loop for all of David's cases 
     * that should give the Cartesian Product
     */
    public static void testNestedLoopJoinsEmpty() {
        // Setup
        String method = "Nested Loop Join";
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        Relation joinedRelation;
        System.out.println("\n");
        String key1;
        String key2;

        // Relations that do not share an attribute with customers
        // employees.txt - no common atts
        // orderdetails.txt - no common atts
        // productlines.txt - no common atts
        // products.txt - no common atts
        //
        // Relations that share an attribute with customers but no values match
        // (tested here)
        // offices.txt
        //
        // Relations that share an attribute and values with customers
        // customers and orders: orderNumber
        // customers and payments: customerNumber

        // Act
        // Relations with no common attribute
        key1 = "customers";
        key2 = "offices";
        joinedRelation = RelationUtil.naturalJoin(relations.get(key1), relations.get(key2), method);
        String expected = """
            customerNumber|customerName|contactLastName|contactFirstName|phone|addressLine1|addressLine2|city|state|postalCode|country|salesRepEmployeeNumber|creditLimit|officeCode|city|addressLine1|addressLine2|state|country|postalCode|territory|
            """;
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("Failed on " + method + " for " + key1 + " and " + key2);
            System.out.println("With common attribute " +  relations.get(key1).getCommonAtts(relations.get(key2)));
            System.out.println(joinedRelation);
        }
    }

    /**
     * Test natural join using nested loop for all of David's cases 
     * that have a common attribute and share values on that attribute
     */
    public static void testNestedLoopJoins() {
        // Setup
        String method = "Nested Loop Join";
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        System.out.println("\n");
        String key1;
        String key2;

        // Relations that share an attribute and values with customers
        // customers and orders: orderNumber
        // customers and payments: customerNumber

        key1 = "customers";
        key2 = "payments";
        RelationUtil.naturalJoin(relations.get(key1), relations.get(key2), method);
        // Not going to test the result, just make sure it runs without throwing an error.
    }

    /**
     * Test natural join using nested loop for David's cases 
     * for relations with no common attributes, for which 
     * the Cartesian Product is performed].
     */
    public static void testNestedLoopJoinsWithSelf() {
        // Setup
        String method = "Nested Loop Join";
        String direc = "data/";
        Relation joinedRelation;
        String key1;
        String key2;
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        System.out.println("\n");

        // Act
        key1 = "customers";
        key2 = "customers";
        joinedRelation = RelationUtil.naturalJoin(relations.get(key1), relations.get(key2), method);
        String expected = """
        customerNumber|customerName|contactLastName|contactFirstName|phone|addressLine1|addressLine2|city|state|postalCode|country|salesRepEmployeeNumber|creditLimit|customerName|contactLastName|contactFirstName|phone|addressLine1|addressLine2|city|state|postalCode|country|salesRepEmployeeNumber|creditLimit|
        103|Atelier graphique|Schmitt|Carine |40.32.2555|54 rue Royale|NULL|Nantes|NULL|44000|France|1370|21000.0|Atelier graphique|Schmitt|Carine |40.32.2555|54 rue Royale|NULL|Nantes|NULL|44000|France|1370|21000.0
        112|Signal Gift Stores|King|Jean|7025551838|8489 Strong St.|NULL|Las Vegas|NV|83030|USA|1166|71800.0|Signal Gift Stores|King|Jean|7025551838|8489 Strong St.|NULL|Las Vegas|NV|83030|USA|1166|71800.0
        114|Australian Collectors, Co.|Ferguson|Peter|03 9520 4555|636 St Kilda Road|Level 3|Melbourne|Victoria|3004|Australia|1611|117300.0|Australian Collectors, Co.|Ferguson|Peter|03 9520 4555|636 St Kilda Road|Level 3|Melbourne|Victoria|3004|Australia|1611|117300.0
        119|La Rochelle Gifts|Labrune|Janine |40.67.8555|67 rue des Cinquante Otages|NULL|Nantes|NULL|44000|France|1370|118200.0|La Rochelle Gifts|Labrune|Janine |40.67.8555|67 rue des Cinquante Otages|NULL|Nantes|NULL|44000|France|1370|118200.0
        """;
        if (!(joinedRelation.toStringfirst(4).equals(expected))) {
            System.out.println("Failed on " + method + " for " + key1 + " and " + key2);
            System.out.println(joinedRelation.toStringfirst(4));
        }
    }


    /**
     * Test natural join using hash join
     */
    public static void testHashJoins() {

        // Setup
        String expected = """
        officeCode|city|phone|addressLine1|addressLine2|state|country|postalCode|territory|employeeNumber|lastName|firstName|extension|email|reportsTo|jobTitle|
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1002|Murphy|Diane|x5800|dmurphy@classicmodelcars.com|NULL|President
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1056|Patterson|Mary|x4611|mpatterso@classicmodelcars.com|1002|VP Sales
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1076|Firrelli|Jeff|x9273|jfirrelli@classicmodelcars.com|1002|VP Marketing
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1088|Patterson|William|x4871|wpatterson@classicmodelcars.com|1056|Sales Manager (APAC)
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1102|Bondur|Gerard|x5408|gbondur@classicmodelcars.com|1056|Sale Manager (EMEA)
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1143|Bow|Anthony|x5428|abow@classicmodelcars.com|1056|Sales Manager (NA)
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1165|Jennings|Leslie|x3291|ljennings@classicmodelcars.com|1143|Sales Rep
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1166|Thompson|Leslie|x4065|lthompson@classicmodelcars.com|1143|Sales Rep
        2|Boston|+1 215 837 0825|1550 Court Place|Suite 102|MA|USA|02107|NA|1188|Firrelli|Julie|x2173|jfirrelli@classicmodelcars.com|1143|Sales Rep
        2|Boston|+1 215 837 0825|1550 Court Place|Suite 102|MA|USA|02107|NA|1216|Patterson|Steve|x4334|spatterson@classicmodelcars.com|1143|Sales Rep
        3|NYC|+1 212 555 3000|523 East 53rd Street|apt. 5A|NY|USA|10022|NA|1286|Tseng|Foon Yue|x2248|ftseng@classicmodelcars.com|1143|Sales Rep
        3|NYC|+1 212 555 3000|523 East 53rd Street|apt. 5A|NY|USA|10022|NA|1323|Vanauf|George|x4102|gvanauf@classicmodelcars.com|1143|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1337|Bondur|Loui|x6493|lbondur@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1370|Hernandez|Gerard|x2028|ghernande@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1401|Castillo|Pamela|x2759|pcastillo@classicmodelcars.com|1102|Sales Rep
        7|London|+44 20 7877 2041|25 Old Broad Street|Level 7|NULL|UK|EC2N 1HN|EMEA|1501|Bott|Larry|x2311|lbott@classicmodelcars.com|1102|Sales Rep
        7|London|+44 20 7877 2041|25 Old Broad Street|Level 7|NULL|UK|EC2N 1HN|EMEA|1504|Jones|Barry|x102|bjones@classicmodelcars.com|1102|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1611|Fixter|Andy|x101|afixter@classicmodelcars.com|1088|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1612|Marsh|Peter|x102|pmarsh@classicmodelcars.com|1088|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1619|King|Tom|x103|tking@classicmodelcars.com|1088|Sales Rep
        5|Tokyo|+81 33 224 5000|4-1 Kioicho|NULL|Chiyoda-Ku|Japan|102-8578|Japan|1621|Nishi|Mami|x101|mnishi@classicmodelcars.com|1056|Sales Rep
        5|Tokyo|+81 33 224 5000|4-1 Kioicho|NULL|Chiyoda-Ku|Japan|102-8578|Japan|1625|Kato|Yoshimi|x102|ykato@classicmodelcars.com|1621|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1702|Gerard|Martin|x2312|mgerard@classicmodelcars.com|1102|Sales Rep
        """;
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        String relation1name = "offices";
        String relation2name = "employees";
        Relation rel1 = relations.get(relation1name);
        Relation rel2 = relations.get(relation2name);

        // Act
        Relation joinedRelation = RelationUtil.naturalJoin(rel1, rel2, "Hash Join");

        // Assert
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("\nFailed testHashJoin");
            System.out.println(joinedRelation);
        }
        //assert joinedRelation.toString().equals(expected);

    }

    /**
     * Test natural join using sort-merge join
     */
    public static void testSortMergeJoins() {

        // Setup
        String expected = """
        officeCode|city|phone|addressLine1|addressLine2|state|country|postalCode|territory|employeeNumber|lastName|firstName|extension|email|reportsTo|jobTitle|
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1002|Murphy|Diane|x5800|dmurphy@classicmodelcars.com|NULL|President
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1056|Patterson|Mary|x4611|mpatterso@classicmodelcars.com|1002|VP Sales
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1076|Firrelli|Jeff|x9273|jfirrelli@classicmodelcars.com|1002|VP Marketing
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1143|Bow|Anthony|x5428|abow@classicmodelcars.com|1056|Sales Manager (NA)
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1165|Jennings|Leslie|x3291|ljennings@classicmodelcars.com|1143|Sales Rep
        1|San Francisco|+1 650 219 4782|100 Market Street|Suite 300|CA|USA|94080|NA|1166|Thompson|Leslie|x4065|lthompson@classicmodelcars.com|1143|Sales Rep
        2|Boston|+1 215 837 0825|1550 Court Place|Suite 102|MA|USA|02107|NA|1188|Firrelli|Julie|x2173|jfirrelli@classicmodelcars.com|1143|Sales Rep
        2|Boston|+1 215 837 0825|1550 Court Place|Suite 102|MA|USA|02107|NA|1216|Patterson|Steve|x4334|spatterson@classicmodelcars.com|1143|Sales Rep
        3|NYC|+1 212 555 3000|523 East 53rd Street|apt. 5A|NY|USA|10022|NA|1286|Tseng|Foon Yue|x2248|ftseng@classicmodelcars.com|1143|Sales Rep
        3|NYC|+1 212 555 3000|523 East 53rd Street|apt. 5A|NY|USA|10022|NA|1323|Vanauf|George|x4102|gvanauf@classicmodelcars.com|1143|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1102|Bondur|Gerard|x5408|gbondur@classicmodelcars.com|1056|Sale Manager (EMEA)
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1337|Bondur|Loui|x6493|lbondur@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1370|Hernandez|Gerard|x2028|ghernande@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1401|Castillo|Pamela|x2759|pcastillo@classicmodelcars.com|1102|Sales Rep
        4|Paris|+33 14 723 4404|43 Rue Jouffroy Dabbans|NULL|NULL|France|75017|EMEA|1702|Gerard|Martin|x2312|mgerard@classicmodelcars.com|1102|Sales Rep
        5|Tokyo|+81 33 224 5000|4-1 Kioicho|NULL|Chiyoda-Ku|Japan|102-8578|Japan|1621|Nishi|Mami|x101|mnishi@classicmodelcars.com|1056|Sales Rep
        5|Tokyo|+81 33 224 5000|4-1 Kioicho|NULL|Chiyoda-Ku|Japan|102-8578|Japan|1625|Kato|Yoshimi|x102|ykato@classicmodelcars.com|1621|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1088|Patterson|William|x4871|wpatterson@classicmodelcars.com|1056|Sales Manager (APAC)
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1611|Fixter|Andy|x101|afixter@classicmodelcars.com|1088|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1612|Marsh|Peter|x102|pmarsh@classicmodelcars.com|1088|Sales Rep
        6|Sydney|+61 2 9264 2451|5-11 Wentworth Avenue|Floor #2|NULL|Australia|NSW 2010|APAC|1619|King|Tom|x103|tking@classicmodelcars.com|1088|Sales Rep
        7|London|+44 20 7877 2041|25 Old Broad Street|Level 7|NULL|UK|EC2N 1HN|EMEA|1501|Bott|Larry|x2311|lbott@classicmodelcars.com|1102|Sales Rep
        7|London|+44 20 7877 2041|25 Old Broad Street|Level 7|NULL|UK|EC2N 1HN|EMEA|1504|Jones|Barry|x102|bjones@classicmodelcars.com|1102|Sales Rep
        """;
        String direc = "data/";
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        String relation1name = "offices";
        String relation2name = "employees";
        Relation rel1 = relations.get(relation1name);
        Relation rel2 = relations.get(relation2name);

        // Act
        Relation joinedRelation = RelationUtil.naturalJoin(rel1, rel2, "Sort Merge Join");

        // Assert
        if (!(joinedRelation.toString().equals(expected))) {
            System.out.println("\nFailed testSortMergeJoin");
            System.out.println(joinedRelation);
        }
        //assert joinedRelation.toString().equals(expected);

    }

    /**
     * Test natural join on a case with a missing relation,
     * to make sure it throws and error as desired.
     * 
     * @throws java.lang.NullPointerException
     */
    public static void testNestedLoopJoinsMissingRelation() {
        // Setup
        String method = "Nested Loop Join";
        String direc = "data/";
        Relation joinedRelation;
        String key1;
        String key2;
        HashMap<String, Relation> relations = RelationUtil.getRelationsFromFiles(direc);
        System.out.println("\n");

        // Act
        key1 = "customers";
        key2 = "missingrelation";
        joinedRelation = RelationUtil.naturalJoin(relations.get(key1), relations.get(key2), method);
        if (!(joinedRelation == null)) {
            System.out.println("Failed on " + method + " for " + key1 + " and " + key2);
        }
    }

}




