package edu.cs.jli.slidingmusic;


import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<String> {
  private final Context context;
  private final ArrayList<String> artistList;
  private final ArrayList<String> songList;
  
  static class ViewHolder {
	    public TextView tv1;
	    public TextView tv2;
	  }

  public MySimpleArrayAdapter(Context context, ArrayList<String> aList, ArrayList<String> sList) {
    super(context, R.layout.songs_array_adapter, aList);
    this.context = context;
    artistList = aList;
    songList = sList;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    
	    View rowView = convertView;
	    // reuse views
	    if (rowView == null) {
	    	LayoutInflater inflater = LayoutInflater.from(context);
	      rowView = inflater.inflate(R.layout.songs_array_adapter, null);
	      // configure view holder
	      ViewHolder viewHolder = new ViewHolder();
	      viewHolder.tv1 = (TextView) rowView.findViewById(R.id.textArtist);
	      viewHolder.tv2 = (TextView) rowView.findViewById(R.id.textSong);
	      rowView.setTag(viewHolder);
	    }
	  
	    // fill data
	    ViewHolder holder = (ViewHolder) rowView.getTag();
	    holder.tv1.setText(songList.get(position)+" - ");
	    holder.tv2.setText(artistList.get(position));

    return rowView;
  }
  
  
} 
