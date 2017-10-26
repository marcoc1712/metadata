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

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import javax.sound.midi.Track;
import jwbroek.cuelib.Index;
import jwbroek.cuelib.LineOfInput;
import jwbroek.cuelib.Message;
import jwbroek.cuelib.Position;
import org.junit.Before;
import org.junit.Test;
import org.mc2.audio.metadata.source.cue.file.CueFile;
import org.mc2.audio.metadata.source.cue.Command;
import org.mc2.audio.metadata.source.cue.CueSheetMetadaParser;
import org.mc2.audio.metadata.source.cue.Mc2CueSheet;
import org.mc2.audio.metadata.source.cue.Mc2FileData;
import org.mc2.audio.metadata.source.cue.Mc2TrackData;
import org.mc2.audio.metadata.source.cue.Mc2TrackIndex;

public class CueTest {
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    }
    @Test
    public void TestCueSheetParser() throws Exception{
         
        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "flac_Due_files.cue";
        
        String path = directory+"/"+filename;
        
        File cuefile = new File(path);
        
        
        Mc2CueSheet cuesheet = (Mc2CueSheet) CueSheetMetadaParser.parse(cuefile);
        
        System.out.println("========================================================================");
        System.out.println("\n");
        System.out.println("Directory:"+ directory);
        System.out.println("File     :"+ filename);
        System.out.println("");
        
        System.out.println("ALBUM COMMANDS:");
        
        for (Command command : cuesheet.getCommands()){
            
             System.out.println("- "+command.toString());
        }
        System.out.println("");
        System.out.println("ALBUM METADATA:");
        
        TestUtils.printMetadata(cuesheet.getMetadata());

        for (Mc2FileData file : cuesheet.getFileDataList()){
            System.out.println("");
            System.out.println("- FILE: "+file.getFile()+ " ");
            System.out.println(" - type: "+file.getFileType());
            System.out.println(" - offset: "+file.getOffsetString()+", "
                                            +file.getOffsetInMillis()+ " ms, "
                                            +file.getOffset()+" sectors"); 
            System.out.println(" - length: "+file.getLengthString()+", "
                                            +file.getLengthInMillis()+ " ms, "
                                            +file.getLenght()+" sectors");
           
            for (Mc2TrackData track : file.getTrackDataList()){
                
                System.out.println("");
                System.out.println(" - TRACK: "+track.getNumber());
                System.out.println("  - content type: "+track.getDataType());

                if (track.getPregap()!=null) {
                    System.out.println("  - PREGAP: " +getPositionString(track.getPregap()));
                }
                System.out.println("  - offeset: "+track.getOffsetString()+", "
                                                  +track.getOffsetInMillis()+ " ms, "
                                                  +track.getOffset()+" sectors");
                
                System.out.println("  - length:  "+track.getLengthString()+", "
                                                  +track.getLengthInMillis()+ " ms, "
                                                  +track.getLength()+" sectors");
                
                System.out.println("  - end:     "+track.getEndString()+", "
                                                  +track.getEndInMillis()+ " ms, "
                                                  +track.getEnd()+" sectors");
                
                printIndices("  ", track.getTrackIndexList());
                
                if (track.getPostgap()!=null) {
                    System.out.println("  - POSTGAP: " +getPositionString(track.getPostgap()));
                }
                
                System.out.println("");
                System.out.println("  - COMMANDS:");
                for (Command command : track.getCommandList()){
            
                    System.out.println("   - "+command.toString());
                
                }
                System.out.println("");
                System.out.println("  - METADATA:");

                TestUtils.printMetadata(track.getMetadata());

            }
        }
        System.out.println("");
        System.out.println("ERRORS AND WARNINGS:");
        System.out.println("");
        
        for (Message message : cuesheet.getMessages()){
            
            System.out.println("- "+message.toString());
        }
        
        System.out.println("");
        System.out.println("SOURCE:");
        System.out.println("");
        
        for (LineOfInput line : cuesheet.getLines()){
            
            System.out.println("    "+line.getLineNumber()+" "+line.getInput());
        }
        System.out.println("");
    
    }

    private void printIndices(String inline,  List<Mc2TrackIndex> indexes){
        
        for (Mc2TrackIndex trackIndex : indexes) {
            System.out.println(inline+"- INDEX: " +trackIndex.getNumber()+" "+getPositionString(trackIndex.getPosition()));
        
            System.out.println(inline+" - offeset: "+trackIndex.getOffsetString()+", "
                                                        +trackIndex.getOffsetInMillis()+ " ms, "
                                                        +trackIndex.getOffset()+" sectors");
                
            System.out.println(inline+" - length:  "+trackIndex.getLengthString()+", "
                                                        +trackIndex.getLengthInMillis()+ " ms, "
                                                        +trackIndex.getLength()+" sectors");
                
        }
    }
    private String getPositionString (Position position){
        
        if (position == null) return null;
        return position.getMinutes()+":"+
                position.getSeconds()+":"+
                position.getFrames()+" (frames: "+
                position.getTotalFrames()+")";
    }
}
