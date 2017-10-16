package thedorkknightrises.techraceapp.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import thedorkknightrises.techraceapp.AppConstants;
import thedorkknightrises.techraceapp.R;
import thedorkknightrises.techraceapp.RequestHandler;

public class ParticipantApplyActivity extends AppCompatActivity {

    EditText passcode;
    Button confirmBtn;
    String GLOBAL_name,JSON_STRING;
    String GLOBAL_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_apply);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.5));

        GLOBAL_name=getIntent().getStringExtra("name");
        confirmBtn=(Button)findViewById(R.id.participantapply_confirm);
        passcode=(EditText)findViewById(R.id.participantapply_passcode);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passcodeis=passcode.getText().toString();
                getJSON(passcodeis);
                Log.v("GLOBAL",""+GLOBAL_type);
                /*if(GLOBAL_type.equals("0"))
                {
                    Toast.makeText(ParticipantApplyActivity.this,"NO VALID",Toast.LENGTH_SHORT).show();
                    finish();
                }else
                {
                    Toast.makeText(ParticipantApplyActivity.this,"Code is valid",Toast.LENGTH_SHORT).show();
                    finish();
                }*/

            }
        });

    }

    private void getJSON(final String passcodeis) {
        class GetJSON extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ParticipantApplyActivity.this, "Validating code", "Wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                Log.v("JSON",JSON_STRING );
                showEmployee(passcodeis);
            }

            @Override
            protected String doInBackground(Void... params) {
                HashMap<String,String> args = new HashMap<>();
                args.put("passcode",passcodeis);

                RequestHandler rh = new RequestHandler();
                String s = rh.sendPostRequest(AppConstants.uno_participant_gettype,args);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }

    private void showEmployee(String passcode){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray("result");

                for (int i = 0; i < result.length(); i++) {
                    JSONObject jo = result.getJSONObject(i);
                    String type = jo.getString("type");
                    Log.v("Result", "Type:" + type);


                        int l=type.length();
                        if (l==4)
                        {
                            Toast.makeText(ParticipantApplyActivity.this,"Invalid",Toast.LENGTH_SHORT).show();
                            finish();
                        }else {
                            updateLeaderboardandCodeused(type,passcode);
                            GLOBAL_type=type;
                            Log.v("Result","no-null"+l);
                            //write functions here to continue
                        }
                    //id null means no such card

                }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void updateLeaderboardandCodeused(final String type,final String passcode){

        /*final String name = pref.getString(AppConstants.PREFS_USERNAME,"Testing 123");
        final String location = String.valueOf(level);*/

        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ParticipantApplyActivity.this,"Updating Leaderboard","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(ParticipantApplyActivity.this,"Successfully applied!",Toast.LENGTH_SHORT).show();
                loading.dismiss();
                finish();

                //Toast.makeText(getActivity(),"Leaderboard updated!",Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",GLOBAL_name);
                params.put("type",type);
                params.put("passcode",passcode);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(AppConstants.uno_participant_updatecardused, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }
}
