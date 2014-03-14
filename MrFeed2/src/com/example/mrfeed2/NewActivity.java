package com.example.mrfeed2;

import clases.RSSNew;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.widget.ToggleButton;

public class NewActivity extends Activity {
	RSSNew rssNew;
	public NewActivity(RSSNew rssNew){
		this.rssNew = rssNew;
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vistanoticia);
		
		
		TextView rssNewTitle = (TextView) findViewById(R.id.RSSNewTitle);
		TextView rssNewDesc = (TextView) findViewById(R.id.RSSNewDesc);
		ToggleButton rssNewFav = (ToggleButton) findViewById(R.id.RSSNewFav);
		
		rssNewTitle.setText(rssNew.getTitle());
		rssNewDesc.setText(rssNew.getDescription());
		if(rssNew.isStar()) rssNewFav.setChecked(true);
		else rssNewFav.setChecked(false);
		
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu., menu);
        return true;
    }
}
