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

package com.mc2.audio.metadata.source.tags.schema;

import java.util.ArrayList;
import java.util.Iterator;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.KeyNotFoundException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTag;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataOrigin;
import com.mc2.audio.metadata.impl.GenericMetadata;
import com.mc2.audio.metadata.fromFiles.TagMetadataOrigin;
import com.mc2.audio.metadata.source.tags.TagsSource;

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
                Metadata metadata= new GenericMetadata(tagField.getId(),origin);
                
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
