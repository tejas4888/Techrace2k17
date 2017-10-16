package thedorkknightrises.techraceapp.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScanner;
import com.edwardvanraak.materialbarcodescanner.MaterialBarcodeScannerBuilder;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import br.com.safety.locationlistenerhelper.core.CurrentLocationListener;
import br.com.safety.locationlistenerhelper.core.CurrentLocationReceiver;
import br.com.safety.locationlistenerhelper.core.LocationTracker;
import thedorkknightrises.techraceapp.AlarmBroadcastReceiver;
import thedorkknightrises.techraceapp.AppConstants;
import thedorkknightrises.techraceapp.Codes;
import thedorkknightrises.techraceapp.LocationCodes;
import thedorkknightrises.techraceapp.R;
import thedorkknightrises.techraceapp.RequestHandler;
import thedorkknightrises.techraceapp.clues.ClueContent;
import thedorkknightrises.techraceapp.locations.LocationContent;

import static android.content.Context.ALARM_SERVICE;


public class ScannerFragment extends Fragment {
    SharedPreferences.Editor edit;
    Button hintBtn;
    CardView bonus;
    SharedPreferences pref;
    View root;
    private FloatingActionButton fab;

    private LocationTracker locationTracker;
    TextView hotcoldtext;
    Button hotbutton,coldbutton;

    NetworkInfo.State wifi,mobile;

    public static final List<String> list=new ArrayList<>();

    FirebaseDatabase database;
    DatabaseReference codeRef;

    AlarmManager alarmManager;
    PendingIntent pendingIntentforAlarm;
    NotificationManager mNotificationManagerforAlarm;

    public ScannerFragment() {
        // Required empty public constructor
    }

