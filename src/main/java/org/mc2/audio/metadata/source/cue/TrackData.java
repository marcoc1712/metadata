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
//import jwbroek.cuelib.TrackData;
//import jwbroek.cuelib.Index;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.MetadataSource;
import org.mc2.audio.metadata.source.tags.file.AudioFile;

/**
 *
 * @author marcoc1712
 */
public class TrackData extends jwbroek.cuelib.TrackData implements MetadataSource{

    private final TrackSection section;
    private int offset;
    private int length;
        
    TrackData(FileData fileData, int trackNumber, String dataType, int offset, int length) {
        super(fileData,trackNumber,dataType);
        this.section = new TrackSection((CueSheet)fileData.getParent(),this);
        this.offset = offset;
        this.length= length;
    }

    TrackData(FileData fileData) {
        super(fileData);
        this.section = new TrackSection((CueSheet)fileData.getParent(),this);
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
    public List<TrackIndex> getTrackIndexList() {
        
        List<TrackIndex> out = new ArrayList<>();
        for (jwbroek.cuelib.Index index: super.getIndices()){
            
            out.add((TrackIndex) index);
        }
        return out;
    }
    /**
     * add a trackindex to the track.
     * @param trackIndex
     */
    public void addTrackIndex(TrackIndex trackIndex){
        super.getIndices().add(trackIndex);
        setLength(getLength()+trackIndex.getLength());
    }
    
    @Override
    public ArrayList<Metadata> getMetadata(){
        
        ArrayList<Metadata> out = getTrackSection().getMetadata();
        out.addAll(getAddtionalMetadataFromFile());
        return out;
    }
    public Metadata getMedata(String genericKey){

        return this.getTrackSection().getMedata(genericKey);
    }
    
    public AudioFile getAudiofile(){
        
        if (section.getCuesheet().getFileDataList().size()!= 1){
            
            return ((FileData)getParent()).getAudiofile();
        }
        return null;
        
    }
    private ArrayList<Metadata> getAddtionalMetadataFromFile(){
        
    if (section.getCuesheet().getFileDataList().size()== 1){ return new ArrayList<>();}

    return ((FileData)getParent()).getAudiofile().getMetadata();
    
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
        return CueSheet.getMilliseconds(getOffset());
    }
    /** @return track length in msec */
    public Long getLengthInMillis(){
        return CueSheet.getMilliseconds(getLength());
    }
     /** @return track end in msec */
    public Long getEndInMillis(){
        return CueSheet.getMilliseconds(getEnd());
    }
    /** @return track offset string */
    public String getOffsetString(){

        return CueSheet.getTimeString(getOffsetInMillis());
    }
    /** @return track length string */
    public String getLengthString(){

        return CueSheet.getTimeString(getLengthInMillis());
    }
    /** @return track end string */
    public String getEndString(){

        return CueSheet.getTimeString(getEndInMillis());
    }

}
