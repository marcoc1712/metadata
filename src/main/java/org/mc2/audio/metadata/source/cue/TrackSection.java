/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.cue;

/**
 *
 * @author marco
 */
public class TrackSection extends Section {
    
     private final Mc2TrackData trackdata;
     
     public TrackSection(Mc2CueSheet cuesheet, Mc2TrackData trackdata){
        super (cuesheet);
        this.trackdata = trackdata;
    }

    /**
     * @return the trackdata
     */
    public Mc2TrackData getTrackdata() {
        return trackdata;
    }
}
