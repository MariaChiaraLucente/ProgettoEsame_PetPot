package com.example.progettoesame_petpot.model

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
    val id: String? = null,
    val timeFix: String = "",
    val dateStart: Date? = null, // Uses Date
    val dateEnd: Date? = null,   // Uses Date
    val quantity: Float = 0f,
    val timestamp: Long = System.currentTimeMillis(),
    var status: String = "Programmed"
) {
    // Costruttore senza argomenti richiesto da Firebase
    constructor() : this(null, "", null, null, 0f, System.currentTimeMillis(),  "Programmed")
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
                                // da togliere appena  riabilitiamo il passaggio dell utente corrente
                                onSuccess()
//                                currentUser?.userId?.let { userId ->
////                                    setCurrentUserInDatabase(userId, onSuccess, onFailure)
////                                }
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

    fun setCurrentUserInDatabase(userId: String?, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        val currentUserRef = db.child("currentUser")
        currentUserRef.setValue(userId)
            .addOnSuccessListener {
                Log.d("Firebase", "CurrentUser aggiornato correttamente!")
                onSuccess()
            }
            .addOnFailureListener { error ->
                Log.e("Firebase", "Errore nell'aggiornamento di CurrentUser", error)
                onFailure("Errore nell'aggiornamento di CurrentUser")
            }
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
        // settare feedNow/comando a start


        ref.setValue(mealWithId)
            .addOnSuccessListener {
                Log.d("Firebase", "Pasto salvato correttamente!")
               // onSuccess()
////////////////gestione di feedNow per il pasto
                val feedNowUpdate = mapOf(
                    "comando" to "start",
                    "quantità" to feed.quantity
                )

                db.child("feedNow").setValue(feedNowUpdate)
                    .addOnSuccessListener {
                        Log.d("Firebase", "feedNow aggiornato correttamente!")
                        onSuccess()
                    }
                    .addOnFailureListener { error ->
                        Log.e("Firebase", "Errore nell'aggiornamento di feedNow", error)
                        onFailure("Errore nell'aggiornamento di feedNow")
                    }

            }
/////////////////////////////////////////////////////////
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


    fun deleteFeed(feed: Feed) {
        val userId = currentUser?.userId ?: return
        val ref = db.child("feeds/$userId/${feed.id}")

        ref.removeValue().addOnSuccessListener {
            Log.d("CalendarViewModel", "Feed eliminato con successo")
        }.addOnFailureListener { e ->
            Log.e("CalendarViewModel", "Errore nell'eliminazione del feed: ${e.message}")
        }
    }

    fun getFeeds(callback: (List<Feed>) -> Unit) {
       db.child("feeds/${currentUser?.userId}").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val feedList = mutableListOf<Feed>()
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            for (child in snapshot.children) {
                val id = child.child("id").getValue(String::class.java)
                val timeFix = child.child("timeFix").getValue(String::class.java) ?: ""
                val dateStartStr = child.child("dateStart").getValue(String::class.java) ?: ""
                val dateEndStr = child.child("dateEnd").getValue(String::class.java) ?: ""
                val quantity = child.child("quantity").getValue(Float::class.java) ?: 0f
                val timestamp = child.child("timestamp").getValue(Long::class.java) ?: System.currentTimeMillis()
                val status = child.child("status").getValue(String::class.java) ?: "Programmed" // Leggi lo stato

                val dateStart = if (dateStartStr.isNotEmpty()) dateFormat.parse(dateStartStr) else null
                val dateEnd = if (dateEndStr.isNotEmpty()) dateFormat.parse(dateEndStr) else null

                val feed = Feed(
                    id = id,
                    timeFix = timeFix,
                    dateStart = dateStart,
                    dateEnd = dateEnd,
                    quantity = quantity,
                    timestamp = timestamp,
                    status = status // Imposta lo stato
                )
                feedList.add(feed)
            }
            callback(feedList)
        }

            override fun onCancelled(error: DatabaseError) {
                Log.e("PetPotModel", "Errore nel recupero dei feed: ${error.message}")
                callback(emptyList()) // Ritorna una lista vuota in caso di errore
            }
        })
    }


