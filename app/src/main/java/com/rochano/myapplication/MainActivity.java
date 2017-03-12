package com.rochano.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rochano.myapplication.model.Shop;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new HttpAsyncTask().execute("http://jbossas-rochano.rhcloud.com/api/shop");
    }

    public static String GET(String url){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    private class HttpAsyncTask extends AsyncTask<String, Void, List<Shop>> {

        @Override
        protected List<Shop> doInBackground(String... urls) {
            List<Shop> result = new ArrayList<Shop>() ;

            try {
                JSONArray arr = new JSONArray(GET(urls[0]));
                for (int i=0; i < arr.length(); i++) {
                    result.add(convertShop(arr.getJSONObject(i)));
                }

                return result;
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        private Shop convertShop(JSONObject obj) throws JSONException {
            String shopNameJp = obj.getString("shopNameJp");
            String shopNameEn = obj.getString("shopNameEn");
            String imageLogo = obj.getString("logoImg");

            return new Shop(shopNameJp, shopNameEn, imageLogo);
        }

        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(List<Shop> result) {
            Toast.makeText(getBaseContext(), "Received!", Toast.LENGTH_LONG).show();

            CustomList adapter = new CustomList(MainActivity.this, result);
            list = (ListView) findViewById(R.id.list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(
                        AdapterView<?> adapterView, View view, int position, long ld) {
                    TextView txtTitle = (TextView) view.findViewById(R.id.title);
                    Toast.makeText(MainActivity.this,
                            "YouClicked at " + txtTitle.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
