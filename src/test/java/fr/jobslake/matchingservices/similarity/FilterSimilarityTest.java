package fr.jobslake.matchingservices.similarity;

import fr.jobslake.similarity.Pos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FilterSimilarityTest {


	Pos posObject = new Pos();

	FilterSimilarityTest() throws IOException {
	}


	@Test
	void testFilterSimilarity() {

		String sentence1 = "mehdi is a data scientist with inevtiv it in France and paris";
		String sentence2 = "mehdi is a data scientist with inevtiv it in France and paris";

		String taggedSentence1 = this.posObject.tagSentence(sentence1);
		String taggedSentence2 = this.posObject.tagSentence(sentence2);

		HashMap<String, ArrayList<String>> filterSentence1 = this.posObject.buildSentenceTags(taggedSentence1);
		HashMap<String, ArrayList<String>> filterSentence2 = this.posObject.buildSentenceTags(taggedSentence2);

		assertEquals(10, this.posObject.filtredSimilarity(filterSentence1, filterSentence2));
	}


}
