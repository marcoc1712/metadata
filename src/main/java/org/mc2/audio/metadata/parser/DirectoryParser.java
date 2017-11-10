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
import jwbroek.cuelib.Message;

import org.apache.commons.io.FilenameUtils;
import org.jaudiotagger.tag.FieldKey;
import org.mc2.audio.metadata.impl.MetadataDefaultImpl;
import org.mc2.audio.metadata.API.exceptions.InvalidAudioFileException;
import org.mc2.audio.metadata.API.exceptions.InvalidAudioFileFormatException;
import org.mc2.audio.metadata.API.exceptions.InvalidCueSheetException;
import org.mc2.audio.metadata.source.cue.FileData;
import org.mc2.audio.metadata.source.cue.TrackData;
import org.mc2.audio.metadata.source.cue.file.CueFile;
import org.mc2.audio.metadata.source.tags.file.AudioFile;
import org.mc2.audio.metadata.impl.AlbumDefaultImpl;
import org.mc2.audio.metadata.API.CoverArt;
import org.mc2.audio.metadata.API.Metadata;
import static org.mc2.audio.metadata.API.MetadataKeys.getAlbumLevelMetadataAlias;
import static org.mc2.audio.metadata.API.MetadataKeys.getTrackLevelMetadataAlias;
import org.mc2.audio.metadata.API.StatusMessage;
import org.mc2.audio.metadata.API.Track;
import org.mc2.audio.metadata.impl.GenericStatusMessage;
import org.mc2.audio.metadata.impl.GenericStatusMessage.Severity;
import org.mc2.audio.metadata.impl.TrackDefaultImpl;
import org.mc2.audio.metadata.source.coverart.FileCoverArt;
import org.mc2.audio.metadata.source.cue.Section;
import static org.mc2.audio.metadata.source.cue.Section.ALBUM;

/**
 *
 * @author marco
 */
public class DirectoryParser {
    

