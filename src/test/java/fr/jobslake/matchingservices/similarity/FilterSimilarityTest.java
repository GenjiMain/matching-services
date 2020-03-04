package fr.jobslake.matchingservices.similarity;

import fr.jobslake.similarity.Pos;
import fr.jobslake.utils.exceptions.NotInTaxonomyException;
import fr.jobslake.utils.exceptions.NotLeafException;
import fr.jobslake.utils.exceptions.NotSameLengthAsTaxonomyDepthException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


class FilterSimilarityTest {


	Pos posObject = new Pos();

	FilterSimilarityTest() throws IOException {
	}


	@Test
	void testFilterSimilarity() throws NotLeafException, NotInTaxonomyException, NotSameLengthAsTaxonomyDepthException {

		String sentence1 = "mehdi is a data science with inevtiv it in France and paris";
		String sentence2 = "mehdi is a data science with inevtiv it in France and paris";

		String taggedSentence1 = this.posObject.tagSentence(sentence1);
		String taggedSentence2 = this.posObject.tagSentence(sentence2);

		HashMap<String, ArrayList<String>> filterSentence1 = this.posObject.buildSentenceSuperTags(taggedSentence1);
		HashMap<String, ArrayList<String>> filterSentence2 = this.posObject.buildSentenceSuperTags(taggedSentence2);

		double score = this.posObject.posFiltredSimilarity(filterSentence1, filterSentence2);

		System.out.println(score);
		assertEquals(1, score);
	}


}
