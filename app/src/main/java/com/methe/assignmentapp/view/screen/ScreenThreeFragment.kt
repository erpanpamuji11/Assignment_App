package com.methe.assignmentapp.view.screen

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.methe.assignmentapp.R
import com.methe.assignmentapp.databinding.FragmentScreenThreeBinding
import com.methe.assignmentapp.utils.PrefsHelper

class ScreenThreeFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentScreenThreeBinding? = null
    private val binding get() = _binding!!
    private var plotData: Boolean = true
    private lateinit var prefsHelper: PrefsHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScreenThreeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefsHelper = PrefsHelper(requireContext())

        binding.apply {
            btnMap.setOnClickListener {
                findNavController().navigate(R.id.action_screenThreeFragment_to_mapFragment)
            }

            btnLogout.setOnClickListener {
                prefsHelper.clear()
                requireActivity().finish()
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (plotData){
            plotData = false
        }

    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

//    override fun onPause() {
//        super.onPause()
//        if (thread != null){
//            thread.interrupt()
//        }
//        sensorManager.unregisterListener(this)
//    }
//
//    override fun onResume() {
//        super.onResume()
//        sensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME)
//    }

}