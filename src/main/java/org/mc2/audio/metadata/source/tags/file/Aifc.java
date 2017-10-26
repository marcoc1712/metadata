/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.file;

import java.io.File;
import java.util.ArrayList;
import org.jaudiotagger.tag.aiff.AiffTag;
import org.mc2.audio.metadata.source.tags.schema.ID3v2.ID3v2TagsSchema;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.mc2.audio.metadata.Metadata;
/**
 *
 * @author marco
 */
public class Aifc extends AudioFile {

    public Aifc(String path) throws Exception {
        super(path); 
    }
    
    public Aifc(File file) throws Exception {
         super(file);
    }
    
    @Override
    protected void initOptions() throws Exception {

    }
    @Override
    protected void initSchema() throws Exception {
        AbstractID3v2Tag abstractId3v2Tag = getTag().getID3Tag();
        super.setTagSchema(new ID3v2TagsSchema(abstractId3v2Tag, this));

    }
    
    @Override
    public AiffTag getTag() {
        return (AiffTag) super.getTag();
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
    
