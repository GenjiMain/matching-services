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
	void testFilterSimilarity(){

		String sentence = "tom is date scientist who uses python";
		String sentence2 = "brad is a java developper";

		String cleanSentence1 = this.posObject.cleanSentence(sentence);
		String cleanSentence2 = this.posObject.cleanSentence(sentence2);

		double score1 = this.posObject.ScoreSimilarity(cleanSentence1, cleanSentence1);
		double score2 = this.posObject.ScoreSimilarity(cleanSentence1, cleanSentence2);

		assertEquals(1, score1);
		assertEquals(0.5435551630506813, score2);
	}

}
