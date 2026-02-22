package com.example.data.repository

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.example.domain.repository.StepCounterRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StepCounterRepositoryImpl @Inject constructor(
    @param: ApplicationContext private val context: Context
) : StepCounterRepository {

    private val _steps = MutableStateFlow(0)
    override val steps: StateFlow<Int> = _steps.asStateFlow()

    private var sensorManager: SensorManager? = null
    private var stepSensor: Sensor? = null
    private var initialSteps = -1

    private val listener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            val totalSteps = event.values[0].toInt()
            if (initialSteps == -1) initialSteps = totalSteps
            _steps.value = totalSteps - initialSteps
        }
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    }

    override fun startCounting() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Log.e("StepCounter", "Device does not have a step counter sensor")
            return
        }

        sensorManager?.registerListener(listener, stepSensor, SensorManager.SENSOR_DELAY_UI)
    }

    override fun stopCounting() {
        sensorManager?.unregisterListener(listener)
    }
}