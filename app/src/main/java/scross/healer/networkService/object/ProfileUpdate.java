package scross.healer.networkService.object;

/**
 * Created by hanee on 2017-07-31.
 */

public class ProfileUpdate {
    public final String name;
    public final String birth;
    public final String gender;

    public ProfileUpdate(String name, String birth, String gender) {
        this.name = name;
        this.birth = birth;
        this.gender = gender;
    }

}
