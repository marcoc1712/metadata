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


import Test.utils.TestUtils;
import java.io.File;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

import com.mc2.audio.metadata.API.AlbumBuilder;
import com.mc2.audio.metadata.API.Album;
import com.mc2.audio.metadata.API.Medium;
import com.mc2.audio.metadata.API.RawKeyValuePair;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.Track;
import java.util.ArrayList;

public class AlbumTest {
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    }
	@Test
    public void Album() throws Exception{
        //String directory = "F:\\SVILUPPO\\04-Leia\\TestCase\\DSD SAMPLE";
        //String filename = "001_DSD128_Tascam DA-3000.dff";
		//String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
		String directory = "Z:\\recorder\\Alicia de Larrocha\\Albéniz_ Ibéria; Navarra; Suite Española\\CD1";
		//String directory =  "Z:/recorder/Berlziot - Te Deum, Abbado";
		//String directory = "Z:/Classica/Albinoni, Tomaso/12 Concertos OP. 10 - I Solisti Veneti; Claudio Scimone (ERATO, 1981)/CD 1";
		//String directory = "Y:/Audiophile/aavv/The Best acoustic Album In The World... Ever (2005 EMI)/cd2";
		printAlbum(directory, true);
		
		
	}
	//@Test
    public void duplicatetrackNoSingleFiles() throws Exception{
		
		String directory = "F:\\SVILUPPO\\04-Leia\\TestCase\\02- flac singoli\\020 - Due dischi, track per disco";
		printAlbum(directory);
		
	
	}
	//@Test
    public void duplicatetrackNoDoubleCue() throws Exception{
		
		String directory = "F:\\SVILUPPO\\04-Leia\\TestCase\\01- wav+cue\\040 -2 cue, Tracce per Disco";
		printAlbum(directory);
	
	}
	
	//@Test
    public void duplicatetrackNoSingleCue() throws Exception{
		
		String directory = "F:\\SVILUPPO\\04-Leia\\TestCase\\01- wav+cue\\030 -Tracce per Disco";
		printAlbum(directory);
		//expected error.
	
	}
    //@Test
    public void filesAndRawKeyValuePairs() throws Exception{
        
        //String directory = "Z:\\recorder\\Alicia de Larrocha\\Albéniz_ Ibéria; Navarra; Suite Española\\CD1";
        //String directory = "Z:/Classica/Albinoni, Tomaso/12 Concertos OP. 10 - I Solisti Veneti; Claudio Scimone (ERATO, 1981)/CD 1";
        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
		
		
      
        
        Album album = AlbumBuilder.parse(directory);

        for (RawKeyValuePairSource rawKeyValuePairSource : album.getRawKeyValuePairSources()){
            System.out.println(rawKeyValuePairSource.getSourceId());
            System.out.println("");
            
            for ( RawKeyValuePair pair : rawKeyValuePairSource.getRawKeyValuePairs()){
                System.out.println(pair.getKey()+" - "+pair.getValue());
            } 
        }
        
    }
    //@Test
    public void DSD() throws Exception{
         
        //String directory = "F:\\SVILUPPO\\01 - SqueezeboxServer Plugins\\musica campione\\ProvaAlbumScan";
        //String directory = "F:\\SVILUPPO\\01 - SqueezeboxServer Plugins\\musica campione\\Albinoni Adagios - Anthony Camden, Julia Girdwood (1993 Naxos)";
        //String directory = "F:\\SVILUPPO\\01 - SqueezeboxServer Plugins\\musica campione\\cue con embedde cover";
        //String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        //String directory = "Z:\\recorder\\Alicia de Larrocha\\Albéniz_ Ibéria; Navarra; Suite Española\\CD1";
		String directory = "F:\\SVILUPPO\\04-Leia\\TestCase\\DSD SAMPLE\\Channel classics";
		 
		printAlbum(directory);
	}
	private void printAlbum(String directory) throws Exception{
		printAlbum(directory, false);
	}	
	private void printAlbum(String directory, Boolean single) throws Exception{
       
		Album album = AlbumBuilder.parse(directory);
		        
        System.out.println("========================================================================");
        System.out.println("\n");
        System.out.println("Directory:"+ directory);
        System.out.println("");
        System.out.println("Files:");
        
        for (File file : album.getFileList()){
            System.out.println("- "+file.getName());
        }

        System.out.println("");
        System.out.println("ALBUM:");
        System.out.println("");
		System.out.println("URL: "+album.getUrl());
		System.out.println("");
        System.out.println("Title: "+album.getAlbum() );
        System.out.println("Artist: "+album.getAlbumArtist());
		System.out.println("Composer: "+album.getComposers());
        System.out.println("Genre: "+album.getGenre());
        System.out.println("Date: "+album.getDate());
        System.out.println("Country: "+album.getCountry());
        System.out.println("Label: "+album.getLabel());
        System.out.println("Catalog: "+album.getCatalogNo());
		System.out.println("");
        System.out.println("Media: "+album.getMedia());
		
		System.out.println("discNo: "+album.getDisc());
		System.out.println("discTor: "+album.getTotalDiscs());
		System.out.println("discTitle: "+album.getDiscTitle());
       
        System.out.println("");
        System.out.println("STATUS : "+album.getStatus().name());
        
        System.out.println("");
        System.out.println(" - STATUS MESSAGES:");
        for (StatusMessage statusMessage : album.getMessageList()){
            
            System.out.println(" - "+ statusMessage.toString());
        }
        
        System.out.println("");
        System.out.println(" - METADATA:");
        TestUtils.printMetadata(album.getMetadataList());
		
		ArrayList<? extends Track> tracks = new ArrayList<>();
		if (single){
			tracks = album.getSingleTrackList();
		} else {
			tracks = album.getTrackList();
		}
		
		
        for (Track track : tracks){
                
            System.out.println("");
            System.out.println(" - TRACK: "+track.getTrackId());
			System.out.println("   title: "+track.getTitle());
			System.out.println("   artist: "+track.getArtist());
			System.out.println("   length "+track.getLength()+" ["+track.getLengthString()+"]");
			System.out.println();
			System.out.println("   - URL "+track.getUrl());
			System.out.println("   - PlayList URL "+track.getPlayListUrl());
			System.out.println();
			System.out.println("   - Album Index "+track.getIndex());
			System.out.println("   - PlayList Index "+track.getPlayListIndex());
			
			System.out.println("");
			System.out.println(" - ARTWORKS:");
			TestUtils.printArtworks(track.getCoverArtList());

            System.out.println("");
            System.out.println("   - FILE:");
           
            
            System.out.println("");
            System.out.println("   - METADATA:");

            TestUtils.printMetadata(track.getMetadataList());
            
            System.out.println("");
            System.out.println("   - STATUS : "+track.getStatus().name());
            System.out.println("");
            System.out.println("   - STATUS MESSAGES:");
            for (StatusMessage statusMessage : track.getMessageList()){
            
                System.out.println(" - "+ statusMessage.toString());
            }

        }
		
		for (Medium medium  : album.getMediaList()){
		
			System.out.println("");
			System.out.println(" - MEDIUM: "+medium.getMediumId());
			System.out.println("   index: "+medium.getIndex());
			System.out.println("   type: "+medium.getType());
			System.out.println("   number: "+medium.getNumber());
			System.out.println("   title: "+medium.getTitle());
			System.out.println();
			
			for (Track track : medium.getTrackList()){
                
				System.out.println("");
				System.out.println(" - TRACK: "+track.getTrackId()+" length "+track.getLength()+" ["+track.getLengthString()+"]");
				System.out.println();
				System.out.println("   - Album Index "+track.getIndex());
				System.out.println("   - PlayList Index "+track.getPlayListIndex());
			}
			
			System.out.println(" - TOC: "+medium.getToc());
		}
		
		System.out.println("");
		System.out.println(" - ARTWORKS:");
		TestUtils.printArtworks(album.getcoverArtList());
    }
}
