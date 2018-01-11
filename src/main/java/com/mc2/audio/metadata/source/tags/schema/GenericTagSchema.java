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

package com.mc2.audio.metadata.source.tags.schema;

import java.util.ArrayList;
import org.jaudiotagger.tag.Tag;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.source.tags.TagsSource;

/**
 *
 * @author marco
 */
public class GenericTagSchema extends TagSchema  {
    
    Tag tag;
    public GenericTagSchema(Tag tag, TagsSource source) {
        super(source);
        this.tag=tag;
    }
    
    public ArrayList<Metadata> getExistingMetadata(){
        
        return this.getExistingAndValidMetadata();
    }
    
    public ArrayList<Metadata> getMetadata(){
       
        return this.getExistingAndValidMetadata();
    }

}
