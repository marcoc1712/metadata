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
import com.mc2.audio.metadata.API.Medium;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataKey;
import com.mc2.audio.metadata.API.MetadataRow;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.Track;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mc2.audio.metadata.API.Album.UNKNOWN_DISC_NO;
import static com.mc2.audio.metadata.API.Album.UNKNOWN_DISC_TOT;
import static com.mc2.audio.metadata.API.Album.UNKNOWN_MEDIA_TYPE;
import static com.mc2.audio.metadata.API.Metadata.SEPARATOR;
import static com.mc2.audio.metadata.API.MetadataKey.getAlbumLevelMetadataAliases;

/**
 *
 * @author marco
 */

public abstract class AbstractAlbum implements Album{

	private final ArrayList<? extends AbstractTrack> trackList;
    private final ArrayList<Metadata> metadataList;
    protected ArrayList<CoverArt> coverArtList;
	
	protected ArrayList<? extends Track>singleTracksList;
	protected ArrayList<StatusMessage> messageList;
	
	
	private ArrayList<GenericMedium> mediaList = new ArrayList<>();

	private Integer totalLength = 0;
	private final String mediaString;
	private final MetadataTable metadataTable;
	
	protected String  format;
	protected Integer sampleRate;
	protected Integer bitsPerSample;
	protected String  channels;
	protected Boolean isLossless;
	protected Boolean isVariableBitRate;
	protected Long    bitRate;

	public AbstractAlbum(	ArrayList<Metadata> metadataList, 
                            ArrayList<? extends AbstractTrack> trackList) {

        this.metadataList = metadataList;  
		this.trackList = initTrackList(trackList);
		
        this.mediaList = initMedia(this.trackList);
		this.mediaString = initMediaString(this.mediaList);
		this.messageList =new ArrayList<>();
		this.metadataTable = new MetadataTable(this);
    }

    @Override
    public abstract String getId();
	
