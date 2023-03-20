package com.example.siwiku;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailActivity extends AppCompatActivity {

    ImageView detailedImg,detailedBack;
    TextView nama,fasilitas,alamat,deskripsi,tiket;
    public String lokasi;
    Button direct;

    private FirebaseFirestore firestore;
    Wisata wisata = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        firestore = FirebaseFirestore.getInstance();

        final Object obj = getIntent().getSerializableExtra("detailed");
        if (obj instanceof Wisata){
            wisata = (Wisata) obj;
        }

        detailedImg = findViewById(R.id.imageDetail);
        detailedBack = findViewById(R.id.btn_backDetail);
        nama = findViewById(R.id.nama_wisataDetail);
        fasilitas = findViewById(R.id.fasilitasDetail);
        alamat = findViewById(R.id.alamatDetail);
        deskripsi = findViewById(R.id.descDetail);
        direct = findViewById(R.id.directDetail);
        tiket = findViewById(R.id.hargaDetail);


        if (wisata != null){
            Glide.with(getApplicationContext()).load(wisata.getImg_url()).into(detailedImg);
            nama.setText(wisata.getNama_wisata());
            fasilitas.setText(wisata.getFasilitas());
            alamat.setText(wisata.getAlamat());
            deskripsi.setText(wisata.getDeskripsi());
            tiket.setText(wisata.getTiket());
            lokasi = wisata.getLokasi();

            direct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    directTomaps(lokasi);
                }
            });
        }

        detailedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this,DashboardActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void directTomaps(String lokasi) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(lokasi));
        startActivity(intent);
    }
}