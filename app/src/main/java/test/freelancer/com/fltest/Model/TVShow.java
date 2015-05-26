package test.freelancer.com.fltest.Model;

/**
 * Created by Android 17 on 5/26/2015.
 */
public class TVShow {

    String name;
    String startTime;
    String endTime;
    String channel;
    String rating;

    public TVShow() {
    }

    public TVShow(String name, String startTime, String endTime, String channel, String rating) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.channel = channel;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
