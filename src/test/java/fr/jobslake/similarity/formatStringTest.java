package fr.jobslake.similarity;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class formatStringTest {


	Pos posObject = new Pos();

	formatStringTest() throws IOException {
	}


	@Test
	void testBuildSentenceTags() {
		String sentence = "tom_NN __UNKNOWN__ kjklqs_UI";
		System.out.println(posObject.formatString(sentence));

	}


}
