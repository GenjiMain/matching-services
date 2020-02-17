package fr.jobslake.matchingservices.similarity;

import fr.jobslake.similarity.Pos;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class GetSimilarityTest {


	Pos posObject = new Pos();

	GetSimilarityTest() throws IOException {
	}

	@Test
	void testcollection() {
		String word1 = "day";
		String word2 = "night";

		double expected = 0.8746729493141174;

		assertEquals(1, this.posObject.getSimilarity(word1, word1));
		assertEquals(expected, this.posObject.getSimilarity(word1, word2));
	}

}
