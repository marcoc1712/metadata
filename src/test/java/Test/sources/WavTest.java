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
//import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.junit.Before;
import org.junit.Test;
import com.mc2.audio.metadata.source.tags.file.AudioFile;
import com.mc2.audio.metadata.source.tags.file.Wav;

public class WavTest {
    
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    } 

    @Test
    public void TestRead() throws Exception{
        
        
        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione/02_Sec";
        String filename = "wav_16_044100_TAGS.wav";
        //String filename = "flac_16_44100_TAG.flac";
        
        String path = directory+"/"+filename;
        
        Wav audiofile = (Wav)AudioFile.get(path);
        
        TestUtils.printAudioFile(audiofile);


    }
    //@Test
    public void TestWrite() throws Exception{
        
        
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
