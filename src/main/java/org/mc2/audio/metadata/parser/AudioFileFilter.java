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

package org.mc2.audio.metadata.parser;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import static org.mc2.util.miscellaneous.ImageHandler.isFileAnImage;

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
public class AudioFileFilter implements FileFilter
{
    /**
     * allows Directories
     */
    private final boolean allowDirectories;

    public AudioFileFilter( boolean allowDirectories) {
        
        this.allowDirectories=allowDirectories;
    }

    public AudioFileFilter() {
        
        this(false);
    }

    /**
     * <p>Check whether the given file meet the required conditions (supported by the library OR directory).
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
            return allowDirectories;
        }

        try
        {
            String ext =  FilenameUtils.getExtension(file.getCanonicalPath());
            if (SupportedFileFormat.valueOf(ext.toUpperCase()) != null) { return true; }
        }
        catch(IllegalArgumentException | IOException ex) {
            return false;
        }
        return false;
	}
}
