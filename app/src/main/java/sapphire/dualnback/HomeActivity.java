package sapphire.dualnback;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Skyler on 10/17/17.
 */

public class HomeActivity extends AppCompatActivity {
    String profileName;
    String profileEmail;
    String userID;
    //TODO configure the onCreate() method to assign these values with the extra information passed gby the intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

    }

    public void signOut() {

    }

}
