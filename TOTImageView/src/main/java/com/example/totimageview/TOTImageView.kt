package com.example.totimageview

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Shader
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class TOTImageView(private val imageView: ImageView) {
    private var imageUrl: String? = null
    private var placeholderResourceId: Int? = null
    private var circularBitmap: Boolean = false

    fun src(url: String): TOTImageView {
        this.imageUrl = url
        return this
    }

    fun placeholder(placeholderResourceId: Int): TOTImageView {
        this.placeholderResourceId = placeholderResourceId
        return this
    }

    fun circle(): TOTImageView {
        this.circularBitmap = true
        return this
    }

    fun load(context: Context) {
        GlobalScope.launch(Dispatchers.IO) {
            placeholderResourceId?.let { resourceId ->
                GlobalScope.launch(Dispatchers.Main) {
                    imageView.setImageResource(resourceId)
                }
            }
            try {
                placeholderResourceId?.let { resourceId ->
                    GlobalScope.launch(Dispatchers.Main) {


                        val placeHolderImage = if (circularBitmap) {
                            val bitmap = drawableToBitmap(context, resourceId)
                            createCircularBitmap(bitmap)
                        } else {
                            drawableToBitmap(context, resourceId)
                        }

                        imageView.setImageBitmap(placeHolderImage as Bitmap?)
                    }
                }

                val url = URL(imageUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                val input: InputStream = connection.inputStream
                val bitmap = BitmapFactory.decodeStream(input)

                val finalBitmap = if (circularBitmap) {
                    createCircularBitmap(bitmap)
                } else {
                    bitmap
                }

                GlobalScope.launch(Dispatchers.Main) {
                    imageView.setImageBitmap(finalBitmap)
                }
            } catch (e: Exception) {
                e.printStackTrace()
                placeholderResourceId?.let { resourceId ->
                    GlobalScope.launch(Dispatchers.Main) {


                        val placeHolderImage = if (circularBitmap) {
                            val bitmap = drawableToBitmap(context, resourceId)
                            createCircularBitmap(bitmap)
                        } else {
                            drawableToBitmap(context, resourceId)
                        }

                        imageView.setImageBitmap(placeHolderImage as Bitmap?)
                    }
                }
            }
        }
    }

    private fun createCircularBitmap(bitmap: Bitmap): Bitmap {
        val circularBitmap =
            Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(circularBitmap)
        val paint = android.graphics.Paint()
        val shader = android.graphics.BitmapShader(
            bitmap,
            android.graphics.Shader.TileMode.CLAMP,
            android.graphics.Shader.TileMode.CLAMP
        )
        paint.shader = shader
        paint.isAntiAlias = true
        val radius = bitmap.width.coerceAtMost(bitmap.height) / 2f
        canvas.drawCircle(bitmap.width / 2f, bitmap.height / 2f, radius, paint)
        return circularBitmap
    }

    fun drawableToBitmap(context: Context, drawableId: Int): Bitmap {
        val drawable: Drawable? = ContextCompat.getDrawable(context, drawableId)
        val bitmap = Bitmap.createBitmap(drawable!!.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }


}

//fun ImageView.load(): ImageLoader {
//    return ImageLoader(this)
//}