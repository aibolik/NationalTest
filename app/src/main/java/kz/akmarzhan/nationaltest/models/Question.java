package kz.akmarzhan.nationaltest.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aibol on 5/27/17.
 */

public class Question implements Parcelable {

    public static final int TYPE_SINGLE = 0;
    public static final int TYPE_MULTIPLE = 1;

    private String objectId;

    private String text;
    private int type;
    private String variants;
    private String answers;

    public Question() {

    }

    protected Question(Parcel in) {
        objectId = in.readString();
        text = in.readString();
        type = in.readInt();
        variants = in.readString();
        answers = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getVariants() {
        return variants;
    }

    public void setVariants(String variants) {
        this.variants = variants;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(objectId);
        dest.writeString(text);
        dest.writeInt(type);
        dest.writeString(variants);
        dest.writeString(answers);
    }
}
