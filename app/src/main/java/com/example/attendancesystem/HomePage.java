package com.example.attendancesystem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;


public class HomePage extends AppCompatActivity  {


    Button log;
    TextView Name;
    TextView Sapid;
    FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mas;
    FirebaseFirestore ff;
    private Fragment frag;
    ConstraintLayout a,b;
    FrameLayout f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        log=(Button)findViewById(R.id.logout);
        Name=findViewById(R.id.nans);
        //Sapid=findViewById(R.id.sans);
        firebaseAuth=FirebaseAuth.getInstance();
        ff=FirebaseFirestore.getInstance();
       a=(ConstraintLayout)findViewById(R.id.cc);








        try {


            String userId;
            userId = firebaseAuth.getCurrentUser().getUid();
            DocumentReference dd;
            dd = ff.collection("Users").document(userId);
            dd.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                            //if document not empty that display data to home page
                    Name.setText(documentSnapshot != null ? documentSnapshot.getString("Full_name") : null);
                    // Sapid.setText(documentSnapshot.getString("Sap_ID"));


//come on discord!! i came come fast steve

                    log.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            FirebaseAuth.getInstance().signOut();
                            ((ConstraintLayout) findViewById(R.id.cc)).removeAllViews();
                            FragmentManager f=getSupportFragmentManager();
                            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                            frag=new firstFragment();
                            ft.replace(R.id.cc,frag).addToBackStack(null);
                            //  ft.addToBackStack(null);
                            ft.commit();
                            Toast.makeText(getApplicationContext(),"Logged Out Successfully",Toast.LENGTH_SHORT).show();
                            // a.setVisibility(View.GONE);



                        }
                    });



                }
            });
        }

        catch (Exception ex)
        {

                ex.printStackTrace();
        }






    }


}
