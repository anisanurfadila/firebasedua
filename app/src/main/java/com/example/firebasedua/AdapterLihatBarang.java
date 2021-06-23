package com.example.firebasedua;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.View;
import android.view.LayoutInflater;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasedua.MainActivity;
import com.example.firebasedua.R;
import com.example.firebasedua.EditBarang;
import com.example.firebasedua.Barang;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdapterLihatBarang extends RecyclerView.Adapter<AdapterLihatBarang.ViewHolder> {
    private ArrayList<Barang> daftarBarang;
    private Context context;
    private DatabaseReference databaseReference;

    public AdapterLihatBarang(ArrayList<Barang> barang, Context ctx){
        daftarBarang = barang;
        context = ctx;
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        ViewHolder(View v){
            super(v);
            tvTitle = (TextView) v.findViewById(R.id.tv_namabarang);

            databaseReference = FirebaseDatabase.getInstance().getReference();
        }
    }
@NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {
        final String name = daftarBarang.get(position).getNama();
        final String kode = daftarBarang.get(position).getKode();
        final String code = daftarBarang.get(position).getCode();
        holder.tvTitle.setText(name);
        holder.tvTitle.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(),v);
                popupMenu.inflate(R.menu.menuteman);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.mnEdit:
                                Bundle bundle = new Bundle();
                                bundle.putString("Kunci1", code);
                                bundle.putString("kunci2", name);
                                bundle.putString("kunci3", kode );

                                Intent intent = new Intent(v.getContext(),EditBarang.class);
                                intent.putExtras(bundle);
                                v.getContext().startActivity(intent);
                                break;

                            case R.id.mnHapus:
                                AlertDialog.Builder alerDlg = new AlertDialog.Builder(v.getContext());
                                alerDlg.setTitle("Yakin data akan dihapus?");
                                alerDlg.setMessage("tekan 'Ya' untuk menghapus")
                                        .setCancelable(false)
                                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(databaseReference !=null){
                                            databaseReference.child("Barang").child(code).removeValue();
                                        }
                                        Toast.makeText(v.getContext(),"Data " +name+" berhasil dihapus",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                                        v.getContext().startActivity(intent);
                                    }
                                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog aDlg = alerDlg.create();
                                aDlg.show();
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return daftarBarang.size();
    }
/*
    public void DeleteData(String code){
        if(databaseReference !=null){
            databaseReference.child("Barang").child(code).removeValue();
        }
    }*/

}
