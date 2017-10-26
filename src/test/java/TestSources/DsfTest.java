package TestSources;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marco
 */

import java.io.PrintStream;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.aiff.AiffTag;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.mp4.Mp4Tag;
import org.junit.Before;
import org.junit.Test;
import org.mc2.audio.metadata.source.tags.file.Aif;
import org.mc2.audio.metadata.source.tags.file.Dsf;
import org.mc2.audio.metadata.source.tags.file.M4a;

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
        Dsf dsf = new Dsf(path);
       
        AudioFile f = dsf.getAudiofile();
        
        AbstractID3v2Tag tag = (AbstractID3v2Tag) f.getTag();
        
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
        TestUtils.printTagFields(dsf.geTagFields());
        
        System.out.println("METADATA:");
        
            TestUtils.printMetadata(dsf.getMetadata());
            System.out.println("");
        
        System.out.println("TESTS:");
        
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
