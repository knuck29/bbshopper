package com.knuck29.bbshopper.catalog;

/**
 * Created by knolker on 9/28/13.
 */
public class Product extends Node implements Comparable<Product> {

	@Override
	public boolean equals(Object obj) {

		if (this == obj) // Are they same instance?
			return true;

		if (obj == null) // Is the object being compared null?
			return false;

		if (!(obj instanceof Category))
			return false;

		Product product = (Product) obj;

		return getId().equals(product.getId());

	}

	@Override
	public int compareTo(Product another) {

		if (another == null) {
			throw new ClassCastException("A Product object expected.");
		}

		return getId().compareTo(another.getId());
	}
}
