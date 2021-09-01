package com.example.clockanalog

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class Clock(context: Context?, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    View(context, attrs, defStyleAttr) {

    private val painter = Paint()
    private val rect = Rect()


    override fun onDraw(canvas: Canvas?) {
        drawCircle(canvas)
        drawHands(canvas)
        drawDivision(canvas)
        postInvalidateDelayed(500)
    }

    private fun drawCircle(canvas: Canvas?) {
        setPainter(Color.BLACK, Paint.Style.STROKE, 8f)
        canvas?.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), RADIUS, painter)
    }

    private fun setPainter(colour: Int, setStroke: Paint.Style, strokeWidth: Float) {

        painter.apply {
            reset()
            color = colour
            style = setStroke
            painter.strokeWidth = strokeWidth
            isAntiAlias = true
        }

    }

    private fun drawHands(canvas: Canvas?) {
        val calendar = Calendar.getInstance()
        var hour = calendar.get(Calendar.HOUR_OF_DAY)
        if (hour > 12) hour -= 12
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        drawHourHand(canvas, (hour + minute / 60.0) * 5f)
        drawMinuteHand(canvas, minute.toFloat())
        drawSecondsHand(canvas, second.toFloat())
    }

    private fun drawMinuteHand(canvas: Canvas?, location: Float) {
        painter.reset()
        setPainter(Color.BLACK, Paint.Style.STROKE, 8f)
        val angle = Math.PI * location / 30 - Math.PI / 2

        canvas?.drawLine(
            (width / 2).toFloat(), (height / 2).toFloat(),
            ((width / 2).toFloat() + cos(angle) * handSize).toFloat(),
            ((height / 2).toFloat() + sin(angle) * handSizeHour).toFloat(),
            painter
        )
    }

    private fun drawHourHand(canvas: Canvas?, location: Double) {
        painter.reset()
        setPainter(Color.RED, Paint.Style.STROKE, 10f)
        val angle = Math.PI * location / 30 - Math.PI / 2

        canvas?.drawLine(
            (width / 2).toFloat(), (height / 2).toFloat(),
            ((width / 2).toFloat() + cos(angle) * handSizeHour).toFloat(),
            ((height / 2).toFloat() + sin(angle) * handSizeHour).toFloat(),
            painter
        )
    }

    private fun drawSecondsHand(canvas: Canvas?, location: Float) {
        painter.reset()
        setPainter(Color.GREEN, Paint.Style.STROKE, 8F)
        val angle = Math.PI * location / 30 - Math.PI / 2

        canvas?.drawLine(
            (width / 2).toFloat(), (height / 2).toFloat(),
            ((width / 2).toFloat() + cos(angle) * handSize).toFloat(),
            ((height / 2).toFloat() + sin(angle) * handSizeHour).toFloat(),
            painter
        )
    }

    private fun drawDivision(canvas: Canvas?) {
        painter.reset()
        setPainter(Color.BLUE, Paint.Style.STROKE, 8F)
        painter.textSize = 60f
        for (i in 1..12) {
            val divisions = i.toString()
            painter.getTextBounds(divisions, 0, divisions.length, rect)
            val angle = Math.PI / 6 * (i - 3)

            canvas?.drawText(
                divisions, ((width / 2) + cos(angle) * RADIUS - rect.width() / 2).toFloat(),
                ((height / 2) + sin(angle) * RADIUS + rect.height() / 2).toFloat(),
                painter
            )
        }
    }


    private companion object {
        private const val RADIUS = 300f
        const val handSize = RADIUS - RADIUS / 4
        const val handSizeHour = RADIUS - RADIUS / 2
    }
}
