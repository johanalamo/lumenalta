package com.devskiller.gyrocompass

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.devskiller.gyrocompass.databinding.ActivityMainBinding
import java.lang.ref.WeakReference


class MainActivity : FragmentActivity() {

    private class AccelerometerSensorDataChangedListener(parent: MainActivity) : SensorEventListener {

        private val mWeakParent = WeakReference(parent)

        override fun onSensorChanged(event: SensorEvent) {
            mWeakParent.get()?.run {
//                println("avilan: onSensorChanged " + event.accuracy )
//                println("avilan: onSensorChanged " + event.timestamp )
//                println("avilan: onSensorChanged " + event.values.get(0) )
//                println("avilan: onSensorChanged " + event.sensor.name )
                // START CHANGES
                // END CHANGES
                mGravityValues = event.values
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
//                println("avilan2: onSensorChanged " + event.accuracy )
//                println("avilan2: onSensorChanged " + event.timestamp )
//                println("avilan2: onSensorChanged " + event.values.get(0) )
//                println("avilan2: onSensorChanged " + event.sensor.name )
                // START CHANGES
                // END CHANGES
                mGeomagneticValues = event.values

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
            Toast.makeText(this, R.string.sensors_available, Toast.LENGTH_SHORT)
                .show()

        val accelerometerSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        val magFieldSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        mLastAccelerometerSensorEventListener = AccelerometerSensorDataChangedListener(this)
        mLastMagneticFieldSensorEventListener = MagneticFieldSensorDataChangedListener(this)
//        accelerometerSensor.registerListener
        sensorManager.registerListener(mLastAccelerometerSensorEventListener, accelerometerSensor, SensorManager.SENSOR_DELAY_FASTEST, 0)
        sensorManager.registerListener(mLastMagneticFieldSensorEventListener, magFieldSensor, SensorManager.SENSOR_DELAY_FASTEST, 0)

//        sensorManager.requestTriggerSensor(mLastAccelerometerSensorEventListener, accelerometerSensor)
//        sensorManager.
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
    var time = System.currentTimeMillis()
    var newXValue = 0f
    var newYValue = 0f
    var newValue = 0f
    private fun tryToCalculateRotation() {

        if ((mGeomagneticValues != null) && (mGravityValues != null)) {
            if ((System.currentTimeMillis() % 100).toInt() == 0) {
                println("Here I got the values but trying to find the formula to calculate rotation degrees. ")
                println("avilan mGeomagneticValues.leng " + mGeomagneticValues!![0] + " " + mGeomagneticValues!![1] + " " + mGeomagneticValues!![2] + " ")
                println("avilan mGravityValues.leng " + mGravityValues!![0] + " " + mGravityValues!![1] + " " + mGravityValues!![2] + " ")

                newXValue = ((System.currentTimeMillis() /  1000L) % 180).toFloat() // trying to get the formula
                newYValue = ((System.currentTimeMillis() /  1000L) % 180).toFloat() // trying to get the formula
                newValue = ((System.currentTimeMillis() /  1000L) % 180).toFloat() // trying to get the formula
            }
            // START CHANGES
            // END CHANGES
            // Assignement
            mViewBinding?.ivNeedle?.rotationX = newXValue
            mViewBinding?.ivNeedle?.rotationY = newYValue
            mViewBinding?.ivNeedle?.rotation = newValue

        }
    }
}
