package fr.jobslake.matchingservices.similarity;

import fr.jobslake.similarity.Pos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CleanSentenceTests {


	Pos posObject = new Pos();

	CleanSentenceTests() throws IOException {
	}

	@Test
	void testCleanSentence() {
		String sentence = "hjdg$h&jk8^i0ssh6";
		String response = "hjdghjk8i0ssh6";

		assertEquals(response, this.posObject.cleanSentence(sentence));
	}

}
