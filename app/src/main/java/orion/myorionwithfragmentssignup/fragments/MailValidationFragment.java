package orion.myorionwithfragmentssignup.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import github.chenupt.springindicator.TabClickListener;
import orion.myorionwithfragmentssignup.R;
import orion.myorionwithfragmentssignup.activity.PaymentPayUActivity;
import orion.myorionwithfragmentssignup.activity.SignUpActivity;
import orion.myorionwithfragmentssignup.useful_Classes.UserDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class MailValidationFragment extends Fragment {

    @BindView(R.id.togoback)
    Button togo_back;
   // @BindView(R.id.tosubmit)
    Button tobe_submit;
    ViewPager viewPager;
    TabClickListener tabClickListener;
    Bundle b1;


    public MailValidationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_mail_validation, container, false);
        tobe_submit=v.findViewById(R.id.tosubmit);
        ButterKnife.bind(this,v);
        b1=new Bundle();
        viewPager= SignUpActivity.viewPager;

        tobe_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//HERE WE ARE PASSING THE WHOLE DATA AS A BUNDLE TO THE NEXT ACTIVIYT

                Intent in=new Intent(getActivity(), PaymentPayUActivity.class);
                in.putExtra("mine",b1);
                startActivity(in);

            }
        });
        return v;
    }

    //WE ARE SWITCHING TO THE PREVIOUS FRAGMENT
    @OnClick(R.id.togoback)
    public void togoBack(View v)
    {
        if(tabClickListener ==null)
        {
            viewPager.setCurrentItem(1);
        }
    }

 //FROM ALL THE FRAGMENTS WE ARE GETTING THE DATA
    public void displayreceivedata1(UserDetails userDetails) {
        Log.d("getstate",userDetails.getState());
        Log.d("getname",userDetails.getPersn_name());

//HERE WE ARE SERIALIZING THE OBJECT TO THE BUNDLE
        b1.putSerializable("my_data",userDetails);

    }
}
