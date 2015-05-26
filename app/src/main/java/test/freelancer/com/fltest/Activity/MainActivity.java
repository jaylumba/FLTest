package test.freelancer.com.fltest.Activity;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

import test.freelancer.com.fltest.Adapter.TVShowAdapter;
import test.freelancer.com.fltest.AsyncTask.DownloadTVShowTask;
import test.freelancer.com.fltest.Constants.Constants;
import test.freelancer.com.fltest.Database.DBHandler;
import test.freelancer.com.fltest.R;


public class MainActivity extends Activity implements BaseActivity {

    Context l_context;
    DBHandler dbHandler;
    ListView lv_tvshow;

    int currentFirstVisibleItem;
    int currentVisibleItemCount;
    int currentTotalItemCount;
    int currentScrollState;
    public static boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l_context = this;
        dbHandler = new DBHandler(this, null, null, 1);
        castViews();
        setEventListeners();
        initialize();

    }

    @Override
    public void castViews() {
        lv_tvshow = (ListView) findViewById(R.id.lv_tvshow);
    }

    @Override
    public void setEventListeners() {
        lv_tvshow.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                currentFirstVisibleItem = firstVisibleItem;
                currentVisibleItemCount = visibleItemCount;
                currentTotalItemCount = totalItemCount;
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                currentScrollState = scrollState;
                isScrollCompleted();
            }

            private void isScrollCompleted() {

                final int lastItem = currentFirstVisibleItem + currentVisibleItemCount;
                if (lastItem == currentTotalItemCount && currentScrollState == SCROLL_STATE_IDLE) {
                    // you have reached end of list, load more data
                    /*** In this way I detect if there's been a scroll which has completed ***/
                    /*** do the work for load more date! ***/

                    Log.i("MainActivity", "End Scroll");

                    if (!isLoading) {
                        isLoading = true;
                        if (Constants.loadComplete == false) {
                            String url_start = Constants.REQUEST_URL + Constants.START;
                            if (isOnline() == true) {
                                DownloadTVShowTask downloadTVShowTask = new DownloadTVShowTask(l_context, url_start, Constants.METHOD_GET, new ArrayList<NameValuePair>());
                                downloadTVShowTask.execute();
                            } else {
                                Toast.makeText(l_context, "No internet connection!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public void initialize() {
        Constants.loadComplete = false;
        Constants.TOTAL_COUNT = 0;
        Constants.START = String.valueOf(Constants.TOTAL_COUNT);
        Constants.tvList.clear();
        Constants.tv_adapter = new TVShowAdapter(MainActivity.this,
                Constants.tvList);
        lv_tvshow.setAdapter(Constants.tv_adapter);

        String url_start = Constants.REQUEST_URL + Constants.START;
        if (isOnline() == true) {
            DownloadTVShowTask downloadTVShowTask = new DownloadTVShowTask(l_context, url_start, Constants.METHOD_GET, new ArrayList<NameValuePair>());
            downloadTVShowTask.execute();
        } else {

            if (Constants.tvList.size() > 0){
                Toast.makeText(l_context, "No internet connection!\nLoading data offline.", Toast.LENGTH_LONG).show();
                Constants.tvList = dbHandler.getAllTVShow();
                Constants.tv_adapter = new TVShowAdapter(MainActivity.this,
                        Constants.tvList);
                lv_tvshow.setAdapter(Constants.tv_adapter);
                Constants.tv_adapter.notifyDataSetChanged();
            }else{
                Toast.makeText(l_context, "No internet connection!\nPlease connect to internet to download data.", Toast.LENGTH_LONG).show();
            }

        }
    }

    private boolean isOnline() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
