/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.file;

import java.io.File;

/**
 *
 * @author marco
 */
public class Aif extends Aiff{
    
    public Aif(String path) throws Exception {
        super(path);
    }
    public Aif(File file) throws Exception {
         super(file);
    }
}
