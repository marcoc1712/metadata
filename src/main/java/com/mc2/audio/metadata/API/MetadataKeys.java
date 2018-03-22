package com.mc2.audio.metadata.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of 'common' metadata derived from Command keys.
 * For a more complete metadata list see: rg.jaudiotagger.tag.FieldKey 
 *
 * @author marcoc1712
 */
public class MetadataKeys {
      
    public static enum METADATA_KEY {
        ACOUSTID_FINGERPRINT,
        ACOUSTID_ID,
        ALBUM,
        ALBUM_ARTIST,
        ALBUM_ARTIST_SORT,
        ALBUM_ARTISTS,
        ALBUM_ARTISTS_SORT,
        ALBUM_SORT,
        AMAZON_ID,
        ARRANGER,
        ARRANGER_SORT,
        ARTIST,
        ARTISTS,
        ARTISTS_SORT,
        ARTIST_SORT,
        BARCODE,
        BPM,
        CATALOG_NO,
        CDTEXTFILE,
        CLASSICAL_CATALOG,
        CLASSICAL_NICKNAME,
        CHOIR,
        CHOIR_SORT,
		CHOIR_MASTER,
		CHOIR_MASTER_SORT,
        COMMENT,
        COMPOSER,
        COMPOSER_SORT,
        CONDUCTOR,
        CONDUCTOR_SORT,
        COPYRIGHT,
        COUNTRY,
        COVER_ART,
        CUSTOM1,
        CUSTOM2,
        CUSTOM3,
        CUSTOM4,
        CUSTOM5,
        DATE,
        DISC_NO,
        DISC_SUBTITLE,
        DISC_TOTAL,
        DJMIXER,
        ENCODER,
        ENGINEER,
        ENSEMBLE,
        ENSEMBLE_SORT,
        FBPM,
        GENRE,
        GROUP,
        GROUPING,
        INSTRUMENT,
        INVOLVED_PERSON,
        IPI,
        ISRC,
        ISWC,
        IS_CLASSICAL,
        IS_SOUNDTRACK,
        IS_COMPILATION,
        ITUNES_GROUPING,
        KEY,
        LABEL,
        LANGUAGE,
        LYRICIST,
        LYRICIST_SORT,
        LYRICS,
        MEDIA,
        MIXER,
        MOOD,
        MOOD_ACOUSTIC,
        MOOD_AGGRESSIVE,
        MOOD_AROUSAL,
        MOOD_DANCEABILITY,
        MOOD_ELECTRONIC,
        MOOD_HAPPY,
        MOOD_INSTRUMENTAL,
        MOOD_PARTY,
        MOOD_RELAXED,
        MOOD_SAD,
        MOOD_VALENCE,
        MOVEMENT,
        MOVEMENT_NO,
        MOVEMENT_TOTAL,
        MUSICBRAINZ_ARTISTID,
        MUSICBRAINZ_DISC_ID,
        MUSICBRAINZ_ORIGINAL_RELEASE_ID,
        MUSICBRAINZ_RELEASEARTISTID,
        MUSICBRAINZ_RELEASEID,
        MUSICBRAINZ_RELEASE_COUNTRY,
        MUSICBRAINZ_RELEASE_GROUP_ID,
        MUSICBRAINZ_RELEASE_STATUS,
        MUSICBRAINZ_RELEASE_TRACK_ID,
        MUSICBRAINZ_RELEASE_TYPE,
        MUSICBRAINZ_TRACK_ID,
        MUSICBRAINZ_WORK,
        MUSICBRAINZ_WORK_ID,
        MUSICBRAINZ_WORK_COMPOSITION,
        MUSICBRAINZ_WORK_COMPOSITION_ID,
        MUSICBRAINZ_WORK_PART_LEVEL1,
        MUSICBRAINZ_WORK_PART_LEVEL1_ID,
        MUSICBRAINZ_WORK_PART_LEVEL1_TYPE,
        MUSICBRAINZ_WORK_PART_LEVEL2,
        MUSICBRAINZ_WORK_PART_LEVEL2_ID,
        MUSICBRAINZ_WORK_PART_LEVEL2_TYPE,
        MUSICBRAINZ_WORK_PART_LEVEL3,
        MUSICBRAINZ_WORK_PART_LEVEL3_ID,
        MUSICBRAINZ_WORK_PART_LEVEL3_TYPE,
        MUSICBRAINZ_WORK_PART_LEVEL4,
        MUSICBRAINZ_WORK_PART_LEVEL4_ID,
        MUSICBRAINZ_WORK_PART_LEVEL4_TYPE,
        MUSICBRAINZ_WORK_PART_LEVEL5,
        MUSICBRAINZ_WORK_PART_LEVEL5_ID,
        MUSICBRAINZ_WORK_PART_LEVEL5_TYPE,
        MUSICBRAINZ_WORK_PART_LEVEL6,
        MUSICBRAINZ_WORK_PART_LEVEL6_ID,
        MUSICBRAINZ_WORK_PART_LEVEL6_TYPE,
        MUSICIP_ID,
        OCCASION,
        OPUS,
        ORCHESTRA,
        ORCHESTRA_SORT,
        ORIGINAL_ALBUM,
        ORIGINAL_ARTIST,
        ORIGINAL_LYRICIST,
        ORIGINAL_YEAR,
        OVERALL_WORK,
        PART,
        PART_NUMBER,
        PART_TYPE,
        PERFORMER,
        PERFORMER_NAME,
        PERFORMER_NAME_SORT,
        PERIOD,
        PRODUCER,
        QUALITY,
        RANKING,
        RATING,
        RECORD_LABEL,
        REMIXER,
        SCRIPT,
        SINGLE_DISC_TRACK_NO,
        SUBTITLE,
        TAGS,
        TEMPO,
        TIMBRE,
        TITLE,
        TITLE_SORT,
        TITLE_MOVEMENT,
        TONALITY,
        TRACK_NO,
        TRACK_TOTAL,
        UPC,
        URL_DISCOGS_ARTIST_SITE,
        URL_DISCOGS_RELEASE_SITE,
        URL_LYRICS_SITE,
        URL_OFFICIAL_ARTIST_SITE,
        URL_OFFICIAL_RELEASE_SITE,
        URL_WIKIPEDIA_ARTIST_SITE,
        URL_WIKIPEDIA_RELEASE_SITE,
        WORK,
        WORK_TYPE,
        YEAR
    };

