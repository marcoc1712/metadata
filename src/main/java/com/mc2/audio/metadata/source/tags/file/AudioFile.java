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

package com.mc2.audio.metadata.source.tags.file;

import com.mc2.audio.metadata.source.coverart.EmbeddedArtwork;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.images.Artwork;
import com.mc2.audio.metadata.API.CoverArt;
import com.mc2.audio.metadata.API.Metadata;
import com.mc2.audio.metadata.API.exceptions.InvalidAudioFileException;
import com.mc2.audio.metadata.API.exceptions.InvalidAudioFileFormatException;
import com.mc2.audio.metadata.API.MetadataSource;
import com.mc2.audio.metadata.API.RawKeyValuePair;
import com.mc2.audio.metadata.API.RawKeyValuePairSource;
import com.mc2.audio.metadata.API.SupportedFileFormat;
import com.mc2.audio.metadata.impl.RawKeyValuePairDefaultImpl;
import com.mc2.audio.metadata.source.tags.TagsSource;
import com.mc2.audio.metadata.source.tags.schema.TagSchema;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author marco
 */
public abstract class AudioFile implements TagsSource, RawKeyValuePairSource, MetadataSource{

    private final File file;
    private org.jaudiotagger.audio.AudioFile audiofile;
    private String path;
    private Tag tag;
    private TagSchema tagSchema;
    
	public static Boolean isAudiofile(String path) throws InvalidAudioFileException, IOException{
		 return isAudiofile(new File(path));
	}
	public static Boolean isAudiofile(File file) throws InvalidAudioFileException, IOException{ 
		
		if (file == null) throw new InvalidAudioFileException ("Invalid file");
		
		String extension =  FilenameUtils.getExtension(file.getCanonicalPath());
		
		try{
			return SupportedFileFormat.valueOf(extension.toUpperCase()) != null;
		} catch (IllegalArgumentException ex) {
			return false;
		}
		
	}
    public static AudioFile get(String path) throws InvalidAudioFileException, InvalidAudioFileFormatException, IOException{
        
        return get(new File(path));
    }
    public static AudioFile get(File file) throws InvalidAudioFileException, InvalidAudioFileFormatException, IOException{
        
        if (file == null) throw new InvalidAudioFileException ("Invalid file");
		
		String extension =  FilenameUtils.getExtension(file.getCanonicalPath());
            
        if(SupportedAudioFileFormat.FLAC.getFilesuffix().equals(extension)) {
            return new Flac (file);
        } else if(SupportedAudioFileFormat.OGG.getFilesuffix().equals(extension)) {
            return new Ogg (file);
        } else if(SupportedAudioFileFormat.MP3.getFilesuffix().equals(extension))  {
            return new Mp3(file);
        } else if(SupportedAudioFileFormat.MP4.getFilesuffix().equals(extension))  {
            return new Mp4(file);
        } else if(SupportedAudioFileFormat.M4A.getFilesuffix().equals(extension)) {
            return new M4a(file);
        } else if(SupportedAudioFileFormat.M4P.getFilesuffix().equals(extension)) {
            return new M4p(file);
        } else if(SupportedAudioFileFormat.WMA.getFilesuffix().equals(extension)) {
            return new Wma(file);
        } else if(SupportedAudioFileFormat.WAV.getFilesuffix().equals(extension)) {
            return new Wav(file);
        } else if(SupportedAudioFileFormat.RA.getFilesuffix().equals(extension)) {
            return new Ra(file);
        } else if(SupportedAudioFileFormat.RM.getFilesuffix().equals(extension)){
            return new Rm(file);
        } else if(SupportedAudioFileFormat.AIF.getFilesuffix().equals(extension)) {
            return new Aif(file);
        } else if(SupportedAudioFileFormat.AIFC.getFilesuffix().equals(extension)) {
            return new Aifc(file);
        } else if(SupportedAudioFileFormat.AIFF.getFilesuffix().equals(extension)) {
            return new Aiff(file);
        } else if(SupportedAudioFileFormat.DSF.getFilesuffix().equals(extension)) {
            return new Dsf(file);
        } else if(SupportedAudioFileFormat.DFF.getFilesuffix().equals(extension)){
            return new Dff(file);
        } else {
            throw new InvalidAudioFileFormatException ("File format not supported");
        }
    }
    protected AudioFile (String  path) throws InvalidAudioFileException {
        this.file = new File(path);
        init(file);
    }
     
