package sapphire.dualnback;

import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    //TabItem tabItem1, tabItem2, tabItem3;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        init();

        viewPager = findViewById(R.id.container);
        setUpViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager){
        SectionPageAdapter spa = new SectionPageAdapter(getSupportFragmentManager());
        spa.addFragment(new TabPlayFragment(), "Play");
        spa.addFragment(new TabTutorialFragment(), "Tutorial");
        spa.addFragment(new TabScoresFragment(), "Scores");
        viewPager.setAdapter(spa);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void init(){
    }
    public void resetOnClick(View view) {}
}
