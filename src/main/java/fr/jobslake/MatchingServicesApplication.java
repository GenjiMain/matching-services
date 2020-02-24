package fr.jobslake;

import fr.jobslake.utils.taxonomy.Taxonomy;
import fr.jobslake.utils.taxonomy.Node;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class MatchingServicesApplication {

	public static void main(String[] args) throws Exception {
		//SpringApplication.run(MatchingServicesApplication.class, args);
		Taxonomy taxonomy = Taxonomy.getInstance();
		// create a Taxonomy graph
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

		// System.out.println(taxonomy.getNodes());
		Integer id =1 ;
		// System.out.println(taxonomy.getIdFromNodeName("node1"));
		// System.out.println(taxonomy.getInstanceFromNodeName("node222"));

		// System.out.println(taxonomy.isLeaf("node1"));
		//System.out.println(taxonomy.hasSameParent("node222", "node221"));
		List<Double> weights = Arrays.asList(new Double[3]);
		weights.set(0,1.0);
		weights.set(1, 0.8);
		weights.set(2,0.6);
		// System.out.println(taxonomy.getDepthOfTaxonomy());
		// System.out.println(taxonomy.getNodes());
		// System.out.println(taxonomy.getAllLeavesName());
		// System.out.println(taxonomy.isInTaxonomy("node"));
		// System.out.println(node01.getParent().name);
		System.out.println(taxonomy.nodesSimilarity("node221", "node221" ,weights));
		// System.out.println(taxonomy.getAllAntecedent("node221"));


	}

}

