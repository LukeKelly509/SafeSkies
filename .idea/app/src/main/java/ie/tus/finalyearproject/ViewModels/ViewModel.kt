package ie.tus.finalyearproject.ViewModels

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.os.Looper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.Timer
import java.util.TimerTask

class ViewModel {

    fun staySignedIn(navController: NavController){
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null){
            navController.navigate("homePage")
        } else {
            navController.navigate("login")
        }
    }

    //Making sure the Login email is a valid email
    fun validLoginEmail(email: String): Boolean {
        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        return email.isNotEmpty() && email.matches(emailRegex)
    }

    //Making sure the Login password is a valid password
    fun validLoginPassword(password: String): Boolean {
        val loginPasswordRegex =
            Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\\\$!%*?&\\-])[A-Za-z\\d@\\\$!%*?&\\-]{8,}$")
        //password requires th following
        // 8 characters minimum
        // 1 digit minimum
        // 1 special character minimum
        // 1 letter minimum
        return loginPasswordRegex.matches(password)
    }

    //Making sure that the Login attempt is valid / makes sure both login email & password are ok
    fun validLoginAttempt(email: String, password: String): Boolean {
        return validLoginEmail(email) && validLoginPassword(password)
    }

    //Allows user to sign in with a created email address via Firebase
    fun signInWithFirebase(email: String, password: String, navController: NavController) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate("homePage")
                } else {
                }
            }
    }

    //Making sure the Sign Up email is valid
    fun validSignUpEmail(email: String): Boolean {
        val emailRegex = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
        return email.isNotEmpty() && email.matches(emailRegex)
    }

    //Making sure the Sign Up password is valid
    fun validSignUpPassword(password: String): Boolean {
        val signUpPasswordRegex =
            Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\\\$!%*?&\\-])[A-Za-z\\d@\\\$!%*?&\\-]{8,}$")
        //password requires th following
        // 8 characters minimum
        // 1 digit minimum
        // 1 special character minimum
        // 1 letter minimum
        return signUpPasswordRegex.matches(password)
    }

    //Making sure the Sign Up is valid / Sign Up email and password are ok
    fun validSignUp(email: String, password: String): Boolean {
        return validSignUpEmail(email) && validSignUpPassword(password)
    }

    //Allowing user to create an account with Firebase
    fun signUpWithFirebase(email: String, password: String, navController: NavController) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    navController.navigate("login")
                } else {
                }

            }
    }

    @Composable
    fun googleMapsDisplaying() {
        val context = LocalContext.current
        val cameraPos = rememberCameraPositionState {
            position = CameraPosition(LatLng(0.0, 0.0), 17f, 0f, 0f) // Default location
        }

        val currentLocation = remember { mutableStateOf(LatLng(0.0, 0.0)) }
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                startLocationUpdates(context, currentLocation, cameraPos)
            } else {
            }
        }

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPos,
            properties = MapProperties(
                isMyLocationEnabled = true,
                mapType = MapType.NORMAL
            )
        ) {
            Marker(
                state = MarkerState(position = currentLocation.value),
                title = "Your Location",
                snippet = "Current location"
            )
        }

        LaunchedEffect(Unit) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun startLocationUpdates(
        context: Context,
        currentLocation: MutableState<LatLng>,
        cameraPos: CameraPositionState
    ) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    location?.let {
                        val newLatLng = LatLng(it.latitude, it.longitude)
                        currentLocation.value = newLatLng

                        // Update camera position on the main thread
                        (context as? Activity)?.runOnUiThread {
                            cameraPos.move(CameraUpdateFactory.newLatLngZoom(newLatLng, 17f))
                        }
                    }
                }
            }
        }, 0, 10000)
    }
}