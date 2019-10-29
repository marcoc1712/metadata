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

import com.mc2.audio.metadata.API.CoverArt;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataKey;
import com.mc2.audio.metadata.API.MetadataRow;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.Track;
import com.mc2.util.miscellaneous.CalendarUtils;
import java.util.ArrayList;

import static com.mc2.audio.metadata.API.MetadataKey.getAlbumLevelMetadataAliases;
import static com.mc2.audio.metadata.API.MetadataKey.getTrackLevelMetadataAliases;

/**
 *
 * @author marco
 */
public abstract class AbstractTrack implements Track {

	protected Integer offset = 0;
	protected Integer length = 0;
	protected String url;
	protected String playListUrl;
	protected Integer playListIndex;
	protected AbstractAlbum album;
	protected Integer index;
	protected final ArrayList<Metadata> metadataList;
	protected ArrayList<CoverArt> coverArtList;
	protected final ArrayList<RawKeyValuePairSource> rawKeyValuePairSources;
	protected final ArrayList<StatusMessage> messageList;
	protected final MetadataTable metadataTable;
	
	protected String format;
	protected Integer sampleRate;
	protected Integer bitsPerSample;
	protected String channels;
	protected Boolean isVariableBitRate;
	protected Long bitRate;
	protected Boolean isLossless;

	public AbstractTrack(ArrayList<Metadata> metadataList) {
		
        this.metadataList = metadataList;
        this.rawKeyValuePairSources =new ArrayList<>();
        this.messageList =new ArrayList<>();
		
		this.metadataTable = new MetadataTable(this);
    }
	
	@Override
	public abstract String getTrackId();

