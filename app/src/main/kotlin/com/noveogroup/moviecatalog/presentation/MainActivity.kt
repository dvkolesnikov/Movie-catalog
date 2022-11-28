package com.noveogroup.moviecatalog.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.noveogroup.moviecatalog.presentation.navigation.MainNavigationHolder
import com.noveogroup.moviecatalog.presentation.theme.AppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                MainNavigationHolder()
            }
        }
    }
}
