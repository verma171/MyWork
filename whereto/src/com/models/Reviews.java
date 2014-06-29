package com.models;

import java.io.Serializable;

public class Reviews implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String review;
	private Double rating;
	private long timespan;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}
	public Double getRating() {
		return rating;
	}
	public void setRating(Double rating) {
		this.rating = rating;
	}
	public long getTimespan() {
		return timespan;
	}
	public void setTimespan(long timespan) {
		this.timespan = timespan;
	}
}
