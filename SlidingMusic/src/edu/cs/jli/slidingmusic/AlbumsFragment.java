package edu.cs.jli.slidingmusic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.cs.jli.slidingmusic.ArtistsFragment.retrieve_ArtistsTask;

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

public class AlbumsFragment extends Fragment {
	
	private ArrayList<Album> list;
	private ListView listView1;
	private ArrayAdapter<String> adapter;
	private ArrayList<String> list2;
	
	public AlbumsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
		setHasOptionsMenu(true);
		
        View rootView = inflater.inflate(R.layout.fragment_albums, container, false);
        
        list = new ArrayList<Album>();
        list2 = new ArrayList<String>();
        
        getAlbumList();
        
        for(int i = 0; i < list.size(); i++) {
        	list2.add(list.get(i).getTitle()+" - "+list.get(i).getArtist());
        }     
        
        listView1 = (ListView) rootView.findViewById(R.id.listView1Albums);
        adapter = new ArrayAdapter<String>
        	(AlbumsFragment.this.getActivity(),
         			android.R.layout.simple_list_item_1, list2);

         listView1.setAdapter(adapter);
        
        return rootView;
    }
	
	public void getAlbumList() {
		new retrieve_AlbumsTask().execute();
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
	
	protected class retrieve_AlbumsTask extends AsyncTask<Void, ArrayList<String>, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(Void ...arg0) {

			ContentResolver musicResolver = getActivity().getContentResolver();
			//Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
			Uri musicUri = android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
			Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);
			
			if(musicCursor!=null && musicCursor.moveToFirst()){
				  //get columns
				  int titleColumn = musicCursor.getColumnIndex
				    (android.provider.MediaStore.Audio.Albums.ALBUM);
				  int idColumn = musicCursor.getColumnIndex
				    (android.provider.MediaStore.Audio.Albums.ALBUM_KEY);
				  int artistColumn = musicCursor.getColumnIndex
				    (android.provider.MediaStore.Audio.Albums.ARTIST);
				  int aColumn = musicCursor.getColumnIndex
						    (android.provider.MediaStore.Audio.Albums.NUMBER_OF_SONGS);
				  //add songs to list
				  do {
				    long thisId = musicCursor.getLong(idColumn);
				    String thisTitle = musicCursor.getString(titleColumn);
				    String thisArtist = musicCursor.getString(artistColumn);
				    list.add(new Album(thisId, thisTitle, thisArtist));
				    list2.add(thisTitle+" - "+thisArtist);
				  }
				  while (musicCursor.moveToNext());
				}
			
			Collections.sort(list2, new Comparator<String>(){
				  public int compare(String a, String b){
				    return a.compareTo(b);
				  }
				});
			
		//	Collections.sort(list, new Comparator<Album>(){
		//		  public int compare(Album a, Album b){
		//		    return a.getTitle().compareTo(b.getTitle());
		//		  }
		//		});
			return list2;
		}

		protected void onPostExecute(ArrayList<String> result) {
			super.onPostExecute(result);
			listView1.setAdapter(adapter);
			adapter.notifyDataSetChanged();
	    }

	  }
}
