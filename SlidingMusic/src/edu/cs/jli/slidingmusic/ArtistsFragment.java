package edu.cs.jli.slidingmusic;

import android.app.Fragment;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class ArtistsFragment extends Fragment {
	
	private TextView mTextView;
	static MediaPlayer mPlayer;
	private Button buttonPlay;
	private Button buttonStop;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View view = inflater.inflate(R.layout.fragment_artists, container, false);
        
        final Intent serviceIntent = new Intent(getActivity(), AudioPlaybackService.class);
        		
        final String MEDIA_PATH = new String(Environment.getExternalStorageDirectory().
        		getPath());
        mTextView = (TextView) view.findViewById(R.id.txtLabel);
        mTextView.setText(MEDIA_PATH);
        buttonPlay = (Button) view.findViewById(R.id.button1);
        
        buttonPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {

				serviceIntent.putExtra(AudioPlaybackService.EXTRA_AUDIO_URL, "http://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");
                ArtistsFragment.this.getActivity().startService(serviceIntent);
				
			}
		});
        
		buttonStop = (Button) view.findViewById(R.id.button2);
		buttonStop.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ArtistsFragment.this.getActivity().stopService(serviceIntent);
			}
		});
        
        return view;
    }

}
