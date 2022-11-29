import PrefDataShabdjal.Key.EMAIL
import PrefDataShabdjal.Key.NAME
import PrefDataShabdjal.Key.PROFILE_IMAGE
import PrefDataShabdjal.Key.UNAME
import android.content.Context
import android.text.TextUtils


object PrefDataShabdjal {
    private const val PREFERENCE = "ShabdjaalPref"

    object Key {
        const val SHABDJAAL_APP_LANGUAGE = "shabdjaal_app_language"
        const val HINDI = "hindi"
        const val ENGLISH = "english"
        const val SHABDJAAL_LANGUAGE = "shabdjaal_language"
        const val SHABDJAAL_APP_ID = "shabdjaal_app_id"

        const val SHABDJAAL_BANNER_AD_ID = "shabdjaal_banner_ad_id"
        const val SHABDJAAL_REWARDED_AD_ID = "shabdjaal_rewarded_ad_id"
        const val SHABDJAAL_INTERTITIAL_ID = "shabdjaal_intertitials_ad_id"


        const val DATE_FOR_LANGUAGE_CANGE_PART = "langauge_change_part"
         const val ISRuleSHow = "IsRuleShow"
         const val IsPlayGuestRuleShow = "isPlayGuestRuleShow"
        const val NAME = "name"
        const val UNAME = "uname"
        const val EMAIL = "email"
        const val PROFILE_IMAGE = "profile_image"
        const val GAME_USER_ID = "game_user_id"
        const val GAME_DATA_DATE = "game_data_date"
        const val IS_DONE = "is_done"
        const val PAUSE_OFF_SET = "pause_offset"
        const val SOUND_STATE = "sound_state"
        const val GUEST_USER = "guest_user"
        const val IS_LOGOUT = "is_logout"
        const val DEVICE_ID = "device_id"

        const val FOR_HURREY_SCREEN = "for_hurrey_Screen"

        const val DEVICE_TOKEN = "device_token"
        const val DEVICE_TYPE = "device_type"

        const val IS_MUSIC_PLAYING = "music_playing"
    }



    fun saveUserDetails(context: Context, name: String, userName: String, email: String, profilePic : String) {
        if (!TextUtils.isEmpty(name)) {
             context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
                .putString(NAME, name).apply()
        }
        if (!TextUtils.isEmpty(name)) {
             context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
                .putString(UNAME, userName).apply()
        }
        if (!TextUtils.isEmpty(email)) {
             context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
                .putString(EMAIL, email).apply()
        }
        if (!TextUtils.isEmpty(profilePic)) {
             context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
                .putString(PROFILE_IMAGE, profilePic).apply()
        }
       /* if(!TextUtils.isEmpty(gameUserId)){
            context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
                .putString(GAME_USER_ID, gameUserId).apply()
        }*/
    }


    fun setBooleanPrefs(context: Context, prefKey: String, value: Boolean) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putBoolean(prefKey, value).apply()
    }


    fun getBooleanPrefs(context: Context, prefKey: String): Boolean {

        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).getBoolean(prefKey, false)
    }




    fun getBoolean(context: Context,key: String?, defaultValue: Boolean): Boolean {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).getBoolean(key, defaultValue)
    }

    /*fun setBooleanMusicPrefs(context: Context, prefKey: String, value: Boolean) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putString(prefKey, value.toString()).apply()
    }


    fun getBooleanMusicPrefs(context: Context, prefKey: String): String? {

        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).getString(prefKey,
            false.toString()
        )
    }*/


    fun getBooleanPrefs(context: Context, prefKey: String, defaultValue: Boolean): Boolean {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getBoolean(prefKey, defaultValue)
    }

    fun setStringPrefs(context: Context, prefKey: String, Value: String) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putString(prefKey, Value).apply()
    }

    fun getStringPrefs(context: Context, prefKey: String): String? {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getString(prefKey, null)
    }

    fun getAppLanguageStringPrefs(context: Context, prefKey: String): String? {
        if(!TextUtils.isEmpty(context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
                .getString(Key.SHABDJAAL_LANGUAGE, null))){
            return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
                .getString(Key.SHABDJAAL_LANGUAGE, null)
        }
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getString(prefKey, null)
    }


    fun getStringPrefs(context: Context, prefKey: String, defaultValue: String): String {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getString(prefKey, defaultValue)
            .toString()
    }

    fun setIntPrefs(context: Context, prefKey: String, value: Int) {
        return context.getSharedPreferences(PREFERENCE, 0).edit().putInt(prefKey, value)
            .apply()
    }

    fun getIntPrefs(context: Context, prefKey: String): Int {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).getInt(prefKey, 0)
    }

    fun getIntPrefss(context: Context, prefKey: String, defaultValue: Int): Int {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getInt(prefKey, defaultValue)
    }

    fun setLongPrefs(context: Context, prefKey: String, value: Long) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putLong(prefKey, value).apply()
    }

    fun getLongPrefs(context: Context, prefKey: String): Long {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getLong(prefKey, 0)
    }

    fun getLongPrefs(context: Context, prefKey: String, defaultValue: Long): Long {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getLong(prefKey, defaultValue)
    }

    fun setFloatPrefs(context: Context, prefKey: String, value: Float) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putFloat(prefKey, value).apply()
    }

    fun getFloatPrefs(context: Context, prefKey: String): Float {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getFloat(prefKey, 0f)
    }

    fun getFloatPrefs(context: Context, prefKey: String, defaultValue: Long): Float {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getFloat(prefKey, defaultValue.toFloat())
    }

    /**
     * Clear all data in SharedPreference
     *
     * @param context
     */
    fun clearWholePreference(context: Context): Boolean {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit().clear()
            .commit()
    }

    /**
     * Clear single key value
     *
     * @param prefKey
     * @param context
     */
    fun remove(context: Context, prefKey: String) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .remove(prefKey).apply()
    }

   /* //getter method for company detail
        fun getUserDetail(context: Context, prefKey: String): UserModel? {
        val gson = Gson()
        val json =
            PreferenceManager.getDefaultSharedPreferences(context).getString(prefKey, null)
        val type = object : TypeToken<UserModel>() {
        }.type
        return gson.fromJson<UserModel>(json, type)
    }

    //setter method for company detail
    fun setUserDetail(context: Context, prefKey: String, model: UserModel?) {
        val gson = Gson()
        val json = gson.toJson(model)
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(prefKey, json)
            .apply()
    }*/



    fun setStringMusicPrefs(context: Context, prefKey: String, Value: String) {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE).edit()
            .putString(prefKey, Value).apply()
    }

    fun getStringMusicPrefs(context: Context, prefKey: String): String? {
        return context.getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE)
            .getString(prefKey, null)
    }


    fun getSoundState(context: Context): Boolean {
        return getBoolean(context,Key.SOUND_STATE, true)
    }

    fun saveSoundState(context: Context,soundState: Boolean) {
        setBooleanPrefs(context, Key.SOUND_STATE, soundState)
    }

}
