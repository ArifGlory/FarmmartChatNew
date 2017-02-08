package com.example.fujimiya.farmartrevisi;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class ProfilUserActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String alamatTerima;
    String emailTerima;
    public static String  keyF,userTerima;
    TextView txtUser,txtAlamat,txtEmail;
    Firebase ref;
    Intent i;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://farmartcorp.firebaseio.com/anggota");
        txtUser = (TextView) findViewById(R.id.txtUser);
        txtAlamat = (TextView) findViewById(R.id.txtAlamat);
        txtEmail = (TextView) findViewById(R.id.txtEmail);

        userTerima = getIntent().getExtras().get("userKirim").toString();
       // Toast.makeText(getApplicationContext(),"Nama User : "+userTerima,Toast.LENGTH_SHORT).show();

        txtUser.setText(userTerima);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String username = (String) child.child("username").getValue();
                    if (username.equals(userTerima)) {

                        String alamatF = (String) child.child("alamat").getValue();
                        String emailF = (String) child.child("email").getValue();
                        keyF = (String) child.getKey();
                        //Toast.makeText(getActivity().getApplication(),""+FlKomoditi ,Toast.LENGTH_LONG).show();
                        txtEmail.setText(emailF);
                        txtAlamat.setText(alamatF);

                    }

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


       recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        RecycleAdapterKomoditi recycleAdapterKomoditi = new RecycleAdapterKomoditi(getApplicationContext());
        recyclerView.setAdapter(recycleAdapterKomoditi);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


    }

    public void btnKeChat(View view) {

        i = new Intent(this,ChatActivity.class);
        i.putExtra("to",userTerima);
        i.putExtra("from",userTerima);
        i.putExtra("nama",CustomerDrawer.name);
        startActivity(i);
    }
}
