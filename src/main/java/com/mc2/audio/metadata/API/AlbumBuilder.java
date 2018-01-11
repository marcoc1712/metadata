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
import java.io.IOException;
import com.mc2.audio.metadata.API.exceptions.InvalidAudioFileException;
import com.mc2.audio.metadata.API.exceptions.InvalidAudioFileFormatException;
import com.mc2.audio.metadata.API.exceptions.InvalidCueSheetException;
import com.mc2.audio.metadata.parser.DirectoryParser;

/**
 *
 * @author marco
 */
public interface AlbumBuilder {

    /**
     * Parse directory and build an album.
     * @param directory
     * @return the album
     * @throws java.io.IOException
     * @throws com.mc2.audio.metadata.API.exceptions.InvalidCueSheetException
     * @throws com.mc2.audio.metadata.API.exceptions.InvalidAudioFileException
     * @throws com.mc2.audio.metadata.API.exceptions.InvalidAudioFileFormatException
    */
    public static Album parse(String directory) throws IOException, InvalidCueSheetException, InvalidAudioFileException, InvalidAudioFileFormatException{
        return DirectoryParser.parse(new File(directory));
    };
    
    /**
     * Parse directory and build an album.
     * @param directory
     * @return the album
     * @throws java.io.IOException
     * @throws com.mc2.audio.metadata.API.exceptions.InvalidCueSheetException
     * @throws com.mc2.audio.metadata.API.exceptions.InvalidAudioFileException
     * @throws com.mc2.audio.metadata.API.exceptions.InvalidAudioFileFormatException
     */ 
    public static Album parse(File directory) throws IOException, InvalidCueSheetException, InvalidAudioFileException, InvalidAudioFileFormatException{
        return DirectoryParser.parse(directory);
    };

}
