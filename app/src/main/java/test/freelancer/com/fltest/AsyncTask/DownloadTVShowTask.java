package test.freelancer.com.fltest.AsyncTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import test.freelancer.com.fltest.Activity.MainActivity;
import test.freelancer.com.fltest.Constants.Constants;
import test.freelancer.com.fltest.Database.DBHandler;
import test.freelancer.com.fltest.Model.TVShow;
import test.freelancer.com.fltest.WebServices.WS_Request;

/**
 * Created by Android 17 on 5/26/2015.
 */
public class DownloadTVShowTask extends AsyncTask<String, Integer, Boolean> {

    String url;
    String method;
    List<NameValuePair> request_params;
    JSONObject response;

    Context l_context;
    ProgressDialog dialog;

    DBHandler dbHandler;

    public DownloadTVShowTask(Context context, String url, String method, List<NameValuePair> request_params) {
        this.l_context = context;
        this.url = url;
        this.method = method;
        this.request_params = request_params;
        dbHandler = new DBHandler(l_context, null, null, 1);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog = new ProgressDialog(l_context);
        dialog.setMessage("Updating...");
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {

        try {
            Log.i("url", url);
            Log.i("method", method);
            Log.i("request_params", request_params.toString());
            response = new WS_Request().makeHttpRequest(url, method, request_params);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        if (dialog != null){
            if (dialog.isShowing()){
                dialog.dismiss();
            }
        }

        if (response != null) {
            Log.i("response", response.toString());

            try {
                int count = response.getInt("count");
                JSONArray arr_tvshow = response.getJSONArray("results");

                for (int i = 0; i < arr_tvshow.length(); i++) {

                    JSONObject obj = arr_tvshow.getJSONObject(i);
                    TVShow tvShow = new TVShow();
                    tvShow.setName(obj.getString("name"));
                    tvShow.setStartTime(obj.getString("start_time"));
                    tvShow.setEndTime(obj.getString("end_time"));
                    tvShow.setChannel(obj.getString("channel"));
                    tvShow.setRating(obj.getString("rating"));

                    if (!Constants.tvList.contains(tvShow)) {
                        Constants.tvList.add(tvShow);
                        if (dbHandler.isShowExist(tvShow) == false){
                            dbHandler.addShow(tvShow);
                            Log.i("isShowExist(tvShow)","false");
                        }else{
                            Log.i("isShowExist(tvShow)","true");
                        }
                    }
                }

                if (Constants.TOTAL_COUNT < count){
                    Constants.TOTAL_COUNT += arr_tvshow.length();
                    Constants.START =  String.valueOf(Constants.TOTAL_COUNT);
                    if (Constants.TOTAL_COUNT == count){
                        Constants.loadComplete = true;
                    }
                }
                Constants.tv_adapter.notifyDataSetChanged();
                MainActivity.isLoading = false;
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            Log.i("response", "is null");
        }


    }
}