	@Override
    public String getAlbum() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.ALBUM.name());
	}
	
	@Override
    public String getAlbumArtist(){
        return this.getMetadataValue(MetadataKey.METADATA_KEY.ALBUM_ARTIST.name());
    }
	
	@Override
    public String getAlbumComposer(){
        return this.getMetadataValue(MetadataKey.METADATA_KEY.COMPOSER.name());
    }
	
	@Override
    public String getTrackLevelComposers(){
		return this.getMetadataValueFromTracks(MetadataKey.METADATA_KEY.COMPOSER.name()); 
    }
	
	@Override
    public String getComposers(){
		return this.getMetadataValueFromAlbumAndTracks(MetadataKey.METADATA_KEY.COMPOSER.name());
    }
	
	@Override
    public String getGenre(){
        return this.getMetadataValue(MetadataKey.METADATA_KEY.GENRE.name());
    }
    
    @Override
    public String getDate(){
        return this.getMetadataValue(MetadataKey.METADATA_KEY.DATE.name());
    }
    
    @Override
    public String getCountry(){
        return this.getMetadataValue(MetadataKey.METADATA_KEY.COUNTRY.name());
    }
	
	@Override
    public String getLabel(){
        return this.getMetadataValue(MetadataKey.METADATA_KEY.LABEL.name());
    }
    
    @Override
    public String getCatalogNo(){
        return this.getMetadataValue(MetadataKey.METADATA_KEY.CATALOG_NO.name());
    }
	
	@Override
	public String getDisc(){
        return this.getMetadataValue(MetadataKey.METADATA_KEY.DISC_NO.name());
    }
	@Override
    public String getTotalDiscs(){
        return this.getMetadataValue(MetadataKey.METADATA_KEY.DISC_TOTAL.name());
    }
	@Override
	public String getDiscTitle(){
        return this.getMetadataValue(MetadataKey.METADATA_KEY.DISC_SUBTITLE.name());
    }
	
	@Override
    public String getMbReleaseId(){
        return this.getMetadataValue(MetadataKey.METADATA_KEY.MUSICBRAINZ_RELEASEID.name());
    }

	@Override
    public String getFormat(){
		return format;
	};
	
	@Override
	public Integer getSampleRate() {
		return sampleRate;
	}
	
	@Override
	public Integer getBitsPerSample() {
		return bitsPerSample;
	}

	@Override
	public String getChannels() {
		return channels;
	}
	
	@Override
    public Boolean isVariableBitRate(){
		return isVariableBitRate;
	};
	
	@Override
    public Long getBitRate(){
		return bitRate;
	};
	
	@Override
	public Boolean isLossless() {
		return isLossless;
	}
	
	@Override
	public Boolean isHiRes(){
		
		if (getBitsPerSample() != null && getBitsPerSample() >16) return true;
		if (getSampleRate() != null && getSampleRate() > 44100) return true;

		return false;
	}

	@Override
	public CoverArt getCoverArt(){
		
		if (coverArtList == null || coverArtList.isEmpty()) {return null;}
		return coverArtList.get(0);
	}

	@Override
	public ArrayList<CoverArt> getcoverArtList() {
        return coverArtList;
    }
	
	@Override
	public String getMedia(){
		return mediaString;
	}
	
	@Override
    public Integer getTotalLength() {
        return totalLength;
    }
	
	@Override
	public ArrayList<? extends Medium> getMediaList() {
		return mediaList;
	}
	
    @Override
    public ArrayList<? extends Track> getTrackList() {
        return trackList;
    }
	
	@Override
	public ArrayList<? extends Track> getSingleTrackList() {
		return singleTracksList;
	}
    	
	public MetadataTable getMetadataTable(){
		 return metadataTable;
	}
   
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
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.CREDITS);
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
    public ArrayList<? extends MetadataRow> getRecordingDescriptorMetadataList() {
		
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.RECORDING_DESCRIPTOR);
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
    public ArrayList<? extends MetadataRow> getMetadaWithNoCategory() {
		
		return metadataTable.getMetadaWithNoCategory();
    }
	protected List< ? extends MetadataRow > getMetadataRows(){
		
		return metadataTable.getMetadataRows();
	}
	
    @Override
    public abstract ArrayList<RawKeyValuePairSource> getRawKeyValuePairSources();
	
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

    @Override
    public ArrayList<StatusMessage> getMessageList() {
        return messageList;
    }
	
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
            
			String trackValue = cleanMetadada(
				((AbstractTrack)track).getAlbumMetadataFromTrack(key)
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
			
			for (String value : ((AbstractTrack)track).getMetadataValuesFromTrack(key)){

				if (!values.contains(value)){

					values.add(value);
				}
			}	
			
		}
		return values;
	}
	
	private String initMediaString(ArrayList<GenericMedium> media){
		
		String out="";
				
		Map<String, Integer> mediaMap = new HashMap<>();
		
		for (GenericMedium medium : media){

			Integer count = mediaMap.get(medium.getType());
			if (count == null || count < 0){count = 0;}
			count ++;
			mediaMap.put(medium.getType(), count);	
		}
		
		Object[] keys = mediaMap.keySet().toArray();
		Arrays.sort(keys);

		for (Object key : keys){

			int count = mediaMap.get((String)key);
			String type = ((String)key).trim().isEmpty() ? UNKNOWN_MEDIA_TYPE : ((String)key).trim();

			if (keys.length == 1 && count < 2 && 
				type.equals(UNKNOWN_MEDIA_TYPE) && getDisc().isEmpty() && getTotalDiscs().isEmpty()){
				
				return "";
			}
			if (keys.length == 1 && count < 2 && 
				type.equals(UNKNOWN_MEDIA_TYPE) && getDisc().isEmpty() && !getTotalDiscs().isEmpty()){
				
				return UNKNOWN_MEDIA_TYPE+" "+UNKNOWN_DISC_NO+" of "+getTotalDiscs();
			}
			
			if (keys.length == 1 && count < 2 && !getDisc().isEmpty()){
				
				Integer disc=0;
				String discNo;
				
				try {
					
					disc = Integer.parseInt(getDisc());
					if (  disc == 1 &&  type.equals(UNKNOWN_MEDIA_TYPE) && getTotalDiscs().isEmpty()) {
					
						return "";
					
					}
					
					discNo=disc+"";
					
				} catch (java.lang.NumberFormatException ex){
					
					discNo=UNKNOWN_DISC_NO;
				}
								
				String value = type+" "+discNo;
	
				if (!getTotalDiscs().isEmpty()){

					value = value +" of "+ getTotalDiscs();
				} else {
					value = value +" of "+ UNKNOWN_DISC_TOT;
				}

				return value;

			}
			
			if (!out.isEmpty()){
				out= out+" + ";
			}
			if (type.equals(UNKNOWN_MEDIA_TYPE) && count > 1){
				type = type+"s";
			}
			out = out+count+" "+type;
		}
		
		return out;
	}
    private ArrayList<GenericMedium> initMedia(ArrayList<? extends AbstractTrack> trackList){
		
		Map<String, GenericMedium> mediaMap = new HashMap<>();

        for (Track track : trackList){

			String mediumId = track.getMedium();
			GenericMedium medium  = mediaMap.get(mediumId);
			
			if (medium == null) {
				
				medium = new GenericMedium(mediumId, track.getMediaType(), track.getDiscNo(), track.getDiscTitle());
				mediaMap.put(mediumId, medium);
			}
			//tracks are already ordered by medium and TrackNo.
			medium.addTrack(track);
        }
        
		Object[] keys = mediaMap.keySet().toArray();
		Arrays.sort(keys);
		
		ArrayList<GenericMedium> out = new ArrayList<>();
		
		for (Object key : keys){
			GenericMedium medium = mediaMap.get((String)key);
			medium.setIndex(out.size());
			out.add(medium);
			
			if ( keys.length > 1 && key.equals("") ){
			
				GenericStatusMessage statusMessage = new GenericStatusMessage(
                    messageList.size()+1,
                    StatusMessage.Severity.WARNING, 
                    "One or more tracks miss both media type and number");
            
					messageList.add(statusMessage);
			}
		}	
		return out;
    }
	private ArrayList<? extends AbstractTrack> initTrackList(ArrayList<? extends AbstractTrack> trackList){
		
		
		String _format= null;
		Integer _sampleRate= null;
		Integer _bitsPerSample= null;
		String  _channels= null;
		Boolean _isLossless= null;
		Boolean _isVariableBitRate= null;
		Long _bitRate= null;

        Integer index = 0;
        for (AbstractTrack track : trackList){
            
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
			
			totalLength= totalLength+track.getLength();
	
			track.setAlbum(this);
			track.setIndex(index);
			
            index++;
        }
		
		format= _format;
		sampleRate = _sampleRate;
		bitsPerSample = _bitsPerSample;
		channels  = _channels;
		isLossless = _isLossless != null && _isLossless;
		isVariableBitRate = _isVariableBitRate;
		bitRate = _bitRate;
		
       return trackList;
    }
}
