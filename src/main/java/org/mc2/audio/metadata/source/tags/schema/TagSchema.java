/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.schema;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.TagField;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.MetadataOrigin;
import org.mc2.audio.metadata.impl.GenericMetadataOrigin;
import org.mc2.audio.metadata.source.tags.TagsSource;

/**
 *
 * @author marco
 */
public abstract class TagSchema {
    
    protected final TagsSource source;
    
    public TagSchema(TagsSource source) {
        this.source=source;
    }
    /* list all the tagField in the Tag Schema.  
    */ 
    public ArrayList<TagField> geTagFields(){
        
        ArrayList<TagField> out= new ArrayList<>();
        try{
            Iterator<TagField> fields =  source.getTag().getFields();
            while(fields.hasNext()) {
                TagField tagfield = fields.next();
                out.add(tagfield);
            }   
        } catch (NullPointerException ex){
        
        }
        return out;
        
    }
    /* list all the metadata  builded form the  TagSchema in
     * the 'generic' FieldKey + values format.
     * Metadata for fieldKeys with invalid value or with key not listed 
     * in org.jaudiotagger.tag.FieldKey are discarded.
     */
    public ArrayList<Metadata> getExistingAndValidMetadata(){
        
        ArrayList<Metadata> out = new ArrayList<>();
        for (FieldKey fieldKey : FieldKey.values()) {
            
            try {
                
                Metadata metadata = getPrettyMetadata(fieldKey);
                if (!metadata.isEmpty()){
                    out.add(metadata);
                }  
                
            } catch (UnsupportedOperationException | KeyNotFoundException  ex) {
                
            }
        }
        return out;
    } 
    
    /* Metadata in the 'generic' FieldKey + values format.
     * Metadata for fieldKeys with invalid value or with key not listed in FieldKey are discarded.
     *
     * This form return the "pretty" KEY: Value format, without the Type="" enclosure for Value.
     */
    public Metadata getPrettyMetadata(FieldKey fieldKey) {

        try {
            List<String> values = source.getTag().getAll(fieldKey);
            GenericMetadataOrigin origin = new GenericMetadataOrigin(source.getSourceId(), "", values,  new ArrayList<>(),new ArrayList<>());
            Metadata metadata = new Metadata(fieldKey.name(), origin);
            return metadata;
        } catch (UnsupportedOperationException | KeyNotFoundException | NullPointerException ex) {
            GenericMetadataOrigin origin = new GenericMetadataOrigin(source.getSourceId(), "", new ArrayList<>(),  new ArrayList<>(),new ArrayList<>());
            Metadata metadata = new Metadata(fieldKey.name(), origin);
            return metadata;
        }
    }
    /* Metadata by the Audiofile in the 'generic' FieldKey + values format.
     * Metadata for fieldKeys with invalid value or with key not listed in FieldKey are reported.
     *
     * This form return the "nasty" KEY: Value format, with the Type="" enclosure for Value.
     */
    public Metadata getNastyMetadata(FieldKey fieldKey) {
        ArrayList<MetadataOrigin> origins = new ArrayList<>();
        Iterator<TagField> iterator = source.getTag().getFields(fieldKey).iterator();
        while (iterator.hasNext()) {
            TagField tagField = iterator.next();
            List<String> values= new ArrayList<>();
            values.add(tagField.getId());
            GenericMetadataOrigin origin = new GenericMetadataOrigin(source.getSourceId(), "", values, new ArrayList<>(),new ArrayList<>());
            origins.add(origin);
        }
        Metadata metadata = new Metadata(fieldKey.name(), origins);
        return metadata;
    }
    
    public Metadata getMetadata(FieldKey fieldKey) {
         
         return (getPrettyMetadata(fieldKey).isEmpty() ? 
                 getNastyMetadata(fieldKey) : getPrettyMetadata(fieldKey));
    }
}
