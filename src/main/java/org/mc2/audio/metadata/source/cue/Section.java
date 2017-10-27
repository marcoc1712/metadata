/*
 * Cuelib library for manipulating cue sheets.
 * Copyright (C) 2007-2008 Jan-Willem van den Broek
 *               2017 Marco Curti
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


package org.mc2.audio.metadata.source.cue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.MetadataSource;
import static org.mc2.audio.metadata.source.cue.MetadataKeys.getAlbumLevelMetadataAlias;
/**
 *
 * @author marcoc1712
 */
public class Section implements MetadataSource{
    
    private String sourceId;
    private final CueSheet cuesheet;
    
    /**
     * Maps of Rem Commands not directly recognized as metadata.
     * May be empty.
     * 
     */
    private Map<String, String> remCommands = new HashMap<>();
    
    private final ArrayList<Command> commandList = new ArrayList<>();
    private final ArrayList<Metadata> metadataList = new ArrayList<>();
    
    public Section(CueSheet cuesheet){
        this.cuesheet = cuesheet;
        
    }
    
    public final CueSheet getCuesheet() {
        return cuesheet;
    }
    
    public ArrayList<Command> getCommandList(){

            return commandList;
    }

    /**
     *
     * @return  the list of metadata of this section.
     */
    @Override
    public ArrayList<Metadata> getMetadata(){

            return metadataList;
    }

    /**
     * @return the sourceId
     */
    @Override
    public String getSourceId() {
        return sourceId;
    }

    /**
     * @param source the sourceId to set
     */
    public void setSourceId(String source) {
        this.sourceId = source;
    }
    
    public Metadata getMedata(String genericKey){
        
        String alias = genericKey;
        String key = getAlbumLevelMetadataAlias(genericKey);
        
        if (key != null){alias=key;}
        
        Metadata out = searchMedata(alias); 
        
        if (!alias.equals(genericKey) && (out == null || out.isEmpty())){
            
            out = searchMedata(genericKey); 
        }
        return out;
    }
    
    private Metadata searchMedata(String parm){
        
        for (Metadata metadata : metadataList){
        
            if (metadata.getKey().toUpperCase().equals(parm.toUpperCase())){
            
                return metadata;
            }
        }
        return null;
    }

    

}