package orion.myorionwithfragmentssignup.useful_Classes;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;

import orion.myorionwithfragmentssignup.R;

/**
 * Created by sulochana on 3/8/17.
 */

public class GenericTextWatcher  implements TextWatcher{

    private View view;
    Context context;

    public GenericTextWatcher(Context context, View view)
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
        switch(view.getId()) {
            case R.id.ent_mailid: {

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
                view.requestFocus();


                String emailid =view.toString();

                Log.d("gettext",emailid);

               /* if (Patterns.EMAIL_ADDRESS.matcher(emailid).matches()) {


                } else {
                    ent_mailid.setError("Not Valid Email");
                    view.requestFocus();

                }
*/

               /* if (Patterns.EMAIL_ADDRESS.matcher(emailid).matches()) {
                    ent_mailid.setError(null);

                } else {
                    ent_mailid.setError("Not Valid Email");
                    ent_mailid.requestFocus();

                }

                if (ent_mailid.getText().length() > 0 && editable.subSequence(0, 1).toString().equalsIgnoreCase(" ")) {
                    ent_mailid.setError("first letter must not be space");
                    ent_mailid.setText("");
                    ent_mailid.requestFocus();


                }*/
                break;
            }
        }

    }
}
