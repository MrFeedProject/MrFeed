package com.app.mrfeedapp.basicclasses;

public class RSSImage {
	String description; //<description>	Optional. Specifies the text in the HTML title attribute of the link around the image
	String height;		//<height>	Optional. Defines the height of the image. Default is 31. Maximum value is 400
	String link;		//<link>	Required. Defines the hyperlink to the website that offers the channel
	String title;		//<title>	Required. Defines the text to display if the image could not be shown
	String url;			//<url>	Required. Specifies the URL to the image
	String width;		//<width>	Optional.  Defines the width of the image. Default is 88. Maximum value is 144
	
	public RSSImage(String description, String height, String link, String title, String url, String width){
		this.description = description;
		this.height = height;
		this.link = link;
		this.title = title;
		this.url = url;
		this.width = width;
	}
	public RSSImage(String link, String title, String url){
		this("NoDesc","NoHeight",link,title,url,"NoWidth");
	}
}
	
	
