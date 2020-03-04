package fr.jobslake.similarity;

import fr.jobslake.similarity.Pos;
import fr.jobslake.utils.exceptions.NotInTaxonomyException;
import fr.jobslake.utils.exceptions.NotLeafException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class BuildSentenceTagsTest {


	Pos posObject = new Pos();

	BuildSentenceTagsTest() throws IOException {
	}


	@Test
	void testBuildSentenceTags() throws NotLeafException, NotInTaxonomyException {

		//String sentence = "mehdi is a data scientist with inevtiv it in France and paris";
		String sentence = "we are looking for machine learning engineer";
		String sentence2 = "mehdi uses python and java";

		String taggedSentence = this.posObject.tagSentence(sentence);
		String taggedSentence2 = this.posObject.tagSentence(sentence2);

		HashMap<String, ArrayList<String>> response = this.posObject.buildSentenceTags(taggedSentence);
		HashMap<String, ArrayList<String>> response2 = this.posObject.buildSentenceTags(taggedSentence2);


		// Test length sentence
		int sizeResponse = 0;
		for(String key:response.keySet()) {
			sizeResponse += response.get(key).size();
		}
		System.out.println(response);
		assertEquals(sizeResponse, sentence.split(" ").length);
		System.out.println(response2);

//		String[] arrayResponse = response.toArray(new String[response.size()]);
//		String[] arrayResponses = responses.toArray(new String[responses.size()]);
//		assertArrayEquals(arrayResponse, arrayResponses);
	}


}
