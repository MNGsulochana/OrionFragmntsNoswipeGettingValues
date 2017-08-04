package orion.myorionwithfragmentssignup.activity;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import github.chenupt.springindicator.TabClickListener;
import orion.myorionwithfragmentssignup.R;
import orion.myorionwithfragmentssignup.fragments.FragmentToGetLocation;
import orion.myorionwithfragmentssignup.toget_location.LocationAddress;
import orion.myorionwithfragmentssignup.toget_location.LocationResult;
import orion.myorionwithfragmentssignup.toget_location.MyLocation;
import orion.myorionwithfragmentssignup.useful_Classes.FrontEndValidation;
import orion.myorionwithfragmentssignup.useful_Classes.UserDetails;


//THIS ACTIVITY FOR TO TEST TEXTWACHER FOR DIFFERENT VIEWS

public class SampleActivity extends AppCompatActivity implements LocationResult{

    @BindView(R.id.one)
    Button one1;
    @BindView(R.id.two) EditText two1;
    @BindView(R.id.three) EditText three1;
    @BindView(R.id.coord_layout)
    CoordinatorLayout cord_layout;

    private MyLocation myLocation;

    private static final String[] INITIAL_PERMS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_PHONE_STATE
    };

    private static final int INITIAL_REQUEST = 13;
    private static final int INITIAL_REQUEST1 = 14;

    double latitude, longitude;

    String pinc;
    boolean check_to = true;

    ViewPager viewpager;
    EditText et;
    TabClickListener tabClickListener;

    Pattern pattern;
    Account[] account;
    UserDetails userDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        ButterKnife.bind(this);

       /* one1.addTextChangedListener(new GenericTextWatcher1(this,one1));
        two1.addTextChangedListener(new GenericTextWatcher1(this,two1));
        three1.addTextChangedListener(new GenericTextWatcher1(this,three1));*/

       myLocation =new MyLocation();
        userDetails=new UserDetails();
        togetpermission();



    }

    private void togetpermission() {

        if (!canAccessLocation() || !canAccessCoreLocation()) {
            //requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
            Log.d("gettinglocation", "shvdsh");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
            }

        } else {
            Log.d("gettingnw", "shvdsh");
            boolean networkPresent = myLocation.getLocation(SampleActivity.this, this);
            if (!networkPresent) {
                showSettingsAlert();
            }
        }
    }

    private void showSettingsAlert() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(INITIAL_PERMS,INITIAL_REQUEST);
                        }
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private boolean canAccessLocation() {



        return (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION));
    }

    private boolean canAccessCoreLocation() {

        return (hasPermission(Manifest.permission.ACCESS_COARSE_LOCATION));
    }

    private boolean hasPermission(String perm) {

        return (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(SampleActivity.this, perm));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case INITIAL_REQUEST:
                if (grantResults.length > 0) {

                    boolean GetAccountPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;

                    Log.d("gramtacnt",""+GetAccountPermission);
                    boolean ReadPhoneStatePermission = grantResults[3] == PackageManager.PERMISSION_GRANTED;

                    if (canAccessLocation() && canAccessCoreLocation() && GetAccountPermission && ReadPhoneStatePermission) {

                     getmailid();
                      one1.setOnClickListener(new View.OnClickListener() {
                          @Override
                          public void onClick(View view) {
                              Intent in=new Intent(SampleActivity.this,SignUpActivity.class);
                              in.putExtra("mydata",userDetails);
                              startActivity(in);
                          }
                      });

                        boolean networkPresent = myLocation.getLocation(SampleActivity.this, this);
                        if (!networkPresent) {
                            showSettingsAlert();
                        }

                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(this,"enable the permissions",Toast.LENGTH_SHORT).show();
                        Snackbar.make(cord_layout,"Enable the permissions",Snackbar.LENGTH_INDEFINITE).setAction("GRANT", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(INITIAL_PERMS, INITIAL_REQUEST);
                                }

                            }
                        }).show();
                    }
                }


                break;

        }
    }

    @Override
    public void getLocation(Location location) {

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        final String result = "Latitude: " + location.getLatitude() +
                " Longitude: " + location.getLongitude();
        Log.d("whole_result", result);

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //  show_text.setText(result);

                Log.d("thrd_lat", "" + latitude);
                Log.d("thrd_lng", "" + longitude);
                LocationAddress locationAddress = new LocationAddress();
                locationAddress.getAddressFromLocation(latitude, longitude,
                        getApplicationContext(), new GeocoderHandler());
            }
        });

    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress, city = null, state = null, postalcode = null, country = null, mainaddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();


                    mainaddress = bundle.getString("mainaddress");
                    city = bundle.getString("city");
                    country = bundle.getString("country");
                    postalcode = bundle.getString("postalcode");

                    state = bundle.getString("state");
                    userDetails.setCity(city);
                    userDetails.setCountry(country);
                    userDetails.setPersonaddress(mainaddress);
                    userDetails.setPostcode(postalcode);
                    userDetails.setState(state);


                    break;

                default:
                    mainaddress = null;
            }


           /* ent_address.setText(mainaddress);
            ent_city.setText(city);
            ent_country.setText(country);
            ent_pincode.setTag("my value");
            ent_pincode.setText(postalcode);
            ent_state.setText(state);
            ent_pincode.setTag(null);*/

        }
    }

    public  void getmailid() {

        pattern= Patterns.EMAIL_ADDRESS;

        Log.d("getmailid","sgdgh");

        try {
            account = AccountManager.get(this).getAccounts();
        }
        catch (SecurityException e) {
        }

        for (Account TempAccount : account) {

            if (pattern.matcher(TempAccount.name).matches()) {

                String namemail=TempAccount.name;

                Log.d("getmailid",namemail);
                userDetails.setPersn_email(namemail);
               // sp_et.putString("personname",namemail);

                // ent_mailid1.setText(namemail);
                String[] part=namemail.split("@");

                if(part.length>1)
                {
                    userDetails.setPersn_name(part[0]);
                   // sp_et.putString("personmail",part[0]);
                    //  edit_name.setText(part[0]);
                }
            }

           /* if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
                String email = possibleEmails.get(0);
                String[] parts = email.split("@");

                if (parts.length > 1)
                    return parts[0];
            }*/
        }

    }

    private class GenericTextWatcher1 implements TextWatcher
    {
        private View view;
        Context context;

        GenericTextWatcher1(Context context,View view)
        {
            this.context=context;
            this.view=view;
        }
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

            String text = editable.toString();//from here we are getting the string

            Log.d("getrf",text);
            switch(view.getId()){
               /* case R.id.one:

                    String df=one1.getText().toString();

                   FrontEndValidation validation=new FrontEndValidation();
                    validation.isValidMobileNumber(df);
                    if (validation.isValidMobileNumber(df)) {
                        one1.setError(null);

                    } else {
                        one1.setError("Not Valid Number ");

                    }

                    break;*/
                case R.id.two:

                    two1.setError("valid");

                    break;
                case R.id.three:

                    Toast.makeText(context,"get three",Toast.LENGTH_SHORT).show();

                    break;
            }

        }
    }
}
