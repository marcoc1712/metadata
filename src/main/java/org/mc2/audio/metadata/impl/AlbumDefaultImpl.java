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
package org.mc2.audio.metadata.impl;

import java.io.File;
import java.util.ArrayList;
import org.mc2.audio.metadata.API.Album;
import org.mc2.audio.metadata.API.CoverArt;
import org.mc2.audio.metadata.API.Metadata;
import org.mc2.audio.metadata.API.MetadataKeys.METADATA_KEY;
import org.mc2.audio.metadata.API.StatusMessage;
import org.mc2.audio.metadata.API.Track;
import org.mc2.audio.metadata.source.cue.file.CueFile;
import org.mc2.audio.metadata.source.tags.file.AudioFile;

import static org.mc2.audio.metadata.API.MetadataKeys.getAlbumLevelMetadataAliases;
import org.mc2.audio.metadata.API.RawKeyValuePairSource;
import org.mc2.audio.metadata.API.StatusMessage.Severity;
import org.mc2.audio.metadata.source.cue.FileData;

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
    private final ArrayList<Track> trackList;
    private final ArrayList<CoverArt> coverArtList;
    private final ArrayList<Metadata> metadataList;
    private final ArrayList<StatusMessage> messageList;
    
    private Integer totalLength = 0;
    ArrayList<Integer> discIdOffsets = new ArrayList<>();
    
    public AlbumDefaultImpl(String url,
                            ArrayList<CoverArt> coverArtList,
                            ArrayList<Metadata> metadataList, 
                            ArrayList<Track> trackList, 
                            ArrayList<File> fileList, 
                            ArrayList<CueFile> cueFileList, 
                            ArrayList<AudioFile> audioFileList, 
                            ArrayList<File> imageFileList,
                            ArrayList<StatusMessage> messageList) {
        
        this.url = url;
        this.coverArtList = coverArtList;  
        this.metadataList = metadataList;  
        this.fileList = fileList;
        this.cueFileList = cueFileList;
        this.audioFileList = audioFileList;
        this.imageFileList = imageFileList;
        this.messageList = messageList;
        
        this.trackList = initTrackList(trackList);

    }
    
    private ArrayList<Track> initTrackList(ArrayList<Track> trackList){

        discIdOffsets.add(0,0);
        
        Integer offset = 0;
        Integer index = 0;
        for (Track track : trackList){
            
            if ( track instanceof TrackDefaultImpl){
                ((TrackDefaultImpl)track).setOffset(offset);
                offset= offset+track.getLength();
                totalLength= totalLength+track.getLength();
                discIdOffsets.add(track.getOffset());
                
                ((TrackDefaultImpl)track).setAlbumUrl(url);
                ((TrackDefaultImpl)track).setIndex(index);
            }
            index++;
            //System.out.println(track.getOffset()+" "+track.getLength()+" "+totalLength);
        }
        
        discIdOffsets.set(0, totalLength);
        return trackList;
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
    public ArrayList<Track> getTrackList() {
        return trackList;
    }
    /**
     * @return the discId offsets
     */
    @Override
    public Integer[] getOffsetArray() {
        return (Integer[])discIdOffsets.toArray();
    }
    /**
     * @return the discId offsets
     */
    @Override
    public ArrayList<Integer> getOffsets() {
        return  discIdOffsets;
    }
    /**
     * @return the discId offsets
     */
    @Override
    public String getToc() {
        
        String offests="";
        for (Integer offset : discIdOffsets){
            offests=offests+" "+offset;
        }
        return  "1 "+ (discIdOffsets.size()-1) + offests;
    }
    /**
     * @return the coverArtList
     */
    @Override
    public ArrayList<CoverArt> getcoverArtList() {
        return coverArtList;
    }
    /**
     * @return the metadataList
     */
    @Override
    public ArrayList<Metadata> getMetadataList() {
        return metadataList;
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
    public String getAlbum(){
       
        return this.getMetadataValue(METADATA_KEY.ALBUM.name());
    }
    
    @Override
    public String getAlbumArtist(){
    
        return this.getMetadataValue(METADATA_KEY.ALBUM_ARTIST.name());
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
    public String getMedia(){
    
        return this.getMetadataValue(METADATA_KEY.MEDIA.name());
    }
    @Override
    public String getDiscTotal(){
    
        return this.getMetadataValue(METADATA_KEY.DISC_TOTAL.name());
    }
    
    @Override  
    public String getDiscNo(){
    
        return this.getMetadataValue(METADATA_KEY.DISC_NO.name());
    }
      
    
    private String getMetadataValue(String key){
        for (Metadata  metadata : metadataList){

            if (metadata.getKey().equals(key)){
                return metadata.getValue();
            } 
        }
        String value = getMetadataValueFromTracks(key);
        
        if (!value.isEmpty()){return value;}
        
        ArrayList<String> aliases = getAlbumLevelMetadataAliases(key);
        
        for (String alias : aliases){
            
            value = getMetadataValue(alias);
            if (!value.isEmpty()){return value;}
        }
        
        return"";
        
    }
    private String getMetadataValueFromTracks(String key){
        
        String value = "";
        
        for (Track  track : this.getTrackList()){
            
            if (track  instanceof TrackDefaultImpl){
                String trackValue = ((TrackDefaultImpl)track).getMetadataValue(key);
                
                if (value.isEmpty()){
                    
                    value= trackValue;
                } else {
                    
                    if (!value.equals(trackValue)){
                    
                        value = "";
                        break;
                    }
                }
            }  
        }
        return value;
    }

}