	@Override
	public String getTrackNumber() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.TRACK_NO.name());
	}
	
	@Override
	public String getMediaType() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.MEDIA.name());
	}
	
	@Override
	public String getMediumNumber() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.DISC_NO.name());
	}
	
	@Override
	public String getMediumIdentification() {
		String mediaType = getMediaType();
		String no = getMediumNumber();
		if (!mediaType.isEmpty() && !no.isEmpty()) {
			return mediaType + " " + no;
		} else if (!no.isEmpty()) {
			return no;
		}
		return "";
	}

	@Override
	public String getMediaTitle() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.DISC_SUBTITLE.name());
	}
	
	@Override
	public AbstractAlbum getAlbum() {
		return album;
	}
	
	@Override
	public Integer getIndex() {
		return index;
	}
	
	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getPlayListUrl() {
		return playListUrl;
	}

	@Override
	public Integer getPlayListIndex() {
		return playListIndex;
	}
	
	@Override
	public String getArtistNames() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.ARTIST.name());
	}
	
	@Override
	public String getComposerNames() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.COMPOSER.name());
	}
	
	@Override
	public String getWorkTitle() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.WORK.name());
	}
	
	@Override
	public String getTitle() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.TITLE.name());
	}

	@Override
	public String getVersion() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.VERSION.name());
	}

	@Override
	public ArrayList<CoverArt> getCoverArtList() {
		return coverArtList;
	}

	@Override
	public CoverArt getCoverArt() {
		if (coverArtList == null || coverArtList.isEmpty()) {
			return null;
		}
		return coverArtList.get(0);
	}
	
	@Override
	public String getFormat() {
		return format;
	}

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
	public Boolean isVariableBitRate() {
		return isVariableBitRate;
	}
	
	@Override
	public Long getBitRate() {
		return bitRate;
	}
	
	@Override
	public Boolean isLossless() {
		return isLossless;
	}
	
	@Override
	public Boolean isHiRes() {
		if (getBitsPerSample() != null && getBitsPerSample() > 16) {
			return true;
		}
		if (getSampleRate() != null && getSampleRate() > 44100) {
			return true;
		}
		return false;
	}	

	@Override
	public Integer getLength() {
		return length;
	}
	
	@Override
	public Long getDuration(){
		return this.getDurationInMillis()*1000;
    }

	@Override
	public Long getDurationInMillis() {
		if (this.getLength() == null) return (long)0;
		return CalendarUtils.getMilliseconds(getLength());
	}
	
	@Override
	public String getDurationString() {
		return CalendarUtils.getTimeString(getDurationInMillis());
	}
	
	@Override
	public Integer getOffset() {
		return offset;
	}

	@Override
	public Integer getEnd() {
		return offset + length;
	}
	
	@Override
	public String getIsrc(){
		return this.getMetadataValue(MetadataKey.METADATA_KEY.ISRC.name());
	}
	
	@Override
    public String getCopyright() {
        return this.getMetadataValue(MetadataKey.METADATA_KEY.COPYRIGHT.name());
    }
	
	 @Override
	public Boolean getParentalWarning() {
		String pw = this.getMetadataValue(MetadataKey.METADATA_KEY.PARENTAL_WARNING.name());
        if (pw.isEmpty()|| 
			pw.toUpperCase().equals("FALSE") || 
			pw.toUpperCase().equals("NO") ||
			pw.equals("0") ||
			pw.equals(" ")){return false;}
		return true;
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
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.MISCELLANEA);
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
	public ArrayList<? extends MetadataRow> getAlbumInfoMetada() {
		return metadataTable.getMetadaPerCategory(MetadataKey.METADATA_CATEGORY.ALBUM_INFO);
	}

	@Override
	public ArrayList<? extends MetadataRow> getMetadaWithNoCategory() {
		return metadataTable.getMetadaWithNoCategory();
	}
	
	@Override
	public ArrayList<RawKeyValuePairSource> getRawKeyValuePairSources() {
		return rawKeyValuePairSources;
	}
	
	@Override
	public StatusMessage.Severity getStatus() {
		int index = 0;
		StatusMessage.Severity status = StatusMessage.Severity.OK;
		for (StatusMessage statusMessage : getMessageList()) {
			if (statusMessage.getSeverityIndex() > index) {
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
	
	// -- PUBLIC SETTERS --
	
	/**
	 * @param album the album to set
	 */
	public void setAlbum(AbstractAlbum album) {
		this.album = album;
	}
	
	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
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
	 * @param playListIndex the playListIndex to set
	 */
	public void setPlayListIndex(Integer playListIndex) {
		this.playListIndex = playListIndex;
	}
	
	/**
	 * @param coverArtList the coverArtList to set
	 */
	public void setCoverArtList(ArrayList<CoverArt> coverArtList) {
		this.coverArtList = coverArtList;
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
	
	/**
	 * @param isLossless the isLossless to set
	 */
	public void setLossless(Boolean isLossless) {
		this.isLossless = isLossless;
	}
	
	/**
	 * @param length the length in sectors to set
	 */
	public void setLength(int length) {
		this.length = length;
	}
	
	/**
	 * @param offset the offset in sectors to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	/**
	 * add a message to the messageList
	 * @param message
	 */
	public void addStatusMessage(StatusMessage message) {
		if (!messageList.contains(message)) {
			messageList.add(message);
		}
	}

	/**
	 * add a souce to the list
	 * @param rawKeyValuePairSource
	 */
	public void addRawKeyValuePairSource(RawKeyValuePairSource rawKeyValuePairSource) {
		if (!rawKeyValuePairSources.contains(rawKeyValuePairSource)) {
			rawKeyValuePairSources.add(rawKeyValuePairSource);
		}
	}
	
	// -- end OF pUBLIC METHODS --
	
	/**
	 * @return the metadataTable;
	 */
	
	protected MetadataTable getMetadataTable() {
		return metadataTable;
	}
	
	
	/*
	 * Returns the value stored in the current track for an album level metadata.
	 * Different from getMetadataFromTrack, becouse it uses album level aliases
	 * before.
	 */
	
	protected String getAlbumMetadataFromTrack(String key) {
		String value = "";
		for (Metadata metadata : metadataList) {
			if (metadata.getKey().equals(key)) {
				value = metadata.getValue();
				if (value != null && !value.isEmpty()) {
					return value;
				}
				break;
			}
		}
		ArrayList<String> aliases = getAlbumLevelMetadataAliases(key);
		// WARNING: it keeps only the first found.
		for (String alias : aliases) {
			value = getMetadataFromTrack(alias);
			if (value != null && !value.isEmpty()) {
				return value;
			}
		}
		aliases = getTrackLevelMetadataAliases(key);
		// WARNING: it keeps only the first found.
		for (String alias : aliases) {
			value = getMetadataFromTrack(alias);
			if (value != null && !value.isEmpty()) {
				return value;
			}
		}
		return "";
	}

	/*
	 * Returns the value stored in the current track for a track level metadata.
	 * Different from getAlbumMetadataFromTrack, becouse it uses track level aliases
	 * only.
	 */
	protected String getMetadataFromTrack(String key) {
		String value = "";
		for (Metadata metadata : metadataList) {
			if (metadata.getKey().equals(key)) {
				value = metadata.getValue();
				if (value != null && !value.isEmpty()) {
					return value;
				}
			}
		}
		ArrayList<String> aliases = getTrackLevelMetadataAliases(key);
		// WARNING: it keeps only the first found.
		for (String alias : aliases) {
			value = getMetadataFromTrack(alias);
			if (value != null && !value.isEmpty()) {
				return value;
			}
		}
		return "";
	}

	/*
	 * Returns the value stored in the current track for a track level metadata or,
	 * if not found, the one stored at album level.
	 */
	protected String getMetadataValue(String key) {
		String value = getMetadataFromTrack(key);
		if (value != null && !value.isEmpty()) {
			return value;
		}
		if (album == null) {
			return "";
		}
		value = album.getMetadataValueFromAlbum(key);
		return value;
	}

	/*
	 * Returns the values stored in the current track for a track level metadata
	 */
	protected ArrayList<String> getMetadataValuesFromTrack(String key) {
		ArrayList<String> values = new ArrayList<>();
		for (Metadata metadata : metadataList) {
			if (metadata.getKey().equals(key)) {
				values = metadata.getValues();
				if (values != null && !values.isEmpty()) {
					return values;
				}
			}
		}
		ArrayList<String> aliases = getTrackLevelMetadataAliases(key);
		for (String alias : aliases) {
			values = getMetadataValuesFromTrack(alias);
			if (values != null &&!values.isEmpty()) {
				return values;
			}
		}
		return values;
	}

}
