/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
