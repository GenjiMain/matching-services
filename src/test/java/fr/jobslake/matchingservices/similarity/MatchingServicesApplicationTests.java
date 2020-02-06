package fr.jobslake.matchingservices.similarity;

import fr.jobslake.similarity.Pos;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MatchingServicesApplicationTests {


	Pos posObject = new Pos();

	MatchingServicesApplicationTests() throws IOException {
	}

	@Test
	void testTagSentence() {

		String sentence = "mehdi is a data scientist";
		String response = "mehdi_NNP is_VBZ a_DT data_NN scientist_NN ";

		assertEquals(response, this.posObject.tagSentence(sentence));
	}

}
