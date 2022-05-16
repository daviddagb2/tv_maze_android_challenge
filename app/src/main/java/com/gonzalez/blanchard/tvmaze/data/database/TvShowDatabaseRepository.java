package com.gonzalez.blanchard.tvmaze.data.database;

import com.gonzalez.blanchard.tvmaze.config.ObjectBoxMain;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel;
import com.gonzalez.blanchard.tvmaze.data.model.TvShowModel_;

import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.query.QueryBuilder;

public class TvShowDatabaseRepository {

    public TvShowModel getTvShow(long id){
        try{
            //Se crea el Box de consulta
            Box<TvShowModel> tvShowBox = ObjectBoxMain.get().boxFor(TvShowModel.class);
            QueryBuilder<TvShowModel> builder = tvShowBox.query();
            builder.equal(TvShowModel_.id, id);
            return builder.build().findFirst();
        }catch (Exception ex){
            return null;
        }
    }

    public List<TvShowModel> getFavoritesTvShowDB(){
        List<TvShowModel> tvshowlist;
        try{
            //Se crea el Box de consulta
            Box<TvShowModel> TvShowBox = ObjectBoxMain.get().boxFor(TvShowModel.class);

            //Se crea el querybuilder
            QueryBuilder<TvShowModel> builder = TvShowBox.query();
            builder.equal(TvShowModel_.isFavorite, true);
            builder.order(TvShowModel_.name);
            tvshowlist = builder.build().find();

        }catch (Exception ex){
            tvshowlist = new ArrayList<>();
        }
        return tvshowlist;
    }

    public void setFavoriteTvShow(TvShowModel model){
        Box<TvShowModel> tvShowBox = ObjectBoxMain.get().boxFor(TvShowModel.class);
        QueryBuilder<TvShowModel> builder = tvShowBox.query();
        model.setIsFavorite(true);
        tvShowBox.put(model);
    }

    public void removeFavoriteTvShow(TvShowModel model){
        Box<TvShowModel> tvShowBox = ObjectBoxMain.get().boxFor(TvShowModel.class);
        QueryBuilder<TvShowModel> builder = tvShowBox.query();
        model.setIsFavorite(false);
        tvShowBox.put(model);
    }

    public void saveTvShow(TvShowModel model){
        //Save in box
        Box<TvShowModel> tvShowBox = ObjectBoxMain.get().boxFor(TvShowModel.class);

        QueryBuilder<TvShowModel> builder = tvShowBox.query();
        builder.equal(TvShowModel_.id, model.getId());
        TvShowModel curTvShowModel = builder.build().findFirst();

        if(curTvShowModel != null){
            model.setIsFavorite(curTvShowModel.getIsFavorite());
        }

        tvShowBox.put(model);
    }


}
