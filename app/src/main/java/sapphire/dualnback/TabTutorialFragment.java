package sapphire.dualnback;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class TabTutorialFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        /* Glide.with(this)
                .load(R.drawable.tutorial)
                .into(image);*/
        return inflater.inflate(R.layout.tab_tutorial_fragment, container, false);
    }
}
