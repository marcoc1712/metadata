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

import java.util.ArrayList;

/**
 * An  unique Metadata in a source.
 * It normally correspond to a single TAG field in an audio file or command 
 * in a cue sheet (the originKey), but if more than one with exactly the same 
 * meaning are found, then we could store more values.
 * 
 * @author marco
 */
public interface MetadataOrigin{
   
    /**
     * @return the source (normally a file pathname) of the metadata.
     */
    public String getSource();

    /**
     * @return the origin tag field or command key.
     */
    public String getOriginKey();
    
    /**
     * @return the origin validated values.
    */
    public ArrayList<String> getValidatedValues();
    
     /**
     * @return the origin discarded values.
    */
    public ArrayList<String> getDiscardedValues();
    /**
     * @return the origin discarded values.
    */
    public ArrayList<String> getInvalidValues();
}
