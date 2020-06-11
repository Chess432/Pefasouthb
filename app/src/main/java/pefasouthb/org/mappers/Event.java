package pefasouthb.org.mappers;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private int id;
    private String subject;
    private String venue;
    private String description;
    private String date;
    private String image;

    public Event(int id, String subject, String venue, String description, String date, String image){
        this.id = id;
        this.subject = subject;
        this.venue = venue;
        this.description = description;
        this.date = date;
        this.image = image;
    }

    protected Event(Parcel in) {
        id = in.readInt();
        subject = in.readString();
        venue = in.readString();
        description = in.readString();
        date = in.readString();
        image = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getVenue() {
        return venue;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
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
        parcel.writeString(venue);
        parcel.writeString(description);
        parcel.writeString(date);
        parcel.writeString(image);
    }
}
