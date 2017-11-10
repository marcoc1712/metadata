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

package Test.sources;

import Test.utils.TestUtils;
import java.io.PrintStream;
import java.util.List;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.images.StandardArtwork;
import org.junit.Before;
import org.junit.Test;
import org.mc2.audio.metadata.source.tags.file.AudioFile;
import org.mc2.audio.metadata.source.tags.file.Flac;

public class FlacTest {
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    }
    //@Test
    public void TestRead() throws Exception{

        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "flac_16_44100_TAG.flac";
        
        String path = directory+"/"+filename;
        
        Flac audiofile = (Flac)AudioFile.get(path);
        
        TestUtils.printAudioFile(audiofile);
    } 
    //@Test
    public void TestReadEmbeddedCueSheet() throws Exception{

        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "flac_16_44100_EMBEDDED_CUE.flac";
                
        String path = directory+"/"+filename;
        
        Flac audiofile = (Flac)AudioFile.get(path);
        
        TestUtils.printAudioFile(audiofile);
    }
    //@Test 
    public void TestReadEmbeddedCover() throws Exception{

        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "flac_16_44100_EMBEDDED_COVER.flac";
       
        String path = directory+"/"+filename;
        
        Flac audiofile = (Flac)AudioFile.get(path);

        TestUtils.printAudioFile(audiofile);

    }  
    @Test
    public void TestWriteEmbeddedCoverFromUrl() throws Exception{

        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "flac_16_44100_EMBEDDED_COVER_URL.flac";
       
        String path = directory+"/"+filename;
        
        Flac flac = (Flac)AudioFile.get(path);
        flac.getAudiofile().getTagOrCreateAndSetDefault();
        Tag tag=  flac.getAudiofile().getTag();
        
        List<Artwork> artList = flac.getAudiofile().getTag().getArtworkList();
        if (artList.isEmpty()){
            StandardArtwork artwork =  StandardArtwork.createLinkedArtworkFromURL("https://ia601508.us.archive.org/22/items/mbid-c1850312-f20a-420b-b6a2-2c61105c2916/mbid-c1850312-f20a-420b-b6a2-2c61105c2916-18262008174.jpg");
            tag.setField(artwork);
            flac.getAudiofile().setTag(tag);
            flac.getAudiofile().commit();
        }
        
        artList = flac.getAudiofile().getTag().getArtworkList();
        
        TestUtils.printAudioFile(flac);

    }  
    
}
