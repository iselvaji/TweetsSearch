package mobileiron.com.mobileirontest.data.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Selva on 4/27/2018.
 */

@Entity(tableName = "searchresultshistory")
public class SearchResult implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "searchkey")
    private String key;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "content")
    private String content;

    @ColumnInfo(name = "image_uri")
    private String imageuri;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.id);
        parcel.writeString(this.key);
        parcel.writeString(this.name);
        parcel.writeString(this.content);
        parcel.writeString(this.imageuri);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public SearchResult createFromParcel(Parcel in) {
            return new SearchResult(in);
        }
        public SearchResult[] newArray(int size) {
            return new SearchResult[size];
        }
    };

    public SearchResult(Parcel in){
        this.id = in.readInt();
        this.key = in.readString();
        this.name =  in.readString();
        this.content =  in.readString();
        this.imageuri =  in.readString();
    }

    public SearchResult(){
    }
}