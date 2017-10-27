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


package org.mc2.audio.metadata.source.cue;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
//import jwbroek.cuelib.FileData;
import jwbroek.cuelib.LineOfInput;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.MetadataSource;
import org.mc2.util.miscellaneous.CalendarUtils;
/**
 *
 * @author marcoc1712
 */
public class CueSheet extends jwbroek.cuelib.CueSheet implements MetadataSource{
    
    /**
    * LineOfInput that compose this CueSheet.
    */
    private List<LineOfInput> lines = new ArrayList<>();
  
    private Charset encoding = StandardCharsets.ISO_8859_1;
    private final AlbumSection section;
    
    public CueSheet(){
        super();
        this.section = new AlbumSection(this);
    }
    public List<FileData> getFileDataList(){
    
        List<FileData> out = new ArrayList<>();
        for (jwbroek.cuelib.FileData fileData: super.getFileData()){
            
            out.add((FileData) fileData);
        }
        return out;
    }
    /**
     * @return the section
     */
    public AlbumSection getAlbumSection() {
        return section;
    }
    
    public ArrayList<Command> getCommands(){

            return getAlbumSection().getCommandList();
    }
    @Override
    public ArrayList<Metadata> getMetadata(){

            return getAlbumSection().getMetadata();
    }
    public Metadata getMedata(String genericKey){

        return this.getAlbumSection().getMedata(genericKey);
    }
   /**
     * @return the source
     */
    @Override
    public String getSourceId() {
        return getAlbumSection().getSourceId();
    }
    /**
     * @param source the source to set
     */
    public void setSourceId(String source) {
        this.getAlbumSection().setSourceId(source);
    }

    /**
     * @return the encoding
     */
    public Charset getEncoding() {
        return encoding;
    }

    /**
     * @param encoding the encoding to set
     */
    public void setEncoding(Charset encoding) {
        this.encoding = encoding;
    }

    /**
     * Get the list of lines that compose the cuesheet.
     * @return the lines
     */
    public List<LineOfInput> getLines() {
        return lines;
    }

    /**
     * Add a new line to the list of lines that compose the cuesheet.
     * @param line the line to add
     */
    public void addLine( LineOfInput  line ) {
            this.lines.add(line);    
    }
    
    /** Convert milliseconds into string (MM:SS:CC)
     * @param millis 
     * @return string 
    */
    protected static String getTimeString(Long millis){

        return CalendarUtils.calcDurationString(millis);
    }
    
    /** Convert sectors into msec.
     * @param sectors 
     * @return Long 
    */
    protected static Long getMilliseconds(int sectors){
        
        return new Long(sectors*1000/75);
    }

    protected void adjustLength() {
        
        int offset = 0;
        
        for (FileData fileData : this.getFileDataList()){
            
            fileData.setOffset(offset);
            
            TrackIndex previousIndex = null;
            
            for (TrackData track : fileData.getTrackDataList()) {
                
                int trackStart = fileData.getOffset()+track.getTrackIndexList().get(0).getPosition().getTotalFrames();
                track.setOffset(trackStart);
                
                for (TrackIndex index : track.getTrackIndexList()) {
                    
                    int IndexOffset = index.getPosition().getTotalFrames();
                    
                    int previousIndexLength=0;
                    
                    if (previousIndex != null){
                        previousIndexLength = IndexOffset-previousIndex.getOffset();
                        previousIndex.setLength(previousIndexLength);
                        
                        addIndexLengthToTrack(previousIndex, fileData.getTrackDataList());
                    }
                    previousIndex = index;
                }
               
            }
            
            if (previousIndex != null){
                int len = fileData.getLength()-previousIndex.getOffset();
                previousIndex.setLength(len);
                addIndexLengthToTrack(previousIndex, fileData.getTrackDataList());
            }
            
            offset=offset+fileData.getLength();   
        }
    }
    private void addIndexLengthToTrack(TrackIndex index,List<TrackData> trackList){
        
        int tracknum= index.getTrackData().getNumber();
        
        for (TrackData track : trackList) {
            
            if (track.getNumber()==tracknum) {    
                track.setLength(track.getLength()+index.getLength());
            }
        }
    }
}