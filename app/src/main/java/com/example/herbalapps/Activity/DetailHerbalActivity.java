package com.example.herbalapps.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.herbalapps.Kelas.ClassHerbal;
import com.example.herbalapps.Kelas.ConfigApi;
import com.example.herbalapps.Kelas.SharedVariabel;
import com.example.herbalapps.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailHerbalActivity extends AppCompatActivity {
ImageView imgDetail;
TextView txtNamaObat, txtCara, txtManfaat, txtDosis;
ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_herbal);
        setTitle("Detail Obat Herbal");
        txtNamaObat = (TextView) findViewById(R.id.txtNamaObat);
        txtCara = (TextView) findViewById(R.id.txtCara);
        txtManfaat = (TextView) findViewById(R.id.txtManfaat);
        txtDosis = (TextView) findViewById(R.id.txtDosis);
        imgDetail = (ImageView) findViewById(R.id.imgDetail);
        Intent intent = getIntent();
        String id_herbal = intent.getStringExtra("id_herbal");
        getData(id_herbal);

//        txtNamaObat.setText(id_herbal);
//        txtCara.setText(SharedVariabel.Cara);
//        txtManfaat.setText(SharedVariabel.Manfaat);
//        txtDosis.setText(SharedVariabel.Dosis);
//        Glide.with(this)
//                .load(SharedVariabel.FotoObat) // image url
//                .placeholder(R.drawable.ic_herballoading) // any placeholder to load at start
//                .error(R.drawable.ic_herballoading)  // any image in case of error
//                .override(1000, 600)
//                .into(imgDetail); // resizing
    }

    private void getData(final String Id) {

        loading = ProgressDialog.show(DetailHerbalActivity.this, "Mohon Tunggu...", "Sedang Proses...", false, false);

        String url = ConfigApi.DetailHerbalApi+Id;

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
//                Toast.makeText(DetailHerbalActivity.this,response,Toast.LENGTH_LONG).show();
                showHerbal(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DetailHerbalActivity.this, error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(DetailHerbalActivity.this);
        requestQueue.add(stringRequest);
    }

    private void showHerbal(String response){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
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

                txtNamaObat.setText(nama_obat);
                txtCara.setText(cara);
                txtManfaat.setText(manfaat);
                txtDosis.setText(dosis);
                Glide.with(this)
                        .load(foto_obat) // image url
                        .placeholder(R.drawable.ic_herballoading) // any placeholder to load at start
                        .error(R.drawable.ic_herballoading)  // any image in case of error
                        .override(1000, 600)
                        .into(imgDetail); // resizing

            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(DetailHerbalActivity.this
                    ,"Data Salah "+e,Toast.LENGTH_LONG).show();
        }

    }


}