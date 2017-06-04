package kz.akmarzhan.nationaltest.models;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Aibol Kussain on 5/20/2017.
 * Working on NationalTest. MobiLabs
 * You can contact me at: aibolikdev@gmail.com
 */

public class Predmet {



    private String objectId;

    private int id;
    private String name;
    private List<Test> tests;

    private boolean isSelected;

    public Predmet() {

    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override public String toString() {
        return "Predmet{" +
                "objectId='" + objectId + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static Predmet createPredmetFromMap(HashMap<String, Object> map) {
        Predmet predmet = new Predmet();
        for(HashMap.Entry<String, Object> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "objectId":
                    predmet.setObjectId((String) entry.getValue());
                    break;
                case "name":
                    predmet.setName((String) entry.getValue());
                    break;
            }
        }
        return predmet;
    }
}
