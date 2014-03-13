package clases;

import java.util.HashMap;
import java.util.Map;

public class RepoRSS {
	Map<String, RSSNew> news;

	public RepoRSS() {
		news = new HashMap<String, RSSNew>();
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