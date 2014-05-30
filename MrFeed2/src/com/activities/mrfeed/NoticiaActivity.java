package com.activities.mrfeed;

import android.R.drawable;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.widget.TextView;
import clases.Favs;
import clases.rss.RSSNew;

import com.example.mrfeed2.R;

public class NoticiaActivity extends Activity {
	private RSSNew rssNew;
	private MenuItem fav;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		rssNew = new RSSNew();
		rssNew.setChn(getIntent().getStringExtra("rssNewChannel"));
		rssNew.setLink(getIntent().getStringExtra("rssNewLink"));
		rssNew.setTitle(getIntent().getStringExtra("rssNewTitle"));
		rssNew.setAuthor(getIntent().getStringExtra("rssNewAuthor"));
		String date = getIntent().getStringExtra("rssNewDate");
		rssNew.setDescription(getIntent().getStringExtra("rssNewDesc"));
		if(Favs.isFav(rssNew.getLink(), getApplicationContext())){ 
			rssNew.setStar(true);
		} else {
			rssNew.setStar(false);
		}
		
		
		
		setTitle(rssNew.getChn());
		TextView rssNewTitle = (TextView) findViewById(R.id.RSSNewTitle);
		rssNewTitle.setText(rssNew.getTitle());
		TextView rssNewAuthorDate = (TextView) findViewById(R.id.RSSNewAuthorDate);
		rssNewAuthorDate.setText(rssNew.getAuthor()+" - "+date);
		WebView webView = (WebView) findViewById(R.id.RSSNewContent); 
		webView.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setPluginState(PluginState.ON);
		webView.setWebChromeClient(new WebChromeClient() {
        });
		
		webView.loadData(rssNew.getDescription(), "text/html; charset=UTF-8", null);
		
		String s = webView.toString();
		System.out.println(s);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.noticiamenu, menu);
		fav = menu.findItem(R.id.action_fav);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		if(rssNew.isStar()){
			fav.setIcon(drawable.btn_star_big_on);
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// action with ID action_refresh was selected
		case R.id.action_fav:
			if(!rssNew.isStar()) {
				Favs.saveFav(rssNew, getApplicationContext());
				rssNew.setStar(true);
				fav.setIcon(drawable.btn_star_big_on);
			} else {
				Favs.deleteFav(rssNew, getApplicationContext());
				rssNew.setStar(false);
				fav.setIcon(drawable.btn_star_big_off);
			}
			break;
		}

		return true;
	}
}
