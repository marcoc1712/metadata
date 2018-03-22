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
package com.mc2.audio.metadata.impl;

import com.mc2.audio.metadata.API.Track;
import java.util.ArrayList;
import com.mc2.audio.metadata.API.Medium;
import com.mc2.util.miscellaneous.CalendarUtils;

/**
 *
 * @author marco
 */
public class MediumDefaultImpl implements Medium {

	private final String id;
	private final String type;
	private final String number;
	private final String title;
	
	private final ArrayList<TrackDefaultImpl> trackList;
	private final ArrayList<Integer> discIdOffsets;	
	
	private Integer index;
	private Integer offset;
	private Integer totalLength;

	MediumDefaultImpl(String id, String type, String number, String title) {
		this.id = id;
		this.type = type;
		this.number = number;
		this.title = title;
		
		this.trackList = new ArrayList<>();
		this.offset=0;
		this.totalLength=0;
		this.index=-1;
		
		this.discIdOffsets = new ArrayList<>();
		this.discIdOffsets.add(0,0);
		
	}
	/**
	 * @return the mediumId
	 */
	@Override
	public String getMediumId() {
		return id;
	}
	/**
	 * @return the index
	 */
	@Override
	public Integer getIndex() {
		return index;
	}
	
	/**
	 * @return the type
	 */
	@Override
	public String getType() {
		return type;
	}

	/**
	 * @return the number
	 */
	@Override
	public String getNumber() {
		return number;
	}

	/**
	 * @return the title
	 */
	@Override
	public String getTitle() {
		return title;
	}
	 /**
     * @return the trackList
     */
    @Override
    public ArrayList<? extends Track> getTrackList() {
        return trackList;
    }
	/**
     * @return the discId offsets
     */
	@Override
    public Integer[] getOffsetArray() {
        return (Integer[])discIdOffsets.toArray();
    }
	
    /**
     * @return the discId offsets
     */
	@Override
    public ArrayList<Integer> getOffsets() {
        return  discIdOffsets;
    }
	
    /**
     * @return the discId offsets
    */
	@Override
    public String getToc() {
        
        String offests="";
        for (Integer offset : discIdOffsets){
            offests=offests+" "+offset;
        }
        return  "1 "+ (discIdOffsets.size()-1) + offests;
    }
	
	void setIndex(int index) {
		this.index = index;
	}
	
	void addTrack(TrackDefaultImpl track) {

		//track.setOffset(offset);
		
		this.trackList.add(track);
		this.discIdOffsets.add(this.offset);
		
		this.offset= this.offset+track.getLength();
		this.totalLength= this.totalLength+track.getLength();

        this.discIdOffsets.set(0, this.totalLength);
	}

	/**
	 * @return the total Length
	 */
	@Override
	public Integer getTotalLength() {
		return totalLength;
	}
	/** @return total length in msec */
    @Override
    public Long getTotalLengthInMillis(){
        return CalendarUtils.getMilliseconds(getTotalLength());
    }
    /** @return total length string */
    @Override
    public String getTotalLengthString(){

        return CalendarUtils.getTimeString(getTotalLengthInMillis());
    }
}
