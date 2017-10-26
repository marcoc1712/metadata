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
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.junit.Before;
import org.junit.Test;
import org.mc2.audio.metadata.source.tags.file.M4a;

public class M4aTest {
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    }
    @Test
    public void TestJaudioTaggerOggReadTag() throws Exception{

        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "alc_16_44100.m4a";
        
        
        String path = directory+"/"+filename;
        M4a m4a = new M4a(path);
       
        AudioFile f = m4a.getAudiofile();
        
        Mp4Tag tag = (Mp4Tag) f.getTag();
        
        System.out.println("FILE: "+path );
        System.out.println("AUDIO HEADAER:");
            
            //System.out.println(f.getAudioHeader());

            System.out.println("- audioDataLength: "+f.getAudioHeader().getAudioDataLength());
            System.out.println("- audioDataStartPosition: "+f.getAudioHeader().getAudioDataStartPosition());
            System.out.println("- audioDataEndPosition: "+f.getAudioHeader().getAudioDataEndPosition());
            System.out.println("- byteRate: "+f.getAudioHeader().getByteRate());
            System.out.println("- bitRate: "+f.getAudioHeader().getBitRate());
            System.out.println("- bitRateAsNumber: "+f.getAudioHeader().getBitRateAsNumber());
            System.out.println("- sampleRate: "+f.getAudioHeader().getSampleRate());
            System.out.println("- sampleRateAsNumber: "+f.getAudioHeader().getSampleRateAsNumber());
            System.out.println("- bitsPerSample: "+f.getAudioHeader().getBitsPerSample());
            System.out.println("- channels: "+f.getAudioHeader().getChannels());
            System.out.println("- encodingType: "+f.getAudioHeader().getEncodingType());
            System.out.println("- format: "+f.getAudioHeader().getFormat());
            System.out.println("- noOfSamples: "+f.getAudioHeader().getNoOfSamples());
            System.out.println("- isVariableBitRate: "+f.getAudioHeader().isVariableBitRate());
            System.out.println("- trackLength: "+f.getAudioHeader().getTrackLength());
            System.out.println("- preciseTrackLength: "+f.getAudioHeader().getPreciseTrackLength());
            System.out.println("- isLossless: "+f.getAudioHeader().isLossless());
        
        System.out.println("TAGS:");
        //System.out.println(tag);
        TestUtils.printTagFields(m4a.geTagFields());
        
        System.out.println("METADATA:");
        
            TestUtils.printMetadata(m4a.getMetadata());
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
    //@Test
    public void TestJaudioTaggerFlacWriteTag() throws Exception{
       
        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "alc_16_44100.m4a";
        
        String path = directory+"/"+filename;

        M4a m4a = new M4a(path);
       
        AudioFile f = m4a.getAudiofile();
        
        Mp4Tag tag =(Mp4Tag) f.getTag();
        
        System.out.println("FILE: "+path );
        System.out.println("AUDIO HEADAER:");
        
        TestUtils.printTagFields(m4a.geTagFields());
        
        tag.setField(FieldKey.ARTIST,"Artist a");
        tag.addField(FieldKey.ARTIST,"Artist b");
        tag.setField(FieldKey.WORK,"Work a");
        tag.addField(FieldKey.WORK,"Work b");
        f.commit();
        
        TestUtils.printTagFields(m4a.geTagFields());

        tag.deleteField(FieldKey.ARTIST);
        tag.deleteField(FieldKey.WORK);
        f.commit();
        
        TestUtils.printTagFields(m4a.geTagFields());
    }
}
