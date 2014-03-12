package com.example.mrfeed2;

import java.util.ArrayList;

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

		ArrayList<UserRecord> users = new ArrayList<UserRecord>();
		UserRecord user1 = new UserRecord("Justin", "someemail@gmail.com");
		users.add(user1);
		UserRecord user2 = new UserRecord("Jeremy", "someemail@yahoo.com");
		users.add(user2);
		UserRecord user3 = new UserRecord("Frank", "someemail@hotmail.com");
		users.add(user3);

		ListView listView = (ListView) findViewById(R.id.listView1);
		listView.setAdapter(new UserItemAdapter(this,
				android.R.layout.simple_list_item_1, users));
	}

	public class UserItemAdapter extends ArrayAdapter<UserRecord> {
		private ArrayList<UserRecord> users;

		public UserItemAdapter(Context context, int textViewResourceId,
				ArrayList<UserRecord> users) {
			super(context, textViewResourceId, users);
			this.users = users;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.listitem, null);
			}

			UserRecord user = users.get(position);
			if (user != null) {
				TextView username = (TextView) v.findViewById(R.id.username);
				TextView email = (TextView) v.findViewById(R.id.email);

				if (username != null) {
					username.setText(user.username);
				}

				if (email != null) {
					email.setText("Email: " + user.email);
				}
			}
			return v;
		}
	}

	public class UserRecord {
		public String username;
		public String email;

		public UserRecord(String username, String email) {
			this.username = username;
			this.email = email;
		}
	}
}