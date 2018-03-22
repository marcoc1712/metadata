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
package com.mc2.audio.metadata.impl;

import java.io.File;
import java.util.ArrayList;
import com.mc2.audio.metadata.API.Album;
import com.mc2.audio.metadata.API.CoverArt;
import com.mc2.audio.metadata.API.Medium;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataKeys.METADATA_KEY;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.Track;
import com.mc2.audio.metadata.source.cue.file.CueFile;
import com.mc2.audio.metadata.source.tags.file.AudioFile;

import static com.mc2.audio.metadata.API.MetadataKeys.getAlbumLevelMetadataAliases;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.StatusMessage.Severity;
import com.mc2.audio.metadata.source.cue.FileData;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author marco
 */
public class AlbumDefaultImpl implements Album {
    
    private final String url;
    private final ArrayList<File> fileList;
    private final ArrayList<CueFile> cueFileList;
    private final ArrayList<AudioFile> audioFileList;  
    private final ArrayList<File> imageFileList;  
    private final ArrayList<TrackDefaultImpl> trackList;
    private final ArrayList<CoverArt> coverArtList;
    private final ArrayList<Metadata> metadataList;
    private final ArrayList<StatusMessage> messageList;
    
    private Integer totalLength = 0;
	private ArrayList<MediumDefaultImpl> mediaList = new ArrayList<>();
	private String mediaString;

    public AlbumDefaultImpl(String url,
                            ArrayList<CoverArt> coverArtList,
                            ArrayList<Metadata> metadataList, 
                            ArrayList<TrackDefaultImpl> trackList, 
                            ArrayList<File> fileList, 
                            ArrayList<CueFile> cueFileList, 
                            ArrayList<AudioFile> audioFileList, 
                            ArrayList<File> imageFileList,
                            ArrayList<StatusMessage> messageList) {
        
        this.url = url;
        this.coverArtList = coverArtList;  
        this.metadataList = metadataList;  
        this.fileList = fileList;
        this.cueFileList = cueFileList;
        this.audioFileList = audioFileList;
        this.imageFileList = imageFileList;
        this.messageList = messageList;
        
		this.trackList = initTrackList(trackList);
        this.mediaList = initMedia(this.trackList);
		this.mediaString = initMediaString(this.mediaList);
    }
	private String initMediaString(ArrayList<MediumDefaultImpl> media){
		
		String out="";
		Map<String, Integer> mediaMap = new HashMap<>();
		
		for (MediumDefaultImpl medium : media){
			
			
			Integer count = mediaMap.get(medium.getType());
			if (count == null || count < 0){count = 0;}
			count ++;
			mediaMap.put(medium.getType(), count);	
		}
		
		Object[] keys = mediaMap.keySet().toArray();
		Arrays.sort(keys);
		
		for (Object key : keys){

			int count = mediaMap.get((String)key);
			
			String type = (String)key;
			
			if (keys.length == 1 && type.isEmpty() && count < 2){
				
				return out;
				
			} else if(keys.length == 1 && type.isEmpty()) {
				
				return out+count;
				
			} else if(type.isEmpty()) {
				
				type = "(?)";
				
			}
			
			if (!out.isEmpty()){
				out= out+" + ";
			}

			out = out+count+" "+type;
		}
		
		return out;
	}
    private ArrayList<MediumDefaultImpl> initMedia(ArrayList<TrackDefaultImpl> trackList){
		
		Map<String, MediumDefaultImpl> mediaMap = new HashMap<>();

        for (TrackDefaultImpl track : trackList){

			String mediumId = track.getMedium();
			MediumDefaultImpl medium  = mediaMap.get(mediumId);
			
			if (medium == null) {
				
				medium = new MediumDefaultImpl(mediumId, track.getMedia(), track.getDiscNo(), track.getDiscTitle());
				mediaMap.put(mediumId, medium);
			}
			//tracks are already ordered by medium and TrackNo.
			medium.addTrack(track);
        }
        
		Object[] keys = mediaMap.keySet().toArray();
		Arrays.sort(keys);
		
		ArrayList<MediumDefaultImpl> out = new ArrayList<>();
		
		for (Object key : keys){
			MediumDefaultImpl medium = mediaMap.get((String)key);
			medium.setIndex(out.size());
			out.add(medium);
			
			if ( keys.length > 1 && key.equals("") ){
			
				GenericStatusMessage statusMessage = new GenericStatusMessage(
                    messageList.size()+1,
                    Severity.WARNING, 
                    "One or more tracks miss both media type and number");
            
					messageList.add(statusMessage);
			}
		}
		
		return out;
		
    }
	private ArrayList<TrackDefaultImpl> initTrackList(ArrayList<TrackDefaultImpl> trackList){

        Integer index = 0;
        for (Track track : trackList){
            
            if ( track instanceof TrackDefaultImpl){
                totalLength= totalLength+track.getLength();
				((TrackDefaultImpl)track).setAlbum(this);
                ((TrackDefaultImpl)track).setIndex(index);
			}
            index++;
        }
        
        return trackList;
    }
	/*
    private ArrayList<Track> initTrackList2(ArrayList<Track> trackList){

        discIdOffsets.add(0,0);
        
        Integer offset = 0;
        Integer index = 0;
        for (Track track : trackList){
            
            if ( track instanceof TrackDefaultImpl){
				
                ((TrackDefaultImpl)track).setOffset(offset);
                offset= offset+track.getLength();
                totalLength= totalLength+track.getLength();
                discIdOffsets.add(track.getOffset());
				((TrackDefaultImpl)track).setAlbum(this);
                ((TrackDefaultImpl)track).setIndex(index);
            
			}
            index++;
            //System.out.println(track.getOffset()+" "+track.getLength()+" "+totalLength);
        }
        
        discIdOffsets.set(0, totalLength);
        return trackList;
    }
	 /**
     * @return the discId offsets

    @Override
    public Integer[] getOffsetArray() {
        return (Integer[])discIdOffsets.toArray();
    }
    /**
     * @return the discId offsets

    @Override
    public ArrayList<Integer> getOffsets() {
        return  discIdOffsets;
    }
    /**
     * @return the discId offsets

    @Override
    public String getToc() {
        
        String offests="";
        for (Integer offset : discIdOffsets){
            offests=offests+" "+offset;
        }
        return  "1 "+ (discIdOffsets.size()-1) + offests;
    }
	*/
	 
