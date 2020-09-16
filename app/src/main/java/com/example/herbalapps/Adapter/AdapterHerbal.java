package com.example.herbalapps.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.herbalapps.Activity.DetailHerbalActivity;
import com.example.herbalapps.Kelas.ClassHerbal;
import com.example.herbalapps.Kelas.SharedVariabel;
import com.example.herbalapps.R;

import java.util.List;

public class AdapterHerbal extends RecyclerView.Adapter<AdapterHerbal.MyViewHolder> {

    private Context mContext;
    private List<ClassHerbal> herbalList;
    public Button btn_detailal;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIdherbal, txtNamaHerbal;
        public CardView cv_main;
        public RelativeLayout relaList;
        public ImageView imgHerbal;


        public MyViewHolder(View view) {
            super(view);
            cv_main = view.findViewById(R.id.cardlist_itemherbal);
            relaList = view.findViewById(R.id.relaList);
            txtIdherbal = view.findViewById(R.id.txtIdHerbal);
            txtNamaHerbal = view.findViewById(R.id.txtNamaHerbal);
            imgHerbal = view.findViewById(R.id.imgHerbal);
            btn_detailal = view.findViewById(R.id.btn_detaildiet);
        }
    }

    public AdapterHerbal(Context mContext, List<ClassHerbal> herbalList) {
        this.mContext = mContext;
        this.herbalList = herbalList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlist_herbal, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        if (herbalList.isEmpty()) {
            Toast.makeText(mContext.getApplicationContext(), "Belum ada data", Toast.LENGTH_LONG).show();
        } else {
            final ClassHerbal herbalClassku = herbalList.get(position);

            holder.txtIdherbal.setText(herbalClassku.getIdHerbal());
            holder.txtNamaHerbal.setText(herbalClassku.getNamaHerbal());
            Glide.with(mContext)
                    .load(herbalClassku.getFotoHerbal()) // image url
                    .placeholder(R.drawable.ic_herballoading) // any placeholder to load at start
                    .error(R.drawable.ic_herballoading)  // any image in case of error
                    .override(100, 100)
                    .into(holder.imgHerbal); // resizing

            btn_detailal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedVariabel.NamaObat = herbalClassku.getNamaHerbal();
                    SharedVariabel.FotoObat = herbalClassku.getFotoHerbal();
                    SharedVariabel.Cara = herbalClassku.getCara();
                    SharedVariabel.Manfaat = herbalClassku.getManfaat();
                    SharedVariabel.Dosis = herbalClassku.getDosis();
                    Intent intent = new Intent(mContext.getApplicationContext(), DetailHerbalActivity.class);
                    intent.putExtra("id_herbal",holder.txtIdherbal.getText().toString());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return herbalList.size();
    }
}