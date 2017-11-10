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

import org.mc2.audio.metadata.API.AlbumBuilder;
import org.mc2.audio.metadata.API.Album;
import org.mc2.audio.metadata.API.StatusMessage;
import org.mc2.audio.metadata.API.Track;

public class AlbumTest {
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    }
    @Test
    public void TestCueeAndSingleFile() throws Exception{
         
        //String directory = "F:\\SVILUPPO\\01 - SqueezeboxServer Plugins\\musica campione\\ProvaAlbumScan";
        //String directory = "F:\\SVILUPPO\\01 - SqueezeboxServer Plugins\\musica campione\\Albinoni Adagios - Anthony Camden, Julia Girdwood (1993 Naxos)";
        String directory = "F:\\SVILUPPO\\01 - SqueezeboxServer Plugins\\musica campione\\cue con embedde cover";
        
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
       
        for (StatusMessage statusMessage : album.getMessageList()){
            
            System.out.println(" - "+ statusMessage.getSeverity()+" "+statusMessage.getMessage());
        }
        
        System.out.println(" - METADATA:");
        TestUtils.printMetadata(album.getMetadataList());

        
        for (Track track : album.getTrackList()){
                
            System.out.println("");
            System.out.println(" - TRACK: "+track.getTrackNo() +" ["+track.getLengthString()+"]");

            System.out.println("");
            System.out.println("  - METADATA:");

            TestUtils.printMetadata(track.getMetadataList());

        }
        System.out.println("");
        System.out.println("  - TOC: "+album.getToc());
        
        System.out.println("");
        System.out.println(" - ARTWORKS:");
        TestUtils.printArtworks(album.getcoverArtList());
    }
}
