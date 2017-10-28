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

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jwbroek.cuelib.Index;
import jwbroek.cuelib.LineOfInput;
import jwbroek.cuelib.Position;
//import jwbroek.cuelib.TrackData;
import org.apache.commons.io.input.BOMInputStream;
import org.mc2.audio.metadata.source.cue.CommandKeys.COMMAND_KEY;

/**
 * Parser for cue sheets. 
 * Based on jwbroek.cuelib.CueSheetCommandParser by jwbroek, it only read the input 
 cuesheet and returns  plain non validated COMMANDS, not metadata.
 
 It's a copy and not a subclass just becouse jwbroek.cuelib.CueSheetCommandParser is
 decared final, so I believe the author did not want other to subclass it.
 * 
 * @author marcoc1712
 */
final public class CueSheetCommandParser {
    /**
     * Logger for this class.
    */
    private final static Logger logger = Logger.getLogger(CueSheetCommandParser.class.getCanonicalName());

    /**
    * A set of all file types that are allowed by the cue sheet spec.
    */
    private final static Set<String> COMPLIANT_FILE_TYPES = new TreeSet<String> ( 
            Arrays.asList ( new String[]  { "BINARY"
                                            , "MOTOROLA"
                                            , "AIFF"
                                            , "WAVE"
                                            , "MP3"
    }));
  
    /**
   * A set of all data types that are allowed by the cue sheet spec.
   */
    private final static Set<String> COMPLIANT_DATA_TYPES = new TreeSet<String> ( 
            Arrays.asList ( new String[]  { "AUDIO"
                                            , "CDG"
                                            , "MODE1/2048"
                                            , "MODE1/2352"
                                            , "MODE2/2336"
                                            , "MODE2/2352"
                                            , "CDI/2336"
                                            , "CDI/2352"
    }));
    
    
    // Constants for warning texts. Quick and dirty. Should really be a ResourceBundle.
    private final static String WARNING_EMPTY_LINES             = "Empty lines not allowed. Will ignore.";
    private final static String WARNING_UNPARSEABLE_INPUT       = "Unparseable line. Will ignore.";
    private final static String WARNING_NOT_ALLOWED_HERE        = "Command is not allowed here. Will ignore.";
    private final static String WARNING_NO_FILE_SPECIFIED       = "Datum must appear in FILE, but no FILE specified.";
    private final static String WARNING_NO_TRACK_SPECIFIED      = "Datum must appear in TRACK, but no TRACK specified.";
    private final static String WARNING_WRONG_NUMBER_OF_DIGITS  = "Wrong number of digits in number.";
    private final static String WARNING_NONCOMPLIANT_DATA_TYPE  = "Noncompliant data type specified.";
    private final static String WARNING_INVALID_TRACK_NUMBER    = "Invalid track number. First number must be 1; all next ones sequential.";
    private final static String WARNING_TOKEN_NOT_UPPERCASE     = "Token has wrong case. Uppercase was expected.";
    private final static String WARNING_NONCOMPLIANT_FILE_TYPE  = "Noncompliant file type.";
    private final static String WARNING_FILE_IN_WRONG_PLACE     = "A FILE datum must come before everything else except REM and CATALOG.";
    private final static String WARNING_INDEX_AFTER_POSTGAP     = "A POSTGAP datum must come after all INDEX data of a TRACK.";
    private final static String WARNING_INVALID_INDEX_NUMBER    = "Invalid index number. First number must be 0 or 1; all next ones consequential.";
    private final static String WARNING_INVALID_FIRST_POSITION  = "Invalid position. First index should have position 00:00:00";
    private final static String WARNING_INVALID_SECONDS_VALUE   = "Position has invalid seconds value. Should be 00-59.";
    private final static String WARNING_INVALID_FRAMES_VALUE    = "Position has invalid frame value. Should be 00-74.";
    private final static String WARNING_DATUM_APPEARS_TOO_OFTEN = "Datum appears too often.";
    private final static String WARNING_PREGAP_IN_WRONG_PLACE   = "A PREGAP datum must come after TRACK, but before any INDEX of that TRACK.";

