package com.example.springclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.springclient.R
import com.example.springclient.databinding.FragmentStudentListBinding
import com.example.springclient.network.Student
import com.example.springclient.ui.ListViewModel
import com.example.springclient.ui.StudentListAdapter
import com.example.springclient.ui.StudentsUiState

class StudentListFragment : Fragment() {
    private var binding: FragmentStudentListBinding? = null
    private lateinit var vm: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentStudentListBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setOnApplyWindowInsetsListener(binding!!.fab) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                bottomMargin = insets.bottom + binding!!.fab.marginBottom
                rightMargin = insets.right + binding!!.fab.marginEnd
            }
            windowInsets
        }

        vm = ViewModelProvider(this, ListViewModel.Factory)[ListViewModel::class.java]
        val recyclerView = binding!!.recyclerView

        recyclerView.layoutManager = LinearLayoutManager(context)
        val itemDecor = DividerItemDecoration(recyclerView.context, DividerItemDecoration.HORIZONTAL)
        recyclerView.addItemDecoration(itemDecor)

        val listAdapter = StudentListAdapter(layoutInflater, this::navTo)
        recyclerView.adapter = listAdapter

        // create new student with no id (0)
        binding!!.fab.setOnClickListener { navTo(Student(0, "", "", "", "")) }

        vm.studentsUiState.observe(viewLifecycleOwner) { uiState ->
            when (uiState) {
                is StudentsUiState.Loading -> {
                    binding!!.loadingText.text = getString(R.string.loading_text)
                    binding!!.loadingText.visibility = View.VISIBLE
                }
                is StudentsUiState.Success -> {
                    binding!!.loadingText.visibility = View.GONE
                    listAdapter.submitList(uiState.students)
                }
                is StudentsUiState.Error -> {
                    binding!!.loadingText.apply {
                        text = getString(R.string.error_text)
                        visibility = View.VISIBLE
                        setOnClickListener { vm.getStudents() }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun navTo(student: Student) {
        parentFragmentManager.commit {
            replace(R.id.main, StudentDisplayFragment.newInstance(student.id))
            addToBackStack(null)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        }

    }
}