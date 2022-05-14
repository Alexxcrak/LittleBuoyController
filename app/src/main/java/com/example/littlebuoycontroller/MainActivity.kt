package com.example.littlebuoycontroller

import android.Manifest
import android.R.*
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity()
{
    private val permission: String = Manifest.permission.READ_SMS
    private val requestCode: Int = 1
    private var number = "             "
    private var text =  "             "
    private var type = " "


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)
        val  tv1 = findViewById<TextView>(R.id.textView1)
        val bt1 = findViewById(R.id.button) as Button

        Log.d("MY APP","Acaaaaa")
        if (ContextCompat.checkSelfPermission(this, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        } else {

        }


        bt1.setOnClickListener{
            Log.d("MY APP","leyendo Mensajes")
            readSms()
            Log.d("MY_APP", "$number $text $type")
            if (type == "1")
            tv1.text = text
        }
    }

    private fun readSms()
    {
        val numberCol = Telephony.TextBasedSmsColumns.ADDRESS
        val textCol = Telephony.TextBasedSmsColumns.BODY
        val typeCol = Telephony.TextBasedSmsColumns.TYPE // 1 - Inbox, 2 - Sent

        val projection = arrayOf(numberCol, textCol, typeCol)

        val cursor = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            projection, null, null, null
        )

        val numberColIdx = cursor!!.getColumnIndex(numberCol)
        val textColIdx = cursor.getColumnIndex(textCol)
        val typeColIdx = cursor.getColumnIndex(typeCol)

        //while (cursor.moveToNext()) {
        cursor.moveToNext()
             number = cursor.getString(numberColIdx)
             text = cursor.getString(textColIdx)
             type = cursor.getString(typeColIdx)

            //Log.d("MY_APP", "$number $text $type")
        //}
        cursor.close()
    }
}