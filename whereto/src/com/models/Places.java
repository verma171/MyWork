package com.models;

import java.util.ArrayList;



public class Places
{  
  public class PartPlaces
	{
	    public String placename ; 
	    public ArrayList<String> templates ;
	    public ArrayList<String> search_keywords ;
	    public ArrayList<String> search ;
	    public ArrayList<String> searchRadius ;
	    public ArrayList<String> tileColors ;
		public PartPlaces(String placename, ArrayList<String> templates,
				ArrayList<String> search_keywords, ArrayList<String> search,
				ArrayList<String> searchRadius, ArrayList<String> tileColors) 
		{
			super();
			this.placename = placename;
			this.templates = templates;
			this.search_keywords = search_keywords;
			this.search = search;
			this.searchRadius = searchRadius;
			this.tileColors = tileColors;
			
		}
	}
    public ArrayList<PartPlaces> master ;

	public Places(ArrayList<PartPlaces> master) 
	{
		this.master = master;
	}
}

