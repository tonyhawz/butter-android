package butter.droid.provider.vodo.api.model;

import com.google.gson.annotations.SerializedName;

public class VodoImages {

    @SerializedName("poster") private String poster;
    @SerializedName("fanart") private String fanart;
    @SerializedName("banner") private String banner;

    public String getPoster() {
        return poster;
    }

    public String getFanart() {
        return fanart;
    }

    public String getBanner() {
        return banner;
    }

//    @Override
//    public String toString() {
//        return "VodoImages{" +
//                "poster='" + poster + '\'' +
//                ", fanart='" + fanart + '\'' +
//                ", banner='" + banner + '\'' +
//                '}';
//    }
}
