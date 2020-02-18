package fr.jobslake;

import fr.jobslake.utils.Taxonomy;
import fr.jobslake.utils.taxonomy.Node;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MatchingServicesApplication {

	public static void main(String[] args) {
		//SpringApplication.run(MatchingServicesApplication.class, args);
		Taxonomy taxonomy = Taxonomy.getInstance();

		Node myNode = new Node("Test", taxonomy.root);

		taxonomy.addNode(myNode, taxonomy.root);

		System.out.println(taxonomy.root.toString());
	}

}
