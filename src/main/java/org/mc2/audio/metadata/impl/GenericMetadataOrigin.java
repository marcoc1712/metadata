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

package org.mc2.audio.metadata.impl;

import org.mc2.audio.metadata.MetadataOrigin;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marco
 */
public class GenericMetadataOrigin implements MetadataOrigin { 
    private final String source;
    private final String key;
    private ArrayList<String> values = new  ArrayList<>() ;
    private ArrayList<String> discarded = new  ArrayList<>() ;
    private ArrayList<String> invalid = new  ArrayList<>() ;

    public GenericMetadataOrigin(String source,String key, 
                                    List<String> values,  
                                    List<String> discarded,  
                                    List<String> invalid){
        this.source = source;
        this.key = key;
        this.values= (ArrayList)values;
        this.discarded = (ArrayList)discarded;
        this.invalid = (ArrayList)invalid;
    }
    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public String getOriginKey() {
        return this.key;
    }

    @Override
    public ArrayList<String> getValidatedValues() {
        return this.values;
    }

    @Override
    public ArrayList<String> getDiscardedValues() {
         return discarded;
    }

    @Override
    public ArrayList<String> getInvalidValues() {
         return invalid;
    }

}
