package com.example.mistaketracker.Adapters

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mistaketracker.ClickToOpenDetailActivity
import com.example.mistaketracker.Data.Mistake
import com.example.mistaketracker.R
import java.io.File
import kotlin.random.Random

class RecyclerViewAdapter(var list: List<Mistake>,var listener: ClickToOpenDetailActivity):RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
val img_category:ImageView = view.findViewById(R.id.category_icon)
val delete:ImageView = view.findViewById(R.id.delete_icon)
val mistake_title:TextView = view.findViewById(R.id.mistake_title)
val mistake_count:TextView = view.findViewById(R.id.mistake_count)
val increase_icon:ImageView = view.findViewById(R.id.increase_icon)
val decrease_icon:ImageView = view.findViewById(R.id.decrease_icon)
val update_mistake:TextView = view.findViewById(R.id.mistake_update)
        val mistake_category:TextView = view.findViewById(R.id.mistake_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
val view = LayoutInflater.from(parent.context).inflate(R.layout.rcy_item,parent,false)
return ViewHolder(view)
    }

    override fun getItemCount(): Int
    {
return list.size
    }
    private fun loadImageFromPath(imagePath: String): Bitmap? {
        val file = File(imagePath)
        val bitmap= BitmapFactory.decodeFile(imagePath)
      return  bitmap
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val x = list[position]
        holder.mistake_title.text = x.title
        holder.mistake_count.text = "Count: "+x.count.toString()
        holder.mistake_category.text = "Category: "+x.category.toString()

        var bitmap = if (x.img_path.isNullOrEmpty())
        {
            addBackgroundColorToBitmap( createBitmapWithText((x.category)[0].toString()),generateRandomColor())
        }
        else
        {
            loadImageFromPath(x.img_path) ?: addBackgroundColorToBitmap( createBitmapWithText((x.category)[0].toString()),generateRandomColor())
        }

        holder.img_category.setImageBitmap(bitmap)

        holder.itemView.setOnClickListener()
        {
         listener.onClickDetailOpen(x)
        }
        holder.increase_icon.setOnClickListener()
        {
            listener.onClickIncreaseCount(x)
        }

        holder.decrease_icon.setOnClickListener()
        {
            listener.onClickDecreaseCount(x)
        }

        holder.delete.setOnClickListener()
        {
            listener.onClickDeleteMistake(x,0)
        }
        holder.delete.setOnLongClickListener() {
            listener.onClickDeleteMistake(x,1)
true
        }

        holder.update_mistake.setOnClickListener()
        {
            listener.onClickUpdateMistake(x)
        }

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
    private fun generateRandomColor(): Int {
        val rnd = Random
        return Color.rgb(rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }
}

