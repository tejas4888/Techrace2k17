package thedorkknightrises.techraceapp;

/**
 * Created by Aashish Nehete on 19-Aug-16.
 */
public class AppConstants {

    // Preferences
    public static final String PREFS = "techrace_prefs";
    public static final String PREFS_UNLOCKED = "unlocked";
    public static final String PREFS_GROUP = "group";
    public static final String PREFS_HINTS = "hints_remaining";
    public static final String PREFS_BONUS = "bonus_unlocked";
    public static final String PREFS_INAPP = "in_app";
    public static final String PREFS_INAPP_Q = "in_app_q";
    public static final String PREFS_INAPP_A = "in_app_a";
    public static final String PREFS_LEVEL = "level";

    //Preference for username
    public static final String PREFS_USERNAME = "username";

    // Groups
    public static final int GROUP1 = 1;
    public static final int GROUP2 = 2;

    public static final String PASSCODE1 = "ALPHA2K16OMEGA";
    public static final String PASSCODE2 = "PHOENIX";

    // Page
    public static final int PAGE_SCANNER = 0;
    public static final int PAGE_CLUES = 1;
    public static final int PAGE_LOCATIONS = 2;
    public static final int PAGE_FEED = 3;

    //JSON Urls
    public static final String feedurl          ="https://techrace2k17.000webhostapp.com/TechraceQueries/GetFeed.php";
    public static final String addParticipant   ="https://techrace2k17.000webhostapp.com/TechraceQueries/addParticipant.php";
    public static final String updatelocation   ="https://techrace2k17.000webhostapp.com/TechraceQueries/updateLocation.php";
    public static final String getLeaderboard   ="https://techrace2k17.000webhostapp.com/TechraceQueries/getLeaderboard.php";

    public static final String uno_participant_gettype="https://techrace2k17.000webhostapp.com/TechraceQueries/participantApplyQueries/gettypeifpasscodeexists.php";
    public static final String uno_participant_updatecardused="https://techrace2k17.000webhostapp.com/TechraceQueries/participantApplyQueries/updatecardcodes.php";
    public static final String uno_participant_updateleaderboard="https://techrace2k17.000webhostapp.com/TechraceQueries/participantApplyQueries/updateLeaderboard.php";

    public static final String uno_volunteer_getid="https://techrace2k17.000webhostapp.com/TechraceQueries/volunteerapplyqueries/checkpasscode.php";
    public static final String uno_volunteer_updateleaderboard="https://techrace2k17.000webhostapp.com/TechraceQueries/volunteerapplyqueries/updateleaderboardstate.php";

}
