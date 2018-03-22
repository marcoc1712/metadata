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
package com.mc2.audio.metadata.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import jwbroek.cuelib.Message;

import org.apache.commons.io.FilenameUtils;
import com.mc2.audio.metadata.impl.MetadataDefaultImpl;
import com.mc2.audio.metadata.API.exceptions.InvalidAudioFileException;
import com.mc2.audio.metadata.API.exceptions.InvalidAudioFileFormatException;
import com.mc2.audio.metadata.API.exceptions.InvalidCueSheetException;
import com.mc2.audio.metadata.source.cue.FileData;
import com.mc2.audio.metadata.source.cue.TrackData;
import com.mc2.audio.metadata.source.cue.file.CueFile;
import com.mc2.audio.metadata.source.tags.file.AudioFile;
import com.mc2.audio.metadata.impl.AlbumDefaultImpl;
import com.mc2.audio.metadata.API.CoverArt;
import com.mc2.audio.metadata.API.Metadata;
import static com.mc2.audio.metadata.API.MetadataKeys.getAlbumLevelMetadataAlias;
import static com.mc2.audio.metadata.API.MetadataKeys.getTrackLevelMetadataAlias;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.StatusMessage.Severity;
import com.mc2.audio.metadata.API.Track;
import com.mc2.audio.metadata.impl.GenericStatusMessage;
import com.mc2.audio.metadata.impl.TrackDefaultImpl;
import com.mc2.audio.metadata.source.coverart.FileCoverArt;
import com.mc2.audio.metadata.source.cue.Section;
import static com.mc2.audio.metadata.source.cue.Section.ALBUM;
import java.util.logging.Logger;

/**
 *
 * @author marco
 */
public class DirectoryParser {
	private static Logger log = Logger.getLogger(DirectoryParser.class.getName()); 
	
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

		HashMap<String,TrackDefaultImpl> trackMap = new HashMap<>();

		if (containsAudio(fileList)){
	    
			if (containsCueFile(fileList)){

				cueFileList = getCueFileList(fileList);

				for (CueFile cueFile :cueFileList){
					
					for (Message message : cueFile.getCuesheet().getMessages()){

						statusMessageList.add (new GenericStatusMessage(
								statusMessageList.size()+1,
								cueFile.getCuesheet().getSourceId(),
								Severity.WARNING, 
								message.getMessage()+" - "+message.getInput()));
					}
					
					if (cueFileList.size() == 1) {
					
						merge(Section.ALBUM, atAlbumLevel, cueFile.getCuesheet().getMetadata());
					}
					for (FileData fileData : cueFile.getCuesheet().getFileDataList()){

						AudioFile audiofile = fileData.getAudiofile();

						if (audiofile !=null){
							  fileList.remove(fileData.getAudiofile().getFile());
							  coverArtList.addAll(fileData.getAudiofile().getEmbeddedArtworks());  
						}

						Integer trackCount= fileData.getTrackDataList().size();

						for (TrackData trackData : fileData.getTrackDataList() ){

							String trackId = trackData.getTrackId();

							TrackDefaultImpl track = trackMap.get(trackData.getTrackId());
							
							ArrayList<Metadata> fromAlbum = new ArrayList<>();
									
							if (cueFileList.size() > 1) {
					
								merge(Section.TRACK,  fromAlbum, cueFile.getCuesheet().getMetadata());

							}

							if ( track != null){
								
								merge(Section.TRACK, track.getMetadataList(), fromAlbum);
								merge(Section.TRACK, track.getMetadataList(), trackData.getMetadata());
								//track.getMetadataList().addAll(trackData.getMetadata());

								GenericStatusMessage statusMessage = new GenericStatusMessage(
										statusMessageList.size()+1,
										Severity.WARNING, 
										"Track defined in more than one audio file or cue sheet");

								if (!statusMessageList.contains(statusMessage)){

									statusMessageList.add(statusMessage);
								}   

								statusMessage = new GenericStatusMessage(
										track.getMessageList().size()+1,
										cueFile.getCuesheet().getSourceId(),
										Severity.WARNING, 
										"Track is defined in more than one audio file or cue sheet");
								track.addStatusMessage(statusMessage);

							} else {

							   track = new TrackDefaultImpl(trackData.getTrackId(), merge(Section.TRACK,fromAlbum, trackData.getMetadata()));
							   trackMap.put(trackData.getTrackId(), track);
							}

							if (audiofile ==null){

								track.setFile(null);
								track.setLength(0);
								track.setOffset(0);
								track.setUrl("");
								track.setPlayListUrl("");
								

							} else if ( track.getFile() != null && audiofile.getFile()!= track.getFile() &&
										track.getLength() !=0 && trackData.getLength()!= track.getLength() ||
										track.getOffset() !=0 && trackData.getOffset()!= track.getOffset()){

								track.setFile(null);
								track.setLength(0);
								track.setOffset(0);
								track.setUrl("");
								track.setPlayListUrl("");
								

							} else {

								track.setFile(audiofile.getFile());
								track.setLength(trackData.getLength());
								track.setOffset(trackData.getOffset());

								if (trackCount > 1){

									track.setUrl(audiofile.getFile().getCanonicalPath()+"#"+trackData.getStartInFileInMillis()/1000+"-"+(trackData.getStartInFileInMillis()+trackData.getLengthInMillis())/1000);
								
								} else {

									track.setUrl(audiofile.getFile().getCanonicalPath());
									
								}
								
								track.setPlayListUrl(cueFile.getSourceId());
							}

						}
						fileList.remove(cueFile.getFile());
					}
				} 
			}
			for (File file : fileList){

				AudioFile audiofile = AudioFile.get(file);

				coverArtList.addAll(audiofile.getEmbeddedArtworks());

				String trackId = audiofile.getTrackId();
							
				if (!trackId.isEmpty()) {

					TrackDefaultImpl track = trackMap.get(trackId);

					if ( track == null){

						track = new TrackDefaultImpl(trackId, merge(Section.TRACK,new ArrayList<>(),audiofile.getMetadata()));
						track.setPlayListUrl(directory.getCanonicalPath());
						
						trackMap.put(trackId, track);

						track.setFile(audiofile.getFile());
						track.setLength( audiofile.getAudioHeader().getTrackLength()*75); //in auidiofile header is expressed in secs.
						track.setUrl(audiofile.getFile().getCanonicalPath());

					 } else {    

						merge(Section.TRACK, track.getMetadataList(), audiofile.getMetadata());
						//track.getMetadataList().addAll();
						GenericStatusMessage statusMessage = new GenericStatusMessage(
								statusMessageList.size()+1,
								Severity.WARNING, 
								"Track defined in more than one audio file or cue sheet");

						if (!statusMessageList.contains(statusMessage)){

							statusMessageList.add(statusMessage);
						} 

						statusMessage = new GenericStatusMessage(
								track.getMessageList().size()+1,
								audiofile.getSourceId(),
								Severity.WARNING, 
								"Track is defined in more than one audio file or cue sheet");

						track.addStatusMessage(statusMessage);

						track.setFile(null);
						track.setUrl("");
						track.setPlayListUrl("");

						if (track.getLength() !=0 && audiofile.getAudioHeader().getTrackLength()!= track.getLength()){

							track.setLength(0);

						} else{

							track.setLength( audiofile.getAudioHeader().getTrackLength()*75);
							//track.setOffset(0);
						}  
					}

					track.addRawKeyValuePairSource(audiofile);

				} else {

				   atAlbumLevel.addAll(audiofile.getMetadata());
				   audioFileList.add(audiofile);
				}
			}
			for (File file : imagefileList){

				CoverArt fileCoverArt = new FileCoverArt(file);
				coverArtList.add(fileCoverArt);

			}
		} 
        /*
       * Validate the directory content
       */
        