    protected AudioFile(File file) throws InvalidAudioFileException {
        this.file = file;
        init(file);
    }
    
    protected abstract void  initOptions();
    protected abstract void  initSchema();
    
    protected final void init(File file) throws  InvalidAudioFileException {
        initOptions();
        try {
            audiofile=  AudioFileIO.read(file);
            tag = this.audiofile.getTag();
            path = this.file.getCanonicalPath();
            initSchema();
            
        } catch (IOException | CannotReadException | ReadOnlyFileException | TagException | InvalidAudioFrameException ex) {
            throw new InvalidAudioFileException(ex);
        }
    }
   /**
     * @return the file
     */
    public File getFile() {
        return file;
    }
    
    /**
     * @return the Header
     */
    public AudioHeader getAudioHeader() {
        return audiofile.getAudioHeader();
    }
    /**
     * @return the audiofile
     */
    public org.jaudiotagger.audio.AudioFile getAudiofile() {
        return audiofile;
    }
    /**
     * @return the path
     */   
    public String getPath() {
        return path;
    }
    /**
     *
     * @return the path as sourceId
     */
    @Override
    public String getSourceId() {
        return path;
    }
    /**
     * @return the tag
     */
    @Override
    public Tag getTag() {
        return tag;
    }
    /**
     * @return the tagSchema
     */
    protected TagSchema getTagSchema() {
        return tagSchema;
    }

    /**
     * @param tagSchema the tagSchema to set
     */
    protected void setTagSchema(TagSchema tagSchema) {
        this.tagSchema = tagSchema;
    }
    /* list all the tagField in the AudioFile.  
    */ 
    @Override
    public ArrayList<TagField> geTagFields(){

        return getTagSchema().geTagFields();
    }
    @Override
    public ArrayList<RawKeyValuePair> getRawKeyValuePairs() {
        ArrayList<RawKeyValuePair> out = new ArrayList<>();
        
        AudioHeader audioHeader = this.getAudioHeader();

        out.add(new RawKeyValuePairDefaultImpl("Audio Data Length: ",audioHeader.getAudioDataLength()+""));
        out.add(new RawKeyValuePairDefaultImpl("Audio Data Start Position: ",audioHeader.getAudioDataStartPosition()+""));
        out.add(new RawKeyValuePairDefaultImpl("Audio Data End Position: ",audioHeader.getAudioDataEndPosition()+""));
        out.add(new RawKeyValuePairDefaultImpl("Byte Rate: ",audioHeader.getByteRate()+""));
        out.add(new RawKeyValuePairDefaultImpl("Bit Rate: ",audioHeader.getBitRate()));
        out.add(new RawKeyValuePairDefaultImpl("Bit Rate As Number: ",audioHeader.getBitRateAsNumber()+""));
        out.add(new RawKeyValuePairDefaultImpl("Sample Rate: ",audioHeader.getSampleRate()));
        out.add(new RawKeyValuePairDefaultImpl("Sample Rate As Number: ",audioHeader.getSampleRateAsNumber()+""));
        out.add(new RawKeyValuePairDefaultImpl("Bits PerSample: ",audioHeader.getBitsPerSample()+""));
        out.add(new RawKeyValuePairDefaultImpl("Channels: ",audioHeader.getChannels()));
        out.add(new RawKeyValuePairDefaultImpl("Encoding Type: ",audioHeader.getEncodingType()));
        out.add(new RawKeyValuePairDefaultImpl("Format: ",audioHeader.getFormat()));
        out.add(new RawKeyValuePairDefaultImpl("No Of Samples: ",audioHeader.getNoOfSamples()+""));
        out.add(new RawKeyValuePairDefaultImpl("Is Variable Bit Rate: ",audioHeader.isVariableBitRate()+""));
        out.add(new RawKeyValuePairDefaultImpl("Track Length: ",audioHeader.getTrackLength()+""));
        out.add(new RawKeyValuePairDefaultImpl("Precise Track Length: ",audioHeader.getPreciseTrackLength()+""));
        out.add(new RawKeyValuePairDefaultImpl("Is Lossless: ",audioHeader.isLossless()+""));
        
        for ( TagField tagField : geTagFields()){
            String value = tagField.isBinary() ? "[bynary content]" : tagField.toString();
            RawKeyValuePair pair = new RawKeyValuePairDefaultImpl(tagField.getId(), value);
            out.add(pair);
        }
        return out;
    }
    