    @Override
    public String getAlbum(){
        return this.getMetadataValue(METADATA_KEY.ALBUM.name());
    }
    
    @Override
    public String getAlbumArtist(){
        return this.getMetadataValue(METADATA_KEY.ALBUM_ARTIST.name());
    }
	@Override
    public String getComposer(){
        return this.getMetadataValue(METADATA_KEY.COMPOSER.name());
    }
    
    @Override
    public String getGenre(){
        return this.getMetadataValue(METADATA_KEY.GENRE.name());
    }
    
    @Override
    public String getDate(){
        return this.getMetadataValue(METADATA_KEY.DATE.name());
    }
    
    @Override
    public String getCountry(){
        return this.getMetadataValue(METADATA_KEY.COUNTRY.name());
    }
    
    @Override
    public String getLabel(){
        return this.getMetadataValue(METADATA_KEY.LABEL.name());
    }
    
    @Override
    public String getCatalogNo(){
        return this.getMetadataValue(METADATA_KEY.CATALOG_NO.name());
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
    public ArrayList<? extends Track> getTrackList() {
        return trackList;
    }
	/**
     * @return the mediaList list
    */
	@Override
	public ArrayList<? extends Medium> getMediaList() {
		return mediaList;
	}
	 /**
     * @return the Media descriptor (es. 2 CD + 1 DVD)
    */
	@Override
	public String getMedia(){
		return mediaString;
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
    /**
     * @return the messageList
     */
    @Override
    public ArrayList<StatusMessage> getMessageList() {
        return messageList;
    }
    /**
     * @return the status
     */
    @Override
    public Severity getStatus(){
        
        int index= 0;
        Severity status= Severity.OK;
        
        for (StatusMessage statusMessage : getMessageList()){
            if (statusMessage.getSeverityIndex()>index){
                index = statusMessage.getSeverityIndex();
                status = statusMessage.getSeverity();
            }
        }
        return status;
    }
    /**
     * @return the url
     */
    @Override
    public String getUrl() {
        return url;
    }

    private String getMetadataValue(String key){

		String value=getMetadataFromAlbum(key);
		if (!value.isEmpty()){return value;}
		
        value = getMetadataValueFromTracks(key);
        
        return value;
        
    }
	
	protected String getMetadataFromAlbum(String key){
		
		String value="";
		
		// first search at album level for the specific key.
        for (Metadata  metadata : metadataList){

            if (metadata.getKey().equals(key)){
                 value = metadata.getValue();
				 if (!value.isEmpty()){return value;}
            } 
        }
		
		// then  search at album level for key aliases.
        ArrayList<String> aliases = getAlbumLevelMetadataAliases(key);
		for (String alias : aliases){
            
            value = getMetadataValue(alias);
            if (!value.isEmpty()){return value;}
        }
		return value;
	}
	
	/*
	if all tracks store the same value for that key, returns the value, 
	blank instead.
	*/
    private String getMetadataValueFromTracks(String key){
        
        String value = "";
        
        for (Track  track : this.getTrackList()){
            
            if (track  instanceof TrackDefaultImpl){
                String trackValue = ((TrackDefaultImpl)track).getMetadataFromTrack(key);
                
                if (value.isEmpty()){
                    
                    value= trackValue;
                } else {
                    
                    if (!value.equals(trackValue)){
                    
                        value = "";
                        break;
                    }
                }
            }  
        }
        return value;
    }
}
