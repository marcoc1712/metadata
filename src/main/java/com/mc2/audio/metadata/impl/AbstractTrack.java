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
	
	/**
	 *  @return the trackId (normally discNo.trackNo)
	 */
	@Override
	public abstract String getTrackId();
	
	/**
	 * @return the trackNo
	 */
	@Override
	public String getTrackNo() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.TRACK_NO.name());
	}
	
	@Override
	public AbstractAlbum getAlbum() {
		return album;
	}

	/**
	 * @param album the album to set
	 */
	public void setAlbum(AbstractAlbum album) {
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
	public Integer getPlayListIndex() {
		return playListIndex;
	}

	/**
	 * @param playListIndex the playListIndex to set
	 */
	public void setPlayListIndex(Integer playListIndex) {
		this.playListIndex = playListIndex;
	}
	/**
     * @return the Work
     */
	@Override
	public abstract String getWork();
	
	/**
     * @return the title
     */
	@Override
	public abstract String getTitle();
	
	/**
     * @return the version of the recording (i.e. recording date).
     */
	@Override
	public abstract String getVersion();

	@Override
	public String getArtist() {
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
	public CoverArt getCoverArt() {
		if (coverArtList == null || coverArtList.isEmpty()) {
			return null;
		}
		return coverArtList.get(0);
	}
	
	@Override
	public abstract String getMediaType();

	@Override
	public String getMedium() {
		String mediaType = getMediaType();
		String no = getDiscNo();
		if (!mediaType.isEmpty() && !no.isEmpty()) {
			return mediaType + " " + no;
		} else if (!no.isEmpty()) {
			return no;
		}
		return "";
	}

	/**
	 * @return the discNo
	 */
	@Override
	public String getDiscNo() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.DISC_NO.name());
	}

	/**
	 * @return the discTitle
	 */
	@Override
	public String getDiscTitle() {
		return this.getMetadataValue(MetadataKey.METADATA_KEY.DISC_SUBTITLE.name());
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
	public Boolean isVariableBitRate() {
		return isVariableBitRate;
	}
	
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
	public Long getBitRate() {
		return bitRate;
	}
	
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
	public abstract Boolean isHiRes();

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
	/** @return length in msec */
	@Override
	public Long getLengthInMillis() {
		return CalendarUtils.getMilliseconds(getLength());
	}

	/** @return length string */
	@Override
	public String getLengthString() {
		return CalendarUtils.getTimeString(getLengthInMillis());
	}
	
	/**
	 * @return the offset in sectors
	 */
	@Override
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
	@Override
	public Integer getEnd() {
		return offset + length;
	}
	/**
	* Get the ISRC code of this track. Null signifies that it has not been set.
	* @return The ISRC code of this track. Null signifies that it has not been set.
	*/
	@Override
	public abstract String getIsrcCode();
	/**
     * @return the copyright
     */
	@Override
    public abstract String getCopyright();
	
	/**
     * @return the parental warning
     */
	@Override
    public abstract Boolean getParentalWarning();
	
	/**
	 * @return the metadataTable;
	 */
	public MetadataTable getMetadataTable() {
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
	
	/**
	 * @return the sources of Raw Key Value Pairs, like tags or cue files commands.
	 */
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
				if (!value.isEmpty()) {
					return value;
				}
				break;
			}
		}
		ArrayList<String> aliases = getAlbumLevelMetadataAliases(key);
		// WARNING: it keeps only the first found.
		for (String alias : aliases) {
			value = getMetadataFromTrack(alias);
			if (!value.isEmpty()) {
				return value;
			}
		}
		aliases = getTrackLevelMetadataAliases(key);
		// WARNING: it keeps only the first found.
		for (String alias : aliases) {
			value = getMetadataFromTrack(alias);
			if (!value.isEmpty()) {
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
				if (!value.isEmpty()) {
					return value;
				}
			}
		}
		ArrayList<String> aliases = getTrackLevelMetadataAliases(key);
		// WARNING: it keeps only the first found.
		for (String alias : aliases) {
			value = getMetadataFromTrack(alias);
			if (!value.isEmpty()) {
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
		if (!value.isEmpty()) {
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
				if (!values.isEmpty()) {
					return values;
				}
			}
		}
		ArrayList<String> aliases = getTrackLevelMetadataAliases(key);
		for (String alias : aliases) {
			values = getMetadataValuesFromTrack(alias);
			if (!values.isEmpty()) {
				return values;
			}
		}
		return values;
	}
	
}