 /* 
Aliases used to consolidate metadata at album level.
*/
 public static final HashMap<String,String> ALBUM_LEVEL_ALIAS;
    static {
        ALBUM_LEVEL_ALIAS = new HashMap<>();
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.UPC.name(), METADATA_KEY.UPC.name());
        ALBUM_LEVEL_ALIAS.put("UPCCODE", METADATA_KEY.UPC.name());
        ALBUM_LEVEL_ALIAS.put("UPC_CODE", METADATA_KEY.UPC.name());
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.BARCODE.name(), METADATA_KEY.BARCODE.name());
        ALBUM_LEVEL_ALIAS.put("BARCODE", METADATA_KEY.BARCODE.name());
        ALBUM_LEVEL_ALIAS.put("BAR_CODE", METADATA_KEY.BARCODE.name());
        
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
        ALBUM_LEVEL_ALIAS.put("ALBUMCOMPOSER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_COMPOSER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("SONGWRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("SONG_WRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMSONGWRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_SONGWRITER", METADATA_KEY.COMPOSER.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_SONG_WRITER", METADATA_KEY.COMPOSER.name());
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.CONDUCTOR.name(), METADATA_KEY.CONDUCTOR.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMCONDUCTOR", METADATA_KEY.CONDUCTOR.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_CONDUCTOR", METADATA_KEY.CONDUCTOR.name());
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.ALBUM.name(), METADATA_KEY.ALBUM.name());
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.TITLE.name(), METADATA_KEY.ALBUM.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMTITLE", METADATA_KEY.ALBUM.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_TITLE", METADATA_KEY.ALBUM.name());

        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.CDTEXTFILE.name(), METADATA_KEY.CDTEXTFILE.name());
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.DATE.name(), METADATA_KEY.DATE.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMDATE",  METADATA_KEY.DATE.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_DATE",  METADATA_KEY.DATE.name());
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.YEAR.name(), METADATA_KEY.DATE.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMYEAR",  METADATA_KEY.DATE.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_YEAR",  METADATA_KEY.DATE.name());
       
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.GENRE.name(), METADATA_KEY.GENRE.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMGENRE", "GENRE");
        ALBUM_LEVEL_ALIAS.put("ALBUM_GENRE", "GENRE");
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.LABEL.name(), METADATA_KEY.LABEL.name());
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.RECORD_LABEL.name(), METADATA_KEY.LABEL.name());
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.COUNTRY.name(), METADATA_KEY.COUNTRY.name());
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.MUSICBRAINZ_RELEASE_COUNTRY.name(), METADATA_KEY.COUNTRY.name());

        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.LYRICS.name(), METADATA_KEY.LYRICS.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMLYRICS", "LYRICS");
        ALBUM_LEVEL_ALIAS.put("ALBUM_LYRICS", "LYRICS");
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.COMMENT.name(), METADATA_KEY.COMMENT.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMCOMMENT", "COMMENT");
        ALBUM_LEVEL_ALIAS.put("ALBUM_COMMENT", "COMMENT");
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.MEDIA.name(), METADATA_KEY.MEDIA.name());
        ALBUM_LEVEL_ALIAS.put("MEDIA_TYPE", METADATA_KEY.MEDIA.name());
        ALBUM_LEVEL_ALIAS.put("MEDIATYPE", METADATA_KEY.MEDIA.name());
		ALBUM_LEVEL_ALIAS.put("MEDIUM", METADATA_KEY.MEDIA.name());
        ALBUM_LEVEL_ALIAS.put("MEDIUM_TYPE", METADATA_KEY.MEDIA.name());
        ALBUM_LEVEL_ALIAS.put("MEDIUMTYPE", METADATA_KEY.MEDIA.name());
		
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.DISC_NO.name(), METADATA_KEY.DISC_NO.name());
        ALBUM_LEVEL_ALIAS.put("DISC", "DISC_NO");
        ALBUM_LEVEL_ALIAS.put("DISC#", "DISC_NO");
		ALBUM_LEVEL_ALIAS.put("DISC_#", "DISC_NO");
        ALBUM_LEVEL_ALIAS.put("DISCNO", "DISC_NO");
        ALBUM_LEVEL_ALIAS.put("DISCNUMBER", "DISC_NO");
        ALBUM_LEVEL_ALIAS.put("DISC_NUMBER", "DISC_NO");
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.DISC_TOTAL.name(), METADATA_KEY.DISC_TOTAL.name());
        ALBUM_LEVEL_ALIAS.put("DISCTOTAL", "DISC_TOTAL");
        ALBUM_LEVEL_ALIAS.put("TOTALDISC", "DISC_TOTAL");
        ALBUM_LEVEL_ALIAS.put("TOTAL_DISC", "DISC_TOTAL");
        ALBUM_LEVEL_ALIAS.put("DISCC", "DISC_TOTAL");

    }
	/* 
	Aliases used to consolidate metadata at album level.
	*/
    public static final HashMap<String,String> TRACK_LEVEL_ALIAS;
    static {
        TRACK_LEVEL_ALIAS = new HashMap<>();
        
		TRACK_LEVEL_ALIAS.put( METADATA_KEY.TRACK_NO.name(), METADATA_KEY.TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("TRACK", METADATA_KEY.TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("TRACK#", METADATA_KEY.TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("TRACK_#", METADATA_KEY.TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("TRACKNO", METADATA_KEY.TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("TRACKNUMBER", METADATA_KEY.TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("TRACK_NUMBER", METADATA_KEY.TRACK_NO.name());
		
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.ISRC.name(), METADATA_KEY.ISRC.name());
        TRACK_LEVEL_ALIAS.put("ISRCCODE", METADATA_KEY.ISRC.name());
        TRACK_LEVEL_ALIAS.put("ISRC_CODE", METADATA_KEY.ISRC.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.ARTIST.name(), METADATA_KEY.ARTIST.name());
		TRACK_LEVEL_ALIAS.put("TRACKARTIST", METADATA_KEY.ARTIST.name());
        TRACK_LEVEL_ALIAS.put("TRACK_ARTIST", METADATA_KEY.ARTIST.name());
        TRACK_LEVEL_ALIAS.put("PERFORMER", METADATA_KEY.ARTIST.name());
        TRACK_LEVEL_ALIAS.put("TRACKPERFORMER", METADATA_KEY.ARTIST.name());
        TRACK_LEVEL_ALIAS.put("TRACK_PERFORMER", METADATA_KEY.ARTIST.name());

        TRACK_LEVEL_ALIAS.put(METADATA_KEY.COMPOSER.name(), METADATA_KEY.COMPOSER.name());
        TRACK_LEVEL_ALIAS.put("TRACKCOMPOSER", METADATA_KEY.COMPOSER.name());
        TRACK_LEVEL_ALIAS.put("TRACK_COMPOSER", METADATA_KEY.COMPOSER.name());
        TRACK_LEVEL_ALIAS.put("SONGWRITER", METADATA_KEY.COMPOSER.name());
        TRACK_LEVEL_ALIAS.put("SONG_WRITER", METADATA_KEY.COMPOSER.name());
        TRACK_LEVEL_ALIAS.put("TRACKSONGWRITER", METADATA_KEY.COMPOSER.name());
        TRACK_LEVEL_ALIAS.put("TRACK_SONGWRITER", METADATA_KEY.COMPOSER.name());
        TRACK_LEVEL_ALIAS.put("TRACK_SONG_WRITER", METADATA_KEY.COMPOSER.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.CONDUCTOR.name(), METADATA_KEY.CONDUCTOR.name());
        TRACK_LEVEL_ALIAS.put("TRACKCONDUCTOR", METADATA_KEY.CONDUCTOR.name());
        TRACK_LEVEL_ALIAS.put("TRACK_CONDUCTOR", METADATA_KEY.CONDUCTOR.name());
        
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
        TRACK_LEVEL_ALIAS.put( METADATA_KEY.DATE.name(), METADATA_KEY.DATE.name());
        TRACK_LEVEL_ALIAS.put("YEAR", METADATA_KEY.DATE.name());
        TRACK_LEVEL_ALIAS.put("TRACKYEAR", METADATA_KEY.DATE.name());
        TRACK_LEVEL_ALIAS.put("TRACK_YEAR", METADATA_KEY.DATE.name());
        TRACK_LEVEL_ALIAS.put("TRACKDATE", METADATA_KEY.DATE.name());
        TRACK_LEVEL_ALIAS.put("TRACK_DATE", METADATA_KEY.DATE.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.GENRE.name(), METADATA_KEY.GENRE.name());
        TRACK_LEVEL_ALIAS.put("TRACKGENRE", METADATA_KEY.GENRE.name());
        TRACK_LEVEL_ALIAS.put("TRACK_GENRE", METADATA_KEY.GENRE.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.LYRICS.name(), METADATA_KEY.LYRICS.name());
        TRACK_LEVEL_ALIAS.put("TRACKLYRICS", METADATA_KEY.LYRICS.name());
        TRACK_LEVEL_ALIAS.put("TRACK_LYRICS", METADATA_KEY.LYRICS.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.COMMENT.name(), METADATA_KEY.COMMENT.name());
        TRACK_LEVEL_ALIAS.put("TRACKCOMMENT", METADATA_KEY.COMMENT.name());
        TRACK_LEVEL_ALIAS.put("TRACK_COMMENT", METADATA_KEY.COMMENT.name());
        
        TRACK_LEVEL_ALIAS.put( METADATA_KEY.DISC_NO.name(), METADATA_KEY.DISC_NO.name());
        TRACK_LEVEL_ALIAS.put("DISC", METADATA_KEY.DISC_NO.name());
        TRACK_LEVEL_ALIAS.put("DISC#", METADATA_KEY.DISC_NO.name());
		TRACK_LEVEL_ALIAS.put("DISC_#", METADATA_KEY.DISC_NO.name());
        TRACK_LEVEL_ALIAS.put("DISCNO", METADATA_KEY.DISC_NO.name());
        TRACK_LEVEL_ALIAS.put("DISCNUMBER", METADATA_KEY.DISC_NO.name());
        TRACK_LEVEL_ALIAS.put("DISC_NUMBER", METADATA_KEY.DISC_NO.name());
        
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.DISC_TOTAL.name(), METADATA_KEY.DISC_TOTAL.name());
        TRACK_LEVEL_ALIAS.put("DISCTOTAL", METADATA_KEY.DISC_TOTAL.name());
        TRACK_LEVEL_ALIAS.put("TOTALDISC", METADATA_KEY.DISC_TOTAL.name());
        TRACK_LEVEL_ALIAS.put("TOTAL_DISC", METADATA_KEY.DISC_TOTAL.name());
        TRACK_LEVEL_ALIAS.put("DISCC", METADATA_KEY.DISC_TOTAL.name());
		
		TRACK_LEVEL_ALIAS.put(METADATA_KEY.DISC_SUBTITLE.name(), METADATA_KEY.DISC_SUBTITLE.name());
		TRACK_LEVEL_ALIAS.put("DISCSUBTITLE", METADATA_KEY.DISC_SUBTITLE.name());
		TRACK_LEVEL_ALIAS.put("DISC_TITLE", METADATA_KEY.DISC_SUBTITLE.name());
		TRACK_LEVEL_ALIAS.put("DISCTITLE", METADATA_KEY.DISC_SUBTITLE.name());
		
		ALBUM_LEVEL_ALIAS.put(METADATA_KEY.MEDIA.name(), METADATA_KEY.MEDIA.name());
        ALBUM_LEVEL_ALIAS.put("MEDIA_TYPE", METADATA_KEY.MEDIA.name());
        ALBUM_LEVEL_ALIAS.put("MEDIATYPE", METADATA_KEY.MEDIA.name());
		ALBUM_LEVEL_ALIAS.put("MEDIUM", METADATA_KEY.MEDIA.name());
        ALBUM_LEVEL_ALIAS.put("MEDIUM_TYPE", METADATA_KEY.MEDIA.name());
        ALBUM_LEVEL_ALIAS.put("MEDIUMTYPE", METADATA_KEY.MEDIA.name());
    }
    
    public static final String getAlbumLevelMetadataAlias(String generic){
        return ALBUM_LEVEL_ALIAS.get(generic);
    }
    public static final String getTrackLevelMetadataAlias(String generic){
        return TRACK_LEVEL_ALIAS.get(generic);
    }
    
    public static final ArrayList<String>  getAlbumLevelMetadataAliases(String metadataKey){
        ArrayList<String> out= new ArrayList<>();
        
        for (Map.Entry<String, String> entry : ALBUM_LEVEL_ALIAS.entrySet()) {
            if (entry.getValue().equals(metadataKey) && 
                !entry.getKey().equals(metadataKey)) {
              out.add(entry.getKey());
            }
        }
        return out;
    }
    public static final ArrayList<String>  getTrackLevelMetadataAliases(String metadataKey){
        ArrayList<String> out= new ArrayList<>();
        
        for (Map.Entry<String, String> entry : TRACK_LEVEL_ALIAS.entrySet()) {
            if (entry.getValue().equals(metadataKey) && 
                !entry.getKey().equals(metadataKey)) {
                
              out.add(entry.getKey());
            }
        }
        return out;
    }
}

