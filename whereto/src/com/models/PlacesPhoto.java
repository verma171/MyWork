package com.models;

public class PlacesPhoto {
private String heigth;
private String width;
private String photo_reference;
public String getHeigth() {
	return heigth;
}
public void setHeigth(String heigth) {
	this.heigth = heigth;
}
public String getWidth() {
	return width;
}
public void setWidth(String width) {
	this.width = width;
}
public String getPhoto_reference() {
	return photo_reference;
}
public void setPhoto_reference(String photo_reference) {
	this.photo_reference = photo_reference;
}
public PlacesPhoto(String heigth, String width, String photo_reference) {
	super();
	this.heigth = heigth;
	this.width = width;
	this.photo_reference = photo_reference;
}
 
}
