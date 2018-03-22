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
     * @return the playback url of the track
     */
    String getUrl();
	
	/**
     * @return the playback playlist url 
     */
	String getPlayListUrl();
	
	/**
     * @return the playback Playlist index position of the track in the playlist,
	 * strating from 0.
     */
    Integer getPlayListIndex();
	
	/**
     * @return the absolute Index position of the track in the album, starting from 0.
     */
    public Integer getIndex();
	
	/**
     * @return the trackId (normally discNo.trackNo)
     */
    String getTrackId();
	
	/**
     * @return the title
     */
    public String getTitle();
    /**
     * @return the artist
    */
    public String getArtist();

	/**
     * @return the metadataList
     */
    ArrayList<Metadata> getMetadataList();

	/**
     * @return the sources of Raw Key Value Pairs, like tag or cue files commands.
     */
    public ArrayList<RawKeyValuePairSource> getRawKeyValuePairSources();
    
    /**
     * @return the track length in sectors
     */
    int getLength();

    /** 
	 * @return file length in msec 
	 */
    Long getLengthInMillis();

    /** 
	 * @return file length string
	 */
    String getLengthString();

	/**
     * @return the overall Track status
    */
    StatusMessage.Severity getStatus();
	
    /**
     * @return the status message List
    */
    public ArrayList<StatusMessage> getMessageList();

}
