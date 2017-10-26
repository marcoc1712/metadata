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

package org.mc2.audio.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 * An unique Metadata.
 * It normally correspond to a single TAG field in an audio file or command 
 * in a cue sheet (the originKey), but if more than one with exactly the same 
 * meaning are found in different sources (i.e. wav id3v2 tags and cue sheet), 
 * then we could store more origin. Every origin then could store more values,
 * both validated or discharged.
 * 
 * @author marco
 */

public class Metadata {

    public enum STATUS {
    
        VALID,
        DISCARDED,
        INVALID,
        DISCARDED_AND_INVALID,
        HAS_DISCARDED_ORIGINS,
        HAS_INVALID_ORIGINS,
        HAS_DISCARDED_AND_INVALID_ORIGINS,
        EMPTY
        
    };
    private final String key;
    private final ArrayList<MetadataOrigin> origins = new ArrayList<>();

    public Metadata(String key, MetadataOrigin origin){
        
        this.key=key;
        this.origins.add(origin);
   
    }
    public Metadata(String key, List<MetadataOrigin> origins){
        
        this.key=key;
        this.origins.addAll(origins);
   
    }   
   
    /**
     * @return the key
     */
    public String getKey() {
        return key;
    }
    /**
     * @return the valid value.
     */
    public String getValidValue(){
        
        String out="";
        
        for (String value : this.getValues()){
            if (!out.isEmpty()){
                out=out+"; "+value;
            } else{
                out=value;
            }
        }
        return out;
    }
    /**
     * @return the discarded value.
     */
    public String getDiscardedValue(){
        
        String out="";
        
        for (String value : this.getDiscardedValues()){
            if (!out.isEmpty()){
                out=out+"; "+value;
            } else{
                out=value;
            }
        }
        return out;
    }
     /**
     * @return the invalid value.
     */
    public String getInvalidValue(){
        
        String out="";
        
        for (String value : this.getInvalidValues()){
            if (!out.isEmpty()){
                out=out+"; "+value;
            } else{
                out=value;
            }
        }
        return out;
    }
    public String getValue(){
        
        String out=getValidValue();
               
        if (out.isEmpty()){
            
            out=getDiscardedValue();
        }
        if (out.isEmpty()){
            
            out=getInvalidValue();
        }
        return out;
    }
     /**
     * @return the status.
     */
    public STATUS getStatus(){
        
        if (getValidValue().isEmpty() && getDiscardedValue().isEmpty() && getInvalidValue().isEmpty()) {return STATUS.EMPTY;}
        if (getValidValue().isEmpty() && getDiscardedValue().isEmpty()) {return STATUS.INVALID;}
        if (getValidValue().isEmpty() && getInvalidValue().isEmpty()) {return STATUS.DISCARDED;}
        if (getValidValue().isEmpty()) {return STATUS.DISCARDED_AND_INVALID;}
        if (getDiscardedValue().isEmpty() && getInvalidValue().isEmpty()) {return STATUS.VALID;}
        if (getDiscardedValue().isEmpty()) {return STATUS.HAS_INVALID_ORIGINS;}  
        if (getInvalidValue().isEmpty()) {return STATUS.HAS_DISCARDED_ORIGINS;}   
        return STATUS.HAS_DISCARDED_AND_INVALID_ORIGINS;
    }
    /**
     * True if the metadata carry no values (valid or discarded).
     * @return isEmpty.
     */
    public boolean isEmpty() {
        return this.getValues().isEmpty() &&  
               this.getDiscardedValues().isEmpty() &&
               this.getInvalidValues().isEmpty();
    }   
    /**
     * @return the values
     */
    public ArrayList<String> getValues() {
      
        ArrayList<String> out= new ArrayList<>();

        for (MetadataOrigin origin : this.getOrigins()){
            //out.addAll(origin.getValidatedValues());
            for (String value : origin.getValidatedValues()){
                if (!out.contains(value)){
                    out.add(value);
                } 
            }
            
        }
        return out;
    }
    /**
     * @return the discarded
    */
    public ArrayList<String> getDiscardedValues() {
      
        ArrayList<String> out= new ArrayList<>();

        for (MetadataOrigin origin : this.getOrigins()){
            //out.addAll(origin.getValidatedValues());
            for (String value : origin.getDiscardedValues()){
                if (!out.contains(value)){
                    out.add(value);
                } 
            }
            
        }
        return out;
    }
    /**
     * @return the discarded
    */
    public ArrayList<String> getInvalidValues() {
      
        ArrayList<String> out= new ArrayList<>();

        for (MetadataOrigin origin : this.getOrigins()){
            //out.addAll(origin.getValidatedValues());
            for (String value : origin.getInvalidValues()){
                if (!out.contains(value)){
                    out.add(value);
                } 
            }
            
        }
        return out;
    }
    /**
     * add s new origin to metadata.
     * @param origin
     */
    public void addOrigin(MetadataOrigin origin) {
        if (!originAlreadyExists(origin)){
            origins.add(origin);
        }
    }
    /**
     * add s new origin to metadata.
     * @param origins
     */
    public void addOrigins(ArrayList<MetadataOrigin> origins) {
        for (MetadataOrigin origin : origins){
            addOrigin(origin);
        }
    }
    /**
     * @return the origins
     */
    public ArrayList<MetadataOrigin> getOrigins() {
        return origins;
    }
    /**
     * @return true ifthorigin is oalready in the origins list.
     */
    private boolean originAlreadyExists(MetadataOrigin toAdd){
        
        for (MetadataOrigin existing : origins){
            if (existing.getOriginKey().equals(toAdd.getOriginKey())) {
                return true;
            }
        }
        return false;
    }
}
    
