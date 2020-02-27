package fr.jobslake.similarity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
class IsSkillTest {


	Pos posObject = new Pos();

	IsSkillTest() throws IOException {
	}


	@Test
	void testBuildSentenceTags() {

		assertEquals(false, posObject.isSkill("c"));
		assertEquals(true, posObject.isSkill("python"));
		assertEquals(true, posObject.isSkill("data science"));

	}


}
