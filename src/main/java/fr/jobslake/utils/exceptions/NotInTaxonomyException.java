package fr.jobslake.utils.exceptions;

public class NotInTaxonomyException extends Exception {
    public NotInTaxonomyException (String nodeName){
       super(nodeName + " not in the taxonomy");
    }
}
