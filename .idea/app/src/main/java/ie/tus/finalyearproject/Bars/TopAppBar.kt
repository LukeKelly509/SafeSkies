package ie.tus.finalyearproject.Bars

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(modifier: Modifier, navController: NavController) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(DarkGray),
        title = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 40.dp),
                contentAlignment = Alignment.Center
            ) {
            }
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Outlined.Menu,
                contentDescription = "Hamburger Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(35.dp)
            )
        },
        actions = {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "Account Icon",
                tint = Color.White,
                modifier = Modifier
                    .size(35.dp)
                    .clickable {
                        navController.navigate("accountDetails")
                    }
            )
        },
        modifier = Modifier
            .fillMaxWidth()
    )
}