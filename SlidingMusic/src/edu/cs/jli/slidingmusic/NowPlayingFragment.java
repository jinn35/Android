package edu.cs.jli.slidingmusic;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NowPlayingFragment extends Fragment {
	

	public NowPlayingFragment(){}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_now_playing, container, false);
        return rootView;
    }
	
}
