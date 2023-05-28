package com.methe.assignmentapp.view.auth

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.methe.assignmentapp.R
import com.methe.assignmentapp.databinding.FragmentLoginBinding
import com.methe.assignmentapp.utils.Constants
import com.methe.assignmentapp.utils.PrefsHelper
import com.methe.assignmentapp.view.home.HomeActivity
import java.util.concurrent.Executor

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    lateinit var sharedPrefs: PrefsHelper

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedPrefs = PrefsHelper(requireContext())


        binding.apply {
            btnLogin.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()
                if (sharedPrefs.getString(Constants.PREF_EMAIL) == email && sharedPrefs.getString(Constants.PREF_PASSWORD) == password) {
                    sharedPrefs.put(Constants.PREF_IS_LOGIN, true)
                    startActivity(Intent(requireContext(), HomeActivity::class.java))
                    Toast.makeText(requireContext(), "Login Succeed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }

            tvMoveSignUp.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }

        checkDeviceHasBiometric()
        biometricAuth()
        binding.ivFingerprint.setOnClickListener {
            biometricPrompt.authenticate(promptInfo)
        }
    }

    private fun checkDeviceHasBiometric(){
        val biometricManager = BiometricManager.from(requireContext())
        when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                Log.d("TAG", "App can authenticate biometric")
                binding.apply {
                    tvCheckBiometric.text = "App can authenticate biometric"
                }
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                Log.d("TAG", "biometric unavailable")
                binding.apply {
                    tvCheckBiometric.text = "biometric unavailable"
                }
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED, BIOMETRIC_STRONG or DEVICE_CREDENTIAL)
                }
                startActivityForResult(enrollIntent, 100)
            }
        }
    }

    private fun biometricAuth(){
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(requireActivity(), executor, object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(requireContext(), "Authentication error: $errString", Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(requireContext(), "Authentication Success", Toast.LENGTH_SHORT).show()
                startActivity(Intent(requireContext(), HomeActivity::class.java))
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(requireContext(), "Authentication Failed", Toast.LENGTH_SHORT).show()
            }
        })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Login dengan Fingerprint")
            .setSubtitle("Tempelkan Jari anda")
            .setNegativeButtonText("Batalkan")
            .build()
    }

    override fun onStart() {
        super.onStart()
        if (sharedPrefs.getBoolean(Constants.PREF_IS_LOGIN)) {
            startActivity(Intent(requireContext(), HomeActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}