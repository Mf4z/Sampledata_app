package emef4z.gmail.com.sampledataapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import static android.R.attr.name;
import static android.R.attr.publicKey;

public class Sampling extends AppCompatActivity {

    EditText txtname,txtage;
    Button submit,show_sample;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sampling);

        txtname = (EditText) findViewById(R.id.name_et);
        txtage = (EditText) findViewById(R.id.age_et);
        submit =(Button) findViewById(R.id.submit_btn);
        show_sample = (Button) findViewById(R.id.showsample_btn);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = txtname.getText().toString();
                String age = txtage.getText().toString();
                final ProgressDialog pd;
                pd = new ProgressDialog(Sampling.this);
                pd.setMessage("Sending Data...");
                pd.setCancelable(false);
                pd.show();
                final String url;

                url = Uri.parse("http://10.0.2.2/sampleweb/data_insertion.php").buildUpon()
                        .appendQueryParameter("myname",name)
                        .appendQueryParameter("myage",age)
                        .build().toString();

                Ion.with(getBaseContext())
                        .load(url)
                        .progressDialog(pd)
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {

                                try
                                {
                                    Toast.makeText(getBaseContext(),result,Toast.LENGTH_SHORT).show();
                                }
                                catch (Exception ex)
                                {
                                    Toast.makeText(getBaseContext(),"Error! " + ex.getMessage() + "No connectivity",Toast.LENGTH_SHORT).show();
                                }
                                pd.dismiss();
                            }
                        });
            }
        });



    }

    public void move(View view)
    {
        Intent myIntent = new Intent(getBaseContext(),MainActivity.class);
        startActivity(myIntent);
    }

}
