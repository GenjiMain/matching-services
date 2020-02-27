package fr.jobslake.similarity;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class ngramsTest {


	Pos posObject = new Pos();

	ngramsTest() throws IOException {
	}


	@Test
	void testBuildSentenceTags() {

		String sentence = "mehdi is a data scientist";

		System.out.println(posObject.ngrams(2, sentence));

	}


}
