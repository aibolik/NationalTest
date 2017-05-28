package kz.akmarzhan.nationaltest.models;

import java.util.HashMap;

import kz.akmarzhan.nationaltest.utils.Logger;

/**
 * Created by aibol on 5/21/17.
 */

public class UserPredmet {

    private String objectId;
    private int exp;
    private int lastTestId;
    private Predmet predmet;

    public UserPredmet() {

    }

    @Override public String toString() {
        return "UserPredmet{" +
                "objectId='" + objectId + '\'' +
                ", exp=" + exp +
                ", lastTestId=" + lastTestId +
                ", predmet=" + predmet +
                '}';
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getLastTestId() {
        return lastTestId;
    }

    public void setLastTestId(int lastTestId) {
        this.lastTestId = lastTestId;
    }

    public Predmet getPredmet() {
        return predmet;
    }

    public void setPredmet(Predmet predmet) {
        this.predmet = predmet;
    }

    public static UserPredmet createFromMap(HashMap<String, Object> map) {
        UserPredmet userPredmet = new UserPredmet();
        for(HashMap.Entry<String, Object> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "objectId":
                    userPredmet.setObjectId((String) entry.getValue());
                    break;
                case "lastTestId":
                    userPredmet.setLastTestId((Integer) entry.getValue());
                    break;
                case "exp":
                    userPredmet.setExp((Integer) entry.getValue());
                    break;
                case "predmet":
                    userPredmet.setPredmet((Predmet) entry.getValue());
//                    userPredmet.setPredmet(Predmet.createPredmetFromMap((HashMap<String, Object>) entry.getValue()));
                    break;
            }
        }
        Logger.d("UserPredmet", "Predmet: " + userPredmet);
        return userPredmet;
    }
}
