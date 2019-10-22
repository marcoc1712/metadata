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
public interface Album {

	final static String UNKNOWN_MEDIA_TYPE = "disc";
	final static String UNKNOWN_DISC_NO = "?";
	final static String UNKNOWN_DISC_TOT = "?";
	
     /*
     * @return the album orignal ID (for external sevices) or URL (for local directories or playlists).
    */
    String getId();

	/**
     * @return the Album title
    */
    String getTitle();
	
	/**
     * @return the Album main artist(s).
    */
    String getAlbumArtistNames();
   /**
     * @return the Artist(s) from tracks.
    */
	String getTrackLevelArtistNames();
	/**
     * @return all Artist(s)from Album and form tracks.
    */
	String getArtistNames();
   /**
     * @return all Credits from Album and form tracks.
    */
	String getCredits();
   
	/**
     * @return the Album composer(s).
    */
	String getAlbumComposerNames();
	/**
     * @return the composer(s) from tracks.
    */
	String getTrackLevelComposerNames();
	/**
     * @return all composer(s) naames from Album and form tracks.
    */
	String getComposerNames();
    /**
	 * @return the Works titles in the album, joined in a single string.
	 */
	String getWorkTitles();
	/**
     * @return the Album genre(s);
    */
	String getGenre();
    /**
     * @return the Album awards
    */
	String getAwards();
		 /**
     * @return the Album Releease Date (or year)
    */	
    String getDate();
    /**
     * @return the Album release Country
    */
    String getCountry();
    /**
     * @return the Album release Label
    */
    String getLabel();
	/**
     * @return the Collection by the label.
    */
	String getCollection();
    /**
     * @return the Album CatalogNo
    */
    String getCatalogNo();
   /**
     * @return the Album UPC code
    */
	String getUpc();
	
	/**
     * @return the Disc number (in case of single disk in a boxset)
    */
	String getDisc();
	/**
     * @return the total Discs (in case of single disk in a boxset)
    */
    String getTotalDiscs();
	/**
     * @return the Disc title (in case of single disk in a boxset)
    */
	String getDiscTitle();
	 	
	/**
	 * @return the corresponding musicbrainz release ID, if any.
	 */
	String getMbReleaseId();
	
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
     * @return the copyright
     */
    String getCopyright();
	
	/**
     * @return the parental warning
     */
	
    Boolean getParentalWarning();
	
	/**
     * @return the Media descriptor (es. 2 CD + 1 DVD)
    */
	String getMediaType();
	
	/**
     * @return the totalLength
     */
    Integer getTotalLength();
	   
	/**
     * @return the media
     */
    ArrayList<? extends Medium> getMediaList();
	
	 /**
     * @return the trackList
     */
    ArrayList<? extends Track> getTrackList();
	
	/*
	* returns the complete and unordered list of tracks extracted form all the
	* files in the directory. This is similar to a playlist, but is made by 'real' 
	* files, more a 'compilation' or a 'collection' of songs.
	*/
	
	ArrayList<? extends Track> getSingleTrackList();
	
	/**
	 *@return the most representative coverArt
	 */
	CoverArt getCoverArt();
	
	/**
     * @return the coverArtList
     */
    ArrayList<CoverArt> getcoverArtList();
	
	/**
     * @return the overall Album status
     */
    StatusMessage.Severity getStatus();
	
    /**
     * @return the messageList
     */
    ArrayList<StatusMessage> getMessageList();
	
	// End One to one with Metadata //
	
	/**
     * @return the metadata List
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
	ArrayList<? extends MetadataRow> getRecordingDescriptorMetadataList();
	ArrayList<? extends MetadataRow> getMiscellaneaMetadataList();
	ArrayList<? extends MetadataRow> getRatingMetadataList();
	ArrayList<? extends MetadataRow> getMetadaWithNoCategory();
	
	/**
     * @return the sources of Raw Key Value Pairs, like tag or cue files lines.
    */
    ArrayList<RawKeyValuePairSource> getRawKeyValuePairSources();

	
	
	
   
}
