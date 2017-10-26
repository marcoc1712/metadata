/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template cueFile, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.cue.file;

import org.mc2.audio.metadata.source.cue.CueSheetMetadaParser;
import java.io.File;
import java.io.IOException;
import org.mc2.audio.metadata.exceptions.InvalidCueSheetException;
import org.mc2.audio.metadata.source.cue.Mc2CueSheet;
/**
 *
 * @author marco
 */
public class CueFile {

    private File cueFile;
    private String path;
    private Mc2CueSheet cuesheet;

    public CueFile(String  path) throws IOException, InvalidCueSheetException{
        this.path = path;
        this.cueFile = new File(path);
        if (!isCueFile())  throw new InvalidCueSheetException("this is not a a cue file!");
        this.cuesheet = CueSheetMetadaParser.parse(cueFile);

    }
     
    public CueFile(File file) throws IOException, InvalidCueSheetException {
        this.cueFile = file;
        this.path = file.getCanonicalPath();
        if (!isCueFile())  throw new InvalidCueSheetException("this is not a a cue file!");
        this.cuesheet = CueSheetMetadaParser.parse(file);

    }

    public final boolean isCueFile(){

        String lowercaseName = cueFile.getName().toLowerCase();
        return lowercaseName.endsWith(".cue")||
               lowercaseName.endsWith(".qbu");
    }
    
    private Mc2CueSheet getCuesheet() {
        return cuesheet;
    }

}
    
