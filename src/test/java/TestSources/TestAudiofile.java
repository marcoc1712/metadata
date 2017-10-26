package TestSources;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.PrintStream;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author marco
 */
public class TestAudiofile {

    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    }
    @Test
    public void TestRead() throws Exception{

        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "dff_02-822-400 (dsd64)_TAG.dff";

        String path = directory+"/"+filename;
        File file = new File(path);
        
        AudioFile f = AudioFileIO.read(file);
        System.out.println("AudioHEader: "+f.getAudioHeader());
        System.out.println("Tag: "+f.getTag());  
    }
}
