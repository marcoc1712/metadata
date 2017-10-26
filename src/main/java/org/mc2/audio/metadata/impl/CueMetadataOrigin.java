/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.impl;

import java.util.ArrayList;
import java.util.List;
import jwbroek.cuelib.Message;
import org.mc2.audio.metadata.MetadataOrigin;
import org.mc2.audio.metadata.source.cue.Command;

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
            out.add(command.toString());
        }
        return out;
    }
    @Override
    public ArrayList<String> getInvalidValues() {
        ArrayList<String> out = new ArrayList<>();
        for (Command command : this.getInvalidCommands()) {
            out.add(command.toString());
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
