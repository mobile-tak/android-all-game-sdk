package com.tvtoday.gamelibrary.crosswordgamesdk.controller.apiConstants

class ApiConstants {

    companion object {
        //BASE URL
       // const val BASE_URL = "https://alphamtappfeed.mobiletak.com/appapi/nt/"
     //   const val BASE_URL = "https://mtappfeed.mobiletak.com/appapi/nt/"

        //new node url--------------
        //const val BASE_URL = "https://cmsapi-dev.mobiletak.com/"
        //new url-----
      // const val BASE_URL = "https://staging-api-takgame.tak.live/"

        //new production url-----
        const val BASE_URL = "https://api-takgame.tak.live"

        const val GET_CROSSWORD = "get_crossword"
        const val SIGNUP_GAME_USER_CROSSWORD = "signup_gameuser_crossword"
        const val GET_LEADERBOARD_CROSSWORD = "get_leaderboard_crossword"

        const val GAME_SUBMIT_CROSSWORD = "game_submit_crossword"

        const val GAME_USER_UPDATE_TOKEN_CROSSWORD = "gameuser_update_token_crossword"

        const val DELETE_SHABDHAM_USER = "delete_shabdam_user"
        const val DELETE_CROSSWORD_USER = "delete_crossword_user"

        const val GET_PAST_PUZZLE = "get_past_puzzle_crossword"
        const val GET_MONTHLY_PAST_PUZZLE = "get_past_puzzle_crossword_by_month"

    }
}