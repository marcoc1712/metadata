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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import jwbroek.cuelib.Message;
import jwbroek.cuelib.Warning;
import org.apache.commons.lang3.StringUtils;
import org.mc2.audio.metadata.Metadata;
import org.mc2.audio.metadata.impl.CueMetadataOrigin;
import org.mc2.audio.metadata.source.cue.CommandKeys.COMMAND_KEY;
import org.mc2.audio.metadata.source.cue.MetadataKeys.METADATA_KEY;
import static org.mc2.audio.metadata.source.cue.MetadataKeys.getAlbumLevelMetadataAlias;
import static org.mc2.audio.metadata.source.cue.MetadataKeys.getTrackLevelMetadataALias;

/**
 *
 * @author marcoc1712
 */
public class CueSheetMetadaParser {
     /**
     * Logger for this class.
    */
    private final static Logger logger = Logger.getLogger(CueSheetCommandParser.class.getCanonicalName());
    
    private final static String WARNING_INVALID_CATALOG_NUMBER  = "Invalid catalog number.";
    private final static String WARNING_INVALID_PERFORMER       = "Invalid performer.";
    private final static String WARNING_INVALID_SONGWRITER      = "Invalid songwriter.";
    private final static String WARNING_INVALID_TITLE           = "Invalid title.";
    private final static String WARNING_NONCOMPLIANT_ISRC_CODE  = "ISRC code has noncompliant format.";
    private final static String WARNING_INVALID_YEAR            = "Invalid year. Should be a number from 1 to 9999.";
    private final static String WARNING_INVALID_GENRE           = "Invalid genre.";
    private final static String WARNING_INVALID_DISCID          = "Invalid discId.";
    private final static String WARNING_INVALID_DISC_NUMBER     = "Invalid disc number. Should be a number from 1 to 9999.";
    private final static String WARNING_INVALID_DISC_TOTAL      = "Invalid disc total. Should be a number from 1 to 9999.";

    private final static String WARNING_FIELD_LENGTH_OVER_80    = "The field is too long to burn as CD-TEXT. The maximum length is 80.";
    private final static String WARNING_DATUM_APPEARS_TOO_OFTEN = "Datum appears too often.";
  
    private final static Pattern PATTERN_CATALOG_NUMBER         = Pattern.compile ("^\\d{13}$");
    private final static Pattern PATTERN_ISRC_CODE              = Pattern.compile("^\\w{5}\\d{7}$");
    private final static Pattern PATTERN_QUOTED_OR_NO_BLANK     = Pattern.compile ("^?\"[^\"]*\"|\\S+\\s*$", Pattern.CASE_INSENSITIVE);
 
    /**
    * Create a CueSheetCommandParser. Should never be used, as all properties and methods of this class are static. 
    */
    private CueSheetMetadaParser () {

        // Intentionally left blank (besides logging). This class doesn't need to be instantiated.
        logger.entering(this.getClass().getCanonicalName(), "CueSheetMetadaParser()");
        logger.log(Level.WARNING, "{0}should not be initialized", this.getClass().getCanonicalName());
        logger.exiting(this.getClass().getCanonicalName(), "CueSheetMetadaParser()");
    }
    /**
    * Parse a cue sheet that will be read from the InputStream.
    * @param inputStream An {@link java.io.InputStream} that produces a cue sheet. The stream will be closed
    * afterward.
    * @param source An identifier for the cuesheet source.
    * @return A representation of the cue sheet.
    * @throws IOException
    */
    public static CueSheet parse(final InputStream inputStream, String source) throws IOException {
        
        return parse(CueSheetCommandParser.parse(inputStream, source));
    }
    /**
    * Parse a cue sheet file.
    * @param file A cue sheet file.
    * @return A representation of the cue sheet.
    * @throws IOException
    */
    public static CueSheet parse(final File file) throws IOException {
        
        return parse(CueSheetCommandParser.parse(file));
    }
    /**
    * Parse a cue sheet.
    * @param reader A reader for the cue sheet. This reader will be closed afterward.
    * @param source An identifier for the cuesheet source.
    * @return A representation of the cue sheet.
    * @throws IOException
    */
    public static CueSheet parse(final LineNumberReader reader, String source) throws IOException {

        return parse(CueSheetCommandParser.parse(reader, source));
    }
    
