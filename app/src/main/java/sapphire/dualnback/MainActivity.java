package sapphire.dualnback;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    private SectionPageAdapter mySectionPageAdapter;
    ViewPager viewPager;
    TabItem tabItem1, tabItem2, tabItem3;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
