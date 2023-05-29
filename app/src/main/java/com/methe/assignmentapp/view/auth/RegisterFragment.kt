package com.methe.assignmentapp.view.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.methe.assignmentapp.R
import com.methe.assignmentapp.databinding.FragmentRegisterBinding
import com.methe.assignmentapp.utils.Constants
import com.methe.assignmentapp.utils.PrefsHelper

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPrefs: PrefsHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefs = PrefsHelper(requireContext())

        binding.apply {

            btnRegis.setOnClickListener {
                if (etEmail.text.isNotEmpty() && etPassword.text!!.isNotEmpty()) {
                    registerUser()
                    findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
                } else {
                    Toast.makeText(requireContext(), "Register Failed", Toast.LENGTH_SHORT).show()
                }
            }

            btnMoveLogin.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        }
    }

    private fun registerUser(){
        binding.apply {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()
            sharedPrefs.put(Constants.PREF_EMAIL, email)
            sharedPrefs.put(Constants.PREF_PASSWORD, password)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}