package fr.jobslake.utils.exceptions;

public class NotLeafException extends Exception {

        public NotLeafException (String nodeName){
            super(nodeName + " not a leaf");
        }
    }

