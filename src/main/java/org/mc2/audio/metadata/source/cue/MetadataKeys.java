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

import java.util.HashMap;

/**
 * Enumeration of 'common' metadata derived from Command keys.
 * For a more complete metadata list see: rg.jaudiotagger.tag.FieldKey 
 *
 * @author marcoc1712
 */
public class MetadataKeys {
      
    public static enum METADATA_KEY{
                            CATALOG_NO,
                            ALBUM_ARTIST,
                            ARTIST,
                            COMPOSER,
                            ALBUM,
                            TITLE, 
                            ISRC,
                            CDTEXTFILE, //to be discarded?
                        //  CONTENT_TYPE, // not really a metadata, discarded.
                        //  FLAGS, //in case tobe esxposed as per single 
                        //  flag meaning, but they are not really metatada,
                        //  just like TYPE.
                            //IS_DIGITAL_COPY_PERMITTED,
                            //IS_FOUR_CHANNEL_AUDIO,
                            //IS_PRE_EMPHASIS_ENABLED,
                            //IS_SERIAL_COPY_MANAGEMNT_SYSTEM
                            
    };

    public static final HashMap<String,String> ALBUM_LEVEL_ALIAS;
    static {
        ALBUM_LEVEL_ALIAS = new HashMap<>();
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.CATALOG_NO.name(), METADATA_KEY.CATALOG_NO.name());
        ALBUM_LEVEL_ALIAS.put("CATALOG", METADATA_KEY.CATALOG_NO.name());
        ALBUM_LEVEL_ALIAS.put("CATALOGUE", METADATA_KEY.CATALOG_NO.name());
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.ALBUM_ARTIST.name(), METADATA_KEY.ALBUM_ARTIST.name());
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.ARTIST.name(), METADATA_KEY.ALBUM_ARTIST.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMARTIST", METADATA_KEY.ALBUM_ARTIST.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_ARTIST", METADATA_KEY.ALBUM_ARTIST.name());
        ALBUM_LEVEL_ALIAS.put("PERFORMER", METADATA_KEY.ALBUM_ARTIST.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMPERFORMER", METADATA_KEY.ALBUM_ARTIST.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_PERFORMER", METADATA_KEY.ALBUM_ARTIST.name());


        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.COMPOSER.name(), METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("SONGWRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("SONG_WRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMSONGWRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_SONGWRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_SONG_WRITER", METADATA_KEY.COMPOSER.name());
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.ALBUM.name(), METADATA_KEY.ALBUM.name());
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.TITLE.name(), METADATA_KEY.ALBUM.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMTITLE", METADATA_KEY.ALBUM.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_TITLE", METADATA_KEY.ALBUM.name());

        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.CDTEXTFILE.name(), METADATA_KEY.CDTEXTFILE.name());
        
        ALBUM_LEVEL_ALIAS.put("YEAR", "YEAR");
        ALBUM_LEVEL_ALIAS.put("ALBUMYEAR", "YEAR");
        ALBUM_LEVEL_ALIAS.put("ALBUM_YEAR", "YEAR");
        ALBUM_LEVEL_ALIAS.put("DATE", "YEAR");
        ALBUM_LEVEL_ALIAS.put("ALBUMDATE", "YEAR");
        ALBUM_LEVEL_ALIAS.put("ALBUM_DATE", "YEAR");
        
        ALBUM_LEVEL_ALIAS.put("GENRE", "GENRE");
        ALBUM_LEVEL_ALIAS.put("ALBUMGENRE", "GENRE");
        ALBUM_LEVEL_ALIAS.put("ALBUM_GENRE", "GENRE");
        
        ALBUM_LEVEL_ALIAS.put("LYRICS", "LYRICS");
        ALBUM_LEVEL_ALIAS.put("ALBUMLYRICS", "LYRICS");
        ALBUM_LEVEL_ALIAS.put("ALBUM_LYRICS", "LYRICS");
        
        ALBUM_LEVEL_ALIAS.put("COMMENT", "COMMENT");
        ALBUM_LEVEL_ALIAS.put("ALBUMCOMMENT", "COMMENT");
        ALBUM_LEVEL_ALIAS.put("ALBUM_COMMENT", "COMMENT");
        
        ALBUM_LEVEL_ALIAS.put("DISC_NO", "DISC_NO");
        ALBUM_LEVEL_ALIAS.put("DISC", "DISC_NO");
        ALBUM_LEVEL_ALIAS.put("DISC#", "DISC_NO");
        ALBUM_LEVEL_ALIAS.put("DISCNO", "DISC_NO");
        ALBUM_LEVEL_ALIAS.put("DISCNUMBER", "DISC_NO");
        ALBUM_LEVEL_ALIAS.put("DISC_NUMBER", "DISC_NO");
        
        ALBUM_LEVEL_ALIAS.put("DISC_TOTAL", "DISC_TOTAL");
        ALBUM_LEVEL_ALIAS.put("DISCTOTAL", "DISC_TOTAL");
        ALBUM_LEVEL_ALIAS.put("TOTALDISC", "DISC_TOTAL");
        ALBUM_LEVEL_ALIAS.put("TOTAL_DISC", "DISC_TOTAL");
        ALBUM_LEVEL_ALIAS.put("DISCC", "DISC_TOTAL");

    }
    public static final HashMap<String,String> TRACK_LEVEL_ALIAS;
    static {
        TRACK_LEVEL_ALIAS = new HashMap<>();
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.ISRC.name(), METADATA_KEY.ISRC.name());
        TRACK_LEVEL_ALIAS.put("ISRCCODE", METADATA_KEY.ISRC.name());
        TRACK_LEVEL_ALIAS.put("ISRC_CODE", METADATA_KEY.ISRC.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.ARTIST.name(), METADATA_KEY.ARTIST.name());
        TRACK_LEVEL_ALIAS.put("PERFORMER", METADATA_KEY.ARTIST.name());
        TRACK_LEVEL_ALIAS.put("TRACKPERFORMER", METADATA_KEY.ARTIST.name());
        TRACK_LEVEL_ALIAS.put("TRACK_PERFORMER", METADATA_KEY.ARTIST.name());

        TRACK_LEVEL_ALIAS.put(METADATA_KEY.COMPOSER.name(), METADATA_KEY.COMPOSER.name());
        TRACK_LEVEL_ALIAS.put("SONGWRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("SONG_WRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("TRACKSONGWRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("TRACK_SONGWRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("TRACK_SONG_WRITER", METADATA_KEY.COMPOSER.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.TITLE.name(), METADATA_KEY.TITLE.name());
        TRACK_LEVEL_ALIAS.put("TRACKTITLE", METADATA_KEY.TITLE.name());
        TRACK_LEVEL_ALIAS.put("TRACK_TITLE", METADATA_KEY.TITLE.name());
        /*
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.CONTENT_TYPE.name(), METADATA_KEY.CONTENT_TYPE.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.IS_DIGITAL_COPY_PERMITTED.name(), METADATA_KEY.IS_DIGITAL_COPY_PERMITTED.name());
        TRACK_LEVEL_ALIAS.put("FLAG_DIGITAL_COPY_PERMITTED", METADATA_KEY.IS_DIGITAL_COPY_PERMITTED.name());
        TRACK_LEVEL_ALIAS.put("FLAG_DCP", METADATA_KEY.IS_DIGITAL_COPY_PERMITTED.name());
        TRACK_LEVEL_ALIAS.put("IS_DCP", METADATA_KEY.IS_DIGITAL_COPY_PERMITTED.name());
        TRACK_LEVEL_ALIAS.put("DCP", METADATA_KEY.IS_DIGITAL_COPY_PERMITTED.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.IS_FOUR_CHANNEL_AUDIO.name(), METADATA_KEY.IS_FOUR_CHANNEL_AUDIO.name());
        TRACK_LEVEL_ALIAS.put("FLAG_FOUR_CHANNEL_AUDIO", METADATA_KEY.IS_FOUR_CHANNEL_AUDIO.name());
        TRACK_LEVEL_ALIAS.put("FLAG_4CH", METADATA_KEY.IS_FOUR_CHANNEL_AUDIO.name());
        TRACK_LEVEL_ALIAS.put("IS_4CH", METADATA_KEY.IS_FOUR_CHANNEL_AUDIO.name());
        TRACK_LEVEL_ALIAS.put("4CH", METADATA_KEY.IS_FOUR_CHANNEL_AUDIO.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.IS_PRE_EMPHASIS_ENABLED.name(), METADATA_KEY.IS_PRE_EMPHASIS_ENABLED.name());
        TRACK_LEVEL_ALIAS.put("FLAG_PRE_EMPHASIS_ENABLED", METADATA_KEY.IS_PRE_EMPHASIS_ENABLED.name());
        TRACK_LEVEL_ALIAS.put("FLAG_PRE", METADATA_KEY.IS_PRE_EMPHASIS_ENABLED.name());
        TRACK_LEVEL_ALIAS.put("IS_PRE", METADATA_KEY.IS_PRE_EMPHASIS_ENABLED.name());
        TRACK_LEVEL_ALIAS.put("RPE", METADATA_KEY.IS_PRE_EMPHASIS_ENABLED.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.IS_SERIAL_COPY_MANAGEMNT_SYSTEM.name(), METADATA_KEY.IS_SERIAL_COPY_MANAGEMNT_SYSTEM.name());
        TRACK_LEVEL_ALIAS.put("FLAG_SERIAL_COPY_MANAGEMNT_SYSTEM", METADATA_KEY.IS_SERIAL_COPY_MANAGEMNT_SYSTEM.name());
        TRACK_LEVEL_ALIAS.put("FLAG_SCMS", METADATA_KEY.IS_SERIAL_COPY_MANAGEMNT_SYSTEM.name());
        TRACK_LEVEL_ALIAS.put("IS_SCMS", METADATA_KEY.IS_SERIAL_COPY_MANAGEMNT_SYSTEM.name());
        TRACK_LEVEL_ALIAS.put("SCMS", METADATA_KEY.IS_SERIAL_COPY_MANAGEMNT_SYSTEM.name());
        */
        
        TRACK_LEVEL_ALIAS.put("YEAR", "YEAR");
        TRACK_LEVEL_ALIAS.put("DATE", "YEAR");
        TRACK_LEVEL_ALIAS.put("TRACKYEAR", "YEAR");
        TRACK_LEVEL_ALIAS.put("TRACK_YEAR", "YEAR");
        TRACK_LEVEL_ALIAS.put("TRACKDATE", "YEAR");
        TRACK_LEVEL_ALIAS.put("TRACK_DATE", "YEAR");
        
        TRACK_LEVEL_ALIAS.put("GENRE", "GENRE");
        TRACK_LEVEL_ALIAS.put("TRACKGENRE", "GENRE");
        TRACK_LEVEL_ALIAS.put("TRACK_GENRE", "GENRE");
        
        TRACK_LEVEL_ALIAS.put("LYRICS", "LYRICS");
        TRACK_LEVEL_ALIAS.put("TRACKLYRICS", "LYRICS");
        TRACK_LEVEL_ALIAS.put("TRACK_LYRICS", "LYRICS");
        
        TRACK_LEVEL_ALIAS.put("COMMENT", "COMMENT");
        TRACK_LEVEL_ALIAS.put("TRACKCOMMENT", "COMMENT");
        TRACK_LEVEL_ALIAS.put("TRACK_COMMENT", "COMMENT");
        
        TRACK_LEVEL_ALIAS.put("DISC_NO", "DISC_NO");
        TRACK_LEVEL_ALIAS.put("DISC", "DISC_NO");
        TRACK_LEVEL_ALIAS.put("DISC#", "DISC_NO");
        TRACK_LEVEL_ALIAS.put("DISCNO", "DISC_NO");
        TRACK_LEVEL_ALIAS.put("DISCNUMBER", "DISC_NO");
        TRACK_LEVEL_ALIAS.put("DISC_NUMBER", "DISC_NO");
        
        TRACK_LEVEL_ALIAS.put("DISC_TOTAL", "DISC_TOTAL");
        TRACK_LEVEL_ALIAS.put("DISCTOTAL", "DISC_TOTAL");
        TRACK_LEVEL_ALIAS.put("TOTALDISC", "DISC_TOTAL");
        TRACK_LEVEL_ALIAS.put("TOTAL_DISC", "DISC_TOTAL");
        TRACK_LEVEL_ALIAS.put("DISCC", "DISC_TOTAL");
        
    }
    
    public static final String getAlbumLevelMetadataAlias(String generic){
        return ALBUM_LEVEL_ALIAS.get(generic);
    }
    public static final String getTrackLevelMetadataALias(String generic){
        return TRACK_LEVEL_ALIAS.get(generic);
    }
}