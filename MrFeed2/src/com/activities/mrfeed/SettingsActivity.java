package com.activities.mrfeed;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import clases.Favs;
import clases.SavedFeeds;
import clases.SavedRadio;
import clases.SelectedCategs;
import clases.rss.RSSNew;
import clases.rss.RepoRSS;

import com.example.mrfeed2.R;

public class SettingsActivity extends FragmentActivity implements
		ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);

		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment fragment = new FragmentConfig();
			switch (position) {
			case 0:
				return fragment = new FragmentFeeds();
			case 1:
				return fragment = new FragmentConfig();
			case 2:
				return fragment = new FragmentAboutUs();
			default:
				break;
			}
			return fragment;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
			case 0:
				return getString(R.string.title_section1);
			case 1:
				return getString(R.string.title_section2);
			case 2:
				return getString(R.string.title_section3);
			}
			return null;
		}
	}

	public class FragmentConfig extends Fragment {
		public static final String ARG_SECTION_NUMBER = "section_number";
		SharedPreferences preferencias = getSharedPreferences("com.activities.mrfeed", Context.MODE_PRIVATE);
		public FragmentConfig() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_settings,
					container, false);
			TextView cfgTitle = (TextView) rootView.findViewById(R.id.cfgTitle);
			ListView listaFavs = (ListView) rootView
					.findViewById(R.id.listaFavs);
			cfgTitle.setText("Favoritos");

			listaFavs.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> lista, View view,
						int position, long arg3) {

					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Borrar Favorito");

					LayoutInflater inflater = getActivity().getLayoutInflater();
					View popup2 = inflater.inflate(R.layout.popup_feed, null);
					builder.setView(popup2);

					final RSSNew r = (RSSNew) lista
							.getItemAtPosition(position);
					builder.setPositiveButton("Borrar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Favs.deleteFav(r, getApplicationContext());
									r.setStar(false);

									ListView listaFavs = (ListView) rootView
											.findViewById(R.id.listaFavs);
									listaFavs.setAdapter(new ConfFavsAdapter(
											getApplicationContext(),
											R.layout.item_favs,
											Favs.getSavedFavs(getApplicationContext())));
									preferencias.edit().putInt("changes", 1).commit();
								}
							});
					builder.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					builder.show();
					return true;

				}
			});
			listaFavs.setAdapter(new ConfFavsAdapter(getApplicationContext(),
					R.layout.item_favs, Favs
							.getSavedFavs(getApplicationContext())));

			return rootView;
		}
	}

	public static class FragmentAboutUs extends Fragment {

		public FragmentAboutUs() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_aboutus,
					container, false);
			TextView auTitle = (TextView) rootView.findViewById(R.id.auTitle);
			TextView txtNosotros = (TextView) rootView.findViewById(R.id.txtNosotros);
			TextView descApp = (TextView) rootView.findViewById(R.id.descApp);
			auTitle.setText("Acerca de MrFeed");
			txtNosotros.setText(" Alberto Aparicio\n Hector González\n Álvaro Najarro");
			descApp.setText("MrFeed - Lector de noticias RSS para Android");
			
			return rootView;
		}
	}

	public class FragmentFeeds extends Fragment {
		private String uri;
		private String nombre;
		private String categoria;
		SharedPreferences preferencias = getSharedPreferences("com.activities.mrfeed", Context.MODE_PRIVATE);
		public FragmentFeeds() {
		}

		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_feeds,
					container, false);
			TextView feedsTitle = (TextView) rootView.findViewById(R.id.feedsTitle);
			TextView categoriesTitle = (TextView) rootView.findViewById(R.id.categoriesTitle);
			Button addFeed = (Button) rootView.findViewById(R.id.addFeed);
			
			RadioGroup radioOrganizarPor = (RadioGroup) rootView.findViewById(R.id.radioOrganizarPor);
			
			feedsTitle.setText("Feeds");
			categoriesTitle.setText("Filtrar por: ");
			addFeed.setText("Añadir");

			if(SavedRadio.getRadio(getApplicationContext()).equals("fc")){
				radioOrganizarPor.check(R.id.fcategorias);
			}else{
				radioOrganizarPor.check(R.id.ffeeds);
			}
			
			radioOrganizarPor.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					// TODO Auto-generated method stub
					RadioButton radioff= (RadioButton) findViewById(R.id.ffeeds);
					RadioButton radiofc= (RadioButton) findViewById(R.id.fcategorias);

					if(radioff.isChecked()){
	                  SavedRadio.saveRadio("ff", getApplicationContext());
	                  preferencias.edit().putInt("changes", 1).commit();
	                }else if(radiofc.isChecked()){
	                  SavedRadio.saveRadio("fc", getApplicationContext());
	                  preferencias.edit().putInt("changes", 1).commit();
	                }
				}
			});

			
			ListView categList = (ListView) rootView
					.findViewById(R.id.categoryList);

			categList.setAdapter(new ConfCategoryAdapter(
					getApplicationContext(), R.layout.fragment_categories,
					SavedFeeds.getCategorias(getApplicationContext())));
			addFeed.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Añadir nuevo feed");

					LayoutInflater inflater = getActivity().getLayoutInflater();
					View popup = inflater
							.inflate(R.layout.dialogoaddfeed, null);
					builder.setView(popup);

					final EditText name = (EditText) popup
							.findViewById(R.id.nameInput);
					name.requestFocus();
					final EditText input = (EditText) popup
							.findViewById(R.id.input);
					final Spinner categorias = (Spinner) popup
							.findViewById(R.id.categoria);
					input.setInputType(InputType.TYPE_CLASS_TEXT
							| InputType.TYPE_TEXT_VARIATION_URI);
					builder.setPositiveButton("Añadir",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									uri = input.getText().toString();
									nombre = name.getText().toString();
									categoria = categorias.getSelectedItem()
											.toString();

									RepoRSS r = new RepoRSS(nombre, uri,
											"desc", categoria);
									SavedFeeds.saveFeed(r,
											getApplicationContext());

									ListView feedList = (ListView) rootView
											.findViewById(R.id.feedList);
									feedList.setAdapter(new ConfFeedAdapter(
											getApplicationContext(),
											R.layout.listitem_feeds,
											SavedFeeds
													.getSavedFeeds(getApplicationContext())));
									preferencias.edit().putInt("changes", 1).commit();
								}
							});
					builder.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					builder.show();
				}
			});

			ListView feedList = (ListView) rootView.findViewById(R.id.feedList);

			feedList.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> lista, View view,
						int position, long arg3) {

					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle("Borrar feed");

					LayoutInflater inflater = getActivity().getLayoutInflater();
					View popup2 = inflater.inflate(R.layout.popup_feed, null);
					builder.setView(popup2);

					final RepoRSS r = (RepoRSS) lista
							.getItemAtPosition(position);
					builder.setPositiveButton("Borrar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									SavedFeeds.deleteFeed(r,
											getApplicationContext());

									ListView feedList = (ListView) rootView
											.findViewById(R.id.feedList);
									feedList.setAdapter(new ConfFeedAdapter(
											getApplicationContext(),
											R.layout.listitem_feeds,
											SavedFeeds
													.getSavedFeeds(getApplicationContext())));
									preferencias.edit().putInt("changes", 1).commit();
								}
							});
					builder.setNegativeButton("Cancelar",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									dialog.cancel();
								}
							});
					builder.show();
					return true;

				}
			});

			feedList.setAdapter(new ConfFeedAdapter(getApplicationContext(),
					R.layout.listitem_feeds, SavedFeeds
							.getSavedFeeds(getApplicationContext())));
			return rootView;
		}
	}

	public class ConfFeedAdapter extends ArrayAdapter<RepoRSS> {
		private ArrayList<RepoRSS> feedResources;

		public ConfFeedAdapter(Context context, int textViewResourceId,
				ArrayList<RepoRSS> feedResources) {
			super(context, textViewResourceId, feedResources);
			this.feedResources = feedResources;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.listitem_feeds, null);
			}

			RepoRSS feedR = feedResources.get(position);
			if (feedR != null) {
				TextView feedName = (TextView) v.findViewById(R.id.feedName);
				TextView feedUrl = (TextView) v.findViewById(R.id.feedUrl);

				if (feedName != null) {
					feedName.setText(feedR.getTit() + " - "
							+ feedR.getCategoria());
				}

				if (feedUrl != null) {
					feedUrl.setText(feedR.getLnk());
				}
			}
			return v;
		}
	}

	public class ConfCategoryAdapter extends ArrayAdapter<String> {
		private ArrayList<String> categorias;
		SharedPreferences preferencias = getSharedPreferences("com.activities.mrfeed", Context.MODE_PRIVATE);
		public ConfCategoryAdapter(Context context, int textViewResourceId,
				ArrayList<String> categorias) {
			super(context, textViewResourceId, categorias);
			this.categorias = categorias;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.fragment_categories, null);// //

			}
			ArrayList<String> selectedCats = SelectedCategs
					.getCategs(getApplicationContext());
			String categoria = categorias.get(position);

			if (categoria != null) {
				final CheckBox catName = (CheckBox) v
						.findViewById(R.id.checkCat);
				catName.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (catName.isChecked()) {
							SelectedCategs.saveCateg(catName.getText()
									.toString(), getApplicationContext());
							preferencias.edit().putInt("changes", 1).commit();
						} else {
							SelectedCategs.deleteCateg(catName.getText()
									.toString(), getApplicationContext());
							preferencias.edit().putInt("changes", 1).commit();
						}
					}
				});
				if (catName != null) {
					catName.setText(categoria);
					if (!selectedCats.contains(categoria)) {
						catName.setChecked(false);
					} else {
						catName.setChecked(true);
					}
				}
			}

			return v;
		}
	}

	public class ConfFavsAdapter extends ArrayAdapter<RSSNew> {
		private ArrayList<RSSNew> favs;

		public ConfFavsAdapter(Context context, int textViewResourceId,
				ArrayList<RSSNew> favs) {
			super(context, textViewResourceId, favs);
			this.favs = favs;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.item_favs, null);

			}

			RSSNew fav = favs.get(position);

			if (fav != null) {
				String nombre = fav.getTitle();
				String link = fav.getLink();
				

				final TextView favName = (TextView) v
						.findViewById(R.id.favName);
				final TextView favLink = (TextView) v
						.findViewById(R.id.favLink);
				favLink.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(Intent.ACTION_VIEW)
								.setData(Uri
										.parse(favLink.getText().toString())));
					}
				});

				if (favName != null) {
					favName.setText(nombre);
				}
				if (favLink != null) {
					favLink.setText(link);
				}
			}
			return v;
		}
	}
	
	/*public class ConfFavsAdapter extends ArrayAdapter<String> {
		private ArrayList<String> favs;

		public ConfFavsAdapter(Context context, int textViewResourceId,
				ArrayList<String> favs) {
			super(context, textViewResourceId, favs);
			this.favs = favs;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.item_favs, null);

			}

			String fav = favs.get(position);

			if (fav != null) {
				StringTokenizer st = new StringTokenizer(fav, "[[");
				String nombre = st.nextToken();
				String link = st.nextToken();
				

				final TextView favName = (TextView) v
						.findViewById(R.id.favName);
				final TextView favLink = (TextView) v
						.findViewById(R.id.favLink);
				favLink.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(Intent.ACTION_VIEW)
								.setData(Uri
										.parse(favLink.getText().toString())));
					}
				});

				if (favName != null) {
					favName.setText(nombre);
				}
				if (favLink != null) {
					favLink.setText(link);
				}
			}
			return v;
		}
	}*/
}
