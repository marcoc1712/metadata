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
import org.jaudiotagger.audio.real.RealTag;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.tags.schema.RealTagSchema;

/**
 *
 * @author marco
 */
public class Rm extends AudioFile{
    
    public Rm(String path) throws Exception {
        super(path); 
    }
    
    public Rm(File file) throws Exception {
         super(file);
    }
    @Override
    protected void initOptions() throws Exception {
       
          //Add here reader/Writer format options.
    }  
    @Override
    protected void initSchema() throws Exception {
        
        RealTag realTag = getTag();
        super.setTagSchema(new RealTagSchema(realTag, this));

    }
    @Override
    public RealTag getTag() {
        return (RealTag) super.getTag();
    } 

    public RealTagSchema getRealTagSchema() {
        return (RealTagSchema)super.getTagSchema();
    }
    @Override
    public ArrayList<Metadata> getMetadata() {
        return getRealTagSchema().getMetadata();
    }
    @Override
    public ArrayList<Metadata> getExistingMetadata(){

        return getRealTagSchema().getExistingMetadata();
    }
    
}
    
