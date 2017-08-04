package orion.myorionwithfragmentssignup.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.common.collect.Lists;

import java.util.List;

import github.chenupt.multiplemodel.viewpager.ModelPagerAdapter;
import github.chenupt.multiplemodel.viewpager.PagerModelManager;
import github.chenupt.springindicator.SpringIndicator;
import github.chenupt.springindicator.viewpager.ScrollerViewPager;
import orion.myorionwithfragmentssignup.R;
import orion.myorionwithfragmentssignup.fragments.FragmentToGetLocation;
import orion.myorionwithfragmentssignup.fragments.FragmentToGetPersonalDetails;
import orion.myorionwithfragmentssignup.fragments.MailValidationFragment;
import orion.myorionwithfragmentssignup.useful_Classes.SendMessage;
import orion.myorionwithfragmentssignup.useful_Classes.UserDetails;

public class SignUpActivity extends AppCompatActivity implements SendMessage{

   public static ScrollerViewPager viewPager;
    UserDetails usddetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        viewPager= (ScrollerViewPager) findViewById(R.id.view_pager);

        SpringIndicator springIndicator = (SpringIndicator) findViewById(R.id.indicator);

        usddetails= (UserDetails) getIntent().getSerializableExtra("mydata");
        if(usddetails!=null)
        {

            Log.d("vbn","bnn");
          //  PagerModelManager manager = new PagerModelManager();
            //  manager.addCommonFragment(GuideFragment.class,getBgRes(),getTitles() );

            // manager.addCommonFragment(getfragments(),getTitles());
           // manager.addCommonFragment(getfragments(),getTitles());
          //  ModelPagerAdapter adapter = new ModelPagerAdapter(getSupportFragmentManager(), manager);
            MyFragmentAdapter adapter=new MyFragmentAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
            viewPager.fixScrollSpeed();
            viewPager.setAllowedSwipeDirection1(ScrollerViewPager.SwipeDirectoin.none);


            // just set viewPager
            springIndicator.setViewPager(viewPager);
        }
        else
        {
            Log.d("vbn","gettingnull");
        }



    }

    private List<? extends Fragment> getfragments() {
        return Lists.newArrayList(new FragmentToGetLocation(),new FragmentToGetPersonalDetails(),new MailValidationFragment());
    }




    private List<String> getTitles(){
        return Lists.newArrayList("STEP 1", "STEP 2", "STEP 3");
    }

 //OVERRIFDING THE INTERFACE METHOD TO COMMUNICATE FRAGMENTS TO SHARE DATA

    @Override
    public void sendData(UserDetails userDetails,int pos) {
        if(pos==0) {
            String tag = "android:switcher:" + R.id.view_pager + ":" + 1;

            FragmentToGetPersonalDetails fdp = (FragmentToGetPersonalDetails) getSupportFragmentManager().findFragmentByTag(tag);
            fdp.displayreceivedata(userDetails);
        }
        else
        {
            if(pos ==1)
            {
                String tag = "android:switcher:" + R.id.view_pager + ":" + 2;
                MailValidationFragment mf= (MailValidationFragment) getSupportFragmentManager().findFragmentByTag(tag);
                mf.displayreceivedata1(userDetails);
            }
        }
    }

    class MyFragmentAdapter extends FragmentPagerAdapter{

        String title[]={"1","2","3"};
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position)
            {
                case 0:
//PASSING THE VALUES TO THE FRAGMENT
                    FragmentToGetLocation fgt1= new FragmentToGetLocation();
                    Bundle b1=new Bundle();
                    b1.putSerializable("mydata",usddetails);
                    fgt1.setArguments(b1);

                    return fgt1;
                case 1:
                    FragmentToGetPersonalDetails fgt=new FragmentToGetPersonalDetails();
                    Bundle b=new Bundle();
                    b.putSerializable("mydata",usddetails);
                    fgt.setArguments(b);
                    return fgt;
                case 2:
                    return new MailValidationFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return title.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
        }
    }
}
