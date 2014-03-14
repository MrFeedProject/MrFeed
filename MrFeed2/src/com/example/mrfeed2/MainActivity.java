package com.example.mrfeed2;

import java.util.ArrayList;
import java.util.Date;

import clases.RSSImage;
import clases.RSSNew;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ArrayList<RSSNew> feeds = new ArrayList<RSSNew>();
		RSSNew feed1 = new RSSNew("Hect is the best", "Descripción1", "www.google.es", null, "Hect", null, false);
		feeds.add(feed1);
		RSSNew feed2 = new RSSNew("Albert es onalotnot", "Descripción2", "www.youtube.com", null, "Albert", null, false);
		feeds.add(feed2);
		RSSNew feed3 = new RSSNew("Naj es potorrobobo", "Descripción3", "www.cuantarazon.com", null, "Naj", null, false);
		feeds.add(feed3);

		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(new UserItemAdapter(this,
				android.R.layout.simple_list_item_1, feeds));
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
}