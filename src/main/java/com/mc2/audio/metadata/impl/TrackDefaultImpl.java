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

import com.mc2.audio.metadata.API.Album;
import com.mc2.audio.metadata.API.CoverArt;
import static com.mc2.audio.metadata.API.CoverArt.SOURCE_EMBEDDED_FILE;
import static com.mc2.audio.metadata.API.CoverArt.SOURCE_IMAGE_FILE;
import static com.mc2.audio.metadata.API.CoverArt.SOURCE_MUSICBRAINZ_COVER_ARCHIVE;
import java.io.File;
import java.util.ArrayList;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataKey;
import static com.mc2.audio.metadata.API.MetadataKey.getAlbumLevelMetadataAliases;
import static com.mc2.audio.metadata.API.MetadataKey.getTrackLevelMetadataAliases;
import com.mc2.audio.metadata.API.MetadataRow;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.Track;
import com.mc2.util.miscellaneous.CalendarUtils;
import java.util.HashMap;

/**
 *
 * @author marco
 */
public class TrackDefaultImpl implements Track {

    private String trackId; 
    private File file;
    private Integer offset=0;
    private Integer length=0;
	
    private String url;
	
	private String  playListUrl;
	private Integer playListIndex;

	private Album album;
	private Integer index;
    
	private String  format;
	private Integer sampleRate;
	private Integer bitsPerSample;
	private String  channels;
	private Boolean isVariableBitRate;
	private	Long    bitRate;
	private Boolean isLossless;
	
    private final ArrayList<Metadata> metadataList;
	private ArrayList<CoverArt> coverArtList;
	
    private final ArrayList<RawKeyValuePairSource> rawKeyValuePairSources; 
    private final ArrayList<StatusMessage> messageList;
	
	private final MetadataTable metadataTable;
    
    public TrackDefaultImpl(String  trackId,
							ArrayList<Metadata> metadataList,  
							ArrayList<CoverArt> coverArtList) {
        this.trackId = trackId;
        this.metadataList = metadataList;
		this.coverArtList = this.orderCoverArt(coverArtList);
        this.rawKeyValuePairSources =new ArrayList<>();
        this.messageList =new ArrayList<>();
		
		this.metadataTable = new MetadataTable(this);
    }
    /**
     * @return the playback url of the track
     */
	@Override
    public String getUrl() {
        return url;
    }
	
	 /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
	
	/**
     * @return the playback playlist url 
     */
    @Override
    public String getPlayListUrl() {
        return playListUrl;
    }
	/**
	 * @param playListUrl the playListUrl to set
	 */
	public void setPlayListUrl(String playListUrl) {
		this.playListUrl = playListUrl;
	}
	
	/**
     * @return the playback Playlist index position of the track in the playlist,
	 * stating from 0.
     */
	@Override
	public Integer getPlayListIndex(){
		return playListIndex;
	}
	/**
	 * @param playListIndex the playListIndex to set
	 */
	public void setPlayListIndex(Integer playListIndex) {
		this.playListIndex = playListIndex;
	}
	
	/**
     * @return the Index position of the track in the Album (normally trackNo -1).
     */
    @Override
    public Integer getIndex() {
        return index;
    }
	
    /**
     * @param index the index to set
     */
    public void setIndex(Integer index) {
        this.index = index;
    }
	
	/**
     *  @return the trackId (normally discNo.trackNo)
     */
    @Override
    public String getTrackId() {
        return trackId;
    }

    @Override
    public String getTitle(){
    
        return this.getMetadataValue(MetadataKey.METADATA_KEY.TITLE.name());
    }
	
    @Override
    public String getArtist(){
    
        return this.getMetadataValue(MetadataKey.METADATA_KEY.ARTIST.name());
    }
	
	/**
	 * @return the coverArtList
	 */
	@Override
	public ArrayList<CoverArt> getCoverArtList() {
		return coverArtList;
	}
	
	
	/**
	 * @param coverArtList the coverArtList to set
	 */
	public void setCoverArtList(ArrayList<CoverArt> coverArtList) {
		this.coverArtList = coverArtList;
	}
	
