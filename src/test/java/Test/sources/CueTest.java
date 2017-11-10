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
import java.io.File;
import java.io.PrintStream;
import java.util.List;
import jwbroek.cuelib.LineOfInput;
import jwbroek.cuelib.Message;
import jwbroek.cuelib.Position;
import org.junit.Before;
import org.junit.Test;
import org.mc2.audio.metadata.source.cue.Command;
import org.mc2.audio.metadata.source.cue.CueSheetMetadaParser;
import org.mc2.audio.metadata.source.cue.CueSheet;
import org.mc2.audio.metadata.source.cue.FileData;
import org.mc2.audio.metadata.source.cue.TrackData;
import org.mc2.audio.metadata.source.cue.TrackIndex;

public class CueTest {
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    }
    @Test
    public void TestSingleFile() throws Exception{
         
        //String directory = "F:\\SVILUPPO\\01 - SqueezeboxServer Plugins\\musica campione\\Albinoni Adagios - Anthony Camden, Julia Girdwood (1993 Naxos)";
        //String filename = "Albinoni - Adagio.cue";
        //String directory = "F:\\SVILUPPO\\01 - SqueezeboxServer Plugins\\musica campione\\provaDff";
        //String filename = "dff_02-822-400 (dsd64).cue";
        String directory = "X:/Classica/Albinoni, Tomaso/12 Concertos OP. 10 - I Solisti Veneti; Claudio Scimone (ERATO, 1981)/CD 1";
        String filename = "12 Concertos Op. 10 (CD 1) CD1.cue";
        
        String path = directory+"/"+filename;
        
        File cuefile = new File(path);
        
        
        CueSheet cuesheet = (CueSheet) CueSheetMetadaParser.parse(cuefile);
        
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

        for (FileData file : cuesheet.getFileDataList()){
            System.out.println("");
            System.out.println("- FILE: "+file.getFile()+ " ");
            System.out.println(" - type: "+file.getFileType());
            System.out.println(" - offset: "+file.getOffsetString()+", "
                                            +file.getOffsetInMillis()+ " ms, "
                                            +file.getOffset()+" sectors"); 
            System.out.println(" - length: "+file.getLengthString()+", "
                                            +file.getLengthInMillis()+ " ms, "
                                            +file.getLength()+" sectors");

            TestUtils.printAudioFile(file.getAudiofile());
            
            for (TrackData track : file.getTrackDataList()){
                
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
    //@Test
    public void TestMultiFIlesAndMuiltitrackPerFiile() throws Exception{
         
        String directory = "F:/SVILUPPO/01 - SqueezeboxServer Plugins/musica campione";
        String filename = "flac_Due_files.cue";
        
        String path = directory+"/"+filename;
        
        File cuefile = new File(path);
        
        
        CueSheet cuesheet = (CueSheet) CueSheetMetadaParser.parse(cuefile);
        
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

        for (FileData file : cuesheet.getFileDataList()){
            System.out.println("");
            System.out.println("- FILE: "+file.getFile()+ " ");
            System.out.println(" - type: "+file.getFileType());
            System.out.println(" - offset: "+file.getOffsetString()+", "
                                            +file.getOffsetInMillis()+ " ms, "
                                            +file.getOffset()+" sectors"); 
            System.out.println(" - length: "+file.getLengthString()+", "
                                            +file.getLengthInMillis()+ " ms, "
                                            +file.getLength()+" sectors");

            TestUtils.printAudioFile(file.getAudiofile());
            
            for (TrackData track : file.getTrackDataList()){
                
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

    private void printIndices(String inline,  List<TrackIndex> indexes){
        
        for (TrackIndex trackIndex : indexes) {
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
