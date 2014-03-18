package com.example.mrfeed2;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clases.RSSImage;
import clases.RSSNew;
import clases.XMLFeedsParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ArrayList<RSSNew> noticias;
	ProgressDialog pd; 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
//		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		if (conMgr.getActiveNetworkInfo() == null
//				&& !conMgr.getActiveNetworkInfo().isConnected()
//				&& !conMgr.getActiveNetworkInfo().isAvailable()) {
//			// No connectivity - Show alert
//			AlertDialog.Builder builder = new AlertDialog.Builder(this);
//			builder.setMessage(
//					"Unable to reach server, \nPlease check your connectivity.")
//					.setTitle("TD RSS Reader")
//					.setCancelable(false)
//					.setPositiveButton("Exit",
//							new DialogInterface.OnClickListener() {
//								@Override
//								public void onClick(DialogInterface dialog,
//										int id) {
//									finish();
//								}
//							});
//
//			AlertDialog alert = builder.create();
//			alert.show();
//
//		} else {
//			// Connected - Start parsing
			new AsyncLoadXMLFeed().execute();

//		}
		

//		ArrayList<RSSNew> feeds = new ArrayList<RSSNew>();
//		RSSNew feed1 = new RSSNew("Hect is the best", "Descripción1", "www.google.es", null, "Hect", null, false);
//		feeds.add(feed1);
//		RSSNew feed2 = new RSSNew("Albert es onalotnot", "Descripción2", "www.youtube.com", null, "Albert", null, false);
//		feeds.add(feed2);
//		RSSNew feed3 = new RSSNew("Naj es potorrobobo", "Descripción3", "www.cuantarazon.com", null, "Naj", null, false);
//		feeds.add(feed3);
//
//		XMLFeedsParser parser = new XMLFeedsParser();
//		//noticias = parser.getNoticias("");
//		
//		ListView listView = (ListView) findViewById(R.id.listView1);
//		listView.setAdapter(new UserItemAdapter(this, 
//				android.R.layout.simple_list_item_1, feeds));
	}

	public class UserItemAdapter extends ArrayAdapter<RSSNew> {
		private ArrayList<RSSNew> feeds;

		public UserItemAdapter(Context context, int textViewResourceId,
				ArrayList<RSSNew> feeds) {
			super(context, textViewResourceId, feeds);
			this.feeds = feeds;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.listitem, null);
			}

			RSSNew feed = feeds.get(position);
			if (feed != null) {
				TextView title = (TextView) v.findViewById(R.id.title);
				TextView description = (TextView) v.findViewById(R.id.description);
				ImageView image = (ImageView) v.findViewById(R.id.image);
//				TextView link = (TextView) v.findViewById(R.id.link);
//				TextView author = (TextView) v.findViewById(R.id.author);

				if (title != null) {
					title.setText(feed.getTitle());
				}

				if (description != null) {
					description.setText(feed.getDescription());
				}
				
				if (image != null) {
					if(feed.getImg()!=null){
						//loadImageFromURL(feed.getImg().getUrl(), image);
						 new DownloadImageTask(image).execute(feed.getImg().getUrl());
					}
					//image.setImageURI(Uri.parse(feed.getImg().getUrl()));
					//System.out.println(Uri.parse(feed.getImg().getUrl()));
					/*URL newurl;
					try {
						if(feed.getImg()!=null){
							newurl = new URL(feed.getImg().getUrl());
							image.setImageBitmap(BitmapFactory.decodeStream(newurl.openConnection().getInputStream()));
						}
						
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} */
					
				}
				
//				if (link != null) {
//					link.setText(feed.getLink());
//				}
//				
//				if (author != null) {
//					author.setText(feed.getAuthor());
//				}
			}
			return v;
		}
		
		public void loadImageFromURL(String fileUrl, ImageView iv){
			try {
				URL myFileUrl = new URL (fileUrl);
			    HttpURLConnection conn =(HttpURLConnection) myFileUrl.openConnection();
			    conn.setDoInput(true);
			    conn.connect();
			 
			    InputStream is = conn.getInputStream();
			    iv.setImageBitmap(BitmapFactory.decodeStream(is));
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    @Override
	    protected void onPreExecute() {
	        // TODO Auto-generated method stub
	        super.onPreExecute();
	        pd.show();
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	          InputStream in = new java.net.URL(urldisplay).openStream();
	          mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    @Override 
	    protected void onPostExecute(Bitmap result) {
	        super.onPostExecute(result);
	        pd.dismiss();
	        bmImage.setImageBitmap(result);
	    }
	  }
	
	private class AsyncLoadXMLFeed extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			// Obtain feed
			XMLFeedsParser parser = new XMLFeedsParser();
			noticias = parser.getNoticias("");
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			//Bundle bundle = new Bundle();
			//bundle.putSerializable("feed", unFeed);

			ListView listView = (ListView) findViewById(R.id.listView1);
			listView.setAdapter(new UserItemAdapter(getApplicationContext(), /*android.R.layout.simple_list_item_1*/R.layout.listitem, noticias));
			
//			// launch List activity
//			Intent intent = new Intent(MainActivity.this, MainActivity.class);
//			intent.putExtras(bundle);
//			startActivity(intent);
//
//			// kill this activity
//			finish();
		}

	}
	
}