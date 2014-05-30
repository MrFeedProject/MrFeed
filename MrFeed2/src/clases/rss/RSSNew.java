package clases.rss;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class RSSNew implements Comparable<RSSNew> {
	private String chn;
	private String tit;
	private String des;
	private String lnk;
	private String aut;
	private Date dat;
	private RSSImage img;
	private List<String> cat;
	private boolean star;

	public RSSNew() {
		this("NoChannel", "Untitled", "NoDesc", "noLinked", null, "NoAuth", null, null, false);
	}
	
	public RSSNew(String title, String link, boolean star) {
		/**
		 * Creates a RSS new with "Title", "Description", "Link", "Author",
		 * "Date"
		 */
		this.chn = "";
		this.tit = title;
		this.des = "";
		this.lnk = link;
		this.aut = "";
		this.dat = null;
		this.img = null;
		this.star = star;
	}

	public RSSNew(String channel, String title, String description, String link, RSSImage image) {
		this(channel, title, description, link, image, "NoAuth", null, null, false);
	}

	public RSSNew(String channel, String title, String description, String link, RSSImage img,
			String author, Date date, List<String> categories, boolean star) {
		/**
		 * Creates a RSS new with "Title", "Description", "Link", "Author",
		 * "Date"
		 */
		this.chn = channel;
		this.tit = title;
		this.des = description;
		this.lnk = link;
		this.aut = author;
		this.dat = date;
		this.img = img;
		this.cat = categories;
		this.star = star;
	}

	public List<String> getCategories() {
		return cat;
	}

	public void setCategories(List<String> cat) {
		this.cat = cat;
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

	public void setImg(RSSImage img) {
		this.img = img;
	}

	public String getTitle() {
		/**
		 * Returns the RSSnew title
		 */
		return tit;
	}

	public RSSImage getImg() {
		return img;
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
		SimpleDateFormat sdf = new SimpleDateFormat(
				"dia: d 'mes: ' MMMM ' año: ' yyyy");
		return "<p>" + getTitle() + "<br>" + getLink() + "<br>" + getAuthor()
				+ "<br>" + getDescription() + "<br>"
				+ sdf.format(getDate()).toString() + "<br>" + "</p>";
	}

	public String getChn() {
		return chn;
	}

	public void setChn(String chn) {
		this.chn = chn;
	}

	@Override
	public int compareTo(RSSNew another) {
		// TODO Auto-generated method stub
		return another.dat.compareTo(this.dat);	
	}

}
