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

package TestSources;

import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import org.mc2.audio.metadata.source.tags.file.Aif;
import org.mc2.audio.metadata.source.tags.file.AudioFile;


public class AifTest {
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    }
    @Test
    public void TestJaudioTaggerOggReadTag() throws Exception{

        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "aif_16_44100.aif";
        String path = directory+"/"+filename;
        
        Aif audiofile = (Aif)AudioFile.get(path);
       
        System.out.println("FILE: "+path );
        System.out.println("AUDIO HEADAER:");
            
            //System.out.println(audiofile.getAudioHeader());

            System.out.println("- audioDataLength: "+audiofile.getAudioHeader().getAudioDataLength());
            System.out.println("- audioDataStartPosition: "+audiofile.getAudioHeader().getAudioDataStartPosition());
            System.out.println("- audioDataEndPosition: "+audiofile.getAudioHeader().getAudioDataEndPosition());
            System.out.println("- byteRate: "+audiofile.getAudioHeader().getByteRate());
            System.out.println("- bitRate: "+audiofile.getAudioHeader().getBitRate());
            System.out.println("- bitRateAsNumber: "+audiofile.getAudioHeader().getBitRateAsNumber());
            System.out.println("- sampleRate: "+audiofile.getAudioHeader().getSampleRate());
            System.out.println("- sampleRateAsNumber: "+audiofile.getAudioHeader().getSampleRateAsNumber());
            System.out.println("- bitsPerSample: "+audiofile.getAudioHeader().getBitsPerSample());
            System.out.println("- channels: "+audiofile.getAudioHeader().getChannels());
            System.out.println("- encodingType: "+audiofile.getAudioHeader().getEncodingType());
            System.out.println("- format: "+audiofile.getAudioHeader().getFormat());
            System.out.println("- noOfSamples: "+audiofile.getAudioHeader().getNoOfSamples());
            System.out.println("- isVariableBitRate: "+audiofile.getAudioHeader().isVariableBitRate());
            System.out.println("- trackLength: "+audiofile.getAudioHeader().getTrackLength());
            System.out.println("- preciseTrackLength: "+audiofile.getAudioHeader().getPreciseTrackLength());
            System.out.println("- isLossless: "+audiofile.getAudioHeader().isLossless());
        
        System.out.println("TAGS:");
        //System.out.println(tag);
        TestUtils.printTagFields(audiofile.geTagFields());
        
        System.out.println("METADATA:");
        
            TestUtils.printMetadata(audiofile.getMetadata());
            System.out.println("");
        
        System.out.println("TESTS:");
        
        //tag.getFields(FieldKey.ITUNES_GROUPING);

        /*
        
        
        System.out.println("ciccia: "+tag.getFirst("CICCIA"));
        
        List<TagField>  list = tag.getFields("CICCIA");
        for(TagField field:list)
        {
            System.out.println("- ciccia: "+field);
        }
        
        */
    }  
}
