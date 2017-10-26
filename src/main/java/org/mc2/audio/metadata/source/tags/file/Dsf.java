/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.file;

import java.io.File;
import java.util.ArrayList;
import org.mc2.audio.metadata.source.tags.schema.ID3v2.ID3v2TagsSchema;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.mc2.audio.metadata.Metadata;
/**
 *
 * @author marco
 */
public class Dsf extends AudioFile {

    public Dsf(String path) throws Exception {
        super(path); 
    }
    
    public Dsf(File file) throws Exception {
         super(file);
    }
    
    @Override
    protected void initOptions() throws Exception {
        
        
        //Default is ID3_V24.
        //TagOptionSingleton.getInstance().setID3V2Version(ID3V2Version.ID3_V24);
        //TagOptionSingleton.getInstance().setID3V2Version(ID3V2Version.ID3_V23);
        //TagOptionSingleton.getInstance().setID3V2Version(ID3V2Version.ID3_V22);
        
    }
    @Override
    protected void initSchema() throws Exception {
        AbstractID3v2Tag abstractId3v2Tag = getTag();
        super.setTagSchema(new ID3v2TagsSchema(abstractId3v2Tag, this));
       
        // We could forget wav info tags, see Init options.
    }
    
    @Override
    public AbstractID3v2Tag getTag() {
        return (AbstractID3v2Tag)super.getTag();
    } 

    @Override
    public ArrayList<Metadata> getMetadata() {
        return this.getiD3v2Tag().getMetadata();
    }
    @Override
    public ArrayList<Metadata> getExistingMetadata(){
        return this.getiD3v2Tag().getExistingMetadata();
    }

    /**
     * @return the iD3v2Tag
     */
    public ID3v2TagsSchema getiD3v2Tag() {
        return (ID3v2TagsSchema)super.getTagSchema();
    }
}
    
