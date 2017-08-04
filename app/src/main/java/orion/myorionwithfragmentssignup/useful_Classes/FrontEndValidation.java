package orion.myorionwithfragmentssignup.useful_Classes;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import orion.myorionwithfragmentssignup.fragments.FragmentToGetPersonalDetails;

/**
 * Created by sulochana on 2/8/17.
 */

public class FrontEndValidation {

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Date date = null;
    Pattern pattern;
    Account[] account;
    String personname;

    public boolean isValidPinCode(String pincode) {
        boolean check = false;


        if (!Pattern.matches("[a-zA-Z]+", pincode)) {
            check = !(pincode.length() < 6 || pincode.length() > 6);
        }

        return check;
    }

    public boolean isValidMobileNumber(String mobile) {
        boolean check = false;


        check = Pattern.matches("^[0-9]{10,15}$", mobile);

        return check;
    }

    public boolean isEighteen(String date) {
        boolean result = false;
        try {
            Date parseddate = dateFormat.parse(date);
            Calendar c2 = Calendar.getInstance();
            c2.add(Calendar.YEAR, -18);
            if (parseddate.before(c2.getTime())) {
                result = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean isValidFullName(String fullname) {
        boolean check = false;
        if (fullname.length() > 2 && fullname.matches("[a-zA-Z]*") && fullname.length() < 16) {
            check = true;
        }

        return check;
    }

    public boolean isValidDob(String dob) {
        try {
            date = dateFormat.parse(dob);
            return dob.equals(dateFormat.format(date));

        } catch (ParseException ex) {
            ex.printStackTrace();
            return false;
        }
    }


    public void getmailid( final Context context, final Handler myHandler) {

        Thread th=new Thread()
        {
            @Override
            public void run() {
              //  super.run();

                pattern=Patterns.EMAIL_ADDRESS;
                try {
                    account = AccountManager.get(context).getAccounts();
                    Log.d(" czxc", String.valueOf(account));

                }
                catch (SecurityException e) {
                }
                finally {
                    Log.d(" czxc","nc x");

                    for (Account TempAccount : account) {
                        Log.d(" czxc","nc x1");

                    if (pattern.matcher(TempAccount.name).matches()) {
                        Log.d(" czxc","nc x2");

                        String namemail = TempAccount.name;
                        personname = namemail;
                        Log.d("getmailid", namemail);


                        String[] part = namemail.split("@");

                        if (part.length > 1) {

                            //  edit_name.setText(part[0]);
                        }
                    }

                    }
                    Message msg=Message.obtain();
                    msg.setTarget(myHandler);
                    msg.what=2;
                    Bundle b=new Bundle();
                    b.putString("myname",personname);
                    msg.setData(b);
                    msg.sendToTarget();
                }
            }
        };
        th.start();


       /* pattern= Patterns.EMAIL_ADDRESS;
        try {
            account = AccountManager.get(context).getAccounts();
        }
        catch (SecurityException e) {
        }

        for (Account TempAccount : account) {

            if (pattern.matcher(TempAccount.name).matches()) {

                String namemail=TempAccount.name;

                Log.d("getmailid",namemail);


                String[] part=namemail.split("@");

                if(part.length>1)
                {

                  //  edit_name.setText(part[0]);
                }
            }

           *//* if (!possibleEmails.isEmpty() && possibleEmails.get(0) != null) {
                String email = possibleEmails.get(0);
                String[] parts = email.split("@");

                if (parts.length > 1)
                    return parts[0];
            }*//*
        }*/
    }
}
