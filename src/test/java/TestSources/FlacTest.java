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
import org.jaudiotagger.tag.flac.FlacTag;
import org.junit.Before;
import org.junit.Test;
import org.mc2.audio.metadata.source.tags.file.Flac;

public class FlacTest {
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    }
    @Test
    public void TestJaudioTaggerFlacReadTag() throws Exception{

        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
         String filename = "flac_16_44100_EMBEDDED_CUE.flac";
        //String filename = "flac_16_44100_TAG.flac";
        
        String path = directory+"/"+filename;
        Flac flac = new Flac(path);
       
        AudioFile f = flac.getAudiofile();
        
        FlacTag tag = (FlacTag) f.getTag();
        
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
        TestUtils.printTagFields(flac.geTagFields());
        
        System.out.println("METADATA:");
        
            TestUtils.printMetadata(flac.getMetadata());
            System.out.println("");
        
        System.out.println("TESTS:");
        
        //TestUtils.printMetadata();
        
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
        String filename = "flac_16_44100_TAG.flac";
        
        String path = directory+"/"+filename;

        Flac flac = new Flac(path);
       
        AudioFile f = flac.getAudiofile();
        
        Tag tag = f.getTag();
        
        System.out.println("FILE: "+path );
        System.out.println("AUDIO HEADAER:");
        
        TestUtils.printTagFields(flac.geTagFields());
        
        tag.setField(FieldKey.ARTIST,"Artist a");
        tag.addField(FieldKey.ARTIST,"Artist b");
        tag.setField(FieldKey.WORK,"Work a");
        tag.addField(FieldKey.WORK,"Work b");
        f.commit();
        
        TestUtils.printTagFields(flac.geTagFields());

        tag.deleteField(FieldKey.ARTIST);
        tag.deleteField(FieldKey.WORK);
        f.commit();
        
        TestUtils.printTagFields(flac.geTagFields());
    }
}
