package com.example.herbalapps.Activity;

import androidx.appcompat.app.AppCompatActivity;
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
import com.example.herbalapps.Adapter.AdapterTanaman;
import com.example.herbalapps.Kelas.ClassHerbal;
import com.example.herbalapps.Kelas.ClassTanaman;
import com.example.herbalapps.Kelas.ConfigApi;
import com.example.herbalapps.R;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TanamanActivity extends AppCompatActivity {
    ProgressDialog loading;
    private RecyclerView recyclerView;
    AdapterTanaman adapterTanaman;
    List<ClassTanaman> listTanaman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanaman);
        setTitle("Data Tanaman Herbal");

        listTanaman = new ArrayList<ClassTanaman>();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_viewTanaman);
        adapterTanaman = new AdapterTanaman(TanamanActivity.this,listTanaman);

        recyclerView.setLayoutManager(new LinearLayoutManager(TanamanActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterTanaman);

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

        loading = ProgressDialog.show(TanamanActivity.this, "Mohon Tunggu...", "Sedang Proses...", false, false);

        String url = ConfigApi.TanamanApi;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
//                Toast.makeText(TanamanActivity.this,response,Toast.LENGTH_LONG).show();
                showTanaman(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(TanamanActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(TanamanActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showTanaman(String response){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        list.clear();
        adapterTanaman.notifyDataSetChanged();
        try {
            jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("result");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);
                String id_tanaman = jo.getString("id_tanaman");
                String nama_tanaman = jo.getString("nama_tanaman");
                String foto_tanaman = jo.getString("foto_tanaman");

                ClassTanaman tanaman = new ClassTanaman(
                        id_tanaman,
                        nama_tanaman,
                        foto_tanaman
                );
                listTanaman.add(tanaman);
                adapterTanaman.notifyDataSetChanged();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(TanamanActivity.this
                    ,"Data Salah "+e,Toast.LENGTH_LONG).show();
        }

    }

}