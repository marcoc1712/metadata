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

import java.util.EnumSet;
import java.util.Set;

/**
 * Enumeration of standard commands and segmentation for use at ALBUM
 * and/or at TRACK level.
 * see: 
 *  http://en.wikipedia.org/wiki/Cue_sheet_%28computing%29
 *  http://wiki.hydrogenaud.io/index.php?title=Cue_sheet
 *  http://digitalx.org/cue-sheet/syntax/
 *
 * @author marcoc1712
 */
public class CommandKeys {
      
    public static enum COMMAND_KEY{
                            CATALOG,
                            CDTEXTFILE,
                            PERFORMER,
                            SONGWRITER,
                            TITLE, 
                            FILE,
                            FLAGS,
                            INDEX,
                            ISRC,
                            POSTGAP,
                            PREGAP,
                            REM,
                            TRACK,
    }
    /*
    * Reserved commands, used to describe the structure of the cuesheet.
    */
    public static final Set<COMMAND_KEY> RESERVED_COMMAND_KEY = EnumSet.of(
                                                    COMMAND_KEY.FILE,
                                                    COMMAND_KEY.TRACK,
                                                    COMMAND_KEY.PREGAP,
                                                    COMMAND_KEY.INDEX, 
                                                    COMMAND_KEY.POSTGAP,
                                                    COMMAND_KEY.REM
    );
    
    public static final Set<COMMAND_KEY> ALBUM_COMMANDS_KEY = EnumSet.of(
                                                    COMMAND_KEY.CATALOG,
                                                    COMMAND_KEY.CDTEXTFILE, 
                                                    COMMAND_KEY.PERFORMER,
                                                    COMMAND_KEY.SONGWRITER,
                                                    COMMAND_KEY.TITLE
    );
    
    public static final Set<COMMAND_KEY> TRACK_COMMANDS_KEY = EnumSet.of(
                                                    COMMAND_KEY.FLAGS,
                                                    COMMAND_KEY.ISRC, 
                                                    COMMAND_KEY.PERFORMER,
                                                    COMMAND_KEY.SONGWRITER,
                                                    COMMAND_KEY.TITLE
    );


    public static boolean isReservedCommandKey(COMMAND_KEY command){
        
        return RESERVED_COMMAND_KEY.contains(command);
    }
    
    public static boolean isAlbumCommandKey(COMMAND_KEY command){
        
        return ALBUM_COMMANDS_KEY.contains(command);
    }
    
    public static boolean isTrackCommandKey(COMMAND_KEY command){
        
        return TRACK_COMMANDS_KEY.contains(command);
    }
    
    public static COMMAND_KEY getCommandKey(String input){
        
        if (input == null || input.isEmpty()){
            return null;
        }

        for (COMMAND_KEY command : COMMAND_KEY.values()) {
            
            if (input.length() >= command.name().length() &&
                input.startsWith(command.name())){
                    
                return command;
            }
            
        }
        return null;
    }
}