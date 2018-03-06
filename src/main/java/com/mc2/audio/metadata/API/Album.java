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
     * @return the metadataList
     */
    ArrayList<Metadata> getMetadataList();

    /**
     * @return the discId offsets
     */
    Integer[] getOffsetArray();

    /**
     * @return the discId offsets
     */
    ArrayList<Integer> getOffsets();

    /**
     * @return the discId offsets
     */
    String getToc();

    /**
     * @return the totalLength
     */
    Integer getTotalLength();

    /**
     * @return the trackList
     */
    ArrayList<Track> getTrackList();

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
	String getComposer();
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
     * @return the Media
    */
    String getMedia();
    /**
     * @return the Album Disc Total
    */
    String getDiscTotal();
            
    /**
     * @return the Album DiscNo
    */
    String getDiscNo();

}
