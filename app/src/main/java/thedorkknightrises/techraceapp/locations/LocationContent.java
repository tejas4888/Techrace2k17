package thedorkknightrises.techraceapp.locations;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import thedorkknightrises.techraceapp.AppConstants;
import thedorkknightrises.techraceapp.R;
import thedorkknightrises.techraceapp.ui.IntroActivity;

public class LocationContent {

    private static SharedPreferences pref;
    /**
     * An array of sample (dummy) items.
     */
    public static final List<Location> ITEMS = new ArrayList<>();



    static {

        ITEMS.add(new Location(0, "SPIT Quad", "This is where your journey starts...<br>Within the walls of Sardar Patel Institute of Technology, an engineering college located in Andheri, a suburb of Mumbai. It is affiliated to Mumbai University and offers graduate (Bachelor), post-graduate (Master) and doctoral (Ph.D.) degrees in Engineering. The Institute is reputed for excellent training in programs in engineering and technology at the certificate, degree, post-graduate and doctoral levels, as well as numerous well-regarded research works. The institute has initiated the process of academic autonomy with Maharashtra State.", R.drawable.spit,"19.1235726","72.8338993"));
        ITEMS.add(new Location(1, "Amphitheatre", "Bhavan's Campus is home to Bhavan's College, Sardar Patel College of Engineering (SPCE), and the hosts of this event, Sardar Patel Institute of Technology(SPIT). It is built upon 45 acres (180,000 m2) of land and has huge volleyball and basketball courts, as well as football pitches,a botanical garden and an adventure park.", R.drawable.p01,"19.1202616","72.8455507"));
        ITEMS.add(new Location(2, "Vile Parle", "Named after the first Parle Factory that started near its Railway Station, Vile Parle is famous for its Institutes, Galleries, Temples and Beaches! Jalsa and Prateeksha are two famous spots for Bollywood enthusiasts, who throng here to catch a glimpse of their Superstar Amitabh Bachan. Iskcon Temple draws crowds from across the globe to its humble campus. People come here in large numbers  especially during Janmashtami to pay reverence to their deity, Krishna. Vile Parle attracts many art, drama, literature enthusiasts, credit to Prithvi Theatre, build in memory of Late Shri Prithviraj Kapoor, an iconic Indian filmstart. This theatre entertains people with various plays, poems, dance, etc.", R.drawable.loc02,"19.1202616","72.8455507"));
        ITEMS.add(new Location(3, "Carter Road", "Famous for its long and calm promenade, Carter Road lies at the coast of the Arabian Sea, giving a picturesque scenario of involvement of the busy city-life and the calmness of the Sea. Many people flock here to witness the soothing sunset. The world's largest steel bat - weighing over two tonnes and standing more than 25 feet high - was unveiled at the Carter Road Promenade in Mumbai to honour the former Indian cricketer Sachin Tendulkar", R.drawable.loc03,"19.0705406","72.8221947"));
        ITEMS.add(new Location(4, "Mahalakshmi Racecourse", "The Mahalaxmi Racecourse is a horse racing track in Mahalaxmi neighbourhood, of Mumbai. The track is oval shaped with 2,400 metres straight chute, spread over approximately 225 acres of open land in the heart of Mumbai city. It’s a place where Western culture touches the bustling life of Mumbaikar’s as they enjoy watching various horseraces that are conducted", R.drawable.loc05,"18.984256","72.8180909"));
        ITEMS.add(new Location(5, "Haji Ali", "Situated at the backdrop of a beautiful view of the sea is the shrine of Haji Ali, a wealthy merchant turned into Muslim Sufi. The Haji Ali Dargah (mausoleum) was raised in 1431 in reminiscence of an affluent Muslim trader, Sayyed Peer Haji Ali Shah Bukhari, who gave up all his worldly belongings before making a trip to Mecca. People from all walks of life and religions come here to seek blessings. Its tomb, made of glass, beautifully depicts the Indo-Islamic culture", R.drawable.loc04,"18.9783108","72.8112236"));
        ITEMS.add(new Location(6, "Wilson College", "The Wilson College, set up in 1832 in Mumbai, is one of India’s oldest colleges, its foundation precedes that of the University of Mumbai, to which it is affiliated, by 25 years. Located opposite Mumbai’s Girgaum Chowpatty, the college building was constructed in 1889 and designed by John Adams in the domestic Victorian Gothic style. It is listed as a Grade III heritage structure in the city. The college offers a variety of courses including Social Sciences, Mass Media, Communications, etc.", R.drawable.loc06,"18.9563862","72.8086483"));
        ITEMS.add(new Location(7, "Taraporewala Aquarium", "Taraporewala Aquarium is India's oldest aquarium and one of the city's main attractions. it hosts marine and freshwater fishes. The aquarium is located on Marine Drive in Mumbai. It had been inaugurated by India’s first president Rajendra Prasad.\n" +
                "It was re-opened after renovation on March 3, 2015. The renovated aquarium has a 12-feet long and 180 degree acrylic glass tunnel. Another attraction is the special pools, where children can touch fish which are harmless. The fish will be kept in large glass tanks, which will be lit with LED lights. \n", R.drawable.loc07,"18.9491331","72.8177871"));
        ITEMS.add(new Location(8, "Oberoi Trident", "A well-known 5-star hotel in Mumbai, Oberoi Trident stands facing the Arabian Ocean, giving its customers it’s plush services alongwith supreme hospitality. Witness to the 26/11 terror attacks, this building stands tall as a symbol of Mumbai’s resilience and firm stance against such atrocities", R.drawable.loc08,"18.9275328","72.8189868"));
        ITEMS.add(new Location(9, "Mantralaya", "Mantralaya is the administrative headquarters of the state government of Maharashtra in South Mumbai, built in 1955. Mantralaya is a seven storeyed building which houses most of the departments of the state government in this building. The Chief Minister and Deputy Chief Minister sit on the sixth floor. The Chief Secretary, sits on the fifth floor. This monument was victim of fire tragedies that occurred in 2012 and 2013, destroying a lot of files and records of vital importance to the administrative officers", R.drawable.loc09,"18.9280047","72.8243719"));
        ITEMS.add(new Location(10, "Jahangir Art Gallery", "Located at Kalaghoda in Southern Mumbai, the Jehangir Art Gallery was donated by Cowasji Jehangir. One of the most famous galleries in Mumbai, the gallery is a prestigious and modern venue for Indian artists to showcase their talent, and hence, has a broad media coverage. Artists have to wait for at least to two years for their artwork to be put here.\n" +
                "Named after Sir Cowasji's late son, Jehangir, the gallery has served as a platform for aspiring artists ever since it was established in 1952. M. F. Hussain, S. H. Raza, etc. who lead India's contemporary art movement, used to be frequenters to the gallery. Constructed in 1952, the structure is an enormous mansion converted into a gallery building. \n", R.drawable.loc10,"18.927461","72.8295143"));
        ITEMS.add(new Location(11, "Asiatic Library", "The Asiatic Society of Mumbai (formerly Asiatic Society of Bombay) is a learned society in the field of Asian studies based in Mumbai, India. It can trace its origin to the Literary Society of Bombay which first met in Mumbai on November 26, 1804, and was founded by Sir James Mackintosh.  It is funded by an annual grant from the Central Government of India. It is famous for its majestic Town Hall which was renovated in 2017 and inaugurated by Chief Minister of Maharashtra, Shri Devendra Fadnavis", R.drawable.loc11,"18.9318373","72.8339669"));
        ITEMS.add(new Location(12, "RBI Museum", "Reserve Bank of India established this museum in. This unique museum, maintained  and funded by the Central Government of India, keeps records of all previously and currently used currency in circulation in Republic of India. The latest addition being the recently demonetized 500 and 1000 Rupee notes, glassed!", R.drawable.loc12,"18.9340356","72.8340527"));
        ITEMS.add(new Location(13, "Maratha Mandir","Maratha Mandir is a Cinema Hall located in Maratha Mandir Marg, Mumbai, India. It is a famous amongst Cinema fans due to its close association with Bollywood since its inception. It opened on 16 October 1958 and has 1000 seats. The theatre created a record after screening the movie Dilwale Dulhaniya Le Jayenge for 1009 weeks since its release in 1995, until 19 February 2015. Maratha Mandir still continues to screen latest Bollywood movies and entertains the masses.",R.drawable.loc13,"18.9713386","72.8199554"));
        ITEMS.add(new Location(14, "Chowpatty","Popularly known as Chowpatty, the beach is located in southern Mumbai along the western railway route. This small sandy beach is a superb place to chill, and a must visit for all tourists who are seeing Marine Drive for the first time. The white sand glistens beautifully in the sunlight, as the adjoining waves dance to the movement of the pleasant breeze. This beach is a retreat from the mind-numbing and chaotic routine of the day-to-day life. Most people visit this place to unwind and relax after a long, strenuous day.",R.drawable.loc14,"19.1235726","72.8338993"));
    }

    public static class Location {
        public final int id;
        public final String name;
        public final String details;
        public int image;

        public String latitude;
        public String longitude;

        public Location(int id, String name, String details, int imageRes,String latitude,String longitude) {
            this.id = id;
            this.name = name;
            this.details = details;
            this.image = imageRes;
            this.latitude=latitude;
            this.longitude=longitude;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public static String getUsernameHashedKey(Context context)
    {
        pref = context.getSharedPreferences(AppConstants.PREFS, IntroActivity.MODE_PRIVATE);
        final String name = pref.getString(AppConstants.PREFS_USERNAME,"Testing 123");
        return name;
    }
}
