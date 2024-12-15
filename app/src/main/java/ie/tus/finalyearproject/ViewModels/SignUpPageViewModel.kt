package ie.tus.finalyearproject.ViewModels

import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

class SignUpPageViewModel {
    fun validSignUpEmail(email: String): Boolean {
        val emailRegex = Regex("K00[0-9]+@student\\.tus\\.ie")
        return email.isNotEmpty() && email.matches(emailRegex)
    }

    fun validSignUpPassword(password: String): Boolean {
        return password.isNotEmpty() && password.length > 5
    }

    fun validSignUp(email: String, password: String): Boolean {
        return validSignUpEmail(email) && validSignUpPassword(password)
    }

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
}