package edu.cs.jli.slidingmusic;


import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LetterPickerArrayAdapter extends ArrayAdapter<String> {
  private final Context context;
  private final ArrayList<String> list;;
  
  static class ViewHolder {
	    public TextView tv1;
	  }

  public LetterPickerArrayAdapter(Context context, ArrayList<String> aList) {
    super(context, R.layout.songs_array_adapter, aList);
    this.context = context;
    list = aList;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    
	    View rowView = convertView;
	    // reuse views
	    if (rowView == null) {
	    	LayoutInflater inflater = LayoutInflater.from(context);
	      rowView = inflater.inflate(R.layout.letter_picker_array_adapter, null);
	      // configure view holder
	      ViewHolder viewHolder = new ViewHolder();
	      viewHolder.tv1 = (TextView) rowView.findViewById(R.id.textLetterPicker);
	      rowView.setTag(viewHolder);
	    }
	  
	    // fill data
	    ViewHolder holder = (ViewHolder) rowView.getTag();
	    holder.tv1.setText(list.get(position));

    return rowView;
  }
  
  
} 