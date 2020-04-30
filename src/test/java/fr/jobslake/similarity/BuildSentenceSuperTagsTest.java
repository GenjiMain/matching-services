package fr.jobslake.similarity;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class BuildSentenceSuperTagsTest {


	Pos posObject = new Pos();

	BuildSentenceSuperTagsTest() throws IOException {
	}


	@Test
	void testBuildSentenceTags() {

		String sentence = "we are looking for machine learning engineer";
		String sentence2 = "mehdi uses python and java";

		String taggedSentence = this.posObject.cleanSentence(sentence);
		String taggedSentence2 = this.posObject.cleanSentence(sentence2);

		HashMap<String, ArrayList<String>> response = this.posObject.buildSentenceSuperTags(taggedSentence);
		HashMap<String, ArrayList<String>> response2 = this.posObject.buildSentenceSuperTags(taggedSentence2);


		// Test length sentence

		int sizeResponse = 0;
		for(String key:response.keySet()) {
			sizeResponse += response.get(key).size();
		}


		System.out.println(response);
		//assertEquals(sizeResponse, sentence.split(" ").length);
		System.out.println(response2);

//		String[] arrayResponse = response.toArray(new String[response.size()]);
//		String[] arrayResponses = responses.toArray(new String[responses.size()]);
//		assertArrayEquals(arrayResponse, arrayResponses);
	}


}
