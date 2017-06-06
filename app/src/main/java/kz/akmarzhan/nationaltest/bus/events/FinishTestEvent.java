package kz.akmarzhan.nationaltest.bus.events;

/**
 * Created by Akmarzhan Raushanova on 5/28/17.
 */

public class FinishTestEvent {

    public String userId;
    public String predmetObjectId;
    public int score;
    public int testId;

    public FinishTestEvent(String userId, String predmetObjectId, int score, int testId) {
        this.userId = userId;
        this.predmetObjectId = predmetObjectId;
        this.score = score;
        this.testId = testId;
    }
}
