package org.mc2.audio.metadata.exceptions;


/**
 * The data FILE does not exists, is not readable or is invalid.
 */

public class InvalidDataFileException extends InvalidCueSheetException {
    
    public InvalidDataFileException() {
		super();
	}

	public InvalidDataFileException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidDataFileException(String message) {
		super(message);
	}

	public InvalidDataFileException(Throwable cause) {
		super(cause);
	}   
}
