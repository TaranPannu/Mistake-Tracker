package com.example.mistaketracker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mistaketracker.Adapters.RecyclerViewAdapter
import com.example.mistaketracker.Room.Dao
import com.example.mistaketracker.Data.Mistake
import com.example.mistaketracker.Room.MistakeDatabase
import com.example.mistaketracker.MVVM.MistakeViewModel
import com.example.mistaketracker.MVVM.MistakeViewModelFactory
import com.example.mistaketracker.MVVM.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MistakeListFragment : Fragment(), ClickToOpenDetailActivity {
lateinit var recyclerView: RecyclerView
lateinit var mistakeDatabase: MistakeDatabase
lateinit var mistake_dao: Dao

    lateinit var mistakeViewModel: MistakeViewModel
    lateinit var repo: Repo
    lateinit var mistakeViewModelFactory: MistakeViewModelFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = requireContext()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mistake_list, container, false)
        init(view)

//        mistake_dao = mistakeDatabase.getMistakeDao()

       mistakeViewModel.getAllMistakes().observe(viewLifecycleOwner){


           val adapter = RecyclerViewAdapter(it,this)
           recyclerView.layoutManager = LinearLayoutManager(context)
           recyclerView.adapter =adapter
       }

//
//        val adapter = RecyclerViewAdapter(list,this)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter =adapter
        return view
    }


    fun init(view: View)
    {
        recyclerView = view.findViewById(R.id.rcy)
        mistakeDatabase = MistakeDatabase(view.context)

        repo = Repo(mistakeDatabase.getMistakeDao())
        mistakeViewModelFactory = MistakeViewModelFactory(repo)
        mistakeViewModel = ViewModelProvider(this, mistakeViewModelFactory).get(MistakeViewModel::class.java)
    }

    override fun onClickDetailOpen(mistake: Mistake) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("key",mistake.id)
        startActivity(intent)
    }

    override fun onClickIncreaseCount(mistake: Mistake) {
        lifecycleScope.launch(Dispatchers.IO) {
            mistake.count =((mistake.count).toInt()+1).toString()
            mistakeViewModel.update(mistake)
        }
        Toast.makeText(context,"Mistake Count Increased",Toast.LENGTH_SHORT).show()
    }

    override fun onClickDecreaseCount(mistake: Mistake) {
        lifecycleScope.launch(Dispatchers.IO) {
            mistake.count =((mistake.count).toInt()-1).toString()


            mistakeViewModel.update(mistake)
        }
        Toast.makeText(context,"Mistake Count Decreased",Toast.LENGTH_SHORT).show()
    }

    override fun onClickDeleteMistake(mistake:Mistake, x:Int)
    {
        if(x==0)
        {
            Toast.makeText(context,"Hold the Delete Button",Toast.LENGTH_SHORT).show()
        }
        else
        {
            lifecycleScope.launch(Dispatchers.IO)
        {
            mistakeViewModel.delete(mistake)
        }
            Toast.makeText(context,"Successfully Deleted",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onClickUpdateMistake(mistake: Mistake) // delete update option from here
    {

    }

    override fun onImgClicked() {
        TODO("Not yet implemented")
    }

    /*  fun showCustomDialog(mistake: Mistake) {
          // Inflate the custom layout
          val dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog, null)

          // Create the AlertDialog
          val dialog = context?.let {
              AlertDialog.Builder(it)
                  .setView(dialogView)
                  .setCancelable(true)
                  .create()
          }

          // Get references to the views in the custom layout
          val dialogTitle:EditText = dialogView.findViewById<EditText>(R.id.dialog_mistake_title)
          val category = dialogView.findViewById<EditText>(R.id.dialog_mistake_category)
          val count = dialogView.findViewById<EditText>(R.id.dialog_mistake_count)
          val detail = dialogView.findViewById<EditText>(R.id.dialog_mistake_detail)
          val lesson = dialogView.findViewById<EditText>(R.id.dialog_mistake_lesson)
          val dialogButton = dialogView.findViewById<Button>(R.id.dialog_button)


          dialogTitle.setText(mistake.title)
          category.setText(mistake.category)
          count.setText(mistake.count)
          detail.setText(mistake.detail)
          lesson.setText(mistake.lesson)

          // Set up the button click listener
          dialogButton.setOnClickListener {
              // Do something with the input, if needed
              lifecycleScope.launch(Dispatchers.IO){
                  mistakeViewModel.update(Mistake(mistake.id, dialogTitle.text.toString(),category.text.toString(),count.text.toString(),detail.text.toString(),lesson.text.toString()))

              }
              if (dialog != null) {
                  dialog.dismiss()
              }
          }

          // Show the dialog
          if (dialog != null) {
              dialog.show()
          }
      }
      */
}