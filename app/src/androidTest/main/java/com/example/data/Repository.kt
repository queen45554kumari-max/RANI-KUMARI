package com.example.data

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import org.json.JSONObject

object Repository {
    private var database: AppDatabase? = null

    // Get DAOs
    private val userDao: UserDao?
        get() = database?.userDao()

    private val journalDao: JournalDao?
        get() = database?.journalDao()

    private val itineraryDao: ItineraryDao?
        get() = database?.itineraryDao()

    /**
     * Initializes the Room database. Call this from MainActivity.
     */
    fun initialize(context: Context) {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "cultureverse_db"
            )
            .fallbackToDestructiveMigration()
            .build()
        }
    }

    // --- REPOSITORY FLOWS & ACTIONS ---

    val allJournals: Flow<List<JournalEntry>>
        get() = journalDao?.getAllJournals() ?: flowOf(emptyList())

    val allItineraries: Flow<List<Itinerary>>
        get() = itineraryDao?.getAllItineraries() ?: flowOf(emptyList())

    suspend fun insertJournal(entry: JournalEntry) = withContext(Dispatchers.IO) {
        journalDao?.insertJournal(entry)
    }

    suspend fun deleteJournal(id: Int) = withContext(Dispatchers.IO) {
        journalDao?.deleteJournalById(id)
    }

    suspend fun insertItinerary(itinerary: Itinerary) = withContext(Dispatchers.IO) {
        itineraryDao?.insertItinerary(itinerary)
    }

    suspend fun deleteItinerary(id: Int) = withContext(Dispatchers.IO) {
        itineraryDao?.deleteItineraryById(id)
    }

    // --- GEMINI ACTIONS ---

    suspend fun askGemini(prompt: String, history: List<Pair<String, Boolean>>): String {
        return GeminiService.getChatResponse(prompt, history)
    }

    /**
     * Helper to write a new journal, generate its cultural reflection, and save it.
     */
    suspend fun saveJournalWithAI(
        title: String,
        content: String,
        location: String,
        category: String,
        imageUrl: String? = null
    ) = withContext(Dispatchers.IO) {
        // First insert journal with no reflection or loading
        val tempEntry = JournalEntry(
            title = title,
            content = content,
            location = location,
            category = category,
            imageUrl = imageUrl,
            culturalReflection = "Reflecting on $location's customs..."
        )
        
        // Save to Room so user sees it instantly
        insertJournal(tempEntry)

        // Generate the reflection asynchronously
        val reflection = GeminiService.getJournalReflection(title, content, location)
        
        // Find if we have any entries with the same timestamp or insert the real one
        val finalEntry = tempEntry.copy(culturalReflection = reflection)
        insertJournal(finalEntry)
    }

    /**
     * Ask Gemini to generate a customized cultural travel plan and save it in Room
     */
    suspend fun generateAndSaveItinerary(
        destination: String,
        days: Int,
        budget: String,
        interests: String,
        startDate: String
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            // Build budget-aware custom focus
            val focusWithBudget = "$interests (Budget: $budget)"
            val jsonString = GeminiService.getItinerary(destination, days, focusWithBudget)
            
            val uniqueTripId = java.util.UUID.randomUUID().toString()
            val itinerary = Itinerary(
                tripId = uniqueTripId,
                destination = destination,
                days = days,
                budget = budget,
                interests = interests,
                generatedItinerary = jsonString,
                timestamp = System.currentTimeMillis(),
                
                // Compatibility mapping:
                durationDays = days,
                startDate = startDate,
                culturalFocus = interests,
                activitiesJson = jsonString,
                createdAt = System.currentTimeMillis()
            )
            insertItinerary(itinerary)
            
            // Sync with active user profile
            updateSavedTrips("local_user_1", uniqueTripId)
            true
        } catch (e: Exception) {
            false
        }
    }

    // --- USER PROFILE OPERATIONS ---

    fun getUserProfile(userId: String): Flow<UserProfile?> {
        return userDao?.getUserProfile(userId) ?: kotlinx.coroutines.flow.flowOf(null)
    }

    suspend fun saveUserProfile(profile: UserProfile) = withContext(Dispatchers.IO) {
        userDao?.insertUserProfile(profile)
    }

    suspend fun deleteUserProfile(userId: String) = withContext(Dispatchers.IO) {
        userDao?.deleteUserProfile(userId)
    }

    suspend fun addFavoritePlace(userId: String, placeName: String) = withContext(Dispatchers.IO) {
        val user = userDao?.getUserProfileOnce(userId) ?: return@withContext
        val updatedPlaces = addToJsonArray(user.favouritePlacesJson, placeName)
        userDao?.insertUserProfile(user.copy(favouritePlacesJson = updatedPlaces))
    }

    suspend fun removeFavoritePlace(userId: String, placeName: String) = withContext(Dispatchers.IO) {
        val user = userDao?.getUserProfileOnce(userId) ?: return@withContext
        val updatedPlaces = removeFromJsonArray(user.favouritePlacesJson, placeName)
        userDao?.insertUserProfile(user.copy(favouritePlacesJson = updatedPlaces))
    }

    suspend fun addSearchHistory(userId: String, query: String) = withContext(Dispatchers.IO) {
        val user = userDao?.getUserProfileOnce(userId) ?: return@withContext
        val updatedHistory = addToJsonArray(user.historyJson, query)
        userDao?.insertUserProfile(user.copy(historyJson = updatedHistory))
    }

    suspend fun removeSearchHistory(userId: String, query: String) = withContext(Dispatchers.IO) {
        val user = userDao?.getUserProfileOnce(userId) ?: return@withContext
        val updatedHistory = removeFromJsonArray(user.historyJson, query)
        userDao?.insertUserProfile(user.copy(historyJson = updatedHistory))
    }

    suspend fun clearSearchHistory(userId: String) = withContext(Dispatchers.IO) {
        val user = userDao?.getUserProfileOnce(userId) ?: return@withContext
        userDao?.insertUserProfile(user.copy(historyJson = "[]"))
    }

    suspend fun updateSavedTrips(userId: String, tripId: String) = withContext(Dispatchers.IO) {
        val user = userDao?.getUserProfileOnce(userId) ?: return@withContext
        val updatedTrips = addToJsonArray(user.savedTripsJson, tripId)
        userDao?.insertUserProfile(user.copy(savedTripsJson = updatedTrips))
    }

    // --- JSON ARRAY UTILS ---

    private fun addToJsonArray(jsonStr: String, element: String): String {
        return try {
            val array = org.json.JSONArray(jsonStr)
            var exists = false
            for (i in 0 until array.length()) {
                if (array.getString(i) == element) {
                    exists = true
                    break
                }
            }
            if (!exists) {
                array.put(element)
            }
            array.toString()
        } catch (e: Exception) {
            val array = org.json.JSONArray()
            array.put(element)
            array.toString()
        }
    }

    private fun removeFromJsonArray(jsonStr: String, element: String): String {
        return try {
            val array = org.json.JSONArray(jsonStr)
            val newArray = org.json.JSONArray()
            for (i in 0 until array.length()) {
                val item = array.getString(i)
                if (item != element) {
                    newArray.put(item)
                }
            }
            newArray.toString()
        } catch (e: Exception) {
            "[]"
        }
    }
}
