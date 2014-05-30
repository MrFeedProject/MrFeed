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

import clases.rss.RSSNew;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class Favs {

	public static ArrayList<RSSNew> getSavedFavs(Context c) { //NOMBRE[[LINK
		ArrayList<RSSNew> links = new ArrayList<RSSNew>();

		try {
			InputStream inputStream = c.openFileInput("Favs.lst");

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					StringTokenizer st = new StringTokenizer(receiveString, "[[");
					String nombre = st.nextToken();
					String link = st.nextToken();
					links.add(new RSSNew(nombre, link, true));
				}
				bufferedReader.close();
				inputStream.close();
			}
		} catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return links;
	}

	public static void deleteFav(RSSNew r, Context c) {
		ArrayList<RSSNew> favs = getSavedFavs(c);
		if (favs != null) {
			for (int i = 0; i < favs.size(); i++) {
				if (favs.get(i).getLink().equals(r.getLink())) {
					favs.remove(i);
				}
			}
		}
		saveFavs(favs, c);
	}

	public static boolean isFav(String link, Context c){
		boolean isFav = false;
		ArrayList<RSSNew> favs = getSavedFavs(c);
		for(RSSNew fav : favs){
			if(fav.getLink().equals(link)) isFav = true;
		}
		return isFav;
	}
	
	public static void saveFav(RSSNew f, Context c) {

		File file = new File(Environment.getExternalStorageDirectory(),
				"Favs.lst");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			ArrayList<RSSNew> favs = getSavedFavs(c);
			boolean existeFav = false;
			for(RSSNew fav : favs){
				if(fav.getLink().equals(f.getLink())) existeFav=true;
			}
			if(!existeFav){
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput("Favs.lst", Context.MODE_APPEND));
			outputStreamWriter.write(f.getTitle()+"[["+f.getLink());
			outputStreamWriter.write("\n");
			outputStreamWriter.close();
				Toast.makeText(c, "Marcada como favorita", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(c, "Noticia ya guardada", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public static void deleteContent(Context c) {
		File file = new File(Environment.getExternalStorageDirectory(),
				"Favs.lst");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput("Favs.lst", Context.MODE_PRIVATE));
			outputStreamWriter.write("");
			outputStreamWriter.close();
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public static void saveFavs(List<RSSNew> favs, Context c) {

		File file = new File(Environment.getExternalStorageDirectory(),
				"Favs.lst");
		deleteContent(c);
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput("Favs.lst", Context.MODE_PRIVATE));
			for (RSSNew fav : favs) {
				outputStreamWriter.write(fav.getTitle()+"[["+fav.getLink());
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
					"Favs.lst");
			if (!file.exists()) {
				file.createNewFile();
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
						c.openFileOutput("Favs.lst", Context.MODE_PRIVATE));
				outputStreamWriter.write("");
				outputStreamWriter.close();
			}
		} catch (Exception e) {
			System.out.println("e: " + e);
		}

	}
}
