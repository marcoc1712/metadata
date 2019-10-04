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

package com.mc2.audio.metadata.fromFiles;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jaudiotagger.tag.TagField;
import com.mc2.audio.metadata.API.MetadataOrigin;
import com.mc2.audio.metadata.API.exceptions.InvalidTagException;
import com.mc2.audio.metadata.source.tags.schema.ID3v2.ID3v2TagsSchema;
import com.mc2.audio.metadata.source.tags.schema.ID3v2.ID3FrameAndSubId;

/**
 *
 * @author marco
 */
public class ID3V2MetadataOrigin  implements MetadataOrigin { 
        
    private final ID3FrameAndSubId frameAndSubId;
    private final TagMetadataOrigin tagMetadataOrigin;
    
    public ID3V2MetadataOrigin(String source, 
                                ID3FrameAndSubId frameAndSubId, 
                                ArrayList<TagField> tagFields,
                                ArrayList<TagField> discardedTagField,
                                ArrayList<TagField> invalidTagField){

        this.tagMetadataOrigin = new TagMetadataOrigin(source, 
                                                        frameAndSubId.getName(),
                                                        tagFields,  
                                                        discardedTagField,
                                                        invalidTagField);
        this.frameAndSubId = frameAndSubId;

    }
    /**
     * @return the frameAndSubId
     */
    public ID3FrameAndSubId getFrameAndSubId() {
        return frameAndSubId;
    }

    @Override
    public String getOriginKey() {
        if (getFrameAndSubId().getSubId() != null) {
            return getFrameAndSubId().getName();
        }
        return getFrameAndSubId().getFrameId();
    }

    @Override
    public String getSource() {
        return tagMetadataOrigin.getSource();
    }

    @Override
    public ArrayList<String> getValidatedValues() {
        return tagMetadataOrigin.getValidatedValues();
    }

    @Override
    public ArrayList<String> getDiscardedValues() {
        
        ArrayList<String> out= new ArrayList<>();
      
        for (TagField discarded : tagMetadataOrigin.getDiscardedTagField()){
            
            String key="";
            String value="";

            try {
                SimpleImmutableEntry<String,String> valuePair;
                valuePair = ID3v2TagsSchema.getValuePair(discarded);
                key = valuePair.getKey();
                if (!valuePair.getValue().isEmpty() ) {
                    value = valuePair.getValue();
                } else {
                    //value = "["+this.getOriginKey()+"] "+discarded.toString();
                    value =discarded.toString();
                }
            } catch (InvalidTagException ex) {
                Logger.getLogger(ID3V2MetadataOrigin.class.getName()).log(Level.WARNING, null, ex);
            }
            
            if (!out.contains(value)){
                out.add(value);
            } 
        }
        return out;
    }
    @Override
    public ArrayList<String> getInvalidValues() {
        ArrayList<String> out= new ArrayList<>();
      
        for (TagField invalid : tagMetadataOrigin.getInvalidTagField()){
            
            //String value = "["+this.getOriginKey()+"] "+invalid.toString();
            String value = invalid.toString();
            
            if (!out.contains(value)){
                out.add(value);
            } 
        }
        return out;
    }
     /**
     * @return the tagFields
     */
    public ArrayList<TagField> getValidatedTagFields() {
        return tagMetadataOrigin.getValidatedTagFields();
    }

    /**
     * @return the discardedTagField
     */
    public ArrayList<TagField> getDiscardedTagField() {
        return tagMetadataOrigin.getDiscardedTagField();
    }
    /**
     * @return the invalidTagField
     */
    public ArrayList<TagField> getInvalidTagField() {
        return tagMetadataOrigin.getInvalidTagField();
    }

   
}

