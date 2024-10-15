    package com.example.yoga_app.component

    import android.content.Context
    import android.content.SharedPreferences
    import androidx.compose.foundation.layout.padding
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.automirrored.filled.ArrowBack
    import androidx.compose.material.icons.filled.Face
    import androidx.compose.material.icons.filled.Logout
    import androidx.compose.material.icons.filled.Output
    import androidx.compose.material.icons.filled.Search
    import androidx.compose.material3.Button
    import androidx.compose.material3.CenterAlignedTopAppBar
    import androidx.compose.material3.ExperimentalMaterial3Api
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.MaterialTheme
    import androidx.compose.material3.Text
    import androidx.compose.material3.TopAppBarDefaults
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.text.style.TextOverflow
    import androidx.compose.ui.unit.dp
    import androidx.navigation.NavController
    import com.example.yoga_app.Routes

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Header(navController: NavController, context: Context, title: String) {
        var showLogoutButton by remember { mutableStateOf(false) }

        CenterAlignedTopAppBar(
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
            title = {
                Text(
                    title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = { showLogoutButton = !showLogoutButton }) {
                    Icon(
                        imageVector = Icons.Filled.Face,
                        contentDescription = "Menu"
                    )
                }
                // Show the logout icon when the button is open
                if (showLogoutButton) {
                    IconButton(onClick = {
                        logout(context)
                        navController.navigate(Routes.Login.route)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Output,
                            contentDescription = "Logout"
                        )
                    }
                }
            }
        )
    }
    fun logout(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }