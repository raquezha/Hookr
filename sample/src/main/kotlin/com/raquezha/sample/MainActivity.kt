package com.raquezha.sample

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_help.*
import net.raquezha.hookr.Hook

import net.raquezha.playground.extensions.toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        val oldFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
        val newFormat = SimpleDateFormat("MM/dd/yyyy 'at' hh:mm a", Locale.getDefault())
        val date = oldFormat.parse(BuildConfig.BUILD_TIME.toString())
        val buildDate = newFormat.format(date).replace("pm", "PM").replace("am", "AM")

        tvAppVersion.text = "App Version: ${BuildConfig.VERSION_NAME}"
        tvBuildDate.text = "Build Date: $buildDate"

        val sample_number = "+639123456780"
        btnSMS.setOnClickListener {
            Hook.up(this@MainActivity).withSMS(sample_number)
        }

        btnCall.setOnClickListener {
            Hook.up(this@MainActivity).withPhoneCall(sample_number)
        }

        btnMessenger.setOnClickListener {
            Hook.up(this@MainActivity).withMessenger("1232131232")
        }

        btnViber.setOnClickListener {
            Hook.up(this@MainActivity).withViber(sample_number)
        }

        btnWhatsapp.setOnClickListener {
            Hook.up(this@MainActivity).withWhatsApp(sample_number)
        }
    }
}
