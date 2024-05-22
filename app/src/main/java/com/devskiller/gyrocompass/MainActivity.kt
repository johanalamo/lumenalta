package com.devskiller.gyrocompass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.devskiller.gyrocompass.databinding.ActivityMainBinding
import java.lang.ref.WeakReference

class MainActivity : FragmentActivity() {

    private class AccelerometerSensorDataChangedListener(parent: MainActivity) : SensorEventListener {

        private val mWeakParent = WeakReference(parent)

        override fun onSensorChanged(event: SensorEvent) {
            mWeakParent.get()?.run {
                // START CHANGES
                // END CHANGES

                tryToCalculateRotation()
            }
        }

        override fun onAccuracyChanged(
            sensor: Sensor,
            accuracy: Int
        ) = Unit
    }

    private class MagneticFieldSensorDataChangedListener(parent: MainActivity) : SensorEventListener {

        private val mWeakParent = WeakReference(parent)

        override fun onSensorChanged(event: SensorEvent) {
            mWeakParent.get()?.run {
                // START CHANGES
                // END CHANGES

                tryToCalculateRotation()
            }
        }

        override fun onAccuracyChanged(
            sensor: Sensor,
            accuracy: Int
        ) = Unit
    }

    private var mGeomagneticValues: FloatArray? = null
    private var mGravityValues: FloatArray? = null
    private var mLastAccelerometerSensorEventListener: SensorEventListener? = null
    private var mLastMagneticFieldSensorEventListener: SensorEventListener? = null
    private var mViewBinding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(ActivityMainBinding.inflate(layoutInflater).run {
            mViewBinding = this
            root
        })

        val sensorManager = (getSystemService(Context.SENSOR_SERVICE) as SensorManager)
        val sensorsAvailable = sensorManager.getSensorList(Sensor.TYPE_ALL)
        val sensorsRequired = listOf(
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD))
        if (!sensorsAvailable.containsAll(sensorsRequired)) {
            Toast.makeText(this, R.string.sensors_unavailable, Toast.LENGTH_SHORT)
                    .show()

            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        (getSystemService(Context.SENSOR_SERVICE) as SensorManager).run {
            // START CHANGES
            // END CHANGES
        }
    }

    override fun onStop() {
        (getSystemService(Context.SENSOR_SERVICE) as SensorManager).run {
            unregisterListener(mLastAccelerometerSensorEventListener!!)
            mLastAccelerometerSensorEventListener = null

            unregisterListener(mLastMagneticFieldSensorEventListener!!)
            mLastMagneticFieldSensorEventListener = null
        }

        super.onStop()
    }

    override fun onDestroy() {
        mViewBinding = null

        super.onDestroy()
    }

    private fun tryToCalculateRotation() {
        if ((mGeomagneticValues != null) && (mGravityValues != null)) {
            // START CHANGES
            // END CHANGES
        }
    }
}
