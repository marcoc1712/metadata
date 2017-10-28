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
package org.mc2.audio.metadata.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.tag.FieldKey;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.exceptions.InvalidAudioFileException;
import org.mc2.audio.metadata.exceptions.InvalidAudioFileFormatException;
import org.mc2.audio.metadata.exceptions.InvalidCueSheetException;
import org.mc2.audio.metadata.source.cue.FileData;
import org.mc2.audio.metadata.source.cue.TrackData;
import org.mc2.audio.metadata.source.cue.file.CueFile;
import org.mc2.audio.metadata.source.tags.file.AudioFile;
import org.mc2.audio.metadata.structures.Album;
import org.mc2.audio.metadata.structures.Track;


/**
 *
 * @author marco
 */
public class AlbumParser {
    
     public static Album parse(String directory) throws IOException, InvalidCueSheetException, InvalidAudioFileException, InvalidAudioFileFormatException {
         return parse(new File(directory));
    }
    public static Album parse(File directory) throws IOException, InvalidCueSheetException, InvalidAudioFileException, InvalidAudioFileFormatException{
        
       ArrayList<File> directoryfileList = new ArrayList<>(Arrays.asList(directory.listFiles(new GenericFileFilter(false))));
       ArrayList<File> fileList = new ArrayList<>(Arrays.asList(directory.listFiles(new AudioFileFilter(false))));
       ArrayList<File> imagefileList = new ArrayList<>(Arrays.asList(directory.listFiles(new ImageFileFilter())));
       ArrayList<CueFile> cueFileList= new ArrayList<>();
       ArrayList<AudioFile> audioFileList= new ArrayList<>();
       
       ArrayList<Metadata> atAlbumLevel= new ArrayList<>();
       HashMap<Integer,Track> trackMap = new HashMap<>();
              
       if (containsCueFile(fileList)){

            cueFileList = getCueFileList(fileList);
            
            for (CueFile cueFile :cueFileList){
                
                atAlbumLevel.addAll(cueFile.getCuesheet().getMetadata());
                
                for (FileData fileData : cueFile.getCuesheet().getFileDataList()){
                    fileList.remove(fileData.getAudiofile().getFile());
                    
                    for (TrackData trackData : fileData.getTrackDataList() ){
                        
                        Track track = trackMap.get(trackData.getNumber());
                       
                        if ( track != null){
                        
                            track.getMetadataList().addAll(trackData.getMetadata());
                        } else {
                            track = new Track(trackData.getNumber(), trackData.getMetadata());
                            trackMap.put(trackData.getNumber(), track);
                        }
                        
                        if (track.getLength() !=0 && trackData.getLength()!= track.getLength() ||
                            track.getOffset() !=0 && trackData.getOffset()!= track.getOffset()){
                
                            track.setLength(0);
                            track.setOffset(0);
                        
                        } else
                            track.setLength(trackData.getLength());
                            track.setOffset(trackData.getOffset());
                        }
                }
                fileList.remove(cueFile.getFile());
            }
           
       } 
       for (File file : fileList){
       
           AudioFile audiofile = AudioFile.get(file);

           int trackNo = Integer.parseInt(audiofile.getMetadata(FieldKey.TRACK).getValue());
           
           if (trackNo > 0) {
           
                Track track = trackMap.get(trackNo);
                
                        
                if ( track != null){
                   
                    track.getMetadataList().addAll(audiofile.getMetadata());
                
                } else {
                    
                    track = new Track(trackNo, audiofile.getMetadata());
                    trackMap.put(trackNo, track);
                }
                
                if (track.getLength() !=0 && audiofile.getAudioHeader().getTrackLength()!= track.getLength()){
                
                    track.setLength(0);
                
                } else{
                    
                    track.setLength( audiofile.getAudioHeader().getTrackLength());
                    //track.setOffset(0);
                }    
                
                track.addAudioFile(audiofile);
 
            } else {
           
               atAlbumLevel.addAll(audiofile.getMetadata());
               audioFileList.add(audiofile);
           }
       }
       ArrayList<Track> tracklist= new ArrayList<>(trackMap.values());
     
       Album out = new Album(atAlbumLevel, tracklist, directoryfileList, cueFileList, audioFileList, imagefileList);
       
       return out;
    }
    
    private static ArrayList<CueFile> getCueFileList(ArrayList<File> fileList) throws IOException, InvalidCueSheetException{
        
        ArrayList<CueFile> out= new ArrayList<>();
        for (File file : fileList){
            if (CueFile.isCueFile(file)){
                
                out.add(new CueFile(file));
            }
            
        }
        return out;
    
    }
    private static boolean containsCueFile(ArrayList<File> fileList) throws IOException{
        
        for (File file : fileList){
            String ext =  FilenameUtils.getExtension(file.getCanonicalPath());
            if (CueFile.isCueFile(file)){
                return true;
            }
            
        }
        return false;
    }
   
}
