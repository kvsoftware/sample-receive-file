package com.kvsoftware.receivefile

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

object FileHelper {

    private const val FILE_PREFIX = "picture_"
    private const val FILE_SUFFIX = ".jpg"

    fun createFile(context: Context, contentUri: Uri): File? {
        val inputStream = context.contentResolver.openInputStream(contentUri) ?: return null

        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val outputFile = File.createTempFile(FILE_PREFIX, FILE_SUFFIX)
        val fos = FileOutputStream(outputFile)
        fos.write(buffer)
        fos.close()

        return outputFile
    }
}