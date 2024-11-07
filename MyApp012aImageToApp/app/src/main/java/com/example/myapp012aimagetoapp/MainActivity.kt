package com.example.myapp012aimagetoapp
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp012aimagetoapp.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inicializace pro viewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
                uri: Uri? -> binding.ivImage.setImageURI(uri)
        }
        binding.btnTakeImage.setOnClickListener {
            getContent.launch("image/*")
        }
        // Přidáme tlačítko pro uložení obrázku
        binding.btnSaveImage.setOnClickListener {
            val bitmap = (binding.ivImage.drawable as? BitmapDrawable)?.bitmap
            if (bitmap != null) {
                saveImageToGallery(bitmap)
            } else {
                Toast.makeText(this, "No image to save", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap) {
        val filename = "${System.currentTimeMillis()}.jpg"
        val fos: OutputStream? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            // Pro Android Q a vyšší
            contentResolver?.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyAppImages") // Zvolte složku
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
            )?.let { uri -> contentResolver.openOutputStream(uri) }
        } else {
            // Pro nižší verze Androidu
            val imagesDir = File(getExternalFilesDir(null), "MyAppImages")
            imagesDir.mkdirs() // Vytvoří složku, pokud neexistuje
            val imageFile = File(imagesDir, filename)
            FileOutputStream(imageFile)
        }

        fos?.use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
            Toast.makeText(this, "Image saved to gallery", Toast.LENGTH_SHORT).show()
        }
    }
}

