package org.mc2.audio.metadata.exceptions;


/**
 * The file is not a CUE Sheet or is invalid.
 */

public class InvalidCueSheetException extends MetadataException {
    
    public InvalidCueSheetException() {
		super();
	}

	public InvalidCueSheetException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidCueSheetException(String message) {
		super(message);
	}

	public InvalidCueSheetException(Throwable cause) {
		super(cause);
	}   
}
