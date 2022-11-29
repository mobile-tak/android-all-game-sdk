package com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdnetwork

import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdcontroller.apiConstants.ApiConstantsShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.gameUserUpdateToken.TokenRequestShabdjal
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.gameUserUpdateToken.TokenResponse
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.submitGame.SubmitGameResponseShabd
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.submitGame.SubmitGameReuquestShabd
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.deleteAccountShabdjaal.DeleteAccShabdjaalRequest
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdmodel.deleteAccountShabdjaal.DeleteAccShabdjaalResponse

import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.leader_board_activity.GetLeaderBoardCrossWordResponse
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.leader_board_activity.GetLeaderBoardShabdRequest
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity.SignUpRequest
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.sign_upActivity.GameUserSignupResponse
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.VargPaheliStartGame.GetCrossWordModel
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalGameRequest
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.ShbdjaalPlayGame.ShabdjaalGameResponse
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.PastPuzzleShabdjaalRequest
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.PastPuzzleShabdjaalResponse
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.monthlyPuzzleActivity.response.PastMonthlyPuzzleShabdjaalRequest
import com.tvtoday.gamelibrary.shabdjaalgamesdk.shabdviews.activities.past_puzzles_shabdjal.monthlyPuzzleActivity.response.PastMonthlyPuzzleShabdjaalResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiServiceShabdjal {

    @GET(ApiConstantsShabdjal.GET_SHABDJAL)
    fun getCrossWord(): Observable<GetCrossWordModel?>?

    @GET
    fun getSplitedShabdjal(@Url string: String) : Observable<ShabdjaalGameResponse?>?

    @POST(ApiConstantsShabdjal.GAME_SUBMIT_SHABDJAL)
    fun gameSubmitCrossWord(@Body body: SubmitGameReuquestShabd?): Observable<SubmitGameResponseShabd?>?

    /*@POST(Constants.SIGNUP_GAME_USER_CROSSWORD)
    fun signupGameUser(@Body gameUserRequestModel: GameUserRequestModel?): Observable<GameUserSignupResponse?>?*/

    @POST(ApiConstantsShabdjal.SIGNUP_GAME_USER_SHABDJAL)
    fun signUpUser(@Body body: SignUpRequest?): Observable<GameUserSignupResponse?>?

    @POST(ApiConstantsShabdjal.GET_PAST_PUZZLE_SHABDJAAL)
    fun getPastPuzzle(@Body body: PastPuzzleShabdjaalRequest?): Observable<PastPuzzleShabdjaalResponse?>?

    @POST(ApiConstantsShabdjal.GET_PAST_MONTHLY_PUZZLE_SHABDJAAL)
    fun getPastMonthlyPuzzle(@Body body: PastMonthlyPuzzleShabdjaalRequest?): Observable<PastMonthlyPuzzleShabdjaalResponse?>?

    @POST(ApiConstantsShabdjal.GET_LEADERBOARD_SHABDJAL)
    fun getLeaderBoardCrossWord(@Body body: GetLeaderBoardShabdRequest?): Observable<GetLeaderBoardCrossWordResponse?>?

    @POST(ApiConstantsShabdjal.GAME_SUBMIT_SHABDJAL)
    fun gameSubmitShabdjaal(@Body body: SubmitGameReuquestShabd?): Observable<SubmitGameResponseShabd?>?


    @POST(ApiConstantsShabdjal.GAME_USER_UPDATE_TOKEN_SHABDJAL)
    fun gameUserUpdateShabdToken(@Body body: TokenRequestShabdjal?): Observable<TokenResponse?>?


    @POST(ApiConstantsShabdjal.GET_SHABDJAAL_GAME)
    fun getShabdjaalGame(@Body body: ShabdjaalGameRequest?) : Observable<ShabdjaalGameResponse?>?


    @POST(ApiConstantsShabdjal.DELETE_ACCOUNT)
    fun deleteShabdjaalAccount(@Body body: DeleteAccShabdjaalRequest?): Observable<DeleteAccShabdjaalResponse?>?
}

