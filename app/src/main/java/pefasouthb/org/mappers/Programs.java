package pefasouthb.org.mappers;

import android.os.Parcel;
import android.os.Parcelable;

public class Programs implements Parcelable {

    private int id;
    private String name;
    private String description;
    private String photo;

    public Programs(int id, String name, String description, String photo){
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    protected Programs(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        photo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(photo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Programs> CREATOR = new Creator<Programs>() {
        @Override
        public Programs createFromParcel(Parcel in) {
            return new Programs(in);
        }

        @Override
        public Programs[] newArray(int size) {
            return new Programs[size];
        }
    };

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getPhoto() {
        return photo;
    }
}