	/**
	 *@return the most representative coverArt
	 */
	@Override
	public CoverArt getCoverArt(){
		
		if (coverArtList == null || coverArtList.isEmpty()) {return null;}
		return coverArtList.get(0);
	}
	 /**
     * @return the metadataList
     */
    @Override
    public ArrayList<Metadata> getMetadataList() {
        return metadataList;
    }
	@Override
    public ArrayList<? extends MetadataRow> getDescriptionMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.DESCRIPTION);
    }
	@Override
    public ArrayList<? extends MetadataRow> getCommentMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.COMMENT);
    }
	@Override
    public ArrayList<? extends MetadataRow> getAwardMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.AWARD);
    }
	@Override
    public ArrayList<? extends MetadataRow> getCollectionMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.COLLECTION);
    }
	@Override
    public ArrayList<? extends MetadataRow> getGoodiesMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.GOODIES);
    }
	@Override
    public ArrayList<? extends MetadataRow> getURLMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.URL);
    }
	@Override
    public ArrayList<? extends MetadataRow> getMusicDescriptorMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.MUSIC_DESCRIPTOR);
    }
	@Override
    public ArrayList<? extends MetadataRow> getCreditMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.CREDIT);
    }
	@Override
    public ArrayList<? extends MetadataRow> getOtherInvolvedPersonMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.OTHER_INVOLVED_PERSON);
    }
	@Override
    public ArrayList<? extends MetadataRow> getMediaDescriptorMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.MEDIA_DESCRIPTOR);
    }
	@Override
    public ArrayList<? extends MetadataRow> getWorkDescriptorMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.WORK_DESCRIPTOR);
    }
	@Override
    public ArrayList<? extends MetadataRow> getMiscellaneaMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.MISCELLANEA);
    }
	@Override
    public ArrayList<? extends MetadataRow> getRatingMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.RATING);
    }
	@Override
	public ArrayList<? extends MetadataRow> getAlbumInfoMetada(){
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.ALBUM_INFO);
    }
	@Override
    public ArrayList<? extends MetadataRow> getMetadaWithNoCategory() {
		
		return metadataTable.getMetadaWithNoCategory();
    }
	/**
     * @return the sources of Raw Key Value Pairs, like tags or cue files commands.
     */
    @Override
    public ArrayList<RawKeyValuePairSource> getRawKeyValuePairSources() {
        return rawKeyValuePairSources;
    }
    /**
     * add a souce to the list
     * @param rawKeyValuePairSource
     */
    public void addRawKeyValuePairSource(RawKeyValuePairSource rawKeyValuePairSource) {
        
        if (!rawKeyValuePairSources.contains(rawKeyValuePairSource)){
            rawKeyValuePairSources.add(rawKeyValuePairSource);
        }   
    }
	
	/**
     * @return the length in sectors.
     */
    @Override
    public Integer getLength() {
        return length;
    }
	/**
     * @param length the length in sectors to set
     */
    public void setLength(int length) {
        this.length = length;
    }
	
    /** @return file length in msec */
    @Override
    public Long getLengthInMillis(){
        return CalendarUtils.getMilliseconds(getLength());
    }
    /** @return file length string */
    @Override
    public String getLengthString(){

        return CalendarUtils.getTimeString(getLengthInMillis());
    }
	
	/**
	 * @return the format
	 */
	@Override
	public String getFormat() {
		return format;
	}
	
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	/**
	 * @return the sampleRate
	 */
	@Override
	public Integer getSampleRate() {
		return sampleRate;
	}
	
	/**
	 * @param sampleRate the sampleRate to set
	 */
	public void setSampleRate(Integer sampleRate) {
		this.sampleRate = sampleRate;
	}

	/**
	 * @return the bitsPerSample
	 */
	@Override
	public Integer getBitsPerSample() {
		return bitsPerSample;
	}

	/**
	 * @param bitsPerSample the bitsPerSample to set
	 */
	public void setBitsPerSample(Integer bitsPerSample) {
		this.bitsPerSample = bitsPerSample;
	}
	
	/**
	 * @return the channels
	 */
	@Override
	public String getChannels() {
		return channels;
	}
	
	/**
	 * @param channels the channels to set
	 */
	public void setChannels(String channels) {
		this.channels = channels;
	}
	
	/**
     * @return if the sampling bitRate is variable or constant
     */
	@Override
    public Boolean isVariableBitRate(){
		return isVariableBitRate;
	};
	
	/**
	 * @param isVariableBitRate the VariableBitRate to set
	 */
	public void setVariableBitRate(Boolean isVariableBitRate) {
		this.isVariableBitRate = isVariableBitRate;
	}
	
	/**
     * @return bitRate as a number, this is the amount of kilobits of data sampled per second
     */
	@Override
    public Long getBitRate(){
		return bitRate;
	};
	
	/**
	 * @param bitRate the bitrate to set
	 */
	public void setBitRate(Long bitRate) {
		this.bitRate = bitRate;
	}
	
	/**
	 * @return the isLossless
	 */
	@Override
	public Boolean isLossless() {
		return isLossless;
	}
	
	/**
	 * @param isLossless the isLossless to set
	 */
	public void setLossless(Boolean isLossless) {
		this.isLossless = isLossless;
	}
	
	/**
	 * @return ture if the file is in hight resolution Audio
	*/
	@Override
	public Boolean isHiRes(){
		
		if (getBitsPerSample() != null && getBitsPerSample() >16) return true;
		if (getSampleRate() != null && getSampleRate() > 44100) return true;
		
		return false;
	}
	
