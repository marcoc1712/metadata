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
import java.io.File;
import java.util.ArrayList;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataKey;
import static com.mc2.audio.metadata.API.MetadataKey.getAlbumLevelMetadataAliases;
import static com.mc2.audio.metadata.API.MetadataKey.getTrackLevelMetadataAliases;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.Track;
import com.mc2.util.miscellaneous.CalendarUtils;

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
    private final ArrayList<RawKeyValuePairSource> rawKeyValuePairSources; 
    private final ArrayList<StatusMessage> messageList;
    
    public TrackDefaultImpl(String  trackId, ArrayList<Metadata> metadataList) {
        this.trackId = trackId;
        this.metadataList = metadataList;
        this.rawKeyValuePairSources =new ArrayList<>();
        this.messageList =new ArrayList<>();
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
     * @return the url
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
	 * @param playListUrl the playListUrl to set
	 */
	public void setPlayListUrl(String playListUrl) {
		this.playListUrl = playListUrl;
	}
	/**
     * @return the Album url
     */
    @Override
    public String getPlayListUrl() {
        return playListUrl;
    }
	/**
	 * @param playListIndex the playListIndex to set
	 */
	public void setPlayListIndex(Integer playListIndex) {
		this.playListIndex = playListIndex;
	}
   
	@Override
	public Integer getPlayListIndex(){
		return playListIndex;
	}
	
	/**
	 * @return the format
	 */
	@Override
	public String getFormat() {
		return format;
	}

	/**
	 * @return the sampleRate
	 */
	@Override
	public Integer getSampleRate() {
		return sampleRate;
	}

	/**
	 * @return the bitsPerSample
	 */
	@Override
	public Integer getBitsPerSample() {
		return bitsPerSample;
	}

	/**
	 * @return the channels
	 */
	@Override
	public String getChannels() {
		return channels;
	}
	/**
     * @return if the sampling bitRate is variable or constant
     */
	@Override
    public boolean isVariableBitRate(){
		return isVariableBitRate;
	};
	
	/**
     * @return bitRate as a number, this is the amount of kilobits of data sampled per second
     */
	@Override
    public long getBitRate(){
		return bitRate;
	};
	/**
	 * @return the isLossless
	 */
	@Override
	public Boolean isLossless() {
		return isLossless;
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
	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @param sampleRate the sampleRate to set
	 */
	public void setSampleRate(Integer sampleRate) {
		this.sampleRate = sampleRate;
	}

	/**
	 * @param bitsPerSample the bitsPerSample to set
	 */
	public void setBitsPerSample(Integer bitsPerSample) {
		this.bitsPerSample = bitsPerSample;
	}

	/**
	 * @param channels the channels to set
	 */
	public void setChannels(String channels) {
		this.channels = channels;
	}
	/**
	 * @param isLossless the isLossless to set
	 */
	public void setLossless(Boolean isLossless) {
		this.isLossless = isLossless;
	}
	/**
	 * @param isVariableBitRate the VariableBitRate to set
	 */
	public void setVariableBitRate(Boolean isVariableBitRate) {
		this.isVariableBitRate = isVariableBitRate;
	}
	/**
	 * @param bitRate the bitrate to set
	 */
	public void setBitRate(Long bitRate) {
		this.bitRate = bitRate;
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
     * @return the trackId
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
     * @return the length in sectors.
     */
    @Override
    public int getLength() {
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
     * @return the metadataList
     */
    @Override
    public ArrayList<Metadata> getMetadataList() {
        return metadataList;
    }

    /**
     * @return the audioFileList
     */
    @Override
    public ArrayList<RawKeyValuePairSource> getRawKeyValuePairSources() {
        return rawKeyValuePairSources;
    }
    /**
     * add a File to the audioFileList
     * @param rawKeyValuePairSource
     */
    public void addRawKeyValuePairSource(RawKeyValuePairSource rawKeyValuePairSource) {
        
        if (!rawKeyValuePairSources.contains(rawKeyValuePairSource)){
            rawKeyValuePairSources.add(rawKeyValuePairSource);
        }   
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
   // to be moved by Disc.
	
	 /**
     * @return the offset in sectors
     */
    
    public int getOffset() {
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
    
    protected String getAlbumMetadataFromTrack(String key){
	
		String value="";
		for (Metadata  metadata : metadataList){
            
            if (metadata.getKey().equals(key)){
				value = metadata.getValue();
				if (!value.isEmpty()){return value;}
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
	
	protected  String getMetadataValue(String key){
	
		String value=getMetadataFromTrack(key);
		if (!value.isEmpty()){return value;}
		
        value = ((AlbumDefaultImpl)album).getMetadataValueFromAlbum(key);
        
        return value;
	}
}