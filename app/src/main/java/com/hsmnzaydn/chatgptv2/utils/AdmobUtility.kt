package com.hsmnzaydn.chatgptv2.utils

import android.app.Activity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class AdmobUtility constructor(private val activity: Activity) {
    private var mInterstitialAd: InterstitialAd? = null

     fun init(){
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(activity,"ca-app-pub-7491116475843767/3653946610", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })
    }

    fun show(){
        mInterstitialAd?.show(activity)
    }
}