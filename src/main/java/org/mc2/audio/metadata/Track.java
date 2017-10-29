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
package org.mc2.audio.metadata;

import java.util.ArrayList;
import org.mc2.audio.metadata.source.tags.file.AudioFile;

/**
 *
 * @author marco
 */
public class Track {

    
    private Integer trackNo; 
    private int offset;
    private int length;
    private final ArrayList<Metadata> metadataList;
    private final ArrayList<AudioFile> audioFileList; 
    
    public Track(Integer trackNo, ArrayList<Metadata> metadataList) {
        this.trackNo = trackNo;
        this.metadataList = metadataList;
        this.audioFileList =new ArrayList<>();
    }
    /**
     * @return the trackNo
     */
    public Integer getTrackNo() {
        return trackNo;
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
    /**
     * @return the metadataList
     */
    public ArrayList<Metadata> getMetadataList() {
        return metadataList;
    }

    /**
     * @return the audioFileList
     */
    public ArrayList<AudioFile> getAudioFileList() {
        return audioFileList;
    }
    /**
     * add a File to the audioFileList
     */
    public void addAudioFile(AudioFile audioFile) {
        
        if (!audioFileList.contains(audioFile)){
            audioFileList.add(audioFile);
        }   
    }
    
}
