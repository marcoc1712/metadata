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

package com.mc2.audio.metadata.utils;

import com.mc2.audio.metadata.API.Album;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.TagField;
import com.mc2.audio.metadata.API.CoverArt;
import com.mc2.audio.metadata.API.Medium;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.Track;
import com.mc2.audio.metadata.source.tags.file.AudioFile;

/**
 *
 * @author marco
 */
public class TestUtils {
    
   
    public void dummy(){}

    public static void printAudioFile(AudioFile audiofile) throws IOException, URISyntaxException{
    
        
        System.out.println("FILE: "+audiofile.getFile().getCanonicalPath());
        
        System.out.println("AUDIO HEADAER:"); 
        TestUtils.printAudioHeader(audiofile.getAudioHeader());

        System.out.println("TAGS:");
        TestUtils.printTagFields(audiofile.geTagFields());

        System.out.println("METADATA:");
        TestUtils.printMetadata(audiofile.getMetadata());
        
        System.out.println("ARTWORKS:");
        
        printArtworks(audiofile.getEmbeddedArtworks());
    
    }
	public static void printArtworks(ArrayList<CoverArt> artworks) throws IOException, URISyntaxException{
		printArtworks(artworks,"");
	}
    public static void printArtworks(ArrayList<CoverArt> artworks, String ident) throws IOException, URISyntaxException{
        
        for (CoverArt artwork : artworks){

            System.out.println(ident+ artwork.toString());
        }
    
    }
    public static void printAudioHeader(AudioHeader audioHeader){
        
        System.out.println("- audioDataLength: "+audioHeader.getAudioDataLength());
        System.out.println("- audioDataStartPosition: "+audioHeader.getAudioDataStartPosition());
        System.out.println("- audioDataEndPosition: "+audioHeader.getAudioDataEndPosition());
        System.out.println("- byteRate: "+audioHeader.getByteRate());
        System.out.println("- bitRate: "+audioHeader.getBitRate());
        System.out.println("- bitRateAsNumber: "+audioHeader.getBitRateAsNumber());
        System.out.println("- sampleRate: "+audioHeader.getSampleRate());
        System.out.println("- sampleRateAsNumber: "+audioHeader.getSampleRateAsNumber());
        System.out.println("- bitsPerSample: "+audioHeader.getBitsPerSample());
        System.out.println("- channels: "+audioHeader.getChannels());
        System.out.println("- encodingType: "+audioHeader.getEncodingType());
        System.out.println("- format: "+audioHeader.getFormat());
        System.out.println("- noOfSamples: "+audioHeader.getNoOfSamples());
        System.out.println("- isVariableBitRate: "+audioHeader.isVariableBitRate());
        System.out.println("- trackLength: "+audioHeader.getTrackLength());
        System.out.println("- preciseTrackLength: "+audioHeader.getPreciseTrackLength());
        System.out.println("- isLossless: "+audioHeader.isLossless());
        
    }
    
    public static void printTagFields(ArrayList<TagField> tagFields){
        
        for (TagField tagfield : tagFields){
            
            System.out.println("- "+tagfield.getId()+": "+tagfield.toString());
        }
    }
    
    public static void printMetadata(ArrayList<Metadata> metadataList){
		
		printMetadata(metadataList,"  ");
	}
	public static void printMetadata(ArrayList<Metadata> metadataList, String ident){
        
        for (Metadata metadata : metadataList){
            printMetadata(metadata, ident);
        }
    }
    
