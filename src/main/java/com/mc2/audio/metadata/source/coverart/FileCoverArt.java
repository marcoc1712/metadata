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
import org.apache.commons.io.FilenameUtils;
import com.mc2.audio.metadata.API.CoverArt;
import com.mc2.util.miscellaneous.ImageHandler;

/**
 *
 * @author marco
 */
public class FileCoverArt extends CoverArtAbstract implements CoverArt {
    
    private final File file;

    public FileCoverArt(File file){
        super();
        
        this.file = file;
        
		//lazy load.
        //init();
    }
    
    private void init(){
        
        byte[] bytes;
        BufferedImage bufferedImage;
        
        try {
            bufferedImage =ImageHandler.getImagefromFile(this.file);
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
        return -1;
    }
    @Override
    public String getUrl() {
        return "";
    }
    @Override
    public String getSource() {
        return CoverArt.SOURCE_IMAGE_FILE;
    }

    @Override
    public String getType() {
        return  FilenameUtils.removeExtension(file.getName());
    }

    @Override
    public String getComment() {
       return file.getParentFile().getPath();
    }
	@Override
    public byte[] getOriginalSizeImageData() {
		
		if (super.getOriginalSizeImageData() == null) init();
		return super.getOriginalSizeImageData();
	}
	@Override
    public BufferedImage getOriginalSizeImage() {
		
		if (super.getOriginalSizeImage() == null) init();
		return super.getOriginalSizeImage();
	}
}
