package org.mc2.audio.metadata.exceptions;


/**
 * The data FILE does not exists, is not readable or is invalid.
 */

public class InvalidTrackList extends InvalidCueSheetException {
    
    public InvalidTrackList() {
		super();
	}

	public InvalidTrackList(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidTrackList(String message) {
		super(message);
	}

	public InvalidTrackList(Throwable cause) {
		super(cause);
	}   
}
