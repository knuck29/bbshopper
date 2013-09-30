package com.knuck29.bbshopper.catalog;

import java.io.Serializable;

/**
 * Created by knolker on 9/28/13.
 */
public class Product extends Node implements Comparable<Product>, Serializable {

    private Price mPrice = null;
    private NodeType mNodeType;
    private Image mImage;

	@Override
	public boolean equals(Object obj) {

		if (this == obj) // Are they same instance?
			return true;

		if (obj == null) // Is the object being compared null?
			return false;

		if (!(obj instanceof Product))
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

    public void setPrice(Price price){
        mPrice = price;
    }

    public void setImage(Image image){
        mImage = image;
    }

    public void setNodeType(NodeType nodeType){
        mNodeType = nodeType;
    }

    public NodeType getNodeType(){
        return mNodeType;
    }

    public String getFullImage(){
        return mImage.getFullImage();
    }

    public String getSmallThumbnail(){
        return mImage.getSmallThumbnail();
    }

    public String getLargeThumbnail(){
        return mImage.getLargeThumbnail();
    }

    public double getSalePrice(){
        return mPrice.getSale();
    }

    public double getValuePrice(){
        return mPrice.getValue();
    }

    public double getRetailPrice(){
        return mPrice.getRetail();
    }

    public String getCurrency(){
        return mPrice.getCurrency();
    }

    public void setRetailPrice(double retailPrice){
        mPrice.setRetail(retailPrice);
    }

    public void setSalePrice(double salePrice){
        mPrice.setSale(salePrice);
    }

    public enum NodeType{
        Category, Product
    }
}
