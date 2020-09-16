package com.example.herbalapps.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.herbalapps.Adapter.AdapterHerbal;
import com.example.herbalapps.Kelas.ClassHerbal;
import com.example.herbalapps.Kelas.ConfigApi;
import com.example.herbalapps.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HerbalActivity extends AppCompatActivity {
    ProgressDialog loading;
    private RecyclerView recyclerView;
    AdapterHerbal adapterHerbal;
    List<ClassHerbal> listHerbal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herbal);
        setTitle("Data Herbal");

        listHerbal = new ArrayList<ClassHerbal>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_viewHerbal);
        adapterHerbal = new AdapterHerbal(HerbalActivity.this,listHerbal);

        recyclerView.setLayoutManager(new LinearLayoutManager(HerbalActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterHerbal);

        if (checkInternet()){
            getData();
        } else {
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "No Internet Connection !", Snackbar.LENGTH_LONG)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            finish();
                        }
                    })
                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                    .show();
        }
    }

    public boolean checkInternet(){
        boolean connectStatus = true;
        ConnectivityManager ConnectionManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=ConnectionManager.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()==true ) {
            connectStatus = true;
        }
        else {
            connectStatus = false;
        }
        return connectStatus;
    }

    private void getData() {

        loading = ProgressDialog.show(HerbalActivity.this, "Mohon Tunggu...", "Sedang Proses...", false, false);

        String url = ConfigApi.HerbalApi;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
//                Toast.makeText(HerbalActivity.this,response,Toast.LENGTH_LONG).show();
                showHerbal(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(HerbalActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(HerbalActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showHerbal(String response){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        list.clear();
        adapterHerbal.notifyDataSetChanged();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id_herbal = jo.getString("id_obat");
                String nama_obat = jo.getString("nama_obat");
                String foto_obat = jo.getString("foto_obat");
                String cara = jo.getString("cara");
                String manfaat = jo.getString("manfaat");
                String dosis = jo.getString("dosis");

                ClassHerbal herbal = new ClassHerbal(
                        id_herbal,
                        nama_obat,
                        foto_obat,
                        cara,
                        manfaat,
                        dosis
                );
                listHerbal.add(herbal);
                adapterHerbal.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(HerbalActivity.this
                    ,"Data Salah "+e,Toast.LENGTH_LONG).show();
        }

    }

}