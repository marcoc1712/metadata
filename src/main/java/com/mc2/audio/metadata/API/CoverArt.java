/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mc2.audio.metadata.API;

import java.awt.image.BufferedImage;
import java.io.File;


public interface CoverArt {
    
    public static final String SOURCE_IMAGE_FILE ="IMAGE_FILE";
    public static final String SOURCE_EMBEDDED_FILE ="EMBEDDED_PICTURE";
    public static final String SOURCE_MUSICBRAINZ_COVER_ARCHIVE ="MUSICBRAINZ_COVER_ARCHIVE";
    
    public String getId();
    
    public String getSource();
    /**/
    public String getUrl();
    public File getFile();
    public Integer getIndex();
    /**/
    public String getType();
    public String getComment();
    /**/
    public BufferedImage getOriginalSizeImage();
    public BufferedImage getImage(); //should depend on prefs, default is 500x500.
    public BufferedImage getThumbnail150();
    public BufferedImage getThumbnail250();
    public BufferedImage getThumbnail500();
    
    /**/
    public byte[] getOriginalSizeImageData();
    public byte[] getImageData(); //should depend on prefs, default is 500x500.
    public byte[] getThumbnail150Data();
    public byte[] getThumbnail250Data();
    public byte[] getThumbnail500Data();
    /*
        Only When the image is directly reachable via an external URL (not local).
    */
    public String getOriginalSizeImageUrl();
    public String getImageUrl(); //should depend on prefs, default is 500x500.
    public String getThumbnail150Url();
    public String getThumbnail250Url();
    public String getThumbnail500Url();
}
