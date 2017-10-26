/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.schema;

import java.util.ArrayList;
import java.util.Iterator;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTag;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.MetadataOrigin;
import org.mc2.audio.metadata.impl.TagMetadataOrigin;
import org.mc2.audio.metadata.source.tags.TagsSource;

/**
 *
 * @author marco
 */
public class VorbisCommentTagSchema extends TagSchema  {
    
    VorbisCommentTag vorbisCommentTag;
    
    public VorbisCommentTagSchema(VorbisCommentTag vorbisCommentTag, TagsSource source) {
        super(source);
        this.vorbisCommentTag=vorbisCommentTag;
    }
    
    public ArrayList<Metadata> getExistingMetadata(){
        
        //Vorbis always return ALL metadata, also if with wrong values (i.e. Tracknmber = b).
        return this.getExistingAndValidMetadata();
    }
    
    public ArrayList<Metadata> getMetadata(){
       
        ArrayList<Metadata> out = new ArrayList<>();
        ArrayList<TagField> considered = new ArrayList<>();
        
         for (FieldKey fieldKey : FieldKey.values()) {
            
            Metadata pretty = this.getPrettyMetadata(fieldKey);
           
            MetadataOrigin origin = this.getMetadataOrigin(source.getSourceId(), fieldKey);
           
            considered.addAll(((TagMetadataOrigin)origin).getValidatedTagFields());
            considered.addAll(((TagMetadataOrigin)origin).getDiscardedTagField());
            
            if (!pretty.isEmpty()){out.add(pretty);}
        }

        Iterator<TagField> iterator = source.geTagFields().iterator();
        while(iterator.hasNext()) {
            
            TagField tagField = iterator.next();
        
            if (!considered.contains(tagField)){
                
                considered.add(tagField);

                ArrayList<TagField> discarded = new ArrayList<>();
                discarded.add(tagField);
                TagMetadataOrigin origin= new TagMetadataOrigin(source.getSourceId() , tagField.getId(), new ArrayList<>(), discarded, new ArrayList<>());
                Metadata metadata= new Metadata(tagField.getId(),origin);
                
                out.add(metadata);

            }
        }
        return out;
    
    }

   /*
     Given a FieldKey, search into TagFields the one that should be considered and returns a
     MetadataOrigin builded over this TagField.
    
    */
    public MetadataOrigin getMetadataOrigin(String source, FieldKey fieldKey) {
        
        ArrayList<TagField> filteredList = new ArrayList<>();
        ArrayList<TagField> dischargedList = new ArrayList<>();
            
        try {
            ArrayList<TagField> list = (ArrayList) vorbisCommentTag.getFields(fieldKey);
            for (TagField tagfield : list) {

                filteredList.add(tagfield);

            }
            
        }catch (UnsupportedOperationException | KeyNotFoundException  ex) {
                    
        }
        
        return new TagMetadataOrigin(source, fieldKey.name(), filteredList,  dischargedList, new ArrayList<>());
        
    }
}
