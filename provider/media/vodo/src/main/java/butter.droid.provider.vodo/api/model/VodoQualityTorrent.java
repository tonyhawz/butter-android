package butter.droid.provider.vodo.api.model;

import com.google.gson.annotations.SerializedName;

public class VodoQualityTorrent {

    @SerializedName("provider")
    private String provider;
    @SerializedName("filesize")
    private String filesize;
    @SerializedName("size")
    private String size;
    @SerializedName("peer")
    private String peer;
    @SerializedName("seed")
    private String seed;
    @SerializedName("url")
    private String url;

    public String getProvider() {
        return provider;
    }

    public String getFilesize() {
        return filesize;
    }

    public String getSize() {
        return size;
    }

    public String getPeer() {
        return peer;
    }

    public String getSeed() {
        return seed;
    }

    public String getUrl() {
        return url;
    }


//    @Override
//    public String toString() {
//        return "VodoQualityTorrent{" +
//                "provider='" + provider + '\'' +
//                ", filesize='" + filesize + '\'' +
//                ", size='" + size + '\'' +
//                ", peer='" + peer + '\'' +
//                ", seed='" + seed + '\'' +
//                ", url='" + url + '\'' +
//                '}';
//    }
}
