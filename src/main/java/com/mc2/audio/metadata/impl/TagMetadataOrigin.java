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

package com.mc2.audio.metadata.impl;

import java.util.ArrayList;
import org.jaudiotagger.tag.TagField;
import com.mc2.audio.metadata.API.MetadataOrigin;
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
