/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.impl;

import java.util.AbstractMap.SimpleImmutableEntry;
import java.util.ArrayList;
import org.jaudiotagger.tag.TagField;
import org.mc2.audio.metadata.MetadataOrigin;
import org.mc2.audio.metadata.source.tags.schema.ID3v2.ID3v2TagsSchema;
import org.mc2.audio.metadata.source.tags.schema.ID3v2.ID3FrameAndSubId;

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
            
            String value;
            
            SimpleImmutableEntry<String,String> valuePair= ID3v2TagsSchema.getValuePair(discarded);
            if (!valuePair.getValue().isEmpty() ) {
                value = valuePair.getValue();
            } else {
                value = "["+this.getOriginKey()+"] "+discarded.toString();
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
            
            String value = "["+this.getOriginKey()+"] "+invalid.toString();
            
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

