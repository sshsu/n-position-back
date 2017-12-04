package sapphire.dualnback;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private SectionPageAdapter mySectionPageAdapter;
    ViewPager viewPager;
    //TabItem tabItem1, tabItem2, tabItem3;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        mySectionPageAdapter = new SectionPageAdapter(getSupportFragmentManager());

        viewPager = (ViewPager) findViewById(R.id.container);
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
}
