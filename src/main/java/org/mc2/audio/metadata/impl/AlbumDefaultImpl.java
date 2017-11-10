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
package org.mc2.audio.metadata.impl;

import java.io.File;
import java.util.ArrayList;
import org.mc2.audio.metadata.API.Album;
import org.mc2.audio.metadata.API.CoverArt;
import org.mc2.audio.metadata.API.Metadata;
import org.mc2.audio.metadata.API.StatusMessage;
import org.mc2.audio.metadata.API.Track;
import org.mc2.audio.metadata.source.cue.file.CueFile;
import org.mc2.audio.metadata.source.tags.file.AudioFile;

/**
 *
 * @author marco
 */
public class AlbumDefaultImpl implements Album {
    
    
    private final ArrayList<File> fileList;
    private final ArrayList<CueFile> cueFileList;
    private final ArrayList<AudioFile> audioFileList;  
    private final ArrayList<File> imageFileList;  
    private final ArrayList<Track> trackList;
    private final ArrayList<CoverArt> coverArtList;
    private final ArrayList<Metadata> metadataList;
    private final ArrayList<StatusMessage> messageList;
    
    private Integer totalLength = 0;
    ArrayList<Integer> discIdOffsets = new ArrayList<>();
    
    public AlbumDefaultImpl(ArrayList<CoverArt> coverArtList,
                 ArrayList<Metadata> metadataList, 
                 ArrayList<Track> trackList, 
                 ArrayList<File> fileList, 
                 ArrayList<CueFile> cueFileList, 
                 ArrayList<AudioFile> audioFileList, 
                 ArrayList<File> imageFileList,
                 ArrayList<StatusMessage> messageList) {
        
        this.coverArtList = coverArtList;  
        this.metadataList = metadataList;  
        this.fileList = fileList;
        this.cueFileList = cueFileList;
        this.audioFileList = audioFileList;
        this.imageFileList = imageFileList;
        this.messageList = messageList;
        
        this.trackList = initTrackList(trackList);
        
        
    }

    private ArrayList<Track> initTrackList(ArrayList<Track> trackList){
        
       
        discIdOffsets.add(0,0);
        
        Integer offset = 0;
        for (Track track : trackList){
            
            if ( track instanceof TrackDefaultImpl){
                ((TrackDefaultImpl)track).setOffset(offset);
                offset= offset+track.getLength();
                totalLength= totalLength+track.getLength();
                discIdOffsets.add(track.getOffset());
            }
           
            //System.out.println(track.getOffset()+" "+track.getLength()+" "+totalLength);
        }
        
        discIdOffsets.set(0, totalLength);
        return trackList;
    }
    
    /**
     * @return the totalLength
     */
    @Override
    public Integer getTotalLength() {
        return totalLength;
    }
    /**
     * @return the trackList
     */
    @Override
    public ArrayList<Track> getTrackList() {
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
    /**
     * @return the coverArtList
     */
    @Override
    public ArrayList<CoverArt> getcoverArtList() {
        return coverArtList;
    }
    /**
     * @return the metadataList
     */
    @Override
    public ArrayList<Metadata> getMetadataList() {
        return metadataList;
    }

    /**
     * @return the fileList
     */
    @Override
    public ArrayList<File> getFileList() {
        return fileList;
    }
    
    /**
     * @return the imageFileList
     */
    @Override
    public ArrayList<File> getImageFileList() {
        return imageFileList;
    }

    /**
     * @return the cueFileList
     */
    @Override
    public ArrayList<CueFile> getCueFileList() {
        return cueFileList;
    }

    /**
     * @return the audioFileList
     */
    @Override
    public ArrayList<AudioFile> getAudioFileList() {
        return audioFileList;
    }

    /**
     * @return the messageList
     */
    @Override
    public ArrayList<StatusMessage> getMessageList() {
        return messageList;
    }

}
