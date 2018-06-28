package com.sail.mappingsound.mappingsound;

import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;

import com.sail.mappingsound.mappingsound.model.RecordItem;

public class MainActivity extends AppCompatActivity implements OnListFragmentInteractionListener{

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Button navigationHistoryButton;
    private Button navigationRecordButton;


    private ViewPager mViewPager;
    private Fragment fragments[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        navigationHistoryButton = (Button) findViewById(R.id.navigation_history);
        navigationRecordButton = (Button) findViewById(R.id.navigation_record);
        setupClickListener();

        fragments = new Fragment[2];
        fragments[0] = new NavigationRecord();
        fragments[1] = new NavigationHistory();
    }
  
    public void setupClickListener(){
        navigationRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(0);
            }
        });

        navigationHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(1);
            }
        });
    }

    @Override
    public void onListFragmentInteraction(RecordItem item) {

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position == 0)
                return fragments[0];
            else return fragments[1];
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }
    }

    NavigationHistory getNavigationHistoryFragment(){
        return (NavigationHistory) fragments[1];
    }
}
