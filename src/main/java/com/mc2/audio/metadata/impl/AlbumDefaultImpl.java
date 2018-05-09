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
import static com.mc2.audio.metadata.API.CoverArt.SOURCE_EMBEDDED_FILE;
import static com.mc2.audio.metadata.API.CoverArt.SOURCE_IMAGE_FILE;
import static com.mc2.audio.metadata.API.CoverArt.SOURCE_MUSICBRAINZ_COVER_ARCHIVE;
import com.mc2.audio.metadata.API.Medium;
import com.mc2.audio.metadata.API.Metadata;
import static com.mc2.audio.metadata.API.Metadata.SEPARATOR;
import com.mc2.audio.metadata.API.MetadataKey.METADATA_CATEGORY;
import com.mc2.audio.metadata.API.MetadataKey.METADATA_KEY;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.Track;
import com.mc2.audio.metadata.source.cue.file.CueFile;
import com.mc2.audio.metadata.source.tags.file.AudioFile;

import static com.mc2.audio.metadata.API.MetadataKey.getAlbumLevelMetadataAliases;
import com.mc2.audio.metadata.API.MetadataRow;
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
	private final String mediaString;

	private String  format;
	private Integer sampleRate;
	private Integer bitsPerSample;
	private String  channels;
	private Boolean isLossless;
	private Boolean isVariableBitRate;
	private	Long    bitRate;
	
	private final MetadataTable metadataTable;
	
	private final  ArrayList<TrackDefaultImpl> singleTracksList;
	
    public AlbumDefaultImpl(String url,
                            ArrayList<CoverArt> coverArtList,
                            ArrayList<Metadata> metadataList, 
                            ArrayList<TrackDefaultImpl> trackList, 
                            ArrayList<File> fileList, 
                            ArrayList<CueFile> cueFileList, 
                            ArrayList<AudioFile> audioFileList, 
                            ArrayList<File> imageFileList,
                            ArrayList<StatusMessage> messageList,
							ArrayList<TrackDefaultImpl> singleTracksList) {
        
        this.url = url;
        
        this.metadataList = metadataList;  
        this.fileList = fileList;
        this.cueFileList = cueFileList;
        this.audioFileList = audioFileList;
        this.imageFileList = imageFileList;
        this.messageList = messageList;
        
		this.trackList = initTrackList(trackList);
        this.mediaList = initMedia(this.trackList);
		this.mediaString = initMediaString(this.mediaList);

		this.metadataTable = new MetadataTable(this);
		this.singleTracksList = singleTracksList;
		
		this.coverArtList = this.orderCoverArt(coverArtList);

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

			if (keys.length == 1 && count < 2 && 
				type.isEmpty() && getTotalDiscs().isEmpty()){
				
				return "";
			}

			if (keys.length == 1 && count < 2 && !getDisc().isEmpty()){
				
				Integer disc=0;
				
				try {
					
					disc = Integer.parseInt(getDisc());
					if (  disc == 1 && getTotalDiscs().isEmpty()) {
					
					return "1x"+type;
					
					}
					if (type.isEmpty()) {

						type = "disc";

					}
					if (  disc > 0 ) {

						String value = type+" "+disc;

						value = value +" of "+ (getTotalDiscs().isEmpty() ? "?" :  getTotalDiscs());

						if (!getDiscTitle().trim().isEmpty()) value = value+" ("+getDiscTitle().trim()+")";

						return value;
					}
				
				} catch (java.lang.NumberFormatException ex){}
			}
			if (type.trim().isEmpty()) {
				
				type = "?";
				
			}
			if (!out.isEmpty()){
				out= out+" + ";
			}

			out = out+count+"x"+type;
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
		
		String _format= null;
		Integer _sampleRate= null;
		Integer _bitsPerSample= null;
		String  _channels= null;
		Boolean _isLossless= null;
		Boolean _isVariableBitRate= null;
		Long _bitRate= null;
	
        Integer index = 0;
        for (Track track : trackList){
            
			if (_format == null) _format = track.getFormat();
			else if (!_format.equals(track.getFormat())) _format="";
			
			if (_sampleRate == null) _sampleRate = track.getSampleRate();
			else if (!_sampleRate.equals(track.getSampleRate())) _sampleRate=0;
			
			if (_bitsPerSample == null) _bitsPerSample = track.getBitsPerSample();
			else if (!_bitsPerSample.equals(track.getBitsPerSample())) _bitsPerSample=0;
			
			if (_channels == null) _channels = track.getChannels();
			else if (!_channels.equals(track.getChannels())) _channels="";
			
			if (_isVariableBitRate == null) _isVariableBitRate = track.isVariableBitRate();
			else if (!_isVariableBitRate.equals(track.isVariableBitRate())) _isVariableBitRate=null;
			
			if (_bitRate == null) _bitRate = track.getBitRate();
			else if (!_bitRate.equals(track.getBitRate())) _bitRate=0L;
			
			if (_isLossless == null) _isLossless = track.isLossless();
			else if (!_isLossless.equals(track.isLossless())) _isLossless=false;

            if ( track instanceof TrackDefaultImpl){
                totalLength= totalLength+track.getLength();
				((TrackDefaultImpl)track).setAlbum(this);
                ((TrackDefaultImpl)track).setIndex(index);
			}
            index++;
        }
		
		format= _format;
		sampleRate = _sampleRate;
		bitsPerSample = _bitsPerSample;
		channels  = _channels;
		isLossless = _isLossless;
		isVariableBitRate = _isVariableBitRate;
		bitRate = _bitRate;
		
       return trackList;
    }
		 
    @Override
    public String getAlbum(){
        return this.getMetadataValue(METADATA_KEY.ALBUM.name());
    }
    
	@Override
    public String getMbReleaseId(){
        return this.getMetadataValue(METADATA_KEY.MUSICBRAINZ_RELEASEID.name());
    }

    @Override
    public String getAlbumArtist(){
        return this.getMetadataValue(METADATA_KEY.ALBUM_ARTIST.name());
    }
	@Override
    public String getAlbumComposer(){
        return this.getMetadataValue(METADATA_KEY.COMPOSER.name());
    }
    @Override
    public String getTrackLevelComposers(){
		return this.getMetadataValueFromTracks(METADATA_KEY.COMPOSER.name()); 
    }
	
	@Override
    public String getComposers(){
		return this.getMetadataValueFromAlbumAndTracks(METADATA_KEY.COMPOSER.name());
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
	
	@Override
	public String getDisc(){
        return this.getMetadataValue(METADATA_KEY.DISC_NO.name());
    }
	@Override
    public String getTotalDiscs(){
        return this.getMetadataValue(METADATA_KEY.DISC_TOTAL.name());
    }
	@Override
	public String getDiscTitle(){
        return this.getMetadataValue(METADATA_KEY.DISC_SUBTITLE.name());
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
	
	@Override
	public CoverArt getCoverArt(){
		
		if (coverArtList == null || coverArtList.isEmpty()) {return null;}
		return coverArtList.get(0);
	}
	
	 /**
     * @return the metadataTable;
     */
	
	public MetadataTable getMetadataTable(){
		 return metadataTable;
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
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.DESCRIPTION);
    }
	@Override
    public ArrayList<? extends MetadataRow> getCommentMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.COMMENT);
    }
	@Override
    public ArrayList<? extends MetadataRow> getAwardMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.AWARD);
    }
	@Override
    public ArrayList<? extends MetadataRow> getCollectionMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.COLLECTION);
    }
	@Override
    public ArrayList<? extends MetadataRow> getGoodiesMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.GOODIES);
    }
	@Override
    public ArrayList<? extends MetadataRow> getURLMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.URL);
    }
	@Override
    public ArrayList<? extends MetadataRow> getMusicDescriptorMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.MUSIC_DESCRIPTOR);
    }
	@Override
    public ArrayList<? extends MetadataRow> getCreditMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.CREDIT);
    }
	@Override
    public ArrayList<? extends MetadataRow> getOtherInvolvedPersonMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.OTHER_INVOLVED_PERSON);
    }
	@Override
    public ArrayList<? extends MetadataRow> getMediaDescriptorMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.MEDIA_DESCRIPTOR);
    }
	@Override
    public ArrayList<? extends MetadataRow> getWorkDescriptorMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.WORK_DESCRIPTOR);
    }
	@Override
    public ArrayList<? extends MetadataRow> getMiscellaneaMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.MISCELLANEA);
    }
	@Override
    public ArrayList<? extends MetadataRow> getRatingMetadataList() {
		
		return metadataTable.getMetadaPerCategory(METADATA_CATEGORY.RATING);
    }
	@Override
    public ArrayList<? extends MetadataRow> getMetadaWithNoCategory() {
		
		return metadataTable.getMetadaWithNoCategory();
    }
	/**
	 * @return the sampleRate
	 */
	@Override
	public Integer getSampleRate() {
		return sampleRate;
	}
	/**
     * @return the format
     */
	@Override
    public String getFormat(){
		return format;
	};
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
	
	@Override
    public ArrayList<? extends Track> getSingleTrackList() {
        return singleTracksList;
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
				case "cover (front)" :  typeScore = 3;
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
	
	// Protected from here
	
	 protected String getMetadataValue(String key){
		
		String out="";
		
		if (getMetadataValuesFromTracks(key).size() > 1){
			return "";
		}
		String albumValue=cleanMetadada(getMetadataValueFromAlbum(key));
		String tracksCommonValue =cleanMetadada(getMetadataValueFromTracksIfTheSame(key));
		
		
		
		if (albumValue.equals(tracksCommonValue)) return albumValue;
		if (albumValue.isEmpty()) return tracksCommonValue;
        return "";
        
    }
	 
	protected String getMetadataValueFromAlbum(String key){
		
		String out="";
		
		// first search at album level for the specific key.
        for (Metadata  metadata : metadataList){

            if (metadata.getKey().equals(key)){
                 out = cleanMetadada(metadata.getValue());
				 if (!out.isEmpty()){return out;}
            } 
        }
		
		// then  search at album level for key aliases 
		// WARNING: it keeps only the first found.
		
        ArrayList<String> aliases = getAlbumLevelMetadataAliases(key);
		for (String alias : aliases){
            
			for (Metadata  metadata : metadataList){

				if (metadata.getKey().equals(alias)){
					 out = cleanMetadada(metadata.getValue());
					 if (!out.isEmpty()){return out;}
				} 
			}
        }
		return cleanMetadada(out);
	}
	
	/*
	if all tracks store the same value for that key, returns the value, 
	blank instead.
	*/
    protected String getMetadataValueFromTracksIfTheSame(String key){
        
        String out = "";
        
        for (Track  track : this.getTrackList()){
            
            if (track  instanceof TrackDefaultImpl){
                
				String trackValue = cleanMetadada(
					((TrackDefaultImpl)track).getAlbumMetadataFromTrack(key)
				);
				
                if (out.isEmpty()){
                    
                    out= trackValue;
                } else {
                    
                    if (!out.equals(trackValue)){
                    
                        out = "";
                        break;
                    }
                }
            }  
        }
        return out;
    }
	protected String getMetadataValueFromTracks(String key){
		
		ArrayList<String> values= getMetadataValuesFromTracks(key);

		String out="";
		for (String value : values){
			value= value.trim();
            if (!out.isEmpty()){
                out=out+SEPARATOR+value;
            } else{
                out=value;
            }
        }
		return out;
	}
	
	// Private from here //
	
	protected String cleanMetadada(String metadata){
		
		if (metadata==null || metadata.isEmpty()) return "";
		String out="";
		ArrayList<String> list = new ArrayList<>(Arrays.asList(metadata.split(SEPARATOR)));
		ArrayList<String> newList=new ArrayList<>();
		
		for (String value : list){
		
			value= value.trim();
			
			if (!newList.contains(value)){
				newList.add(value);
			}
		}
		return String.join(SEPARATOR, newList);
	}

	protected String getMetadataValueFromAlbumAndTracks(String key){

		ArrayList<String> values=getMetadataValuesFromAlbumAndTracks(key);
		
		String out="";
		for (String value : values){
			value =  value;
			if (!out.isEmpty()){
                out=out+SEPARATOR+value;
            } else{
                out=value;
            }
        }
		return cleanMetadada(out);
		
	}
	
	protected ArrayList<String> getMetadataValuesFromAlbumAndTracks(String key){

		ArrayList<String> values=getMetadataValuesFromAlbum(key);
		
        for (String value : getMetadataValuesFromTracks(key)){
			
			if (!values.contains(value)){
				values.add(value);
			}

		}
        return values;
    }
	protected ArrayList<String> getMetadataValuesFromAlbum(String key){
        
		ArrayList<String> values= new ArrayList<>();
		for (Metadata  metadata : metadataList){
            
            if (metadata.getKey().equals(key)){
				values = metadata.getValues();
				if (!values.isEmpty()){return values;}
            }
           
        }
		ArrayList<String> aliases = getAlbumLevelMetadataAliases(key);
       
		for (String alias : aliases){
            
            values = getMetadataValuesFromAlbum(alias);
            if (!values.isEmpty()){return values;}
        }
        return values;
    }

	protected ArrayList<String> getMetadataValuesFromTracks(String key){
		
		ArrayList<String> values= new ArrayList<>();
        
        for (Track  track : this.getTrackList()){
			if (track  instanceof TrackDefaultImpl){
				
				for (String value : ((TrackDefaultImpl)track).getMetadataValuesFromTrack(key)){
					
					if (!values.contains(value)){
					
						values.add(value);
					}
				}	
			}
		}
		return values;
	}

}
