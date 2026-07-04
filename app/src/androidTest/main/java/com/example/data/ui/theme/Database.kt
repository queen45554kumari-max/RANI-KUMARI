package com.example.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

// --- USER PROFILE ENTITY ---
@Entity(tableName = "users")
data class UserProfile(
    @PrimaryKey val userId: String = "local_user_1",
    val name: String,
    val email: String,
    val photo: String, // selected avatar emoji or profile image URI
    val savedTripsJson: String = "[]", // JSON array of saved tripIds (savedTrips[])
    val favouritePlacesJson: String = "[]", // JSON array of favorite sights (favouritePlaces[])
    val historyJson: String = "[]" // JSON array of search/chat history (history[])
)

// --- JOURNAL ENTRY ENTITY ---
@Entity(tableName = "journal_entries")
data class JournalEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val content: String,
    val location: String,
    val category: String, // "Festival", "Food", "History", "Reflection", "General"
    val timestamp: Long = System.currentTimeMillis(),
    val imageUrl: String? = null,
    val culturalReflection: String? = null // AI-generated cultural reflection
)

// --- ITINERARY / TRIP ENTITY ---
@Entity(tableName = "itineraries")
data class Itinerary(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val tripId: String, // Unique trip identifier (UUID)
    val destination: String,
    val days: Int, // Number of days
    val budget: String, // Budget selection (e.g. Budget, Moderate, Luxury)
    val interests: String, // Cultural interest focus (e.g. Culinary, Traditional Arts)
    val generatedItinerary: String, // Store detailed itinerary JSON
    val timestamp: Long = System.currentTimeMillis(),
    
    // Compatibility fields to keep other parts of the app compiling flawlessly:
    val durationDays: Int = days,
    val startDate: String = "",
    val culturalFocus: String = interests,
    val activitiesJson: String = generatedItinerary,
    val createdAt: Long = timestamp
)

// --- DAOs ---
@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    fun getUserProfile(userId: String): Flow<UserProfile?>

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1")
    suspend fun getUserProfileOnce(userId: String): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(profile: UserProfile)

    @Query("DELETE FROM users WHERE userId = :userId")
    suspend fun deleteUserProfile(userId: String)
}

@Dao
interface JournalDao {
    @Query("SELECT * FROM journal_entries ORDER BY timestamp DESC")
    fun getAllJournals(): Flow<List<JournalEntry>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJournal(entry: JournalEntry)

    @Query("DELETE FROM journal_entries WHERE id = :id")
    suspend fun deleteJournalById(id: Int)
}

@Dao
interface ItineraryDao {
    @Query("SELECT * FROM itineraries ORDER BY timestamp DESC")
    fun getAllItineraries(): Flow<List<Itinerary>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItinerary(itinerary: Itinerary)

    @Query("DELETE FROM itineraries WHERE id = :id")
    suspend fun deleteItineraryById(id: Int)
}

// --- DATABASE ---
@Database(
    entities = [UserProfile::class, JournalEntry::class, Itinerary::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun journalDao(): JournalDao
    abstract fun itineraryDao(): ItineraryDao
}
