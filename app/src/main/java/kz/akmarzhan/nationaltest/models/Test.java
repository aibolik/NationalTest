package kz.akmarzhan.nationaltest.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Akmarzhan Raushanova on 5/27/17.
 */

public class Test implements Parcelable {

    private String objectId;

    private int id;
    private List<Question> questions;

    public Test() {

    }

    protected Test(Parcel in) {
        objectId = in.readString();
        id = in.readInt();
        questions = new ArrayList<>();
        in.readList(questions, Question.class.getClassLoader());
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeInt(id);
        dest.writeList(questions);
    }
}