    /* metadata by the Audiofile in the 'generic' FieldKey + values format.
     * Metadata for fieldKeys with invalid value or with key not listed in FieldKey are discarded.
     *
     * This form return the "nasty" KEY: Type="" + Value format, not really good for displaying.
    */
    @Override
    public abstract ArrayList<Metadata> getMetadata();
    
    /* list all the metadata by the Audiofile in the 'generic' FieldKey + values format.
     * Metadata for fieldKeys with invalid value are reported and value is in the form:
     *
     * [TAGFIELD] tagValue (note that tagValue contains the Type= label and value is quoted by "").
     *  
     * NOTE: Reported keys are still only those in FieldKey enumerator.
    */
    public abstract ArrayList<Metadata> getExistingMetadata();

    /* list all the metadata by the Audiofile in the 'generic' FieldKey + values format.
     * Metadata for fieldKeys with invalid value or with key not listed 
     * in org.jaudiotagger.tag.FieldKey are discarded.
     */
    public ArrayList<Metadata> getExistingAndValidMetadata(){

        return getTagSchema().getExistingAndValidMetadata();
    } 
    /* Metadata by the Audiofile in the 'generic' FieldKey + values format.
     * Metadata for fieldKeys with invalid value or with key not listed in FieldKey are discarded.
     *
     * This form return the "pretty" KEY: Value format, without the Type="" enclosure for Value.
     */
    public Metadata getPrettyMetadata(FieldKey fieldKey) {
        return getTagSchema().getPrettyMetadata(fieldKey);
    }
    
    public Metadata getNastyMetadata(FieldKey fieldKey) {
        return getTagSchema().getNastyMetadata(fieldKey);
    }
    
    public Metadata getMetadata(FieldKey fieldKey) {
        return getTagSchema().getMetadata(fieldKey);
    }
	
    public ArrayList<CoverArt> getEmbeddedArtworks(){
        
        ArrayList<CoverArt> out=new ArrayList<>();
        
        if (getTag() != null && getTag().getArtworkList()!= null){
            int i = 0;
            for (Artwork artwork: getTag().getArtworkList()){
                
                out.add(new EmbeddedArtwork(this.file, artwork, i));
                i++;
            }
           
        }
        return out;
    }
	public String getTrackId(){
		
		Metadata tracknumber = this.getMetadata(FieldKey.TRACK);
		Metadata discnumber = this.getMetadata(FieldKey.DISC_NO);
		
		String track="";
		String disc="";
		
		String id="";
		
		if (discnumber != null && 
		    discnumber.getValue() != null && 
		    !discnumber.getValue().isEmpty()){
		
			disc = discnumber.getValue();
			if (disc.length()<2) {disc="0"+disc;}
		}
		
		if (tracknumber != null && 
		    tracknumber.getValue() != null && 
		    !tracknumber.getValue().isEmpty()){
		
			track = tracknumber.getValue();
			if (track.length()<2) {track="0"+track;}
		}
		
		if (!disc.isEmpty() && !track.isEmpty()){
		
			id = disc+"."+track;
			
		} else if (!track.isEmpty()){
			
			id = track;
		
		}
		
		return id;
	}
}