// fare il set user quando faccio il login


    // ✅ SALVATAGGIO FEED NEL DATABASE
    fun saveFeed(feed: Feed) {
        val ref = db.child("feeds/${currentUser?.userId}").push()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        // aggiungo una variabile status che mi dice se il pasto è stato dato o meno
        val feedWithId = feed.copy(id = ref.key, status = "Programmed") // Imposta lo stato iniziale


        val feed = mapOf(
            "id" to ref.key,
            "timeFix" to feed.timeFix,
            "dateStart" to (feed.dateStart?.let { dateFormat.format(it) } ?: ""),
            "dateEnd" to (feed.dateEnd?.let { dateFormat.format(it) } ?: ""),
            "quantity" to feed.quantity,
            "timestamp" to feed.timestamp,
            "status" to feedWithId.status
        )

        ref.setValue(feed)
    }

    //funzioni di gestione dello stato di feed
    fun updateFeedStatus(userId: String, feedId: String, newStatus: String, onSuccess: () -> Unit, onFailure: (String) -> Unit) {
        db.child("feeds/$userId/$feedId/status").setValue(newStatus)
            .addOnSuccessListener {
                Log.d("Firebase", "Stato del feed aggiornato a $newStatus")
                onSuccess()
            }
            .addOnFailureListener { error ->
                Log.e("Firebase", "Errore nell'aggiornamento dello stato del feed", error)
                onFailure("Errore nell'aggiornamento dello stato del feed")
            }
    }

    //nel caso si volesse gestire la comparsa o meno delle feed nel calendario uso questa che prende le feed quando sono programmed
//fun getProgrammedFeeds(callback: (List<Feed>) -> Unit) {
//    val userId = currentUser?.userId ?: return
//    db.child("feeds/$userId").orderByChild("status").equalTo("Programmed")
//        .addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val feedList = mutableListOf<Feed>()
//                for (child in snapshot.children) {
//                    child.getValue(Feed::class.java)?.let { feed ->
//                        feedList.add(feed)
//                    }
//                }
//                callback(feedList)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("Firebase", "Errore nel recupero dei feed programmati", error.toException())
//                callback(emptyList())
//            }
//        })
//}
        // nel caso in cui si volesse implementare una notifica in cui si vuole dire che la feed è stata presa in carico tipo
    fun getInProgressFeeds(callback: (List<Feed>) -> Unit) {
        val userId = currentUser?.userId ?: return
        db.child("feeds/$userId").orderByChild("status").equalTo("InProgress")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val feedList = mutableListOf<Feed>()
                    for (child in snapshot.children) {
                        child.getValue(Feed::class.java)?.let { feed ->
                            feedList.add(feed)
                        }
                    }
                    callback(feedList)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Errore nel recupero dei feed in progress", error.toException())
                    callback(emptyList())
                }
            })
    }

// feed completata
fun getCompletedFeeds(callback: (List<Feed>) -> Unit) {
    val userId = currentUser?.userId ?: return
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    db.child("feeds/$userId").orderByChild("status").equalTo("Completed")
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val feedList = mutableListOf<Feed>()
                for (child in snapshot.children) {
                    val id = child.child("id").getValue(String::class.java)
                    val timeFix = child.child("timeFix").getValue(String::class.java) ?: ""
                    val dateStartStr = child.child("dateStart").getValue(String::class.java) ?: ""
                    val dateEndStr = child.child("dateEnd").getValue(String::class.java) ?: ""
                    val quantity = child.child("quantity").getValue(Float::class.java) ?: 0f
                    val timestamp = child.child("timestamp").getValue(Long::class.java) ?: System.currentTimeMillis()
                    val status = child.child("status").getValue(String::class.java) ?: "Completed"

                    val dateStart = try {
                        if (dateStartStr.isNotEmpty()) dateFormat.parse(dateStartStr) else null
                    } catch (e: ParseException) {
                        Log.e("Firebase", "Errore nel parsing della data di inizio: $dateStartStr", e)
                        null
                    }

                    val dateEnd = try {
                        if (dateEndStr.isNotEmpty()) dateFormat.parse(dateEndStr) else null
                    } catch (e: ParseException) {
                        Log.e("Firebase", "Errore nel parsing della data di fine: $dateEndStr", e)
                        null
                    }

                    val feed = Feed(
                        id = id,
                        timeFix = timeFix,
                        dateStart = dateStart,
                        dateEnd = dateEnd,
                        quantity = quantity,
                        timestamp = timestamp,
                        status = status
                    )
                    feedList.add(feed)
                }
                callback(feedList)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Errore nel recupero dei feed completati", error.toException())
                callback(emptyList())
            }
        })
}


}
