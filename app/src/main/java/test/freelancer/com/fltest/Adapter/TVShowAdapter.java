package test.freelancer.com.fltest.Adapter;

/**
 * Created by Android 17 on 5/26/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import test.freelancer.com.fltest.Model.TVShow;
import test.freelancer.com.fltest.R;


public class TVShowAdapter extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<TVShow> tvshowList;

    public TVShowAdapter(Activity act, List<TVShow> msg) {
        this.activity = act;
        this.tvshowList = msg;

    }

    @Override
    public int getCount() {
        return tvshowList.size();
    }

    @Override
    public Object getItem(int position) {
        return tvshowList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null) {
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.tvshow_listitem, null);

            holder.tv_name = (TextView) convertView
                    .findViewById(R.id.tv_name);
            holder.tv_time = (TextView) convertView
                    .findViewById(R.id.tv_time);
            holder.tv_channel = (TextView) convertView
                    .findViewById(R.id.tv_channel);
            holder.tv_rating = (TextView) convertView
                    .findViewById(R.id.tv_rating);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        TVShow e = tvshowList.get(position);

        holder.tv_name.setText(e.getName());
        holder.tv_channel.setText(e.getChannel());
        holder.tv_time.setText(e.getStartTime() + " - " + e.getEndTime());
        holder.tv_rating.setText(e.getRating());

        return convertView;
    }


    private class ViewHolder {
        TextView tv_name;
        TextView tv_time;
        TextView tv_channel;
        TextView tv_rating;
    }
}

