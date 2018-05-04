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

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author marco
 */
public interface Album {
    
     /*
     * @return the album url (a directory or a playlist).
    */
    public String getUrl();
    
    /**
     * @return the sources of Raw Key Value Pairs, like tag or cue files lines.
    */
    ArrayList<RawKeyValuePairSource> getRawKeyValuePairSources();
    
    /*
     * @return the fileList
     */
    ArrayList<File> getFileList();

    /**
     * @return the imageFileList
     */
    ArrayList<File> getImageFileList();

    /**
     * @return the messageList
     */
    ArrayList<StatusMessage> getMessageList();
    
     /**
     * @return the overall Album status
     */
    StatusMessage.Severity getStatus();

    /**
     * @return the totalLength
     */
    Integer getTotalLength();

    /**
     * @return the trackList
     */
    ArrayList<? extends Track> getTrackList();

	/**
     * @return the media
     */
    ArrayList<? extends Medium> getMediaList();

    /**
     * @return the coverArtList
     */
    ArrayList<CoverArt> getcoverArtList();
   
    /**
     * @return the Album title
    */
    String getAlbum();
    /**
     * @return the Album main artist(s).
    */
    String getAlbumArtist();
   /**
     * @return the Album composer(s).
    */
	String getAlbumComposer();
	/**
     * @return the composer(s) from tracks.
    */
	String getTrackLevelComposers();
	/**
     * @return all composer(s)from Album and form tracks.
    */
	String getComposers();
    /**
     * @return the Album genre(s);
    */
	String getGenre();
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
     * @return the Album CatalogNo
    */
    String getCatalogNo();
   
	/**
     * @return the Disc number (in case of single disk in a boxset)
    */
	public String getDisc();
	/**
     * @return the total Discs (in case of single disk in a boxset)
    */
    public String getTotalDiscs();
	/**
     * @return the Disc title (in case of single disk in a boxset)
    */
	public String getDiscTitle();
	 /**
     * @return the Media descriptor (es. 2 CD + 1 DVD)
    */
	public String getMedia();
	
	/**
	 * @return the corresponding musicbrainz release ID, if any.
	 */
	String getMbReleaseId();
	
	 /**
     * @return the format
     */
    public String getFormat();
	
	/**
     * @return the Sampling rate, the number of samples taken per second
     */
    public Integer getSampleRate();
	
	/**
	 * @return the bitsPerSample
	 */
	public Integer getBitsPerSample();

	/**
	 * @return the channels
	 */
	public String getChannels();
	
	/**
     * @return if the sampling bitRate is variable or constant
     */
    public boolean isVariableBitRate();
	
	/**
     * @return bitRate as a number, this is the amount of kilobits of data sampled per second
     */
    public long getBitRate();
	
	/**
	 * @return the isLossless
	 */
	public Boolean isLossless();
	
	/**
	 * @return ture if the file is in hight resolution Audio
	*/
	public Boolean isHiRes();
	
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
	ArrayList<? extends MetadataRow> getMetadaWithNoCategory();
}
