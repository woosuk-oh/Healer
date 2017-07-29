package scross.healer.timeline;

import android.view.View;

import java.util.HashMap;

/**
 * Created by hanee on 2017-07-30.
 */

public class ContentNameHashMap {

    static HashMap<String, String> contentName = new HashMap<>();

    static {
        contentName.put("도입 - 자기 관찰하기", "1");
        contentName.put("초기 트라우마 - 부정적 기억 내보내기", "2");
        contentName.put("초기 트라우마 - 긍정적 정서 떠올리기", "3");
        contentName.put("초기 트라우마 - 감정 조절 배우기","4");
        contentName.put("빅 트라우마 - 트라우마 정화 I","5");
        contentName.put("빅 트라우마 - 트라우마 정화 II", "6");
        contentName.put("빅 트라우마 - 자아 대면하기", "7");
        contentName.put("마무리 - 자아 회복하기", "8");


    }

    static HashMap<String, String> contentName2 = new HashMap<>();

    static { // TODO 작성하다 말았음 -> 나중에 서버에서 받는대로 뿌리면 되기때문에
        contentName2.put("USA", "AMERICA");
        contentName2.put("JPN", "");
        contentName2.put("홍콩", "HKG");
        contentName2.put("이탈리아","ITA");
        contentName2.put("프랑스","FRA");
        contentName2.put("태국", "THA");
        contentName2.put("호주", "AUS");
        contentName2.put("중국", "CHN");
        contentName2.put("필리핀", "PHL");
        contentName2.put("대만", "TWN");

    }

    public String getContentName(String key) {
        return contentName.get(key);
    }

}
