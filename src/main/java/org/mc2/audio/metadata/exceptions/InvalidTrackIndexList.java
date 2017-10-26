package org.mc2.audio.metadata.exceptions;


/**
 * The data FILE does not exists, is not readable or is invalid.
 */

public class InvalidTrackIndexList extends InvalidCueSheetException {
    
    public InvalidTrackIndexList() {
		super();
	}

	public InvalidTrackIndexList(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTrackIndexList(String message) {
		super(message);
	}

	public InvalidTrackIndexList(Throwable cause) {
		super(cause);
	}   
}
