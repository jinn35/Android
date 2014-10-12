package edu.cs.jli.slidingmusic;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;


public class LetterPickerActivity extends Activity {

	private ArrayAdapter<String> adapter;
	private GridView gridView1;
	private ArrayList<String> letters;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_letter_picker);
		
		String[] alpha=new String[]{"#","A","B","C","D","E","F","G","H","I","J","K","L",
        		"M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        
        letters = new ArrayList<String>();

        for(int i = 0; i < alpha.length; i++)
        {
        	letters.add(alpha[i]);
        }
        
    	adapter = new LetterPickerArrayAdapter(LetterPickerActivity.this, letters);
        
  //      adapter = new ArrayAdapter<String>(getApplicationContext(),
  //      		android.R.layout.simple_list_item_1, letters);

        gridView1 = (GridView) findViewById(R.id.gridViewLetterPickerActivity);
        gridView1.setAdapter(adapter);
        
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
            	
            	Intent intent = getIntent();
            	intent.putExtra("key", String.valueOf(position));
            	setResult(RESULT_OK, intent);
            	finish();
            }

          });
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.letter_picker, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
