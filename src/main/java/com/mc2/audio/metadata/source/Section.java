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
package com.mc2.audio.metadata.source;

import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataSource;

import static com.mc2.audio.metadata.API.MetadataKey.getAlbumLevelMetadataAlias;
import static com.mc2.audio.metadata.API.MetadataKey.getTrackLevelMetadataAlias;

import java.util.ArrayList;

/**
 *
 * @author marco
 */
public abstract class Section implements MetadataSource {
	
	public static final String ALBUM = "ALBUM";
	public static final String TRACK = "TRACK";
	
	protected String sourceId;
	protected final ArrayList<Metadata> metadataList = new ArrayList<>();

	public Section() {
	}

	/**
	 * @param source the sourceId to set
	 */
	public void setSourceId(String source) {
		this.sourceId = source;
	}

	/**
	 *
	 * @return  the list of metadata of this section.
	 */
	@Override
	public ArrayList<Metadata> getMetadata() {
		return metadataList;
	}

	/**
	 * @return the sourceId
	 */
	@Override
	public String getSourceId() {
		return sourceId;
	}

	public Metadata getMedata(String level, String genericKey) {
		String alias = genericKey;
		String key = level.equals(ALBUM) ? getAlbumLevelMetadataAlias(genericKey) : getTrackLevelMetadataAlias(genericKey);
		if (key != null) {
			alias = key;
		}
		Metadata out = searchMedata(alias);
		if (!alias.equals(genericKey) && (out == null || out.isEmpty())) {
			out = searchMedata(genericKey);
		}
		return out;
	}

	public Metadata searchMedata(String parm) {
		for (Metadata metadata : metadataList) {
			if (metadata.getKey().toUpperCase().equals(parm.toUpperCase())) {
				return metadata;
			}
		}
		return null;
	}
	
}
