package fr.jobslake.matchingservices.similarity;

import fr.jobslake.similarity.Pos;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;


class BuildSentenceTagsTest {


	//Pos posObject = new Pos();

	BuildSentenceTagsTest() throws IOException {
	}


	@Test
	void testBuildSentenceTags() throws IOException {
	
		Pos posObject = new Pos();

		String sentence = "mehdi is a data science with inevtiv it in France and paris";
		String taggedSentence = posObject.tagSentence(sentence);

		HashMap<String, ArrayList<String>> response = posObject.buildSentenceSuperTags(taggedSentence);


		// Test length sentence
		int sizeResponse = 0;
		for(String key:response.keySet()) {
			sizeResponse += response.get(key).size();
		}
		System.out.print(response);
		assertEquals(sizeResponse, sentence.split(" ").length);


//		String[] arrayResponse = response.toArray(new String[response.size()]);
//		String[] arrayResponses = responses.toArray(new String[responses.size()]);
//		assertArrayEquals(arrayResponse, arrayResponses);
	}


}
