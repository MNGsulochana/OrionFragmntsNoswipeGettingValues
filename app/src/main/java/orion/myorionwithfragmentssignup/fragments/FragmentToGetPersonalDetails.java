package orion.myorionwithfragmentssignup.fragments;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import github.chenupt.springindicator.TabClickListener;
import orion.myorionwithfragmentssignup.R;
import orion.myorionwithfragmentssignup.activity.SignUpActivity;
import orion.myorionwithfragmentssignup.useful_Classes.FrontEndValidation;
import orion.myorionwithfragmentssignup.useful_Classes.GenericTextWatcher;
import orion.myorionwithfragmentssignup.useful_Classes.SendMessage;
import orion.myorionwithfragmentssignup.useful_Classes.UserDetails;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentToGetPersonalDetails extends Fragment implements RadioGroup.OnCheckedChangeListener,View.OnClickListener{




    @BindView(R.id.edit_name) EditText edit_name;
   // @BindView(R.id.ent_cnfmpswd) EditText confrm_pswd;
    //@BindView(R.id.ent_pswd) EditText ent_psw;
    @BindView(R.id.ent_mailid) EditText ent_mailid1;
    @BindView(R.id.ent_mobile) EditText ent_mobile;
    @BindView(R.id.button_continue) Button con;
    @BindView(R.id.button_back) Button back;
    @BindView(R.id.coordinator_layout) CoordinatorLayout coord_layout;
    @BindView(R.id.gend_female) RadioButton female_btn;
    @BindView(R.id.gend_male) RadioButton male_btn;
    @BindView(R.id.gend_other) RadioButton other_btn;
    @BindView(R.id.radio_group) RadioGroup radioGroup;
     //@BindView(R.id.dob)
    static TextView date_of_birth;

    @BindView(R.id.datepick_imagebtn) ImageButton datepick_imagbtn;

    String mail_id;
    public static final int RequestPermissionCode = 1;

    public static Pattern pattern;
    public static Account[] account;
    private static int year;
    private static int month;
    private static int day;

    ViewPager viewpager;
    TabClickListener tabClickListener;
    FrontEndValidation validation;
    UserDetails userDetails;

    SharedPreferences sp;

public static String persn_name;
    public static Context context;
    SendMessage sendMessage;

    public FragmentToGetPersonalDetails() {
        // Required empty public constructor
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            sendMessage= (SendMessage) getActivity();
          //  userDetails=new UserDetails();
        }catch (ClassCastException e)
        {
            throw  new ClassCastException("error getting adata");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v=inflater.inflate(R.layout.fragment_fragment_to_get_personal_details, container, false);
        date_of_birth=v.findViewById(R.id.dob);
        ButterKnife.bind(this,v);



        viewpager= SignUpActivity.viewPager;
        validation = new FrontEndValidation();


        radioGroup.setOnCheckedChangeListener(this);
        datepick_imagbtn.setOnClickListener(this);
        pattern= Patterns.EMAIL_ADDRESS;


        Bundle b1=getArguments();

        if(b1!=null)
        {
            userDetails= (UserDetails) b1.getSerializable("mydata");
            ent_mailid1.setText(userDetails.getPersn_email());
            edit_name.setText(userDetails.getPersn_name());

            Log.d("getnamefrg",userDetails.getPersn_name());
        }
        else
        {
            Log.d("bfbnd","fnbdn");
        }



        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

 //HERE VALIDATING THE MAIL ADDRESS


       ent_mailid1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                InputFilter filter = new InputFilter() {
                    public CharSequence filter(CharSequence source, int start, int end,
                                               Spanned dest, int dstart, int dend) {
                        for (int i = start; i < end; i++) {
                            if (Character.isWhitespace(source.charAt(i))) {
                                return "";
                            }
                        }
                        return null;
                    }

                };

                editable.setFilters(new InputFilter[]{filter});
                ent_mailid1.requestFocus();
                String emailid=ent_mailid1.getText().toString().trim();
                if (Patterns.EMAIL_ADDRESS.matcher(emailid).matches()) {
                    ent_mailid1.setError(null);

                } else {
                    ent_mailid1.setError("Not Valid Email");
                    ent_mailid1.requestFocus();

                }

                if (ent_mailid1.getText().length() > 0 && editable.subSequence(0, 1).toString().equalsIgnoreCase(" ")) {
                    ent_mailid1.setError("first letter must not be space");
                    ent_mailid1.setText("");
                    ent_mailid1.requestFocus();


                }

            }
        });

        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String fullname = edit_name.getText().toString();
                FrontEndValidation validation = new FrontEndValidation();
                if (validation.isValidFullName(fullname)) {
                    edit_name.setError(null);

                } else {
                    edit_name.setError("User name must be min 3 and max 15 \n " +
                            "and Alphabet");
                    edit_name.requestFocus();

                }

            }
        });
        ent_mobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String mobile=ent_mobile.getText().toString().trim();


                validation.isValidMobileNumber(mobile);
                if (validation.isValidMobileNumber(mobile)) {
                    ent_mobile.setError(null);

                } else {
                    ent_mobile.setError("Not Valid Number ");

                }

            }
        });

       date_of_birth.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

           }

           @Override
           public void afterTextChanged(Editable editable) {
               String dob=date_of_birth.getText().toString();


              if (!validation.isEighteen(dob)) {
                   date_of_birth.setError("Mininmum age should be 18");
                  date_of_birth.requestFocus();
               } else {
                   date_of_birth.setError(null);

               }

           }
       });

        return v;
    }

    @OnClick(R.id.button_continue)
    public void clickOnContinuePay(View v)
    {
        String gendr=null;
        int selectedId = radioGroup.getCheckedRadioButtonId();

        // find which radioButton is checked by id
        if (selectedId == female_btn.getId()) {

            gendr=female_btn.getText().toString();
        } else if (selectedId == male_btn.getId()) {
            gendr=male_btn.getText().toString();
        }

        String mailid=ent_mailid1.getText().toString().trim();
        String name=edit_name.getText().toString().trim();
      //  String paswd=ent_psw.getText().toString().trim();
      //  String cnfrm_pswd=confrm_pswd.getText().toString().trim();
        String mobile=ent_mobile.getText().toString().trim();
        String dob=date_of_birth.getText().toString().trim();


        Log.d("getnam",name);

        if(tabClickListener==null) {

            if (!name.equals("") && !mailid.equals("") && !gendr.equals("") && !mobile.equals("") && !dob.equals(""))  {

//HERE I AM GETTING THE INSTANCE OF POJO CLASS OBJECT FROM PREVIOUS FRAGMENT BECAZ WE NEED TO ADD ALL THE DATA TO THE SAME OBJECT FOR FURTHER REFERENCE

                userDetails=FragmentToGetLocation.userDetails;
                userDetails.setPersn_email(mailid);
                userDetails.setPersn_name(name);
                userDetails.setGender(gendr);
                userDetails.setPhone(mobile);
                userDetails.setDob(dob);
 //HERE I AM SENDING THE DATA AS OBJECT TO THE PARENT ACTIVIYT(SIGNUP ACTIVIYT) FROM THERE WE CAN PASS TO THE NEXT FRAGMENT BECAZ THE COMMUNICATION BETWEEN FRAGMENTS IS(FRAG1 ---> PARENT ACTIVIYT --->FRAG2)

                sendMessage.sendData(userDetails,viewpager.getCurrentItem());

//HERE I AM SWITCHING TO THE NEXT FRAGMENT

                viewpager.setCurrentItem(2);


            } else {

                Snackbar snackbar = Snackbar.make(coord_layout, "provide all the values", Snackbar.LENGTH_SHORT);
                snackbar.show();

            }
        }



    }

    @OnClick(R.id.button_back)
    public void clickOnBackPay()
    {
        if(tabClickListener ==null)
        {
            viewpager.setCurrentItem(0);
        }
    }


    @Override
    public void onClick(View view) {

        DialogFragment newFragment = new SelectDateFragment();
        newFragment.show(getFragmentManager(), "DatePicker");
        Log.d("selected12","smnbsdmn");


    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

        int check_id=radioGroup.getCheckedRadioButtonId();

        if(check_id == R.id.gend_female)
        {
            Toast.makeText(getActivity(),"female selectd",Toast.LENGTH_SHORT).show();
        }
        else if(check_id == R.id.gend_male)
        {
            Toast.makeText(getActivity(),"male selectd",Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(getActivity(),"other selectd",Toast.LENGTH_SHORT).show();
        }

    }
//WE ARE GETTING THE DATA FROM FIRST FRAGMENT
    public void displayreceivedata(UserDetails userDetails) {
        Log.d("getnmd",userDetails.getState());
    }


    @SuppressLint("ValidFragment")
    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
           // return super.onCreateDialog(savedInstanceState);
            return new DatePickerDialog(getActivity(),this,year,month,day);
        }

        @Override
        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
            Log.d("selected","smnbsdmn");
            year = selectedYear;
            month = selectedMonth+1;
            day = selectedDay;
            String s=""+day+"-"+month+"-"+year;

            date_of_birth.setText(s);
            Log.d("selecteddate",s);
        }
    }


}
