package fr.jobslake.utils;

import fr.jobslake.utils.exceptions.NotInTaxonomyException;
import fr.jobslake.utils.exceptions.NotLeafException;
import fr.jobslake.utils.exceptions.NotSameLengthAsTaxonomyDepthException;
import fr.jobslake.utils.taxonomy.Node;
import fr.jobslake.utils.taxonomy.Taxonomy;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class NodeSimilarityTest {


	Taxonomy taxonomy = Taxonomy.getInstance();

	NodeSimilarityTest() throws IOException {
	}

	@Test
	void testNodeSimilarity() throws NotSameLengthAsTaxonomyDepthException, NotLeafException, NotInTaxonomyException {

		List<Double> weights = Arrays.asList(new Double[3]);
		weights.set(0,1.0);
		weights.set(1, 0.8);
		weights.set(2,0.6);

		Node node1 = new Node("node1", taxonomy.root);
		Node node2 = new Node("node2", taxonomy.root);

		Node node11 = new Node("node11", node1);
		Node node12 = new Node("node12", node1);
		Node node21 = new Node("node21", node2);
		Node node22 = new Node("node22", node2);

		Node node111 = new Node("data science", node11);
		Node node112 = new Node("machine learning", node11);
		Node node121 = new Node("java", node12);
		Node node122 = new Node("python", node12);
		Node node211 = new Node("power bi", node21);
		Node node212 = new Node("sql server", node21);
		Node node221 = new Node("visual basic", node22);
		Node node222 = new Node("c++", node22);

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

		System.out.println(taxonomy.nodesSimilarity("python", "python", weights));
		System.out.println(taxonomy.nodesSimilarity("python", "java", weights));
		System.out.println(taxonomy.nodesSimilarity("python", "machine learning", weights));
		System.out.println(taxonomy.nodesSimilarity("python", "c++", weights));
	}


}
