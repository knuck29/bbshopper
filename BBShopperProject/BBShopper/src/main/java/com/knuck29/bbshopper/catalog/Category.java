package com.knuck29.bbshopper.catalog;

/**
 * Created by knolker on 9/28/13.
 */
public class Category implements Comparable<Category> {

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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (!(obj instanceof Category))
			return false;

		Category another = (Category) obj;

		return id.equals(another.id);

	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public int compareTo(Category another) {

		if (another == null) {
			throw new ClassCastException("A Category object expected.");
		}

		return id.compareTo(another.id);
	}
}
