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

package com.mc2.audio.metadata.source.tags.file;

import java.io.File;
import java.util.ArrayList;
import com.mc2.audio.metadata.source.tags.schema.ID3v2.ID3v2TagsSchema;
import org.jaudiotagger.audio.wav.WavOptions;
import org.jaudiotagger.tag.TagOptionSingleton;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.wav.WavTag;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.exceptions.InvalidAudioFileException;
/**
 *
 * @author marco
 */
public class Wav extends AudioFile {

    public Wav(String path) throws InvalidAudioFileException{
        super(path); 
    }
    
    public Wav(File file) throws InvalidAudioFileException{
         super(file);
    }
    
    @Override
    protected void initOptions(){
        
        /*  this way all informations are presented as id3v2 tags, so we don't 
        *   need to look at wav info tags.
        */
        TagOptionSingleton.getInstance().setWavOptions(WavOptions.READ_ID3_ONLY_AND_SYNC);
        
        //TagOptionSingleton.getInstance().setWavSaveOptions(WavSaveOptions.SAVE_EXISTING_AND_ACTIVE);
        //TagOptionSingleton.getInstance().setWavSaveOrder(WavSaveOrder.INFO_THEN_ID3);

    }
    @Override
    protected void initSchema(){
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
    