    private final static Pattern PATTERN_FILE                   = Pattern.compile
        ("^"+COMMAND_KEY.FILE+"\\s+((?:\"[^\"]*\")|\\S+)\\s+(\\S+)\\s*$", Pattern.CASE_INSENSITIVE);
    private final static Pattern PATTERN_TRACK                  = Pattern.compile
        (COMMAND_KEY.TRACK+"\\s+(\\d+)\\s+(\\S+)\\s*$", Pattern.CASE_INSENSITIVE);
    private final static Pattern PATTERN_PREGAP                 = Pattern.compile
    ("^"+COMMAND_KEY.PREGAP+"\\s+(\\d*:\\d*:\\d*)\\s*$", Pattern.CASE_INSENSITIVE);
    private final static Pattern PATTERN_INDEX                  = Pattern.compile
        ("^"+COMMAND_KEY.INDEX+"\\s+(\\d+)\\s+(\\d*:\\d*:\\d*)\\s*$", Pattern.CASE_INSENSITIVE);
    private final static Pattern PATTERN_POSITION               = Pattern.compile
        ("^(\\d*):(\\d*):(\\d*)$");
    private final static Pattern PATTERN_POSTGAP                = Pattern.compile
    ("^"+COMMAND_KEY.POSTGAP+"\\s+(\\d*:\\d*:\\d*)\\s*$", Pattern.CASE_INSENSITIVE);
    private final static Pattern PATTERN_REM_COMMAND_ARGS       = Pattern.compile
        ("^("+COMMAND_KEY.REM+"\\s)+([A-Z0-9\\.\\-\\_]+\\s)+(.+)\\s*$"); // case sensitive!
    private final static Pattern PATTERN_REM_COMMENT            = Pattern.compile
        ("^("+COMMAND_KEY.REM+"\\s)+(.+)\\s*$"); // case sensitive!
  
