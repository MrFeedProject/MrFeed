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

<<<<<<< HEAD
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
=======
public class MainActivity extends FragmentActivity {

	private LayoutInflater mInflater;
	private List<RSSNew> data;////////////////////////////////
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
     * will keep every loaded fragment in memory. If this becomes too memory
     * intensive, it may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.Hello
     */
    //SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       //ListView list= 
//        list.
//        GridLayout general=new GridLayout(this);
//        for (int i = 0; i < 5; i++) {
//			new LinearLayout(this);
//			general.
//		}
        setContentView(R.layout.activity_main);  
        
        mInflater = (LayoutInflater) getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        
        XMLFeedsParser xmlparser=new XMLFeedsParser();
        //try{
        	/////////////////////data = xmlparser.getNoticias("");
        //}catch(Exception e){
        	//System.out.println("Hola");
        //}
        data = xmlparser.getNoticias("");//////////////////
        
//        RSSNew rss = null;
//        RSSNew rssa = null;
//        data = new ArrayList<RSSNew>();
//        rss = new RSSNew("T�tulo", "Noticia blublublu ble ble ble", "www.link.com", null);
//        rssa = new RSSNew("T�tuloddd", "Noticia blublublu ble ble bldddddde", "www.link.com", null);
//        data.add(rss);
//        data.add(rssa);
        
        ListView list= (ListView) findViewById(R.id.listView1);
   
       
//        ArrayList<String> lista = new ArrayList<String>();
//        lista.add(rss.getTitle());
//        lista.add(rssa.getTitle());
        
        //RSSNew rss;
//        for (int i = 0; i < data.size(); i++) {
//        	
//		}
        
        final ArrayAdapter<RSSNew> adapter = new ArrayAdapter<RSSNew>(this, android.R.layout.simple_list_item_1, data);

        list.setAdapter(adapter);
       

    }
    
//    public void onListItemClick(ListView parent, View v, int position, long id) {
//        CustomAdapter adapter = (CustomAdapter) parent.getAdapter();
//    	RowData row = adapter.getItem(position);		
//        Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle(row.mItem); 
//        builder.setMessage(row.mDescription + " -> " + position );
//        builder.setPositiveButton("ok", null);
//        builder.show();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private class MyArrayAdapter extends ArrayAdapter<RSSNew> {
    	
    	private Context context;

    	HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public MyArrayAdapter(Context context, int textViewResourceId,
            ArrayList<RSSNew> items) {
          super(context, textViewResourceId, items);
          this.context = context;
//          for (int i = 0; i < items.size(); ++i) {
//            mIdMap.put(items.get(i), i);
//          }
        }
        
//        public View getView(int position, View convertView, ViewGroup parent) {
//            View view = convertView;
//            
//            if (view == null) {
//                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(android.R.layout.simple_list_item_1, null);
//            }
//
//            RSSNew item = getItem(position);
//            if (item!= null) {
//                // My layout has only one TextView
//                //ListView listView = (ListView) view.findViewById(R.id.listView1);
//                
//            	TextView itemView = (TextView) view.findViewById(R.id.listView1);
////                TextView itemView = new TextView(context);
//                
//                if (itemView != null) {
//                    // do whatever you want with your string and long
//                    itemView.setText(String.format("%s %s", "Mis huevos", item.getDescription()));
//                }
//             }
//            
//            return view;
//        }

//        @Override
//        public long getItemId(int position) {
//        	String item = getItem(position);
//          return mIdMap.get(item);
//          //return mIdMap.get(position);
//        }
//
//        @Override
//        public boolean hasStableIds() {
//          return true;
//        }

      }
    
//    	public CustomAdapter(Context context, int resource, int textViewResourceId, List<RSSNew> objects) {
//    		super(context, resource, textViewResourceId, objects);
//
//    	}
//
//    	@Override
//    	public View getView(int position, View convertView, ViewGroup parent) {
//    		Viewer holder = null;
//
//    		//widgets displayed by each item in your list
//    		TextView item = null;
//    		TextView description = null;
//
//    		//data from your adapter
//    		RSSNew rssNew= getItem(position);
//
//
//    		//we want to reuse already constructed row views...
//    		if(null == convertView){
//    			convertView = mInflater.inflate(R.layout.custom_row, null);
//    			holder = new Viewer(convertView);
//    			convertView.setTag(holder);
//    		}
//    		// 
//    		holder = (Viewer) convertView.getTag();
//    		item = holder.getItem();
//    		item.setText(rssNew.mItem);
//
//    		description = holder.getDescription();		
//    		description.setText(rssNew.mDescription);
//
//    		return convertView;
//    	}
 //   }
    
//    private class Viewer {      
//        private View mRow;
//        private TextView description = null;
//        private TextView item = null;
//
//    	public Viewer(View row) {
//        	mRow = row;
//    	}
//
//    	public TextView getDescription() {
//    		if(null == description){
//    			description = (TextView) mRow.findViewById(R.id.description);
//    		}
//    		return description;
//    	}
//
//    	public TextView getItem() {
//    		if(null == item){
//    			item = (TextView) mRow.findViewById(R.id.item);
//    		}
//    		return item;
//    	}    	
//    }


//    /**
//     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
//     * one of the sections/tabs/pages.
//     */
//    public class SectionsPagerAdapter extends FragmentPagerAdapter {
//
//        public SectionsPagerAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
//            // getItem is called to instantiate the fragment for the given page.
//            // Return a DummySectionFragment (defined as a static inner class
//            // below) with the page number as its lone argument.
//            Fragment fragment = new DummySectionFragment();
//            Bundle args = new Bundle();
//            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
//            fragment.setArguments(args);
//            return fragment;
//        }
//
//        @Override
//        public int getCount() {
//            // Show 3 total pages.
//            return 3;
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            Locale l = Locale.getDefault();
//            switch (position) {
//                case 0:
//                    return getString(R.string.title_section1).toUpperCase(l);
//                case 1:
//                    return getString(R.string.title_section2).toUpperCase(l);
//                case 2:
//                    return getString(R.string.title_section3).toUpperCase(l);
//            }
//            return null;
//        }
//    }
//
//    /**
//     * A dummy fragment representing a section of the app, but that simply
//     * displays dummy text.
//     */
//    public static class DummySectionFragment extends Fragment {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        public static final String ARG_SECTION_NUMBER = "section_number";
//
//        public DummySectionFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_main_dummy, container, false);
//            TextView dummyTextView = (TextView) rootView.findViewById(R.id.section_label);
//            dummyTextView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
//            return rootView;
//        }
//    }

}
>>>>>>> 495d40328ba8e1896a492caf554a9a23ff0cd10d
