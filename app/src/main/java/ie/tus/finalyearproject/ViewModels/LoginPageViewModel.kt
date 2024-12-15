package ie.tus.finalyearproject.ViewModels

import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

class LoginPageViewModel {
    fun validLoginEmail(email: String): Boolean {
        val emailRegex = Regex("K00[0-9]+@student\\.tus\\.ie")
        return email.isNotEmpty() && email.matches(emailRegex)
    }

    fun validLoginPassword(password: String): Boolean {
        return password.isNotEmpty()
    }

    fun validLoginAttempt(email: String, password: String): Boolean {
        return validLoginEmail(email) && validLoginPassword(password)
    }

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
}