package com.exla.test

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * A utility class to load model files from assets
 */
class LocalModelLoader(private val context: Context) {
    companion object {
        private const val TAG = "LocalModelLoader"
        private const val DUMMY_MODEL_NAME = "dummy_model.gguf"
    }
    
    private val modelDir: File = File(context.filesDir, "models")
    private val modelFile: File = File(modelDir, DUMMY_MODEL_NAME)
    
    init {
        if (!modelDir.exists()) {
            modelDir.mkdirs()
        }
    }
    
    /**
     * Check if the model file exists locally
     */
    fun isModelAvailable(): Boolean {
        return modelFile.exists() && modelFile.length() > 0
    }
    
    /**
     * Get the path to the model file
     */
    fun getModelPath(): String {
        return modelFile.absolutePath
    }
    
    /**
     * Copy the dummy model from assets to internal storage
     * @return Result with the path to the model file or an error
     */
    fun copyModelFromAssets(): Result<String> {
        return try {
            // If model already exists, return its path
            if (isModelAvailable()) {
                Log.i(TAG, "Model already copied: ${modelFile.absolutePath}")
                return Result.success(modelFile.absolutePath)
            }
            
            Log.i(TAG, "Copying model from assets")
            
            // Open the asset and copy to internal storage
            context.assets.open(DUMMY_MODEL_NAME).use { inputStream ->
                FileOutputStream(modelFile).use { outputStream ->
                    val buffer = ByteArray(1024 * 1024) // 1MB buffer
                    var read: Int
                    
                    while (inputStream.read(buffer).also { read = it } != -1) {
                        outputStream.write(buffer, 0, read)
                    }
                    
                    outputStream.flush()
                }
            }
            
            // Verify the file was copied successfully
            if (!modelFile.exists() || modelFile.length() == 0L) {
                throw IOException("Failed to copy model file")
            }
            
            Log.i(TAG, "Model copied successfully: ${modelFile.absolutePath}")
            Result.success(modelFile.absolutePath)
            
        } catch (e: Exception) {
            Log.e(TAG, "Error copying model: ${e.message}", e)
            Result.failure(e)
        }
    }
} 