    /**
    * Parse cuesheet validCommands and valorize metadata.
    * @param cuesheet The {@link org.mc2.audio.metadata.source.cue.CueSheet} to be analized to produce metadata.
    * @return A Metadata representation of the cue sheet.
    */
    public static CueSheet parse(CueSheet cuesheet){
        
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parse(PlainCueSheet)", cuesheet);
        logger.fine("Parsing cue sheet.");
        
        //erase all metadata stored in dedicated structures.
        cuesheet.setCatalog("");
        cuesheet.setCdTextFile("");
        cuesheet.setPerformer("");
        cuesheet.setSongwriter("");
        cuesheet.setTitle("");
        
        //non standard EAC
        cuesheet.setComment("");
        cuesheet.setGenre("");
        cuesheet.setDiscid("");
        cuesheet.setYear(0);

        // parse Album command Keys.
        
        for (Command command : cuesheet.getCommands()){
        
               parseCommand(cuesheet, command);
        }
        
        int offset = 0;
        
        for (FileData file : cuesheet.getFileDataList()){
    
            for (TrackData track : file.getTrackDataList()){
                
                for (Command command : track.getCommandList()){
                    
                    //erase all metadata stored in dedicated structures.
                    track.setTitle("");
                    track.setIsrcCode("");
                    track.setPerformer("");
                    track.setSongwriter("");

                    // parse per trackcommand Keys.
                    
                    parseCommand(track, command);
                }
            }
        }
        
        
        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parse(PlainCueSheet)", cuesheet);

        return cuesheet;
    }     
    /**
    * Parse a command and extract metadata
    * @param command the command to parse.
    */
    private static void parseCommand(CueSheet cuesheet, Command command) {
        
       COMMAND_KEY commandKey = command.getCommandKey();
        
        switch (commandKey){

            case CATALOG:
                parseCatalog(cuesheet, command);
                break;
            case CDTEXTFILE:
                parseCdTextFile(cuesheet, command);
                break;
            case PERFORMER:
                parsePerformer(cuesheet.getAlbumSection(), command);
                break;
            case SONGWRITER:
                parseSongwriter(cuesheet.getAlbumSection(), command);
                break;
            case TITLE:
                parseTitle(cuesheet.getAlbumSection(), command);
                break;
            case REM:
                parseRem(cuesheet.getAlbumSection(), command);
                break;
            default:
                break;
        }
    } 
     /**
    * Parse a command and extract metadata
    * @param command the command to parse.
    */
    private static void parseCommand(TrackData track, Command command) {
        
       COMMAND_KEY commandKey = command.getCommandKey();
        
        switch (commandKey){

            case PERFORMER:
                parsePerformer(track.getTrackSection(), command);
                break;
            case SONGWRITER:
                parseSongwriter(track.getTrackSection(), command);
                break;
            case TITLE:
                parseTitle(track.getTrackSection(), command);
                break;
            case FLAGS:
                //not really metadata.
                break;
            case ISRC:
                parseIsrc(track, command);
                break;
            case REM:
                parseRem(track.getTrackSection(), command);
                break;
            default:
                break;
        }
    } 
    /**
   * Parse the CATALOG command.
   * 
   * CATALOG [media-catalog-number]
   * CD catalog number. Code follows UPC/EAN rules.
   * Usually the first command, but this is not required. Not a mandatory command.
   * 
   * @param input
   */
    
