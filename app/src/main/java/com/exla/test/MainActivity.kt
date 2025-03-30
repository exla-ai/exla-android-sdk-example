package com.exla.test

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.exla.aisdk.ExlaAiSdk

class MainActivity : AppCompatActivity() {
    private lateinit var statusTextView: TextView
    private lateinit var outputTextView: TextView
    private lateinit var promptEditText: EditText
    private lateinit var loadModelButton: Button
    private lateinit var generateButton: Button
    private lateinit var progressBar: ProgressBar
    
    private lateinit var sdk: ExlaAiSdk
    
    companion object {
        private const val TAG = "ExlaSDKTest"
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // Initialize views
        statusTextView = findViewById(R.id.statusTextView)
        outputTextView = findViewById(R.id.outputTextView)
        promptEditText = findViewById(R.id.promptEditText)
        loadModelButton = findViewById(R.id.loadModelButton)
        generateButton = findViewById(R.id.generateButton)
        progressBar = findViewById(R.id.progressBar)
        
        // Initially hide the progress bar
        progressBar.visibility = View.GONE
        
        // Check network connectivity
        val isConnected = isNetworkAvailable()
        Log.d(TAG, "Network connectivity: $isConnected")
        
        try {
            // Initialize SDK to verify JitPack integration
            sdk = ExlaAiSdk.getInstance(applicationContext)
            
            // Set initial status
            val networkStatus = if (isConnected) "Connected" else "Not Connected"
            statusTextView.text = "SDK Version: ${sdk.version}\nNetwork: $networkStatus"
            outputTextView.text = "SDK loaded from JitPack successfully.\n\n" +
                "Press 'Download Model' to test the model download functionality."
            
            Toast.makeText(this, "JitPack integration successful!", Toast.LENGTH_SHORT).show()
            
        } catch (e: Exception) {
            Log.e(TAG, "Error initializing SDK: ${e.message}", e)
            statusTextView.text = "SDK Initialization Error"
            outputTextView.text = "Error: ${e.message}"
            
            // Disable buttons if initialization failed
            loadModelButton.isEnabled = false
            generateButton.isEnabled = false
        }
        
        // Setup button click listeners
        loadModelButton.setOnClickListener {
            downloadModel()
        }
        
        generateButton.setOnClickListener {
            generateText()
        }
    }
    
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
            
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            @Suppress("DEPRECATION")
            return networkInfo != null && networkInfo.isConnected
        }
    }
    
    private fun downloadModel() {
        // Disable the button and show progress
        loadModelButton.isEnabled = false
        progressBar.visibility = View.VISIBLE
        statusTextView.text = "Starting model download..."
        
        sdk.initialize(
            progressCallback = { progress ->
                runOnUiThread {
                    progressBar.progress = progress
                    statusTextView.text = "Downloading model: $progress%"
                    Log.d(TAG, "Downloading model: $progress%")
                }
            },
            completionCallback = { success ->
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    loadModelButton.isEnabled = true
                    
                    if (success) {
                        statusTextView.text = "Model loaded successfully! SDK Ready."
                        outputTextView.text = "Model is ready for text generation.\n\n" +
                            "Enter a prompt below and click 'Generate' to test."
                        
                        Toast.makeText(this, "Model download successful!", Toast.LENGTH_LONG).show()
                    } else {
                        statusTextView.text = "Failed to download model."
                        outputTextView.text = "Model download failed. Please check your network connection and try again."
                        
                        Toast.makeText(this, "Model download failed", Toast.LENGTH_LONG).show()
                    }
                }
            }
        )
    }
    
    private fun generateText() {
        val prompt = promptEditText.text.toString().trim()
        if (prompt.isEmpty()) {
            outputTextView.text = "Please enter a prompt first."
            return
        }
        
        // Verify SDK is ready
        if (!sdk.isReady()) {
            outputTextView.text = "Model is not ready. Please download the model first."
            return
        }
        
        // Disable button while generating
        generateButton.isEnabled = false
        outputTextView.text = "Generating response..."
        
        // Actually generate text using the SDK
        sdk.askAI(prompt) { response ->
            runOnUiThread {
                outputTextView.text = response
                generateButton.isEnabled = true
            }
        }
    }
} 