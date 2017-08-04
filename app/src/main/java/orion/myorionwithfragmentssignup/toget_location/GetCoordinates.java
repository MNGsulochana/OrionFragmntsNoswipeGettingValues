package orion.myorionwithfragmentssignup.toget_location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by root on 15/7/17.
 */

public class GetCoordinates extends AsyncTask<String,Void,String>
{
   // ProgressDialog dialog = new ProgressDialog();
    public static String lat,lng,modify;
    Context context;
    Handler handler;
    EditText entadr,entcity,entstate,entcntry,entpincode;
    boolean checkto;
    StringBuilder sb;
    String city,state,country,postalCode;
   /* public GetCoordinates(Context context,Handler geocoderHandler)
    {
        this.context=context;
        handler=geocoderHandler;
    }*/

    public GetCoordinates(Context context,EditText entadr,EditText entcity,EditText entstate,EditText entcntry,EditText entpincode,Handler geocoderHandler)
    {
        this.context=context;
        handler=geocoderHandler;
        this.entadr=entadr;
        this.entcity=entcity;
        this.entcntry=entcntry;
        this.entstate=entstate;
        this.entpincode=entpincode;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        /*dialog.setMessage("Please wait....");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();*/
    }

    @Override
    protected String doInBackground(String... strings) {
        String response;
        try{
            String address = strings[0];
            Log.d("httpadre",address);
            HttpDataHandler http = new HttpDataHandler();
            String url = String.format("https://maps.googleapis.com/maps/api/geocode/json?address=%s",address);
            response = http.getHTTPData(url);
            return response;
        }
        catch (Exception ex)
        {

        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        try{
            JSONObject jsonObject = new JSONObject(s);

            lat = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").get("lat").toString();
            lng = ((JSONArray)jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry")
                    .getJSONObject("location").get("lng").toString();

          getLocationDetails();

//HERE WE ARE GETTING THE PLACE ID

            String placeid=((JSONArray)jsonObject.get("results")).getJSONObject(0).getString("place_id");

            Log.d("getcordPLACEID",placeid);
            Log.d("getcord",lat);
            Log.d("getcord_lang",lng);

            /*txtCoord.setText(String.format("Coordinates : %s / %s ",lat,lng));

            if(dialog.isShowing())
                dialog.dismiss();*/

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void  getLocationDetails() {

        Thread thread=new Thread()
        {
            public void run()
            {
                double latitude= Double.parseDouble(lat);
                double longitude= Double.parseDouble(lng);

                Geocoder geocoder = new Geocoder(context, Locale.getDefault());

                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocation(latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);

                        sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex() - 1; i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                            Log.d("addressline_modify", address.getAddressLine(i));

                            //it will returns whole address like block,street name,street,which road
                        }

                        city = address.getLocality();
                        state = address.getAdminArea();
                        country = address.getCountryName();
                        postalCode = address.getPostalCode();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    Message msg = handler.obtainMessage();
                    msg.what = 0;
                    Bundle bundle = new Bundle();

                    bundle.putString("mainaddress1", sb.toString());
                    bundle.putString("city1", city);
                    bundle.putString("state1", state);
                    bundle.putString("country1", country);
                    bundle.putString("postalcode1",postalCode);
                    //  bundle.putString("mainaddress1", sb.toString());
                    msg.setData(bundle);
                    msg.sendToTarget();
                }

            }
        };
        thread.start();

    }
}


