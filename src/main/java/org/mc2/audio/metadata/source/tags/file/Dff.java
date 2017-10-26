/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.file;

import java.io.File;
import java.util.ArrayList;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.tags.schema.GenericTagSchema;
/**
 *
 * @author marco
 */
public class Dff extends AudioFile {

    public Dff(String path) throws Exception {
        super(path); 
    }
    
    public Dff(File file) throws Exception {
         super(file);
    }
    
    @Override
    protected void initOptions() throws Exception {
        
        
        //Default is ID3_V24.
        //TagOptionSingleton.getInstance().setID3V2Version(ID3V2Version.ID3_V24);
        //TagOptionSingleton.getInstance().setID3V2Version(ID3V2Version.ID3_V23);
        //TagOptionSingleton.getInstance().setID3V2Version(ID3V2Version.ID3_V22);
        
    }
    @Override
    protected void initSchema() throws Exception {
        super.setTagSchema(new GenericTagSchema(getTag(), this));
    }

    @Override
    public ArrayList<Metadata> getMetadata() {
        return this.getGenericTagSchema().getMetadata();
    }

    @Override
    public ArrayList<Metadata> getExistingMetadata() {
        return this.getGenericTagSchema().getExistingMetadata();
    }
        /**
     * @return the genericTagSchema
     */
    public GenericTagSchema getGenericTagSchema() {
        return (GenericTagSchema)super.getTagSchema();
    }
}
    
