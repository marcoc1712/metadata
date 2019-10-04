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
package com.mc2.audio.metadata.source.coverart;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;
import com.mc2.audio.metadata.API.CoverArt;
import com.mc2.util.miscellaneous.ImageHandler;

/**
 *
 * @author marco
 */
public abstract class CoverArtAbstract implements CoverArt {
    
    public static final String JPG = "jpg";
    
    private final String id;
	protected String type;
	protected String url;
    
    private String originalSizeImageUrl="";
    private BufferedImage originalSizeImage; //no limits
    private byte[] originalSizeImageData; //no limits
    
    private String imageUrl=""; //500
    private String thumbnail150Url=""; //150
    private String thumbnail250Url=""; //250
    
    private BufferedImage image; //500
    private BufferedImage thumbnail150; //150
    private BufferedImage thumbnail250; //250

    private byte[] imageData; //500
    private byte[] thumbnail150Data; //150
    private byte[] thumbnail250Data; //250
    
    public CoverArtAbstract(){
    
        this.id=UUID.randomUUID().toString();
    }
    

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }
	@Override
	public abstract String getSource();
	/**
     * @return the URL
     */
    @Override
    public String getUrl() {
        return url;
    }
	/**
     * @return the Type
     */
    @Override
    public String getType() {
        return type;
    }
    @Override
    public String getOriginalSizeImageUrl() {
        return this.originalSizeImageUrl;
    }
    
    @Override
    public BufferedImage getOriginalSizeImage() {
        if (this.originalSizeImage == null && 
            this.getOriginalSizeImageUrl() != null &&
            !this.getOriginalSizeImageUrl().isEmpty()){
            
            try {
                URL url= new URL(this.getOriginalSizeImageUrl());
                this.originalSizeImage = ImageHandler.getBufferedImage(url);
            } catch (IOException ex) {
                this.originalSizeImage=ImageHandler.BLANKIMAGE;
            }
        }

        return this.originalSizeImage;
        
    }
    
    @Override
    public byte[] getOriginalSizeImageData() {
        if (this.originalSizeImageData == null){
            
            try {
                this.originalSizeImageData = ImageHandler.getByteArray(getOriginalSizeImage(), JPG);
            } catch (IOException ex) {
                 this.originalSizeImageData = ImageHandler.EMPTY;
            }
            
        }
        return this.originalSizeImageData;
    }
    /**/

    @Override
    public String getImageUrl() {
       return this.imageUrl;
    }   
    
    @Override
    public BufferedImage getImage() {
        
        if (this.image == null && 
            this.getImageUrl() != null &&
            !this.getImageUrl().isEmpty()){
            
            try {
                URL url= new URL(this.getImageUrl());
                this.image = ImageHandler.getBufferedImage(url);
            } catch (IOException ex) {
                
            }
            
        }
        
        if (this.image == null){
            this.image = ImageHandler.resizeMantainProps(this.getOriginalSizeImage(),500,500);
        } 
        
        return  this.image;
    }
    @Override
    public byte[] getImageData() {
        if (this.imageData != null) return this.imageData;
        
        try {
            this.imageData = ImageHandler.getByteArray(this.getImage(), JPG);
            return this.imageData;
        } catch (IOException ex) {
            return ImageHandler.EMPTY;
        }
    }

    @Override
    public String getThumbnail500Url() {
        return this.getImageUrl();
    }
    
    @Override
    public BufferedImage getThumbnail500() {
        return this.getImage();
    }
    
    @Override
    public byte[] getThumbnail500Data() {
        return this.getImageData();
    }
    
    @Override
    public String getThumbnail250Url() {
        return this.thumbnail250Url;
    }
    
    @Override
    public BufferedImage getThumbnail250() {
        
        if (this.thumbnail250 == null && 
            this.getThumbnail250Url() != null &&
            !this.getThumbnail250Url().isEmpty()){
            
            try {
                URL url= new URL(this.getThumbnail250Url());
                this.thumbnail250 = ImageHandler.getBufferedImage(url);
            } catch (IOException ex) {
                
            }
            
        }
        
        if (this.thumbnail250 == null){
            this.thumbnail250 = ImageHandler.resizeMantainProps(this.getOriginalSizeImage(),250,250);
        } 
        return  this.thumbnail250;
    }
    
    @Override
    public byte[] getThumbnail250Data() {
        if (this.thumbnail250Data != null) return this.thumbnail250Data;
        
        try {
            this.thumbnail250Data =  ImageHandler.getByteArray(this.getThumbnail250(), JPG);
            return this.thumbnail250Data;
        } catch (IOException ex) {
            return ImageHandler.EMPTY;
        }
    }
        
    @Override
    public String getThumbnail150Url() {
        return this.thumbnail150Url;
    }
    
    @Override
    public BufferedImage getThumbnail150() {
        
        if (this.thumbnail150 == null && 
            this.getThumbnail150Url() != null &&
            !this.getThumbnail150Url().isEmpty()){
            
            try {
                URL url= new URL(this.getThumbnail150Url());
                this.thumbnail150 = ImageHandler.getBufferedImage(url);
            } catch (IOException ex) {
                
            }
            
        }
        
        if (this.thumbnail150 == null){
            this.thumbnail150 = ImageHandler.resizeMantainProps(this.getOriginalSizeImage(),150,150);
        } 
        return  this.thumbnail150;
    }
    
    @Override
    public byte[] getThumbnail150Data() {
        if (this.thumbnail150Data != null) return this.thumbnail150Data;
        
        try {
            this.thumbnail150Data =  ImageHandler.getByteArray(this.getThumbnail150(), JPG);
            return this.thumbnail150Data;
        } catch (IOException ex) {
            return ImageHandler.EMPTY;
        }
    }

    protected void setOriginalSizeImageUrl(String originalSizeImageUrl) {
        this.originalSizeImageUrl = originalSizeImageUrl;
    }

    protected void setOriginalSizeImage(BufferedImage originalSizeImage) {
        this.originalSizeImage = originalSizeImage;
    }

    protected void setOriginalSizeImageData(byte[] originalSizeImageData) {
        this.originalSizeImageData = originalSizeImageData;
    }

    protected void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    protected void setThumbnail150Url(String thumbnail150Url) {
        this.thumbnail150Url = thumbnail150Url;
    }

    protected void setThumbnail250Url(String thumbnail250Url) {
        this.thumbnail250Url = thumbnail250Url;
    }

    protected void setImage(BufferedImage image) {
        this.image = image;
    }

    protected void setThumbnail150(BufferedImage thumbnail150) {
        this.thumbnail150 = thumbnail150;
    }

    protected void setThumbnail250(BufferedImage thumbnail250) {
        this.thumbnail250 = thumbnail250;
    }

    protected void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    protected void setThumbnail150Data(byte[] thumbnail150Data) {
        this.thumbnail150Data = thumbnail150Data;
    }

    protected void setThumbnail250Data(byte[] thumbnail250Data) {
        this.thumbnail250Data = thumbnail250Data;
    }

}
