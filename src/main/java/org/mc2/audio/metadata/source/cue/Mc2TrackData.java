/*
 * Cuelib library for manipulating cue sheets.
 * Copyright (C) 2007-2008 Jan-Willem van den Broek
 *               2017 Marco Curti (marcoc1712 at gmail dot com)
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

package org.mc2.audio.metadata.source.cue;

import java.util.ArrayList;
import java.util.List;
import jwbroek.cuelib.TrackData;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.MetadataSource;

/**
 *
 * @author marcoc1712
 */
public class Mc2TrackData extends TrackData implements MetadataSource{

    private final TrackSection section;
    private int offset;
    private int length;
    
    private List<Mc2TrackIndex> trackIndexList= new ArrayList<>();
    
    Mc2TrackData(Mc2FileData fileData, int trackNumber, String dataType, int offset, int length) {
        super(fileData,trackNumber,dataType);
        this.section = new TrackSection((Mc2CueSheet)fileData.getParent(),this);
        this.offset = offset;
        this.length= length;
    }

    Mc2TrackData(Mc2FileData fileData) {
        super(fileData);
        this.section = new TrackSection((Mc2CueSheet)fileData.getParent(),this);
        this.offset = 0;
        this.length= 0;
    }
    /**
     * @return the section
     */
    public TrackSection getTrackSection() {
        return section;
    }

    @Override
    public int getNumber() {
        return super.getNumber(); //To change body of generated methods, choose Tools | Templates.
    }
    /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }
    
    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }
    /**
     * @return the track End position refferred to the Album (not the file).
     */
    public int getEnd() {
        return offset+length;
    }
    public ArrayList<Command> getCommandList(){

            return this.getTrackSection().getCommandList();
    }
    
    /**
     * @return the trackIndexList
     */
    public List<Mc2TrackIndex> getTrackIndexList() {
        return trackIndexList;
    }
    /**
     * add a trackindex to the track.
     * @param trackIndex
     */
    public void addTrackIndex(Mc2TrackIndex trackIndex){
        this.getTrackIndexList().add(trackIndex);
        setLength(getLength()+trackIndex.getLength());
    }
    
    @Override
    public ArrayList<Metadata> getMetadata(){

            return getTrackSection().getMetadata();
    }
    public Metadata getMedata(String genericKey){

        return this.getTrackSection().getMedata(genericKey);
    }
   /**
     * @return the source
     */
    @Override
    public String getSourceId() {
        return getTrackSection().getSourceId();
    }
    
    /** @return track offset in msec */
    public Long getOffsetInMillis(){
        return Mc2CueSheet.getMilliseconds(getOffset());
    }
    /** @return track length in msec */
    public Long getLengthInMillis(){
        return Mc2CueSheet.getMilliseconds(getLength());
    }
     /** @return track end in msec */
    public Long getEndInMillis(){
        return Mc2CueSheet.getMilliseconds(getEnd());
    }
    /** @return track offset string */
    public String getOffsetString(){

        return Mc2CueSheet.getTimeString(getOffsetInMillis());
    }
    /** @return track length string */
    public String getLengthString(){

        return Mc2CueSheet.getTimeString(getLengthInMillis());
    }
    /** @return track end string */
    public String getEndString(){

        return Mc2CueSheet.getTimeString(getEndInMillis());
    }
}
