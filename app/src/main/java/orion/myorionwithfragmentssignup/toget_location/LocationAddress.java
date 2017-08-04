package orion.myorionwithfragmentssignup.toget_location;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class LocationAddress {
    private static final String TAG = "LocationAddress";

    static String address1,city,state,country,postalCode,knownName;


    public static void getAddressFromLocation(final double latitude, final double longitude,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                String result = null;

                try {
                    List<Address> addressList = geocoder.getFromLocation(
                            latitude, longitude, 1);
                    if (addressList != null && addressList.size() > 0) {
                        Address address = addressList.get(0);


                        city = address.getLocality();
                         state = address.getAdminArea();
                         country = address.getCountryName();
                        postalCode = address.getPostalCode();





                        StringBuilder sb = new StringBuilder();
                        for (int i = 0; i < address.getMaxAddressLineIndex()-1; i++) {
                            sb.append(address.getAddressLine(i)).append("\n");
                            Log.d("addressline",address.getAddressLine(i));
                           // Log.d("addressline",);
                            //it will returns whole address like block,street name,street,which road
                        }

                        result = sb.toString();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Unable connect to Geocoder", e);
                } finally {
                    Message message = Message.obtain();
                    message.setTarget(handler);
                    if (result != null) {
                        message.what = 1;
                        Bundle bundle = new Bundle();

                        bundle.putString("mainaddress",result);
                        bundle.putString("city",city);
                        bundle.putString("country",country);
                        bundle.putString("postalcode",postalCode);
                        bundle.putString("knownname",knownName);
                        bundle.putString("state",state);
                        message.setData(bundle);
                    } else {
                        message.what = 1;
                        Bundle bundle = new Bundle();

                        bundle.putString("mainaddress",result);
                        bundle.putString("city",city);
                        bundle.putString("country",country);
                        bundle.putString("postalcode",postalCode);

                        bundle.putString("state",state);

                        message.setData(bundle);
                    }
                    message.sendToTarget();
                }
            }
        };
        thread.start();
    }
}