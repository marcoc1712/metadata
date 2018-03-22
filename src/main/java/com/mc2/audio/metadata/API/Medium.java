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
public interface Medium {

	/**
	 * @return the index
	 */
	Integer getIndex();

	/**
	 * @return the mediumId
	 */
	String getMediumId();

	/**
	 * @return the number
	 */
	String getNumber();
	
	/**
     * @return the trackList
     */
	ArrayList<? extends Track> getTrackList();
	/**
	 * @return the discId offsets
	 */
	Integer[] getOffsetArray();

	/**
	 * @return the discId offsets
	 */
	ArrayList<Integer> getOffsets();

	/**
	 * @return the title
	 */
	String getTitle();

	/**
	 * @return the discId offsets
	 */
	String getToc();

	/**
	 * @return the type
	 */
	String getType();
	
	/** @return total length */
	Integer getTotalLength();
	
	/** @return total length in msec */
    Long getTotalLengthInMillis();
	
    /** @return total length string */
    String getTotalLengthString();
}