    public static void printMetadata(Metadata metadata,String ident){
        
        String status="";
        switch (metadata.getStatus()) {
            case VALID:                             status="     "; break;
            case INVALID:                           status=" !   "; break;
            case DISCARDED:                         status=" +   "; break;
            case DISCARDED_AND_INVALID:             status=" !+  "; break;
            case EMPTY:                             status=" -   "; break;
            case HAS_INVALID_ORIGINS:               status=" >!  "; break;
            case HAS_DISCARDED_ORIGINS:             status=" >+  "; break;
            case HAS_DISCARDED_AND_INVALID_ORIGINS: status=" >!+ "; break;
            
            default: break;
        }
        
        System.out.println(ident+status+metadata.getKey()+": "+metadata.getValue(true, true));
        
    }
	public static void printAlbum(Album album) throws Exception{
   
        System.out.println("========================================================================");
       
		System.out.println("");
		System.out.println("URL: "+album.getId());
		System.out.println("");
        System.out.println("ALBUM:");
		System.out.println("");
        System.out.println("Title: "+album.getAlbum() );
        System.out.println("Artist: "+album.getAlbumArtist());
		System.out.println("Composer: "+album.getComposers());
		System.out.println("Track level Composer: "+album.getTrackLevelComposers());
		System.out.println("Composers: "+album.getComposers());
		System.out.println("");
        System.out.println("Genre: "+album.getGenre());
        System.out.println("Date: "+album.getDate());
        System.out.println("Country: "+album.getCountry());
        System.out.println("Label: "+album.getLabel());
        System.out.println("Catalog: "+album.getCatalogNo());
		System.out.println("");
		System.out.println("discNo: "+album.getDisc());
		System.out.println("discTot: "+album.getTotalDiscs());
		System.out.println("discTitle: "+album.getDiscTitle());
        System.out.println("");
		System.out.println("Musicbrainz Release Id: "+album.getMbReleaseId());
		System.out.println("");
		System.out.println("Format: "+album.getFormat());
		System.out.println("SampleRate: "+album.getSampleRate());
		System.out.println("BitsPerSample: "+album.getBitsPerSample());
		System.out.println("Channels: "+album.getChannels());
		System.out.println("VariableBitRate: "+album.isVariableBitRate());
		System.out.println("BitRate: "+album.getBitRate());
		System.out.println("Lossless: "+album.isLossless());
		System.out.println("HiRes: "+album.isHiRes());
		System.out.println("");
		System.out.println("Cover art: "+album.getCoverArt().toString());
		System.out.println("- COVER ARTS:");
		TestUtils.printArtworks(album.getcoverArtList(),"   ");
		
		System.out.println("");
		System.out.println("Media: "+album.getMedia());
		System.out.println("Total Length: "+album.getTotalLength());
		System.out.println("");
		
		System.out.println("- MEDIA: ");
		for (Medium medium  : album.getMediaList()){
		
			System.out.println("");
			System.out.println("  MEDIUM: "+medium.getMediumId());
			System.out.println("    index: "+medium.getIndex());
			System.out.println("    type: "+medium.getType());
			System.out.println("    number: "+medium.getNumber());
			System.out.println("    title: "+medium.getTitle());
			System.out.println("");
			printTracks(medium.getTrackList());
			System.out.println("");	
			System.out.println("    TOC: "+medium.getToc());
			System.out.println("");
		}

        System.out.println("- TRACKLIST:");
		System.out.println("");
        printTracks(album.getTrackList());
		System.out.println("");
		
        System.out.println("- SINGLE TRACKLIST:");
		System.out.println("");
		printTracks(album.getSingleTrackList());
		System.out.println("");
		
		System.out.println("");
        System.out.println("- METADATA:");
        TestUtils.printMetadata(album.getMetadataList(), "");	
		
		/*
		System.out.println("");
        System.out.println(" - METADATA ROWS:");

	
        TestUtils.printMetadataRows(album.getAwardMetadataList());
		TestUtils.printMetadataRows(album.getCollectionMetadataList());
		TestUtils.printMetadataRows(album.getCommentMetadataList());
		TestUtils.printMetadataRows(album.getCreditMetadataList());
		TestUtils.printMetadataRows(album.getDescriptionMetadataList());
		TestUtils.printMetadataRows(album.getGoodiesMetadataList());
		TestUtils.printMetadataRows(album.getMediaDescriptorMetadataList());
		TestUtils.printMetadataRows(album.getMetadaWithNoCategory());
		TestUtils.printMetadataRows(album.getMiscellaneaMetadataList());
		TestUtils.printMetadataRows(album.getMusicDescriptorMetadataList());
		TestUtils.printMetadataRows(album.getRatingMetadataList());
		TestUtils.printMetadataRows(album.getURLMetadataList());
		TestUtils.printMetadataRows(album.getWorkDescriptorMetadataList());
		*/
		System.out.println("");
		System.out.println("- RAW KEY AND VALUE PAIR SOURCES:");
        for (RawKeyValuePairSource rawKeyValuePairSource : album.getRawKeyValuePairSources()){
            
            System.out.println("  "+ rawKeyValuePairSource.getSourceId());
        }
		System.out.println("");
        System.out.println("Status : "+album.getStatus().name());
        System.out.println("- STATUS MESSAGES:");
        for (StatusMessage statusMessage : album.getMessageList()){
            
            System.out.println("  "+ statusMessage.toString());
        }
		System.out.println("");
		System.out.println("END");
		System.out.println("");
		System.out.println("");
    }
	private static void printTracks(ArrayList<? extends Track> tracks) throws IOException, URISyntaxException{
		printTracks(tracks,"   ");
	}
	private static void printTracks(ArrayList<? extends Track> tracks, String ident) throws IOException, URISyntaxException{
	
		for (Track track : tracks){
                
            System.out.println("");
            System.out.println(ident+"- TRACK: "+track.getTrackId());
			System.out.println(ident+"  trackNo : "+track.getTrackNo());
			System.out.println(ident+"  length "+track.getLength()+" ["+track.getLengthString()+"]");
			System.out.println();
			System.out.println(ident+"  album: "+(track.getAlbum() == null ? "" : track.getAlbum().getAlbum()));
			System.out.println(ident+"  album index : "+track.getIndex());
			System.out.println();
			System.out.println(ident+"  URL "+track.getUrl());
			System.out.println(ident+"  PlayList URL "+track.getPlayListUrl());
			System.out.println(ident+"  PlayList index : "+track.getPlayListIndex());
			System.out.println();
			System.out.println(ident+"  work: "+track.getWork());
			System.out.println(ident+"  title: "+track.getTitle());
			System.out.println(ident+"  version: "+track.getVersion());
			System.out.println();
			System.out.println(ident+"  artist: "+track.getArtist());
			System.out.println("");
			
			System.out.println(ident+"  cover art: "+(track.getCoverArt() == null ? "" : track.getCoverArt().toString()));
			System.out.println(ident+"  - COVER ARTS:");
			TestUtils.printArtworks(track.getCoverArtList(),ident+"    ");
			System.out.println("");
			System.out.println(ident+"  Media Type: "+track.getMediaType());
			System.out.println(ident+"  Medium: "+track.getMedium());
			System.out.println("");
			System.out.println(ident+"  discNo: "+track.getDiscNo());
			System.out.println(ident+"  discTitle: "+track.getDiscTitle());
			System.out.println("");
			System.out.println(ident+"  Format: "+track.getFormat());
			System.out.println(ident+"  SampleRate: "+track.getSampleRate());
			System.out.println(ident+"  BitsPerSample: "+track.getBitsPerSample());
			System.out.println(ident+"  Channels: "+track.getChannels());
			System.out.println(ident+"  VariableBitRate: "+track.isVariableBitRate());
			System.out.println(ident+"  BitRate: "+track.getBitRate());
			System.out.println(ident+"  Lossless: "+track.isLossless());
			System.out.println(ident+"  HiRes: "+track.isHiRes());
			System.out.println("");
			System.out.println(ident+"  Offset: "+track.getOffset());
			System.out.println(ident+"  End: "+track.getEnd());
			System.out.println("");
			System.out.println(ident+"  IsrcCode: "+track.getIsrcCode());
			System.out.println(ident+"  Copyright: "+track.getCopyright());
			System.out.println(ident+"  ParentalWarning: "+track.getParentalWarning());
			
            System.out.println("");
            System.out.println(ident+"  - METADATA:");

            TestUtils.printMetadata(track.getMetadataList(),ident+"   ");
            
            System.out.println("");
            System.out.println(ident+"  Status : "+track.getStatus().name());
            System.out.println("");
            System.out.println(ident+"  - STATUS MESSAGES:");
            for (StatusMessage statusMessage : track.getMessageList()){
            
                System.out.println(ident+"    "+ statusMessage.toString());
            }
			System.out.println("");
        }
		System.out.println("");
	}
}
