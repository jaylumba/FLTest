package test.freelancer.com.fltest.Constants;

import java.util.ArrayList;

import test.freelancer.com.fltest.Adapter.TVShowAdapter;
import test.freelancer.com.fltest.Model.TVShow;

/**
 * Created by Android 17 on 5/26/2015.
 */
public class Constants {

    public static final String REQUEST_URL = "http://whatsbeef.net/wabz/guide.php?start=";
    public static final String METHOD_GET = "GET";

    public static ArrayList<TVShow> tvList = new ArrayList<TVShow>();
    public static TVShowAdapter tv_adapter = null;

    public static String START = "0";
    public static int TOTAL_COUNT = 0;
    public static boolean loadComplete = false;

}
