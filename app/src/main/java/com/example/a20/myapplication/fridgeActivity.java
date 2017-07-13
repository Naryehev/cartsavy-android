package com.example.a20.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 20 on 7/11/2017.
 */

public class fridgeActivity extends AppCompatActivity {

    private ListView lv;
    private DatabaseReference mDatabase;
    private List<scannedFood> foodlist = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fridge);
        lv = (ListView) findViewById(R.id.fridgeList);
        List<String> your_array_list = new ArrayList<String>();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String username = user.getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference(username);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                foodlist.clear();
                for(DataSnapshot foodSnapshot: dataSnapshot.getChildren() ){
                    scannedFood tempfood = foodSnapshot.getValue(scannedFood.class);
                    foodlist.add(tempfood);

                }
                listAdapter adapter = new listAdapter(fridgeActivity.this, foodlist);
                lv.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
