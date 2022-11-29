package com.tvtoday.gamelibrary.shabdamgamesdk.network;



import com.tvtoday.gamelibrary.shabdamgamesdk.model.GetWordRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.SignupRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.adduser.AddUserRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.adduser.AddUserResponse;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.deleteAccount.DeleteAccountRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.deleteAccount.DeleteAccountResponse;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.dictionary.CheckWordDicRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.dictionary.CheckWordDicResponse;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.game_user_update_token.UpdateUserTokenRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.game_user_update_token.UpdateUserTokenResponse;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.gamesubmit.GameSubmitResponse;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.gamesubmit.SubmitGameRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.getwordresp.GetWordResponse;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.leaderboard.GetLeaderboardList;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.leaderboard.GetLeaderboardRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.statistics.StatisticsMainModel;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.word.WordResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {

    @POST("get_words")
    Observable<GetWordResponse> fetchNewWord(@Body GetWordRequest body);

   /* @POST("get_words")
    Observable<GetWordResponse> fetchEnglisNewWord(@Url String url);*/

    @POST("get_leaderboard")
    Observable<GetLeaderboardList> getLeaderBoardAPIList(@Body GetLeaderboardRequest body);

    @POST("get_streak")
    Observable<StatisticsMainModel> getStreakData(@Body GetLeaderboardRequest body);

    @POST("add_gameuser")
    Observable<AddUserResponse> addUser(@Body AddUserRequest body);

    @POST("check_words")
    Observable<CheckWordDicResponse> checkWord(@Body CheckWordDicRequest body);

    @POST("game_submit")
    Observable<GameSubmitResponse> submitGame(@Body SubmitGameRequest body);

    @POST("signup_gameuser")
    Observable<AddUserResponse> signUpUser(@Body SignupRequest body);

    @POST("get_gamewordsid")
    Observable<WordResponse> fetchWordFromWordId(@Body CheckWordDicRequest body);

    @POST("gameuser_update_token")
    Observable<UpdateUserTokenResponse> updateUserToken(@Body UpdateUserTokenRequest body);

    @POST("delete_shabdam_user")
    Observable<DeleteAccountResponse> deleteAccount(@Body DeleteAccountRequest body);

    /*@GET
    Observable<GetWordResponse> fetchEnglisNewWord(@Url String url);*/



}
