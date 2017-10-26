/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mc2.audio.metadata.source.tags.schema.ID3v2;

/**
 *
 * @author marco
 */
 public class ID3FrameAndSubId {
        
    private final String genericKey;
    private final String frameId;
    private final String subId;

    public ID3FrameAndSubId(String genericKey, String frameId, String subId)
    {
        this.genericKey = genericKey;
        this.frameId = frameId;
        this.subId = subId;
    }
    public String getSubId()
    {
        return subId;
    }
    public String getGenericKey()
    {
        return genericKey;
    }

    public String getFrameId()
    {
        return frameId;
    }

    public String getName()
    {
        if (getSubId() != null && !getSubId().isEmpty()){
            return getFrameId()+":"+getSubId();
        } else{
            return getFrameId();
        }
    }
 }
