package com.kvsoftware.receivefile

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.FileNotFoundException

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE = 1000
        const val MIME_TYPE = "image/jpeg"
    }

    private lateinit var imageViewPick: ImageView
    private lateinit var buttonPickPicture: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initializeView()
        buttonPickPicture.setOnClickListener {
            pickPicture()
        }
    }

    private fun initializeView() {
        imageViewPick = findViewById(R.id.imageview_pick)
        buttonPickPicture = findViewById(R.id.button_pick_picture)
    }

    /**
     * Select a JPEG file from other application.
     */
    private fun pickPicture() {
        val requestFileIntent = Intent(Intent.ACTION_PICK).apply { type = MIME_TYPE }
        startActivityForResult(requestFileIntent, REQUEST_CODE)
    }

    /**
     * Access the requested file from other application.
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == REQUEST_CODE
            && resultCode == Activity.RESULT_OK
            && intent?.data != null
        ) {
            /**
             * Open the file for "read" access using the returned URI.
             * If the file isn't found, it will return exception.
             */
            try {
                val file = FileHelper.createFile(this, intent.data!!)
                Glide.with(this).load(file).into(imageViewPick)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        }
    }
}