    public static AlbumDefaultImpl parse(String directory) throws IOException, InvalidAudioFileException, InvalidAudioFileFormatException {
         return parse(new File(directory));
    }
    public static AlbumDefaultImpl parse(File directory) throws IOException,  InvalidAudioFileException, InvalidAudioFileFormatException{
        
       ArrayList<File> directoryfileList = new ArrayList<>(Arrays.asList(directory.listFiles(new GenericFileFilter(false))));
       ArrayList<File> fileList = new ArrayList<>(Arrays.asList(directory.listFiles(new AudioFileFilter(false))));
       ArrayList<File> imagefileList = new ArrayList<>(Arrays.asList(directory.listFiles(new ImageFileFilter())));
       
       ArrayList<CueFile> cueFileList= new ArrayList<>();
       ArrayList<AudioFile> audioFileList= new ArrayList<>();
       ArrayList<StatusMessage> statusMessageList= new ArrayList<>();
       
       ArrayList<Metadata> atAlbumLevel= new ArrayList<>();
       ArrayList<CoverArt> coverArtList= new ArrayList<>();
       HashMap<Integer,TrackDefaultImpl> trackMap = new HashMap<>();
              
       if (containsCueFile(fileList)){

            cueFileList = getCueFileList(fileList);
            
            for (CueFile cueFile :cueFileList){
                
                //atAlbumLevel.addAll(cueFile.getCuesheet().getMetadata());
                merge(Section.ALBUM, atAlbumLevel, cueFile.getCuesheet().getMetadata());

                for (Message message : cueFile.getCuesheet().getMessages()){

                    statusMessageList.add (new GenericStatusMessage(
                            Severity.WARNING, message.getMessage()+" - "+message.getInput()));
                }
                     
                for (FileData fileData : cueFile.getCuesheet().getFileDataList()){
                    
                    if (fileData.getAudiofile()!=null){
                          fileList.remove(fileData.getAudiofile().getFile());
                          coverArtList.addAll(fileData.getAudiofile().getEmbeddedArtworks());
                    }

                    for (TrackData trackData : fileData.getTrackDataList() ){
                        
                        TrackDefaultImpl track = trackMap.get(trackData.getNumber());
                       
                        if ( track != null){
                            
                            merge(Section.TRACK, track.getMetadataList(), trackData.getMetadata());
                            //track.getMetadataList().addAll(trackData.getMetadata());
                            
                            GenericStatusMessage statusMessage = new GenericStatusMessage(Severity.WARNING, "Track "+trackData.getNumber()+" is defined  in more than one cuesheet");
                            statusMessageList.add(statusMessage);
                            
                        } else {
                            
                           track = new TrackDefaultImpl(trackData.getNumber(), merge(Section.TRACK,new ArrayList<>(),trackData.getMetadata()));
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

            coverArtList.addAll(audiofile.getEmbeddedArtworks());
            
            Metadata Tracknumber = audiofile.getMetadata(FieldKey.TRACK);
            int trackNo=0;
            
            try {
                trackNo = Integer.parseInt(Tracknumber.getValue());
            } catch (NumberFormatException ex){
            
            }
           
            if (trackNo > 0) {
           
                TrackDefaultImpl track = trackMap.get(trackNo);
     
                if ( track == null){
                    
                    track = new TrackDefaultImpl(trackNo, merge(Section.TRACK,new ArrayList<>(),audiofile.getMetadata()));
                    trackMap.put(trackNo, track);
                    track.setLength( audiofile.getAudioHeader().getTrackLength());
                    
                 } else {    

                    merge(Section.TRACK, track.getMetadataList(), audiofile.getMetadata());
                    //track.getMetadataList().addAll();
                    GenericStatusMessage statusMessage = new GenericStatusMessage(Severity.WARNING, "Track "+trackNo+" is defined in more than one audio file or cue sheet");
                    statusMessageList.add(statusMessage);
                    
                    if (track.getLength() !=0 && audiofile.getAudioHeader().getTrackLength()!= track.getLength()){

                        track.setLength(0);

                    } else{

                        track.setLength( audiofile.getAudioHeader().getTrackLength());
                        //track.setOffset(0);
                    }  
                }
                
                track.addAudioFile(audiofile);
 
            } else {
           
               atAlbumLevel.addAll(audiofile.getMetadata());
               audioFileList.add(audiofile);
            }
        }
        for (File file : imagefileList){
            
            CoverArt fileCoverArt = new FileCoverArt(file);
            coverArtList.add(fileCoverArt);
            
        }
        /*
       * Validate the directory content
       */
        if (cueFileList.size() > 1 &&  audioFileList.size() > 0){
            
            GenericStatusMessage statusMessage = new GenericStatusMessage(Severity.WARNING, "Cue sheets and audio files defines Album ");
            statusMessageList.add(statusMessage);
           
        } else if (cueFileList.size() > 1) {
        
            GenericStatusMessage statusMessage = new GenericStatusMessage(Severity.WARNING, "More than one cue sheet defines Album ");
             statusMessageList.add(statusMessage);
        
        } else if (audioFileList.size() > 1){
        
            GenericStatusMessage statusMessage = new GenericStatusMessage(Severity.WARNING, "More than one audio file defines Album ");
            statusMessageList.add(statusMessage);
        } 

        ArrayList<Track> tracklist= new ArrayList<>(trackMap.values());
        AlbumDefaultImpl out = new AlbumDefaultImpl(coverArtList, atAlbumLevel, tracklist, directoryfileList, cueFileList, audioFileList, imagefileList, statusMessageList);
       
        return out;
    }

    private static ArrayList<Metadata> merge(String level, ArrayList<Metadata> target, ArrayList<Metadata> source){
        
        for (Metadata toAdd : source){

            String alias = level.equals(ALBUM) ?  getAlbumLevelMetadataAlias(toAdd.getKey()) :
                                                  getTrackLevelMetadataAlias(toAdd.getKey());
        
            String key = alias == null ? toAdd.getKey() : alias;
            
            boolean found = false;
            
            for (Metadata existing : target){
                
                if (existing.getKey().equals(key)){
                    
                    existing.getOrigins().addAll(toAdd.getOrigins());
                    found = true;
                    break;
                }
            }
            if (!found){
                target.add(new MetadataDefaultImpl(key, toAdd.getOrigins()));
            }
        }
         
        return target;
    }
    
    private static ArrayList<CueFile> getCueFileList(ArrayList<File> fileList) throws IOException {
        
        ArrayList<CueFile> out= new ArrayList<>();
        for (File file : fileList){
            if (CueFile.isCueFile(file)){
                
                try{
                    out.add(new CueFile(file));
                } catch (InvalidCueSheetException ex) {
                    
                   System.out.println("==== DirectoryParser.getCueFileList === InvalidCueSheetException: "+ ex);
                    
                }
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
