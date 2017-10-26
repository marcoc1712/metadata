/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.impl;

import org.mc2.audio.metadata.MetadataOrigin;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marco
 */
public class GenericMetadataOrigin implements MetadataOrigin { 
    private final String source;
    private final String key;
    private ArrayList<String> values = new  ArrayList<>() ;
    private ArrayList<String> discarded = new  ArrayList<>() ;
    private ArrayList<String> invalid = new  ArrayList<>() ;

    public GenericMetadataOrigin(String source,String key, 
                                    List<String> values,  
                                    List<String> discarded,  
                                    List<String> invalid){
        this.source = source;
        this.key = key;
        this.values= (ArrayList)values;
        this.discarded = (ArrayList)discarded;
        this.invalid = (ArrayList)invalid;
    }
    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public String getOriginKey() {
        return this.key;
    }

    @Override
    public ArrayList<String> getValidatedValues() {
        return this.values;
    }

    @Override
    public ArrayList<String> getDiscardedValues() {
         return discarded;
    }

    @Override
    public ArrayList<String> getInvalidValues() {
         return invalid;
    }

}
