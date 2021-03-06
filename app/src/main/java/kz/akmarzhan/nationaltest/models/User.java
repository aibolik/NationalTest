package kz.akmarzhan.nationaltest.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Akmarzhan Raushanova on 5/21/17.
 */

public class User extends RealmObject {

    @PrimaryKey
    @SerializedName("objectId")
    private String objectId;

    @SerializedName("name")
    private String name;

    @SerializedName("email")
    private String email;

    private int exp;

    public User() {

    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }
}
