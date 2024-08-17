package com.example.mistaketracker

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mistaketracker.Adapters.DetailAdapter
import com.example.mistaketracker.Data.Mistake
import com.example.mistaketracker.Room.MistakeDatabase
import com.example.mistaketracker.Data.MistakeReport
import com.example.mistaketracker.MVVM.MistakeViewModel
import com.example.mistaketracker.MVVM.MistakeViewModelFactory
import com.example.mistaketracker.MVVM.Repo
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.MPPointF
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.random.Random

@AndroidEntryPoint
class DetailFragment : Fragment() {
    @Inject
    lateinit var mistakeViewModel: MistakeViewModel
    lateinit var pieChart: PieChart
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_detail, container, false)
        init(view)
        setPieChart1()

       mistakeViewModel.getAllMistakes().observe(viewLifecycleOwner){
           val entries: ArrayList<PieEntry> = ArrayList()
           val colors: ArrayList<Int> = ArrayList()
           val color_list: ArrayList<Int> = ArrayList()
           var list = it.toMutableList()
           var total = list.size

           var report_list = mutableListOf<MistakeReport>()
           for( i in GetReportList(list))
           {
               val floatValue: Float = i.value.toFloat() ?: 0f
               entries.add(PieEntry((floatValue *1.0f / total) * 100f))
               val color = generateRandomColor()
               colors.add(color)
               color_list.add(color)
               report_list.add(MistakeReport(i.key,i.value))
           }
           setPieChart(entries,colors)
           recyclerView.layoutManager = GridLayoutManager(context, 3, GridLayoutManager.HORIZONTAL, false)
           recyclerView.adapter=DetailAdapter(report_list,color_list)
       }
        return view
    }

    fun init(view: View)
    {
        pieChart = view.findViewById(R.id.pieChart)
        recyclerView = view.findViewById(R.id.rcy_detail)
    }

    fun setPieChart(entries:ArrayList<PieEntry>,colors:ArrayList<Int>)
    {

        // on below line we are setting pie data set
        val dataSet = PieDataSet(entries, "Mobile OS")

        // on below line we are setting icons.
        dataSet.setDrawIcons(false)

        // on below line we are setting slice for pie
        dataSet.sliceSpace = 3f
        dataSet.iconsOffset = MPPointF(0f, 40f)
        dataSet.selectionShift = 5f

        // on below line we are setting colors.
        dataSet.colors = colors

        // on below line we are setting pie data set
        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter())
        data.setValueTextSize(15f)
        data.setValueTypeface(Typeface.DEFAULT_BOLD)
        data.setValueTextColor(Color.WHITE)
        pieChart.setData(data)

        // undo all highlights
        pieChart.highlightValues(null)

        // loading chart
        pieChart.invalidate()
    }

    fun setPieChart1()
    {
        pieChart.setUsePercentValues(true)
        pieChart.getDescription().setEnabled(false)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.setDragDecelerationFrictionCoef(0.95f)

        // on below line we are setting hole
        // and hole color for pie chart
        pieChart.setDrawHoleEnabled(true)
        pieChart.setHoleColor(Color.WHITE)

        // on below line we are setting circle color and alpha
        pieChart.setTransparentCircleColor(Color.WHITE)
        pieChart.setTransparentCircleAlpha(110)

        // on  below line we are setting hole radius
        pieChart.setHoleRadius(58f)
        pieChart.setTransparentCircleRadius(61f)

        // on below line we are setting center text
        pieChart.setDrawCenterText(true)

        // on below line we are setting
        // rotation for our pie chart
        pieChart.setRotationAngle(0f)

        // enable rotation of the pieChart by touch
        pieChart.setRotationEnabled(true)
        pieChart.setHighlightPerTapEnabled(true)

        // on below line we are setting animation for our pie chart
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        // on below line we are disabling our legend for pie chart
        pieChart.legend.isEnabled = false
        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(12f)

    }
   private fun generateRandomColor(): Int {
      val rnd = Random
      return Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
   }

    private fun GetReportList(list: List<Mistake>):Map<String,Int>
    {
        val mutableMap = mutableMapOf<String, Int>()
        for(i in list)
        {
if(mutableMap.containsKey(i.category))
{
    mutableMap[i.category]=i.count.toInt()+ mutableMap[i.category]!!

}else
{
    mutableMap[i.category]=i.count.toInt()
}
        }
        return mutableMap
    }
}