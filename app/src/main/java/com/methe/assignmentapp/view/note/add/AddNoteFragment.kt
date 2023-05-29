package com.methe.assignmentapp.view.note.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.methe.assignmentapp.R
import com.methe.assignmentapp.databinding.FragmentAddNoteBinding
import com.methe.assignmentapp.model.Note
import com.methe.assignmentapp.viewmodel.ViewModelFactory
import com.methe.mynotesapp.helper.DateHelper

class AddNoteFragment : Fragment() {

    private var _binding: FragmentAddNoteBinding? = null
    private val binding get() = _binding!!

    private var isEdit = false
    private var note: Note? = null
    private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel
    private val args by navArgs<AddNoteFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteAddUpdateViewModel = obtainViewModel(requireActivity() as AppCompatActivity)

        note = args.noteArguments

        if (note?.description.isNullOrBlank()) {
            note = Note()
        } else {
            isEdit = true
        }
        val appBarTitle: String
        val btnTitle: String
        if (isEdit) {
            appBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (note != null) {
                note?.let { note ->
                    binding.apply {
                        edtDescription.setText(note.description)
                    }
                }
            }
            binding.btnDelete.isVisible = true
        } else {
            appBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        binding.btnSubmit.apply {
            text = btnTitle
            setOnClickListener {
                val description = binding.edtDescription.text.toString().trim()
                when {
                    description.isEmpty() -> {
                        binding.edtDescription.error = getString(R.string.empty)
                    }
                    else -> {
                        note.let { note ->
                            note?.description = description
                        }
                        if (isEdit) {
                            noteAddUpdateViewModel.update(note as Note)
                            showToast(getString(R.string.changed))
                        } else {
                            note.let { note ->
                                note?.date = DateHelper.getCurrentDate()
                            }
                            noteAddUpdateViewModel.insert(note as Note)
                            showToast(getString(R.string.added))
                        }
                        findNavController().popBackStack()
                    }
                }
            }
        }
        binding.apply {
            tvAppbarTitle.text = appBarTitle
            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        binding.btnDelete.setOnClickListener {
            noteAddUpdateViewModel.delete(note as Note)
            showToast(getString(R.string.deleted))
            findNavController().popBackStack()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[NoteAddUpdateViewModel::class.java]
    }
}