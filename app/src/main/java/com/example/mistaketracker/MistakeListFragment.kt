package com.example.mistaketracker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mistaketracker.Adapters.RecyclerViewAdapter
import com.example.mistaketracker.DataClass.Mistake
import com.example.mistaketracker.MVVM.MistakeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MistakeListFragment : Fragment(), ClickToOpenDetailActivity {
    lateinit var recyclerView: RecyclerView

    @Inject
    lateinit var mistakeViewModel: MistakeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val context = requireContext()
        val view = inflater.inflate(R.layout.fragment_mistake_list, container, false)
        init(view)
        mistakeViewModel.getAllMistakes().observe(viewLifecycleOwner) {


            val adapter = RecyclerViewAdapter(it, this)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }
        return view
    }


    fun init(view: View) {
        recyclerView = view.findViewById(R.id.rcy)
    }

    override fun onClickDetailOpen(mistake: Mistake) {
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("key", mistake.id)
        startActivity(intent)
    }

    override fun onClickIncreaseCount(mistake: Mistake) { //count won't be shared
        lifecycleScope.launch(Dispatchers.IO) {
            mistake.timestamp = System.currentTimeMillis()
            mistake.count = ((mistake.count).toInt() + 1).toString()
            mistakeViewModel.update(mistake)
        }
        Toast.makeText(context, "Mistake Count Increased", Toast.LENGTH_SHORT).show()
    }

    override fun onClickDecreaseCount(mistake: Mistake) {
        lifecycleScope.launch(Dispatchers.IO) {
            mistake.timestamp = System.currentTimeMillis()
            mistake.count = ((mistake.count).toInt() - 1).toString()
            mistakeViewModel.update(mistake)
        }
        Toast.makeText(context, "Mistake Count Decreased", Toast.LENGTH_SHORT).show()
    }

    override fun onClickDeleteMistake(mistake: Mistake, x: Int) { // Delete From Server
        if (x == 0) {
            Toast.makeText(context, "Hold the Delete Button", Toast.LENGTH_SHORT).show()
        } else {
            lifecycleScope.launch(Dispatchers.IO)
            {
                mistakeViewModel.delete(mistake)
            }
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onClickUpdateMistake(mistake: Mistake) // delete update option from here
    {

    }

    override fun onImgClicked() {
        TODO("Not yet implemented")
    }

}