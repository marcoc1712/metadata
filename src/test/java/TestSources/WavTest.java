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
//import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.wav.WavTag;
import org.junit.Before;
import org.junit.Test;
import org.mc2.audio.metadata.source.tags.file.AudioFile;
import org.mc2.audio.metadata.source.tags.file.Wav;

public class WavTest {
    
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    } 

    @Test
    public void TestJaudioTaggerWavReadTag() throws Exception{
    
        String path= "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione/02_Sec/wav_16_044100_TAGS.wav";
        
        Wav audiofile = (Wav)AudioFile.get(path);
        
        
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
            
        System.out.println("IDV3 TAGS:");
        
           //System.out.println("wav info tag;"+tag.getInfoTag());
           //System.out.println("id3 tag;"+tag.getID3Tag());
            
            TestUtils.printTagFields(audiofile.geTagFields());

        //System.out.println("DISCHARGED TAGS:");
        //    System.out.println("- unrecognized id3Tag;"+tag.getID3Tag().getInvalidFrames());
        //    System.out.println("- unrecognized wav info fields:"+tag.getInfoTag().getUnrecognisedFields());

        System.out.println("METADATA:");
        
             TestUtils.printMetadata(audiofile.getMetadata());

            System.out.println("");
        
        System.out.println("TESTS:");


    }
    //@Test
    public void TestJaudioTaggerWavWriteTag() throws Exception{
        
        
        String path= "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione/02_Sec/wav_16_044100_TAGS.wav";
        org.jaudiotagger.audio.AudioFile f = new Wav(path).getAudiofile();
        
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
