package com.methe.assignmentapp.view.screen

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.BatteryManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.methe.assignmentapp.databinding.FragmentScreenOneBinding
import java.text.DateFormat
import java.util.*

class ScreenOneFragment : Fragment(), SensorEventListener {

    private var _binding: FragmentScreenOneBinding? = null
    private val binding get() = _binding!!
    private lateinit var calender: Calendar
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

//    lateinit var featureGRV: GridView
//    lateinit var featureList: List<GridViewModal>
    private lateinit var sensorManager: SensorManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        _binding = FragmentScreenOneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        featureGRV = binding.gridView
//        featureList = ArrayList()
//
//        featureList = featureList + GridViewModal("Accelerator", R.drawable.ic_startup_white)
//        featureList = featureList + GridViewModal("Gyroscope", R.drawable.ic_startup_white)
//        featureList = featureList + GridViewModal("MagneteMatter", R.drawable.ic_startup_white)
//        featureList = featureList + GridViewModal("Location", R.drawable.ic_startup_white)
//
//        val courseAdapter = GridFeatureAdapter(featureList = featureList, requireContext())
//
//        featureGRV.adapter = courseAdapter
//        featureGRV.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
//            // inside on click method we are simply displaying
//            // a toast message with course name.
//            Toast.makeText(
//                requireContext(), featureList[position].featureName + " selected",
//                Toast.LENGTH_SHORT
//            ).show()
//        }

        calender = Calendar.getInstance()
        val currentDate = DateFormat.getDateInstance(android.icu.text.DateFormat.FULL).format(calender.time)
        binding.tvDateNow.text = currentDate

        requireActivity().registerReceiver(mBatteryInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))

        setUpSensorStuff()
        setUpSensorGyroscope()
        binding.btnCurrentLocation.setOnClickListener {
            fetchLocation()
        }
    }

    private val mBatteryInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val level = intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1)
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)

            val batteryPct = level * 100 / scale.toFloat()
            binding.tvBattery.text = "Battery Status: $batteryPct%"
        }

    }

    private fun setUpSensorStuff() {
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager

        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI
            )
        }

        sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)?.also { magneticField ->
            sensorManager.registerListener(
                this,
                magneticField,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    private fun setUpSensorGyroscope(){
        sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)?.also {
            sensorManager.registerListener(
                this,
                it,
                SensorManager.SENSOR_DELAY_NORMAL,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val sides = event.values[0]
            val upDown = event.values[1]
            val ward = event.values[2]

            binding.square.apply {
                rotationX = upDown * 3f
                rotationY = sides * 3f
                rotation = -sides
                translationX = sides * -10
                translationY = sides * 10
            }

            val color = if (upDown.toInt() == 0 && sides.toInt() == 0) Color.GREEN else Color.GRAY
            binding.square.apply {
                setBackgroundColor(color)
                text = "X: ${sides}\n Y: ${upDown}\n Z: ${ward}"
            }
        }

        if (event?.sensor?.type == Sensor.TYPE_GYROSCOPE) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            binding.tvGyroscope.text = "X: $x \nY: $y \nZ: $z"
        }

        if (event?.sensor?.type == Sensor.TYPE_MAGNETIC_FIELD) {

        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        return
    }

    private fun fetchLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val task = fusedLocationProviderClient.lastLocation

        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if (it != null){
                val location = "Longitude: ${it.latitude}, Latitude: ${it.longitude}"
                binding.tvCurrentLocation.apply {
                    text = location
                    visibility = View.VISIBLE
                }
            } else {
                Toast.makeText(requireContext(), "Aktifkan Lokasimu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        sensorManager.unregisterListener(this)
    }
}