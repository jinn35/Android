package edu.cs.jli.slidingmusic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArtistsFragment extends Fragment {
	
	private ArrayList<String> list;
	private ListView listView1;
	private ArrayAdapter<String> adapter;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		setHasOptionsMenu(true);
		
        View view = inflater.inflate(R.layout.fragment_artists, container, false);
        
        list = new ArrayList<String>();
  
        listView1 = (ListView) view.findViewById(R.id.listViewArtists);
        adapter = new ArrayAdapter<String>
        	(ArtistsFragment.this.getActivity(),
         			android.R.layout.simple_list_item_1, list);

        listView1.setAdapter(adapter);
		getArtistList();
        
        return view;
    }
	
	public void getArtistList() {
		new retrieve_ArtistsTask().execute();
		}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    menu.findItem(R.id.action_letter).setVisible(false);
	    super.onCreateOptionsMenu(menu, inflater);
	} 
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar actions click
		switch (item.getItemId()) {
		case R.id.action_settings:
			return true;
		case R.id.action_letter:
		{
		//	Intent intent = new Intent(getActivity(), LetterPickerActivity.class);
        //    startActivityForResult(intent, REQUEST_CODE);
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
	protected class retrieve_ArtistsTask extends AsyncTask<Void, ArrayList<String>, ArrayList<String>> {

			@Override
			protected ArrayList<String> doInBackground(Void ...arg0) {

				ContentResolver musicResolver = getActivity().getContentResolver();
				//Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				Uri musicUri = android.provider.MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
				Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
				
				if(musicCursor!=null && musicCursor.moveToFirst()){
					  //get columns
					  int titleColumn = musicCursor.getColumnIndex
					    (android.provider.MediaStore.Audio.Artists.ARTIST);
					  int idColumn = musicCursor.getColumnIndex
					    (android.provider.MediaStore.Audio.Artists.NUMBER_OF_ALBUMS);
					  int artistColumn = musicCursor.getColumnIndex
					    (android.provider.MediaStore.Audio.Artists.NUMBER_OF_TRACKS);
					  //add songs to list
					  do {
				//	    long thisId = musicCursor.getLong(idColumn);
					    String thisTitle = musicCursor.getString(titleColumn);
					    String thisArtist = musicCursor.getString(idColumn);
					    list.add(thisTitle+" - ("+thisArtist+")");
					  }
					  while (musicCursor.moveToNext());
					}
				
				Collections.sort(list, new Comparator<String>(){
					  public int compare(String a, String b){
					    return a.compareTo(b);
					  }
					});
				return list;
			}

			protected void onPostExecute(ArrayList<String> result) {
				super.onPostExecute(result);
				listView1.setAdapter(adapter);
				adapter.notifyDataSetChanged();
		    }

		  }

}
