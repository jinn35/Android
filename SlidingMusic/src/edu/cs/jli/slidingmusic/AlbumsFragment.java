package edu.cs.jli.slidingmusic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
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
			  }
			  while (musicCursor.moveToNext());
			}
		
		Collections.sort(list, new Comparator<Album>(){
			  public int compare(Album a, Album b){
			    return a.getTitle().compareTo(b.getTitle());
			  }
			});

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
}
