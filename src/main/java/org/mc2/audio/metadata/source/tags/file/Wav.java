/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.file;

import java.io.File;
import java.util.ArrayList;
import org.mc2.audio.metadata.source.tags.schema.ID3v2.ID3v2TagsSchema;
import org.jaudiotagger.audio.wav.WavOptions;
import org.jaudiotagger.tag.TagOptionSingleton;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.wav.WavTag;
import org.mc2.audio.metadata.Metadata;
/**
 *
 * @author marco
 */
public class Wav extends AudioFile {

    public Wav(String path) throws Exception {
        super(path); 
    }
    
    public Wav(File file) throws Exception {
         super(file);
    }
    
    @Override
    protected void initOptions() throws Exception {
        
        /*  this way all informations are presented as id3v2 tags, so we don't 
        *   need to look at wav info tags.
        */
        TagOptionSingleton.getInstance().setWavOptions(WavOptions.READ_ID3_ONLY_AND_SYNC);
        
        //TagOptionSingleton.getInstance().setWavSaveOptions(WavSaveOptions.SAVE_EXISTING_AND_ACTIVE);
        //TagOptionSingleton.getInstance().setWavSaveOrder(WavSaveOrder.INFO_THEN_ID3);

    }
    @Override
    protected void initSchema() throws Exception {
        AbstractID3v2Tag abstractId3v2Tag = getTag().getID3Tag();
        super.setTagSchema(new ID3v2TagsSchema(abstractId3v2Tag, this));
       
        // We could forget wav info tags, see Init options.
    }
    
    @Override
    public WavTag getTag() {
        return (WavTag) super.getTag();
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
    
