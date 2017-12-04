package sapphire.dualnback;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static sapphire.dualnback.R.layout.scores_list_item;

public class TabScoresFragment extends Fragment {
	//ScoreListAdapter sla;
	String[] projection = {
			DualProvider.COL_DATE_TIME,
			DualProvider.COL_SCORE,
			DualProvider.COL_LEVEL};
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tab_scores_fragment, container, false);

		//get date, score and level from database in String form, add each to
		ArrayList<String[]> sList = new ArrayList<>();
		Cursor cursor = getContext().getContentResolver().query(DualProvider.CONTENT_URI,projection,null,null,"_ID DESC");
		if(cursor.getCount() > 0) {
			cursor.moveToFirst();
			for(int i = 0; i < cursor.getCount(); i++) {
				String[] scoreElement = new String[3];
				scoreElement[0] = cursor.getString(0);
				scoreElement[1] = String.valueOf(cursor.getInt(1));
				scoreElement[2] = String.valueOf(cursor.getInt(2));
				sList.add(scoreElement);
				cursor.moveToNext();
				for(int j = 0; j < 3; j++)
					Log.e(String.valueOf(j), scoreElement[j]);

			}
		}
		ListView listView = view.findViewById(R.id.list_view_scores);
		ScoreListAdapter scoreAdapt = new ScoreListAdapter(getContext(), sList);
		listView.setAdapter(scoreAdapt);
		return view;
	}
}
