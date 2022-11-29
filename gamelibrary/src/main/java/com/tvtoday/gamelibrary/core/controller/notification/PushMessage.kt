package com.tvtoday.gamelibrary.core.controller.notification

import android.os.Bundle

class PushMessage {

    private var customMessage: String? = null
    private var header: String? = null
    private var message: String? = null
    private var deepLink: String? = null
    private var bigPictureUrl: String? = null
    private var largeIconUrl: String? = null
    private var streamAlertId: String? = null

    fun getCustomMessage(): String? {
        return customMessage
    }

    fun getStreamAlertId(): String? {
        return streamAlertId
    }

    fun setStreamAlertId(streamAlertId: String?) {
        this.streamAlertId = streamAlertId
    }

    fun getVideoId(): String? {
        return videoId
    }

    fun setVideoId(videoId: String?) {
        this.videoId = videoId
    }

    private var videoId: String? = null

    fun getBundle(): Bundle? {
        return bundle
    }

    fun setBundle(bundle: Bundle?) {
        this.bundle = bundle
    }

    private var bundle: Bundle? = null


    fun setHeader(header: String?) {
        this.header = header
    }

    fun setLargeIconUrl(largeIconUrl: String?) {
        this.largeIconUrl = largeIconUrl
    }


    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String?) {
        this.message = message
    }


    fun getDeepLink(): String? {
        return deepLink
    }

    fun setDeepLink(deepLink: String?) {
        this.deepLink = deepLink
    }


    fun setCustomMessage(customMessage: String?) {
        this.customMessage = customMessage
    }

    fun getCustomData(): String? {
        return customMessage
    }

    fun getHeader(): String? {
        return header
    }

    fun setBigPictureUrl(bigPictureUrl: String?) {
        this.bigPictureUrl = bigPictureUrl
    }

    fun getBigPictureUrl(): String? {
        return bigPictureUrl
    }

    fun getLargeIconUrl(): String? {
        return largeIconUrl
    }
}