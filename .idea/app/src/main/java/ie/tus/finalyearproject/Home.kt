package ie.tus.finalyearproject

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ie.tus.finalyearproject.Bars.MyBottomBar
import ie.tus.finalyearproject.Bars.MyTopBar

@Composable
fun homePage(navController: NavController){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        MyTopBar(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Home Page Content",
            modifier = Modifier.align(Alignment.Center)
        )

        MyBottomBar(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}