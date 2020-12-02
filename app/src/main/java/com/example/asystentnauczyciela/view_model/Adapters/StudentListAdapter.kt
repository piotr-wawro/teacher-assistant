package com.example.asystentnauczyciela.view_model.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Student
import com.example.asystentnauczyciela.view_model.AllStudentsViewModel

class StudentListAdapter(var students: LiveData<List<Student>>, var studentViewModel: AllStudentsViewModel): RecyclerView.Adapter<StudentListAdapter.StudentHolder>() {

    inner class StudentHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.student_row, parent, false)
        return StudentHolder(view)
    }

    override fun onBindViewHolder(holder: StudentHolder, position: Int) {
        var firstName = holder.itemView.findViewById<TextView>(R.id.textViewFirstName)
        var lastName = holder.itemView.findViewById<TextView>(R.id.textViewLastName)
        var deleteButton = holder.itemView.findViewById<Button>(R.id.buttonDeleteStudent)

        firstName.text = students.value?.get(position)?.firstName.toString()
        lastName.text = students.value?.get(position)?.lastName.toString()
        deleteButton.setOnClickListener { studentViewModel.deleteStudent(students.value?.get(position)!!) }
    }

    override fun getItemCount(): Int {
        return students.value?.size ?: 0
    }
}