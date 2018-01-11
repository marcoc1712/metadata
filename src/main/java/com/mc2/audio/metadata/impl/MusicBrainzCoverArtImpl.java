package com.mc2.audio.metadata.impl;

import java.io.File;
import com.mc2.audio.metadata.API.musicbrainz.MusicBrainzCoverArt;
import com.mc2.audio.metadata.API.CoverArt;
import com.mc2.audio.metadata.source.coverart.CoverArtAbstract;
import com.mc2.musicbrainz.coverart.Image;

/*

A single cover art Image in Musicbrainz Cover Archive.

Types are listed in fm.last.musicbrainz.coverart.CoverArtType 
For each cover art you have large (500x500) and small (250*250) Thumbnail,
getters for Original size image, image and 500 Thumbnailis all return the 
500*500 px image URL, 250 is 250*250 and  150*150 is empty.

*/

public class MusicBrainzCoverArtImpl extends CoverArtAbstract implements CoverArt, MusicBrainzCoverArt{

    private final Image image;
    private final String mbId;

    public MusicBrainzCoverArtImpl (String mbId, Image image){
        super();
        this.mbId=mbId;
        this.image=image;
        
        init();
        
    }
    private void init(){
        
        if (this.image != null && this.image.getImageUrl() != null ){
            setOriginalSizeImageUrl(this.image.getImageUrl());
            setImageUrl(this.image.getImageUrl());
        }
        if (this.image != null && this.image.getSmallThumbnailUrl() != null ){
            setThumbnail250Url(this.image.getSmallThumbnailUrl());
        }
        setThumbnail150Url("");
    }

    @Override
    public String getSource() {

        return com.mc2.audio.metadata.API.CoverArt.SOURCE_MUSICBRAINZ_COVER_ARCHIVE;
    }
    
    @Override
    public String getUrl() {
       return "";
    }

    @Override
    public File getFile() {
       return null;
    }

    @Override
    public Integer getIndex() {
        return -1;
    }
    @Override
    public String getType() {
        if (this.image != null) {return this.image.getType();}
        return "";
    }

    @Override
    public String getComment() {
        if (this.image != null) {return this.image.getComment();}
        return "";
        
    }

    @Override
    public String getMbId() {
        return mbId;
    }

   
    

    

    

   
}