@Override
    public StatusMessage.Severity getStatus(){
        
        int index= 0;
        StatusMessage.Severity status= StatusMessage.Severity.OK;
        
        for (StatusMessage statusMessage : getMessageList()){
            if (statusMessage.getSeverityIndex()>index){
                index = statusMessage.getSeverityIndex();
                status = statusMessage.getSeverity();
            }
        }
        return status;
    }
	
    /**
     * @return the messageList
     */
    @Override
    public ArrayList<StatusMessage> getMessageList() {
        return messageList;
    }
    /**
     * add a message to the messageList
     * @param message
     */
    
    public void addStatusMessage(StatusMessage message) {
        
        if (!messageList.contains(message)){
            messageList.add(message);
        }   
    }

	/**
	 * @return the album
	 */
	public Album getAlbum() {
		return album;
	}

	/**
	 * @param album the album to set
	 */
	public void setAlbum(Album album) {
		this.album = album;
	}
	
		
	public String getMedia() {
       return this.getMetadataValue(MetadataKey.METADATA_KEY.MEDIA.name());
    }
	
	public String getMedium() {
		String media = getMedia();
		String no = getDiscNo();
		
		if (!media.isEmpty() && !no.isEmpty()){
			return media+" "+no;
		} else if (!no.isEmpty()){
			return no;
		}
		return "";
	}
	
	/**
     * @return the discNo
     */
    public String getDiscNo() {
       return this.getMetadataValue(MetadataKey.METADATA_KEY.DISC_NO.name());
    }
	
	/**
     * @return the discTitle
     */
    public String getDiscTitle() {
       return this.getMetadataValue(MetadataKey.METADATA_KEY.DISC_SUBTITLE.name());
    }
	
	/**
     * @return the trackNo
     */
    public String getTrackNo() {
         return this.getMetadataValue(MetadataKey.METADATA_KEY.TRACK_NO.name());
    }
	 /**
     * @return the file
     */
    public File getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(File file) {
        this.file = file;
    }  
	
	 /**
     * @return the offset in sectors
     */
    
    public Integer getOffset() {
        return offset;
    }

    /**
     * @param offset the offset in sectors to set
     */
    public void setOffset(int offset) {
        this.offset = offset;
    }
	
    /**
     * @return the track End position refferred to the Album (not the file).
     */
    
    public int getEnd() {
        return offset+length;
    }
	private ArrayList<CoverArt> orderCoverArt(ArrayList<CoverArt> coverArtList){

		HashMap< Integer, ArrayList<CoverArt> > scored = new HashMap<>();
		for (CoverArt coverArt : coverArtList){
			
			Integer sourceScore;
			Integer typeScore;
			
			String source = coverArt.getSource() != null ? coverArt.getSource() : "";
			String type = coverArt.getType()!= null ? coverArt.getType().toLowerCase().trim() : "";
			
			switch (source) {
				case "" :  sourceScore = 9;
						 break;
				case SOURCE_MUSICBRAINZ_COVER_ARCHIVE :  sourceScore = 5;
						 break;
				case SOURCE_IMAGE_FILE :  sourceScore = 3;
						 break;
				case SOURCE_EMBEDDED_FILE :  sourceScore = 0;
						 break;
				default :  sourceScore = 7;
			}
			switch (type) {
				case "" :  typeScore = 9;
						 break;
				case "front" :  typeScore = 5;
						 break;
				case "cover (front)" :  typeScore = 3;
						 break;
				case "cover" :  typeScore = 0;
						 break;
				default :  typeScore = 7;
			}
			
			Integer score = sourceScore*10+typeScore;
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
	
	
    /*
	* Returns the value stored in the current track for an album level metadata.
	* Different from getMetadataFromTrack, becouse it uses album level aliases
	* before.
	*/
    protected String getAlbumMetadataFromTrack(String key){
	
		String value="";
		for (Metadata  metadata : metadataList){
            
            if (metadata.getKey().equals(key)){
				value = metadata.getValue();
				if (!value.isEmpty()){return value;}
				break;
            }
           
        }
		ArrayList<String> aliases = getAlbumLevelMetadataAliases(key);

		// WARNING: it keeps only the first found.
		for (String alias : aliases){
            
            value = getMetadataFromTrack(alias);
            if (!value.isEmpty()){return value;}
        }
        aliases = getTrackLevelMetadataAliases(key);

		// WARNING: it keeps only the first found.
		for (String alias : aliases){
            
            value = getMetadataFromTrack(alias);
            if (!value.isEmpty()){return value;}
        }
        return "";
	}
	/*
	* Returns the value stored in the current track for a track level metadata.
	* Different from getAlbumMetadataFromTrack, becouse it uses track level aliases
	* only.
	*/
    protected String getMetadataFromTrack(String key){
        
		String value="";
		for (Metadata  metadata : metadataList){
            
            if (metadata.getKey().equals(key)){
				value = metadata.getValue();
				if (!value.isEmpty()){return value;}
            }
           
        }
		ArrayList<String> aliases = getTrackLevelMetadataAliases(key);

		// WARNING: it keeps only the first found.
		for (String alias : aliases){
            
            value = getMetadataFromTrack(alias);
            if (!value.isEmpty()){return value;}
        }
        return "";
    }
	/*
	* Returns the value stored in the current track for a track level metadata or,
	* if not found, the one stored at album level.
	*/
	protected  String getMetadataValue(String key){
	
		String value=getMetadataFromTrack(key);
		if (!value.isEmpty()){return value;}
		
		if (album == null) return "";
        value = ((AlbumDefaultImpl)album).getMetadataValueFromAlbum(key);
        
        return value;
	}
	/*
	* Returns the values stored in the current track for a track level metadata
	*/
	protected ArrayList<String> getMetadataValuesFromTrack(String key){
        
		ArrayList<String> values= new ArrayList<>();
		for (Metadata  metadata : metadataList){
            
            if (metadata.getKey().equals(key)){
				values = metadata.getValues();
				if (!values.isEmpty()){return values;}
            }
           
        }
		ArrayList<String> aliases = getTrackLevelMetadataAliases(key);
       
		for (String alias : aliases){
            
            values = getMetadataValuesFromTrack(alias);
            if (!values.isEmpty()){return values;}
        }
        return values;
    }

}