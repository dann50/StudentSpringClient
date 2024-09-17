package com.example.springclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.springclient.R
import com.example.springclient.databinding.StudentEditorBinding
import com.example.springclient.network.Student
import com.example.springclient.ui.StudentDisplayViewModel

private const val ID_PARAM = "id"

class StudentDisplayFragment : Fragment() {
    private lateinit var binding: StudentEditorBinding
    private var id: Int = 0
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            // if student doesn't exist, id = 0
            id = it.getInt(ID_PARAM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StudentEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding.saveBtn) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = insets.bottom + binding.saveBtn.marginBottom
            }
            windowInsets
        }

        val vm = ViewModelProvider(this, StudentDisplayViewModel.Factory)[StudentDisplayViewModel::class.java]

        if (id != 0) {
            // if there's an existing student
            student = vm.loadStudent(id)
            binding.nameEditText.setText(student.name)
            binding.dobView.setText(student.dob)
            binding.deptEditText.setText(student.dept)
            binding.majorEditText.setText(student.major)
        }

        binding.saveBtn.setOnClickListener {

            val name = binding.nameEditText.getText().toString()
            val dob = binding.dobView.getText().toString()
            val dept = binding.deptEditText.getText().toString()
            val major = binding.majorEditText.getText().toString()

            // verify that editText fields aren't empty
            if (name.isNotBlank() && dob.isNotBlank() && dept.isNotBlank() && major.isNotBlank()) {
                val tempStudent = Student(id, name, dob, dept, major)
                if (id == 0) vm.save(tempStudent) else vm.update(tempStudent)
                parentFragmentManager.popBackStack()
            } else {
                Toast.makeText(
                    this@StudentDisplayFragment.context,
                    resources.getString(R.string.fill_all_fields),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    companion object {
        @JvmStatic
        fun newInstance(id: Int) =
            StudentDisplayFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_PARAM, id)
                }
            }
    }
}