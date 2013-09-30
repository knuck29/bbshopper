package com.knuck29.bbshopper.catalog;

/**
 * Created by knolker on 9/29/13.
 */
public class Image {

    String fullImage;
    String smallThumbnail;
    String largeThumbnail;

    public String getFullImage () {
        return fullImage;
    }

    public void setFullImage (String fullImage) {
        this.fullImage = fullImage;
    }

    public String getSmallThumbnail () {
        return smallThumbnail;
    }

    public void setSmallThumbnail (String smallThumbnail) {
        this.smallThumbnail = smallThumbnail;
    }

    public String getLargeThumbnail () {
        return largeThumbnail;
    }

    public void setLargeThumbnail (String largeThumbnail) {
        this.largeThumbnail = largeThumbnail;
    }
}
