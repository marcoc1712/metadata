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
import static com.mc2.audio.metadata.API.MetadataKey.getAlbumLevelMetadataAlias;
import static com.mc2.audio.metadata.API.MetadataKey.getTrackLevelMetadataAlias;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.StatusMessage.Severity;
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
	private static Logger logger = Logger.getLogger(DirectoryParser.class.getName()); 
	
    public static AlbumDefaultImpl parse(String directory) throws IOException, InvalidAudioFileException, InvalidAudioFileFormatException {
         return parse(new File(directory));
    }
    public static AlbumDefaultImpl parse(File directory) throws IOException,  InvalidAudioFileException, InvalidAudioFileFormatException{
        
		logger.info("ENTRY");
		
		ArrayList<File> directoryfileList = new ArrayList<>(Arrays.asList(directory.listFiles(new GenericFileFilter(false))));
		ArrayList<File> fileList = new ArrayList<>(Arrays.asList(directory.listFiles(new AudioFileFilter(false))));
		ArrayList<File> imagefileList = new ArrayList<>(Arrays.asList(directory.listFiles(new ImageFileFilter())));

		ArrayList<CueFile> cueFileList= new ArrayList<>();
		ArrayList<AudioFile> audioFileList= new ArrayList<>();

		ArrayList<StatusMessage> statusMessageList= new ArrayList<>();
		ArrayList<Metadata> atAlbumLevel= new ArrayList<>();
		ArrayList<CoverArt> coverArtList= new ArrayList<>();
		
		ArrayList<TrackDefaultImpl> singleTrackList = new ArrayList<>();

		HashMap<String,TrackDefaultImpl> trackMap = new HashMap<>();

		if (containsAudio(fileList)){
	    
			ArrayList<CoverArt> directorycoverArtList= new ArrayList<>();
			for (File file : imagefileList){

				CoverArt imagefileCoverArt = new FileCoverArt(file);
				directorycoverArtList.add(imagefileCoverArt);

			}
			
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
						ArrayList<CoverArt> fileCoverArtList = new ArrayList<>();

						if (audiofile !=null){
							  
							fileList.remove(fileData.getAudiofile().getFile());
							fileCoverArtList = fileData.getAudiofile().getEmbeddedArtworks();
							coverArtList.addAll(fileCoverArtList);  

							Integer trackCount= fileData.getTrackDataList().size();

							for (TrackData trackData : fileData.getTrackDataList() ){

								String trackId = trackData.getTrackId();
								ArrayList<Metadata> fromAlbum = new ArrayList<>();
								merge(Section.TRACK,  fromAlbum, cueFile.getCuesheet().getMetadata());
								
								ArrayList<CoverArt> trackCoverArt = new ArrayList<>();
								trackCoverArt.addAll(fileCoverArtList);
								trackCoverArt.addAll(directorycoverArtList);

								TrackDefaultImpl singleTrack = new TrackDefaultImpl(
									trackData.getTrackId(), 
									merge(Section.TRACK,fromAlbum, 
									trackData.getMetadata()), 
									trackCoverArt);

								completeTrack (	singleTrack,
									cueFile.getSourceId(),
									audiofile, 
									trackCount,
									trackData.getLength(),
									trackData.getOffset(),
									trackData.getStartInFileInMillis(),
									trackData.getLengthInMillis());
								
								singleTrackList.add(singleTrack);

								TrackDefaultImpl track = trackMap.get(trackId);
								if ( track == null){

									track = singleTrack;
									trackMap.put(trackData.getTrackId(), track);


								} else {

									merge(Section.TRACK, track.getMetadataList(), fromAlbum);
									merge(Section.TRACK, track.getMetadataList(), trackData.getMetadata());
									//track.getMetadataList().addAll(trackData.getMetadata());

									track.getCoverArtList().addAll(fileCoverArtList);

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
									
									completeTrack (	track,
											cueFile.getSourceId(),
											audiofile, 
											trackCount,
											trackData.getLength(),
											trackData.getOffset(),
											trackData.getStartInFileInMillis(),
											trackData.getLengthInMillis());
								} 
							}
							fileList.remove(cueFile.getFile());
						}
					}
				} 
			}
			for (File file : fileList){

				AudioFile audiofile = AudioFile.get(file);
				
				if (audiofile != null){ 
					
					ArrayList<CoverArt> fileCoverArtList= new ArrayList<>();
					fileCoverArtList.addAll(audiofile.getEmbeddedArtworks());
					coverArtList.addAll(fileCoverArtList);  

					String trackId = audiofile.getTrackId();
					
					ArrayList<CoverArt> trackCoverArt = new ArrayList<>();
					trackCoverArt.addAll(fileCoverArtList);
					trackCoverArt.addAll(directorycoverArtList);
					
					TrackDefaultImpl singleTrack = 
							new TrackDefaultImpl(
									trackId, 
									merge(Section.TRACK,new ArrayList<>(),audiofile.getMetadata()),
									trackCoverArt);	
					
					singleTrack.setPlayListUrl(directory.getCanonicalPath());
					
					completeTrack (	singleTrack,
									directory.getCanonicalPath(),
									audiofile, 
									1,
									audiofile.getAudioHeader().getTrackLength()*75,
									0,
									0L,
									0L);

					singleTrack.addRawKeyValuePairSource(audiofile);

					singleTrackList.add(singleTrack);

					TrackDefaultImpl track = trackMap.get(trackId);

					if ( track == null){

						track = singleTrack;
						trackMap.put(trackId, track);

					} else {    

						track.getCoverArtList().addAll(fileCoverArtList);
						merge(Section.TRACK, track.getMetadataList(), audiofile.getMetadata());

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

						track.addRawKeyValuePairSource(audiofile);

						completeTrack (	track,
										directory.getCanonicalPath(),
										audiofile, 
										1,
										audiofile.getAudioHeader().getTrackLength()*75,
										0,
										0L,
										0L);
					}

					/* }
					// Don't add no tracks file at album level, handle them as 
					// track zeto. BEWARE to embedded cue sheets.
					 else {

					   atAlbumLevel.addAll(audiofile.getMetadata());
					   audioFileList.add(audiofile);
					}*/
				}
			}
			coverArtList.addAll(directorycoverArtList);	
		} 
        /*
       * Validate the directory content
       */
        
        String url = "";

        if (cueFileList.size() > 0 &&  audioFileList.size() > 0){
            
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
		logger.info("ENTRY ALBUM BUILD");
        AlbumDefaultImpl out = new AlbumDefaultImpl(url, coverArtList, atAlbumLevel, tracklist, directoryfileList, cueFileList, audioFileList, imagefileList, statusMessageList, singleTrackList);
        logger.info("RETURN");
        return out;
    }
	private static void completeTrack(	TrackDefaultImpl track,
										String playListUrl, //CueFile cueFile, directory.getCanonicalPath()
										AudioFile audiofile,
										Integer trackCount,
										//TrackData trackData,
										Integer length,
										Integer offset,
										Long startInFileInMillis,
										Long lengthInMillis ) throws IOException{

		if ( track.getFile() != null && audiofile.getFile()!= track.getFile() &&
			 track.getLength() != null && !track.getLength().equals(length) &&
			 track.getOffset()!= null && !track.getOffset().equals(offset)){

			track.setFile(null);
			track.setLength(0);
			track.setOffset(0);
			track.setUrl("");
			track.setPlayListUrl("");
			
			if (track.getFormat() == null || track.getFormat().equals("")){
				
				track.setFormat(audiofile.getAudioHeader().getFormat());
				
			} else if (!audiofile.getAudioHeader().getFormat().equals(track.getFormat())){
				
				track.setFormat("");
			}

			if (track.getSampleRate() == null || track.getSampleRate() == 0){

					track.setSampleRate(audiofile.getAudioHeader().getSampleRateAsNumber());

			} else if (audiofile.getAudioHeader().getSampleRateAsNumber()!= track.getSampleRate()){
					
					track.setSampleRate(0);
					
			}
			
			if (track.getBitsPerSample() == null || track.getBitsPerSample() ==0){

					track.setBitsPerSample(audiofile.getAudioHeader().getBitsPerSample());

			} else if (audiofile.getAudioHeader().getBitsPerSample()!= track.getBitsPerSample()){
					
					track.setBitsPerSample(0);
					
			}
			
			if (track.getChannels() == null || track.getChannels().equals("")){
				
				track.setChannels(audiofile.getAudioHeader().getChannels());
				
			} else if (!audiofile.getAudioHeader().getChannels().equals(track.getChannels())){
				
				track.setChannels("");
			}
			
			if (track.getBitRate() == null || track.getBitRate() ==0){

					track.setBitRate(audiofile.getAudioHeader().getBitRateAsNumber());

			} else if (audiofile.getAudioHeader().getBitRateAsNumber()!= track.getBitRate()){
					
					track.setBitRate(0L);
					
			}
						
			if (track.isVariableBitRate() == null || (track.isVariableBitRate() && !audiofile.getAudioHeader().isVariableBitRate())){

					track.setVariableBitRate(false);

				} else{

					track.setVariableBitRate(audiofile.getAudioHeader().isVariableBitRate());
			}
			
			if (track.isLossless() == null || (track.isLossless() && !audiofile.getAudioHeader().isLossless())){

					track.setLossless(false);

				} else{

					track.setLossless(audiofile.getAudioHeader().isLossless());
			}


		} else {

			track.setFile(audiofile.getFile());
			track.setLength(length);
			track.setOffset(offset);

			track.setFormat(audiofile.getAudioHeader().getFormat());
			track.setSampleRate(audiofile.getAudioHeader().getSampleRateAsNumber());
			track.setBitsPerSample(audiofile.getAudioHeader().getBitsPerSample());
			track.setChannels(audiofile.getAudioHeader().getChannels());
			track.setVariableBitRate(audiofile.getAudioHeader().isVariableBitRate());
			track.setBitRate(audiofile.getAudioHeader().getBitRateAsNumber());
			track.setLossless(audiofile.getAudioHeader().isLossless());

			if (trackCount > 1){

				track.setUrl(audiofile.getFile().getCanonicalPath()+"#"+startInFileInMillis/1000+"-"+(startInFileInMillis+lengthInMillis)/1000);

			} else {

				track.setUrl(audiofile.getFile().getCanonicalPath());

			}

			track.setPlayListUrl(playListUrl);

		}
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
