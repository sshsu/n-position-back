package sapphire.dualnback;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import java.util.Objects;

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

        spinner = view.findViewById(R.id.nValues);
        spinner.setOnItemSelectedListener(this);
        btnPlay = view.findViewById(R.id.btnPlay);
        btnPlay.setOnClickListener(v -> {
            int pos = spinner.getSelectedItemPosition();
            String spinnerValue = (String)spinner.getItemAtPosition(pos);
            n = Integer.parseInt(spinnerValue);
            System.out.println("difficulty: " + n);
            // Perform action on click
            Intent activityChangeIntent = new Intent(getContext(), PlayActivity.class);
            activityChangeIntent.putExtra("difficulty", Integer.toString(n));
            // currentContext.startActivity(activityChangeIntent);
            Objects.requireNonNull(getContext()).startActivity(activityChangeIntent);
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
