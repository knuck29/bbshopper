package com.knuck29.bbshopper.catalog;

/**
 * Created by knolker on 9/29/13.
 */
public class Breadcrumb extends Node {
    @Override
    public int hashCode () {
        return getTitle().hashCode();
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj)
            return true;

        if (obj == null)
            return false;

        if (!(obj instanceof Category))
            return false;

        Breadcrumb another = (Breadcrumb) obj;

        return getTitle().equals(another.getTitle());
    }
}
