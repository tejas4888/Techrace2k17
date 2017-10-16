package thedorkknightrises.techraceapp.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.heinrichreimersoftware.materialintro.app.SlideFragment;

import java.util.HashMap;

import thedorkknightrises.techraceapp.AppConstants;
import thedorkknightrises.techraceapp.R;
import thedorkknightrises.techraceapp.RequestHandler;

/**
 * Created by Samriddha Basu on 8/10/2016.
 */
public class CodeFragment extends SlideFragment {
    private EditText passcode;
    private EditText username;
    private Button button;
    private boolean unlocked = false;

    public CodeFragment() {
        // Required empty public constructor
    }

    public static CodeFragment newInstance() {
        return new CodeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.intro_code, container, false);

        final SharedPreferences pref = getActivity().getSharedPreferences(AppConstants.PREFS,
                IntroActivity.MODE_PRIVATE);
        final SharedPreferences.Editor edit = pref.edit();

        /*SharedPreferences userpref = getActivity().getSharedPreferences(AppConstants.PREFS_USERNAME,
                IntroActivity.MODE_PRIVATE);
        final SharedPreferences.Editor usernameeditor=userpref.edit();*/

        passcode = (EditText) root.findViewById(R.id.code);
        username = (EditText) root.findViewById(R.id.username);

        button = (Button) root.findViewById(R.id.codeBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passcode.getText().toString().equals(AppConstants.PASSCODE1)) {
                    unlocked = true;
                    unlocked();

                    addParticipant();
                    edit.putString(AppConstants.PREFS_USERNAME,username.getText().toString());
                    edit.commit();

                    String s=pref.getString(AppConstants.PREFS_USERNAME,"Default");
                    Toast.makeText(getActivity(),"Username is:"+s,Toast.LENGTH_SHORT).show();

                    edit.putBoolean(AppConstants.PREFS_UNLOCKED, true);
                    edit.putInt(AppConstants.PREFS_GROUP, AppConstants.GROUP1);
                    edit.commit();
                    updateNavigation();
                    Toast.makeText(getContext(), "Unlocked!", Toast.LENGTH_SHORT).show();
                } else if (passcode.getText().toString().equals(AppConstants.PASSCODE2)) {
                    unlocked = true;
                    unlocked();

                    addParticipant();
                    edit.putString(AppConstants.PREFS_USERNAME,username.getText().toString());
                    edit.commit();

                    String s=pref.getString(AppConstants.PREFS_USERNAME,"Default");
                    Toast.makeText(getActivity(),"Username is:"+s,Toast.LENGTH_SHORT).show();

                    edit.putBoolean(AppConstants.PREFS_UNLOCKED, true);
                    edit.putInt(AppConstants.PREFS_GROUP, AppConstants.GROUP2);
                    edit.commit();
                    updateNavigation();
                    Toast.makeText(getContext(), "Unlocked!", Toast.LENGTH_SHORT).show();
                } else {
                    passcode.setHintTextColor(Color.RED);
                    passcode.setHint("Incorrect Passcode");
                    passcode.setText("");
                }
            }
        });
        return root;
    }

    @Override
    public boolean canGoForward() {
        return unlocked;
    }

    private void unlocked() {
        passcode.setEnabled(false);
        button.setEnabled(false);
        button.setText("✓");
    }

    private void addParticipant(){

        final String name = username.getText().toString();
        final String location = "0";

        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Adding...","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),"Participant added successfully!",Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("location",location);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(AppConstants.addParticipant, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }
}
