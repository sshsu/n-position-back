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
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class TabScoresFragment extends Fragment {
	ScoreListAdapter sla;
	String[] projection = {
			DualProvider.COL_DATE_TIME,
			DualProvider.COL_SCORE,
			DualProvider.COL_LEVEL};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_scores_fragment, container, false);

        //get date, score and level from database in String form, add each to
		List<String[]> sList = new ArrayList<>();
        Cursor cursor = getContext().getContentResolver().query(DualProvider.CONTENT_URI,projection,null,null,"_ID DESC");
		if(cursor.getCount() > 0) {
			cursor.moveToFirst();
			for(int i = 0; i < cursor.getCount(); i++) {
				String[] scoreElement = new String[3];
				scoreElement[0] = cursor.getString(0);
				scoreElement[1] = String.valueOf(cursor.getInt(1));
				scoreElement[2] = String.valueOf(cursor.getString(2))
				cursor.moveToNext();
			}
		}

		ListView listView = view.findViewById(R.id.list_view_scores);
        return view;
    }
}
