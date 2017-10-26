/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.file;

import java.io.File;
import java.util.ArrayList;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.tags.schema.Mp4TagSchema;

/**
 *
 * @author marco
 */
public class M4p extends AudioFile{
    
    public M4p(String path) throws Exception {
        super(path); 
    }
    
    public M4p(File file) throws Exception {
         super(file);
    }
    @Override
    protected void initOptions() throws Exception {
       
          //Add here reader/Writer format options.
    }  
    @Override
    protected void initSchema() throws Exception {
        
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
    
