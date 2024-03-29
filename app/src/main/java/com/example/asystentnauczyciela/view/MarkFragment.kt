package com.example.asystentnauczyciela.view

import android.os.Bundle
import android.text.TextUtils
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.asystentnauczyciela.R
import com.example.asystentnauczyciela.model.Mark
import com.example.asystentnauczyciela.view_model.Adapters.MarkListAdapter
import com.example.asystentnauczyciela.view_model.MarkViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_add_mark.view.*
import kotlinx.android.synthetic.main.fragment_mark.*
import java.time.LocalDateTime
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private lateinit var viewModel: MarkViewModel
/**
 * A simple [Fragment] subclass.
 * Use the [MarkFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MarkFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var myAdapter: MarkListAdapter
    private lateinit var myLayoutManager: LinearLayoutManager
    private lateinit var recyclerView: RecyclerView

    val args: MarkFragmentArgs by navArgs()

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
        viewModel = ViewModelProvider(requireActivity()).get(MarkViewModel::class.java)
        viewModel.studentMarks(args.studentId, args.groupId)

        myAdapter = MarkListAdapter(viewModel.studentMarks, viewModel)
        myLayoutManager = LinearLayoutManager(context)

        viewModel.studentMarks.observe(viewLifecycleOwner, {
            myAdapter.notifyDataSetChanged()
        })

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mark, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.student_marks)

        recyclerView = recyclerViewMark.apply {
            this.layoutManager = myLayoutManager
            this.adapter = myAdapter
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
        }

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_add_mark, null)
        bottomSheetDialog.setContentView(bottomSheetView)

        bottomSheetView.buttonAddMark.setOnClickListener {
            if(!TextUtils.isEmpty(bottomSheetView.editTextMarkValue.toString())) {
                val mark = Mark(0, args.studentId, args.groupId, bottomSheetView.editTextMarkValue.text.toString().toInt(), Date(), bottomSheetView.editTextMarkNote.text.toString())
                viewModel.addMark(mark)
                bottomSheetView.editTextMarkValue.setText("")
                bottomSheetView.editTextMarkNote.setText("")
            }
        }

        imageButtonShowMarkInput.setOnClickListener {
            if(bottomSheetDialog.isShowing) {
                bottomSheetDialog.hide()
            }
            else {
                bottomSheetDialog.show()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MarkFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MarkFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}