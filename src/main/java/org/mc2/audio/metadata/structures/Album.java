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
package org.mc2.audio.metadata.structures;

import java.io.File;
import java.util.ArrayList;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.cue.file.CueFile;
import org.mc2.audio.metadata.source.tags.file.AudioFile;

/**
 *
 * @author marco
 */
public class Album {
    
    
    private final ArrayList<File> fileList;
    private final ArrayList<CueFile> cueFileList;
    private final ArrayList<AudioFile> audioFileList;  
    private final ArrayList<File> imageFileList;  
    private final ArrayList<Track> trackList;
    private final ArrayList<Metadata> metadataList;
    
    public Album(ArrayList<Metadata> metadataList, ArrayList<Track> trackList, 
                ArrayList<File> fileList, ArrayList<CueFile> cueFileList, 
                ArrayList<AudioFile> audioFileList, ArrayList<File> imageFileList) {
        
        this.metadataList = metadataList;
        this.trackList = trackList;
        this.fileList = fileList;
        this.cueFileList = cueFileList;
        this.audioFileList = audioFileList;
        this.imageFileList = imageFileList;
       
    }

    /**
     * @return the trackList
     */
    public ArrayList<Track> getTrackList() {
        return trackList;
    }

    /**
     * @return the metadataList
     */
    public ArrayList<Metadata> getMetadataList() {
        return metadataList;
    }

    /**
     * @return the fileList
     */
    public ArrayList<File> getFileList() {
        return fileList;
    }
    
    /**
     * @return the imageFileList
     */
    public ArrayList<File> getImageFileList() {
        return imageFileList;
    }

    /**
     * @return the cueFileList
     */
    public ArrayList<CueFile> getCueFileList() {
        return cueFileList;
    }

    /**
     * @return the audioFileList
     */
    public ArrayList<AudioFile> getAudioFileList() {
        return audioFileList;
    }

}