   private static void parseCatalog(CueSheet cuesheet, Command command ) {
       
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseCatalog(Command)", command);

        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){

            String catalogNumber = command.getValueMap().get(key);

            if (!PATTERN_CATALOG_NUMBER.matcher(catalogNumber).matches()) {
                
                addWarning(messages, cuesheet, key, catalogNumber, WARNING_INVALID_CATALOG_NUMBER);
                //invalidCommands.add(command);
                
            } else if (cuesheet.getCatalog()!= null && !cuesheet.getCatalog().isEmpty())  {
                
                addWarning(messages, cuesheet, key, catalogNumber, WARNING_DATUM_APPEARS_TOO_OFTEN);
            
            }else{
                
                validCommands.add(command);
            }
           
        }
        
        Metadata metadata = addOrUpdateMetadata(cuesheet.getAlbumSection(), 
                                            command.getCommandKey(), 
                                            command.getRemSubKey(),
                                            METADATA_KEY.CATALOG_NO.name(),
                                            validCommands,
                                            discardedCommands,
                                            invalidCommands,
                                            messages);

        cuesheet.setCatalog(metadata.getValue());
            
        
        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseCatalog(LineOfInput)");
    }
    /**
    * Parse the CDTEXTFILE command.
    *
    * CDTEXTFILE [filename]
    * File that contains cd text data. Not mandatory.
    * 
    * @param input
    */
    private static void parseCdTextFile(CueSheet cuesheet, Command command ) {
        
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseCdTextFile(Command)", command);
        
        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){
            
            String cdTextFile = command.getValueMap().get(key);

            if (cuesheet.getCdTextFile()!= null && !cuesheet.getCdTextFile().isEmpty())  {
                
                addWarning(messages, cuesheet, key, cdTextFile, WARNING_DATUM_APPEARS_TOO_OFTEN);
                invalidCommands.add(command);
            
            } else{
                
                validCommands.add(command);
            }
        }
        
        Metadata metadata = addOrUpdateMetadata(cuesheet.getAlbumSection(), 
                                            command.getCommandKey(), 
                                            command.getRemSubKey(),
                                            METADATA_KEY.CDTEXTFILE.name(),
                                            validCommands,
                                            discardedCommands,
                                            invalidCommands,
                                            messages);

        cuesheet.setCdTextFile(metadata.getValue());

        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseCdTextFile(LineOfInput)");
    }
    
    /**
    * Parse the PERFORMER command.
    *
    * PERFORMER [performer-string]
    * Performer of album/TRACK.
    * 
    * [performer-string] should be <= 80 character if you want to burn it to disc.
    * If used before any TRACK fields, then it is the album artist. If after a TRACK field, then
    * it is the performer of that track.
    * 
    * @param input
    */
    private static void parsePerformer(Section section, Command command ) {
        
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parsePerformer(Command)", command);

        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){
        
            String performer = command.getValueMap().get(key);

            if (!PATTERN_QUOTED_OR_NO_BLANK.matcher(performer).matches()) {
                
                addWarning(messages, section, key, performer, WARNING_INVALID_PERFORMER);
                invalidCommands.add(command);

            } else if (performer.length() > 80) {
                
                addWarning(messages, section, key, performer, WARNING_FIELD_LENGTH_OVER_80);
                invalidCommands.add(command);

            } else if ((section instanceof AlbumSection && 
                            section.getCuesheet().getPerformer()!= null && 
                            !section.getCuesheet().getPerformer().isEmpty()
                       )|| 
                       (section instanceof TrackSection &&
                            ((TrackSection)section).getTrackdata().getPerformer()!= null && 
                            !((TrackSection)section).getTrackdata().getPerformer().isEmpty()
                      )){
            
                 addWarning(messages, section, key, performer, WARNING_DATUM_APPEARS_TOO_OFTEN);
                 invalidCommands.add(command);
            
            } else if (section instanceof AlbumSection && 
                       section.getCuesheet().getSongwriter()!= null && !section.getCuesheet().getSongwriter().isEmpty()){
            
                 addWarning(messages, section, key, performer, WARNING_DATUM_APPEARS_TOO_OFTEN);
                 invalidCommands.add(command);
            
            } else{
                
                validCommands.add(command);
            }
        }
        
        Metadata metadata = addOrUpdateMetadata(section, 
                                                command.getCommandKey(), 
                                                command.getRemSubKey(),
                                                METADATA_KEY.ALBUM_ARTIST.name(),
                                                validCommands,
                                                discardedCommands,
                                                invalidCommands,
                                                messages);
        
        if (section instanceof TrackSection) {
            
            ((TrackSection)section).getTrackdata().setPerformer(metadata.getValue());
        
        } else{
            
            section.getCuesheet().setPerformer(metadata.getValue());
        }
        
        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parsePerformer(Command)");
    }
     /**
   * Parse the SONGWRITER command.
   *
   * SONGWRITER [songwriter-string]
   * Songwriter of CD/TRACK.
   * [songwriter-string] should be <= 80 character if you want to burn it to disc.
   * If used before any TRACK fields, then it is the album writer. If after a TRACK field, then
   * it is the writer of that track.
   * 
   * @param input
   */
    private static void parseSongwriter(Section section, Command command ) {
        
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseSongwriter(Command)", command);

        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){
        
            String songwriter = command.getValueMap().get(key);
                        
            if (!PATTERN_QUOTED_OR_NO_BLANK.matcher(songwriter).matches()) {
                
                addWarning(messages, section, key, songwriter, WARNING_INVALID_SONGWRITER);
                invalidCommands.add(command);

            } else  if (songwriter.length() > 80) {
                
                addWarning(messages, section, key, songwriter, WARNING_FIELD_LENGTH_OVER_80);
                invalidCommands.add(command);

            } else if ((section instanceof AlbumSection && 
                            section.getCuesheet().getSongwriter()!= null && 
                            !section.getCuesheet().getSongwriter().isEmpty()
                       )|| 
                       (section instanceof TrackSection &&
                            ((TrackSection)section).getTrackdata().getSongwriter()!= null && 
                            !((TrackSection)section).getTrackdata().getSongwriter().isEmpty()
                      )){
            
                 addWarning(messages, section, key, songwriter, WARNING_DATUM_APPEARS_TOO_OFTEN);
                 invalidCommands.add(command);
            
            } else if (section instanceof AlbumSection && 
                       section.getCuesheet().getSongwriter()!= null && !section.getCuesheet().getSongwriter().isEmpty()){
            
                 addWarning(messages, section, key, songwriter, WARNING_DATUM_APPEARS_TOO_OFTEN);
                 invalidCommands.add(command);
            } else{
                
                validCommands.add(command);
            }
        }
        
        Metadata metadata = addOrUpdateMetadata(section, 
                                                command.getCommandKey(), 
                                                command.getRemSubKey(),
                                                METADATA_KEY.COMPOSER.name(),
                                                validCommands,
                                                discardedCommands,
                                                invalidCommands,
                                                messages);
        
        if (section instanceof TrackSection) {
            
            ((TrackSection)section).getTrackdata().setSongwriter(metadata.getValue());
        
        } else{
            
            section.getCuesheet().setSongwriter(metadata.getValue());
        }
       
        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseSongwriter(Command)");
    }
    /**
    * Parse the TITLE command.
    *
    * TITLE [title-string]
    * Title of CD/TRACK.
    * [title-string] should be <= 80 character if you want to burn it to disc.
    * If used before any TRACK fields, then it is the album title. If after a TRACK field, then
    * it is the title of that track.
    * 
    * @param input
    */
    private static void parseTitle(Section section, Command command) {
        
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseTitle(command)", command);

        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){
        
            String title = command.getValueMap().get(key);
                    
            if (!PATTERN_QUOTED_OR_NO_BLANK.matcher(title).matches()) {
                addWarning(messages, section, key, title, WARNING_INVALID_TITLE);
                invalidCommands.add(command);
            
            }else  if (title.length() > 80) {
                
                addWarning(messages, section, key, title, WARNING_FIELD_LENGTH_OVER_80);
                invalidCommands.add(command);
            
            } else if ((section instanceof AlbumSection && 
                            section.getCuesheet().getTitle()!= null && 
                            !section.getCuesheet().getTitle().isEmpty()
                       )|| 
                       (section instanceof TrackSection &&
                            ((TrackSection)section).getTrackdata().getTitle()!= null && 
                            !((TrackSection)section).getTrackdata().getTitle().isEmpty()
                      )){
            
                 addWarning(messages, section, key, title, WARNING_DATUM_APPEARS_TOO_OFTEN);
                 invalidCommands.add(command);
            
            } else if (section instanceof AlbumSection && 
                       section.getCuesheet().getTitle()!= null && !section.getCuesheet().getTitle().isEmpty()){
            
                 addWarning(messages, section, key, title, WARNING_DATUM_APPEARS_TOO_OFTEN);
                 invalidCommands.add(command);
            } else{
                validCommands.add(command);
            }
            
        }
        Metadata metadata = addOrUpdateMetadata(section, 
                                                command.getCommandKey(), 
                                                command.getRemSubKey(),
                                                METADATA_KEY.TITLE.name(),
                                                validCommands,
                                                discardedCommands,
                                                invalidCommands,
                                                messages);

        
        if (section instanceof TrackSection) {
            
            ((TrackSection)section).getTrackdata().setTitle(metadata.getValue());
        
        } else{
            
            section.getCuesheet().setTitle(metadata.getValue());
        }

        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseTitle(command)");
    }
    /**
    * Parse the ISRC command.
    *
    * ISRC [code]
    * International Standard Recording Code of track. Must come after TRACK, but before INDEX.
    * 
    * @param input
    */
    private static void parseIsrc(TrackData track, Command command ) {
       
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseIsrc(Command)", command);

        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){

            String isrcCode = command.getValueMap().get(key);

            if (!PATTERN_ISRC_CODE.matcher(isrcCode).matches()) {
                
                addWarning(messages, track, key, isrcCode, WARNING_NONCOMPLIANT_ISRC_CODE);
                invalidCommands.add(command);
                
            } else if (track.getIsrcCode()!= null && !track.getIsrcCode().isEmpty())  {
                
                addWarning(messages, track, key, isrcCode, WARNING_DATUM_APPEARS_TOO_OFTEN);
            
            }else{
                
                validCommands.add(command);
            }
           
        }
         
        Metadata metadata = addOrUpdateMetadata(track.getTrackSection(), 
                                            command.getCommandKey(), 
                                            command.getRemSubKey(),
                                            METADATA_KEY.ISRC.name(),
                                            validCommands,
                                            discardedCommands,
                                            invalidCommands,
                                            messages);

        track.setIsrcCode(metadata.getValue());
            
        
        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseIsrc(LineOfInput)");
    }
    /**
    * Parse the REM command. Will recognize and validate a  number of non-standard 
    * commands used by Exact Audio Copy or generic 'namend' comments (commands).
    * 
    * NOTE: 
    * 
    * REM [comment] form has already been catched in command parsing and 
    * transalted in REM COMMENT [comment] form.
    * 
    * Looks for:
    * 
    * 1. recognized non-standard commands that needs to be somehow validated and/or
    * added to specific field.
    * 
    * REM COMMENT [comment]
    * REM YEAR [year]
    * REM DISCID [discid]
    * REM GENRE [genre]
    * REM DISC_NO [discnumber]
    * REM DISC_TOTAL [disctotal]
    * REM ... (see list below).
    * 
    * 2. Generic commands:
    * 
    * REM [COMMAND] [args]
    * 
    * @param input
    */
    private static void parseRem(Section section, Command command){
        
        /* This is a comment, but popular implementation like Exact Audio Copy 
        * may still embed information here.
        * We'll try to parse explicity declared command keys, but we'll silently 
        * accept anything. except for case mismatches.
        */
        
        String remSubKey = command.getRemSubKey();
        
        /* 
        *  redirect known naming variation to an unique 'standard' name.
        */
        String alias;
        if (section instanceof AlbumSection){
            alias =getAlbumLevelMetadataAlias(remSubKey);
        } else {
            alias =getTrackLevelMetadataALias(remSubKey);
        }

        if (alias != null && !alias.isEmpty()){

            remSubKey= alias;
        }

        switch (remSubKey){
            case "COMMENT":
                parseRemComment(section,command);
                break;
            case "YEAR":
                parseRemYear(section,command);
                break;
            case "GENRE":
                parseRemGenre(section,command);
                break;
            case "DISCID":
                 parseRemDiscId(section,command);
                break;
            case "DISC_NO":
                parseRemDiscNumber(section,command);
                break;
            case "DISCS_TOTAL":
                parseRemDiscTotal(section,command);
                break;
            default:
                parseRemOther(section,command);
                break;
        }
    }
    /**
    * Parse the non-standard REM COMMENT command.
    *
    * REM COMMENT [comment]
    * 
    * @param input
    */
    private static void parseRemComment(Section section, Command command) {
        
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseRemComment(Command)", command);

      
        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){

            String comment = command.getValueMap().get(key);
            validCommands.add(command);

        }
        Metadata metadata = addOrUpdateMetadata(section, 
                                                command.getCommandKey(), 
                                                command.getRemSubKey(),
                                                command.getRemSubKey(), //use subkey as metadata.
                                                validCommands,
                                                discardedCommands,
                                                invalidCommands,
                                                messages);

        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseRemComment(Command)");
    }
    /**
    * Parse the non-standard REM GENRE command.
    *
    * REM GENRE [genre]
    * 
    * @param command
    */
    private static void parseRemGenre(Section section, Command command) {
      
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseRemGenre(Command)", command);
        
        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){
            
            String genre = command.getValueMap().get(key);
            
             if (!PATTERN_QUOTED_OR_NO_BLANK.matcher(genre).matches()) {
                
                addWarning(messages, section, key, genre, WARNING_INVALID_GENRE);
                invalidCommands.add(command);
                
            } else { 
                 
                validCommands.add(command);
            }
        }
        
        Metadata metadata = addOrUpdateMetadata(section, 
                                            command.getCommandKey(), 
                                            command.getRemSubKey(),
                                            command.getRemSubKey(), //use subkey as metadata.
                                            validCommands,
                                            discardedCommands,
                                            invalidCommands,
                                            messages);

        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseRemGenre(LineOfInput)");
    }
    /**
    * Parse the non-standard REM YEAR command.
    *
    * REM YEAR [year]
    * 
    * @param command
    */
    private static void parseRemYear(Section section, Command command) {
      
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseRemYear(Command)", command);
        
        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){
            
            String value = command.getValueMap().get(key);
            
            if (!StringUtils.isNumeric(value)) {
                
                addWarning(messages, section, key, value, WARNING_INVALID_YEAR);
                invalidCommands.add(command);
                
            } else { 
                int year = Integer.parseInt(value);

                if (year < 1 || year > 9999) {
                    addWarning(messages, section, key,  value, WARNING_INVALID_YEAR);
                    invalidCommands.add(command);
                
                } else{
                    validCommands.add(command);
                }
            }
        }
        
        Metadata metadata = addOrUpdateMetadata(section, 
                                            command.getCommandKey(), 
                                            command.getRemSubKey(),
                                            command.getRemSubKey(), //use subkey as metadata.
                                            validCommands,
                                            discardedCommands,
                                            invalidCommands,
                                            messages);

        int year=0;
        if (StringUtils.isNumeric(metadata.getValue())){
            year = Integer.parseInt(metadata.getValue());
        }

        if (section instanceof AlbumSection) {
            
            section.getCuesheet().setYear(year);
        }

        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseRemYear(LineOfInput)");
    }
    /**
    * Parse the non-standard REM DISCID command.
    *
    * REM DISCID [discid]
    * 
    * @param command
    */
    private static void parseRemDiscId(Section section, Command command) {
      
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseRemDiscId(Command)", command);
        
        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){
            
            String discId = command.getValueMap().get(key);
            
             if (!PATTERN_QUOTED_OR_NO_BLANK.matcher(discId).matches()) {
                
                addWarning(messages, section, key, discId, WARNING_INVALID_DISCID);
                invalidCommands.add(command);
                
            } else { 
                 
                validCommands.add(command);
            }
        }
        
        Metadata metadata = addOrUpdateMetadata(section, 
                                            command.getCommandKey(), 
                                            command.getRemSubKey(),
                                            command.getRemSubKey(), //use subkey as metadata.
                                            validCommands,
                                            discardedCommands,
                                            invalidCommands,
                                            messages);

        if (section instanceof AlbumSection) {
            
            section.getCuesheet().setDiscid(metadata.getValue());
        }

        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseRemDiscId(LineOfInput)");
    }
    /**
    * Parse the non-standard REM DISC_NO command.
    *
    * REM DISC_NO [discnumber]
    * 
    * @param command
    */
    private static void parseRemDiscNumber(Section section, Command command) {
      
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseRemDate(Command)", command);
        
        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){
            
            String value = command.getValueMap().get(key);
            
            if (!StringUtils.isNumeric(value)) {
                
                addWarning(messages, section, key, value, WARNING_INVALID_DISC_NUMBER);
                invalidCommands.add(command);
                
            } else { 
                int discNo = Integer.parseInt(value);

                if (discNo < 1 || discNo > 9999) {
                    addWarning(messages, section, key,  value, WARNING_INVALID_DISC_NUMBER);
                    invalidCommands.add(command);
                
                } else{
                    validCommands.add(command);
                }
            }
        }
        
        Metadata metadata = addOrUpdateMetadata(section, 
                                            command.getCommandKey(), 
                                            command.getRemSubKey(),
                                            command.getRemSubKey(), //use subkey as metadata.
                                            validCommands,
                                            discardedCommands,
                                            invalidCommands,
                                            messages);

        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseRemDate(LineOfInput)");
    }
     /**
    * Parse the non-standard REM DISC_NO command.
    *
    * REM DISC_NO [discnumber]
    * 
    * @param command
    */
    private static void parseRemDiscTotal(Section section, Command command) {
      
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseRemDate(Command)", command);
        
        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){
            
            String value = command.getValueMap().get(key);
            
            if (!StringUtils.isNumeric(value)) {
                
                addWarning(messages, section, key, value, WARNING_INVALID_DISC_TOTAL);
                invalidCommands.add(command);
                
            } else { 
                int discTotal = Integer.parseInt(value);

                if (discTotal < 1 || discTotal > 9999) {
                    addWarning(messages, section, key,  value, WARNING_INVALID_DISC_TOTAL);
                    invalidCommands.add(command);
                
                } else{
                    validCommands.add(command);
                }
            }
        }
        
        Metadata metadata = addOrUpdateMetadata(section, 
                                            command.getCommandKey(), 
                                            command.getRemSubKey(),
                                            command.getRemSubKey(), //use subkey as metadata.
                                            validCommands,
                                            discardedCommands,
                                            invalidCommands,
                                            messages);

        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseRemDate(LineOfInput)");
    }
    /**
    * Parse REM commands not directly recognized in parseREM.
    *
    * ISRC [code]
    * International Standard Recording Code of track. Must come after TRACK, but before INDEX.
    * 
    * @param input
    */
    private static void parseRemOther(Section section, Command command){
   
        logger.entering(CueSheetMetadaParser.class.getCanonicalName(), "parseRemOther(Command)", command);
        
        ArrayList<Command> validCommands= new ArrayList<>();
        ArrayList<Command> discardedCommands= new ArrayList<>();  
        ArrayList<Command> invalidCommands= new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        
        for (int key : command.getValueMap().keySet()){

            String args = command.getValueMap().get(key);

            validCommands.add(command);
        
        }
        Metadata metadata = addOrUpdateMetadata(section, 
                                                command.getCommandKey(), 
                                                command.getRemSubKey(),
                                                command.getRemSubKey(), //use subkey as metadata.
                                                validCommands,
                                                discardedCommands,
                                                invalidCommands,
                                                messages);
        
        logger.exiting(CueSheetMetadaParser.class.getCanonicalName(), "parseRemOther(Command)");
   }
   private static Metadata addOrUpdateMetadata(Section section, 
                                            COMMAND_KEY commandKey, 
                                            String remSubkey,
                                            String metadataKey,
                                            List<Command> validCommands,  
                                            List<Command> discardedCommands,  
                                            List<Command> invalidCommands,
                                            List<Message> messages){
        
        CueMetadataOrigin origin = new CueMetadataOrigin(section.getSourceId(),
                                                            commandKey.name(),
                                                            remSubkey,
                                                            validCommands,
                                                            discardedCommands,
                                                            invalidCommands,
                                                            messages);

        Metadata metadata = section.getMedata(metadataKey);

        if (metadata == null ){
           
            String alias;
            if (section instanceof AlbumSection){
                alias =getAlbumLevelMetadataAlias(metadataKey);
            } else {
                 alias =getTrackLevelMetadataALias(metadataKey);
            }

            if (alias != null && !alias.isEmpty()){
            
                metadataKey= alias;
            }
            
            metadata = new Metadata(metadataKey, origin);
            section.getMetadata().add(metadata);
        } else  {
            metadata.addOrigin(origin);
        }
        
        return metadata;
    }
   /**
    * Write a warning to the logging and the cueSheet associated.
    * @param messages the list of messages for the metadata.
    * @param section the section related to the message.
    * @param lineNumber the line tof text number in the cuesheet that originated the warning.
    * @param input The content of the line the warning pertains to.
    * @param warning The warning to write.
    */
    protected static void addWarning(ArrayList<Message> messages, Section section, final int lineNumber, final String input, final String warning) {
        logger.warning(warning);
        Message msg= new Warning(input, lineNumber, warning);
        section.getCuesheet().getMessages().add( msg);
        messages.add(msg);
                
    }
    /**
    * Write a warning to the logging and the cueSheet associated.
    * @param messages the list of messages for the metadata.
    * @param cuesheet the cueschet related to the message.
    * @param lineNumber the line tof text number in the cuesheet that originated the warning.
    * @param input The content of the line the warning pertains to.
    * @param warning The warning to write.
    */
    protected static void addWarning(ArrayList<Message> messages, CueSheet cuesheet, final int lineNumber, final String input, final String warning) {
        logger.warning(warning);
        Message msg= new Warning(input, lineNumber, warning);
        cuesheet.getMessages().add( msg);
        messages.add(msg);
                
    }
    /* Write a warning to the logging and the cueSheet associated.
    * @param messages the list of messages for the metadata.
    * @param trackData the trackdata related to the message.
    * @param lineNumber the line tof text number in the cuesheet that originated the warning.
    * @param input The content of the line the warning pertains to.
    * @param warning The warning to write.
    */
    protected static void addWarning(ArrayList<Message> messages, TrackData trackData, final int lineNumber, final String input, final String warning) {
        logger.warning(warning);
        Message msg= new Warning(input, lineNumber, warning);
        trackData.getParent().getParent().getMessages().add( msg);
        messages.add(msg);
                
    }
}