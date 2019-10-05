package com.mc2.audio.metadata.API;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enumeration of 'common' metadata derived from Command keys.
 * For a more complete metadata list see: rg.jaudiotagger.tag.FieldKey 
 *
 * @author marcoc1712
 */
public class MetadataKey {
	
	public static enum METADATA_LEVEL {
		ALBUM,
		TRACK,
		BOTH
	};
	
    public static enum METADATA_CATEGORY {
		
		
		RESERVED,
		NOT_TO_SHOW,
		ALBUM_INFO,
		COVERART,
			
		DESCRIPTION,
		COMMENT,
        AWARD,
		COLLECTION,
		GOODIES,
		URL,
		MUSIC_DESCRIPTOR,
		CREDITS,
		OTHER_INVOLVED_PERSON,
		RECORDING_DESCRIPTOR,
		MEDIA_DESCRIPTOR,
		WORK_DESCRIPTOR,
		MISCELLANEA,
		RATING,
	};
    public static enum METADATA_KEY {
        
		ALBUM(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.ALBUM_INFO),
        ALBUM_ARTIST(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.ALBUM_INFO),
        ARTIST(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        ARTISTS(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
		BIT_DEPTH(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        CATALOG_NO(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.ALBUM_INFO),
        CHANNEL_COUNT(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.ALBUM_INFO),
        COPYRIGHT(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        COVER_ART(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
		COUNTRY(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.ALBUM_INFO),
        DATE(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.ALBUM_INFO),
        DESCRIPTION(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.ALBUM_INFO),
        DISC_NO(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        DISC_SUBTITLE(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        DISC_TOTAL(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
		DURATION(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
		GOODIES(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
		HIRES(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
		ID(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        ISRC(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        LABEL(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.ALBUM_INFO),
		MEDIA(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        MUSICBRAINZ_ARTISTID(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        MUSICBRAINZ_DISC_ID(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        MUSICBRAINZ_RELEASEARTISTID(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_RELEASEID(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        MUSICBRAINZ_RELEASE_COUNTRY(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_RELEASE_GROUP_ID(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_RELEASE_TRACK_ID(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_TRACK_ID(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_ID(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.NOT_TO_SHOW),
        PARENTAL_WARNING(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
		PERFORMER(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
		SINGLE_DISC_TRACK_NO(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        SAMPLING_RATE(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        TITLE(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        TECHNICAL_SPECIFICATIONS(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        TRACK_NO(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        TRACK_TOTAL(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.RESERVED),
        YEAR(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.ALBUM_INFO),
		CREATED_AT(METADATA_CATEGORY.RESERVED, METADATA_CATEGORY.ALBUM_INFO),
		
		ACOUSTID_FINGERPRINT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        ACOUSTID_ID(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        ALBUM_ARTIST_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        ALBUM_ARTISTS(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        ALBUM_ARTISTS_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
		ARRANGER_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        ARTISTS_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        ARTIST_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
		ALBUM_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        CDTEXTFILE(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
		CHOIR_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
		CHOIR_MASTER_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
		COMPOSER_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
		CONDUCTOR_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
		ENSEMBLE_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        ITUNES_GROUPING(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
		LYRICIST_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_ORIGINAL_RELEASE_ID(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_RELEASE_STATUS(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_RELEASE_TYPE(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_COMPOSITION(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_COMPOSITION_ID(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL1(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL1_ID(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL1_TYPE(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL2(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL2_ID(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL2_TYPE(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL3(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL3_ID(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL3_TYPE(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL4(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL4_ID(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL4_TYPE(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL5(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL5_ID(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL5_TYPE(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL6(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL6_ID(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICBRAINZ_WORK_PART_LEVEL6_TYPE(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        MUSICIP_ID(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        ORCHESTRA_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        PERFORMER_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        PERFORMER_NAME(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        PERFORMER_NAME_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        QUALITY(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),
        TITLE_SORT(METADATA_CATEGORY.NOT_TO_SHOW, METADATA_CATEGORY.NOT_TO_SHOW),

		AMAZON_ID(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.ALBUM_INFO),
        AREA(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
		ARRANGER(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
        BAND(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
		BARCODE(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.ALBUM_INFO),
        BPM(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        CLASSICAL_CATALOG(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        CLASSICAL_NICKNAME(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        CHOIR(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
		CHOIR_MASTER(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
		COLLECTION(METADATA_CATEGORY.COLLECTION, METADATA_CATEGORY.NOT_TO_SHOW),
        COMMENT(METADATA_CATEGORY.COMMENT, METADATA_CATEGORY.COMMENT),
        COMPOSER(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
        CONDUCTOR(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
		CREDITS(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
		CUSTOM1(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        CUSTOM2(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        CUSTOM3(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        CUSTOM4(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        CUSTOM5(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        DJMIXER(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
        ENCODER(METADATA_CATEGORY.MEDIA_DESCRIPTOR, METADATA_CATEGORY.MEDIA_DESCRIPTOR),
        ENGINEER(METADATA_CATEGORY.OTHER_INVOLVED_PERSON, METADATA_CATEGORY.OTHER_INVOLVED_PERSON),
        ENSEMBLE(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
        FBPM(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        GENRE(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        GROUP(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
        GROUPING(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        INSTRUMENT(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        INVOLVED_PERSON(METADATA_CATEGORY.OTHER_INVOLVED_PERSON, METADATA_CATEGORY.OTHER_INVOLVED_PERSON),
        IPI(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        ISWC(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        IS_CLASSICAL(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        IS_SOUNDTRACK(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        IS_COMPILATION(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        KEY(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),       
        LANGUAGE(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.NOT_TO_SHOW),
        LYRICIST(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
        LYRICS(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        MIXER(METADATA_CATEGORY.OTHER_INVOLVED_PERSON, METADATA_CATEGORY.OTHER_INVOLVED_PERSON),
        MOOD(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOOD_ACOUSTIC(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOOD_AGGRESSIVE(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOOD_AROUSAL(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOOD_DANCEABILITY(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOOD_ELECTRONIC(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOOD_HAPPY(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOOD_INSTRUMENTAL(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOOD_PARTY(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOOD_RELAXED(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOOD_SAD(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOOD_VALENCE(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        MOVEMENT(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        MOVEMENT_NO(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        MOVEMENT_TOTAL(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),       
        OCCASION(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        OPUS(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        ORCHESTRA(METADATA_CATEGORY.CREDITS, METADATA_CATEGORY.CREDITS),
        ORIGINAL_ALBUM(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        ORIGINAL_ARTIST(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
		ORIGINAL_DATE(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        ORIGINAL_LYRICIST(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        ORIGINAL_YEAR(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        OVERALL_WORK(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        PART(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        PART_NUMBER(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        PART_TYPE(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        PERIOD(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        PRODUCER(METADATA_CATEGORY.OTHER_INVOLVED_PERSON, METADATA_CATEGORY.OTHER_INVOLVED_PERSON),
        RANKING(METADATA_CATEGORY.RATING, METADATA_CATEGORY.RATING),
        RATING(METADATA_CATEGORY.RATING, METADATA_CATEGORY.RATING),
        RECORD_LABEL(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        RECORDING_INFORMATION(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        REMIXER(METADATA_CATEGORY.OTHER_INVOLVED_PERSON, METADATA_CATEGORY.OTHER_INVOLVED_PERSON),
        SCRIPT(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.NOT_TO_SHOW),
        SERIE(METADATA_CATEGORY.COLLECTION, METADATA_CATEGORY.NOT_TO_SHOW),
		SUBTITLE(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        TAGS(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
        TEMPO(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        TIMBRE(METADATA_CATEGORY.MUSIC_DESCRIPTOR, METADATA_CATEGORY.MUSIC_DESCRIPTOR),
        TITLE_MOVEMENT(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        TONALITY(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        UPC(METADATA_CATEGORY.MISCELLANEA, METADATA_CATEGORY.MISCELLANEA),
		URL_DISCOGS_ARTIST_SITE(METADATA_CATEGORY.URL, METADATA_CATEGORY.URL),
        URL_DISCOGS_RELEASE_SITE(METADATA_CATEGORY.URL, METADATA_CATEGORY.NOT_TO_SHOW),
        URL_LYRICS_SITE(METADATA_CATEGORY.URL, METADATA_CATEGORY.URL),
        URL_OFFICIAL_ARTIST_SITE(METADATA_CATEGORY.URL, METADATA_CATEGORY.URL),
        URL_OFFICIAL_RELEASE_SITE(METADATA_CATEGORY.URL, METADATA_CATEGORY.NOT_TO_SHOW),
        URL_WIKIPEDIA_ARTIST_SITE(METADATA_CATEGORY.URL, METADATA_CATEGORY.URL),
        URL_WIKIPEDIA_RELEASE_SITE(METADATA_CATEGORY.URL, METADATA_CATEGORY.NOT_TO_SHOW),
        VERSION(METADATA_CATEGORY.RECORDING_DESCRIPTOR, METADATA_CATEGORY.RECORDING_DESCRIPTOR),
        WEBSITE(METADATA_CATEGORY.URL, METADATA_CATEGORY.NOT_TO_SHOW),
		WORK(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
        WORK_TYPE(METADATA_CATEGORY.WORK_DESCRIPTOR, METADATA_CATEGORY.WORK_DESCRIPTOR),
		;
		
		private final METADATA_CATEGORY albumLevelCategory;
		private final METADATA_CATEGORY trackLevelCategory;

		METADATA_KEY (METADATA_CATEGORY albumLevelCategory, METADATA_CATEGORY trackLevelCategory) {
			this.albumLevelCategory = albumLevelCategory;
			this.trackLevelCategory = trackLevelCategory;
		}
		public METADATA_CATEGORY getAlbumLevelCategory() { return albumLevelCategory; }
		public METADATA_CATEGORY getTrackLevelCategory () { return trackLevelCategory; }
    };

	public static final List<METADATA_KEY> getMetadataKeysForCategory(METADATA_LEVEL level, METADATA_CATEGORY category){
		
		List<METADATA_KEY> out = new ArrayList<>();
		
		for (METADATA_KEY mk : METADATA_KEY.values()){
			
			if  (	(mk.getAlbumLevelCategory().equals(category) 
					&& (level.equals(METADATA_LEVEL.BOTH) 
						|| level.equals(METADATA_LEVEL.ALBUM))) 
				|| (mk.getTrackLevelCategory().equals(category) 
					&& (level.equals(METADATA_LEVEL.BOTH)
						|| level.equals(METADATA_LEVEL.TRACK)))
				){		
				out.add(mk);
			}
		}
		return out;
	}
	/* 
	Aliases used to consolidate metadata both at album and track level.
	*/
	public static final HashMap<String,String> BOTH_LEVEL_ALIAS;
    static {
		BOTH_LEVEL_ALIAS = new HashMap<>();
		
				
        BOTH_LEVEL_ALIAS.put(METADATA_KEY.DISC_TOTAL.name(), METADATA_KEY.DISC_TOTAL.name());
        BOTH_LEVEL_ALIAS.put("DISCTOTAL", METADATA_KEY.DISC_TOTAL.name());
        BOTH_LEVEL_ALIAS.put("TOTALDISC", METADATA_KEY.DISC_TOTAL.name());
        BOTH_LEVEL_ALIAS.put("TOTAL_DISC", METADATA_KEY.DISC_TOTAL.name());
        BOTH_LEVEL_ALIAS.put("DISCC", METADATA_KEY.DISC_TOTAL.name());
		BOTH_LEVEL_ALIAS.put("DISCS_TOTAL", METADATA_KEY.DISC_TOTAL.name());
		BOTH_LEVEL_ALIAS.put("DISCSTOTAL", METADATA_KEY.DISC_TOTAL.name());
		BOTH_LEVEL_ALIAS.put("TOTALDISCS", METADATA_KEY.DISC_TOTAL.name());
        BOTH_LEVEL_ALIAS.put("TOTAL_DISCS", METADATA_KEY.DISC_TOTAL.name());
		
		BOTH_LEVEL_ALIAS.put(METADATA_KEY.TRACK_TOTAL.name(),METADATA_KEY.TRACK_TOTAL.name());
		BOTH_LEVEL_ALIAS.put("TRACKTOTAL",METADATA_KEY.TRACK_TOTAL.name());
		BOTH_LEVEL_ALIAS.put("TOTAL_TRACK",METADATA_KEY.TRACK_TOTAL.name());
		BOTH_LEVEL_ALIAS.put("TOTALTRACK",METADATA_KEY.TRACK_TOTAL.name());
		BOTH_LEVEL_ALIAS.put("TOTAL_TRACKS",METADATA_KEY.TRACK_TOTAL.name());
		BOTH_LEVEL_ALIAS.put("TOTALTRACKS",METADATA_KEY.TRACK_TOTAL.name());
		
		BOTH_LEVEL_ALIAS.put(METADATA_KEY.DISC_NO.name(), METADATA_KEY.DISC_NO.name());
        BOTH_LEVEL_ALIAS.put("DISC", METADATA_KEY.DISC_NO.name());
        BOTH_LEVEL_ALIAS.put("DISC#", METADATA_KEY.DISC_NO.name());
		BOTH_LEVEL_ALIAS.put("DISC_#", METADATA_KEY.DISC_NO.name());
        BOTH_LEVEL_ALIAS.put("DISCNO", METADATA_KEY.DISC_NO.name());
		BOTH_LEVEL_ALIAS.put("DISC_NO", METADATA_KEY.DISC_NO.name());
        BOTH_LEVEL_ALIAS.put("DISCNUMBER", METADATA_KEY.DISC_NO.name());
        BOTH_LEVEL_ALIAS.put("DISC_NUMBER", METADATA_KEY.DISC_NO.name());
        
		BOTH_LEVEL_ALIAS.put(METADATA_KEY.DISC_SUBTITLE.name(), METADATA_KEY.DISC_SUBTITLE.name());
		BOTH_LEVEL_ALIAS.put("DISCSUBTITLE", METADATA_KEY.DISC_SUBTITLE.name());
		BOTH_LEVEL_ALIAS.put("DISC_TITLE", METADATA_KEY.DISC_SUBTITLE.name());
		BOTH_LEVEL_ALIAS.put("DISCTITLE", METADATA_KEY.DISC_SUBTITLE.name());
		
		BOTH_LEVEL_ALIAS.put(METADATA_KEY.MEDIA.name(), METADATA_KEY.MEDIA.name());
        BOTH_LEVEL_ALIAS.put("MEDIA_TYPE", METADATA_KEY.MEDIA.name());
        BOTH_LEVEL_ALIAS.put("MEDIATYPE", METADATA_KEY.MEDIA.name());
		BOTH_LEVEL_ALIAS.put("MEDIA_TYPE", METADATA_KEY.MEDIA.name());
		BOTH_LEVEL_ALIAS.put("MEDIUM", METADATA_KEY.MEDIA.name());
        BOTH_LEVEL_ALIAS.put("MEDIUM_TYPE", METADATA_KEY.MEDIA.name());
        BOTH_LEVEL_ALIAS.put("MEDIUMTYPE", METADATA_KEY.MEDIA.name());
		
		BOTH_LEVEL_ALIAS.put(METADATA_KEY.UPC.name(), METADATA_KEY.UPC.name());
        BOTH_LEVEL_ALIAS.put("UPCCODE", METADATA_KEY.UPC.name());
        BOTH_LEVEL_ALIAS.put("UPC_CODE", METADATA_KEY.UPC.name());
        
        BOTH_LEVEL_ALIAS.put(METADATA_KEY.BARCODE.name(), METADATA_KEY.BARCODE.name());
        BOTH_LEVEL_ALIAS.put("BARCODE", METADATA_KEY.BARCODE.name());
        BOTH_LEVEL_ALIAS.put("BAR_CODE", METADATA_KEY.BARCODE.name());

		BOTH_LEVEL_ALIAS.put(METADATA_KEY.LABEL.name(), METADATA_KEY.LABEL.name());
        BOTH_LEVEL_ALIAS.put(METADATA_KEY.RECORD_LABEL.name(), METADATA_KEY.LABEL.name());

        BOTH_LEVEL_ALIAS.put(METADATA_KEY.CATALOG_NO.name(), METADATA_KEY.CATALOG_NO.name());
        BOTH_LEVEL_ALIAS.put("CATALOG", METADATA_KEY.CATALOG_NO.name());
        BOTH_LEVEL_ALIAS.put("CATALOGUE", METADATA_KEY.CATALOG_NO.name());

		BOTH_LEVEL_ALIAS.put(METADATA_KEY.ORIGINAL_DATE.name(), METADATA_KEY.ORIGINAL_DATE.name());
		BOTH_LEVEL_ALIAS.put("ORIGINALDATE", METADATA_KEY.ORIGINAL_DATE.name());
        BOTH_LEVEL_ALIAS.put(METADATA_KEY.ORIGINAL_YEAR.name(), METADATA_KEY.ORIGINAL_DATE.name());
		BOTH_LEVEL_ALIAS.put("ORIGINALYEAR", METADATA_KEY.ORIGINAL_DATE.name());
	
		BOTH_LEVEL_ALIAS.put(METADATA_KEY.MUSICBRAINZ_RELEASE_TYPE.name(),METADATA_KEY.MUSICBRAINZ_RELEASE_TYPE.name());
		BOTH_LEVEL_ALIAS.put("MB_RELEASE_TYPE",METADATA_KEY.MUSICBRAINZ_RELEASE_TYPE.name());
		BOTH_LEVEL_ALIAS.put("MB_RELEASETYPE",METADATA_KEY.MUSICBRAINZ_RELEASE_TYPE.name());
		BOTH_LEVEL_ALIAS.put("MBRELEASE_TYPE",METADATA_KEY.MUSICBRAINZ_RELEASE_TYPE.name());
		BOTH_LEVEL_ALIAS.put("MBRELEASETYPE",METADATA_KEY.MUSICBRAINZ_RELEASE_TYPE.name());
		BOTH_LEVEL_ALIAS.put("RELEASE_TYPE",METADATA_KEY.MUSICBRAINZ_RELEASE_TYPE.name());
		BOTH_LEVEL_ALIAS.put("RELEASETYPE",METADATA_KEY.MUSICBRAINZ_RELEASE_TYPE.name());
		
		BOTH_LEVEL_ALIAS.put(METADATA_KEY.MUSICBRAINZ_RELEASE_STATUS.name(),METADATA_KEY.MUSICBRAINZ_RELEASE_STATUS.name());
		BOTH_LEVEL_ALIAS.put("MB_RELEASE_STATUS",METADATA_KEY.MUSICBRAINZ_RELEASE_STATUS.name());
		BOTH_LEVEL_ALIAS.put("MB_RELEASESTATUS",METADATA_KEY.MUSICBRAINZ_RELEASE_STATUS.name());
		BOTH_LEVEL_ALIAS.put("MBRELEASE_STATUS",METADATA_KEY.MUSICBRAINZ_RELEASE_STATUS.name());
		BOTH_LEVEL_ALIAS.put("MBRELEASESTATUS",METADATA_KEY.MUSICBRAINZ_RELEASE_STATUS.name());
		BOTH_LEVEL_ALIAS.put("RELEASE_STATUS",METADATA_KEY.MUSICBRAINZ_RELEASE_STATUS.name());
		BOTH_LEVEL_ALIAS.put("RELEASESTATUS",METADATA_KEY.MUSICBRAINZ_RELEASE_STATUS.name());
		
		BOTH_LEVEL_ALIAS.put(METADATA_KEY.ENCODER.name(),METADATA_KEY.ENCODER.name());
		BOTH_LEVEL_ALIAS.put("VENDOR",METADATA_KEY.ENCODER.name());
		
		BOTH_LEVEL_ALIAS.put(METADATA_KEY.COLLECTION.name(), METADATA_KEY.COLLECTION.name());
		BOTH_LEVEL_ALIAS.put(METADATA_KEY.SERIE.name(), METADATA_KEY.COLLECTION.name());
		
		BOTH_LEVEL_ALIAS.put(METADATA_KEY.HIRES.name(), METADATA_KEY.HIRES.name());
		BOTH_LEVEL_ALIAS.put("ISHIRES", METADATA_KEY.HIRES.name());
		BOTH_LEVEL_ALIAS.put("IS_HIRES", METADATA_KEY.HIRES.name());
		BOTH_LEVEL_ALIAS.put("HIREZ", METADATA_KEY.HIRES.name());
		BOTH_LEVEL_ALIAS.put("ISHIREZ", METADATA_KEY.HIRES.name());
		BOTH_LEVEL_ALIAS.put("IS_HIREZ", METADATA_KEY.HIRES.name());
    }
	/* 
   Aliases used to consolidate metadata at album level.
   */
	public static final HashMap<String,String> ALBUM_LEVEL_ALIAS;
    static {
        ALBUM_LEVEL_ALIAS = new HashMap<>();
		ALBUM_LEVEL_ALIAS.putAll(BOTH_LEVEL_ALIAS);

        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.ALBUM.name(), METADATA_KEY.ALBUM.name());
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.TITLE.name(), METADATA_KEY.ALBUM.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMTITLE", METADATA_KEY.ALBUM.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_TITLE", METADATA_KEY.ALBUM.name());

        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.ALBUM_ARTIST.name(), METADATA_KEY.ALBUM_ARTIST.name());
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.ARTIST.name(), METADATA_KEY.ALBUM_ARTIST.name());
		ALBUM_LEVEL_ALIAS.put(METADATA_KEY.ARTISTS.name(), METADATA_KEY.ALBUM_ARTISTS.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMARTIST", METADATA_KEY.ALBUM_ARTIST.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_ARTIST", METADATA_KEY.ALBUM_ARTIST.name());
        ALBUM_LEVEL_ALIAS.put("PERFORMER", METADATA_KEY.ALBUM_ARTIST.name());
		ALBUM_LEVEL_ALIAS.put("PERFORMER_NAME", METADATA_KEY.ALBUM_ARTIST.name());
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
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.CDTEXTFILE.name(), METADATA_KEY.CDTEXTFILE.name());
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.DATE.name(), METADATA_KEY.DATE.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMDATE",  METADATA_KEY.DATE.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_DATE",  METADATA_KEY.DATE.name());
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.YEAR.name(), METADATA_KEY.DATE.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMYEAR",  METADATA_KEY.DATE.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_YEAR",  METADATA_KEY.DATE.name());
       
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.GENRE.name(), METADATA_KEY.GENRE.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMGENRE", METADATA_KEY.GENRE.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_GENRE", METADATA_KEY.GENRE.name());
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.LABEL.name(), METADATA_KEY.LABEL.name());
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.RECORD_LABEL.name(), METADATA_KEY.LABEL.name());
		        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.COUNTRY.name(), METADATA_KEY.COUNTRY.name());
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.MUSICBRAINZ_RELEASE_COUNTRY.name(), METADATA_KEY.COUNTRY.name());
		ALBUM_LEVEL_ALIAS.put("RELEASECOUNTRY", METADATA_KEY.COUNTRY.name());

        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.LYRICS.name(), METADATA_KEY.LYRICS.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMLYRICS", METADATA_KEY.LYRICS.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_LYRICS", METADATA_KEY.LYRICS.name());
        
        ALBUM_LEVEL_ALIAS.put(METADATA_KEY.COMMENT.name(), METADATA_KEY.COMMENT.name());
        ALBUM_LEVEL_ALIAS.put("ALBUMCOMMENT", METADATA_KEY.COMMENT.name());
        ALBUM_LEVEL_ALIAS.put("ALBUM_COMMENT", METADATA_KEY.COMMENT.name());
		
    };
    
	/* 
	Aliases used to consolidate metadata at track level.
	*/
    public static final HashMap<String,String> TRACK_LEVEL_ALIAS;
    static {
        TRACK_LEVEL_ALIAS = new HashMap<>();
        TRACK_LEVEL_ALIAS.putAll(BOTH_LEVEL_ALIAS);
		
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.TITLE.name(), METADATA_KEY.TITLE.name());
        TRACK_LEVEL_ALIAS.put("TRACKTITLE", METADATA_KEY.TITLE.name());
        TRACK_LEVEL_ALIAS.put("TRACK_TITLE", METADATA_KEY.TITLE.name());
		
		TRACK_LEVEL_ALIAS.put(METADATA_KEY.ARTIST.name(), METADATA_KEY.ARTIST.name());
		ALBUM_LEVEL_ALIAS.put(METADATA_KEY.ARTISTS.name(), METADATA_KEY.ARTIST.name());
		TRACK_LEVEL_ALIAS.put("TRACKARTIST", METADATA_KEY.ARTIST.name());
        TRACK_LEVEL_ALIAS.put("TRACK_ARTIST", METADATA_KEY.ARTIST.name());
        TRACK_LEVEL_ALIAS.put("PERFORMER", METADATA_KEY.ARTIST.name());
		ALBUM_LEVEL_ALIAS.put("PERFORMER_NAME", METADATA_KEY.ARTIST.name());
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
		
		TRACK_LEVEL_ALIAS.put( METADATA_KEY.SINGLE_DISC_TRACK_NO.name(), METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("SINGLE_DISC_TRACK", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("SINGLE_DISC_TRACK#", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("SINGLE_DISC_TRACK_#", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("SINGLE_DISC_TRACKNO", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("SINGLE_DISC_TRACKNUMBER", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("SINGLE_DISC_TRACK_NUMBER", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("SINGLEDISC_TRACK", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("SINGLEDISC_TRACK#", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("SINGLEDISC_TRACK_#", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("SINGLEDISC_TRACKNO", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("SINGLEDISC_TRACKNUMBER", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("SINGLEDISC_TRACK_NUMBER", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("SINGLEDISCTRACK", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("SINGLEDISCTRACK#", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("SINGLEDISCTRACK_#", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("SINGLEDISCTRACKNO", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("SINGLEDISCTRACKNUMBER", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("SINGLEDISCTRACK_NUMBER", METADATA_KEY.SINGLE_DISC_TRACK_NO.name());
		
		TRACK_LEVEL_ALIAS.put( METADATA_KEY.TRACK_NO.name(), METADATA_KEY.TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("TRACK", METADATA_KEY.TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("TRACK#", METADATA_KEY.TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("TRACK_#", METADATA_KEY.TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("TRACKNO", METADATA_KEY.TRACK_NO.name());
		TRACK_LEVEL_ALIAS.put("TRACK_NO", METADATA_KEY.TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("TRACKNUMBER", METADATA_KEY.TRACK_NO.name());
        TRACK_LEVEL_ALIAS.put("TRACK_NUMBER", METADATA_KEY.TRACK_NO.name());
				
        TRACK_LEVEL_ALIAS.put(METADATA_KEY.ISRC.name(), METADATA_KEY.ISRC.name());
        TRACK_LEVEL_ALIAS.put("ISRCCODE", METADATA_KEY.ISRC.name());
        TRACK_LEVEL_ALIAS.put("ISRC_CODE", METADATA_KEY.ISRC.name());
		
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
		}
	
	public static METADATA_KEY getByName(String name){
	
		for (METADATA_KEY key : METADATA_KEY.values()){
			
			if (key.name().equals(name)){return key;}
		}
		return null;
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