    /**
    * Create a CueSheetCommandParser. Should never be used, as all properties and methods of this class are static. 
    */
    private CueSheetCommandParser () {
        // Intentionally left blank (besides logging). This class doesn't need to be instantiated.
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "CueSheetMetadaParser()");
        logger.log(Level.WARNING, "{0}should not be initialized", this.getClass().getCanonicalName());
        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "CueSheetMetadaParser()");
    }
  
    /**
    * Parse a cue sheet that will be read from the InputStream.
    * @param inputStream An {@link java.io.InputStream} that produces a cue sheet. The stream will be closed
    * afterward.
    * @param source An identifier for the source.
    * @return A representation of the cue sheet.
    * @throws IOException
    */
    public static CueSheet parse(final InputStream inputStream, String source) throws IOException {
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "parse(InputStream)", inputStream);

        // use a BufferedInputStream becouse we need do mark BEFORE testing
        // for the BOM and then reset if the bom is not there. 

        InputStream buffered = new BufferedInputStream(inputStream);
        buffered.markSupported();
        buffered.mark(0);

        // Some cue sheets have BOM for encoding, we should test and remove it before 
        // parse (other than use correct encoding...).

        BOMInputStream bOMInputStream = new BOMInputStream(buffered);

        final CueSheet result;
        
        if (bOMInputStream.hasBOM()) {
            // has a UTF-8 BOM
            result = CueSheetCommandParser.parse(new LineNumberReader(new InputStreamReader(buffered,"UTF8")), source);
            result.setEncoding(StandardCharsets.UTF_8);
            
            
        } else{
            buffered.reset();
            result = CueSheetCommandParser.parse(new LineNumberReader(new InputStreamReader(buffered)), source);
            result.setEncoding(StandardCharsets.ISO_8859_1);
        }
        //result.setSourceId(source);   
        //final CueSheet result = CueParser.parse(new LineNumberReader(new InputStreamReader(inputStream)));

        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parse(InputStream)", result);

        return result;
    }

    /**
    * Parse a cue sheet file.
    * @param file A cue sheet file.
    * @return A representation of the cue sheet.
    * @throws IOException
    */
    public static CueSheet parse(final File file) throws IOException {
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "parse(File)", file);

        InputStream inputStream = new FileInputStream(file);
        final CueSheet result = parse(inputStream,file.getCanonicalPath());

        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parse(File)", result);
        return result;
    }

    /**
    * Parse a cue sheet.
    * @param reader A reader for the cue sheet. This reader will be closed afterward.
    * @param source An identifier for the source.
    * @return A representation of the cue sheet.
    * @throws IOException
    */
    public static CueSheet parse(final LineNumberReader reader, String source) throws IOException {
        
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "parse(LineNumberReader)", reader);
        logger.fine("Parsing cue sheet.");

        final CueSheet result = new CueSheet();
        result.setSourceId(source);

        try {
            
            // Go through all lines of input.
            String inputLine= reader.readLine();

            while(inputLine != null) {
                logger.finest("Processing input line.");

                // Normalize by removing left and right whitespace.
                inputLine = inputLine.trim();

                final LineOfInput input = new LineOfInput(reader.getLineNumber(), inputLine, result);
                result.addLine(input);

                // Do some validation. If there are no problems, then parse the line.
                if (inputLine.length()==0)
                {
                  // File should not contain empty lines.
                  addWarning(input, WARNING_EMPTY_LINES);
                }
                else if (inputLine.length() < 3)
                {
                  // No token in the spec has length smaller than 3. Unknown token.
                  addWarning(input, WARNING_UNPARSEABLE_INPUT);
                }
                else {
                    
                    parseInputLine(input);

                }

                // And on to the next line...
                inputLine = reader.readLine();
            }
        } finally {
            logger.finest("Closing input reader.");
            reader.close();

            result.adjustLength();
        }

        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parse(LineNumberReader)", result);

        return result;

    }
    /**
    * Parse a cue sheet row
    * @param LineOfInput A line of the cue sheet.
    */
    private static void parseInputLine(final LineOfInput input) {
   
        COMMAND_KEY commandKey = CommandKeys.getCommandKey(input.getInput());
        
        if (commandKey == null){
            addWarning(input, WARNING_UNPARSEABLE_INPUT);
        } else {
        
            switch (commandKey){
                
                case FILE:
                    parseFile(input);
                    break;
                case TRACK:
                    parseTrack(input);
                    break;
                case PREGAP:
                    parsePregap(input);
                    break;
                case INDEX:
                    parseIndex(input);
                    break;
                case POSTGAP:
                    parsePostgap(input);
                    break;
                case REM:
                    parseRem(input);
                    break;
                default:
                    if (isAtAlbumLevel(input) && CommandKeys.isAlbumCommandKey(commandKey)){
                    
                        addCommandLine(commandKey, "", input);
                        
                    } else if (!isAtAlbumLevel(input) && CommandKeys.isTrackCommandKey(commandKey)){
                    
                        addCommandLine(commandKey, "", input);
                    
                    } else{
                        
                        addWarning(input, WARNING_NOT_ALLOWED_HERE);
                    
                    }
                    break;
            }
        }
    }
    /**
    * Parse the FILE command.
    *
    * FILE [filename] [filetype]
    * File containing data.
    * According to the spec it must come before every other command except CATALOG. This rule
    * contradicts the official examples and is often broken in practice. Hence, we don't raise
    * a warning when this rule is broken.
    * 
    * @param input
    */
    private static void parseFile(final LineOfInput input) {
        
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "parseFile(LineOfInput)", input);
 
        Matcher fileMatcher = PATTERN_FILE.matcher(input.getInput());
        
        if (fileMatcher.matches()) {

            if (!COMPLIANT_FILE_TYPES.contains(fileMatcher.group(2))){

                if (COMPLIANT_FILE_TYPES.contains(fileMatcher.group(2).toUpperCase())){
                  addWarning(input, WARNING_TOKEN_NOT_UPPERCASE);
                }else {
                  addWarning(input, WARNING_NONCOMPLIANT_FILE_TYPE);
                }

            }

            /*
             *  This is a silly rule that is very commonly broken. Hence, we don't enforce it.
             *  
             *  Check to see if FILE is the first command in the sheet, except for CATALOG. (Technically, we should
             *  also check for REM commands, but we don't keep track of all of those.)
             *  
            if (  input.getAssociatedSheet().getFileData().size()==0
               && (  input.getAssociatedSheet().getCdTextFile() != null
                  || input.getAssociatedSheet().getPerformer() != null
                  || input.getAssociatedSheet().getSongwriter() != null
                  || input.getAssociatedSheet().getTitle() != null
                  || input.getAssociatedSheet().getComment() != null
                  || input.getAssociatedSheet().getDiscid() != null
                  || input.getAssociatedSheet().getYear() != -1
                  || input.getAssociatedSheet().getGenre() != null
                  )
               )
            {
              CueParser.logger.warning(WARNING_FILE_IN_WRONG_PLACE);
              input.getAssociatedSheet().addWarning(input, WARNING_FILE_IN_WRONG_PLACE);
            }
             */

            // If the file name is enclosed in quotes, remove those. 
            String file = fileMatcher.group(1);
            if (file.length() > 0 && file.charAt(0)=='"' && file.charAt(file.length()-1)=='"') {
              file = file.substring(1, file.length()-1);
            }

            input.getAssociatedSheet().getFileData().add  ( new FileData  ( input
                                                                          , file
                                                                          , fileMatcher.group(2).toUpperCase()
                                                                          )
                                                          );

        }else {
            
            addWarning(input, WARNING_UNPARSEABLE_INPUT);
        }
        
        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parseFile(LineOfInput)");
    }
    /**
    * Parse the TRACK command.
    *
    * TRACK [number] [datatype]
    * Beginning of track data.
    * First track number may be > 1, but all others must be sequential. Allowed are 1-99 inclusive.
    * 
    * Modes recognized by the spec. (Others will be parsed, but will also cause a warning to be
    * raised.)
    * AUDIO - Audio/Music (2352)
    * CDG - Karaoke CD+G (2448)
    * MODE1/2048 - CDROM Mode1 Data (cooked)
    * MODE1/2352 - CDROM Mode1 Data (raw)
    * MODE2/2336 - CDROM-XA Mode2 Data
    * MODE2/2352 - CDROM-XA Mode2 Data
    * CDI/2336 - CDI Mode2 Data
    * CDI/2352 - CDI Mode2 Data
    * 
    * @param input
    */
    private static void parseTrack(final LineOfInput input) {
    
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "parseTrack(LineOfInput)", input);
    
        Matcher trackMatcher = PATTERN_TRACK.matcher(input.getInput());

        if (trackMatcher.matches()) {
            
            if (trackMatcher.group(1).length() != 2) {
              addWarning(input, WARNING_WRONG_NUMBER_OF_DIGITS);
            }
            int trackNumber = Integer.parseInt(trackMatcher.group(1));

            String dataType = trackMatcher.group(2);      
            if (!COMPLIANT_DATA_TYPES.contains(dataType)) {
              addWarning(input, WARNING_NONCOMPLIANT_DATA_TYPE);
            }

            List<jwbroek.cuelib.TrackData> trackDataList = input.getAssociatedSheet().getAllTrackData();

            // First track must have number 1; all next ones sequential.
            if (  trackDataList.isEmpty() && trackNumber != 1
               || ! trackDataList.isEmpty() && trackDataList.get(trackDataList.size()-1).getNumber() != trackNumber - 1 ) {

                addWarning(input, WARNING_INVALID_TRACK_NUMBER);
            }

            FileData lastFileData = getLastFileData(input);
            lastFileData.getTrackData().add(new TrackData(lastFileData, trackNumber, dataType,0,0));
        }
        else {
          
            addWarning(input, WARNING_UNPARSEABLE_INPUT);
            
        }

        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parseTrack(LineOfInput)");
    }
    /**
    * Parse the PREGAP command.
    *
    * PREGAP [mm:ss:ff]
    * Must come after TRACK, but before INDEX fields for that track.
    * 
    * @param input
    */
    private static void parsePregap(final LineOfInput input) {
        
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "parsePregap(LineOfInput)", input);

        Matcher pregapMatcher = PATTERN_PREGAP.matcher(input.getInput());

        if (pregapMatcher.matches()) {
          
            TrackData trackData = getLastTrackData(input);
            
            if (trackData.getPregap() != null) {
              addWarning(input, WARNING_DATUM_APPEARS_TOO_OFTEN);
            }

            if (trackData.getIndices().size() > 0) {
              addWarning(input, WARNING_PREGAP_IN_WRONG_PLACE);
            }

            trackData.setPregap(parsePosition(input, pregapMatcher.group(1)));
        }
        else {
          addWarning(input, WARNING_UNPARSEABLE_INPUT);
        }

        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parsePregap(LineOfInput)");
    }

    /**
    * Parse the INDEX command.
    *
    * INDEX [number] [mm:ss:ff]
    * Indexes or subindexes within a track. Relative w.r.t. beginning of file.
    * 
    * ff = frames; 75 frames/s
    * First index must be 0 or 1. All others sequential. First index must be 00:00:00
    * 0 is track pregap.
    * 1 is starting time of track data.
    * > 1 is subindex within track.
    * 
    * @param input
    */
    private static void parseIndex(final LineOfInput input) {
        
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "parseIndex(LineOfInput)", input);

        Matcher indexMatcher = PATTERN_INDEX.matcher(input.getInput());

        if (indexMatcher.matches()) {
            if (indexMatcher.group(1).length() != 2) {
              addWarning(input, WARNING_WRONG_NUMBER_OF_DIGITS);
            }

            TrackData trackData = getLastTrackData(input);
            List<TrackIndex> trackIndices = trackData.getTrackIndexList();

            // Postgap data must come after all index data. Only check for first index. No need to repeat this warning for
            // all indices that follow.
            if (trackIndices.isEmpty() && trackData.getPostgap() != null) {
              addWarning(input, WARNING_INDEX_AFTER_POSTGAP);
            }

            int indexNumber = Integer.parseInt(indexMatcher.group(1));

            // If first index of track, then number must be 0 or 1; if not first index of track, then number must be 1
            // higher than last one.  
            if ((trackIndices.isEmpty() && indexNumber > 1) ||
                ( ! trackIndices.isEmpty() && 
                  trackIndices.get(trackIndices.size()-1).getNumber() != indexNumber - 1 ))   {
                
                addWarning(input, WARNING_INVALID_INDEX_NUMBER);
            
            }

            List<Index> fileIndices = getLastFileData(input).getAllIndices();

            Position position = parsePosition(input, indexMatcher.group(2));

            // Position of first index of file must be 00:00:00.
            if (  fileIndices.isEmpty() && 
                 ! (  position.getMinutes() == 0 &&
                      position.getSeconds() == 0 && 
                      position.getFrames()  == 0 )) {
                
                addWarning(input, WARNING_INVALID_FIRST_POSITION);
            }
            trackData.addTrackIndex(new TrackIndex(trackData, indexNumber, position));
        }
        else {
            
            addWarning(input, WARNING_UNPARSEABLE_INPUT);
        }

        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parseIndex(LineOfInput)");
    }
    /**
   * Parse the POSTGAP command.
   *
   * POSTGAP [mm:ss:ff]
   * Must come after all INDEX fields for a track. Only one per track allowed.
   * 
   * @param input
   */
  private static void parsePostgap(final LineOfInput input) {
        
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "parsePostgap(LineOfInput)", input);

        Matcher postgapMatcher = PATTERN_POSTGAP.matcher(input.getInput());

        if (postgapMatcher.matches()) {
            TrackData trackData = getLastTrackData(input);

            if (trackData.getPostgap() != null) {
              addWarning(input, WARNING_DATUM_APPEARS_TOO_OFTEN);
            }

            trackData.setPostgap(parsePosition(input, postgapMatcher.group(1)));
        
        } else {
        
            addWarning(input, WARNING_UNPARSEABLE_INPUT);
        }

        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parsePostgap(LineOfInput)");
    }
    /**
    * Parse a position, as used by several commands.
    *
    * [mm:ss:ff]
    * mm = minutes
    * ss = seconds
    * ff = frames (75 per second)
    * 
    * @param input
    */
    private static Position parsePosition(final LineOfInput input, final String position){
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "parsePosition(LineOfInput)", input);

        Matcher positionMatcher = PATTERN_POSITION.matcher(position);

        if (positionMatcher.matches()) {
          String minutesString  = positionMatcher.group(1);
          String secondsString  = positionMatcher.group(2);
          String framesString   = positionMatcher.group(3);
          int minutes           = Integer.parseInt(minutesString);
          int seconds           = Integer.parseInt(secondsString);
          int frames            = Integer.parseInt(framesString);

          if  ( ! (  minutesString.length() == 2
                  && secondsString.length() == 2
                  && framesString.length()  == 2  )) {
              
                addWarning(input, WARNING_WRONG_NUMBER_OF_DIGITS);
          }

          if (seconds > 59) {
                addWarning(input, WARNING_INVALID_SECONDS_VALUE);
          }

          if (frames > 74) {
                addWarning(input, WARNING_INVALID_FRAMES_VALUE);
          }

          Position result = new Position(minutes, seconds, frames);
          logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parsePosition(LineOfInput)", result);
          return result;
        }
        else {
          addWarning(input, WARNING_UNPARSEABLE_INPUT);
          Position result = new Position();
          logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parsePosition(LineOfInput)", result);
          return result;
        }
    }
    /**
    * Parse the REM command.
    * 
    * REM [comment] are translated to REM COMMENT [command].
    */        
    private static void parseRem(final LineOfInput input){
   
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "parseRem(LineOfInput)", input);
        
        Matcher matcherCommand = PATTERN_REM_COMMAND_ARGS.matcher(input.getInput());

        if (matcherCommand.find()) {
            String remSubKey = matcherCommand.group(2).trim();
            String args = matcherCommand.group(3).trim();
           
            addCommandLine(COMMAND_KEY.REM, remSubKey, input);
  
        } else {
            
            // adjust input to be parsed correctly.
            LineOfInput line= new LineOfInput(input.getLineNumber(), "COMMENT"+input.getInput(),  input.getAssociatedSheet());
            addCommandLine(COMMAND_KEY.REM, "COMMENT", line);
        } 
        
        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parseRem(LineOfInput)");
   }

    /**
    * Determine if the input is at Album or Track level. Will return true if at Album level.
    * 
    * @param input The input to check.
    **/
    private static boolean isAtAlbumLevel (final LineOfInput input){

        // First check file data, as getLastFileData will create a FileData instance if there is none
       // and we don't actually want to create such an instance.

       return (  input.getAssociatedSheet().getFileData().isEmpty() || 
                 getLastFileData(input).getTrackData().isEmpty() );
    }
    /**
    * Get the last {@link jwbroek.cuelib.FileData} element. If none exist, an empty one is created and a warning
    * added.
    * @param input
    * @return The last {@link jwbroek.cuelib.FileData} element. If none exist, an empty one is created and a warning
    * added.
    */
    private static FileData getLastFileData(final LineOfInput input) {
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "getLastFileData(LineOfInput)", input);

        List<jwbroek.cuelib.FileData> fileDataList = input.getAssociatedSheet().getFileData();

        if (fileDataList.isEmpty()) {
          fileDataList.add(new FileData((CueSheet)input.getAssociatedSheet()));
          addWarning(input, WARNING_NO_FILE_SPECIFIED);
        }

        FileData result = (FileData)fileDataList.get(fileDataList.size()-1);
        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "getLastFileData(LineOfInput)", result);
        return result;
    }
    /**
    * Get the last {@link jwbroek.cuelib.TrackData} element. If none exist, an empty one is created and a warning
    * added.
    * @param input
    * @return The last {@link jwbroek.cuelib.TrackData} element. If none exist, an empty one is created and a
    * warning added.
    */
    private static TrackData getLastTrackData(final LineOfInput input) {
    
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "getLastTrackData(LineOfInput)", input);

        FileData lastFileData = getLastFileData(input);
        List<TrackData> trackDataList = lastFileData.getTrackDataList();

        if (trackDataList.isEmpty()) {
          trackDataList.add(new TrackData(lastFileData));
          addWarning(input, WARNING_NO_TRACK_SPECIFIED);
        }

        TrackData result = trackDataList.get(trackDataList.size()-1); 
        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "getLastTrackData(LineOfInput)", result);
        return result;
    }

    /**
    * Add the parsed command line.
    * @param commandKey
    * @param commandKey
    * @param input
    */
    private static void addCommandLine(final COMMAND_KEY commandKey, final String remSubKey, final LineOfInput input) {
        
        logger.entering(CueSheetCommandParser.class.getCanonicalName(), "addCommand(command, LineOfInput)");
        
        String value = input.getInput().substring(commandKey.name().length()).trim();
        if (!remSubKey.isEmpty()){
            value = value.substring(remSubKey.length()+1).trim();
        }
        
        if ( isAtAlbumLevel(input)) {
        
            CueSheet cuesheet = (CueSheet) input.getAssociatedSheet();
             addCommandLine(commandKey, remSubKey, cuesheet, input.getLineNumber(), value);

        } else {
        
            TrackData trackData = getLastTrackData(input);
            addCommandLine(commandKey, remSubKey, trackData, input.getLineNumber(), value);
        }

        logger.exiting(CueSheetCommandParser.class.getCanonicalName(), "parseCatalog(command, LineOfInput)");
    }
    
    private static void addCommandLine(COMMAND_KEY commandKey, String remSubKey,CueSheet cuesheet, int lineNo, String value){

        for (Command command : cuesheet.getCommands()){
        
            if (command.getCommandKey().equals(commandKey) && command.getRemSubKey().equals(remSubKey)){
            
                command.addValue(lineNo, value);
                return;
            }
            
        }
        cuesheet.getCommands().add(new Command(commandKey,remSubKey,lineNo, value)); 
    }
    private static void addCommandLine(COMMAND_KEY commandKey, String remSubKey,TrackData trackData, int lineNo, String value){

        for (Command command : trackData.getCommandList()){
        
            if (command.getCommandKey().equals(commandKey) && command.getRemSubKey().equals(remSubKey)){
                
                command.addValue(lineNo, value);
                return;
            }
            
        }
        trackData.getCommandList().add(new Command(commandKey,remSubKey,lineNo, value)); 
    }
    /**
    * Write a warning to the logging and the {@link CueSheet} associated with the
    * {@link jwbroek.cuelib.LineOfInput}.
    * @param input The {@link jwbroek.cuelib.LineOfInput} the warning pertains to.
    * @param warning The warning to write.
    */
    protected static void addWarning(final LineOfInput input, final String warning) {
        logger.warning(warning);
        input.getAssociatedSheet().addWarning(input, warning);
    }
}