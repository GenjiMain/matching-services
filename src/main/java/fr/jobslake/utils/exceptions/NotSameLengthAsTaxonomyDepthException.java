package fr.jobslake.utils.exceptions;

public class NotSameLengthAsTaxonomyDepthException extends Exception{

    public NotSameLengthAsTaxonomyDepthException (int depth){
        super("the weight vector has to be of length : " +  depth);
    }
}
