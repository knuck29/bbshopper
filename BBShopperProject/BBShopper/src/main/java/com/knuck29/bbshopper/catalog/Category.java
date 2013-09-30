package com.knuck29.bbshopper.catalog;

/**
 * Created by knolker on 9/28/13.
 */
public class Category extends Node implements Comparable<Category> {

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof Category))
			return false;

		Category another = (Category) obj;

		return getId().equals(another.getId());

	}

	@Override
	public int compareTo(Category another) {

		if (another == null) {
			throw new ClassCastException("A Category object expected.");
		}

		return getId().compareTo(another.getId());
	}
}
