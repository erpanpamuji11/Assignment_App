package com.methe.assignmentapp.view.screen

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.methe.assignmentapp.BuildConfig
import com.methe.assignmentapp.R
import com.methe.assignmentapp.databinding.FragmentScreenTwoBinding

class ScreenTwoFragment : Fragment() {

    private var _binding: FragmentScreenTwoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenTwoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        versionInfo()

        binding.apply {
            btnQrCode.setOnClickListener {
                findNavController().navigate(R.id.action_screenTwoFragment_to_qrCodeFragment)
            }

            btnNoteCrud.setOnClickListener {
                findNavController().navigate(R.id.action_screenTwoFragment_to_noteFragment)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun versionInfo(){
        val manufacturer = Build.MANUFACTURER+""
        val model = Build.MODEL+""
        val build = Build.VERSION.RELEASE+""
        val sdk = Build.VERSION.SDK_INT.toString()+""
        val versionCode = BuildConfig.VERSION_CODE

        binding.tvVersionInfo.text = "Manufacture: $manufacturer\n" +
                "Model: $model\n" +
                "Build: $build\n" +
                "SDK: $sdk\n" +
                "Version Code: $versionCode"
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}