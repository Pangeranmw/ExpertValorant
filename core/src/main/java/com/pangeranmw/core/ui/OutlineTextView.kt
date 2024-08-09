package com.pangeranmw.core.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.pangeranmw.core.R

class OutlineTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var strokeColor: Int = Color.BLACK
    private var strokeWidth: Float = 1f

    init {
        // Retrieve custom attributes, if any
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.OutlineTextView,
            0, 0).apply {
            try {
                strokeColor = getColor(R.styleable.OutlineTextView_outlineColor, Color.BLACK)
                strokeWidth = getDimension(R.styleable.OutlineTextView_outlineWidth, 1f)
            } finally {
                recycle()
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        val textColor = currentTextColor

        // Draw the stroke
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = strokeWidth
        setTextColor(strokeColor)
        super.onDraw(canvas)

        // Draw the text
        paint.style = Paint.Style.FILL
        setTextColor(textColor)
        super.onDraw(canvas)
    }
}