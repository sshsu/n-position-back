package sapphire.dualnback;

import android.app.ActionBar;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

/**
 * Created by skyler on 12/2/2017.
 */

public class TabPlayFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    Button btnPlay;
    int n;
    Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_play_fragment, container, false);

        //ActionBar actionBar = getActivity().getActionBar();
        //actionBar.hide();

        spinner = (Spinner) view.findViewById(R.id.nValues);
        spinner.setOnItemSelectedListener(this);
        btnPlay = (Button) view.findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                int pos = spinner.getSelectedItemPosition();
                String spinnerValue = (String)spinner.getItemAtPosition(pos);
                n = Integer.parseInt(spinnerValue.toString());
                System.out.println("difficulty: " + n);
                // Perform action on click
                Intent activityChangeIntent = new Intent(getContext(), PlayActivity.class);
                activityChangeIntent.putExtra("difficulty", Integer.toString(n));
                // currentContext.startActivity(activityChangeIntent);

                getContext().startActivity(activityChangeIntent);
            }
        });

        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    adapterView.getItemAtPosition(i);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
