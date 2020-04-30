package fr.jobslake.similarity;

import fr.jobslake.utils.exceptions.NotInTaxonomyException;
import fr.jobslake.utils.exceptions.NotLeafException;
import fr.jobslake.utils.exceptions.NotSameLengthAsTaxonomyDepthException;

import java.util.ArrayList;
import java.util.HashMap;

public interface Similarity {

    String cleanSentence(String sentence);
    double ScoreSimilarity(String sentence1, String sentence2);

}
