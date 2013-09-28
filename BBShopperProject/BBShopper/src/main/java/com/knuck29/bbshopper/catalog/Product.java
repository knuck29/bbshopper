package com.knuck29.bbshopper.catalog;

/**
 * Created by knolker on 9/28/13.
 */
public class Product implements Comparable<Product> {

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

		if (this == obj) // Are they same instance?
			return true;

		if (obj == null) // Is the object being compared null?
			return false;

		if (!(obj instanceof Category))
			return false;

		Product product = (Product) obj;

		return id.equals(product.id);

	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public int compareTo(Product another) {

		if (another == null) {
			throw new ClassCastException("A Product object expected.");
		}

		return id.compareTo(another.id);
	}
}
