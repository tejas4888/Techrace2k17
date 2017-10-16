package thedorkknightrises.techraceapp;

import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samriddha Basu on 10/14/2016.
 */

public class Codes {

    static FirebaseDatabase database=FirebaseDatabase.getInstance();

    static LocationCodes codes;

    SharedPreferences sharedPreferences;



    /**
     * An array of sample (dummy) items.
     */
    public static final List<String> ITEMS = new ArrayList<>();
    public static final String tester="tester";

    static {

        DatabaseReference myref=database.getReference("codes");
        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ITEMS.clear();
                ITEMS.add((String)dataSnapshot.child("location1").getValue());
                Log.v("Hey",""+(String)dataSnapshot.child("location1").getValue());
                ITEMS.add((String)dataSnapshot.child("location2").getValue());
                ITEMS.add((String)dataSnapshot.child("location3").getValue());
                ITEMS.add((String)dataSnapshot.child("location4").getValue());
                ITEMS.add((String)dataSnapshot.child("location5").getValue());
                ITEMS.add((String)dataSnapshot.child("location6").getValue());
                ITEMS.add((String)dataSnapshot.child("location7").getValue());
                ITEMS.add((String)dataSnapshot.child("location8").getValue());
                ITEMS.add((String)dataSnapshot.child("location9").getValue());
                ITEMS.add((String)dataSnapshot.child("location10").getValue());
                ITEMS.add((String)dataSnapshot.child("location11").getValue());
                ITEMS.add((String)dataSnapshot.child("location12").getValue());
                ITEMS.add((String)dataSnapshot.child("location13").getValue());
                ITEMS.add((String)dataSnapshot.child("location14").getValue());
                Log.v("Last",ITEMS.get(ITEMS.size()-1));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        Log.v("ITEMS", String.valueOf(ITEMS));

    }


    static {
        /*Log.v("here","at the wall");
        ITEMS.add("bhavloc1");
        ITEMS.add("andseloc2");
        ITEMS.add("parfloc3");
        ITEMS.add("sidvloc4");
        ITEMS.add("nescloc5");
        ITEMS.add("worlloc6");
        ITEMS.add("hgardloc7");
        ITEMS.add("ncpartloc8");
        ITEMS.add("bsexloc9");
        ITEMS.add("azmadloc10");
        ITEMS.add("bandsloc12");
        ITEMS.add("spitechloc13");*/
    }

}
