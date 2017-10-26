/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.schema;

import java.util.ArrayList;
import org.jaudiotagger.tag.Tag;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.tags.TagsSource;

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
