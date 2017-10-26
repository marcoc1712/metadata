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

package org.mc2.audio.metadata.source.tags.file;

import java.io.File;
import java.util.ArrayList;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagField;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.MetadataSource;
import org.mc2.audio.metadata.source.tags.TagsSource;
import org.mc2.audio.metadata.source.tags.schema.TagSchema;


/**
 *
 * @author marco
 */
public abstract class AudioFile implements TagsSource, MetadataSource{

    
    
    private final File file;
    private org.jaudiotagger.audio.AudioFile audiofile;
    private String path;
    private Tag tag;
    private TagSchema tagSchema;

    public AudioFile (String  path) throws Exception {
        this.file = new File(path);
        init(file);
    }
     
    public AudioFile(File file) throws Exception {
        this.file = file;
        init(file);
    }
    
    protected abstract void  initOptions()throws Exception;
    protected abstract void  initSchema()throws Exception;
    
    protected final void init(File file)throws Exception {
        initOptions();
        audiofile=  AudioFileIO.read(file);    
        tag = this.audiofile.getTag();
        path = this.file.getCanonicalPath();
        initSchema();
    }
   /**
     * @return the file
     */
    public File getFile() {
        return file;
    }
    /**
     * @return the audiofile
     */
    public org.jaudiotagger.audio.AudioFile getAudiofile() {
        return audiofile;
    }
    /**
     * @return the path
     */   
    public String getPath() {
        return path;
    }
    /**
     *
     * @return the path as sourceId
     */
    @Override
    public String getSourceId() {
        return path;
    }
    /**
     * @return the tag
     */
    @Override
    public Tag getTag() {
        return tag;
    }
    /**
     * @return the tagSchema
     */
    protected TagSchema getTagSchema() {
        return tagSchema;
    }

    /**
     * @param tagSchema the tagSchema to set
     */
    protected void setTagSchema(TagSchema tagSchema) {
        this.tagSchema = tagSchema;
    }
    /* list all the tagField in the AudioFile.  
    */ 
    @Override
    public ArrayList<TagField> geTagFields(){

        return getTagSchema().geTagFields();
    }
    
    /* metadata by the Audiofile in the 'generic' FieldKey + values format.
     * Metadata for fieldKeys with invalid value or with key not listed in FieldKey are discarded.
     *
     * This form return the "nasty" KEY: Type="" + Value format, not really good for displaying.
    */
    @Override
    public abstract ArrayList<Metadata> getMetadata();
    
    /* list all the metadata by the Audiofile in the 'generic' FieldKey + values format.
     * Metadata for fieldKeys with invalid value are reported and value is in the form:
     *
     * [TAGFIELD] tagValue (note that tagValue contains the Type= label and value is quoted by "").
     *  
     * NOTE: Reported keys are still only those in FieldKey enumerator.
    */
    public abstract ArrayList<Metadata> getExistingMetadata();

    /* list all the metadata by the Audiofile in the 'generic' FieldKey + values format.
     * Metadata for fieldKeys with invalid value or with key not listed 
     * in org.jaudiotagger.tag.FieldKey are discarded.
     */
    public ArrayList<Metadata> getExistingAndValidMetadata(){

        return getTagSchema().getExistingAndValidMetadata();
    } 
    /* Metadata by the Audiofile in the 'generic' FieldKey + values format.
     * Metadata for fieldKeys with invalid value or with key not listed in FieldKey are discarded.
     *
     * This form return the "pretty" KEY: Value format, without the Type="" enclosure for Value.
     */
    public Metadata getPrettyMetadata(FieldKey fieldKey) {
        return getTagSchema().getPrettyMetadata(fieldKey);
    }
    
    public Metadata getNastyMetadata(FieldKey fieldKey) {
        return getTagSchema().getNastyMetadata(fieldKey);
    }
    
    public Metadata getMetadata(FieldKey fieldKey) {
        return getTagSchema().getMetadata(fieldKey);
    }
}
