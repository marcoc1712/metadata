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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jwbroek.cuelib.FileData;
import jwbroek.cuelib.LineOfInput;
import jwbroek.cuelib.TrackData;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.mc2.audio.metadata.exceptions.InvalidDataFileException;
import static org.mc2.audio.metadata.source.cue.CueSheetCommandParser.addWarning;

/**
 *
 * @author marcoc1712
 */
public class Mc2FileData extends FileData {

    private final static String WARNING_DATA_FILE_DOES_NOT_EXISTS           = 
        "Data file does not exists.";
    private final static String WARNING_CAN_NOT_READ_DATA_FILE              = 
        "Can't read Data File.";
    private final static String WARNING_PROBLEMS_READING_DATA_FILE_HEADER   = 
        "Problems reading Data file Haader: ";
     
    private File datafile; 
    private int lenght;
    private int offset;

    public Mc2FileData(Mc2CueSheet cuesheet) {
        super(cuesheet);
    }
    
    public Mc2FileData(LineOfInput input, String file, String fileType) {
        super((Mc2CueSheet)input.getAssociatedSheet(),file, fileType);
        
        datafile = new File(this.getFile());
        if (!datafile.exists()){
            
            File souceFile = new File(((Mc2CueSheet)input.getAssociatedSheet()).getSourceId());
        
            if (souceFile.exists()){
                souceFile.getParentFile();
                datafile = new File(souceFile.getParentFile(), this.getFile()); 
            }
        }
        
        if (!datafile.exists()){
            //throw new InvalidDataFileException("Invalid dataFile");
            addWarning(input, WARNING_DATA_FILE_DOES_NOT_EXISTS);

        } else if (!datafile.canRead()){
            
            //throw new InvalidDataFileException("Invalid dataFile");
            addWarning(input, WARNING_CAN_NOT_READ_DATA_FILE);
        
        } else{
        
            try {
                
                lenght = calcFileLength(datafile);
            
            } catch (InvalidDataFileException ex){
                addWarning(input, WARNING_PROBLEMS_READING_DATA_FILE_HEADER+
                                  ex.getMessage());
            }
            
        }
    }
    /**
     * @return the lenght
     */
    public int getLenght() {
        return lenght;
    }
        /**
     * @param offset the offset to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
        /**
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }
    
    /** @return file length in msec */
    public Long getLengthInMillis(){
        return Mc2CueSheet.getMilliseconds(getLenght());
    }
    /** @return file length string */
    public String getLengthString(){

        return Mc2CueSheet.getTimeString(getLengthInMillis());
    }
    /** @return file offset in msec */
    public Long getOffsetInMillis(){
        return Mc2CueSheet.getMilliseconds(getOffset());
    }
    /** @return file offset string */
    public String getOffsetString(){

        return Mc2CueSheet.getTimeString(getOffsetInMillis());
    }
    public List<Mc2TrackData> getTrackDataList(){
    
        List<Mc2TrackData> out = new ArrayList<>();
        for (TrackData trackData: super.getTrackData()){
            
            out.add((Mc2TrackData) trackData);
        }
        return out;
    
    }
    /**
    * @return the filelenght
    */
    private int calcFileLength(File datafile) throws InvalidDataFileException {

       try {
           AudioFile audiofile =  AudioFileIO.read(datafile);
           int seconds = audiofile.getAudioHeader().getTrackLength();
           //System.out.println("- seconds: "+seconds);
           return  seconds*75;

       } catch (CannotReadException | IOException | ReadOnlyFileException |
                InvalidAudioFrameException| TagException ex) {

           throw new InvalidDataFileException(ex);
       }
    }

}
