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
package com.mc2.audio.metadata.fromFiles;

import java.util.ArrayList;
import com.mc2.audio.metadata.API.Album;
import com.mc2.audio.metadata.API.CoverArt;
import static com.mc2.audio.metadata.API.CoverArt.SOURCE_EMBEDDED_FILE;
import static com.mc2.audio.metadata.API.CoverArt.SOURCE_IMAGE_FILE;
import static com.mc2.audio.metadata.API.CoverArt.SOURCE_MUSICBRAINZ_COVER_ARCHIVE;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.Track;
import com.mc2.audio.metadata.impl.AbstractAlbum;
import com.mc2.audio.metadata.source.cue.FileData;
import com.mc2.audio.metadata.source.cue.file.CueFile;
import com.mc2.audio.metadata.source.tags.file.AudioFile;
import java.util.HashMap;


/**
 *
 * @author marco
 */
public class AlbumFromFiles extends AbstractAlbum implements Album {
	
	private final String albumId;
	private final ArrayList<CueFile> cueFileList;
    private final ArrayList<AudioFile> audioFileList;  

    public AlbumFromFiles(	String albumId,
                            ArrayList<CoverArt> coverArtList,
                            ArrayList<Metadata> metadataList, 
                            ArrayList<TrackFromFile> trackList,
                            ArrayList<CueFile> cueFileList, 
                            ArrayList<AudioFile> audioFileList, 
                            ArrayList<StatusMessage> messageList,
							ArrayList<? extends Track> singleTracksList) {
        
		super(metadataList,trackList);
		
		this.albumId=albumId;

        this.cueFileList = cueFileList;
        this.audioFileList = audioFileList;
		this.singleTracksList = singleTracksList;
		this.messageList = messageList;
		this.coverArtList = this.orderCoverArt(coverArtList);

    }
	@Override
    public String getId() {
        return albumId;
    }
	
	@Override
    public ArrayList<RawKeyValuePairSource> getRawKeyValuePairSources() {
        
		ArrayList<RawKeyValuePairSource> sources=  new ArrayList<>();

        for (AudioFile audioFile :audioFileList){
            
             sources.add(audioFile);
        }
        for (CueFile cueFile : cueFileList){
        
            sources.add(cueFile);
            
            for (FileData fileData : cueFile.getCuesheet().getFileDataList()){
                
                sources.add(fileData.getAudiofile());
            }
        }
        for (Track track : this.getTrackList()){
                
            sources.addAll(track.getRawKeyValuePairSources());
        }
        
        return sources;
        
    }
	
	private ArrayList<CoverArt> orderCoverArt(ArrayList<CoverArt> coverArtList){

		HashMap< Integer, ArrayList<CoverArt> > scored = new HashMap<>();
		for (CoverArt coverArt :coverArtList){
			
			Integer sourceScore;
			Integer typeScore;
			
			String source = coverArt.getSource() != null ? coverArt.getSource() : "";
			String type = coverArt.getType()!= null ? coverArt.getType().toLowerCase().trim() : "";
			
			switch (source) {
				case "" :  sourceScore = 9;
						 break;
				case SOURCE_MUSICBRAINZ_COVER_ARCHIVE :  sourceScore = 5;
						 break;
				case SOURCE_EMBEDDED_FILE :  sourceScore = 3;
						 break;
				case SOURCE_IMAGE_FILE :  sourceScore = 0;
						 break;
				default :  sourceScore = 7;
			}
			switch (type) {
				case "" :  typeScore = 9;
						 break;
				case "front" :  typeScore = 5;
						 break;
				case "folder  (front)" :  typeScore = 4;
						 break;
				case "cover (front)" :  typeScore = 3;
						 break;
				case "folder" :  typeScore = 1;
						 break;
				case "cover" :  typeScore = 0;
						 break;
				default :  typeScore = 7;
			}
			
			Integer score = typeScore*10+sourceScore;
			if (scored.get(score) == null){
				
				scored.put(score,new  ArrayList<>());
				
			}
			
			scored.get(score).add(coverArt);

		}
		ArrayList<CoverArt> out = new  ArrayList<>();
		for (Integer score : scored.keySet()){
			
			out.addAll(scored.get(score));
		}
		return out;
	}
	
}
