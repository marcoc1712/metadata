/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TestSources;

import java.util.ArrayList;
import org.jaudiotagger.tag.TagField;
import org.mc2.audio.metadata.Metadata;

/**
 *
 * @author marco
 */
public class TestUtils {
    
    protected static void printTagFields(ArrayList<TagField> tagFields){
        
        for (TagField tagfield : tagFields){
            
            System.out.println("- "+tagfield.getId()+": "+tagfield.toString());
        }
    }
    
    protected static void printMetadata(ArrayList<Metadata> metadataList){
        
        for (Metadata metadata : metadataList){
            printMetadata(metadata);
        }
    }
    
    protected static void printMetadata(Metadata metadata){
        
        String status="";
        switch (metadata.getStatus()) {
            case VALID:                             status="     "; break;
            case INVALID:                           status=" !   "; break;
            case DISCARDED:                         status=" +   "; break;
            case DISCARDED_AND_INVALID:             status=" !+  "; break;
            case EMPTY:                             status=" -   "; break;
            case HAS_INVALID_ORIGINS:               status=" >!  "; break;
            case HAS_DISCARDED_ORIGINS:             status=" >+  "; break;
            case HAS_DISCARDED_AND_INVALID_ORIGINS: status=" >!+ "; break;
            
            default: break;
        }
        
        System.out.println(status+metadata.getKey()+": "+metadata.getValue());
        
    }
}
