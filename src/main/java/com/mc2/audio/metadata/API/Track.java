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
package com.mc2.audio.metadata.API;

import java.util.ArrayList;

/**
 *
 * @author marco
 */
public interface Track {
	
	/**
     * @return the trackId (normally discNo.trackNo)
     */
    String getTrackId();
	
	/**
	 * @return the trackNo
	 */
	String getTrackNo();
	
	/**
     * @return the album.
     */
	
	Album getAlbum();
	
	/**
     * @return the absolute Index position of the track in the album, starting from 0.
     */
    Integer getIndex();
	
	/**
     * @return the playback url of the track
     */
    String getUrl();
	
	/**
     * @return the playback playlist url 
     */
	String getPlayListUrl();
	
	/**
     * @return the playback Playlist index position of the track in the playlist,
	 * stating from 0.
     */
    Integer getPlayListIndex();

	/**
     * @return the Work
     */
	String getWork();
	
	/**
     * @return the title
     */
    String getTitle();
	
	/**
     * @return the version of the recording (i.e. recording date).
     */
	String getVersion();
	
    /**
     * @return the artist
    */
    String getArtist();

	/**
	 *@return the coverArt List
	 */
	ArrayList<CoverArt> getCoverArtList();
	
	/**
	 *@return the most representative coverArt
	 */
	CoverArt getCoverArt();
	
	/**
     * @return the media type
    */
	String getMediaType();
	/**
     * @return the media number if availlable.
    */
	String getMedium();
	/**
     * @return the discNo
     */
     String getDiscNo();
	
	/**
     * @return the discTitle
     */
    String getDiscTitle();
	
	
	 /**
     * @return the format
     */
    String getFormat();
	/**
     * @return the Sampling rate, the number of samples taken per second
     */
    Integer getSampleRate();
	
	/**
	 * @return the bitsPerSample
	 */
	Integer getBitsPerSample();

	/**
	 * @return the channels
	 */
	String getChannels();
	
	/**
     * @return if the sampling bitRate is variable or constant
     */
    Boolean isVariableBitRate();
	
	/**
     * @return bitRate as a number, this is the amount of kilobits of data sampled per second
     */
    Long getBitRate();
	
	/**
	 * @return the isLossless
	 */
	Boolean isLossless();
	/**
	 * @return ture if the file is in hight resolution Audio
	*/
	Boolean isHiRes();

	/**
     * @return the track length in sectors
     */
    Integer getLength();

    /** 
	 * @return file length in msec 
	 */
    Long getLengthInMillis();

    /** 
	 * @return file length string
	 */
    String getLengthString();
	
	/**
	 * @return the offset in sectors
	 */
	Integer getOffset();
	
	/**
	 * @return the track End position refferred to the Album (not the file).
	 */
	Integer getEnd();
	/**
	* Get the ISRC code of this track. Null signifies that it has not been set.
	* @return The ISRC code of this track. Null signifies that it has not been set.
	*/
	String getIsrcCode();
	/**
     * @return the copyright
     */
    String getCopyright();	
	
	/**
     * @return the parental warning
     */
    Boolean getParentalWarning();
	
	/**
     * @return the metadataList
    */
    ArrayList<Metadata> getMetadataList();
	
	ArrayList<? extends MetadataRow> getDescriptionMetadataList();
	ArrayList<? extends MetadataRow> getCommentMetadataList();
	ArrayList<? extends MetadataRow> getAwardMetadataList();
	ArrayList<? extends MetadataRow> getCollectionMetadataList();
	ArrayList<? extends MetadataRow> getGoodiesMetadataList();
	ArrayList<? extends MetadataRow> getURLMetadataList();
	ArrayList<? extends MetadataRow> getMusicDescriptorMetadataList();
	ArrayList<? extends MetadataRow> getCreditMetadataList();
	ArrayList<? extends MetadataRow> getOtherInvolvedPersonMetadataList();
	ArrayList<? extends MetadataRow> getMediaDescriptorMetadataList();
	ArrayList<? extends MetadataRow> getWorkDescriptorMetadataList();
	ArrayList<? extends MetadataRow> getMiscellaneaMetadataList();
	ArrayList<? extends MetadataRow> getRatingMetadataList();
	ArrayList<? extends MetadataRow> getAlbumInfoMetada();
	ArrayList<? extends MetadataRow> getMetadaWithNoCategory();
	
	/**
     * @return the sources of Raw Key Value Pairs, like tags or cue files commands.
     */
    ArrayList<RawKeyValuePairSource> getRawKeyValuePairSources();
 
	/**
     * @return the overall Track status
    */
    StatusMessage.Severity getStatus();
	
    /**
     * @return the status message List
    */
    ArrayList<StatusMessage> getMessageList();

}
