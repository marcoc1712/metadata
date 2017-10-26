/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
