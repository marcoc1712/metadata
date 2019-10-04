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

package com.mc2.audio.metadata.source.cue.file;

import com.mc2.audio.metadata.source.cue.CueSheetMetadaParser;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import jwbroek.cuelib.LineOfInput;
import org.apache.commons.io.FilenameUtils;
import com.mc2.audio.metadata.API.RawKeyValuePair;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.exceptions.InvalidCueSheetException;
import com.mc2.audio.metadata.impl.GenericRawKeyValuePair;
import com.mc2.audio.metadata.source.cue.CueSheet;
/**
 *
 * @author marco
 */
public class CueFile implements RawKeyValuePairSource{

    private File cueFile;
    private String path;
    private CueSheet cuesheet;

    public CueFile(String  path) throws IOException, InvalidCueSheetException{
        this.path = path;
        this.cueFile = new File(path);
        if (!isCueFile())  throw new InvalidCueSheetException("this is not a a cue file!");
        this.cuesheet = CueSheetMetadaParser.parse(cueFile);

    }
     
    public CueFile(File file) throws IOException, InvalidCueSheetException {
        this.cueFile = file;
        this.path = file.getCanonicalPath();
        if (!isCueFile())  throw new InvalidCueSheetException("this is not a a cue file!");
        this.cuesheet = CueSheetMetadaParser.parse(file);
    }
    public static boolean isCueFile(File file) throws IOException{
        
        String ext =  FilenameUtils.getExtension(file.getCanonicalPath());

        try{
            return SupportedCueFileFormat.valueOf(ext.toUpperCase()) != null;
        } catch (IllegalArgumentException ex){
            return false;
        }
        
    }
    /**
     * @return the File
     */
    public File getFile() {
        return cueFile;
    }
    /**
     * @return the Cuesheet
     */
    public CueSheet getCuesheet() {
        return cuesheet;
    }
    private final boolean isCueFile() throws IOException{
        return isCueFile(getFile());
    }
   
    @Override
    public String getSourceId() {
        return this.cuesheet.getSourceId();
    }
    
    /**
     * 
     * @return the lines as simple key value pairs
    */
    @Override
    public ArrayList<RawKeyValuePair> getRawKeyValuePairs() {
        ArrayList<RawKeyValuePair> out = new ArrayList<>();

        for (LineOfInput line : this.cuesheet.getLines()){
            
            RawKeyValuePair pair = new GenericRawKeyValuePair(line.getLineNumber()+"", line.getInput());
            out.add(pair);
        }
        return out;
    }

    
}
    
