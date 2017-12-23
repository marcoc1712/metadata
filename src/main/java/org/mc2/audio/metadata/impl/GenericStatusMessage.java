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
package org.mc2.audio.metadata.impl;

import org.mc2.audio.metadata.API.StatusMessage;

/**
 *
 * @author marco
 */
public class GenericStatusMessage implements StatusMessage {
    
    private final String origin;
    private final Severity severity;
    private final String message;
    private final Integer sequenceNo;
    
    public GenericStatusMessage(Integer sequenceNo, Severity severity, String message){
        this.sequenceNo = sequenceNo;
        this.origin = "";
        this.severity = severity;
        this.message = message;
    }
    public GenericStatusMessage(Integer sequenceNo,String origin, Severity severity, String message){
        this.sequenceNo = sequenceNo;
        this.origin = origin == null ? "" : origin;
        this.severity = severity;
        this.message = message == null ? "" : message;;
    }
    @Override
    public Integer getSequenceNo() {
        return sequenceNo;
    }
       
    @Override
    public String getOrigin() {
        return origin;
    }
    /**
     * @return the severity
     */
    @Override
    public Severity getSeverity() {
        return severity;
    }
    
    @Override
    public Integer getSeverityIndex() {
        return Severity.valueOf(severity.name()).ordinal();
    }
    /**
     * @return the message
     */
    @Override
    public String getMessage() {
        return message;
    }
    
    @Override
    public String toString(){
        
        return getSeverity()+" "+getMessage()+" ["+getOrigin()+"]";
        
    }
    @Override
    public boolean equals(Object aThat){
        if ( this == aThat ) return true;
        if ( !(aThat instanceof StatusMessage) ) return false;
        
        StatusMessage that = (StatusMessage)aThat;
        
        return (
            that.getOrigin().equals(this.getOrigin()) &&
            that.getSeverity().equals(this.getSeverity()) &&
            that.getMessage().equals(this.getMessage())
        ); 
    }

    
}

