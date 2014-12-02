package edu.cs.jli.slidingmusic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Fragment;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class SongsFragment extends Fragment{
	
	private ArrayList<Song> songList;
	private ArrayList<String> titles;
	private ArrayList<String> artists;
	private ArrayList<String> testList;
	private ArrayList<Integer> letterIndex;
	private LetterPickerArrayAdapter adapter3;
	private ListView listView1;
	private ImageButton mPrevious;
	private ImageButton mPlayPause;
	private ImageButton mNext;
	private int length;
	private int currentPosition;
	
	public static final int REQUEST_CODE = 1;
	
	public SongsFragment(){}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_songs, container, false);
        
        setHasOptionsMenu(true);
        
        final Intent serviceIntent = new Intent(getActivity(), AudioPlaybackService.class);
        
        String[] alpha=new String[]{"#","A","B","C","D","E","F","G","H","I","J","K","L",
        		"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z","{}{}{}{}"};
        
        songList = new ArrayList<Song>();
        titles = new ArrayList<String>();
        artists = new ArrayList<String>();
        
        testList = new ArrayList<String>();
        
        mNext = (ImageButton) rootView.findViewById(R.id.imageButtonNextSongs);
        mPrevious = (ImageButton) rootView.findViewById(R.id.imageButtonPreviousSongs);
        mPlayPause = (ImageButton) rootView.findViewById(R.id.imageButtonPlayPauseSongs);
        
        letterIndex = new ArrayList<Integer>();
        letterIndex.add(0);
        
        getSongList();
        
        int index = 1;
        for(int i = 0; i < songList.size(); i++) {
        	titles.add(songList.get(i).getTitle());
        	if (songList.get(i).getTitle().startsWith(alpha[index]))
        	{
        		letterIndex.add(i);
        		index++;
        	}
        	artists.add(songList.get(i).getArtist());
        	testList.add(songList.get(i).getTitle()+" - "+songList.get(i).getArtist());
        }
        
    	listView1 = (ListView) rootView.findViewById(R.id.listView1);
    	
    	adapter3 = new LetterPickerArrayAdapter(getActivity(), testList);
        listView1.setAdapter(adapter3);
    	
    	
   // 	adapter = new ArrayAdapter<String>
   //	(SongsFragment.this.getActivity(),
   // 			android.R.layout.simple_list_item_1, titles);

   // 	listView1.setAdapter(adapter);
	
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {     	
            	
        //      player.reset();
            	
              currentPosition = position;
              
              final String item = (String) parent.getItemAtPosition(position);
              Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
              
              long currSong = songList.get(position).getID();

      	      Uri trackUri = ContentUris.withAppendedId(
      		  android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,currSong);
              
              String x = trackUri.toString();
              //if x = internet url, can play media stored online
              serviceIntent.putExtra(AudioPlaybackService.EXTRA_AUDIO_URL, x);
              serviceIntent.putExtra("SONG_INFO", item);
              SongsFragment.this.getActivity().startService(serviceIntent);
              
              mPlayPause.setImageResource(R.drawable.ic_pause);
            }

          });
        
        mPrevious.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				SongsFragment.this.getActivity().stopService(serviceIntent);
				currentPosition--;
					
				String toShow = testList.get(currentPosition);
				Toast.makeText(getActivity(), toShow, Toast.LENGTH_LONG).show();
					
				try{
					long currSong = songList.get(currentPosition).getID();
		      	      
					Uri trackUri = ContentUris.withAppendedId(
		      	      		  android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,currSong);
					String x = trackUri.toString();
		            serviceIntent.putExtra(AudioPlaybackService.EXTRA_AUDIO_URL, x);
		            serviceIntent.putExtra("SONG_INFO", toShow);
		            SongsFragment.this.getActivity().startService(serviceIntent);
		      	                       
		      	}catch(Exception e)
		      	    {
		      	        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
		      	    }
				
		      	    mPlayPause.setImageResource(R.drawable.ic_pause);
			}
		});
        
        mNext.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				SongsFragment.this.getActivity().stopService(serviceIntent);
				currentPosition++;
				
				String toShow = testList.get(currentPosition);
				Toast.makeText(getActivity(), toShow, Toast.LENGTH_LONG).show();
				
				try{
				long currSong = songList.get(currentPosition).getID();
	      	      
				Uri trackUri = ContentUris.withAppendedId(
	      	      		  android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,currSong);
				String x = trackUri.toString();
	            serviceIntent.putExtra(AudioPlaybackService.EXTRA_AUDIO_URL, x);
	            serviceIntent.putExtra("SONG_INFO", toShow);
	            SongsFragment.this.getActivity().startService(serviceIntent);
	      	                       
	      	    }catch(Exception e)
	      	    {
	      	        Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
	      	    }
	      	        mPlayPause.setImageResource(R.drawable.ic_pause);
			}
		});
        
        mPlayPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				if(AudioPlaybackService.mediaPlayer.isPlaying())
				{
					mPlayPause.setImageResource(R.drawable.ic_play);
					length = AudioPlaybackService.mediaPlayer.getCurrentPosition();
					AudioPlaybackService.mediaPlayer.pause();
				}else
				{
					mPlayPause.setImageResource(R.drawable.ic_pause);
					AudioPlaybackService.mediaPlayer.seekTo(length);
					AudioPlaybackService.mediaPlayer.start();
				}
			}
		});
        	return rootView;
    }
	
	public void getSongList() {
		
		ContentResolver musicResolver = getActivity().getContentResolver();
		Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
		Cursor musicCursor = musicResolver.query
				(musicUri, null, MediaStore.Audio.Media.IS_MUSIC + " = 1", null, null);
		
		if(musicCursor!=null && musicCursor.moveToFirst()){
			  //get columns
			  int titleColumn = musicCursor.getColumnIndex
			    (android.provider.MediaStore.Audio.Media.TITLE);
			  int idColumn = musicCursor.getColumnIndex
			    (android.provider.MediaStore.Audio.Media._ID);
			  int artistColumn = musicCursor.getColumnIndex
			    (android.provider.MediaStore.Audio.Media.ARTIST);
			  int albumColumn = musicCursor.getColumnIndex
				(android.provider.MediaStore.Audio.Media.ALBUM);
			  int durationColumn = musicCursor.getColumnIndex
						(android.provider.MediaStore.Audio.Media.DURATION);
			  int sizeColumn = musicCursor.getColumnIndex
						(android.provider.MediaStore.Audio.Media.SIZE);
			  int yearColumn = musicCursor.getColumnIndex
						(android.provider.MediaStore.Audio.Media.YEAR);
			  //add songs to list
			  do {
			    long thisId = musicCursor.getLong(idColumn);
			    String thisTitle = musicCursor.getString(titleColumn);
			    String thisArtist = musicCursor.getString(artistColumn);
			    String thisAlbum = musicCursor.getString(albumColumn);
			    String thisDuration = musicCursor.getString(durationColumn);
			    String thisSize = musicCursor.getString(sizeColumn);
			    String thisYear = musicCursor.getString(yearColumn);
			    songList.add(new Song(thisId,thisTitle,thisArtist,thisAlbum,
			    		thisDuration,thisSize,thisYear));
			  }
			  while (musicCursor.moveToNext());
			}
		
		Collections.sort(songList, new Comparator<Song>(){
			  public int compare(Song a, Song b){
			    return a.getTitle().compareTo(b.getTitle());
			  }
			});

		}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        
		try {
            	super.onActivityResult(requestCode, resultCode, data);

                String requiredValue = data.getStringExtra("key");
              //  Toast.makeText(getActivity(), requiredValue,
                  //      Toast.LENGTH_LONG).show();
                listView1.setSelection(letterIndex.get(Integer.parseInt(requiredValue)));
        } catch (Exception ex) {
            Toast.makeText(getActivity(), ex.toString(),
                    Toast.LENGTH_LONG).show();
        }

    }

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	    menu.findItem(R.id.action_letter).setVisible(true);
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
			Intent intent = new Intent(getActivity(), LetterPickerActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
		}
		default:
			return super.onOptionsItemSelected(item);
		}
	}	

}
