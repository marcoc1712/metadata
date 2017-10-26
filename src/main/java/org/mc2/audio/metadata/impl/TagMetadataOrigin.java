/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.impl;

import java.util.ArrayList;
import org.jaudiotagger.tag.TagField;
import org.mc2.audio.metadata.MetadataOrigin;
/**
 *
 * @author marco
 */
public class TagMetadataOrigin  implements MetadataOrigin{

    private String source;
    private final String originKey;
    private ArrayList<TagField> tagFields;
    private ArrayList<TagField> discardedTagField;
    private ArrayList<TagField> invalidTagField;

    public TagMetadataOrigin(String source, 
                             String originKey, 
                             ArrayList<TagField> tagFields, 
                             ArrayList<TagField> discardedTagField,
                             ArrayList<TagField> invalidTagField) {
        this.source=source;
        this.originKey=originKey;
        this.tagFields=tagFields;
        this.discardedTagField=discardedTagField;
        this.invalidTagField=invalidTagField;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }
    /**
     * @return the tagFields
     */
    public ArrayList<TagField> getTagFields() {
        return tagFields;
    }
    
    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public ArrayList<String> getValidatedValues() {
        ArrayList<String> out = new ArrayList<>();
        for (TagField tagField : this.getValidatedTagFields()) {
            out.add(tagField.toString());
        }
        return out;
    }

    @Override
    public ArrayList<String> getDiscardedValues() {
        ArrayList<String> out = new ArrayList<>();
        for (TagField tagField : this.getDiscardedTagField()) {
            out.add(tagField.toString());
        }
        return out;
    }
    @Override
    public ArrayList<String> getInvalidValues() {
        ArrayList<String> out = new ArrayList<>();
        for (TagField tagField : this.getInvalidTagField()) {
            out.add(tagField.toString());
        }
        return out;
    }

    /**
     * @return the tagFields
     */
    public ArrayList<TagField> getValidatedTagFields() {
        return this.getTagFields();
    }

    /**
     * @return the discardedTagField
     */
    public ArrayList<TagField> getDiscardedTagField() {
        return this.discardedTagField;
    }
    /**
     * @return the invalidTagField
     */
    public ArrayList<TagField> getInvalidTagField() {
         return this.invalidTagField;
    }
    @Override
    public String getOriginKey() {
        return this.originKey;
    }

}
