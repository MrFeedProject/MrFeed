package clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import clases.rss.RepoRSS;



import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class SavedFeeds {

	public static ArrayList<RepoRSS> getSavedFeeds(Context c) { // nombre[[link
		// TODO Auto-generated method stub
		ArrayList<RepoRSS> feeds = new ArrayList<RepoRSS>();

		try {
			InputStream inputStream = c.openFileInput("SFeeds.lst");

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(receiveString,
							"[[");
					String nombre = st.nextToken();
					String link = st.nextToken();
					String categoria = st.nextToken();
					feeds.add(new RepoRSS(nombre, link, "", categoria));
				}
				bufferedReader.close();
				inputStream.close();
			}
		} catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return feeds;
	}

	public static ArrayList<RepoRSS> getSelectedCategories(Context c,
			List<String> categorias) { // nombre[[link
		// TODO Auto-generated method stub
		ArrayList<RepoRSS> feeds = new ArrayList<RepoRSS>();

		try {
			InputStream inputStream = c.openFileInput("SFeeds.lst");

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(receiveString,
							"[[");
					String nombre = st.nextToken();
					String link = st.nextToken();
					String categoria = st.nextToken();
					if (categorias.indexOf(categoria) != -1) {
						feeds.add(new RepoRSS(nombre, link, "", categoria));
					}
				}
				bufferedReader.close();
				inputStream.close();
			}
		} catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return feeds;
	}

	public static ArrayList<String> getCategorias(Context c) { // nombre[[link
		// TODO Auto-generated method stub
		ArrayList<String> categorias = new ArrayList<String>();

		try {
			InputStream inputStream = c.openFileInput("SFeeds.lst");

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(receiveString,
							"[[");
					st.nextToken();
					st.nextToken();
					String categoria = st.nextToken();
					if (!categorias.contains(categoria)) {
						categorias.add(categoria);
					}
				}
				bufferedReader.close();
				inputStream.close();
			}
		} catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return categorias;
	}

	public static void deleteFeed(RepoRSS newFeed, Context c) { // GUARDA UN
																// FEED NUEVO
																// CON
		ArrayList<RepoRSS> repos = getSavedFeeds(c);
		if (repos != null) {
			for (int i = 0; i < repos.size(); i++) {
				if (repos.get(i).getLnk().equals(newFeed.getLnk())
						&& repos.get(i).getTit().equals(newFeed.getTit())) {
					repos.remove(i);
				}
			}
		}

		saveListOfFeeds(repos, c);
	}

	public static void saveFeed(RepoRSS newFeed, Context c) { // GUARDA UN FEED
																// NUEVO CON
																// FORMATO
																// nombre[[link
		File file = new File(Environment.getExternalStorageDirectory(),
				"SFeeds.lst");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput("SFeeds.lst", Context.MODE_APPEND));
			outputStreamWriter.write(newFeed.getTit() + "[[" + newFeed.getLnk()
					+ "[[" + newFeed.getCategoria());
			outputStreamWriter.write("\n");
			outputStreamWriter.close();
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public static void deleteContent(Context c) {
		File file = new File(Environment.getExternalStorageDirectory(),
				"SFeeds.lst");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput("SFeeds.lst", Context.MODE_PRIVATE));
			outputStreamWriter.write("");
			outputStreamWriter.close();
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public static void saveListOfFeeds(List<RepoRSS> feeds, Context c) {

		File file = new File(Environment.getExternalStorageDirectory(),
				"SFeeds.lst");
		deleteContent(c);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput("SFeeds.lst", Context.MODE_PRIVATE));
			for (RepoRSS repoRSS : feeds) {
				outputStreamWriter.write(repoRSS.getTit() + "[["
						+ repoRSS.getLnk() + "[[" + repoRSS.getCategoria());
				outputStreamWriter.write("\n");
			}
			outputStreamWriter.close();
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public static void createFile(Context c) { // GUARDA UN FEED NUEVO CON

		try {
			File file = new File(Environment.getExternalStorageDirectory(),
					"SFeeds.lst");
			if (!file.exists()) {
				file.createNewFile();
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
						c.openFileOutput("SFeeds.lst", Context.MODE_PRIVATE));
				outputStreamWriter
						.write("Engadget en Español[[http://es.engadget.com/rss.xml[[Tecnología"
								+ "\n");
				outputStreamWriter
						.write("VidaExtra[[http://www.vidaextra.com/atom.xml[[Videojuegos"
								+ "\n");
				outputStreamWriter
						.write("FeedBurner[[http://feeds.feedburner.com/elladodelmal[[Otros"
								+ "\n");
				outputStreamWriter.close();
			}

		} catch (Exception e) {
			System.out.println("e: " + e);
		}

		// if
		// (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
		// //handle case of no SDCARD present
		// System.out.println("nooooo caaaaard");
		// } else {
		// File file = new File(Environment.getExternalStorageDirectory()
		// +File.separator
		// +"feedsCont" //folder name
		// +File.separator
		// +"feeds.lst"); //file name
		// file.mkdirs();
		// }

	}
}
