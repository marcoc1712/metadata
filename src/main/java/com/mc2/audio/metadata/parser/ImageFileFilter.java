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

package com.mc2.audio.metadata.parser;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import static com.mc2.util.miscellaneous.ImageHandler.isFileARealImage;

/**
 * <p>This is a simple FileFilter that will only allow the audio file supported 
 * by this library amd images. Depèendiing on an input parameter, It couls also 
 * accept directories. 
 * 
 * An additional condition is that file must be readable (read permission) and
 * not hidden (dot files, or hidden files).
 * 
 * Derived form  AudioFileFilter in JAudiotagger.
 */
public class ImageFileFilter implements FileFilter
{
    /**
     * allows Directories
     */
  
    public ImageFileFilter() {
        
    }


    /**
     * <p>Check whether the given file contains an image.
     * The File must also be readable and not hidden.
     *
     * @param    file    The file to test
     * @return a boolean indicating if the file is accepted or not
     */
    @Override
    public boolean accept(File file) {
        
        if (file.isHidden() || !file.canRead()) {
            return false;
        }

        if (file.isDirectory()) {
            return false;
        }

        try {
            if (isFileARealImage(file)){ return true;}
        }
        catch(IOException ex) {
            return false;
        }
        return false;
	}
}
