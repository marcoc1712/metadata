/*
 * Cuelib library for manipulating cue sheets.
 * Copyright (C) 2007-2008 Jan-Willem van den Broek
 *               2017 Marco Curti
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
package com.mc2.audio.metadata.source.cue;

import jwbroek.cuelib.Index;
import jwbroek.cuelib.Position;
import com.mc2.util.miscellaneous.CalendarUtils;


/**
 *
 * @author marco
 */

public class TrackIndex extends Index{
    
    private TrackData trackData;
    private int length;
    
    TrackIndex(TrackData trackData, int indexNumber, Position position) {
        super(indexNumber,position);
        this.trackData= trackData;
        this.length= 0;
    }

     /**
     * @return the trackData
     */
    public TrackData getTrackData() {
        return trackData;
    }
    
    /**
     * @return the offset
     */
    public int getOffset() {
        return getPosition().getTotalFrames();
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }
    /** @return track offset in msec */
   public Long getOffsetInMillis(){
       return CalendarUtils.getMilliseconds(getOffset());
   }
   /** @return track length in msec */
   public Long getLengthInMillis(){
       return CalendarUtils.getMilliseconds(getLength());
   }

   /** @return track offset string */
   public String getOffsetString(){

       return CalendarUtils.getTimeString(getOffsetInMillis());
   }
   /** @return track length string */
   public String getLengthString(){

       return CalendarUtils.getTimeString(getLengthInMillis());
   }
   /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

}