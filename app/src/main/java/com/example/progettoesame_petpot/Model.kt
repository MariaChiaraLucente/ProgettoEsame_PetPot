package com.example.progettoesame_petpot.model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import java.util.Date

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

data class Meal(
    val id: String? = null,
    val description: String = "",
    val timeFix: String = "",
    val dateStart: Date? = null, // Uses Date
    val dateEnd: Date? = null,   // Uses Date
    val quantity: Float = 0f,
    val timestamp: Long = System.currentTimeMillis()
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
        var currentUser: User? = null;
        var meals: Array<Meal>? = null;
    }

    private val db: DatabaseReference = Firebase.database.reference

    fun getCurrentUser(): User? {
        return currentUser
    }

    fun getMeals(): Array<Meal>? {
        return meals
    }

    fun clear() {
        currentUser = null
        meals = null
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
                                return
                            }
                        }
                        Log.e("Firebase", "Password errata!")
                        onFailure("Wrong Password!")
                    } else {
                        Log.e("Firebase", "User not found!")
                        onFailure("User not found!")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Errore nel database", error.toException())
                    onFailure("Connection Error!")
                }
            })
    }

    // ✅ REGISTRAZIONE UTENTE
    fun registerUser(user: User, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        db.child("users").orderByChild("username").equalTo(user.username)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.e("Firebase", "Already existing User!")
                        onFailure("Already existing User!")
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
                                Log.e("Firebase", "Registration Error", it)
                                onFailure("Registration Error")
                            }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Errore nel database", error.toException())
                    onFailure("Connection Error")
                }
            })
    }

    fun getDogProfile(userId: String, onSuccess: (User) -> Unit, onFailure: (String) -> Unit) {
        db.child("users").child(userId)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(User::class.java)?.let { profile ->
                        onSuccess(profile)
                    } ?: onFailure("No profile found")
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Errore nel recupero dati", error.toException())
                    onFailure("Connection Error")
                }
            })
    }

    // ✅ Aggiorna il profilo nel database
    fun updateDogProfile(userId: String, profile: User, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        db.child("users").child(userId).setValue(profile)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure("An error occurred during the saving") }
    }

    fun saveMeal(feed: Meal, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val ref = db.child("meals/${currentUser?.userId}").push()
        val mealWithId = feed.copy(id = ref.key)

        ref.setValue(mealWithId)
            .addOnSuccessListener {
                Log.d("Firebase", "Pasto salvato correttamente!")
                onSuccess()
            }
            .addOnFailureListener { error ->
                Log.e("Firebase", "Errore nel salvataggio del pasto", error)
                onFailure("Errore nel salvataggio del pasto")
            }
    }

    fun getRecentFeeds(callback: (List<Meal>) -> Unit) {
        db.child("meals/${currentUser?.userId}").get().addOnSuccessListener { snapshot ->
            val feeds = snapshot.children.mapNotNull { it.getValue(Meal::class.java) }
            callback(feeds)
        }.addOnFailureListener {
            Log.e("Firebase", "Errore nel recupero dei feed", it)
        }
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
