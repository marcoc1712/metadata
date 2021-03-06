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

import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataKey.METADATA_CATEGORY;
import com.mc2.audio.metadata.API.MetadataKey.METADATA_LEVEL;
import com.mc2.audio.metadata.API.Track;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marco
 */

public class MetadataTable {
	
	private final METADATA_LEVEL level;
	private final AbstractAlbum album;
	private final AbstractTrack track;
	private final List<GenericMetadataRow> metadataRowList;
	
	public MetadataTable(AbstractTrack track){
		
		this.level = METADATA_LEVEL.TRACK;
		this.album = track.getAlbum();
		this.track = track;
		this.metadataRowList = new ArrayList<>();
		
		ArrayList<String> keys= new ArrayList<>();
		
		for (Metadata metadata : this.track.getMetadataList()){

			addTrackMetadata(metadata, keys);
		}
		if (this.album != null) {
			for (Metadata metadata : this.album.getMetadataList()){

				addTrackMetadata(metadata, keys);
			}
		}
		
	}
	
	public MetadataTable(AbstractAlbum album){
		
		this.level = METADATA_LEVEL.ALBUM;
		this.album = album;
		this.track = null;
		this.metadataRowList = new ArrayList<>();
		
		ArrayList<String> keys= new ArrayList<>();

		for (Metadata metadata : this.album.getMetadataList()){

			addAlbumMetadata(metadata, keys);
		}
		
		for (Track track : this.album.getTrackList() ){
			for (Metadata metadata : track.getMetadataList()){
				addAlbumMetadata(metadata, keys);
			}
		}
		
		for (GenericMetadataRow albumMetadataRow : this.metadataRowList){
			
			if  (albumMetadataRow.getKeyName() != null && 
				 albumMetadataRow.getTrackLevelValue() != null &&
				 !albumMetadataRow.getTrackLevelValue().isEmpty()){		
				
				for (Track track : album.getTrackList()){
					
					for (GenericMetadataRow trackMetadataRow : ((AbstractTrack)track).getMetadataTable().metadataRowList){
						
						if(trackMetadataRow.getKeyName() != null &&
							trackMetadataRow.getKeyName().equals(albumMetadataRow.getKeyName()) &&
							trackMetadataRow.getAlbumLevelValue().isEmpty()	){

							trackMetadataRow.setAlbumLevelValue(albumMetadataRow.getTrackLevelValue());
						}
						
					}
				}
			}
		}
	}
	protected List<GenericMetadataRow> getMetadataRows(){
		
		return metadataRowList;
	}
	private void addAlbumMetadata(Metadata metadata, ArrayList<String> keys){
		
		String metadataKey= metadata.getKey();
		String metadataCategoryName= "";
		if (metadata.getAlbumLevelCategory() != null){
			
			if (metadata.getAlbumLevelCategory().equals(METADATA_CATEGORY.NOT_TO_SHOW) ||
			    metadata.getAlbumLevelCategory().equals(METADATA_CATEGORY.RESERVED)){
				return;
			}
			metadataKey = metadata.getAlbumLevelMetadataKey().name();
			metadataCategoryName =  metadata.getAlbumLevelCategory().name();
		}

		if (!keys.contains(metadataKey)){

			keys.add(metadataKey);
			
			String albumValue=this.album.cleanMetadada(this.album.getMetadataValueFromAlbum(metadataKey));
			String tracksCommonValue =this.album.cleanMetadada(this.album.getMetadataValueFromTracksIfTheSame(metadataKey));
			String value;
			
			if (albumValue.equals(tracksCommonValue)) value = albumValue;
			else if (albumValue.isEmpty()) value = tracksCommonValue;
			else value = "";

			if (value != null && !value.isEmpty()){

				GenericMetadataRow metadataRow = 
					new GenericMetadataRow(METADATA_LEVEL.ALBUM,
										 metadata.getAlbumLevelCategory(),
										 metadata.getAlbumLevelMetadataKey(),
										 "",
										 metadataCategoryName,
										 metadataKey,
										 albumValue,
										 tracksCommonValue,
										 value
					);
				metadataRowList.add(metadataRow);
			}
		}
	}
	private void addTrackMetadata(Metadata metadata, ArrayList<String> keys){
		
		String metadataKey= metadata.getKey();
		String metadataCategoryName= "";
		if (metadata.getTrackLevelCategory() != null){
			
			if (metadata.getTrackLevelCategory().equals(METADATA_CATEGORY.NOT_TO_SHOW) ||
			    metadata.getTrackLevelCategory().equals(METADATA_CATEGORY.RESERVED)){
				return;
			}
			metadataKey = metadata.getTrackLevelMetadataKey().name();
			metadataCategoryName =  metadata.getTrackLevelCategory().name();
		}

		if (!keys.contains(metadataKey)){

			keys.add(metadataKey);

			String albumValue   = this.album != null ? this.album.getMetadataValueFromAlbum(metadataKey) : "";
			String tracksValue  = this.track.getMetadataFromTrack(metadataKey);
			
			String value  = tracksValue != null && !tracksValue.isEmpty() ? tracksValue : albumValue;

			if (value != null && !value.isEmpty()){

				GenericMetadataRow metadataCategory = 
					new GenericMetadataRow(METADATA_LEVEL.TRACK,
										 metadata.getTrackLevelCategory(),
										 metadata.getTrackLevelMetadataKey(),
										 this.track.getTrackNumber(),
										 metadataCategoryName,
										 metadataKey,
										 albumValue,
										 tracksValue,
										 value
					);
				metadataRowList.add(metadataCategory);
			}
		}
	}
	
	
	public ArrayList<GenericMetadataRow> getMetadaPerCategory(METADATA_CATEGORY category){
		
		ArrayList<GenericMetadataRow> out = new ArrayList<>();
		
		for (GenericMetadataRow mk : metadataRowList){
			
			if  (mk.getCategory() != null && mk.getCategory().equals(category)){		
				out.add(mk);
			}
		}
		return out;
	}
	public ArrayList<GenericMetadataRow> getMetadaWithNoCategory(){
		
		ArrayList<GenericMetadataRow> out = new ArrayList<>();
		
		for (GenericMetadataRow mk : metadataRowList){
			
			if  (mk.getCategory() == null){		
				out.add(mk);
			}
		}
		return out;
	}
}

