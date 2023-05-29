package com.methe.assignmentapp.view.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.methe.assignmentapp.databinding.FragmentNoteBinding
import com.methe.assignmentapp.model.Note
import com.methe.assignmentapp.viewmodel.ViewModelFactory

class NoteFragment : Fragment() {

    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NoteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = NoteAdapter(::onClicked)
        binding.rvNotes.layoutManager = LinearLayoutManager(requireContext())
        binding.rvNotes.setHasFixedSize(true)
        binding.rvNotes.adapter = adapter

        val mainViewModel = obtainViewModel(requireActivity() as AppCompatActivity)
        mainViewModel.getAllNotes().observe(viewLifecycleOwner) { noteList ->
            if (noteList != null) {
                adapter.setListNotes(noteList)
            }
        }

        binding.fabAdd.setOnClickListener {
            val noteData = Note(
                id = 0,
                description = "",
                date = ""
            )
            val noteBlack = NoteFragmentDirections.actionNoteFragmentToAddNoteFragment(noteData)
            findNavController().navigate(noteBlack)
        }
    }

    private fun onClicked(note: Note) {
        val noteData = Note(
            id = note.id,
            description = note.description,
            date = note.date
        )
        val editNote = NoteFragmentDirections.actionNoteFragmentToAddNoteFragment(noteData)
        findNavController().navigate(editNote)

    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}