    public static ScannerFragment newInstance() {
        return new ScannerFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("Code in","onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_scanner, container, false);
        hotcoldtext=(TextView)root.findViewById(R.id.clueHotColdText);
        hotbutton=(Button)root.findViewById(R.id.scanner_hotBtn);
        coldbutton=(Button)root.findViewById(R.id.scanner_coldBtn);


        ConnectivityManager conMan = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
         mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();

        //wifi
         wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();




        Log.v("Code in","onCreateView");
        setupHintsButton();

        database=FirebaseDatabase.getInstance();
        codeRef=database.getReference("codes");

        return root;
    }

    private void setupHintsButton() {
        hintBtn = (Button) root.findViewById(R.id.hintBtn);

        pref = getActivity().getSharedPreferences(AppConstants.PREFS, IntroActivity.MODE_PRIVATE);
        edit = pref.edit();
        int hintsRemaining = pref.getInt(AppConstants.PREFS_HINTS, -1);

        if (hintsRemaining == -1) {
            hintsRemaining = 2;
            edit.putInt(AppConstants.PREFS_HINTS, hintsRemaining);
            edit.apply();
        } else if (hintsRemaining == 0) {
            hintBtn.setEnabled(false);
            hintBtn.setTextColor(Color.GRAY);
        }

        hintBtn.setText(String.format(Locale.ENGLISH, "Hint (%d)", hintsRemaining));

        int level = pref.getInt(AppConstants.PREFS_LEVEL, 0);
        if (level == 0 || level == 3 || level == 4 || level == 8 || level == 9 || level == 10) {
            hintBtn.setText("No hint");
            hintBtn.setClickable(false);
            hintBtn.setEnabled(false);
        } else hintBtn.setOnClickListener(getOnClickListener(hintsRemaining));
        if (hintsRemaining == 0 || pref.getBoolean(AppConstants.PREFS_BONUS, false))
            hintBtn.setEnabled(false);
        else hintBtn.setEnabled(true);
    }

    private View.OnClickListener getOnClickListener(final int hintsRemaining) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (hintsRemaining > 0) {
                    Intent intent = new Intent(getActivity(), HintActivity.class);
                    startActivity(intent);
                }
            }
        };
    }

    private void setupBonusHint() {
        bonus = (CardView) root.findViewById(R.id.bonusCard);
        pref = getActivity().getSharedPreferences(AppConstants.PREFS, Context.MODE_PRIVATE);
        if (pref.getBoolean(AppConstants.PREFS_BONUS, false)) {
            bonus.setVisibility(View.VISIBLE);
        } else bonus.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().invalidateOptionsMenu();
        Log.v("Code in","onActivityCreated");
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

        fab.setVisibility(View.VISIBLE);
        fab.animate().scaleX(1).scaleY(1).setDuration(300).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                fab.setClickable(false);
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                fab.setClickable(true);
            }

            @Override
            public void onAnimationCancel(Animator animator) {
                fab.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        }).start();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.main);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onOptionsItemSelected(item);
                return true;
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        setupBonusHint();
        setupHintsButton();
        Log.v("Codein","onResume");
        TextView clueText = (TextView) root.findViewById(R.id.clueText);
        clueText.setMovementMethod(LinkMovementMethod.getInstance());
        TextView bonusClueText = (TextView) root.findViewById(R.id.bonusClueText);

        /*String usernamehaskedkey= LocationContent.getUsernameHashedKey(getActivity());
        Log.v("USERNAMEHASKEDKEY",""+usernamehaskedkey);*/


        final int level = pref.getInt(AppConstants.PREFS_LEVEL, 0);
        final Location cluelocation = new Location("");

        if (pref.getBoolean(AppConstants.PREFS_UNLOCKED, false)) {

            if (level < LocationContent.ITEMS.size() - 1) {
                Log.v("Level",""+level);
                if(level==1) {
                    int hashedvalue=hashingUsernametoCode(pref.getString(AppConstants.PREFS_USERNAME,"Testing 123"));

                    if(hashedvalue==0) {
                        clueText.setText("Knock knock\n" +
                                "Kaun?\n" +
                                "Isk.");
                        cluelocation.setLatitude(19.1129552);
                        cluelocation.setLongitude(72.8243642);
                        bonusClueText.setText("Kaun , Isk..?");
                    }else if(hashedvalue==1){
                        clueText.setText("Once a progeria affected child, once a constipation ridden old man!\n" +
                                "From 1969 till forever, “just wait” here like thousands do everyday!\n");
                        cluelocation.setLatitude(19.1129552);
                        cluelocation.setLongitude(72.8243642);
                        bonusClueText.setText("The house that 'waits'");
                    }else if (hashedvalue==2){
                        clueText.setText("Decode: .--- .- .-.. ... .-");
                        cluelocation.setLatitude(19.1050651);
                        cluelocation.setLongitude(72.8253356);
                        bonusClueText.setText("Decode the Morse code");
                    }else{
                        clueText.setText("Named after its founder, this place buzzes with passionate Thespians everyday");
                        cluelocation.setLatitude(19.1059648);
                        cluelocation.setLongitude(72.8235217);
                        bonusClueText.setText("Also famous for its food with an adjoining cafe");
                    }
                }
                else if (pref.getInt(AppConstants.PREFS_GROUP, AppConstants.GROUP1) == AppConstants.GROUP1) {
                    clueText.setText(Html.fromHtml(ClueContent.ITEMS_1.get(level).details));

                    cluelocation.setLatitude(Double.parseDouble(ClueContent.ITEMS_1.get(level).latitude));
                    cluelocation.setLongitude(Double.parseDouble(ClueContent.ITEMS_1.get(level).longitude));
                    //Toast.makeText(getActivity(),"Latitude:"+ClueContent.ITEMS_1.get(level).latitude+" Longitude:"+ClueContent.ITEMS_1.get(level).longitude,Toast.LENGTH_SHORT).show();

                    bonusClueText.setText(Html.fromHtml(ClueContent.ITEMS_2.get(level).details));
                } else {
                    clueText.setText(Html.fromHtml(ClueContent.ITEMS_2.get(level).details));

                    cluelocation.setLatitude(Double.parseDouble(ClueContent.ITEMS_2.get(level).latitude));
                    cluelocation.setLongitude(Double.parseDouble(ClueContent.ITEMS_2.get(level).longitude));
                    // Toast.makeText(getActivity(),"Latitude:"+ClueContent.ITEMS_2.get(level).latitude+" Longitude:"+ClueContent.ITEMS_2.get(level).longitude,Toast.LENGTH_SHORT).show();

                    bonusClueText.setText(Html.fromHtml(ClueContent.ITEMS_1.get(level).details));
                }
            } else {
                root.findViewById(R.id.clueTitle).setVisibility(View.GONE);
                hintBtn.setVisibility(View.GONE);
                fab.setVisibility(View.GONE);

                try {
                    cluelocation.setLatitude(Double.parseDouble(ClueContent.ITEMS_2.get(LocationContent.ITEMS.size() - 1).latitude));
                    cluelocation.setLongitude(Double.parseDouble(ClueContent.ITEMS_2.get(LocationContent.ITEMS.size() - 1).longitude));
                }catch (Exception e)
                {
                }

                clueText.setText("\uD83C\uDF89 You have successfully completed the SPIT TechRace 2K16 \uD83C\uDF8A \n\nStep forth so you may receive the honor and glory that is your due \uD83C\uDF96");
            }
        }

        try {
            locationTracker = new LocationTracker("my.action")
                    .setInterval(30000)
                    .setGps(true)
                    .setNetWork(false)
                    .currentLocation(new CurrentLocationReceiver(new CurrentLocationListener() {
                        @Override
                        public void onCurrentLocation(Location location) {
                            //Toast.makeText(getActivity(), "Currently:" + location.getLatitude() + " " + location.getLongitude(), Toast.LENGTH_SHORT).show();
                            double distanceinmetres=cluelocation.distanceTo(location);

                            //Toast.makeText(getActivity(),"Distance: "+distanceinmetres,Toast.LENGTH_SHORT).show();

                            if (mobile == NetworkInfo.State.CONNECTED || wifi == NetworkInfo.State.CONNECTED) {
                                if (distanceinmetres<=250)
                                {
                                    try {
                                        hotcoldtext.setText("HOT");

                                        int color = Color.parseColor("#FFFFFF");
                                        hotbutton.setTextColor(color);
                                        hotbutton.setBackground(getResources().getDrawable(R.drawable.button_hot_leftrounded));

                                        int color2 = Color.parseColor("#ff999999");
                                        coldbutton.setTextColor(color2);
                                        coldbutton.setBackground(getResources().getDrawable(R.drawable.button_rightrounded));
                                    }catch (Exception e){

                                    }
                                }else {

                                    try {
                                        hotcoldtext.setText("COLD");
                                        int color = Color.parseColor("#FFFFFF");
                                        coldbutton.setTextColor(color);
                                        coldbutton.setBackground(getResources().getDrawable(R.drawable.button_cold_rightrounded));

                                        int color2 = Color.parseColor("#ff999999");
                                        hotbutton.setTextColor(color2);
                                        hotbutton.setBackground(getResources().getDrawable(R.drawable.button_leftrounded));
                                    }catch (Exception e){

                                    }

                                }

                            }
                        }

                        @Override
                        public void onPermissionDiened() {
                            hotcoldtext.setText("Location OFF");
                        }
                    }))
                    .start(getActivity().getBaseContext(), (AppCompatActivity) getActivity());
        }catch (Exception e)
        {
            Log.v("EROOR",""+e);
        }


        if ((level == 0 || level == 2 ) && !pref.getBoolean(AppConstants.PREFS_INAPP, false)) {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock_white_24dp));
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(getActivity(), InAppChallengeActivity.class);
                    i.putExtra(AppConstants.PREFS_INAPP_Q, ClueContent.getInAppChallenge(level + 1).question);
                    i.putExtra(AppConstants.PREFS_INAPP_A, ClueContent.getInAppChallenge(level + 1).answer);
                    startActivity(i);
                }
            });
        } else {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_center_focus_weak_white_24dp));
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fab.setClickable(false);
                    enterReveal();
                }
            });
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());

            TextView head = new TextView(getActivity());
            head.setText(R.string.action_help);
            head.setTextSize(24);
            head.setTextColor(Color.WHITE);
            head.setPadding(0, 0, 0, 16);

            TextView textView = new TextView(getActivity());
            textView.setText(R.string.scan_help);
            textView.setTextSize(20);
            textView.setPadding(0, 0, 0, 16);

            Button button = new Button(getActivity());
            button.setText(R.string.scan_manual);
            button.setTextColor(getResources().getColor(R.color.colorAccent));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bottomSheetDialog.dismiss();

                    final BottomSheetDialog bottomSheet = new BottomSheetDialog(getActivity());

                    TextView textView = new TextView(getActivity());
                    textView.setText(R.string.manual_desc);
                    textView.setTextSize(20);

                    final EditText codeText = new EditText(getActivity());

                    Button button = new Button(getActivity());
                    button.setText(R.string.action_confirm);
                    button.setTextColor(getResources().getColor(R.color.colorAccent));
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            scanned(codeText.getText().toString());
                            codeText.setText("");
                            bottomSheet.dismiss();
                        }
                    });

                    LinearLayout linearLayout = new LinearLayout(getActivity());
                    linearLayout.setOrientation(LinearLayout.VERTICAL);
                    linearLayout.setBackgroundColor(Color.DKGRAY);
                    linearLayout.setPadding(48, 64, 48, 64);
                    linearLayout.addView(textView);
                    linearLayout.addView(codeText);
                    linearLayout.addView(button);

                    bottomSheetDialog.setTitle(R.string.action_help);
                    bottomSheetDialog.setContentView(linearLayout);
                    bottomSheetDialog.setCanceledOnTouchOutside(true);
                    bottomSheetDialog.show();
                }
            });

            LinearLayout linearLayout = new LinearLayout(getActivity());
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundColor(Color.DKGRAY);
            linearLayout.setPadding(48, 48, 48, 64);
            linearLayout.addView(head);
            linearLayout.addView(textView);
            linearLayout.addView(button);

            bottomSheetDialog.setTitle(R.string.action_help);
            bottomSheetDialog.setContentView(linearLayout);
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    private void startScan() {
        /**
         * Build a new MaterialBarcodeScanner
         */
        final MaterialBarcodeScanner materialBarcodeScanner = new MaterialBarcodeScannerBuilder()
                .withActivity(getActivity())
                .withEnableAutoFocus(true)
                .withBleepEnabled(true)
                .withBackfacingCamera()
                .withCenterTracker()
                .withText("Scanning...")
                .withResultListener(new MaterialBarcodeScanner.OnResultListener() {
                    @Override
                    public void onResult(Barcode barcode) {
                        scanned(barcode.displayValue);
                    }
                })
                .build();
        materialBarcodeScanner.startScan();
    }

    void enterReveal() {
        if (Build.VERSION.SDK_INT >= 21) {

            final View myView = getActivity().findViewById(R.id.view);
            // get the center for the clipping circle
            int cx = (fab.getLeft() + fab.getRight()) / 2;
            int cy = (fab.getTop() + fab.getBottom()) / 2;

            // get the final radius for the clipping circle
            int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

            // create the animator for this view (the start radius is zero)
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);


            myView.setVisibility(View.VISIBLE);

            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(Animator animation) {
                    startScan();
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    myView.setVisibility(View.GONE);
                    fab.setClickable(true);
                }
            });
            anim.setDuration(500);
            anim.start();
        } else startScan();
    }

    private void scanned(String code) {
        String useless=Codes.tester;

        try{
            String useless2 = Codes.ITEMS.get(0);
        }catch (Exception e)
        {

        }

        int level = pref.getInt(AppConstants.PREFS_LEVEL, 0);
        if (level >= LocationContent.ITEMS.size() - 1 || ((level == 0 || level == 2 ) && !pref.getBoolean(AppConstants.PREFS_INAPP, false)))
            return;


        try
        {
            if (code.equals(Codes.ITEMS.get(level))) {

                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();


                if(level>=7 && level<=11) {
                    //Code for alarm notification manager
                    int i;
                    switch (level){
                        case 7:i=Integer.parseInt("300");
                                break;

                        case 8:i=Integer.parseInt("300");
                            break;

                        case 9:i=Integer.parseInt("420");
                            break;

                        case 10:i=Integer.parseInt("300");
                            break;

                        case 11:i=Integer.parseInt("1200");
                            break;

                        default:i=Integer.parseInt("120");
                            break;

                    }


                    Intent intent = new Intent(getActivity(), AlarmBroadcastReceiver.class);
                    pendingIntentforAlarm = PendingIntent.getBroadcast(
                            getActivity(), 234324243, intent, 0);

                    alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()
                            + (i * 1000), pendingIntentforAlarm);

                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                    int minutes=i/60;

                    NotificationCompat.Builder mBuilderalarm =
                            new NotificationCompat.Builder(getActivity())
                                    .setSmallIcon(R.drawable.logo)
                                    .setContentTitle("Hurry!!")
                                    .setContentText("Timer of "+minutes+" mins is set on " + currentDateTimeString)
                                    .setOngoing(true)
                                    .setAutoCancel(false);

                    mNotificationManagerforAlarm =
                            (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManagerforAlarm.notify(1, mBuilderalarm.build());
                }
                //End of alarm notification manager


                updateLocationofParticipant(level+1);


                pref = getActivity().getSharedPreferences(AppConstants.PREFS, Context.MODE_PRIVATE);
                level++;
                edit = pref.edit();
                edit.putBoolean(AppConstants.PREFS_BONUS, false);
                edit.putInt(AppConstants.PREFS_LEVEL, level);
                edit.putBoolean(AppConstants.PREFS_INAPP, false);
                edit.apply();
                onResume();

                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(getActivity())
                                .setSmallIcon(R.drawable.logo)
                                .setContentTitle("New location unlocked!")
                                .setContentText("Tap to see details")
                                .setAutoCancel(true);

                Intent resultIntent = new Intent(getActivity(), DetailsActivity.class);
                resultIntent.putExtra("image", LocationContent.ITEMS.get(level).image);
                resultIntent.putExtra("location", LocationContent.ITEMS.get(level).name);
                resultIntent.putExtra("location_desc", LocationContent.ITEMS.get(level).details);

                PendingIntent resultPendingIntent =
                        PendingIntent.getActivity(
                                getActivity(),
                                0,
                                resultIntent,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );

                mBuilder.setContentIntent(resultPendingIntent);

                NotificationManager mNotificationManager =
                        (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(0, mBuilder.build());

            } else if (code.startsWith("http")) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(code));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getActivity().startActivity(i);
            } else {
                Toast.makeText(getActivity(), "Invalid code!", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            Toast.makeText(getActivity(),"Please scan again. Also check network",Toast.LENGTH_SHORT).show();
        }

    }

    private void updateLocationofParticipant(int level){

        final String name = pref.getString(AppConstants.PREFS_USERNAME,"Testing 123");
        final String location = String.valueOf(level);

        class AddEmployee extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(getActivity(),"Updating","Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                Toast.makeText(getActivity(),"Leaderboard updated!",Toast.LENGTH_LONG).show();
            }

            @Override
            protected String doInBackground(Void... v) {
                HashMap<String,String> params = new HashMap<>();
                params.put("name",name);
                params.put("location",location);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(AppConstants.updatelocation, params);
                return res;
            }
        }

        AddEmployee ae = new AddEmployee();
        ae.execute();
    }

    int hashingUsernametoCode(String s)
    {
        try{
            int sum=0;
            sum=sum+(int)s.charAt(0);
            sum=sum+(int)s.charAt(1);
            sum=sum+(int)s.charAt(2);

            /*for(int i=0;i<s.length();i++)
            {
                sum=sum+(int)s.charAt(i);
            }*/

            return sum%4;
        }catch (Exception e)
        {
            return 1;
        }


    }
}
