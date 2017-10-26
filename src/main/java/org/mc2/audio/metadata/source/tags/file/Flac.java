/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.file;

import java.io.File;
import java.util.ArrayList;
import org.jaudiotagger.tag.flac.FlacTag;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTag;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.tags.schema.VorbisCommentTagSchema;

/**
 *
 * @author marco
 */
public class Flac extends AudioFile{
    
    public Flac(String path) throws Exception {
        super(path); 
    }
    
    public Flac(File file) throws Exception {
         super(file);
    }
    @Override
    protected void initOptions() throws Exception {
        
        // i.e. 
        //TagOptionSingleton.getInstance().setVorbisAlbumArtistReadOptions(VorbisAlbumArtistReadOptions.READ_ALBUMARTIST);
        //TagOptionSingleton.getInstance().setVorbisAlbumArtistSaveOptions(VorbisAlbumArtistSaveOptions.WRITE_ALBUMARTIST);

    }  
    @Override
    protected void initSchema() throws Exception {
        
        VorbisCommentTag vorbisCommentTag = getTag().getVorbisCommentTag();
        super.setTagSchema(new VorbisCommentTagSchema(vorbisCommentTag, this));

    }
    @Override
    public FlacTag getTag() {
        return (FlacTag) super.getTag();
    } 
    /**
     * @return the vorbisCommentTagSchema
     */
    public VorbisCommentTagSchema getVorbisCommentTagSchema() {
        return (VorbisCommentTagSchema)super.getTagSchema();
    }
    @Override
    public ArrayList<Metadata> getMetadata() {
        //return this.vorbisCommentTagSchema.getMetadata();
        return getVorbisCommentTagSchema().getMetadata();
    }
    @Override
    public ArrayList<Metadata> getExistingMetadata(){

        return getVorbisCommentTagSchema().getExistingMetadata();
    }
    
}
    
