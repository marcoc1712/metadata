/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags;

import java.util.ArrayList;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagField;

/**
 *
 * @author marco
 */
public interface TagsSource  {

    public String getSourceId();
     
    public Tag getTag();
   
    public ArrayList<TagField> geTagFields();
   
   
}
    
