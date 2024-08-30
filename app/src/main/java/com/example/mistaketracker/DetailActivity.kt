package com.example.mistaketracker

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.mistaketracker.DataClass.Mistake
import com.example.mistaketracker.MVVM.MistakeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat
import com.example.mistaketracker.utility.Online
import dagger.hilt.android.AndroidEntryPoint
import org.junit.runner.manipulation.Ordering.Context
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var mistakeViewModel: MistakeViewModel

    private lateinit var dialogTitle: EditText
    private lateinit var spinner: Spinner
    private lateinit var category: TextView
    private lateinit var count: EditText
    private lateinit var detail: EditText
    private lateinit var lesson: EditText
    private lateinit var dialogButton: Button
    private lateinit var img: ImageView
    var img_path: String? = ""
    var mistakeuid: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        init()
        img.setOnClickListener()
        {
            getImgFromGallery()

        }
//        val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
//        val savedValue = sharedPreferences.getString("key", "default_value")
//        Log.d("s23",savedValue.toString())

        val mistake_id: Long = intent.getLongExtra("key", 0L)
        lifecycleScope.launch(Dispatchers.IO) {
            val mistake = mistakeViewModel.getMistakebyId(mistake_id)
            mistakeuid = mistake.uid
            UpdateCurrentMistakeData(mistake)
        }

        category.setOnClickListener() {
            Toast.makeText(this, "Click the arrow ▼", Toast.LENGTH_SHORT).show()
        }
        val items = listOf(
            "Category not Selected",
            "other",
            "School",
            "Studies",
            "Relationship",
            "Finance",
            "Health",
            "Time Management",
            "Organization",
            "Communication",
            "Personal Growth",
            "Habits",
            "Technology",
            "Travel",
            "Work/Career",
            "Education",
            "Home Management",
            "Self-Care",
            "Social Interactions",
            "A", "B", "C", "D", "E", "F", "G", "H", "J", "k"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.setSelection(0)
        var show = true
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                if (position != 0)
                    category.text = selectedItem
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
        dialogButton.setOnClickListener {
            if (!(dialogTitle.text.toString() == "" || category.text.toString() == "" || count.text.toString() == "" || detail.text.toString() == "" || lesson.text.toString() == "")) {
                show = false
            }

            lifecycleScope.launch(Dispatchers.IO) {
                val timestamp = System.currentTimeMillis();
//                val sharedPreferences = getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
//                val editor = sharedPreferences.edit()
//                editor.putLong("Time_Stamp", timestamp)
//                editor.apply()
                var mistakeObj =
                    Mistake(
                        mistake_id,
                        mistakeuid,
                        dialogTitle.text.toString(),
                        category.text.toString(),
                        count.text.toString(),
                        detail.text.toString(),
                        lesson.text.toString(), img_path.toString(), timestamp, false
                    )
                if (!(dialogTitle.text.toString() == "" || category.text.toString() == "" || count.text.toString() == "" || detail.text.toString() == "" || lesson.text.toString() == "")) {
                    if (Online.isOnline(this@DetailActivity)) {
                        val sharedPreferences = getSharedPreferences(
                            "myPreferences",
                            android.content.Context.MODE_PRIVATE
                        )
                        mistakeObj.timestamp = sharedPreferences.getLong("Time_Stamp", 0L)
                        mistakeViewModel.InsertMistake(mistakeObj) //basically update
                    }
                    mistakeViewModel.update(
                        mistakeObj
                    )
                }
            }
            if (show)
                Toast.makeText(
                    this@DetailActivity,
                    "Fill in the required details",
                    Toast.LENGTH_SHORT
                ).show()
            else
                Toast.makeText(
                    this@DetailActivity,
                    "Mistake successfully saved",
                    Toast.LENGTH_SHORT
                ).show()
        }


    }

    private fun getImgFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            val imageUri = data?.data
            img.setImageURI(imageUri)
            img_path = getRealPathFromURI(imageUri)

        }
    }

    private fun getRealPathFromURI(uri: Uri?): String? {
        var path: String? = null
        uri?.let {
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            val cursor = contentResolver.query(uri, projection, null, null, null)
            cursor?.use {
                if (cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                    path = cursor.getString(columnIndex)
                }
            }
        }
        return path
    }

    fun UpdateCurrentMistakeData(mistake: Mistake) {
        dialogTitle.setText(mistake.title.toString())
        category.setText(mistake.category.toString())
        count.setText(mistake.count.toString())
        detail.setText(mistake.detail.toString())
        lesson.setText(mistake.lesson.toString())
        img_path = mistake.img_path

        val bitmap = if (mistake.img_path.isNullOrEmpty()) {
            addBackgroundColorToBitmap(
                createBitmapWithText(mistake.category[0].toString()),
                Color.GREEN
            )
        } else {
            val loadedBitmap = loadImageFromPath(mistake.img_path)
            if (loadedBitmap != null) {
                loadedBitmap
            } else {
                addBackgroundColorToBitmap(
                    createBitmapWithText(mistake.category[0].toString()),
                    Color.GREEN
                )
            }
        }
        img.setImageBitmap(bitmap)
    }

    private fun loadImageFromPath(imagePath: String): Bitmap? {
        val bitmap = BitmapFactory.decodeFile(imagePath)
        return bitmap
    }


    private fun createBitmapWithText(text: String): Bitmap {
        Log.d("BitmapCreation", "Creating bitmap with text: $text")
        val bitmap = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.BLACK
        paint.textSize = 30f
        canvas.drawText(text, 17f, bitmap.height / 2f + 10f, paint)
        return bitmap
    }

    fun addBackgroundColorToBitmap(bitmap: Bitmap, backgroundColor: Int): Bitmap {
        val width = bitmap.width
        val height = bitmap.height

        val newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(newBitmap)


        val paint = Paint()
        paint.color = backgroundColor
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        canvas.drawBitmap(bitmap, 0f, 0f, null)

        return newBitmap
    }

    fun init() {
        dialogTitle = findViewById(R.id.mistake_title)
        spinner = findViewById(R.id.dialog_mistake_category)
        category = findViewById(R.id.x0) // Assuming x0 is a valid ID
        count = findViewById(R.id.dialog_mistake_count)
        detail = findViewById(R.id.mistake_detail)
        lesson = findViewById(R.id.mistake_lesson)
        dialogButton = findViewById(R.id.mistake_update)
        img = findViewById(R.id.category_icon)
    }
}