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

package prove;

import java.io.PrintStream;
import java.util.HashMap;
import jwbroek.cuelib.CueSheet.MetaDataField;
import org.jaudiotagger.tag.FieldKey;
import org.junit.Before;
import org.junit.Test;

public class Prove {
     
     
    @Before
    public void setUp() throws Exception {
        
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        
    }
    @Test
    public void mergeMetadata() throws Exception{
        
        // jwbroek.cuelib.CueSheet.MetaDataField;
        // org.jaudiotagger.tag.FieldKey;
        
        HashMap<String, String> tagToCue = new HashMap<>();
        HashMap<String, String> cueTotag = new HashMap<>();

        for (MetaDataField cueKey : MetaDataField.values()){
            
            for (FieldKey tagkey : FieldKey.values()){
                
                if (tagkey.toString().equals(cueKey.toString())){
                    
                    cueTotag.put(cueKey.toString(), tagkey.toString());
                    break;
                }
            }
            if (!cueTotag.containsKey(cueKey.toString())){
                cueTotag.put(cueKey.toString(), "");
            }
            
        }
        for (String key : cueTotag.keySet()){
            
            System.out.println("cue: "+key+" > tag: "+cueTotag.get(key));
        }
        
        /*
        for (FieldKey tagkey : FieldKey.values()){
            
            if (tagkey.toString().equals("GENRE")) {
            
            }
            for (MetaDataField cueKey : MetaDataField.values()){
            
               
                if (tagkey.toString().equals(cueKey.toString())){
                    
                    tagToCue.put(tagkey.toString(), cueKey.toString());
                    break;
                }
            }
            if (tagToCue.get(tagkey.toString()) == null || tagToCue.get(tagkey.toString()).isEmpty()){
                    tagToCue.put(tagkey.toString(), "");
            }
        }
        
        for (MetaDataField cueKey : MetaDataField.values()){
           
            for (FieldKey tagkey : FieldKey.values()){
                
                if (tagkey.toString().equals(cueKey.toString())){
                    
                    cueTotag.put(cueKey.toString(), tagkey.toString());
                    break;
                }
            }
            if (cueTotag.get(cueKey.toString())==null || cueTotag.get(cueKey.toString()).isEmpty()){
                    tagToCue.put(cueKey.toString(), "");
            }
            
        }
        for (String key : tagToCue.keySet()){
            
            System.out.println("tag: "+key+" > "+"cue: "+tagToCue.get(key));
        }
        for (String key : cueTotag.keySet()){
            
            System.out.println("tag: "+cueTotag.get(key)+" > "+"cue: "+key);
        }
        */
    }
    
}
