package clases;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class SavedRadio {
	public static String getRadio(Context c) {
		String r="";
		try {
			InputStream inputStream = c.openFileInput("radio.lst");

			if (inputStream != null) {
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String receiveString = "";

				while ((receiveString = bufferedReader.readLine()) != null) {
					r = receiveString;
				}
				bufferedReader.close();
				inputStream.close();
			}
		} catch (FileNotFoundException e) {
			Log.e("login activity", "File not found: " + e.toString());
		} catch (IOException e) {
			Log.e("login activity", "Can not read file: " + e.toString());
		}

		return r;
	}

	public static void deleteContent(Context c) {
		File file = new File(Environment.getExternalStorageDirectory(),
				"radio.lst");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput("radio.lst", Context.MODE_PRIVATE));
			outputStreamWriter.write("");
			outputStreamWriter.close();
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public static void saveRadio(String r, Context c) {
		File file = new File(Environment.getExternalStorageDirectory(),
				"radio.lst");
		try {
			file.createNewFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					c.openFileOutput("radio.lst", Context.MODE_PRIVATE));
			outputStreamWriter.write(r);
			outputStreamWriter.write("\n");
			outputStreamWriter.close();
		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}

	public static void createFile(Context c) {

		try {
			File file = new File(Environment.getExternalStorageDirectory(),
					"radio.lst");
			if (!file.exists()) {
				file.createNewFile();
				OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
						c.openFileOutput("radio.lst", Context.MODE_PRIVATE));
				outputStreamWriter.write("fc");
				outputStreamWriter.write("\n");
				outputStreamWriter.close();
			}

		} catch (Exception e) {
			System.out.println("e: " + e);
		}
	}
}
