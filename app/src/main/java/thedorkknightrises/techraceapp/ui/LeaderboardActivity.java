package thedorkknightrises.techraceapp.ui;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import thedorkknightrises.techraceapp.AppConstants;
import thedorkknightrises.techraceapp.FeedAdapter;
import thedorkknightrises.techraceapp.FeedItem;
import thedorkknightrises.techraceapp.HttpHandler;
import thedorkknightrises.techraceapp.LeaderboardAdapter;
import thedorkknightrises.techraceapp.LeaderboardItem;
import thedorkknightrises.techraceapp.R;

public class LeaderboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<LeaderboardItem> leaderboardItems;
    ProgressDialog progressDialog;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (Build.VERSION.SDK_INT >= 21)
            setupWindowAnimations();

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.leaderboard_swiperefresh);
        leaderboardItems=new ArrayList<LeaderboardItem>();
        recyclerView=(RecyclerView)findViewById(R.id.leaderboard_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //leaderboardItems.add(new LeaderboardItem("Name","Clues solved"));
        new getLeaderboard().execute();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                leaderboardItems.clear();
                //leaderboardItems.add(new LeaderboardItem("Name","Clues solved"));
                new getLeaderboard().execute();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private class getLeaderboard extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog=new ProgressDialog(LeaderboardActivity.this);
            progressDialog.setMessage("Populating Leaderboard...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(AppConstants.getLeaderboard);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray feeds = jsonObj.getJSONArray("result");
                    Log.v("Inside1","here");
                    // looping through All Contacts
                    for (int i = 0; i < feeds.length(); i++) {
                        JSONObject c = feeds.getJSONObject(i);

                        String name=c.getString("name");
                        String location=c.getString("location");
                        String state=c.getString("state");
                        String applied=c.getString("applied");
                        Log.v("Inside2",""+name+location);

                        LeaderboardItem object=new LeaderboardItem(name,location,state,applied);
                        leaderboardItems.add(object);
                    }

                } catch (final JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LeaderboardActivity.this,"Couldn't get data from Server. Please try again later", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LeaderboardActivity.this,"No Internet Connection",Toast.LENGTH_LONG).show();
                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if (progressDialog.isShowing())
            {
                progressDialog.hide();
            }
            recyclerView.setAdapter(new LeaderboardAdapter(leaderboardItems,LeaderboardActivity.this));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(300);
        getWindow().setEnterTransition(slide);
        getWindow().setReenterTransition(slide);
    }
}
