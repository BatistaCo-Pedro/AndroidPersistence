package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    var isRunning = false
    var isCoroutineRunning = false
    val executor = Executors.newSingleThreadExecutor()
    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        anzahlAufrufe()
    }

    fun executing(v:View) {
        if(isRunning) {
            Snackbar.make(v, "Is currently running", Snackbar.LENGTH_SHORT).show()
        }
        else {
            isRunning = true
            val btn = findViewById<Button>(R.id.execute)
            val intArr = "5, 7, 90, 23, 53"
            btn.text = "execution in progress"
            executor.execute {
                Snackbar.make(v, intArr, Snackbar.LENGTH_SHORT).show()
                Thread.sleep(7000)
                handler.post {
                    btn.text = "EXECUTOR STARTEN"
                    isRunning = false
                }
            }
        }
    }

    fun coroutineEx(v:View) {
        val btn = findViewById<Button>(R.id.coroutine)
        if(isCoroutineRunning) {
            Snackbar.make(v, "Läuft bereits", Snackbar.LENGTH_SHORT).show()
            return
        }
        CoroutineScope(IO).launch {
            isCoroutineRunning = true
            btn.text = "Läuft"
            Thread.sleep(4000)
            btn.text = "Coroutine Starten"
            isCoroutineRunning = false
        }
    }

    fun anzahlAufrufe() {
        val textAnzahlAufrufe = findViewById<TextView>(R.id.ShowCount)
        val preferences: SharedPreferences = getSharedPreferences("ch.bfo.emvs", MODE_PRIVATE)
        val newResumeCount: Int = preferences.getInt("resumeCount", 0) + 1
        val editor = preferences.edit()
        editor.putInt("resumeCount", newResumeCount)
        editor.apply()
        textAnzahlAufrufe.text = "MainActivity.onResume() wurde seit der Installation dieser App ${newResumeCount} mal aufgerufen."
    }

    fun openPreferences(view: View){
        val myIntent = Intent(this, TeePreferences::class.java)
        startActivity(myIntent)
    }

    override fun onResume() {
        super.onResume()
        val preferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = preferences.edit()
        val textTee = findViewById<TextView>(R.id.textView4)
        var value : String? = ""
        if(preferences.getBoolean("teaWithSugar", false))
        {
            value = preferences.getString("teaSweetener", "")
            editor.putBoolean("gesüsst", true)
            editor.commit()
            textTee.text = "Ich drinke am liebsten - gesüsst"
            Log.i("sweetenedPreference", "its sweetened")
            if(!value?.isEmpty()!!)
            {
                editor.putString("teaSweetener", value)
                editor.commit()
                textTee.text = "Ich drinke am liebsten - ${value} gesüsst"
                Log.i("sweetenedPreference", "${value}")
            }
        }
        else textTee.text = "Ich drinke am liebsten - ungesüsst"

        val textValue = preferences.getString("marke", "")
        if (!textValue?.isEmpty()!!) {
            editor.putString("teaMarke", textValue)
            textTee.text = "Ich drinke am liebsten ${textValue} ${value} gesüsst "
            Log.i("sweetenedPreference", "${textValue}")
        }
    }
}

