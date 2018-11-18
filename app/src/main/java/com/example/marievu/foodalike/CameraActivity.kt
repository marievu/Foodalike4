package com.example.marievu.foodalike

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_camera.*
import java.io.ByteArrayOutputStream
import org.json.JSONObject
import android.graphics.drawable.BitmapDrawable
import com.example.marievu.foodalike.R.id.photoImageView
import com.loopj.android.http.JsonHttpResponseHandler
import com.loopj.android.http.RequestParams


class CameraActivity : AppCompatActivity() {

    val CAMERA_REQUEST_CODE =0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        cameraButton.setOnClickListener{
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(callCameraIntent.resolveActivity(packageManager) != null){
                startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
            }
        }
        selfieButton.setOnClickListener{

            val COMPRESSION_QUALITY = 100
            val encodedImage: String
            val byteArrayBitmapStream = ByteArrayOutputStream()
            photoImageView.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                    byteArrayBitmapStream)
            val b = byteArrayBitmapStream.toByteArray()
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT)

            HttpUtils.post("api/" + encodedImage,  RequestParams(), JsonHttpResponseHandler())

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            CAMERA_REQUEST_CODE->{
                if(resultCode == Activity.RESULT_OK && data!= null){
                    photoImageView.setImageBitmap(data.extras.get("data") as Bitmap)
                }
            }

            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getStringFromBitmap(photoImageView: Bitmap): String {
        /*
         * This functions converts Bitmap picture to a string which can be
         * JSONified.
         * */

        val COMPRESSION_QUALITY = 100
        val encodedImage: String
        val byteArrayBitmapStream = ByteArrayOutputStream()
        photoImageView.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY,
                byteArrayBitmapStream)
        val b = byteArrayBitmapStream.toByteArray()
        encodedImage = Base64.encodeToString(b, Base64.DEFAULT)
        return encodedImage
    }

//    var bm = (photoImageView.getDrawable() as BitmapDrawable).bitmap
//    var encodedImage = getStringFromBitmap(bm)
//    var jsonObj = JSONObject("{\"image\":\" + encodedImage + \"}")


}