/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source;

import java.util.ArrayList;
import org.mc2.audio.metadata.Metadata;


/**
 *
 * @author marco
 */
public interface MetadataSource  {

    public String getSourceId();
    public ArrayList<Metadata> getMetadata();
    
   
}
    
