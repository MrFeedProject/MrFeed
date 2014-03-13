package com.example.mrfeed2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import clases.RSSImage;
import clases.RSSNew;
import clases.XMLFeedsParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private ArrayList<RSSNew> noticias;

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
				TextView link = (TextView) v.findViewById(R.id.link);
				TextView author = (TextView) v.findViewById(R.id.author);

				if (title != null) {
					title.setText(feed.getTitle());
				}

				if (description != null) {
					description.setText(feed.getDescription());
				}
				
				if (link != null) {
					link.setText(feed.getLink());
				}
				
				if (author != null) {
					author.setText(feed.getAuthor());
				}
			}
			return v;
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
			listView.setAdapter(new UserItemAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, noticias));
			
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