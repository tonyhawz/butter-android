/*
 * This file is part of Butter.
 *
 * Butter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Butter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Butter. If not, see <http://www.gnu.org/licenses/>.
 */

package butter.droid.provider.vodo.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class VodoMovie {


//    @SerializedName("SizeByte") private long sizeBytes;
//    @SerializedName("Rating") private int rating;
//    //    @SerializedName("Runtime") private int runtime;
//    @SerializedName("MovieUrl") private String movieUrl;
//    @SerializedName("ImdbUrl") private String imdbUrl;
//    @SerializedName("CoverImage") private String coverImage;
//    @SerializedName("Popularity") private int popularity;
//    @SerializedName("TorrentUrl") private String torrentUrl;
//    @SerializedName("MovieRating") private float movieRating;
//    @SerializedName("Synopsis") private String synopsis;
//    @SerializedName("Tagline") private String tagline;
//    @SerializedName("Genre") private String genre;
//    @SerializedName("Size") private String size;
//    @SerializedName("Quality") private String quality;
//    @SerializedName("MovieTitleClean") private String movieTitleClean;
//    @SerializedName("MovieYear") private int movieYear;
//    @SerializedName("ImdbCode") private String imdbCode;

    @SerializedName("_id") private String id;
    @SerializedName("imdb_id") private String imbdCode;
    @SerializedName("title") private String title;
    @SerializedName("year") private String year;
    @SerializedName("synopsis") private String synopsis;
    @SerializedName("runtime") private String runtime;
    @SerializedName("torrents") private Map<String, Map<String, VodoQualityTorrent>> torrents;

    @SerializedName("trailer") private String trailer;

    @SerializedName("images") private VodoImages images;

    @SerializedName("genres") private String[] genres;

//                    "released": 1455235200,
//                    "trailer": "http://youtube.com/watch?v=9vN6DHB6bJc",
//                    "certification": "R",


    public String getId() {
        return id;
    }

    public String getImbdCode() {
        return imbdCode;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getRuntime() {
        return runtime;
    }


    public Map<String, Map<String, VodoQualityTorrent>> getTorrents() {
        return torrents;
    }

    public VodoImages getImages() {
        return images;
    }

    public String getTrailer() {
        return trailer;
    }


//    @Override
//    public String toString() {
//        return "VodoMovie{" +
//                "id='" + id + '\'' +
//                ", imbdCode='" + imbdCode + '\'' +
//                ", title='" + title + '\'' +
//                ", year='" + year + '\'' +
//                ", synopsis='" + synopsis + '\'' +
//                ", runtime='" + runtime + '\'' +
//                ", torrents=" + torrents +
//                ", trailer='" + trailer + '\'' +
//                ", images=" + images +
//                '}';
//    }

    public String[] getGenres() {
        return genres;
    }
}
