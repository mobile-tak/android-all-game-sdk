package com.tvtoday.gamelibrary.crosswordgamesdk.network


import com.tvtoday.gamelibrary.crosswordgamesdk.controller.apiConstants.ApiConstants
import com.tvtoday.gamelibrary.crosswordgamesdk.model.delete_account_api.DeleteAccountRequest
import com.tvtoday.gamelibrary.crosswordgamesdk.model.delete_account_api.DeleteAccountResponse
import com.tvtoday.gamelibrary.crosswordgamesdk.model.gameUserUpdateToken.GameUserUpdateTokenRequest
import com.tvtoday.gamelibrary.crosswordgamesdk.model.gameUserUpdateToken.GameUserUpdateTokenResponse
import com.tvtoday.gamelibrary.crosswordgamesdk.model.submitGame.SubmitGameResponse
import com.tvtoday.gamelibrary.crosswordgamesdk.model.submitGame.SubmitGameReuquest
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.leader_board_activity.GetLeaderBoardCrossWordResponse
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.leader_board_activity.GetLeaderBoardRequest
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.PastPuzzleCrossWordRequest
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.PastPuzzleCrossWordResponse
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.monthlyPastPuzzleActivity.PastPuzzleMonthlyCrossWordRequest
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.pastPuzzleCrossWord.monthlyPastPuzzleActivity.PastPuzzleMonthlyCrossWordResponse
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity.SignUpRequest
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.sign_upActivity.GameUserSignupResponse
import com.tvtoday.gamelibrary.crosswordgamesdk.views.activities.vargPaheliStartGame.GetCrossWordModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap
import java.util.HashMap

interface ApiService {

    @GET(ApiConstants.GET_CROSSWORD)
    fun getCrossWord(@QueryMap map: HashMap<String,String>): Observable<GetCrossWordModel?>?

    /*@POST(Constants.SIGNUP_GAME_USER_CROSSWORD)
    fun signupGameUser(@Body gameUserRequestModel: GameUserRequestModel?): Observable<GameUserSignupResponse?>?*/

    @POST(ApiConstants.SIGNUP_GAME_USER_CROSSWORD)
    fun signUpUser(@Body body: SignUpRequest?): Observable<GameUserSignupResponse?>?

    @POST(ApiConstants.GET_PAST_PUZZLE)
    fun getPastPuzzle(@Body body: PastPuzzleCrossWordRequest?): Observable<PastPuzzleCrossWordResponse?>?

    @POST(ApiConstants.GET_MONTHLY_PAST_PUZZLE)
    fun getMonthlyPastPuzzle(@Body body: PastPuzzleMonthlyCrossWordRequest?): Observable<PastPuzzleMonthlyCrossWordResponse?>?

    @POST(ApiConstants.GET_LEADERBOARD_CROSSWORD)
    fun getLeaderBoardCrossWord(@Body body: GetLeaderBoardRequest?): Observable<GetLeaderBoardCrossWordResponse?>?

    @POST(ApiConstants.GAME_SUBMIT_CROSSWORD)
    fun gameSubmitCrossWord(@Body body: SubmitGameReuquest?): Observable<SubmitGameResponse?>?

    @POST(ApiConstants.GAME_USER_UPDATE_TOKEN_CROSSWORD)
    fun gameUserUpdateToken(@Body body: GameUserUpdateTokenRequest?): Observable<GameUserUpdateTokenResponse?>?

    @POST(ApiConstants.DELETE_CROSSWORD_USER)
    fun deleteAccount(@Body body: DeleteAccountRequest?): Observable<DeleteAccountResponse?>?
}