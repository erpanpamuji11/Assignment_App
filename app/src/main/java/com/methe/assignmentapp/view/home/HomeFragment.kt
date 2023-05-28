package com.methe.assignmentapp.view.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import com.methe.assignmentapp.R
import com.methe.assignmentapp.databinding.FragmentHomeBinding
import com.methe.assignmentapp.utils.Constants
import com.methe.assignmentapp.utils.PrefsHelper
import com.methe.assignmentapp.view.screen.ScreenOneFragment
import com.methe.assignmentapp.view.screen.ScreenTwoFragment

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}