        String url = "";

        if (cueFileList.size() > 1 &&  audioFileList.size() > 0){
            
            GenericStatusMessage statusMessage = new GenericStatusMessage(
                    statusMessageList.size()+1,
                    Severity.WARNING, 
                    "Cue sheets and audio files defines Album ");
            
            statusMessageList.add(statusMessage);
           
        } else if (cueFileList.size() > 1) {
        
            GenericStatusMessage statusMessage = new GenericStatusMessage(
                    statusMessageList.size()+1,
                    Severity.INFO, 
                    "More than one cue sheet defines Album");
            
             statusMessageList.add(statusMessage);
			 url=directory.getCanonicalPath();
        
        } else if (audioFileList.size() > 1){
        
            GenericStatusMessage statusMessage = new GenericStatusMessage(
                    statusMessageList.size()+1,
                    Severity.WARNING, 
                    "More than one audio file defines Album ");
            
            statusMessageList.add(statusMessage);
        
		} else if (cueFileList.size()==1){
            
            url=cueFileList.get(0).getSourceId();
        
        } else {
            
            url=directory.getCanonicalPath();
        }
		
		Object[] keys = trackMap.keySet().toArray();
		Arrays.sort(keys);
		
		ArrayList<TrackDefaultImpl> tracklist= new ArrayList<>();
		for (Object key : keys){
			tracklist.add(trackMap.get((String)key));
		}

        AlbumDefaultImpl out = new AlbumDefaultImpl(url, coverArtList, atAlbumLevel, tracklist, directoryfileList, cueFileList, audioFileList, imagefileList, statusMessageList);
       
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
	
	private static boolean containsAudio(ArrayList<File> fileList) throws IOException{
		
		if (containsCueFile(fileList)) return true;
		
		for (File file : fileList){
		
			try {
				if (AudioFile.get(file) != null){
					return true; 
				}
					
			} catch (InvalidAudioFileException  | InvalidAudioFileFormatException ex) {
				//do nothing
			}
			
		}
		return false;
	}
   
}
