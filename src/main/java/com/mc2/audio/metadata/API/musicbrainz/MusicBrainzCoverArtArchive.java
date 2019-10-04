package com.mc2.audio.metadata.API.musicbrainz;

import fm.last.musicbrainz.coverart.CoverArtType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.mc2.audio.metadata.source.coverart.MusicBrainzCoverArtImpl;
import com.mc2.musicbrainz.coverart.Image;
import com.mc2.musicbrainz.coverart.ImageGetter;

/*

Cover Art Archive in Music Brainz.
*/

public class MusicBrainzCoverArtArchive  {
    
    private List<MusicBrainzCoverArt> coverArtList = new ArrayList<>();
    private String mbId="";

    private ImageGetter getter = ImageGetter.getInstance();
    private MusicBrainzCoverArt front;
        
    public MusicBrainzCoverArtArchive (String mbReleaseId, String mbReleaseGroupId){
        
        this.mbId=mbReleaseId;
        
        init(mbReleaseId);

        if (front== null){
            
            init(mbReleaseGroupId);
        }
    }

    public MusicBrainzCoverArtArchive (String mbId){
        
        this.mbId = mbId;
        
        init(mbId);
    }
    
     /**
     * @return the coverArtList
     */

    public List<MusicBrainzCoverArt> getCoverArtList() {
        return coverArtList;
    }

    /**
     * @return the mbId
     */
  
    public String getMbId() {
        return mbId;
    }

    /**
     * @return the front
     */

    public MusicBrainzCoverArt getFront() {
        return front;
    }
    
    private void init(String mbId){
        
        Iterator<Image> i = getter.getImageListByMbID(mbId).iterator();
        while (i.hasNext()){

           MusicBrainzCoverArt musicBrainzCoverArt = new MusicBrainzCoverArtImpl(this.mbId, i.next());
           
            getCoverArtList().add(musicBrainzCoverArt);
			
			if(front == null && musicBrainzCoverArt.getType().equals(CoverArtType.FRONT.toString())){
               front=  musicBrainzCoverArt;
			}
        }
    }

   


}
