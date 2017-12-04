package sapphire.dualnback;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
		//Get textviews to populate
		TextView dateTV = (TextView) convertView.findViewById(R.id.scores_list_item_date);
		TextView scoreTV = (TextView) convertView.findViewById(R.id.scores_list_item_score);
		TextView levelTV = (TextView) convertView.findViewById(R.id.scores_list_item_level);
		// Populate the data into the template view using the data object

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