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

import com.mc2.audio.metadata.API.MetadataKey;
import com.mc2.audio.metadata.API.MetadataRow;

/**
 *
 * @author marco
 */
public class GenericMetadataRow implements MetadataRow {
	
	private final MetadataKey.METADATA_LEVEL level;
	private final MetadataKey.METADATA_CATEGORY category;
	private final MetadataKey.METADATA_KEY metadataKey;

	private final String trackNo;
	
	private final String categoryName;
	private final String keyName;
	
	private String albumLevelValue;
	private final String trackLevelValue;
	private final String value;
	
	public GenericMetadataRow (MetadataKey.METADATA_LEVEL level, 
							 MetadataKey.METADATA_CATEGORY category,
							 MetadataKey.METADATA_KEY metadataKey,
							 String trackNo,
							 String categoryName,
							 String keyName,
							 String albumLevelValue,
							 String trackLevelValue,
							 String value){
		
		this.level = level;
		this.category = category;
		this.metadataKey = metadataKey;
		this.trackNo = trackNo;
		this.categoryName = categoryName;
		this.keyName = keyName;
		this.albumLevelValue = albumLevelValue;
		this.trackLevelValue = trackLevelValue;
		this.value = value;

	}

	/**
	 * @return the level
	 */
	public MetadataKey.METADATA_LEVEL getLevel() {
		return level;
	}

	/**
	 * @return the category
	 */
	public MetadataKey.METADATA_CATEGORY getCategory() {
		return category;
	}

	/**
	 * @return the metadataKey
	 */
	public MetadataKey.METADATA_KEY getMetadataKey() {
		return metadataKey;
	}

	/**
	 * @return the categoryName
	 */
	@Override
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @return the keyName
	 */
	@Override
	public String getKeyName() {
		return keyName;
	}

	/**
	 * @return the albumLevelValue
	 */
	@Override
	public String getAlbumLevelValue() {
		return albumLevelValue;
	}

	protected void setAlbumLevelValue(String value) {
		albumLevelValue = value;
	}
	
	/**
	 * @return the trackLevelValue
	 */
	@Override
	public String getTrackLevelValue() {
		return trackLevelValue;
	}

	/**
	 * @return the value
	 */
	@Override
	public String getValue() {
		return value;
	}

	/**
	 * @return the trackNo
	 */
	@Override
	public String getTrackNo() {
		return trackNo;
	}
}

