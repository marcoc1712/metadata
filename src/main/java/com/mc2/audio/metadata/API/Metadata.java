/*
 * Library for manipulating metadata from Audiofiles and cue sheets.
 *
 * Copyright (C) 2017 Marco Curti (marcoc1712 at gmail dot com).
 *
 * Based upon (and depends on):
 * 
 * - cueLib by Jan-Willem van den Broek
 * - jaudiotagger:audio tagging library Copyright (C) 2015 Paul Taylor
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package com.mc2.audio.metadata.API;

import com.mc2.audio.metadata.API.MetadataKey.METADATA_CATEGORY;
import com.mc2.audio.metadata.API.MetadataKey.METADATA_KEY;
import java.util.ArrayList;

/**
 *
 * @author marco
 */
public interface Metadata {
    
	public static final String  SEPARATOR = ";";
	
    public enum STATUS {
    
        VALID,
        DISCARDED,
        INVALID,
        DISCARDED_AND_INVALID,
        HAS_DISCARDED_ORIGINS,
        HAS_INVALID_ORIGINS,
        HAS_DISCARDED_AND_INVALID_ORIGINS,
        EMPTY
        
    };
	/**
     * @return the key
     */
    String getKey();
	
	/**
     * @return the value
     */
    String getValue();
    
	/**
     * True if the metadata carry no values (valid or discarded).
     * @return isEmpty.
     */
    Boolean isEmpty();
	
	 /**
     * @return the status.
     */
    STATUS getStatus();
	
	 /**
     * @return the valid value.
     */
    String getValidValue();

    /**
     * @return the valid values
     */
    ArrayList<String> getValidValues();
	
	/**
     * @return the discarded value.
     */
    String getDiscardedValue();

    /**
     * @return the discarded values
     */
    ArrayList<String> getDiscardedValues();

    /**
     * @return the invalid value.
     */
    String getInvalidValue();

    /**
     * @return the invalid values
     */
    ArrayList<String> getInvalidValues();

    /* END OF SimpleMetadata */ 

    /**
     * @return the origins
     */
    ArrayList<MetadataOrigin> getOrigins();

    /** Return the value, merging values accordingly with the input flags.
     * @param mergeDiscarded
     * @param mergeInvalid
     * @return the value.
     */
    String getValue(boolean mergeDiscarded, boolean mergeInvalid);
	
	 /**
     * @return the values
     */
	ArrayList<String> getValues();
	
	 /** Return the value, merging values accordingly with the input flags.
     * @param mergeDiscarded
     * @param mergeInvalid
     * @return the values.
     */
	ArrayList<String> getValues(boolean mergeDiscarded, boolean mergeInvalid);

	 /**
     * 'standard' name  for the metadata Key, at Album level.
	 * @return METADATA_KEY
     */
	METADATA_KEY getAlbumLevelMetadataKey();
	
	/**
     * 'standard' name  for the metadata Key, at Track level.
	 * @return METADATA_KEY
     */
	METADATA_KEY getTrackLevelMetadataKey();
	
	 /**
     * category in witch the metadata is listed at Album level.
	 * @return METADATA_CATEGORY
     */
	METADATA_CATEGORY getAlbumLevelCategory();
	/**
     * category in witch the metadata is listed at Track level.
     * @return METADATA_CATEGORY
	 */
	METADATA_CATEGORY getTrackLevelCategory();
    
}
