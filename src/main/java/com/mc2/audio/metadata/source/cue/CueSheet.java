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

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
//import jwbroek.cuelib.FileData;
import jwbroek.cuelib.LineOfInput;
import org.jaudiotagger.tag.FieldKey;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataSource;
import com.mc2.audio.metadata.API.RawKeyValuePair;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.impl.RawKeyValuePairDefaultImpl;
import com.mc2.audio.metadata.source.tags.file.AudioFile;
/**
 *
 * @author marcoc1712
 */
public class CueSheet extends jwbroek.cuelib.CueSheet implements ImputLinesSource, MetadataSource{
    
     private final static String WARNING_DATA_FILE_HAS_TRACK =  "Data File is per Album but has track defined.";
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
        
        ArrayList<Metadata> out =  getAlbumSection().getMetadata();
        
        if (getAddtionalMetadataFromFile() != null){
            out.addAll(getAddtionalMetadataFromFile());
        }
        return out;
    }
    
    private ArrayList<Metadata> getAddtionalMetadataFromFile(){

        if (getAudiofile() != null){
            return  getAudiofile().getMetadata();
        }
        return null;
    }
    
    public AudioFile getAudiofile(){
        
        if (getFileDataList().size()== 1){
            
            AudioFile audioFile = getFileDataList().get(0).getAudiofile();
            
            if (audioFile == null) return null;
            
            Metadata Tracknumber=audioFile.getMetadata(FieldKey.TRACK);
            
            if (Tracknumber == null || Tracknumber.isEmpty()){ return audioFile;}
            int trackNO = Integer.parseInt(Tracknumber.getValue());
            
            if ( trackNO == 0 ){ return audioFile;}
            
        }
        return null;
        
    }
            
    public Metadata getMedata(String genericKey){

        return this.getAlbumSection().getMedata(Section.ALBUM,genericKey);
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
    @Override
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
    
    protected void afterParsing(){
        
        adjustLength();
        checkAudiofiles();
        
    }
    private void checkAudiofiles(){
    
        if (getAudiofile() != null){
            
            Metadata Tracknumber =  getAudiofile().getMetadata(FieldKey.TRACK);
            int trackNO=0;
 
            try {
                trackNO = Integer.parseInt(Tracknumber.getValue());
            } catch (NumberFormatException ex){
            
            }

            if ( trackNO> 0 ){
             
                LineOfInput input = new LineOfInput(0,"", this);
                addWarning(input, WARNING_DATA_FILE_HAS_TRACK);
            }
        }
    }
    private void adjustLength() {
        
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