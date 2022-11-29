package com.tvtoday.gamelibrary.shabdamgamesdk;

import com.tvtoday.gamelibrary.shabdamgamesdk.model.getwordresp.Datum;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.leaderboard.LeaderboardListModel;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.statistics.Data;

import java.util.List;

public interface GameView {

    void showProgress();
    void hideProgress();
    void onError(String errorMsg);

    default void onWordFetched(Datum correctWord){

    }

    default void onGetLeaderBoardListFetched(List<LeaderboardListModel> list){

    }

    default void onStatisticsDataFetched(Data data){

    }

    default void onAddUser(com.tvtoday.gamelibrary.shabdamgamesdk.model.adduser.Data data){

    }

    default void onWordCheckDic(boolean isMatched){

    }

    default void onGameSubmit(){

    }
}
