package prove;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author marco
 */

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
