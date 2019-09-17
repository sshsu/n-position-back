package sapphire.dualnback;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

import java.util.Objects;

public class ResultFrag extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
        builder.setView(R.layout.result_fragment)
                .setPositiveButton("BLANK", (dialog, id) -> {

                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}