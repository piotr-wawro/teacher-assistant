package com.example.asystentnauczyciela.view

import android.os.Bundle
import android.text.TextUtils
import android.transition.AutoTransition
import android.transition.TransitionManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Student
import com.example.asystentnauczyciela.view_model.Adapters.StudentListAdapter
import com.example.asystentnauczyciela.view_model.AllStudentsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_add_student.view.*
import kotlinx.android.synthetic.main.fragment_all_groups.*
import kotlinx.android.synthetic.main.fragment_all_students.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var studentViewModel: AllStudentsViewModel
/**
 * A simple [Fragment] subclass.
 * Use the [StudentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudentsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var myAdapter: StudentListAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        studentViewModel = ViewModelProvider(requireActivity()).get(AllStudentsViewModel::class.java)

        myAdapter = StudentListAdapter(studentViewModel.allStudent, studentViewModel)
        myLayoutManager = LinearLayoutManager(context)

        studentViewModel.allStudent.observe(viewLifecycleOwner, {
            myAdapter.notifyDataSetChanged()
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_all_students, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.students_list)

        recyclerView = recyclerViewAllStudents.apply {
            this.layoutManager = myLayoutManager
            this.adapter = myAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_add_student, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        bottomSheetView.buttonAddStudent.setOnClickListener {
            val firstName = bottomSheetView.editTextFirstName.text.toString()
            val lastName = bottomSheetView.editTextLastName.text.toString()
            bottomSheetView.editTextFirstName.setText("")
            bottomSheetView.editTextLastName.setText("")

            if(inputValidation(firstName, lastName)) {
                val student = Student(0, firstName, lastName)
                studentViewModel.addStudent(student)
                Toast.makeText(requireContext(), "Student dodany", Toast.LENGTH_LONG).show()
            }
        }

        imageButtonShowStudentInput.setOnClickListener {
            if(bottomSheetDialog.isShowing) {
                bottomSheetDialog.hide()
            }
            else {
                bottomSheetDialog.show()
            }
        }
    }

    private fun inputValidation(firstName: String, lastName: String): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StudentsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StudentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}