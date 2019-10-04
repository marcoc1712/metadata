package com.mc2.audio.metadata.impl;

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


import com.mc2.audio.metadata.fromFiles.AlbumFromFiles;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.MetadataKey.METADATA_KEY;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import com.mc2.audio.metadata.API.Controller;


public class MetadataTest {
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));   
    }
	@Test
    public void Album() throws Exception{
		 String directory = "X:\\musica-test\\TestCase\\04 DSD";
        //String filename = "001_DSD128_Tascam DA-3000.dff";
		//String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
		//String directory = "Z:\\recorder\\Alicia de Larrocha\\Albéniz_ Ibéria; Navarra; Suite Española\\CD1";
		//String directory =  "Z:/recorder/Berlziot - Te Deum, Abbado";
		//String directory = "Z:/Classica/Albinoni, Tomaso/12 Concertos OP. 10 - I Solisti Veneti; Claudio Scimone (ERATO, 1981)/CD 1";
		//String directory = "Y:/Audiophile/aavv/The Best acoustic Album In The World... Ever (2005 EMI)/cd2";

		AlbumFromFiles album = (AlbumFromFiles) Controller.parse(directory);
		//String key = METADATA_KEY.ALBUM.name();
		String key = METADATA_KEY.ALBUM.name();
		
		String value = album.getMetadataValue(key);
		System.out.println("album.getMetadataValue(): "+key+": "+value );
		
		value = album.getMetadataValueFromAlbum(key);
		System.out.println("album.getMetadataValueFromAlbum(): "+key+": "+value );
		
		for (Metadata  metadata : album.getMetadataList()){

            if (metadata.getKey().equals(key)){
                System.out.println("  album.getMetadataValueFromAlbum() metadatLoop getValue         : "+key+": "+metadata.getValue() );
				System.out.println("  album.getMetadataValueFromAlbum() metadatLoop getValidValue    : "+key+": "+metadata.getValidValue());
				System.out.println("  album.getMetadataValueFromAlbum() metadatLoop getDiscardedValue: "+key+": "+metadata.getDiscardedValue());
				System.out.println("  album.getMetadataValueFromAlbum() metadatLoop getInvalidValue  : "+key+": "+metadata.getInvalidValue());
            } 
        }
		
		value = album.getMetadataValueFromTracksIfTheSame(key);
		System.out.println("album.getMetadataValueFromTracksIfTheSame(): "+key+": "+value );
		
		value = album.getMetadataValueFromTracks(key);
		System.out.println("album.getMetadataValueFromTracks(): "+key+": "+value );
			
		for (String value2 : album.getMetadataValuesFromAlbumAndTracks(key)){
			
			System.out.println("  album.getMetadataValuesFromAlbumAndTracks(): "+key+": "+value2 );
		}
		
		
		
		
	}
}
