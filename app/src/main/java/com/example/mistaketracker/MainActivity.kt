package com.example.mistaketracker

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.mistaketracker.Adapters.VPAdapter
import com.example.mistaketracker.Data.Mistake
import com.example.mistaketracker.Room.MistakeDatabase
import com.example.mistaketracker.MVVM.MistakeViewModel
import com.example.mistaketracker.MVVM.MistakeViewModelFactory
import com.example.mistaketracker.MVVM.Repo
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject lateinit var mistakeViewModel: MistakeViewModel

    lateinit var tabLayout: TabLayout
    lateinit var viewPager: ViewPager2
    lateinit var VP_Adapter: VPAdapter
    lateinit var floatingActionButton: FloatingActionButton

//    lateinit var mistakeViewModel: MistakeViewModel
//    lateinit var repo: Repo
//    lateinit var mistakeDatabase: MistakeDatabase
//    lateinit var mistakeViewModelFactory: MistakeViewModelFactory
    var ImagePath:String =""
    lateinit var img: ImageView
    lateinit var  floatLayout: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(!checkPermission())
            requestPermission()

        init()
        TabLayoutMediator(tabLayout,viewPager)
        {
                tab: TabLayout.Tab, position: Int ->
            when(position)
            {
                0 -> tab.text = "Mistakes"
                else -> tab.text = "Report"
            }
        }.attach()
floatingActionButton.setOnClickListener()
{
    showCustomDialog()
}
floatLayout.setOnClickListener()
{
            showCustomDialog()
}
    }
    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            111
        )
    }
    private fun showCustomDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.alert_dialog, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        val dialogTitle = dialogView.findViewById<EditText>(R.id.dialog_mistake_title)
        val spinner = dialogView.findViewById<Spinner>(R.id.dialog_mistake_category)
        val category = dialogView.findViewById<TextView>(R.id.x0)

        val count = dialogView.findViewById<EditText>(R.id.dialog_mistake_count)
        val detail = dialogView.findViewById<EditText>(R.id.dialog_mistake_detail)
        val lesson = dialogView.findViewById<EditText>(R.id.dialog_mistake_lesson)
        val dialogButton = dialogView.findViewById<Button>(R.id.dialog_button)
        img= dialogView.findViewById(R.id.category_icon)
        img.setOnClickListener()
        {
            getImgFromGallery()

        }
        category.setOnClickListener(){
            Toast.makeText(this,"Click the arrow ▼",Toast.LENGTH_SHORT).show()
        }
        val items = listOf(
            "Category not Selected",
            "other",
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
            "A","B","C","D","E","F","G","H","J","k"
        )
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter= adapter
        spinner.setSelection(-1)
        var show = true
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedItem = parent.getItemAtPosition(position).toString()
                category.text = selectedItem
            }
            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }
          dialogButton.setOnClickListener {
              if(!(dialogTitle.text.toString() == "" || category.text.toString() =="" || count.text.toString() ==""||detail.text.toString()==""||lesson.text.toString()=="" ))
              {
show = false
              }
            lifecycleScope.launch(Dispatchers.IO){
                val uniqueId = System.currentTimeMillis()
                if(!(dialogTitle.text.toString() == "" || category.text.toString() =="" || count.text.toString() ==""||detail.text.toString()==""||lesson.text.toString()==""))
                {
                    mistakeViewModel.insert(
                        Mistake(
                            0,
                            dialogTitle.text.toString(),
                            category.text.toString(),
                            count.text.toString(),
                            detail.text.toString(),
                            lesson.text.toString(),ImagePath
                        )
                    )
                    dialog.dismiss()
                }
            }
              if(show)
                  Toast.makeText(this@MainActivity,"Fill in the required details",Toast.LENGTH_SHORT).show()
              else
                  Toast.makeText(this@MainActivity,"Mistake successfully saved",Toast.LENGTH_SHORT).show()
        }
        dialog.show()
    }

    fun init()
    {
        VP_Adapter = VPAdapter(this)
        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.View_pager2)
        floatingActionButton = findViewById(R.id.float_btn)
        viewPager.adapter = VP_Adapter
        floatLayout=findViewById(R.id.float_text)

//        mistakeDatabase = MistakeDatabase(this)
//        repo = Repo(mistakeDatabase.getMistakeDao())
//        mistakeViewModelFactory = MistakeViewModelFactory(repo)
        //mistakeViewModel = ViewModelProvider(this, mistakeViewModelFactory).get(MistakeViewModel::class.java)
    }
    private fun getImgFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, 101)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK) {
            val imageUri = data?.data
            img.setImageURI(data?.data)

            // Get the image path from the URI
            val imagePath = getRealPathFromURI(imageUri)
            if (imagePath != null) {
                ImagePath= imagePath
            } else {
                Log.e("Image Path", "Could not get the image path")
            }
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

    private fun loadImageFromPath(imagePath: String) {
        val bitmap = BitmapFactory.decodeFile(imagePath)
//        img.setImageBitmap(bitmap)
    }

}


interface ClickToOpenDetailActivity
{
    fun onClickDetailOpen(mistake: Mistake)
    fun onClickIncreaseCount(mistake: Mistake)
    fun onClickDecreaseCount(mistake: Mistake)
    fun onClickDeleteMistake(mistake: Mistake, x: Int )
    fun onClickUpdateMistake(mistake: Mistake)

    fun onImgClicked()
}

