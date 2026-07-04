package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.Repository
import com.example.ui.CultureVerseApp
import com.example.ui.CultureVerseViewModel
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize local Room Database instances
        Repository.initialize(applicationContext)
        
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                val vm: CultureVerseViewModel = viewModel()
                CultureVerseApp(viewModel = vm)
            }
        }
    }
}
