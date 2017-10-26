/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.file;

import java.io.File;
import java.util.ArrayList;
import org.jaudiotagger.tag.asf.AsfTag;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.tags.schema.AsfTagSchema;
/**
 *
 * @author marco
 */
public class Wma extends AudioFile {

    public Wma(String path) throws Exception {
        super(path); 
    }
    
    public Wma(File file) throws Exception {
         super(file);
    }
    
    @Override
    protected void initOptions() throws Exception {

    }
    @Override
    protected void initSchema() throws Exception {
        AsfTag asfTag = getTag();
        super.setTagSchema(new AsfTagSchema(asfTag, this));

    }
    
    @Override
    public AsfTag getTag() {
        return (AsfTag) super.getTag();
    } 

    @Override
    public ArrayList<Metadata> getMetadata() {
        return this.getAsfTagSchema().getMetadata();
    }
    @Override
    public ArrayList<Metadata> getExistingMetadata(){
        return this.getAsfTagSchema().getExistingMetadata();
    }

    /**
     * @return the iD3v2Tag
     */
    public AsfTagSchema getAsfTagSchema() {
        return (AsfTagSchema)super.getTagSchema();
    }
}
    
