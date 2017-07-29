package scross.healer.networkService.object;


import scross.healer.timeline.TimelineFragment;

/**
 * Created by hanee on 2017-07-30.
 */

public class TimelineObject extends TimelineFragment {
    public final String name;
    public final String birth;
    public final String emotion;
    public final String gender;

    public final String finish_date1;
    public final String finish_date2;
    public final String finish_date3;
    public final String finish_date4;
    public final String finish_date5;
    public final String finish_date6;
    public final String finish_date7;
    public final String finish_date8;
    public final String finish_date9;

    public TimelineObject(String name, String birth, String emotion, String gender, String finish_date1, String finish_date2, String finish_date3, String finish_date4, String finish_date5, String finish_date6, String finish_date7, String finish_date8, String finish_date9) {
        this.name = name;
        this.birth = birth;
        this.emotion = emotion;
        this.gender = gender;
        this.finish_date1 = finish_date1;
        this.finish_date2 = finish_date2;
        this.finish_date3 = finish_date3;
        this.finish_date4 = finish_date4;
        this.finish_date5 = finish_date5;
        this.finish_date6 = finish_date6;
        this.finish_date7 = finish_date7;
        this.finish_date8 = finish_date8;
        this.finish_date9 = finish_date9;
    }

    public String getName() {
        return name;
    }

    public String getBirth() {
        return birth;
    }

    public String getEmotion() {
        return emotion;
    }
}
