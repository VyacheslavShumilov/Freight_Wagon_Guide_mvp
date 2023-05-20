package com.hfad.smgrapp.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.textview.MaterialTextView
import com.hfad.smgrapp.R

class HelpActivity : AppCompatActivity() {

    private lateinit var txtMail: MaterialTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)

        setContentView(R.layout.activity_help)

        txtMail = findViewById(R.id.txt_mail)
        txtMail.setOnClickListener {
            sendEmail()
        }
    }

    private fun sendEmail() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("vvshumilov@mail.ru"))
        intent.putExtra(Intent.EXTRA_SUBJECT, "О приложении Справочник грузовых вагонов")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "...введите ваше сообщение..."
        )
        startActivity(Intent.createChooser(intent, "Отправить сообщение"))
    }

}