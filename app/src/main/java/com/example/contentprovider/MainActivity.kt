package com.example.contentprovider

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import java.io.File
import java.net.URL
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private  lateinit var captura : ImageView
    private lateinit var urlImagen : Uri
    private lateinit var tiempo : String
    private lateinit var nombreImagen : String
    private var contract = registerForActivityResult(ActivityResultContracts.TakePicture()){
        captura.setImageURI(null)
        captura.setImageURI(urlImagen)
    }


    private  fun createImagenUri():Uri{
       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            tiempo = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss").withZone(ZoneOffset.UTC).format(Instant.now())
        }
        nombreImagen = "$tiempo.png"
        val imagen = File(filesDir,nombreImagen)

        return FileProvider.getUriForFile(this,"com.example.contentprovider.FileProvider", imagen)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        captura = findViewById(R.id.captureImageView)

        val captureImeBtn = findViewById<Button>(R.id.captureImageBtn)

        captureImeBtn.setOnClickListener {
            urlImagen = createImagenUri()
            contract.launch(urlImagen)
        }
    }
}