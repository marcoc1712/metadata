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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
//import jwbroek.cuelib.FileData;
import jwbroek.cuelib.LineOfInput;
//import jwbroek.cuelib.TrackData;

import static com.mc2.audio.metadata.source.cue.CueSheetCommandParser.addWarning;
import com.mc2.audio.metadata.source.tags.file.AudioFile;
import com.mc2.util.miscellaneous.CalendarUtils;

/**
 *
 * @author marcoc1712
 */
public class FileData extends jwbroek.cuelib.FileData {

    private final static String WARNING_DATA_FILE_DOES_NOT_EXISTS           = 
        "Data file does not exists.";
    private final static String WARNING_CAN_NOT_READ_DATA_FILE              = 
        "Can't read Data File.";
    private final static String WARNING_PROBLEMS_READING_DATA_FILE_HEADER   = 
        "Problems reading Data file Haader: ";
     
    private File datafile; 
    private AudioFile audiofile;
    private int length;
    private int offset;

    public FileData(CueSheet cuesheet) {
        super(cuesheet);
    }
    
    public FileData(LineOfInput input, String file, String fileType) {
        super((CueSheet)input.getAssociatedSheet(),file, fileType);
        
        datafile = new File(this.getFile());
        if (!datafile.exists()){
            
            File souceFile = new File(((CueSheet)input.getAssociatedSheet()).getSourceId());
        
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
                audiofile =  AudioFile.get(datafile);
                int seconds = audiofile.getAudioHeader().getTrackLength();

                length=  seconds*75;

            } catch (Exception  ex) {
                addWarning(input, WARNING_PROBLEMS_READING_DATA_FILE_HEADER+
                                  ex.getMessage());
            }
        }
    }
    /**
     * @return the length
     */
    public int getLength() {
        return length;
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
        return CalendarUtils.getMilliseconds(getLength());
    }
    /** @return file length string */
    public String getLengthString(){

        return CalendarUtils.getTimeString(getLengthInMillis());
    }
    /** @return file offset in msec */
    public Long getOffsetInMillis(){
        return CalendarUtils.getMilliseconds(getOffset());
    }
    /** @return file offset string */
    public String getOffsetString(){

        return CalendarUtils.getTimeString(getOffsetInMillis());
    }
    public List<TrackData> getTrackDataList(){
    
        List<TrackData> out = new ArrayList<>();
        for (jwbroek.cuelib.TrackData trackData: super.getTrackData()){
            
            out.add((TrackData) trackData);
        }
        return out;
    
    }
    /**
     * @return the audiofile
     */
    public AudioFile getAudiofile() {
        return audiofile;
    }
}
