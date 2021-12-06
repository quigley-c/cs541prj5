package com.example.cs541_prj5

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameActivity : AppCompatActivity() {
    val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
    val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)
    val gravListener: GravitySensor = GravitySensor()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val ball = RollingBall(this, gravListener)
        setContentView(ball)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(gravListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(gravListener)
    }
}

class RollingBall : SurfaceView, SurfaceHolder.Callback {
    constructor(context: Context, gravListener: GravitySensor) : super(context)
    var x=0; var y=0;


    override fun surfaceCreated(surfaceHolder: SurfaceHolder)   {
    }

    override fun surfaceChanged(surfaceHolder: SurfaceHolder, fmt: Int, width: Int, height: Int)   {
    }

    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder)   {
    }

    override fun onDraw(canvas: Canvas) {
        val img = BitmapFactory.decodeFile("./ball.png")
        val axes = gravListener.getAxes()
        x = axes[0]
        y = axes[1]
        canvas.drawBitmap(img, x, y, null)
    }
}

class GravitySensor : SensorEventListener {
    private val sensorVals: Array<Number?> = arrayOfNulls<Number>(2)

    override fun onSensorChanged(event: SensorEvent) {
        //understanding orientation
        sensorVals[0] = event.values[0]
        sensorVals[1] = event.values[1]
        sensorVals[2] = event.values[2]
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
    }

    fun getAxes(): Array<Number?>   {
        return sensorVals
    }
}

