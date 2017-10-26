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
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.wav.WavTag;
import org.junit.Before;
import org.junit.Test;
import org.mc2.audio.metadata.source.tags.file.Wav;

public class WavTest {
    
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    } 

    @Test
    public void TestJaudioTaggerWavReadTag() throws Exception{
    
        String path= "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione/02_Sec/wav_16_044100_TAGS.wav";
        Wav wav = new Wav(path);
        
        AudioFile f = wav.getAudiofile();
        WavTag tag = (WavTag) f.getTag();
        
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
            
        System.out.println("IDV3 TAGS:");
        
           //System.out.println("wav info tag;"+tag.getInfoTag());
           //System.out.println("id3 tag;"+tag.getID3Tag());
            
            TestUtils.printTagFields(wav.geTagFields());

        //System.out.println("DISCHARGED TAGS:");
        //    System.out.println("- unrecognized id3Tag;"+tag.getID3Tag().getInvalidFrames());
        //    System.out.println("- unrecognized wav info fields:"+tag.getInfoTag().getUnrecognisedFields());

        System.out.println("METADATA:");
        
             TestUtils.printMetadata(wav.getMetadata());

            System.out.println("");
        
        System.out.println("TESTS:");


    }
    //@Test
    public void TestJaudioTaggerWavWriteTag() throws Exception{
        
        
        String path= "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione/02_Sec/wav_16_044100_TAGS.wav";
        AudioFile f = new Wav(path).getAudiofile();
        
        Tag tag = f.getTag();
                
        tag.setField(FieldKey.ARTIST,"Artist a");
        tag.addField(FieldKey.ARTIST,"Artist b");
        tag.setField(FieldKey.WORK,"Work a");
        tag.addField(FieldKey.WORK,"Work b");
        f.commit();
        System.out.println(tag);
       
        tag.deleteField(FieldKey.ARTIST);
        tag.deleteField(FieldKey.WORK);
        f.commit();
        
    }
}
