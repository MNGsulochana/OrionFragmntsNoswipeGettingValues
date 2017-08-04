package orion.myorionwithfragmentssignup.fragments;


import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import github.chenupt.springindicator.TabClickListener;
import orion.myorionwithfragmentssignup.R;
import orion.myorionwithfragmentssignup.activity.SignUpActivity;
import orion.myorionwithfragmentssignup.toget_location.GetCoordinates;
import orion.myorionwithfragmentssignup.toget_location.LocationAddress;
import orion.myorionwithfragmentssignup.toget_location.LocationResult;
import orion.myorionwithfragmentssignup.toget_location.MyLocation;
import orion.myorionwithfragmentssignup.useful_Classes.FrontEndValidation;
import orion.myorionwithfragmentssignup.useful_Classes.SendMessage;
import orion.myorionwithfragmentssignup.useful_Classes.UserDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentToGetLocation extends Fragment implements View.OnClickListener{


    EditText ent_city, ent_country, ent_address;
    EditText ent_pincode, ent_state;
    Button contin_loc;
    CoordinatorLayout location_cordnate_layout;

    ViewPager viewpager;
    EditText et;
    TabClickListener tabClickListener;



    public static UserDetails userDetails;



    SendMessage sendMessage;

    public FragmentToGetLocation() {
        // Required empty public constructor
    }
//HERE WE ARE GETTING THE INTERFACE FOR TEHE PARENT ACTIVIYT TO COMUNICATE
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            sendMessage= (SendMessage) getActivity();
        }catch (ClassCastException e)
        {
            throw  new ClassCastException("error getting adata");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_fragment_to_get_location, container, false);

        ent_country = v.findViewById(R.id.entr_country);
        ent_city = v.findViewById(R.id.entr_city);
        ent_address = v.findViewById(R.id.entr_address);
        ent_pincode = v.findViewById(R.id.entr_pincode);
        ent_state = v.findViewById(R.id.entr_state);
        contin_loc=v.findViewById(R.id.ent_btn_loctn);

      //  location_cordnate_layout=v.findViewById(R.id.loctn_coord_layout);
        //  ButterKnife.bind(getActivity(), v);



        viewpager= SignUpActivity.viewPager;
        userDetails=new UserDetails();
//GETTING THE VALUES FROM PARENT ACTIVIYT AFTER GRANTING PERMISSIONS
        Bundle b1=getArguments();
        if(b1!=null)
        {
            UserDetails usd= (UserDetails) b1.getSerializable("mydata");
            Log.d("bnfbdn","fnbdn");
            ent_address.setText(usd.getPersonaddress());
            ent_city.setText(usd.getCity());
            ent_country.setText(usd.getCountry());
            ent_pincode.setTag("my value");
            ent_pincode.setText(usd.getPostcode());
            ent_state.setText(usd.getState());
            ent_pincode.setTag(null);
        }
        else
        {
            Log.d("bnfbdn1","fnbdn");
        }


//BASED ON USER PINCODE GETTING THE ADDRESS
        ent_pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (ent_pincode.getTag() == null) {

                    final String pincode=ent_pincode.getText().toString().trim();

                    FrontEndValidation validation = new FrontEndValidation();
                    validation.isValidPinCode(pincode);
                    ent_pincode.clearComposingText();
                    if (validation.isValidPinCode(pincode)) {
                        ent_pincode.getError();
                        ent_pincode.setError(null);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                new GetCoordinates(getActivity(), ent_address, ent_state, ent_pincode, ent_city, ent_country,new Geocodehand() ).execute("" + pincode);


                            }
                        });


                    } else {
                        ent_pincode.setError("Not Valid Pin");

                    }


                }

            }
        });

        contin_loc.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View view) {

        String entpin=ent_pincode.getText().toString().trim();

        String entcity=ent_city.getText().toString().trim();

        String entaddres=ent_address.getText().toString().trim();

        String entstate=ent_state.getText().toString().trim();

        String entcuntry=ent_country.getText().toString().trim();

        if(tabClickListener==null) {

            if (entaddres.equals("") || entcity.equals("") || entpin.equals("") || entstate.equals("") || entcuntry.equals("")) {
                Toast.makeText(getActivity(), "pleae fill the value", Toast.LENGTH_SHORT).show();

            } else {

                userDetails.setCity(entcity);
                userDetails.setPostcode(entpin);
                userDetails.setPersonaddress(entaddres);
                userDetails.setCountry(entcuntry);
                userDetails.setState(entstate);

 //SENDING THE DATA TO THE PARENT ACTIVIYT     (FRAGMENT1 ---> PARENT ACTIVIYT ---> FRAGMENT2)

                sendMessage.sendData(userDetails,viewpager.getCurrentItem());

 //SETTING THE REQUIRED FRAGMENT TO THE VIEWPAGER

                viewpager.setCurrentItem(1);


            }

        }
/*

//REALM PART ITS WORKING FINE
              */
/*  ++id;
                setRealmData(entcity,entstate,id);

            // refresh the realm instance
            RealmController.with(this).refresh();*//*
*/

    }




    private class Geocodehand extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress, city = null, state = null, postalcode = null, country = null, mainaddress;
            switch (message.what) {
                case 0:
                    Bundle bundle = message.getData();


                    mainaddress = bundle.getString("mainaddress1");
                    city = bundle.getString("city1");
                    country = bundle.getString("country1");

                    state = bundle.getString("state1");

                    Log.d("getadre", mainaddress);
                    break;

                default:
                    mainaddress = null;
            }
            ent_address.setText(mainaddress);
            ent_city.setText(city);
            ent_country.setText(country);

            //   ent_pincode.setText(postalcode);
            ent_state.setText(state);
        }


    }

}
