package com.example.volleyjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;
    String[] id;
    String[] firstName;
    String[] gender;
    String[] mail;
    String[] phoneH;
    String[] phoneO;
    String[] mobile;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mTextViewResult = findViewById(R.id.text_view_result);

        mQueue = Volley.newRequestQueue(this);

        jsonParse(mobile);

    }

    private void jsonParse(String[] mobile) {

        phoneH=new String[10000];
        phoneO=new String[10000];
        this.mobile =new String[10000];
        firstName=new String[10000];
        id=new String[10000];
        gender=new String[10000];
        mail=new String[10000];

        String url = "https://run.mocky.io/v3/b8b15165-0686-47f3-b55c-340bce926dc4";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("users");
                            for ( i = 0; i < jsonArray.length(); i++) {
                                JSONObject employee = jsonArray.getJSONObject(i);

                                JSONObject addressObject = employee.getJSONObject("contact");
                                phoneH[i] = addressObject.getString("home");
                                phoneO[i] = addressObject.getString("office");
                                MainActivity.this.mobile[i] = addressObject.getString("mobile");
                                firstName[i] = employee.getString("name");
                                id[i] = employee.getString("id");
                                gender[i] = employee.getString("gender");
                                mail[i] = employee.getString("email");

                                Button toast = findViewById(R.id.button_parse);
                                final String phH=phoneH[i];
                                final String phO=phoneO[i];

                                toast.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        Toast.makeText(getApplicationContext(), phH, Toast.LENGTH_SHORT).show();
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable()
                                        {
                                            @Override
                                            public void run()
                                            {
                                                Toast.makeText(MainActivity.this, phO, Toast.LENGTH_SHORT).show();
                                            }
                                        }, 2000);


                                    }
                                });

                                mTextViewResult.append(id[i]+", "+firstName[i] + ", " + gender[i] + ", " + mail[i] + ", " + phoneH[i] + ", " + phoneO[i] + "\n\n");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mQueue.add(request);
    }

}