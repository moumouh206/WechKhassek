package com.mba2dna.wechkhassek.activity;


        import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mba2dna.wechkhassek.R;
import com.mba2dna.wechkhassek.constants.Constants;
        import com.mba2dna.wechkhassek.util.DatabaseHandler;
        import com.mba2dna.wechkhassek.util.UserFunctions;

import me.relex.circleindicator.CircleIndicator;


public class TutorialActivity extends FragmentActivity {
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    TextView PassBtn;
    UserFunctions userFunctions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        db.addTutorial();
        mDemoCollectionPagerAdapter =
                new DemoCollectionPagerAdapter(
                        getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        CircleIndicator defaultIndicator = (CircleIndicator) findViewById(R.id.indicator_custom);
        defaultIndicator.setViewPager(mViewPager);
        Typeface tf = Typeface.createFromAsset(getAssets(),
                Constants.NexaBold);
        Typeface tl = Typeface.createFromAsset(getAssets(),
                Constants.NexaLight);

        PassBtn = (TextView) findViewById(R.id.PassBtn);
        PassBtn.setTypeface(tf);
        PassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userFunctions = new UserFunctions();
                if (userFunctions.isUserLoggedIn(getApplicationContext())) {
                    finish();
                    Intent myIntent = new Intent(TutorialActivity.this,
                            MainActivity.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myIntent);
                }else{
                    finish();
                    Intent myIntent = new Intent(TutorialActivity.this,
                            LoginActivity.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myIntent);
                }

            }
        });
    }

    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {
        public DemoCollectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new tut1Fragment();
            switch (i) {
                case 0:
                    fragment = new tut1Fragment();
                    break;
                case 1:
                    fragment = new tut2Fragment();
                    break;
                case 2:
                    fragment = new tut3Fragment();
                    break;
                case 3:
                    fragment = new tut4Fragment();
                    break;

            }
            //  Fragment fragment = new tut1Fragment();
           /* Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(tut1Fragment.ARG_OBJECT, i + 1);
            fragment.setArguments(args);*/
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }


    // Instances of this class are fragments representing a single
// object in our collection.
    public static class tut1Fragment extends Fragment {
        TextView Tl, Disc;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.

            View rootView = inflater.inflate(
                    R.layout.fragment_tut1, container, false);
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                    Constants.NexaBold);
            Typeface tl = Typeface.createFromAsset(getActivity().getAssets(),
                    Constants.NexaLight);
            Tl = (TextView) rootView.findViewById(R.id.Tl);
            Tl.setTypeface(tf);
            Disc = (TextView) rootView.findViewById(R.id.Disc);
            Disc.setTypeface(tl);
            return rootView;
        }
    }

    public static class tut2Fragment extends Fragment {
        TextView Tl, Disc;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.
            View rootView = inflater.inflate(
                    R.layout.fragment_tut2, container, false);
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                    Constants.NexaBold);
            Typeface tl = Typeface.createFromAsset(getActivity().getAssets(),
                    Constants.NexaLight);

            Tl = (TextView) rootView.findViewById(R.id.Tl);
            Tl.setTypeface(tf);
            Disc = (TextView) rootView.findViewById(R.id.Disc);
            Disc.setTypeface(tl);
            return rootView;
        }
    }
    public static class tut3Fragment extends Fragment {
        TextView Tl, Disc;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.

            View rootView = inflater.inflate(
                    R.layout.fragment_tut3, container, false);
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                    Constants.NexaBold);
            Typeface tl = Typeface.createFromAsset(getActivity().getAssets(),
                    Constants.NexaLight);
            Tl = (TextView) rootView.findViewById(R.id.Tl);
            Tl.setTypeface(tf);
            Disc = (TextView) rootView.findViewById(R.id.Disc);
            Disc.setTypeface(tl);
            return rootView;
        }
    }
    public static class tut4Fragment extends Fragment {
        TextView Tl, Disc;

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container, Bundle savedInstanceState) {
            // The last two arguments ensure LayoutParams are inflated
            // properly.

            View rootView = inflater.inflate(
                    R.layout.fragment_tut4, container, false);
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),
                    Constants.NexaBold);
            Typeface tl = Typeface.createFromAsset(getActivity().getAssets(),
                    Constants.NexaLight);
            Tl = (TextView) rootView.findViewById(R.id.Tl);
            Tl.setTypeface(tf);
            Disc = (TextView) rootView.findViewById(R.id.Disc);
            Disc.setTypeface(tl);
            return rootView;
        }
    }
}
