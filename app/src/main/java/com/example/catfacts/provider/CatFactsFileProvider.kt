package com.example.catfacts.provider;

import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.core.content.FileProvider
import com.example.catfacts.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class CatFactsFileProvider : FileProvider(
    R.xml.file_provider
) {

    companion object {

        fun getImageFile(context: Context): File {
            val timestamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

            val storageDir: File? = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            storageDir?.mkdirs()
            
//            val storageDir = File(context.cacheDir, "images")
//            storageDir.mkdirs()

//            val storageDir = File(context.filesDir, "images")
//            storageDir.mkdirs()


            return File.createTempFile(
                "JPEG_${timestamp}_", /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
            )
        }

        fun getImageUri(context: Context, file: File): Uri {

            val authority = "${context.packageName}.file_provider"

            return getUriForFile(context, authority, file)
        }
    }

}
