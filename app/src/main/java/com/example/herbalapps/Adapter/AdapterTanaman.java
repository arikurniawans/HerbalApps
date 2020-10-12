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
import com.example.herbalapps.Kelas.ClassTanaman;
import com.example.herbalapps.Kelas.SharedVariabel;
import com.example.herbalapps.R;

import java.util.List;

public class AdapterTanaman extends RecyclerView.Adapter<AdapterTanaman.MyViewHolder> {

    private Context mContext;
    private List<ClassTanaman> tanamanList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtIdtanaman, txtNamaTanaman;
        public CardView cv_main;
        public RelativeLayout relaList;
        public ImageView imgTanaman;


        public MyViewHolder(View view) {
            super(view);
            cv_main = view.findViewById(R.id.cardlist_itemtanaman);
            relaList = view.findViewById(R.id.relaList);
            txtIdtanaman = view.findViewById(R.id.txtIdTanaman);
            txtNamaTanaman = view.findViewById(R.id.txtNamaTanaman);
            imgTanaman = view.findViewById(R.id.imgTanaman);
        }
    }

    public AdapterTanaman(Context mContext, List<ClassTanaman> tanamanlList) {
        this.mContext = mContext;
        this.tanamanList = tanamanlList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemlist_tanaman, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {

        if (tanamanList.isEmpty()) {
            Toast.makeText(mContext.getApplicationContext(), "Belum ada data", Toast.LENGTH_LONG).show();
        } else {
            final ClassTanaman tanamanClassku = tanamanList.get(position);

            holder.txtIdtanaman.setText(tanamanClassku.getIdTanaman());
            holder.txtNamaTanaman.setText(tanamanClassku.getNamaTanaman());
            Glide.with(mContext)
                    .load(tanamanClassku.getFotoTanaman()) // image url
                    .placeholder(R.drawable.ic_herballoading) // any placeholder to load at start
                    .error(R.drawable.ic_herballoading)  // any image in case of error
                    .override(100, 100)
                    .into(holder.imgTanaman); // resizing

        }

    }

    @Override
    public int getItemCount() {
        return tanamanList.size();
    }
}