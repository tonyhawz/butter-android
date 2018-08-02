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

package butter.droid.provider.vodo;

import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butter.droid.provider.AbsMediaProvider;
import butter.droid.provider.base.filter.Filter;
import butter.droid.provider.base.filter.Genre;
import butter.droid.provider.base.filter.Sorter;
import butter.droid.provider.base.model.Format;
import butter.droid.provider.base.model.Media;
import butter.droid.provider.base.model.Movie;
import butter.droid.provider.base.model.Torrent;
import butter.droid.provider.base.nav.NavItem;
import butter.droid.provider.base.paging.ItemsWrapper;
import butter.droid.provider.base.paging.Paging;
import butter.droid.provider.base.util.Optional;
import butter.droid.provider.filter.Pager;
import butter.droid.provider.vodo.api.VodoService;
import butter.droid.provider.vodo.api.model.VodoMovie;
import butter.droid.provider.vodo.api.model.VodoQualityTorrent;
import butter.droid.provider.vodo.api.model.VodoResponse;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.functions.Function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class VodoProvider extends AbsMediaProvider {

    private static final Sorter SORTER_SEEDS = new Sorter("seeds", R.string.sorter_vodo_popularity);
    private static final Sorter SORTER_YEAR = new Sorter("year", R.string.sorter_vodo_year);
    private static final Sorter SORTER_DATE_ADDED = new Sorter("date_added", R.string.sorter_vodo_date_added);
    private static final Sorter SORTER_RATING = new Sorter("rating", R.string.sorter_vodo_rating);
    private static final Sorter SORTER_TITLE = new Sorter("title", R.string.sorter_vodo_alphabet);
    private static final Sorter SORTER_TRENDING = new Sorter("trending_score", R.string.sorter_vodo_trending);
    private static final List<Sorter> SORTERS = Arrays.asList(SORTER_SEEDS, SORTER_YEAR, SORTER_DATE_ADDED, SORTER_RATING, SORTER_TITLE,
            SORTER_TRENDING);
    private static final List<Genre> GENRES = Arrays.asList(Genre.DOCUMENTARY, Genre.DRAMA, Genre.HORROR, Genre.SCI_FI, Genre.THRILLER);
    private static final List<NavItem> NAV_ITEMS = Arrays.asList(
            new NavItem(R.drawable.filter_popular_now, R.string.sorter_vodo_popularity, SORTER_SEEDS),
            new NavItem(R.drawable.filter_release_date, R.string.sorter_vodo_year, SORTER_YEAR),
            new NavItem(R.drawable.filter_updated, R.string.sorter_vodo_date_added, SORTER_DATE_ADDED),
            new NavItem(R.drawable.filter_top_rated, R.string.sorter_vodo_rating, SORTER_RATING),
            new NavItem(R.drawable.filter_a_to_z, R.string.sorter_vodo_alphabet, SORTER_TITLE),
            new NavItem(R.drawable.filter_trending, R.string.sorter_vodo_trending, SORTER_TRENDING)
    );

    private static final int ITEMS_PER_PAGE = 30;

    private final VodoService vodoService;

    public VodoProvider(final VodoService vodoService) {
        this.vodoService = vodoService;
    }

    @NonNull
    @Override
    public Single<ItemsWrapper> items(@Nullable final Filter filter, @Nullable Pager pager) {

        String query = null;
        String genre = null;
        String sorter = null;

        if (filter != null) {
            if (filter.getGenre() != null) {
                genre = filter.getGenre().getKey();
            }

            if (filter.getSorter() != null) {
                sorter = filter.getSorter().getKey();
            }

            query = filter.getQuery();
        }

        final int page;
        if (pager != null && pager.getEndCursor() != null) {
            page = Integer.parseInt(pager.getEndCursor());
        } else {
            page = 0;
        }

//        vodoService.fetchMovies(query, genre, sorter, null, null, ITEMS_PER_PAGE, 1)
//                .flattenAsObservable(m -> m)
//                .map(this::mapVodoMovie)
//                .subscribe(m -> Log.d("matias", m.getTitle()));

        return vodoService.fetchMovies(query, genre, sorter, null, null, ITEMS_PER_PAGE, page + 1)
                .flattenAsObservable(m -> m)
                .map(this::mapVodoMovie)
                .cast(Media.class)
                .toList()
                .map(m -> new ItemsWrapper(m, new Paging(String.valueOf(page + 1), m.size() == ITEMS_PER_PAGE)));
    }

    @NonNull
    @Override
    public Single<Media> detail(final Media media) {
        return Single.just(media);
    }

    @NonNull
    @Override
    public Maybe<List<Sorter>> sorters() {
        return Maybe.just(SORTERS);
    }

    @NonNull
    @Override
    public Maybe<List<Genre>> genres() {
        return Maybe.just(GENRES);
    }

    @NonNull
    @Override
    public Maybe<List<NavItem>> navigation() {
        return Maybe.just(NAV_ITEMS);
    }

    @NonNull
    @Override
    public Single<Optional<Sorter>> getDefaultSorter() {
        return Single.just(Optional.of(SORTER_SEEDS));
    }

    private Movie mapVodoMovie(@NonNull VodoMovie vodoMovie) {

//        Log.d("matias", "map " + vodoMovie.getTitle());
//
//        Log.d("matias", vodoMovie.toString());
        ArrayList<Torrent> torrents = new ArrayList<>();
        for (Map<String, VodoQualityTorrent> v : vodoMovie.getTorrents().values()) {
            for (String k : v.keySet()) {
                VodoQualityTorrent t = v.get(k);
                torrents.add(new Torrent(
                        t.getUrl(),
                        parseFormat(k),
                        0,
                        Long.parseLong(t.getSize()),
                        Integer.parseInt(t.getPeer()),
                        Integer.parseInt(t.getSeed())));
            }
        }
        return new Movie(
                vodoMovie.getImbdCode(),
                vodoMovie.getTitle(),
                Integer.parseInt(vodoMovie.getYear()),
                new Genre[0],
                100 / 10f,
                vodoMovie.getImages().getPoster(),
                vodoMovie.getImages().getFanart(),
                vodoMovie.getSynopsis(),
                torrents.toArray(new Torrent[torrents.size()]),
                vodoMovie.getTrailer());
    }

    private Format parseFormat(@Nullable String vodoQuality) {
        int formatType;
        int quality;
        if ("3D".equals(vodoQuality)) {
            formatType = Format.FORMAT_3D;
            quality = 0;
        } else {
            formatType = Format.FORMAT_NORMAL;
            if (vodoQuality != null) {
                try {
                    quality = Integer.parseInt(vodoQuality.substring(0, vodoQuality.indexOf('p')));
                } catch (NumberFormatException e) {
                    quality = 0;
                }
            } else {
                quality = 0;
            }
        }

        return new Format(quality, formatType);
    }

}
