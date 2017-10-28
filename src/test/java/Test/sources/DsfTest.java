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
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.junit.Before;
import org.junit.Test;
import org.mc2.audio.metadata.source.tags.file.Dsf;

public class DsfTest {
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    }
    @Test
    public void TestJaudioTaggerOggReadTag() throws Exception{

        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "dsf_02-822-400 (dsd64)_TAG.dsf";
        String path = directory+"/"+filename;
        
        Dsf audiofile = (Dsf)org.mc2.audio.metadata.source.tags.file.AudioFile.get(path);
        
        TestUtils.printAudioFile(audiofile);
        
    }  
    //@Test
    public void TestJaudioTaggerFlacWriteTag() throws Exception{
       
        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "dsf_02-822-400 (dsd64)_TAG.dsf";
        
        
        String path = directory+"/"+filename;
        Dsf dsf = new Dsf(path);
       
        AudioFile f = dsf.getAudiofile();
        
        AbstractID3v2Tag tag = (AbstractID3v2Tag) f.getTag();
        
        System.out.println("FILE: "+path );
        
        TestUtils.printTagFields(dsf.geTagFields());
        
        if (tag == null) {
            
            f.getTagAndConvertOrCreateAndSetDefault();
            tag = (AbstractID3v2Tag) f.getTag();
        }

        tag.setField(FieldKey.ARTIST,"Artist a");
        tag.addField(FieldKey.ARTIST,"Artist b");
        tag.setField(FieldKey.WORK,"Work a");
        tag.addField(FieldKey.WORK,"Work b");
        f.commit();
        
        System.out.println(tag);
        
        TestUtils.printTagFields(dsf.geTagFields());

        //tag.deleteField(FieldKey.ARTIST);
        //tag.deleteField(FieldKey.WORK);
        //f.commit();
        
        //TestUtils.printTagFields(dsf.geTagFields());

    }
}
