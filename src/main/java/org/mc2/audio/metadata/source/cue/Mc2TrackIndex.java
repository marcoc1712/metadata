/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.cue;

import jwbroek.cuelib.Index;
import jwbroek.cuelib.Position;


/**
 *
 * @author marco
 */

public class Mc2TrackIndex extends Index{
    
    private Mc2TrackData trackData;
    private int length;
    
    Mc2TrackIndex(Mc2TrackData trackData, int indexNumber, Position position) {
        super(indexNumber,position);
        this.trackData= trackData;
        this.length= 0;
    }

     /**
     * @return the trackData
     */
    public Mc2TrackData getTrackData() {
        return trackData;
    }
    
    /**
     * @return the offset
     */
    public int getOffset() {
        return getPosition().getTotalFrames();
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }
    /** @return track offset in msec */
   public Long getOffsetInMillis(){
       return Mc2CueSheet.getMilliseconds(getOffset());
   }
   /** @return track length in msec */
   public Long getLengthInMillis(){
       return Mc2CueSheet.getMilliseconds(getLength());
   }

   /** @return track offset string */
   public String getOffsetString(){

       return Mc2CueSheet.getTimeString(getOffsetInMillis());
   }
   /** @return track length string */
   public String getLengthString(){

       return Mc2CueSheet.getTimeString(getLengthInMillis());
   }
   /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

}