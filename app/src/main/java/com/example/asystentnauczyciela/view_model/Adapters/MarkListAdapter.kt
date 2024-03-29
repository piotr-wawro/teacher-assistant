package com.example.asystentnauczyciela.view_model.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Mark
import com.example.asystentnauczyciela.view_model.MarkViewModel
import java.text.SimpleDateFormat

class MarkListAdapter(var marks: LiveData<List<Mark>>, var viewModel: MarkViewModel ): RecyclerView.Adapter<MarkListAdapter.Holder>() {

    inner class Holder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.mark_row, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val markValue = holder.itemView.findViewById<TextView>(R.id.textViewValue)
        val date = holder.itemView.findViewById<TextView>(R.id.textViewDate)
        val note = holder.itemView.findViewById<TextView>(R.id.textViewMarkNote)
        val deleteButton = holder.itemView.findViewById<ImageButton>(R.id.buttonDeleteMark)

        markValue.text = marks.value?.get(position)?.value.toString()
        date.text = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(marks.value?.get(position)?.date)
        note.text = marks.value?.get(position)?.note.toString()
        deleteButton.setOnClickListener { viewModel.deleteMark(marks.value?.get(position)!!) }
    }

    override fun getItemCount(): Int {
        return marks.value?.size ?: 0
    }
}