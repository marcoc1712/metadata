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

package org.mc2.audio.metadata.source.tags.file;

import java.io.File;
import java.util.ArrayList;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.exceptions.InvalidAudioFileException;
import org.mc2.audio.metadata.source.tags.schema.Mp4TagSchema;

/**
 *
 * @author marco
 */
public class M4a extends AudioFile{
    
    public M4a(String path) throws InvalidAudioFileException  {
        super(path); 
    }
    
    public M4a(File file) throws InvalidAudioFileException  {
         super(file);
    }
    @Override
    protected void initOptions() {
       
          //Add here reader/Writer format options.
    }  
    @Override
    protected void initSchema() {
        
        Mp4Tag Mp4Tag = getTag();
        super.setTagSchema(new Mp4TagSchema(Mp4Tag, this));

    }
    @Override
    public Mp4Tag getTag() {
        return (Mp4Tag) super.getTag();
    } 

    public Mp4TagSchema getMp4TagSchema() {
        return (Mp4TagSchema)super.getTagSchema();
    }
    @Override
    public ArrayList<Metadata> getMetadata() {
        return getMp4TagSchema().getMetadata();
    }
    @Override
    public ArrayList<Metadata> getExistingMetadata(){

        return getMp4TagSchema().getExistingMetadata();
    }
    
}
    
