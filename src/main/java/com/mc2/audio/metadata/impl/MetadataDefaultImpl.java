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
import java.util.List;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataKey;
import com.mc2.audio.metadata.API.MetadataKey.METADATA_CATEGORY;
import com.mc2.audio.metadata.API.MetadataKey.METADATA_KEY;
import com.mc2.audio.metadata.API.MetadataOrigin;

/**
 * An unique MetadataDefaultImpl.
 * It normally correspond to a single TAG field in an audio file or COMMAND 
 * in a cue sheet, but if more than one instance is found either in different 
 * sources (i.e. wav id3v2 tags and cue sheet) or different command/tags in same 
 * file (i.e GATALOG and REM CATALOGNUMBER in cue), then we store more origin for
 * the same Matadata.
 * 
 * Note that every metadata origin could store more values by itself.
 * 
 * @author marco
 */

public class MetadataDefaultImpl implements Metadata {

    private final boolean defaultMergeDiscarded=false;
    private final boolean defaultMergeInvalid=false;
    
    String key;
    private final ArrayList<MetadataOrigin> origins = new ArrayList<>();

    public MetadataDefaultImpl(String key, MetadataOrigin origin){
        
        this.key=key;
        this.origins.add(origin);
   
    }
    public MetadataDefaultImpl(String key, List<MetadataOrigin> origins){
        
        this.key=key;
        this.origins.addAll(origins);
   
    }   
   
    /**
     * @return the key
     */
    @Override
    public String getKey() {
        return key;
    }
    /**
     * @return the value 
     */
    @Override
    public String getValue(){
        return getValue(defaultMergeDiscarded,defaultMergeInvalid);
    }
    /** Return the value, merging values accordingly with the input flags.
     * @param mergeDiscarded
     * @param mergeInvalid
     * @return the value.
     */
    @Override
    public String getValue(boolean mergeDiscarded, boolean mergeInvalid){
    
		String out="";
		
		for (String value : this.getValues(mergeDiscarded, mergeInvalid)){
            
			if (!out.isEmpty()){
                out=out+SEPARATOR+value;
            } else{
                out=value;
            }
        }
        return out;
    }
	
    /**
     * @return the valid value.
     */
    @Override
    public String getValidValue(){
        
        String out="";
        
        for (String value : this.getValidValues()){
            if (!out.isEmpty()){
                out=out+SEPARATOR+value;
            } else{
                out=value;
            }
        }
        return out;
    }
    /**
     * @return the discarded value.
     */
    @Override
    public String getDiscardedValue(){
        
        String out="";
        
        for (String value : this.getDiscardedValues()){
            if (!out.isEmpty()){
                out=out+SEPARATOR+value;
            } else{
                out=value;
            }
        }
        return out;
    }
    /**
     * @return the invalid value.
     */
    @Override
    public String getInvalidValue(){
        
        String out="";
        
        for (String value : this.getInvalidValues()){
            if (!out.isEmpty()){
                out=out+SEPARATOR+value;
            } else{
                out=value;
            }
        }
        return out;
    }

    /**
     * @return the status.
     */
    @Override
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
    @Override
    public boolean isEmpty() {
        return this.getValidValues().isEmpty() &&  
               this.getDiscardedValues().isEmpty() &&
               this.getInvalidValues().isEmpty();
    }  
	/**
     * @return the values
    */
	
    @Override
    public ArrayList<String> getValues(){
        return getValues(defaultMergeDiscarded,defaultMergeInvalid);
    }
    /** Return the value, merging values accordingly with the input flags.
     * @param mergeDiscarded
     * @param mergeInvalid
     * @return the values.
     */
	@Override
	public ArrayList<String> getValues(boolean mergeDiscarded, boolean mergeInvalid){
		
		ArrayList<String> out= getValidValues();
		
		if (out.isEmpty()){
            
            out=getDiscardedValues();
			
		} else if (mergeDiscarded && !getDiscardedValues().isEmpty()){
			
			for (String value : this.getDiscardedValues()){
				
				if (!out.contains(value)){

					out.add(value);

				} 
			}
		} 
		
		if (out.isEmpty()){
            
            out=getInvalidValues();
        
        }else if (mergeInvalid && !getInvalidValues().isEmpty()){
            
            for (String value : this.getInvalidValues()){
				
				if (!out.contains(value)){

					out.add(value);

				} 
			}
            
        }
        return out;
        		
	}
    /**
     * @return the valid values
     */
    @Override
    public ArrayList<String> getValidValues() {
      
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
     * @return the discarded values
    */
    @Override
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
     * @return the invalid values
    */
    @Override
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
    @Override
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

	@Override
	public METADATA_KEY getAlbumLevelMetadataKey() {
		
		if (this.getKey() == null ) return null;
		
		String generic=this.getKey();
		String alias = MetadataKey.getAlbumLevelMetadataAlias(generic);
		
		if (alias != null ){generic = alias;}
		
		return MetadataKey.getByName(generic);
	}

	@Override
	public METADATA_KEY getTrackLevelMetadataKey() {
		
		if (this.getKey() == null ) return null;
		
		String generic=this.getKey();
		String alias = MetadataKey.getTrackLevelMetadataAlias(generic);
		
		if (alias != null){generic = alias;}
		
		return MetadataKey.getByName(generic);
	}
	
	@Override
	public METADATA_CATEGORY getAlbumLevelCategory() {
		
		
		METADATA_KEY mk = getAlbumLevelMetadataKey();
		
		if (mk == null) return null;

		return mk.getAlbumLevelCategory();	
	}

	@Override
	public MetadataKey.METADATA_CATEGORY getTrackLevelCategory() {
	
		METADATA_KEY mk = getTrackLevelMetadataKey();
		
		if (mk == null) return null;

		return mk.getTrackLevelCategory();	
	}
}
    
