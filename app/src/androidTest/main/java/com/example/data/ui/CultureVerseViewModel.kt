package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.GeminiService
import com.example.data.Itinerary
import com.example.data.JournalEntry
import com.example.data.Repository
import com.example.data.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    val timestamp: Long = System.currentTimeMillis()
)

class CultureVerseViewModel : ViewModel() {

    private val currentUserId = "local_user_1"

    // --- Database-Backed Profile State ---
    val userProfile: StateFlow<UserProfile?> = Repository.getUserProfile(currentUserId)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )

    val username: StateFlow<String> = userProfile
        .map { it?.name ?: "Cultural Explorer" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "Cultural Explorer")

    val userEmail: StateFlow<String> = userProfile
        .map { it?.email ?: "" }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    val avatarIndex: StateFlow<Int> = userProfile
        .map { 
            val photoStr = it?.photo ?: "0"
            photoStr.toIntOrNull() ?: 0
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val favoriteRegion: StateFlow<String> = userProfile
        .map { 
            // Return standard region
            "East Asia"
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "East Asia")

    val isOnboarded: StateFlow<Boolean> = userProfile
        .map { it != null && it.name.isNotEmpty() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    // --- Dynamic Lists based on Rich Schema requirements ---
    val savedTrips: StateFlow<List<String>> = userProfile
        .map { parseJsonArray(it?.savedTripsJson) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favouritePlaces: StateFlow<List<String>> = userProfile
        .map { parseJsonArray(it?.favouritePlacesJson) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val history: StateFlow<List<String>> = userProfile
        .map { parseJsonArray(it?.historyJson) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // --- Local Room Data ---
    val itineraries: StateFlow<List<Itinerary>> = Repository.allItineraries
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val journals: StateFlow<List<JournalEntry>> = Repository.allJournals
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    // --- Chat Session ---
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                text = "Konnichiwa! Bonjour! Namaste! I am your CultureVerse AI travel guide. Ask me anything about cultural etiquettes, native greetings, local cuisines, or traditional architecture around the world!",
                isUser = false
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isChatLoading = MutableStateFlow(false)
    val isChatLoading: StateFlow<Boolean> = _isChatLoading.asStateFlow()

    // --- Planner Loading ---
    private val _isGeneratingItinerary = MutableStateFlow(false)
    val isGeneratingItinerary: StateFlow<Boolean> = _isGeneratingItinerary.asStateFlow()

    // --- Journal Loading ---
    private val _isSavingJournal = MutableStateFlow(false)
    val isSavingJournal: StateFlow<Boolean> = _isSavingJournal.asStateFlow()

    // --- API Status check ---
    val isApiKeyValid: Boolean
        get() = GeminiService.isApiKeyAvailable()

    // --- ACTIONS ---

    fun completeOnboarding(name: String, email: String, avatarIdx: Int, region: String) {
        viewModelScope.launch {
            val profile = UserProfile(
                userId = currentUserId,
                name = if (name.trim().isEmpty()) "Cultural Explorer" else name,
                email = email,
                photo = avatarIdx.toString(),
                savedTripsJson = "[]",
                favouritePlacesJson = "[]",
                historyJson = "[]"
            )
            Repository.saveUserProfile(profile)
        }
    }

    fun updateProfile(name: String, email: String, avatarIdx: Int, region: String) {
        viewModelScope.launch {
            val current = userProfile.value ?: UserProfile(userId = currentUserId, name = "", email = "", photo = "0")
            val updated = current.copy(
                name = name,
                email = email,
                photo = avatarIdx.toString()
            )
            Repository.saveUserProfile(updated)
        }
    }

    fun logout() {
        viewModelScope.launch {
            Repository.deleteUserProfile(currentUserId)
        }
    }

    /**
     * Favorites Toggler (saved in Room UserProfile)
     */
    fun toggleFavoritePlace(placeName: String) {
        viewModelScope.launch {
            val currentFavorites = favouritePlaces.value
            if (currentFavorites.contains(placeName)) {
                Repository.removeFavoritePlace(currentUserId, placeName)
            } else {
                Repository.addFavoritePlace(currentUserId, placeName)
            }
        }
    }

    /**
     * Add to activity search history (saved in Room UserProfile)
     */
     fun addSearchQuery(query: String) {
         if (query.trim().isEmpty()) return
         viewModelScope.launch {
             Repository.addSearchHistory(currentUserId, query)
         }
     }

     fun deleteSearchHistory(query: String) {
         viewModelScope.launch {
             Repository.removeSearchHistory(currentUserId, query)
         }
     }

     fun clearSearchHistory() {
         viewModelScope.launch {
             Repository.clearSearchHistory(currentUserId)
         }
     }

    /**
     * Send message in Chatbot
     */
    fun sendMessage(text: String) {
        if (text.trim().isEmpty()) return
        
        val userMsg = ChatMessage(text = text, isUser = true)
        val updatedMessages = _chatMessages.value + userMsg
        _chatMessages.value = updatedMessages
        _isChatLoading.value = true

        // Log this chat text to user history!
        addSearchQuery("Chatted about: $text")

        viewModelScope.launch {
            // Build simple history list for API context
            val historyList = updatedMessages.dropLast(1).map { Pair(it.text, it.isUser) }
            val aiResponseText = Repository.askGemini(text, historyList)
            
            _chatMessages.value = _chatMessages.value + ChatMessage(text = aiResponseText, isUser = false)
            _isChatLoading.value = false
        }
    }

    /**
     * Generate custom itinerary and save to Room with budget support
     */
    fun generateCulturalItinerary(
        destination: String,
        days: Int,
        budget: String,
        interests: String,
        startDate: String,
        onComplete: (Boolean) -> Unit
    ) {
        if (destination.trim().isEmpty()) {
            onComplete(false)
            return
        }
        _isGeneratingItinerary.value = true

        // Log generation request in Search History!
        addSearchQuery("Planned trip to $destination ($days days, Budget: $budget)")

        viewModelScope.launch {
            val success = Repository.generateAndSaveItinerary(
                destination = destination,
                days = days,
                budget = budget,
                interests = interests,
                startDate = startDate
            )
            _isGeneratingItinerary.value = false
            onComplete(success)
        }
    }

    /**
     * Delete an itinerary
     */
    fun deleteItinerary(id: Int) {
        viewModelScope.launch {
            Repository.deleteItinerary(id)
        }
    }

    /**
     * Save journal entry with AI reflection
     */
    fun addJournalEntry(
        title: String,
        content: String,
        location: String,
        category: String,
        imageUrl: String? = null,
        onComplete: () -> Unit
    ) {
        if (title.trim().isEmpty() || content.trim().isEmpty()) {
            onComplete()
            return
        }
        _isSavingJournal.value = true

        viewModelScope.launch {
            Repository.saveJournalWithAI(title, content, location, category, imageUrl)
            _isSavingJournal.value = false
            onComplete()
        }
    }

    /**
     * Delete a journal entry
     */
    fun deleteJournal(id: Int) {
        viewModelScope.launch {
            Repository.deleteJournal(id)
        }
    }

    // --- INTERACTIVE SEARCH MAP STATE ---
    private val _mapCityName = MutableStateFlow("")
    val mapCityName: StateFlow<String> = _mapCityName.asStateFlow()

    private val _mapCenterLat = MutableStateFlow(26.9124)
    val mapCenterLat: StateFlow<Double> = _mapCenterLat.asStateFlow()

    private val _mapCenterLng = MutableStateFlow(75.7873)
    val mapCenterLng: StateFlow<Double> = _mapCenterLng.asStateFlow()

    private val _mapPins = MutableStateFlow<List<MapPin>>(emptyList())
    val mapPins: StateFlow<List<MapPin>> = _mapPins.asStateFlow()

    private val _isMapLoading = MutableStateFlow(false)
    val isMapLoading: StateFlow<Boolean> = _isMapLoading.asStateFlow()

    private val _mapSearchStep = MutableStateFlow(0) // 0: Idle, 1: Querying Places API, 2: Running Gemini Filters, 3: Success
    val mapSearchStep: StateFlow<Int> = _mapSearchStep.asStateFlow()

    fun loadHiddenGemsMap(city: String) {
        if (city.trim().isEmpty()) return
        viewModelScope.launch {
            _isMapLoading.value = true
            _mapCityName.value = city
            _mapSearchStep.value = 1 // Step 1: Querying Google Places API
            kotlinx.coroutines.delay(1200) // Simulate Nearby Search latency
            
            _mapSearchStep.value = 2 // Step 2: Running Gemini AI Filter
            val jsonResponse = GeminiService.getNearbyHiddenGemsMap(city)
            kotlinx.coroutines.delay(1000) // Simulate AI reasoning latency
            
            try {
                val obj = org.json.JSONObject(jsonResponse)
                _mapCenterLat.value = obj.optDouble("centerLat", 26.9124)
                _mapCenterLng.value = obj.optDouble("centerLng", 75.7873)
                
                val pinsArray = obj.optJSONArray("pins")
                val list = mutableListOf<MapPin>()
                if (pinsArray != null) {
                    for (i in 0 until pinsArray.length()) {
                        val pObj = pinsArray.getJSONObject(i)
                        list.add(
                            MapPin(
                                name = pObj.optString("name", "Unknown Place"),
                                latitude = pObj.optDouble("latitude", 0.0),
                                longitude = pObj.optDouble("longitude", 0.0),
                                rating = pObj.optDouble("rating", 4.5),
                                reviews = pObj.optInt("reviews", 100),
                                isHiddenGem = pObj.optBoolean("isHiddenGem", false),
                                type = pObj.optString("type", "Sight"),
                                description = pObj.optString("description", ""),
                                hiddenGemReason = pObj.optString("hiddenGemReason", "")
                            )
                        )
                    }
                }
                _mapPins.value = list
                _mapSearchStep.value = 3 // Success!
            } catch (e: Exception) {
                _mapSearchStep.value = 0
            } finally {
                _isMapLoading.value = false
            }
        }
    }

    // --- BADGES STATE SYSTEM ---
    private val _unlockedBadges = MutableStateFlow<Set<String>>(setOf("Jaipur_Cultural", "Varanasi_Spiritual", "Goa_Beach"))
    val unlockedBadges: StateFlow<Set<String>> = _unlockedBadges.asStateFlow()

    fun toggleBadge(badgeId: String) {
        val current = _unlockedBadges.value.toMutableSet()
        if (current.contains(badgeId)) {
            current.remove(badgeId)
        } else {
            current.add(badgeId)
        }
        _unlockedBadges.value = current
    }

    fun unlockBadge(badgeId: String) {
        val current = _unlockedBadges.value.toMutableSet()
        if (!current.contains(badgeId)) {
            current.add(badgeId)
            _unlockedBadges.value = current
        }
    }

    // --- JSON ARRAY HELPER ---
    private fun parseJsonArray(jsonStr: String?): List<String> {
        if (jsonStr.isNullOrEmpty()) return emptyList()
        return try {
            val array = org.json.JSONArray(jsonStr)
            val list = mutableListOf<String>()
            for (i in 0 until array.length()) {
                list.add(array.getString(i))
            }
            list
        } catch (e: Exception) {
            emptyList()
        }
    }
}

data class MapPin(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    val reviews: Int,
    val isHiddenGem: Boolean,
    val type: String,
    val description: String,
    val hiddenGemReason: String
)

