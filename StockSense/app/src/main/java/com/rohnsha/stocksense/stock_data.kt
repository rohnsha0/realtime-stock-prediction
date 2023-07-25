package com.rohnsha.stocksense

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.rohnsha.stocksense.databinding.ActivityStockDataBinding
import com.rohnsha.stocksense.technical_api.object_technical.technicalAPIservice
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class stock_data : AppCompatActivity() {

    private lateinit var bindingData: ActivityStockDataBinding
    lateinit var mBannerAdView: AdView
    lateinit var mBannerAdView2: AdView
    lateinit var mBannerAdView3: AdView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingData= ActivityStockDataBinding.inflate(layoutInflater)
        setContentView(bindingData.root)

        val symbol= intent.getStringExtra("symbolStock").toString().uppercase()
        val name=intent.getStringExtra("nameStock").toString()

        mBannerAdView= bindingData.bannerAdData
        mBannerAdView2= bindingData.bannerAdData2
        mBannerAdView3= bindingData.bannerAdData3

        var adClickCount = 0

        val adRequest= AdRequest.Builder().build()
        mBannerAdView.loadAd(adRequest)
        mBannerAdView2.loadAd(adRequest)
        mBannerAdView3.loadAd(adRequest)
        mBannerAdView.adListener = object: AdListener() {
            override fun onAdClicked() {
                super.onAdClicked()
                adClickCount= clickProcess(adClickCount)
            }
            override fun onAdFailedToLoad(adError : LoadAdError) {
                super.onAdFailedToLoad(adError)
                mBannerAdView.loadAd(adRequest)
            }
            override fun onAdLoaded() {
                if (checkAdStatus()){
                    mBannerAdView.visibility= View.VISIBLE
                }else{
                    mBannerAdView.visibility= View.GONE
                }
            }
        }

        mBannerAdView2.adListener = object: AdListener() {
            override fun onAdClicked() {
                super.onAdClicked()
                adClickCount= clickProcess(adClickCount)
            }
            override fun onAdFailedToLoad(adError : LoadAdError) {
                super.onAdFailedToLoad(adError)
                mBannerAdView2.loadAd(adRequest)
            }
            override fun onAdLoaded() {
                if (checkAdStatus()){
                    mBannerAdView2.visibility= View.VISIBLE
                } else{
                    mBannerAdView2.visibility= View.GONE
                }
            }
        }

        mBannerAdView3.adListener = object: AdListener() {
            override fun onAdClicked() {
                super.onAdClicked()
                adClickCount= clickProcess(adClickCount)
            }
            override fun onAdFailedToLoad(adError : LoadAdError) {
                super.onAdFailedToLoad(adError)
                mBannerAdView3.loadAd(adRequest)
            }
            override fun onAdLoaded() {
                if (checkAdStatus()){
                    mBannerAdView3.visibility= View.VISIBLE
                } else{
                    mBannerAdView3.visibility= View.GONE
                }
            }
        }

        lifecycleScope.launch(Dispatchers.IO){
            Log.e("stockData", "sending requests....")
            val dynamicURL= "https://quuicqg435fkhjzpkawkhg4exi0vjslb.lambda-url.ap-south-1.on.aws/technical/$symbol"
            Log.e("stockData", "sent requests....")
            try {
                val response= technicalAPIservice.getTehnicalData(dynamicURL)
                withContext(Dispatchers.Main){
                    bindingData.appbarData.visibility= View.VISIBLE
                    bindingData.blueCircle.visibility= View.VISIBLE
                    bindingData.iconInfoSVG.visibility= View.VISIBLE
                    bindingData.mainContent.visibility= View.VISIBLE
                    bindingData.predTitle.visibility= View.VISIBLE
                    bindingData.loadingContainerData.visibility= View.GONE

                    bindingData.dateVal.text= systemDate()
                    bindingData.symbolVal.text= symbol.substringBefore('.')
                    bindingData.ISINVal.text= response.ISIN
                    bindingData.currentPriceValue.text= response.faceValue.toString()
                    bindingData.indstryVal.text= response.industry
                    bindingData.stockNameVal.text= name
                    bindingData.SMA50Val.text= String.format("%.2f", response.sma50)
                    bindingData.SMA100Val.text= String.format("%.2f", response.sma100)
                    bindingData.SMA200Val.text= String.format("%.2f", response.sma200)
                    bindingData.EMA50Val.text= String.format("%.2f", response.ema50)
                    bindingData.EMA100Val.text= String.format("%.2f", response.ema100)
                    bindingData.EMA200Val.text= String.format("%.2f", response.ema200)
                    bindingData.RSIVal.text= String.format("%.2f", response.rsi)
                    bindingData.MACDVal.text= String.format("%.2f", response.macd)
                    bindingData.ATRval.text= String.format("%.2f", response.atr)
                    bindingData.BBUpperVal.text= String.format("%.2f", response.bollingerBandUpper)
                    bindingData.BBLowerVal.text= String.format("%.2f", response.bollingerBankLoweer)

                    bindingData.backData.setOnClickListener {
                        onBackPressed()
                    }

                    bindingData.dataIntentBTN.setOnClickListener {
                        val intent= Intent(this@stock_data, homepage::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
            } catch (e:Exception){
                bindingData.errorViewData.visibility= View.VISIBLE
                bindingData.loadingContainerData.visibility= View.GONE
                Log.e("error", e.toString())
            }
        }

    }

    private fun systemDate(): String {
        val dateFormat= SimpleDateFormat("MMMM d, yyyy", Locale.US)
        val currentDate= Date(System.currentTimeMillis())
        return dateFormat.format(currentDate)
    }

    private fun updateLastAdClickTimeMillis(currentTimeMillis: Long) {
        val currentTimeMillis = System.currentTimeMillis()
        val adReEnableTimeMillis = 3 * 60 * 60 * 1000
        val sharedPreferences = this.getSharedPreferences("AdClickDataBanner", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putLong("lastAdClickTimeMillisData", currentTimeMillis)
        editor.apply()

        val lastAdClickTimeMillis = sharedPreferences.getLong("lastAdClickTimeMillis", 0)
        if (currentTimeMillis - lastAdClickTimeMillis > adReEnableTimeMillis) {
            mBannerAdView.visibility = View.VISIBLE
            mBannerAdView2.visibility= View.VISIBLE
            mBannerAdView3.visibility= View.VISIBLE
        }
    }

    private fun getLastAdClickTimeMillis(): Long {
        val sharedPreferences = this.getSharedPreferences("AdClickDataBanner", Context.MODE_PRIVATE)
        return sharedPreferences.getLong("lastAdClickTimeMillisData", 0)
    }

    private fun clickProcess(clickCount: Int): Int{
        var adClickCount= clickCount
        val adClickTimeLimitMillis = 2 * 60 * 60 * 1000
        adClickCount++
        if (adClickCount >= 5) {
            val currentTimeMillis = System.currentTimeMillis()
            val lastAdClickTimeMillis = getLastAdClickTimeMillis()
            if (currentTimeMillis - lastAdClickTimeMillis <= adClickTimeLimitMillis) {
                mBannerAdView.visibility = View.GONE
                mBannerAdView2.visibility= View.GONE
                mBannerAdView3.visibility= View.GONE
                customToast.makeText(this@stock_data, "You're abusing app usage policy. It might lead to account suspension!", 2).show()
                adClickCount= 0
            }
        }
        updateLastAdClickTimeMillis(System.currentTimeMillis())
        return adClickCount
    }

    private fun checkAdStatus(): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        val adReEnableTimeMillis = 3 * 60 * 60 * 1000
        val sharedPreferences = this.getSharedPreferences("AdClickDataBanner", Context.MODE_PRIVATE)

        val lastAdClickTimeMillis = sharedPreferences.getLong("lastAdClickTimeMillisData", 0)
        if (currentTimeMillis - lastAdClickTimeMillis < adReEnableTimeMillis) {
            return false
        }
        return true
    }
}