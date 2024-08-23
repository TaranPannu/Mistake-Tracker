package com.example.mistaketracker.Adapters

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mistaketracker.DataClass.MistakeReport
import com.example.mistaketracker.R

class DetailAdapter(var list: List<MistakeReport>, var color_list:ArrayList<Int>):RecyclerView.Adapter<DetailAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val color_img = view.findViewById<ImageView>(R.id.color)
        val category_name =view.findViewById<TextView>(R.id.category)
        val mistake_count =view.findViewById<TextView>(R.id.count)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rcy_detail_list,parent,false)
        return DetailAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int {
      return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.category_name.text = item.category
        holder.color_img.setImageBitmap((addBackgroundColorToBitmap(color_list[position],createBitmapWithText(item.category[0].toString()))))
        holder.mistake_count.text = "Count: "+item.count.toString()
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
    fun addBackgroundColorToBitmap(backgroundColor: Int,bitmap: Bitmap): Bitmap {

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

}