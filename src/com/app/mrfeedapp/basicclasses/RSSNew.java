package com.app.mrfeedapp.basicclasses;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RSSNew {
	private String tit;
	private String des;
	private String lnk;
	private String aut;
	private Date dat;
	private boolean star;

	public RSSNew() {
		this("Untitled", "NoDesc", "noLinked", "NoAuth", new Date(), false);
	}

	public RSSNew(String title, String description, String link, String author,
			Date date, boolean star) {
		/**
		 * Creates a RSS new with "Title", "Description", "Link", "Author",
		 * "Date"
		 */
		this.tit = title;
		this.des = description;
		this.lnk = link;
		this.aut = author;
		this.dat = date;
		this.star = star;
	}

	public void setTitle(String title) {
		/**
		 * Sets the RSSnew Title to "title"
		 */
		this.tit = title;
	}

	public void setLink(String link) {
		/**
		 * Sets the RSSnew Link to "link"
		 */
		this.lnk = link;
	}

	public void setDescription(String description) {
		/**
		 * Sets the RSSnew Description to "description"
		 */
		this.des = description;
	}

	public void setAuthor(String author) {
		/**
		 * Sets the RSSnew Author to "author"
		 */
		this.aut = author;
	}

	public void setDate(Date date) {
		/**
		 * Sets the RSSnew Date to "date"
		 */
		this.dat = date;
	}
	
	public boolean isStar() {
		return star;
	}

	public void setStar(boolean star) {
		this.star = star;
	}
	
	public String getTitle() {
		/**
		 * Returns the RSSnew title
		 */
		return tit;
	}

	public String getLink() {
		/**
		 * Returns the RSSnew link
		 */
		return lnk;
	}

	public String getDescription() {
		/**
		 * Returns the RSSnew description
		 */
		return des;
	}

	public String getAuthor() {
		/**
		 * Returns the RSSnew author
		 */
		return aut;
	}

	public Date getDate() {
		/**
		 * Returns the RSSnew publish date (java.util.Date)
		 */
		return dat;
	}

	public String toHTML() {
		SimpleDateFormat sdf = new SimpleDateFormat("dia: d 'mes: ' MMMM ' a�o: ' yyyy");
	    return "<p>"+
				getTitle()+"<br>"+
				getLink()+"<br>"+
				getAuthor()+"<br>"+
				getDescription()+"<br>"+
				sdf.format(getDate()).toString()+"<br>"+
				"</p>";
	}
}
