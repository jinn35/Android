package edu.cs.jli.slidingmusic;

import java.util.concurrent.TimeUnit;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class NowPlayingFragment extends Fragment {
	
	private TextView mSongInfo;
	private ImageButton mPlayPause;
	private SeekBar mSeek;
	private TextView mLeft;
	private TextView mRight;

	public NowPlayingFragment(){}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_now_playing, container, false);
        
        mSongInfo = (TextView)rootView.findViewById(R.id.textNowPlayingInfo);
        mLeft = (TextView)rootView.findViewById(R.id.timeleft);
        mRight = (TextView)rootView.findViewById(R.id.timeright);
        mSongInfo.setText(AudioPlaybackService.songInfo);
        mPlayPause = (ImageButton)rootView.findViewById(R.id.imageButtonPlayNow);
        mSeek = (SeekBar)rootView.findViewById(R.id.seekBar1);
        
        mLeft.setText("0:00");
        mRight.setText("0:00");
        
        if(AudioPlaybackService.mediaPlayer!=null)
        {
        	
        int dur = AudioPlaybackService.mediaPlayer.getDuration();
        
        mSeek.setMax(dur);
        mSeek.postDelayed(onEverySecond, 1000);
        
        mSeek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                    boolean fromUser) {

                if(fromUser) {
                	AudioPlaybackService.mediaPlayer.seekTo(progress);
                }
            }
        });
        
        }

        mPlayPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if(AudioPlaybackService.mediaPlayer!=null)
				{
				
				if(AudioPlaybackService.mediaPlayer.isPlaying())
				{
					mPlayPause.setImageResource(R.drawable.ic_play);
					AudioPlaybackService.mediaPlayer.pause();
				}else
				{
					mPlayPause.setImageResource(R.drawable.ic_pause);
					AudioPlaybackService.mediaPlayer.start();
				}
				}
			}
		});
        
        return rootView;
    }
	
	private Runnable onEverySecond=new Runnable() {

	    @Override
	    public void run() {

	        if(mSeek != null) {
	        	int progress = AudioPlaybackService.mediaPlayer.getCurrentPosition();
	        	int dur = AudioPlaybackService.mediaPlayer.getDuration();
	            mSeek.setProgress(progress);
	            mLeft.setText(milliConv(progress));
	            mRight.setText(milliConv(dur-progress));
	        }

	        if(AudioPlaybackService.mediaPlayer.isPlaying()) {
	            mSeek.postDelayed(onEverySecond, 1000);
	        }

	    }
	};
	
	private String milliConv(int millis)
	{
		String minutes = String.valueOf(TimeUnit.MILLISECONDS.toMinutes(millis));
		String seconds = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millis) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
		
		if(seconds.length()==1)
		{
			seconds = "0" + seconds;
		}
		
		return minutes+":"+seconds;
	}
	
}
