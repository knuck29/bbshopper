package com.knuck29.bbshopper.catalog;

import java.net.URL;

/**
 * Created by knolker on 9/29/13.
 */
public abstract class Node  {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
