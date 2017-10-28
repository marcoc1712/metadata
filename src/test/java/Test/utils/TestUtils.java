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

package Test.utils;

import java.io.IOException;
import java.util.ArrayList;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.images.Artwork;
import org.jaudiotagger.tag.reference.PictureTypes;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.source.tags.file.AudioFile;

/**
 *
 * @author marco
 */
public class TestUtils {
    
    public static void printAudioFile(AudioFile audiofile) throws IOException{
    
        
        System.out.println("FILE: "+audiofile.getFile().getCanonicalPath());
        
        System.out.println("AUDIO HEADAER:"); 
        TestUtils.printAudioHeader(audiofile.getAudioHeader());

        System.out.println("TAGS:");
        TestUtils.printTagFields(audiofile.geTagFields());

        System.out.println("METADATA:");
        TestUtils.printMetadata(audiofile.getMetadata());
        
        System.out.println("ARTWORKS:");
        
        printArtworks(audiofile.getEmbeddedArtworks());
    
    }
    public static void printArtworks(ArrayList<Artwork> artworks){
        
        for (Artwork artwork : artworks){
            
            String description =  PictureTypes.getInstanceOf().getValueForId(artwork.getPictureType());
            if (description.isEmpty()){description = artwork.getDescription(); }
            
            description = description+  ": "+artwork.getMimeType() + 
                                        " [ h: "+artwork.getHeight()+
                                        ", w: "+ artwork.getWidth()+"]";
                                

            System.out.println("- embedded artwork: "+ description);
        }
    
    }
    public static void printAudioHeader(AudioHeader audioHeader){
        
        System.out.println("- audioDataLength: "+audioHeader.getAudioDataLength());
        System.out.println("- audioDataStartPosition: "+audioHeader.getAudioDataStartPosition());
        System.out.println("- audioDataEndPosition: "+audioHeader.getAudioDataEndPosition());
        System.out.println("- byteRate: "+audioHeader.getByteRate());
        System.out.println("- bitRate: "+audioHeader.getBitRate());
        System.out.println("- bitRateAsNumber: "+audioHeader.getBitRateAsNumber());
        System.out.println("- sampleRate: "+audioHeader.getSampleRate());
        System.out.println("- sampleRateAsNumber: "+audioHeader.getSampleRateAsNumber());
        System.out.println("- bitsPerSample: "+audioHeader.getBitsPerSample());
        System.out.println("- channels: "+audioHeader.getChannels());
        System.out.println("- encodingType: "+audioHeader.getEncodingType());
        System.out.println("- format: "+audioHeader.getFormat());
        System.out.println("- noOfSamples: "+audioHeader.getNoOfSamples());
        System.out.println("- isVariableBitRate: "+audioHeader.isVariableBitRate());
        System.out.println("- trackLength: "+audioHeader.getTrackLength());
        System.out.println("- preciseTrackLength: "+audioHeader.getPreciseTrackLength());
        System.out.println("- isLossless: "+audioHeader.isLossless());
        
    }
    
    public static void printTagFields(ArrayList<TagField> tagFields){
        
        for (TagField tagfield : tagFields){
            
            System.out.println("- "+tagfield.getId()+": "+tagfield.toString());
        }
    }
    
    public static void printMetadata(ArrayList<Metadata> metadataList){
        
        for (Metadata metadata : metadataList){
            printMetadata(metadata);
        }
    }
    
    public static void printMetadata(Metadata metadata){
        
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
