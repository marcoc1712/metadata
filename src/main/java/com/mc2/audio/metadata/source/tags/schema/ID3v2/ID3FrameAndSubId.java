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

package com.mc2.audio.metadata.source.tags.schema.ID3v2;

/**
 *
 * @author marco
 */
 public class ID3FrameAndSubId {
        
    private final String genericKey;
    private final String frameId;
    private final String subId;

    public ID3FrameAndSubId(String genericKey, String frameId, String subId)
    {
        this.genericKey = genericKey;
        this.frameId = frameId;
        this.subId = subId;
    }
    public String getSubId()
    {
        return subId;
    }
    public String getGenericKey()
    {
        return genericKey;
    }

    public String getFrameId()
    {
        return frameId;
    }

    public String getName()
    {
        if (getSubId() != null && !getSubId().isEmpty()){
            return getFrameId()+":"+getSubId();
        } else{
            return getFrameId();
        }
    }
 }
