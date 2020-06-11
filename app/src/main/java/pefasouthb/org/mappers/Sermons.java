package pefasouthb.org.mappers;

import android.os.Parcel;
import android.os.Parcelable;

public class Sermons implements Parcelable {
    private int id;
    private String subject;
    private String text;
    private String speaker;
    private String date;
    private String scripture;
    private String image;

    public Sermons(int id, String subject, String text, String speaker, String date, String scripture, String image) {
        this.id = id;
        this.subject = subject;
        this.text = text;
        this.speaker = speaker;
        this.date = date;
        this.image = image;
        this.scripture = scripture;
    }

    protected Sermons(Parcel in) {
        id = in.readInt();
        subject = in.readString();
        text = in.readString();
        speaker = in.readString();
        date = in.readString();
        scripture = in.readString();
        image = in.readString();
    }

    public static final Creator<Sermons> CREATOR = new Creator<Sermons>() {
        @Override
        public Sermons createFromParcel(Parcel in) {
            return new Sermons(in);
        }

        @Override
        public Sermons[] newArray(int size) {
            return new Sermons[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getText() {
        return text;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getDate() {
        return date;
    }

    public String getScripture() {
        return scripture;
    }

    public String getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(subject);
        parcel.writeString(text);
        parcel.writeString(speaker);
        parcel.writeString(date);
        parcel.writeString(scripture);
        parcel.writeString(image);
    }
}
