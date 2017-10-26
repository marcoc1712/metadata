package org.mc2.audio.metadata.exceptions;

import org.mc2.util.exceptions.MC2Exception;

/**
 * All Metadata specific exceptions extend this class.
 */

public class MetadataException extends MC2Exception {
    
    public MetadataException() {
		super();
	}

	public MetadataException(String message, Throwable cause) {
		super(message, cause);
	}

	public MetadataException(String message) {
		super(message);
	}

	public MetadataException(Throwable cause) {
		super(cause);
	}

}
