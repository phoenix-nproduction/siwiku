package com.example.siwiku;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {

    ImageView notes, exit;
    TextView nickname;
    private DatabaseReference root,user,ref;
    FirebaseFirestore db;

    RecyclerView wisataRecyclerview;
    RecyclerAdapter wisataRecycleradapter;
    ArrayList<Wisata> wisataList;
    Context context;
    ProgressDialog progressDialog;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();

        notes = findViewById(R.id.image_icon);
        exit = findViewById(R.id.image_icon_exit);
        nickname = findViewById(R.id.nicknameShow);

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        root = FirebaseDatabase.getInstance().getReference();
        user = root.child("user");
        ref = user.child(uid);
        db = FirebaseFirestore.getInstance();

        wisataRecyclerview = findViewById(R.id.wisata_recycler);
        wisataRecyclerview.setHasFixedSize(true);
        wisataRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        wisataList = new ArrayList<Wisata>();
        wisataRecycleradapter = new RecyclerAdapter(wisataList,DashboardActivity.this);

        wisataRecyclerview.setAdapter(wisataRecycleradapter);

        EventChangeListener();


        if (uid != null){
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.child("nickname").getValue().toString();
                    nickname.setText(name+"!");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else  {
            nickname.setText("Travellers!");
        }

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DashboardActivity.this,AboutActivity.class);
                startActivity(intent);
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                Intent intent = new Intent(DashboardActivity.this,signAwalActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void EventChangeListener() {
        db.collection("wisata").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){

                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Log.e("Firestore Error",error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()){
                    if (dc.getType() == DocumentChange.Type.ADDED){
                        wisataList.add(dc.getDocument().toObject(Wisata.class));
                    }
                    wisataRecycleradapter.notifyDataSetChanged();
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }

}