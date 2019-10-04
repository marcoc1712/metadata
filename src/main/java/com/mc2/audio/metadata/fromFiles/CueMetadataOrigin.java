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

package com.mc2.audio.metadata.fromFiles;

import java.util.ArrayList;
import java.util.List;
import jwbroek.cuelib.Message;
import com.mc2.audio.metadata.API.MetadataOrigin;
import com.mc2.audio.metadata.source.cue.Command;

/**
 *
 * @author marco
 */
public class CueMetadataOrigin implements MetadataOrigin { 
    private final String source;
    private final String key;
    private final String remSubKey;
    
    private ArrayList<Command> commands = new  ArrayList<>() ;
    private ArrayList<Command> discardedCommands = new  ArrayList<>() ;
    private ArrayList<Command> invalidCommands = new  ArrayList<>() ;
    private ArrayList<Message> messages = new  ArrayList<>() ;

    public CueMetadataOrigin(String source, String key, String remSubKey,
                                    List<Command> commands,  
                                    List<Command> discardedCommands,  
                                    List<Command> invalidCommands,
                                    List<Message> messages){
        this.source = source;
        this.key = key;
        this.remSubKey = remSubKey;
        this.commands= (ArrayList)commands;
        this.discardedCommands = (ArrayList)discardedCommands;
        this.invalidCommands = (ArrayList)invalidCommands;
        this.messages = (ArrayList)messages;
    }
    
    /**
     * @return the commands
     */
    public ArrayList<Command> getCommands() {
        
        return this.commands;
    }

    @Override
    public String getSource() {
        return this.source;
    }

    @Override
    public String getOriginKey() {
        if (this.remSubKey.isEmpty()){return this.key;}
        return this.key+":"+this.remSubKey;
    }
    
    @Override
    public ArrayList<String> getValidatedValues() {
        ArrayList<String> out = new ArrayList<>();
        for (Command command : this.getValidatedCommands()) {
            out.add(command.getValueString());
        }
        return out;
    }

    @Override
    public ArrayList<String> getDiscardedValues() {
        ArrayList<String> out = new ArrayList<>();
        for (Command command : this.getDiscardedCommands()) {
            out.add(command.getValueString());
        }
        return out;
    }
    @Override
    public ArrayList<String> getInvalidValues() {
        ArrayList<String> out = new ArrayList<>();
        for (Command command : this.getInvalidCommands()) {
            out.add(command.getValueString());
        }
        return out;
    }
    /**
     * @return the Commands
     */
    public ArrayList<Command> getValidatedCommands() {
        return this.getCommands();
    }

    /**
     * @return the discardedCommands
     */
    public ArrayList<Command> getDiscardedCommands() {
        return this.discardedCommands;
    }
    /**
     * @return the invalidCommands
     */
    public ArrayList<Command> getInvalidCommands() {
         return this.invalidCommands;
    }

    /**
     * @return the messages
     */
    public ArrayList<Message> getMessages() {
        return messages;
    }
}
