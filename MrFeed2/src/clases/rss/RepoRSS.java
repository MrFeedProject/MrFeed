package clases.rss;

import java.util.HashMap;
import java.util.Map;


public class RepoRSS {

	private Map<String, RSSNew> news;
	private RSSImage channelImg;
	private String tit;
	private String lnk;
	private String desc;
	private String categoria;

	public RepoRSS() {
		news = new HashMap<String, RSSNew>();
		tit = "NoTitle";
		lnk = "NoLink";
		desc = "NoDesc";
		channelImg = null;
		categoria = "Otros";
	}

	public RepoRSS(String title, String link, String desc) {
		this();
		this.tit = title;
		this.lnk = link;
		this.desc = desc;
	}

	public RepoRSS(String title, String link, String desc, String categoria) {
		this();
		this.tit = title;
		this.lnk = link;
		this.desc = desc;
		this.categoria = categoria;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Map<String, RSSNew> getNews() {
		return news;
	}

	public void setNews(Map<String, RSSNew> news) {
		this.news = news;
	}

	public RSSImage getChannelImg() {
		return channelImg;
	}

	public void setChannelImg(RSSImage channelImg) {
		this.channelImg = channelImg;
	}

	public String getTit() {
		return tit;
	}

	public void setTit(String tit) {
		this.tit = tit;
	}

	public String getLnk() {
		return lnk;
	}

	public void setLnk(String lnk) {
		this.lnk = lnk;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public RSSNew getNew(String link) {
		/**
		 * Returns the new that its URL is "link" Returns null if "link" does
		 * not exist in the repo.
		 */
		return news.get(link);
	}

	public void addNew(RSSNew n) {
		if (!existsNew(n.getLink()))
			news.put(n.getLink(), n);
	}

	public void removeNew(String link) {
		news.remove(link);
	}

	public boolean existsNew(String link) {
		return news.containsKey(link);
	}
}