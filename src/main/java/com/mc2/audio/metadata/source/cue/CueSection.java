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


package com.mc2.audio.metadata.source.cue;

import com.mc2.audio.metadata.source.Section;
import java.util.ArrayList;
/**
 *
 * @author marcoc1712
 */
public class CueSection extends Section{
    
    private final CueSheet cuesheet;
    private final ArrayList<Command> commandList = new ArrayList<>();
    
    public CueSection(CueSheet cuesheet){
        this.cuesheet = cuesheet;  
    }
    
    public final CueSheet getCuesheet() {
        return cuesheet;
    }
    
    public ArrayList<Command> getCommandList(){
            return commandList;
    }


    

}