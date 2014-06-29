package com.models;

import java.util.ArrayList;

public class Group {
	   public String groupname;
	  public  ArrayList<ListItemModel> children = new ArrayList<ListItemModel>();

	  public Group(String string) {
	    this.groupname = string;
	  }
}
