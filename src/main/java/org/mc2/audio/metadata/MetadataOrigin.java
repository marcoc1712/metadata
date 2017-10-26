/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata;

import java.util.ArrayList;

/**
 * An  unique Metadata in a source.
 * It normally correspond to a single TAG field in an audio file or command 
 * in a cue sheet (the originKey), but if more than one with exactly the same 
 * meaning are found, then we could store more values both validated or discarded, 
 * that's why have lists. 
 * 
 * @author marco
 */
public interface MetadataOrigin{
   
    /**
     * @return the source (normally a file pathname) of the metadata.
     */
    public String getSource();

    /**
     * @return the origin tag field or command key.
     */
    public String getOriginKey();
    
    /**
     * @return the origin validated values.
    */
    public ArrayList<String> getValidatedValues();
    
     /**
     * @return the origin discarded values.
    */
    public ArrayList<String> getDiscardedValues();
    /**
     * @return the origin discarded values.
    */
    public ArrayList<String> getInvalidValues();
}
