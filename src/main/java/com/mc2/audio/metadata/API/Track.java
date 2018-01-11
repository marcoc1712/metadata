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
 *
 * @author marco
 */
public interface Track {

    /**
     * @return the track End position refferred to the Album (not the file).
     */
    int getEnd();

    /**
     * @return the length
     */
    int getLength();

    /** @return file length in msec */
    Long getLengthInMillis();

    /** @return file length string */
    String getLengthString();

    /**
     * @return the metadataList
     */
    ArrayList<Metadata> getMetadataList();

    /**
     * @return the offset
     */
    int getOffset();

    /**
     * @return the trackNo
     */
    Integer getTrackNo();
    /**
     * @return the title
     */
    public String getTitle();
    /**
     * @return the artist
    */
    public String getArtist();
    /**
     * @return the sources of Raw Key Value Pairs, like tag or cue files lines.
     */
    public ArrayList<RawKeyValuePairSource> getRawKeyValuePairSources();
    
    /**
     * @return the message List
     */
    public ArrayList<StatusMessage> getMessageList();
    
    /**
     * @return the overall Track status
     */
    StatusMessage.Severity getStatus();
    
    /**
     * @return the url fo the track
     */
    public String getUrl();
    
    /**
     * @return the Album url
     */
    public String getAlbumUrl();
     /**
     * @return the Index position of the track in the Album (normally trackNo -1).
     */
    public Integer getIndex();
}
