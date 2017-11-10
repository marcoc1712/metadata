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
package org.mc2.audio.metadata.API;

import java.util.ArrayList;

/**
 *
 * @author marco
 */
public interface Metadata {
    
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

    /**
     * @return the key
     */
    String getKey();

    /**
     * @return the origins
     */
    ArrayList<MetadataOrigin> getOrigins();

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
     * @return the value
     */
    String getValue();

    /** Return the value, merging values accordingly with the input flags.
     * @param mergeDiscarded
     * @param mergeInvalid
     * @return the value.
     */
    String getValue(boolean mergeDiscarded, boolean mergeInvalid);

    /**
     * True if the metadata carry no values (valid or discarded).
     * @return isEmpty.
     */
    boolean isEmpty();
    
}
