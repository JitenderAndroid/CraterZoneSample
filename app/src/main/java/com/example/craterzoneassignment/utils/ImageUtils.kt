package com.example.craterzoneassignment.utils

import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.widget.ImageView
import com.example.craterzoneassignment.models.Photo

object ImageHelper {

    fun getRoundedCornerBitmap(bitmap: Bitmap, pixels: Int): Bitmap {
        val output = Bitmap.createBitmap(
            bitmap.width, bitmap
                .height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = pixels.toFloat()
        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

    fun updateImageCorners(imageView: ImageView, cornerRadius: Int) {
        val bitmap = (imageView.drawable as? BitmapDrawable)?.bitmap
        if (bitmap != null)
            imageView.setImageBitmap(getRoundedCornerBitmap(bitmap, cornerRadius))
    }

    fun createImageUrl(photo: Photo) : String {
        var imageUrl = "https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_m.jpg"
        imageUrl = imageUrl.replace("{farm-id}", photo.farm.toString())
            .replace("{server-id}", photo.server)
            .replace("{id}", photo.id)
            .replace("{secret}", photo.secret)
        return imageUrl
    }
}