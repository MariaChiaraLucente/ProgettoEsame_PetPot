package com.example.progettoesame_petpot.model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError

data class User(
    val username: String = "",
    val password: String = "",
    val size: String = "",
    val age: String = "",
    val breed: String = "",
    var favoriteFood: String = "",
    var allergies: String = "",
    var others: String = "",
    var vetName: String = "",
    var vetPhone: String = "",
)

class PetPotModel {
    private val db: DatabaseReference = Firebase.database.reference

    // ✅ LOGIN UTENTE
    fun loginUser(username: String, password: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        db.child("users").orderByChild("username").equalTo(username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (userSnapshot in snapshot.children) {
                            val storedPassword = userSnapshot.child("password").getValue(String::class.java)
                            if (storedPassword == password) {
                                Log.d("Firebase", "Login riuscito!")
                                onSuccess()
                                return
                            }
                        }
                        Log.e("Firebase", "Password errata!")
                        onFailure("Password errata!")
                    } else {
                        Log.e("Firebase", "Utente non trovato!")
                        onFailure("Utente non trovato!")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Errore nel database", error.toException())
                    onFailure("Errore di connessione!")
                }
            })
    }

    // ✅ REGISTRAZIONE UTENTE
    fun registerUser(user: User, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        db.child("users").orderByChild("username").equalTo(user.username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.e("Firebase", "Username già esistente!")
                        onFailure("Username già esistente!")
                    } else {
                        db.child("users").push().setValue(user)
                            .addOnSuccessListener {
                                Log.d("Firebase", "Registrazione completata!")
                                onSuccess()
                            }
                            .addOnFailureListener {
                                Log.e("Firebase", "Errore nella registrazione", it)
                                onFailure("Errore durante la registrazione!")
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Errore nel database", error.toException())
                    onFailure("Errore di connessione!")
                }
            })
    }
}
