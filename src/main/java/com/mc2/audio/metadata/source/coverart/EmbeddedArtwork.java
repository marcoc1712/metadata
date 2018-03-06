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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.reference.PictureTypes;
import com.mc2.audio.metadata.API.CoverArt;
import com.mc2.util.miscellaneous.ImageHandler;

/**
 *
 * @author marco
 */
public class EmbeddedArtwork extends CoverArtAbstract implements CoverArt {

    private final Artwork artwork;
    private final Integer index;
    private final File file;

    public EmbeddedArtwork(File file, Artwork artwork, Integer index){
        super();
                      
        this.artwork = artwork;
        this.index = index;
        this.file = file;
        
         if ( artwork.isLinked()){
            
            init(artwork.getImageUrl());
            
        } else {
        
            init(artwork.getBinaryData());
        }
    }
    
    private void init(byte[] originalSizeImageData){
        
        setOriginalSizeImageData(originalSizeImageData);
        BufferedImage bufferedImage;
        
        try {
            bufferedImage = ImageHandler.getBufferedImage(originalSizeImageData);
        } catch (IOException ex) {
            bufferedImage = ImageHandler.BLANKIMAGE;
        }
        setOriginalSizeImage(bufferedImage);
        
    }
    private void init(String imageUrl){
        
        setOriginalSizeImageUrl(imageUrl);
        byte[] bytes = ImageHandler.EMPTY;
        BufferedImage bufferedImage;
        
        try {
            bufferedImage=ImageHandler.getBufferedImage(new URL(imageUrl));
            bytes = ImageHandler.getByteArray(bufferedImage, JPG);
            
        } catch (IOException ex) {
            bytes = ImageHandler.EMPTY;
            bufferedImage = ImageHandler.BLANKIMAGE;
        }
        setOriginalSizeImageData(bytes);
        setOriginalSizeImage(bufferedImage);
    }
    @Override
    public File getFile() {
        return file;
    }
    @Override
    public Integer getIndex() {
        return index;
    }
    @Override
    public String getUrl() {
        return getOriginalSizeImageUrl();
    }
    
    @Override
    public String getSource() {
        return CoverArt.SOURCE_EMBEDDED_FILE;
    }

    @Override
    public String getType() {
        return PictureTypes.getInstanceOf().getValueForId(artwork.getPictureType()); 
    }

    @Override
    public String getComment() {
       return artwork.getDescription();
    }

    public String getMimeType(){
       return artwork.getMimeType();
    }

    public Boolean isLinked(){
       return artwork.isLinked();
    }

    public int getHeight(){
       return artwork.getHeight();
    }

    public int getWidth(){
       return artwork.getWidth();
    }

}
