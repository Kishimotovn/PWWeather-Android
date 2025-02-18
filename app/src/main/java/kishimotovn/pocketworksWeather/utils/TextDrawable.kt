package kishimotovn.pocketworksWeather.utils

import android.graphics.*
import android.graphics.drawable.shapes.RoundRectShape
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.ShapeDrawable
import kotlin.math.min


class TextDrawable private constructor(builder: Builder) : ShapeDrawable(builder.shape) {

    private val textPaint: Paint
    private val borderPaint: Paint
    private val text: String?
    private val color: Int
    private val shape: RectShape?
    private val height: Int
    private val width: Int
    private val fontSize: Int
    private val radius: Float
    private val borderThickness: Int

    init {

        // shape properties
        shape = builder.shape
        height = builder.height
        width = builder.width
        radius = builder.radius

        // text and color
        text = if (builder.toUpperCase) builder.text!!.toUpperCase() else builder.text
        color = builder.color

        // text paint settings
        fontSize = builder.fontSize
        textPaint = Paint()
        textPaint.setColor(builder.textColor)
        textPaint.setAntiAlias(true)
        textPaint.setFakeBoldText(builder.isBold)
        textPaint.setStyle(Paint.Style.FILL)
        textPaint.setTypeface(builder.font)
        textPaint.setTextAlign(Paint.Align.CENTER)
        textPaint.strokeWidth = builder.borderThickness.toFloat()

        // border paint settings
        borderThickness = builder.borderThickness
        borderPaint = Paint()
        borderPaint.color = color
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderThickness.toFloat()

        // drawable paint color
        val paint = paint
        paint.color = color

    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val r = bounds

        // draw border
        if (borderThickness > 0) {
            drawBorder(canvas)
        }

        val count = canvas.save()
        canvas.translate(r.left.toFloat(), r.top.toFloat())

        // draw text
        val width = if (this.width < 0) r.width() else this.width
        val height = if (this.height < 0) r.height() else this.height
        val fontSize = if (this.fontSize < 0) min(width, height) / 2f else this.fontSize.toFloat()
        textPaint.textSize = fontSize
        canvas.drawText(text ?: "N/A", width / 2f, height / 2f - (textPaint.descent() + textPaint.ascent()) / 2f, textPaint)

        canvas.restoreToCount(count)
    }

    private fun drawBorder(canvas: Canvas) {
        val rect = RectF(bounds)
        rect.inset((borderThickness / 2f), (borderThickness / 2f))

        if (shape is OvalShape) {
            canvas.drawOval(rect, borderPaint)
        } else if (shape is RoundRectShape) {
            canvas.drawRoundRect(rect, radius, radius, borderPaint)
        } else {
            canvas.drawRect(rect, borderPaint)
        }
    }

    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        textPaint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicWidth(): Int {
        return width
    }

    override fun getIntrinsicHeight(): Int {
        return height
    }

    class Builder: IConfigBuilder, IShapeBuilder, IBuilder {

        var text: String? = null

        var color: Int = 0

        var borderThickness: Int = 0

        var width: Int = 0

        var height: Int = 0

        var font: Typeface? = null

        var shape: RectShape? = null

        var textColor: Int = 0

        var fontSize: Int = 0

        var isBold: Boolean = false

        var toUpperCase: Boolean = false

        var radius: Float = 0f

        init {
            text = ""
            color = Color.GRAY
            textColor = Color.WHITE
            borderThickness = 0
            width = -1
            height = -1
            shape = RectShape()
            font = Typeface.create("sans-serif-light", Typeface.NORMAL)
            fontSize = -1
            isBold = false
            toUpperCase = false
        }

        override fun width(width: Int): IConfigBuilder {
            this.width = width
            return this
        }

        override fun height(height: Int): IConfigBuilder {
            this.height = height
            return this
        }

        override fun textColor(color: Int): IConfigBuilder {
            this.textColor = color
            return this
        }

        override fun withBorder(thickness: Int): IConfigBuilder {
            this.borderThickness = thickness
            return this
        }

        override fun useFont(font: Typeface): IConfigBuilder {
            this.font = font
            return this
        }

        override fun fontSize(size: Int): IConfigBuilder {
            this.fontSize = size
            return this
        }

        override fun bold(): IConfigBuilder {
            this.isBold = true
            return this
        }

        override fun toUpperCase(): IConfigBuilder {
            this.toUpperCase = true
            return this
        }

        override fun beginConfig(): IConfigBuilder {
            return this
        }

        override fun endConfig(): IShapeBuilder {
            return this
        }

        override fun rect(): IBuilder {
            this.shape = RectShape()
            return this
        }

        override fun round(): IBuilder {
            this.shape = OvalShape()
            return this
        }

        override fun roundRect(radius: Int): IBuilder {
            this.radius = radius.toFloat()
            val radii = floatArrayOf(radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat())
            this.shape = RoundRectShape(radii, null, null)
            return this
        }

        override fun buildRect(text: String, color: Int): TextDrawable {
            rect()
            return build(text, color)
        }

        override fun buildRoundRect(text: String, color: Int, radius: Int): TextDrawable {
            roundRect(radius)
            return build(text, color)
        }

        override fun buildRound(text: String, color: Int): TextDrawable {
            round()
            return build(text, color)
        }

        override fun build(text: String, color: Int): TextDrawable {
            this.color = color
            this.text = text
            return TextDrawable(this)
        }
    }

    interface IConfigBuilder {
        fun width(width: Int): IConfigBuilder

        fun height(height: Int): IConfigBuilder

        fun textColor(color: Int): IConfigBuilder

        fun withBorder(thickness: Int): IConfigBuilder

        fun useFont(font: Typeface): IConfigBuilder

        fun fontSize(size: Int): IConfigBuilder

        fun bold(): IConfigBuilder

        fun toUpperCase(): IConfigBuilder

        fun endConfig(): IShapeBuilder
    }

    interface IBuilder {

        fun build(text: String, color: Int): TextDrawable
    }

    interface IShapeBuilder {

        fun beginConfig(): IConfigBuilder

        fun rect(): IBuilder

        fun round(): IBuilder

        fun roundRect(radius: Int): IBuilder

        fun buildRect(text: String, color: Int): TextDrawable

        fun buildRoundRect(text: String, color: Int, radius: Int): TextDrawable

        fun buildRound(text: String, color: Int): TextDrawable
    }

    companion object {
        private val SHADE_FACTOR = 0.9f

        fun builder(): IShapeBuilder {
            return Builder()
        }
    }
}