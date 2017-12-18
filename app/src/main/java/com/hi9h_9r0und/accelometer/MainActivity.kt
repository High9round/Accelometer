package com.hi9h_9r0und.accelometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var mSensorManager: SensorManager

    lateinit var mAccLis: SensorEventListener
    lateinit var mAccelometerSensor: Sensor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        mAccelometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        mAccLis = AccelometerListener()

        mSensorManager.registerListener(mAccLis, mAccelometerSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onResume() {
        super.onResume()

        mSensorManager.registerListener(mAccLis, mAccelometerSensor, SensorManager.SENSOR_DELAY_UI)
    }
    override fun onPause() {
        super.onPause()

        mSensorManager.unregisterListener(mAccLis)
    }

    override fun onDestroy() {
        super.onDestroy()

        mSensorManager.unregisterListener(mAccLis)
    }

    private inner class AccelometerListener : SensorEventListener {

        override fun onSensorChanged(event: SensorEvent) {

            val accX = event.values[0].toDouble()
            val accY = event.values[1].toDouble()
            val accZ = event.values[2].toDouble()

            val angleXZ = Math.atan2(accX, accZ) * 180 / Math.PI
            val angleYZ = Math.atan2(accY, accZ) * 180 / Math.PI

            val result="[X]:" + String.format("%.4f", event.values[0]) +
                    "\n[Y]:" + String.format("%.4f", event.values[1]) +
                    "\n[Z]:" + String.format("%.4f", event.values[2]) +
                    "\n[angleXZ]: " + String.format("%.4f", angleXZ) +
                    "\n[angleYZ]: " + String.format("%.4f", angleYZ)

            Log.i("LOG", result)

            textView_Result.text=result

        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

        }
    }
}

