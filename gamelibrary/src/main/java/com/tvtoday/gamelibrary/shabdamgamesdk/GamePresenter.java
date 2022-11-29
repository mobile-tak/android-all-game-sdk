package com.tvtoday.gamelibrary.shabdamgamesdk;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;


import com.tvtoday.gamelibrary.shabdamgamesdk.db.DatabaseClient;
import com.tvtoday.gamelibrary.shabdamgamesdk.db.Task;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.GetWordRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.SignupRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.adduser.AddUserRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.dictionary.CheckWordDicRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.game_user_update_token.UpdateUserTokenRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.gamesubmit.SubmitGameRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.model.leaderboard.GetLeaderboardRequest;
import com.tvtoday.gamelibrary.shabdamgamesdk.network.ApiService;
import com.tvtoday.gamelibrary.shabdamgamesdk.network.RetrofitClient;
import com.tvtoday.gamelibrary.shabdamgamesdk.pref.CommonPreference;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class GamePresenter {
    private GameView gameView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ApiService apiService = RetrofitClient.getInstance();
    private Context context;
    private boolean firstLogin;


    public GamePresenter(GameView gameView, Context context){
        this.gameView = gameView;
        this.context = context;
    }


    public void fetchNewWord(Context context, GetWordRequest request){
        if(GameDataManager.getInstance().getDataList() != null && GameDataManager.getInstance().getDataList().size()>0){
            if(gameView != null){
                gameView.hideProgress();
            }
          if(GameDataManager.getInstance().getDataList().get(0) != null && GameDataManager.getInstance().getDataList().get(0).getWords() != null){
              gameView.onWordFetched(GameDataManager.getInstance().getDataList().get(0));
          }
        }else {
            if(gameView != null){
                gameView.showProgress();
            }

            compositeDisposable.add(DatabaseClient.getInstance(context.getApplicationContext()).getAppDatabase().taskDao().getAll()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(response -> {
                        ArrayList<String> wordIdList = new ArrayList<>();
                        if(response != null ){
                            if(response.size() > 0){
                                for (int i = 0; i <response.size() ; i++) {
                                    wordIdList.add(response.get(i).getWordId());
                                }
                            }
                        }

                        request.setWordId(wordIdList);
                        callWordAPI(request);
                    }, throwable -> {
                        callWordAPI(request);
                    }));
        }
    }

/*    public void fetchEnglish(){
        compositeDisposable.add(apiService.fetchEnglisNewWord("https://mocki.io/v1/89ebc624-41ab-459a-89b7-39af0f4d3aa8")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    Log.e("English_Game",response.getData().toString());
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                    if(response != null && response.getData() != null && response.getData() != null && response.getData().size() > 0){

                        if(!response.getWoord_status().equalsIgnoreCase("true")){

                            boolean isTutShown = CommonPreference.getInstance(context.getApplicationContext()).getBoolean(CommonPreference.Key.IS_TUTORIAL_SHOWN, false);
                            boolean isRuleShown = CommonPreference.getInstance(context.getApplicationContext()).getBoolean(CommonPreference.Key.IS_RULE_SHOWN, false);

                            String applicationId = CommonPreference.getInstance(context.getApplicationContext()).getPackageString("applicationId");
                            String appUniqueId = CommonPreference.getInstance(context.getApplicationContext()).getUniqueAppId();

                            CommonPreference.getInstance(context.getApplicationContext()).clear();

                            CommonPreference.getInstance(context.getApplicationContext()).put(CommonPreference.Key.IS_TUTORIAL_SHOWN, isTutShown);
                            CommonPreference.getInstance(context.getApplicationContext()).put(CommonPreference.Key.IS_RULE_SHOWN, isRuleShown);
                            CommonPreference.getInstance(context.getApplicationContext()).put("applicationId", applicationId);
                            CommonPreference.getInstance(context.getApplicationContext()).put("appUniqueId", appUniqueId);
                        }
                        GameDataManager.getInstance().addData(response.getData());
                        if(GameDataManager.getInstance().getDataList().get(0) != null && GameDataManager.getInstance().getDataList().get(0).getWords() != null){
                            gameView.onWordFetched(response.getData().get(0));
                        }
                    }
                }, throwable -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                }));
    }*/

    public void callWordAPI(GetWordRequest request){
        if(context != null
                && !TextUtils.isEmpty(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID))){
            request.setApp_id(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID));

            if(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_LANGUAGE)!=null &&
                    CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")){
                request.setLanguageId("1");
            }else{
                request.setLanguageId("2");
            }

            //request.setApp_id(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID));
        }

        compositeDisposable.add(apiService.fetchNewWord(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }

                    if(response != null && response.getData() != null &&response.getData() != null && response.getData().size() > 0){

                        if(!response.getWoord_status().equalsIgnoreCase("true")){

                            boolean isTutShown = CommonPreference.getInstance(context.getApplicationContext()).getBoolean(CommonPreference.Key.IS_TUTORIAL_SHOWN, false);
                            boolean isRuleShown = CommonPreference.getInstance(context.getApplicationContext()).getBoolean(CommonPreference.Key.IS_RULE_SHOWN, false);

                            String applicationId = CommonPreference.getInstance(context.getApplicationContext()).getPackageString("applicationId");
                            String appUniqueId = CommonPreference.getInstance(context.getApplicationContext()).getUniqueAppId();

                            CommonPreference.getInstance(context.getApplicationContext()).clear();

                            CommonPreference.getInstance(context.getApplicationContext()).put(CommonPreference.Key.IS_TUTORIAL_SHOWN, isTutShown);
                            CommonPreference.getInstance(context.getApplicationContext()).put(CommonPreference.Key.IS_RULE_SHOWN, isRuleShown);
                          //  CommonPreference.getInstance(context.getApplicationContext()).put("applicationId", applicationId);
                            CommonPreference.getInstance(context.getApplicationContext()).put("appUniqueId", appUniqueId);
                        }
                        GameDataManager.getInstance().addData(response.getData());
                        gameView.onWordFetched(response.getData().get(0));

                    }
                }, throwable -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                }));
    }

    public void fetchLeaderBoardList(String game_id, String lanId){

        GetLeaderboardRequest request=new GetLeaderboardRequest();
        request.setGameUserId(game_id);
        request.setLanguageId(lanId);

        if(context != null
                && !TextUtils.isEmpty(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID))){
            request.setApp_id(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID));
        }

        if(gameView != null){
            gameView.showProgress();
        }

        compositeDisposable.add(apiService.getLeaderBoardAPIList(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                    if(response != null ){

                        gameView.onGetLeaderBoardListFetched(response.getData());
                    }
                }, throwable -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                }));
    }

    public void fetchStatisticsData(String game_id, String lan){
        GetLeaderboardRequest request=new GetLeaderboardRequest();
        request.setGameUserId(game_id);
        request.setLanguageId(lan);
        if(gameView != null){
            gameView.showProgress();
        }
        if(context != null
                && !TextUtils.isEmpty(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID))){
            request.setApp_id(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID));
        }
        compositeDisposable.add(apiService.getStreakData(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                    if(response != null ){

                        gameView.onStatisticsDataFetched(response.getData());
                    }
                }, throwable -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                }));
    }

    public void addUser(AddUserRequest addUserRequest){

        if(gameView != null){
            gameView.showProgress();
        }
        if(context != null
                && !TextUtils.isEmpty(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID))){
            addUserRequest.setApp_id(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID));
        }
        compositeDisposable.add(apiService.addUser(addUserRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                    if(response != null ){
                        gameView.onAddUser(response.getData());
                    }
                }, throwable -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                }));
    }

    public void submitGame(SubmitGameRequest submitGameRequest){

        if(gameView != null){
            gameView.showProgress();
        }
        if(context != null
                && !TextUtils.isEmpty(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID))){
            submitGameRequest.setApp_id(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID));
        }
        GameDataManager.getInstance().removeData();
        compositeDisposable.add(apiService.submitGame(submitGameRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                    if(response != null ){
                        gameView.onGameSubmit();
                    }
                }, throwable -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                }));
    }

    public void setFirstLogin(boolean firstLogin){
        this.firstLogin = firstLogin;
    }

    public void signUpUser(SignupRequest addUserRequest){

        if(gameView != null){
            gameView.showProgress();
        }

        if(context != null
                && !TextUtils.isEmpty(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID))){
            addUserRequest.setApp_id(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID));
        }
        compositeDisposable.add(apiService.signUpUser(addUserRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }

                    if(response != null ){

                        String userId = response.getData().getId().toString();

                        UpdateUserTokenRequest updateTokenRequest = new UpdateUserTokenRequest();
                        updateTokenRequest.setGameUserId(userId);
                        updateTokenRequest.setDeviceType(Constants.DEVICE_TYPE);
                        updateTokenRequest.setDeviceToken(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.DEVICE_TOKEN));
                        updateTokenRequest.setApp_id(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID));
                        updateUserToken(updateTokenRequest);
                        gameView.onAddUser(response.getData());
                    }

                }, throwable -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                    Log.d("Error",""+throwable);

                }));
    }



    public void checkDictionary(CheckWordDicRequest checkWordDicRequest){
        if(gameView != null){
            gameView.showProgress();
        }
        if(context != null
                && !TextUtils.isEmpty(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID))){
            checkWordDicRequest.setApp_id(CommonPreference.getInstance(context.getApplicationContext()).getString(CommonPreference.Key.SHABDAM_APP_ID));
        }

        compositeDisposable.add(apiService.checkWord(checkWordDicRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                    if(response != null ){
                        gameView.onWordCheckDic(response.getStatus().equalsIgnoreCase("true")?true:false);
                    }
                }, throwable -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                }));

    }


    public void saveIDLocalDB(Context context, Task task){

        compositeDisposable.add(Completable.fromAction(() -> DatabaseClient.getInstance(context.getApplicationContext()).getAppDatabase().taskDao().insert(task))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe());

    }

    public void onDestroy(){

        if(compositeDisposable != null){
            compositeDisposable.clear();
            compositeDisposable.dispose();
        }
        context = null;
    }

    public void updateUserToken(UpdateUserTokenRequest updateUserToken ){

        compositeDisposable.add(apiService.updateUserToken(updateUserToken)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if(response != null ){
                        Log.d("response:updateToken",""+response.getData().getDeviceToken().toString());

                        String lanId="";
                        if(!TextUtils.isEmpty(CommonPreference.getInstance(context).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(context).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")) {
                        lanId ="1";
                        }else{
                            lanId ="2";
                        }
                            fetchLeaderBoardList(updateUserToken.getGameUserId(), lanId);
                    }
                }, throwable -> {
                    if(gameView != null){
                        gameView.hideProgress();
                    }
                }));
    }
}
