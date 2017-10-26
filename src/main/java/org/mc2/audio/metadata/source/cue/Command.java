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
import org.mc2.audio.metadata.source.cue.CommandKeys.COMMAND_KEY;

/**
 *
 * @author marcoc1712
 */
public class Command {

    private final COMMAND_KEY command;
    private final String remSubKey;
    private final HashMap<Integer, String> valueMap = new HashMap<>();
    
    public Command(COMMAND_KEY command, String remSubKey){
        this.command = command;
        this.remSubKey = remSubKey;
    }
    public Command(COMMAND_KEY command, String remSubKey, int lineNo, String value){
        this.command = command;
        this.remSubKey = remSubKey;
        this.addValue(lineNo, value);
    }
    
    
    /**
     * @return the COMMAND_KEY 
     */
    public COMMAND_KEY getCommandKey() {
        return command;
    }

    /**
     * @return the remSubKey
     */
    public String getRemSubKey() {
        return remSubKey;
    }
   
    /**
    * Returns the Id of the represented command.<br>
    * This value uniquely identify a source of data, like title or rem genre.
    * @return Unique identifier for the fields type. (title, rem genre, ...)
    */
    public String getId() {
        if (this.getRemSubKey().isEmpty()){return this.getCommandKey().name();}
        return this.getCommandKey().name()+":"+this.getRemSubKey();
    }

    /**
     * @return the value map
     */
    public HashMap<Integer, String> getValueMap() {
        return valueMap;
    }
    /**
     * @return the values as list
     */
    public ArrayList<String> getValueList() {
        return new ArrayList<>(getValueMap().values());
    }
    /**
     * @return the first encountered value.
     */
    public String getFirstValue() {
        return getValueList().get(0);
    }
    /**
     * @return values joined in string by "; " added separator.
     * quotes are removed in multi value string.
     */
    public String getValueString() {
  
        String out="";
        for (String value: getValueList()){
            if (!out.isEmpty()) {out=out+";";}
            out=out+removeQuotes(value);
        }
        return out;
    }

    /**
     * Add a new value to Values;
     * @param LineNo
     * @param value
     */
    public final void addValue(int LineNo, String value){
        
        getValueMap().put(LineNo, value);
    }
    private static String removeQuotes(String in){
    
        String out  = in;
        if (out.length() > 0 && out.charAt(0)=='"' && out.charAt(out.length()-1)=='"') {
              out = out.substring(1, out.length()-1);
        }
        return out;
    }
    @Override
    public String toString(){
        
        return getCommandKey()+ (getRemSubKey().isEmpty() ? "" : " "+getRemSubKey())+" "+getValueString();

    } 
    @Override
    public boolean equals(Object obj){
        if (!(obj instanceof Command)) {return false;}
        if (this == null) {return false;}
        
        return ((Command)obj).getId().equals(this.getId());
    }
}
