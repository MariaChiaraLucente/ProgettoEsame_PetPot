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
    var userId: String = "",
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

data class Feed(
    var id: String? = null,
    val timeFix: String = "",
    val dateStart: String= "", // Uses Date
    val dateEnd: String= "",   // Uses Date
    val quantity: Float = 0f
){
    // Costruttore senza argomenti richiesto da Firebase
    constructor() : this(null, "", "", "", 0f)
}

class PetPotModel {

    companion object {
        var  currentUser: User? = null;
    }

    private val db: DatabaseReference = Firebase.database.reference

    fun getCurrentUser(): User? {
        return currentUser
    }

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
                                currentUser = userSnapshot.getValue(User::class.java);
                                onSuccess()
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
                        val ref = db.child("users").push();
                        user.userId = ref.key.toString();
                        ref.setValue(user)
                            .addOnSuccessListener {
                                Log.d("Aiuto", db.child("users").key.toString() + "AHHHHHHHHHHH")
                                currentUser = user;
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

    fun getDogProfile(userId: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        db.child("users").child(userId).child("dogProfile")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(User::class.java)?.let { profile ->
                        onSuccess(profile)
                    } ?: onFailure("Nessun profilo trovato")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Errore nel recupero dati", error.toException())
                    onFailure("Errore di connessione!")
                }
            })
    }

    // ✅ Aggiorna il profilo nel database
    fun updateDogProfile(userId: String, profile: User, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        db.child("users").child(userId).child("dogProfile").setValue(profile)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure("Errore nel salvataggio!") }
    }

    fun getFeeds(callback: (List<Feed>) -> Unit) {
        db.child("feeds").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val feedList = mutableListOf<Feed>()
                for (child in snapshot.children) {
                    val feed = child.getValue(Feed::class.java)
                    feed?.let { feedList.add(it) }
                }
                callback(feedList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PetPotModel", "Errore nel recupero dei feed: ${error.message}")
                callback(emptyList()) // Ritorna una lista vuota in caso di errore
            }
        })
    }



    // ✅ SALVATAGGIO FEED NEL DATABASE
    fun saveFeed(feed: Feed) {
        val ref = db.child("feeds").push()
        feed.id = ref.key
        ref.setValue(feed)
    }
}
