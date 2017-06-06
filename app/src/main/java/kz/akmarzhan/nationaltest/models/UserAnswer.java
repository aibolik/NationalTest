package kz.akmarzhan.nationaltest.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Akmarzhan Raushanova on 5/28/17.
 */

public class UserAnswer {

    public char singleAnswer;
    public Map<String, Boolean> multipleAnswer;

    public UserAnswer() {
        singleAnswer = '0';
        multipleAnswer = new HashMap<>();
        multipleAnswer.put("a", false);
        multipleAnswer.put("b", false);
        multipleAnswer.put("c", false);
        multipleAnswer.put("d", false);
        multipleAnswer.put("e", false);
    }

}
