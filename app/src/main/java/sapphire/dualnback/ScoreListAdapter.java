package sapphire.dualnback;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class ScoreListAdapter extends ArrayAdapter<String[]> {
	public ScoreListAdapter(Context context, ArrayList<String[]> elements) {
		super(context, 0, elements);
	}
	@NonNull
	@Override
	public View getView(int position, View convertView, @NonNull ViewGroup parent) {
		// Get the data item for this position
		String[] element = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.scores_list_item, parent, false);
		}
		//Get textviews to populate
		TextView dateTV = convertView.findViewById(R.id.scores_list_item_date);
		TextView scoreTV = convertView.findViewById(R.id.scores_list_item_score);
		TextView levelTV = convertView.findViewById(R.id.scores_list_item_level);
		// Populate the data into the template view using the data object

		assert element != null;
		dateTV.setText(element[0]);
		Log.e("adaptDate", element[0]);
		scoreTV.setText(element[1]);
		Log.e("adaptScore", element[1]);
		levelTV.setText(element[2]);
		Log.e("adaptLevel", element[2]);
		// Return the completed view to render on screen
		return convertView;
	}
}