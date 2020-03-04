package fr.jobslake.similarity;

import fr.jobslake.utils.exceptions.NotInTaxonomyException;
import fr.jobslake.utils.exceptions.NotLeafException;
import fr.jobslake.utils.exceptions.NotSameLengthAsTaxonomyDepthException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class FilterSimilaritySuperTest {


	Pos posObject = new Pos();

	FilterSimilaritySuperTest() throws IOException {
	}

	@Test
	void testFilterSimilarity() throws NotSameLengthAsTaxonomyDepthException, NotLeafException, NotInTaxonomyException {

		String sentence1 = "mehdi is a python with inevtiv it in France and paris";
		String sentence2 = "mehdi is a python with inevtiv it in France and paris";

		String taggedSentence1 = this.posObject.tagSentence(sentence1);
		String taggedSentence2 = this.posObject.tagSentence(sentence2);

		HashMap<String, ArrayList<String>> filterSentence1 = this.posObject.buildSentenceSuperTags(taggedSentence1);
		HashMap<String, ArrayList<String>> filterSentence2 = this.posObject.buildSentenceSuperTags(taggedSentence2);

		double score = this.posObject.posFiltredSimilarity(filterSentence1, filterSentence2);
		assertEquals(1, score);
	}

}
