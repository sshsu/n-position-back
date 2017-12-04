package sapphire.dualnback;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ScoreListAdapter extends ArrayAdapter<String[]> {


	public ScoreListAdapter(Context context, ArrayList<String[]> elements) {
		super(context, 0, elements);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Get the data item for this position
		String[] element = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		if (convertView == null) {
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.scores_list_item, parent, false);
		}
		// Lookup view for data population
		TextView date = (TextView) convertView.findViewById(R.id.scores_list_item_date);
		TextView score = (TextView) convertView.findViewById(R.id.scores_list_item_score);
		TextView level = (TextView) convertView.findViewById(R.id.scores_list_item_level);
		// Populate the data into the template view using the data object
		date.setText(element[0]);
		score.setText(element[1]);
		level.setText(element[2]);
		// Return the completed view to render on screen
		return convertView;
	}
}