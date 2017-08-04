package orion.myorionwithfragmentssignup.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import orion.myorionwithfragmentssignup.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toget_signup)
    Button toget_signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.toget_signup)
    public void  toGetSignUP()
    {
        Intent in=new Intent(MainActivity.this,SignUpActivity.class);
        startActivity(in);
    }
}
