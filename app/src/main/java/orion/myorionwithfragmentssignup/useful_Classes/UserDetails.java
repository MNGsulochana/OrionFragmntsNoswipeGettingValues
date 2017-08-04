package orion.myorionwithfragmentssignup.useful_Classes;

import android.util.Log;

import java.io.Serializable;

/**
 * Created by sulochana on 3/8/17.
 */

public class UserDetails implements Serializable{

    public String persn_name,persn_email,personaddress,city,state,postcode,phone,gender,dob,country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPersonaddress() {
        return personaddress;
    }

    public void setPersonaddress(String personaddress) {
        this.personaddress = personaddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPersn_name() {
        return persn_name;
    }

    public void setPersn_name(String persn_name) {
        Log.d("getname",persn_name);
        this.persn_name = persn_name;
    }

    public String getPersn_email() {
        return persn_email;
    }

    public void setPersn_email(String persn_email) {
        this.persn_email = persn_email;
    }
}
