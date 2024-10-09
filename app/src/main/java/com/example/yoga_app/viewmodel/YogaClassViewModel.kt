package com.example.yoga_app.viewmodel

import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.yoga_app.dao.YogaClassDao
import com.example.yoga_app.database.YogaClass
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class YogaClassViewModel(private val yogaClassDao: YogaClassDao): ViewModel() {
    val allYogaClasses: Flow<List<YogaClass>> = yogaClassDao.getAllYogaClasses()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YogaClassLandingPage(yogaClassViewModel: YogaClassViewModel = viewModel()) {
    val yogaClasses by yogaClassViewModel.allYogaClasses.collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yoga Classes") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (yogaClasses.isEmpty()) {
                Text("No yoga classes available.")
            } else {
                LazyColumn {
                    items(yogaClasses) { yogaClass ->
                        YogaClassCard(yogaClass)
                    }
                }
            }
        }
    }
}

@Composable
fun YogaClassCard(yogaClass: YogaClass) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = yogaClass.className, style = MaterialTheme.typography.bodyMedium)
            Text(text = "Type: ${yogaClass.yogaClassType}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Day: ${yogaClass.dayOfWeek}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Time: ${yogaClass.time}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Capacity: ${yogaClass.capacity}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Price: $${yogaClass.pricePerClass}", style = MaterialTheme.typography.bodyMedium)
            yogaClass.description?.let {
                Text(text = "Description: $it", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}