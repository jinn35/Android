package edu.cs.jli.slidingmusic;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
 
public class AudioPlaybackService extends Service implements OnPreparedListener, OnCompletionListener
{
    final int NOTIFICATION_ID = 1;
    public static final String EXTRA_AUDIO_URL = "audio_url";
    public static final String 
        BROADCAST_PLAYBACK_STOP = "stop",
        BROADCAST_PLAYBACK_PAUSE = "pause",
        BROADCAST_PLAYBACK_PREVIOUS = "previous",
        BROADCAST_PLAYBACK_NEXT = "next",
        BROADCAST_PLAYBACK_PLAY = "play";
 
    public static MediaPlayer mediaPlayer;
    String url;
    Uri u;
    private String songInfo = "Song - Artist";
 
    final BroadcastReceiver broadcastReceiver = new BroadcastReceiver()
    {        
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
 
            if (action.equals(BROADCAST_PLAYBACK_STOP))
            {
            	stopSelf();
            }
            else if (action.equals(BROADCAST_PLAYBACK_PAUSE)) 
            {
                if (mediaPlayer.isPlaying()) 
                {
                	mediaPlayer.pause();
                }
                else 
                {
               // 	mediaPlayer.start();
                }
            }else if (action.equals(BROADCAST_PLAYBACK_PLAY)) 
            {
                if (mediaPlayer.isPlaying()) 
                {
                //	mediaPlayer.pause();
                }
                else 
                {
                	mediaPlayer.start();
                }
            }
        }
    };
 
    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {        
      //  this.url = intent.getStringExtra(EXTRA_AUDIO_URL);
     //  Log.d("URL", url);
    	Uri myUri = Uri.parse(intent.getStringExtra(EXTRA_AUDIO_URL));
    	songInfo = intent.getStringExtra("SONG_INFO");
    	//this.u = intent.get
 
        mediaPlayer.reset();
        try
        {
            mediaPlayer.setDataSource(getApplicationContext(), myUri);
            mediaPlayer.prepareAsync();
 
            showNotification();
        } 
        catch (Exception e)
        {
            e.printStackTrace();
        } 
 
        return START_STICKY;
    }   
 
    @Override
    public void onCreate()
    {
        super.onCreate();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnCompletionListener(this);
 
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_PLAYBACK_STOP);
        intentFilter.addAction(BROADCAST_PLAYBACK_PAUSE);
        intentFilter.addAction(BROADCAST_PLAYBACK_PLAY);
        registerReceiver(broadcastReceiver, intentFilter);
    }
 
    @Override
    public void onCompletion(MediaPlayer mp)
    {
        stopSelf();
    }
 
    @Override
    public void onPrepared(MediaPlayer mp)
    {
        Log.d("Service", "MediaPlayer prepared. Music will play now.");
        mediaPlayer.start();
    }
 
    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }
 
    private PendingIntent makePendingIntent(String broadcast)
    {
        Intent intent = new Intent(broadcast);
        return PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
    }
 
    private void showNotification()
    {
        // Create notification
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.drawable.ic_launcher)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(songInfo) 
            .setContentIntent(PendingIntent.getActivity(getApplicationContext(), 0, new Intent(getApplicationContext(), MainActivity.class), 0))
            .addAction(R.drawable.ic_pause_light, "Pau.", makePendingIntent(BROADCAST_PLAYBACK_PAUSE))
            .addAction(R.drawable.ic_play_light, "Play", makePendingIntent(BROADCAST_PLAYBACK_PLAY))
            .addAction(R.drawable.ic_remove_light, "Stop", makePendingIntent(BROADCAST_PLAYBACK_STOP));
 
        // Show notification
        startForeground(NOTIFICATION_ID, notificationBuilder.build());
    }
 
    @Override
    public void onDestroy() // called when the service is stopped
    {
        mediaPlayer.stop();
        stopForeground(true);
        unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
    
 
}