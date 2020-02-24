package fr.jobslake.utils;

import fr.jobslake.utils.taxonomy.Node;
import fr.jobslake.utils.taxonomy.Taxonomy;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class TaxonomyTest {

    @Test
    void getInstance() {
    }

    @Test
    void addNode() {
    }

    @Test
    void testAddNode() {
    }

    @Test
    void getNodes() {
    }

    @Test
    void getIdFromNodeName() {
    }

    @Test
    void getInstanceFromId() {
    }

    @Test
    void getInstanceFromNodeName() {
    }

    @Test
    void isInTaxonomy() {
    }

    @Test
    void isLeaf() {
    }

    @Test
    void hasSameParent() {
    }

    @Test
    void nodesSimilarity() throws Exception {
        //Given
        String skill1 = "node1";
        String skill2 = "node2" ;

        buildTaxonomy();
        List<Double> weights = Arrays.asList(new Double[3]);
        weights.set(0,1.0);
        weights.set(1, 0.8);
        weights.set(2,0.6);

        //When
        Taxonomy taxonomy = Taxonomy.getInstance() ;
        Double similarity = taxonomy.nodesSimilarity("node1","node2" , weights) ;

        //Then
        // assertEquals("" , ) ;
    }

    private Taxonomy buildTaxonomy() {
        Taxonomy taxonomy = Taxonomy.getInstance();
        // create a Taxonomy graph
        // Node myNode = new Node("Test", taxonomy.root);
        Node node1 = new Node("node1", taxonomy.root);
        Node node2 = new Node("node2", taxonomy.root);
        Node node11 = new Node("node11", node1);
        Node node12 = new Node("node12", node1);

        Node node21 = new Node("node21", node2);
        Node node22 = new Node("node22", node2);

        Node node111 = new Node("node111", node11);
        Node node112 = new Node("node112", node11);
        Node node121 = new Node("node121", node12);
        Node node122 = new Node("node122", node12);
        Node node211 = new Node("node211", node21);
        Node node212 = new Node("node212", node21);
        Node node221 = new Node("node221", node22);
        Node node222 = new Node("node222", node22);


        taxonomy.addNode(node1, taxonomy.root);
        taxonomy.addNode(node2, taxonomy.root);

        taxonomy.addNode(node11, node1);
        taxonomy.addNode(node12, node1);
        taxonomy.addNode(node21, node2);
        taxonomy.addNode(node22, node2);

        taxonomy.addNode(node111, node11);
        taxonomy.addNode(node112, node11);
        taxonomy.addNode(node121, node12);
        taxonomy.addNode(node122, node12);

        taxonomy.addNode(node211, node21);
        taxonomy.addNode(node212, node21);
        taxonomy.addNode(node221, node22);
        taxonomy.addNode(node222, node22);
        return taxonomy;
    }
}