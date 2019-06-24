package emef4z.gmail.com.sampledataapp;

import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SampleDB delOB;
    ListView lv_sample;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         delOB = new SampleDB(getBaseContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //delOB.DeleteSampleData();
        getRemoteData(); //


        lv_sample =(ListView) findViewById(R.id.sample_list);
    }

    public void getRemoteData()
    {

        delOB.DeleteSampleData();
     final ProgressDialog pd;
        pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("Loading...");
        pd.setCancelable(false);
        pd.show();

        final String url =
                Uri.parse("http://10.0.2.2/sampleweb/sampledatajson.php").buildUpon().build().toString();
        Ion.with(getBaseContext())
                .load(url)
                .progressDialog(pd)
                .as(new TypeToken<List<SampleJson>>() {})
                .setCallback(new FutureCallback<List<SampleJson>>() {
                    @Override
                    public void onCompleted(Exception e, List<SampleJson> result) {

                        if (result != null)
                        {
                            try {
                                SampleDB DB = new SampleDB(getBaseContext());

                                for (SampleJson r : result) {
                                   String comm = DB.addSampleData(r.name, r.phone_no, r.email);
                                    Toast.makeText(getBaseContext(),comm,Toast.LENGTH_LONG).show();

                                }

                                String[] columns ={"name","phone_no"};
                                int[] labels = {android.R.id.text1,android.R.id.text2};
                                Cursor myList = DB.getSampleData();

                                SimpleCursorAdapter myAdapter = new SimpleCursorAdapter(getBaseContext(), android.R.layout.simple_expandable_list_item_2,
                                       myList, columns,labels);

                                lv_sample.setAdapter(myAdapter);


                            } catch (Exception ex) {

                                Toast.makeText(getApplicationContext(), "Error!!" + ex.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                    }
                        pd.dismiss();

                    }
                });
    }

    }

