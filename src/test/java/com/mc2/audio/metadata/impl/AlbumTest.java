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


import com.mc2.audio.metadata.utils.TestUtils;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

import com.mc2.audio.metadata.API.Album;
import com.mc2.audio.metadata.API.Medium;
import com.mc2.audio.metadata.API.MetadataKey;
import com.mc2.audio.metadata.API.MetadataRow;
import com.mc2.audio.metadata.API.RawKeyValuePair;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.StatusMessage;
import com.mc2.audio.metadata.API.Track;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import com.mc2.audio.metadata.API.Controller;

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
		//String directory = "Z:\\recorder\\Alicia de Larrocha\\Albéniz_ Ibéria; Navarra; Suite Española\\CD1";
		//String directory =  "Z:/recorder/Berlziot - Te Deum, Abbado";
		String directory = "Z:/Classica/Albinoni, Tomaso/12 Concertos OP. 10 - I Solisti Veneti; Claudio Scimone (ERATO, 1981)/CD 1";
		//String directory = "Y:/Audiophile/aavv/The Best acoustic Album In The World... Ever (2005 EMI)/cd2";
		//String directory = "Z:\\Classica\\aavv\\Anne-Sophie Mutter\\The Four Seasons and The Devil's Trill";
		//String directory = "Z:\\Classica\\aavv\\111 Years Of Deutsche Grammophon (55 CD) (2009 DG)\\01 - Abbado - Brahms - Hungarian Dances\\art";
		//String directory = "X:\\musica-test\\TestCase\\02- flac singoli\\021 - flc singoli con embedded";

		printAlbum(directory);
		
		
	}
	//@Test
    public void testCredits() throws Exception{
        //String directory = "F:\\SVILUPPO\\04-Leia\\TestCase\\DSD SAMPLE";
        //String filename = "001_DSD128_Tascam DA-3000.dff";
		//String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
		//String directory = "Z:\\recorder\\Alicia de Larrocha\\Albéniz_ Ibéria; Navarra; Suite Española\\CD1";
		//String directory =  "Z:/recorder/Berlziot - Te Deum, Abbado";
		String directory = "Z:/Classica/Albinoni, Tomaso/12 Concertos OP. 10 - I Solisti Veneti; Claudio Scimone (ERATO, 1981)/CD 1";
		//String directory = "Y:/Audiophile/aavv/The Best acoustic Album In The World... Ever (2005 EMI)/cd2";
		//String directory = "Z:\\Classica\\aavv\\Anne-Sophie Mutter\\The Four Seasons and The Devil's Trill";
		//String directory = "Z:\\Classica\\aavv\\111 Years Of Deutsche Grammophon (55 CD) (2009 DG)\\01 - Abbado - Brahms - Hungarian Dances\\art";
		//String directory = "X:\\musica-test\\TestCase\\02- flac singoli\\021 - flc singoli con embedded";
		
		Album album = Controller.parse(directory);
		if (album != null){
			
			System.out.println("URL: "+album.getId());
			
			/*for (MetadataRow row : ((AlbumDefaultImpl)album).getMetadataRows()){

				System.out.println("Key: "+row.getKeyName()+" - Category: "+row.getCategoryName());
				System.out.println("At Album level: "+row.getAlbumLevelValue());
				System.out.println("Tracks common value: "+row.getTrackLevelValue());
				System.out.println("Value: "+row.getValue());
			}*/

			for (MetadataRow row : album.getCreditMetadataList()){

				System.out.println(MetadataKey.METADATA_CATEGORY.CREDITS);

				System.out.println("Key: "+row.getKeyName()+" - Category: "+row.getCategoryName());
				System.out.println("At Album level: "+row.getAlbumLevelValue());
				System.out.println("Tracks common value: "+row.getTrackLevelValue());
				System.out.println("Value: "+row.getValue());
			}
		}
		
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
		
		
      
        
        Album album = Controller.parse(directory);

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
		Album album = Controller.parse(directory);
		TestUtils.printAlbum(album);
	}

}
