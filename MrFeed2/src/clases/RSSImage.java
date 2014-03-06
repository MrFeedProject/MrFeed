package clases;

public class RSSImage {
	private String description; // <description> Optional. Specifies the text in
								// the HTML title attribute of the link around
								// the image
	private String height; // <height> Optional. Defines the height of the
							// image. Default is 31. Maximum value is 400
	private String link; // <link> Required. Defines the hyperlink to the
							// website that offers the channel
	private String title; // <title> Required. Defines the text to display if
							// the image could not be shown
	private String url; // <url> Required. Specifies the URL to the image
	private String width; // <width> Optional. Defines the width of the image.
							// Default is 88. Maximum value is 144

	public RSSImage(String description, String height, String link,
			String title, String url, String width) {
		this.description = description;
		this.height = height;
		this.link = link;
		this.title = title;
		this.url = url;
		this.width = width;
	}

	public RSSImage(String link, String title, String url) {
		this("NoDesc", "NoHeight", link, title, url, "NoWidth");
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}
}
