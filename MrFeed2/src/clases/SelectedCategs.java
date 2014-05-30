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

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class SelectedCategs {
	public static ArrayList<String> getCategs(Context c) {
		ArrayList<String> categs = new ArrayList<String>();
		try {
			InputStream inputStream = c.openFileInput("categs.lst");

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(
						inputStream);
				BufferedReader bufferedReader = new BufferedReader(
						inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					String categoria = receiveString;
					categs.add(categoria);
				}
				bufferedReader.close();
				inputStream.close();
			}
		} catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return categs;
	}

	public static void deleteCateg(String cat, Context c) {
		ArrayList<String> categs = getCategs(c);
		if (categs != null) {
			for (int i = 0; i < categs.size(); i++) {
				if (categs.get(i).equals(cat)) {
					categs.remove(i);
				}
			}
		}
		saveListOfCategs(categs, c);
	}

	public static void deleteContent(Context c) {
		File file = new File(Environment.getExternalStorageDirectory(),
				"categs.lst");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput("categs.lst", Context.MODE_PRIVATE));
			outputStreamWriter.write("");
			outputStreamWriter.close();
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public static void saveCateg(String cat, Context c) {
		File file = new File(Environment.getExternalStorageDirectory(),
				"categs.lst");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput("categs.lst", Context.MODE_APPEND));
			outputStreamWriter.write(cat);
			outputStreamWriter.write("\n");
			outputStreamWriter.close();
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public static void saveListOfCategs(List<String> categs, Context c) {

		File file = new File(Environment.getExternalStorageDirectory(),
				"categs.lst");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput("categs.lst", Context.MODE_PRIVATE));
			for (String cat : categs) {
				outputStreamWriter.write(cat);
				outputStreamWriter.write("\n");
			}
			outputStreamWriter.close();
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public static void createFile(Context c) {

		try {
			File file = new File(Environment.getExternalStorageDirectory(),
					"categs.lst");
			if (!file.exists()) {
				file.createNewFile();
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
						c.openFileOutput("categs.lst", Context.MODE_PRIVATE));
				for (String cat : SavedFeeds.getCategorias(c)) {
					outputStreamWriter.write(cat);
					outputStreamWriter.write("\n");
				}
				outputStreamWriter.close();
			}

		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}
}
