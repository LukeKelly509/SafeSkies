package ie.tus.finalyearproject

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import ie.tus.finalyearproject.Bars.MyBottomBar
import ie.tus.finalyearproject.Bars.MyTopBar
import ie.tus.finalyearproject.ViewModels.ViewModel

@Composable
fun googleMapsPage(navController: NavController){
    ViewModel().googleMapsDisplaying()

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
        MyBottomBar(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        )
    }
}