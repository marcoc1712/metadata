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
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataKeys;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.Track;
import com.mc2.util.miscellaneous.CalendarUtils;

/**
 *
 * @author marco
 */
public class TrackDefaultImpl implements Track {

    private Integer trackNo; 
    private File file;
    private Integer offset=0;
    private Integer length=0;
    private String url;
    private String albumUrl;
    private Integer index;
    
    private final ArrayList<Metadata> metadataList;
    private final ArrayList<RawKeyValuePairSource> rawKeyValuePairSources; 
    private final ArrayList<StatusMessage> messageList;
    
    public TrackDefaultImpl(Integer trackNo, ArrayList<Metadata> metadataList) {
        this.trackNo = trackNo;
        this.metadataList = metadataList;
        this.rawKeyValuePairSources =new ArrayList<>();
        this.messageList =new ArrayList<>();
    }
    
    /**
     * @return the Album url
     */
    @Override
    public String getAlbumUrl() {
        return albumUrl;
    }
    /**
     * @param albumUrl the albumUrl to set
     */
    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
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
     * @return the trackNo
     */
    @Override
    public Integer getTrackNo() {
        return trackNo;
    }
    /**
     * @return the offset in sectors
     */
    @Override
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
     * @return the length in sectors.
     */
    @Override
    public int getLength() {
        return length;
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
     * @param length the length in sectors to set
     */
    public void setLength(int length) {
        this.length = length;
    }
    /**
     * @return the track End position refferred to the Album (not the file).
     */
    @Override
    public int getEnd() {
        return offset+length;
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
    @Override
    public String getTitle(){
    
        return this.getMetadataValue(MetadataKeys.METADATA_KEY.TITLE.name());
    }
    @Override
    public String getArtist(){
    
        return this.getMetadataValue(MetadataKeys.METADATA_KEY.ARTIST.name());
    }
    /**
     * @return the url
     */
    @Override
    public String getUrl() {
        return url;
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
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
    
    protected String getMetadataValue(String key){
        for (Metadata  metadata : metadataList){
            
            if (metadata.getKey().equals(key)){
                return metadata.getValue();
            }
           
        }
        return "";
    }

}
