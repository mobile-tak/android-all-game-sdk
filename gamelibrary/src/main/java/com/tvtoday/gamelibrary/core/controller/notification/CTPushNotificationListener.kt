package com.tvtoday.gamelibrary.core.controller.notification

interface CTPushNotificationListener {

    fun onNotificationClickedPayloadReceived(payload: HashMap<String?, Any?>?)

}