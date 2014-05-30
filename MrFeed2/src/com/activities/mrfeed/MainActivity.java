package com.activities.mrfeed;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;



import com.example.mrfeed2.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import clases.ImageLoader;
import clases.SavedFeeds;
import clases.SavedRadio;
import clases.SelectedCategs;
import clases.rss.RSSNew;
import clases.rss.RepoRSS;
import clases.xml.XMLFeedsParser;

@SuppressLint("DefaultLocale")
public class MainActivity extends Activity {

	private ArrayList<RepoRSS> listaRepos;
	ProgressDialog pd;
	SharedPreferences preferencias;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		preferencias = getSharedPreferences("com.activities.mrfeed", Context.MODE_PRIVATE);
		ListView listView = (ListView) findViewById(R.id.listaNoticias);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setFocusable(false);
		preferencias.edit().putInt("changes", 0).commit();
		
		SavedFeeds.createFile(getApplicationContext());// new
		SelectedCategs.createFile(getApplicationContext());
		SavedRadio.createFile(getApplicationContext());
		
		ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (conMgr.getActiveNetworkInfo() == null
				&& !conMgr.getActiveNetworkInfo().isConnected()
				&& !conMgr.getActiveNetworkInfo().isAvailable()) {
			// No connectivity - Show alert
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage(
					"Unable to reach server, \nPlease check your connectivity.")
					.setTitle("TD RSS Reader")
					.setCancelable(false)
					.setPositiveButton("Exit",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int id) {
									finish();
								}
							});

			AlertDialog alert = builder.create();
			alert.show();

		} else {
			// // Connected - Start parsing
			new AsyncLoadXMLFeed().execute();
		}

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> lista, View view,
					int position, long arg3) {

				RSSNew rssNew = (RSSNew) lista.getItemAtPosition(position);
				if (rssNew == null) {
					Toast toast = Toast.makeText(getApplicationContext(),
							"Noticia no disponible", Toast.LENGTH_SHORT);
					toast.show();
				} else {
					Intent intent = new Intent(MainActivity.this,
							NoticiaActivity.class);
					intent.putExtra("rssNewChannel", rssNew.getChn());
					intent.putExtra("rssNewLink", rssNew.getLink());
					intent.putExtra("rssNewTitle", rssNew.getTitle());
					SimpleDateFormat sdf = new SimpleDateFormat("d'/'M'/'y");
					intent.putExtra("rssNewAuthor", rssNew.getAuthor());
					intent.putExtra("rssNewDate", sdf.format(rssNew.getDate()));
					intent.putExtra("rssNewDesc", rssNew.getDescription());
					intent.putExtra("rssNewCategories", rssNew.getCategories()
							.toString());
					intent.putExtra("rssNewFav", rssNew.isStar());
					startActivity(intent);
				}

			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		System.out.println(preferencias.getInt("changes", 0));
		if(preferencias.getInt("changes", 0)==1){
			Toast.makeText(this, "Actualizando...", Toast.LENGTH_SHORT).show();
			new AsyncRefresh().execute();
			preferencias.edit().putInt("changes", 0).commit();
		}
		
	}

	public class UserItemAdapter extends ArrayAdapter<RSSNew> {
		private ArrayList<RSSNew> feeds;
		public ImageLoader imageLoader;

		public UserItemAdapter(Context context, int textViewResourceId,
				ArrayList<RSSNew> feeds) {
			super(context, textViewResourceId, feeds);
			this.feeds = feeds;
			imageLoader = new ImageLoader(context);
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
				TextView categories = (TextView) v
						.findViewById(R.id.categories);
				ImageView image = (ImageView) v.findViewById(R.id.image);
				image.getLayoutParams().height = 160;
				image.getLayoutParams().width = 150;

				if (title != null) {
					title.setText(feed.getTitle());
				}

				if (categories != null) {
					if (feed.getCategories().size() > 3) {
						categories.setText(feed.getCategories().subList(0, 3)
								.toString());
					} else {
						categories.setText(feed.getCategories().toString());
					}
				}

				if (feed.getImg() != null) {
					imageLoader.DisplayImage(feed.getImg().getUrl(), image);
				}
			}
			return v;
		}

		@SuppressWarnings("deprecation")
		public void loadImageFromURL(String fileUrl, ImageView iv) {
			try {
				URL myFileUrl = new URL(fileUrl);
				HttpURLConnection conn = (HttpURLConnection) myFileUrl
						.openConnection();
				conn.setDoInput(true);
				conn.connect();

				InputStream is = conn.getInputStream();
				Bitmap bm = BitmapFactory.decodeStream(is);
				bm = BitmapFactory.decodeFile(fileUrl);

				iv.setImageDrawable(new BitmapDrawable(bm));
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
			//String organizarPor = preferencias.getString("checkboxOrg", "fc");
			ArrayList<RepoRSS> r = null;
			if(SavedRadio.getRadio(getApplicationContext()).equals("fc")){
				r = SavedFeeds.getSelectedCategories(getApplicationContext(),SelectedCategs.getCategs(getApplicationContext()));
			} else { //ff
				r = SavedFeeds.getSavedFeeds(getApplicationContext());
			}
					
			String[] channels = new String[r.size()];
			for (int i = 0; i < r.size(); i++) {
				channels[i] = r.get(i).getLnk();
				System.out.println(channels[i]);
			}
			listaRepos = parser.getNoticias(channels);
			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ListView listView = (ListView) findViewById(R.id.listaNoticias);
			ArrayList<RSSNew> news = new ArrayList<RSSNew>();
			for (RepoRSS repo : listaRepos) {
				for (RSSNew rssNew : repo.getNews().values()) {
					news.add(rssNew);
				}
			}

			Collections.sort(news);
			listView.setAdapter(new UserItemAdapter(getApplicationContext(),
					R.layout.listitem, news));
		}

	}

	private class AsyncRefresh extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			// Obtain feed
			XMLFeedsParser parser = new XMLFeedsParser();
			//String organizarPor = preferencias.getString("com.activities.mrfeed.checkboxOrg", "fc");
			ArrayList<RepoRSS> r;
			
			if(SavedRadio.getRadio(getApplicationContext()).equals("fc")){
				r = SavedFeeds.getSelectedCategories(getApplicationContext(),SelectedCategs.getCategs(getApplicationContext()));
			} else { //ff
				r = SavedFeeds.getSavedFeeds(getApplicationContext());
			}
			String[] channels = new String[r.size()];
			for (int i = 0; i < r.size(); i++) {
				channels[i] = r.get(i).getLnk();
			}
			listaRepos = parser.getNoticias(channels);

			return null;

		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			ListView listView = (ListView) findViewById(R.id.listaNoticias);
			ArrayList<RSSNew> news = new ArrayList<RSSNew>();
			for (RepoRSS repo : listaRepos) {
				for (RSSNew rssNew : repo.getNews().values()) {
					news.add(rssNew);
				}
			}

			Collections.sort(news);
			listView.setAdapter(new UserItemAdapter(getApplicationContext(),
					R.layout.listitem, news));
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();

		getActionBar().setDisplayHomeAsUpEnabled(true);
		inflater.inflate(R.menu.main, menu);
		MenuItem menuSearch = menu.findItem(R.id.action_search);

		final EditText searchText = (EditText) menuSearch.getActionView()
				.findViewById(R.id.txtSearch);
		
		searchText.setFocusable(true);
		searchText.requestFocus();
		
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(searchText, InputMethodManager.SHOW_IMPLICIT);
//		InputMethodManager keyboard=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        keyboard.showSoftInput(searchText, 0);
//		if(imm != null){
//	        imm.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
//	    }
		//imm.toggleSoftInputFromWindow(searchText.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
		searchText.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				//if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_SEARCH) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_SEARCH){
					String sText = searchText.getText().toString()
							.toLowerCase();
					if (!sText.equals("")) {
						actualizaListView(
								(ListView) findViewById(R.id.listaNoticias), sText);
					} else {
						new AsyncRefresh().execute();
					}
					return true;
				} else if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
					new AsyncRefresh().execute();
				}
				return false;

			}

			private void actualizaListView(ListView listView, String searchTerm) {
				// TODO Auto-generated method stub
				ArrayList<RSSNew> news = new ArrayList<RSSNew>();
				for (int i = 0; i < listView.getCount(); i++) {
					RSSNew rss = (RSSNew) listView.getAdapter().getItem(i);
					boolean estaEnCats = false;
					for (String cat : rss.getCategories()) {
						if (cat.toLowerCase().contains(searchTerm))
							estaEnCats = true;
					}
					if (rss.getTitle().toLowerCase().contains(searchTerm)
							|| estaEnCats) {
						news.add(rss);
					}
				}
				Collections.sort(news);
				listView.setAdapter(new UserItemAdapter(
						getApplicationContext(), R.layout.listitem, news));
			}
		});
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_refresh:
			Toast.makeText(this, "Actualizando...", Toast.LENGTH_SHORT).show();
			new AsyncRefresh().execute();
			break;
		case R.id.action_settings:
			Intent i = new Intent(MainActivity.this, SettingsActivity.class);
			i.putExtra("vista", preferencias.getString("vista", "lista"));
			startActivity(i);
			break;
		default:
			break;
		}

		return true;
	}
}