package clases.rss;

import java.util.ArrayList;
import java.util.List;


public class RSSFeeds {
	private static List<RepoRSS> feeds = new ArrayList<RepoRSS>();

	public static List<RepoRSS> getFeedList() {
		return feeds;
	}

	public static void addToFeedList(RepoRSS reporss) {
		if (!existeRepo(reporss))
			feeds.add(new RepoRSS(reporss.getTit(), reporss.getLnk(), reporss
					.getDesc()));
	}

	private static boolean existeRepo(RepoRSS reporss) {
		boolean encontrado = false;
		for (RepoRSS rs : feeds) {
			if (rs.getLnk().equals(reporss.getLnk()))
				encontrado = true;
		}
		return encontrado;
	}

	public static void removeFromFeedList(RepoRSS reporss) {
		if(existeRepo(reporss))
			feeds.remove(reporss);
	}
}
