package com.example.data

import android.util.Log
import com.example.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.TimeUnit

object GeminiService {
    private const val TAG = "GeminiService"
    private const val MODEL_NAME = "gemini-3.5-flash"
    private const val BASE_URL = "https://generativelanguage.googleapis.com/v1beta/models/$MODEL_NAME:generateContent"

    private val client = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    private val mediaType = "application/json; charset=utf-8".toMediaType()

    /**
     * Checks if API Key is configured and valid
     */
    fun isApiKeyAvailable(): Boolean {
        val key = BuildConfig.GEMINI_API_KEY
        return !key.isNullOrEmpty() && key != "MY_GEMINI_API_KEY"
    }

    /**
     * Sends a direct generateContent request to Gemini API
     */
    private suspend fun callGeminiApi(payload: JSONObject): String? = withContext(Dispatchers.IO) {
        val apiKey = BuildConfig.GEMINI_API_KEY
        if (!isApiKeyAvailable()) {
            Log.e(TAG, "Gemini API key is not configured. Using fallback logic.")
            return@withContext null
        }

        val url = "$BASE_URL?key=$apiKey"
        val body = payload.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        try {
            client.newCall(request).execute().use { response ->
                val bodyString = response.body?.string()
                if (!response.isSuccessful) {
                    Log.e(TAG, "API call failed with code ${response.code}: $bodyString")
                    return@withContext null
                }
                if (bodyString.isNullOrEmpty()) return@withContext null

                val jsonResponse = JSONObject(bodyString)
                val candidates = jsonResponse.optJSONArray("candidates")
                if (candidates != null && candidates.length() > 0) {
                    val content = candidates.getJSONObject(0).optJSONObject("content")
                    val parts = content?.optJSONArray("parts")
                    if (parts != null && parts.length() > 0) {
                        return@withContext parts.getJSONObject(0).optString("text")
                    }
                }
                return@withContext null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Network exception calling Gemini API", e)
            return@withContext null
        }
    }

    /**
     * Generate chatbot response for cultural query
     */
    suspend fun getChatResponse(prompt: String, history: List<Pair<String, Boolean>>): String {
        val payload = JSONObject()
        val contentsArray = JSONArray()

        // Construct history context
        // User parts are Pair(message, true), Model parts are Pair(message, false)
        for (item in history) {
            val turn = JSONObject()
            val role = if (item.second) "user" else "model"
            turn.put("role", role)
            
            val partsArray = JSONArray()
            val partObj = JSONObject()
            partObj.put("text", item.first)
            partsArray.put(partObj)
            
            turn.put("parts", partsArray)
            contentsArray.put(turn)
        }

        // Add final prompt
        val currentTurn = JSONObject().apply {
            put("role", "user")
            put("parts", JSONArray().put(JSONObject().put("text", prompt)))
        }
        contentsArray.put(currentTurn)
        payload.put("contents", contentsArray)

        // System Instruction
        val systemInstruction = JSONObject().apply {
            put("parts", JSONArray().put(JSONObject().put("text", 
                "You are CultureVerse AI, an expert cultural travel companion. " +
                "Help the user learn about global traditions, historical insights, travel etiquettes, local cuisines, and language basics. " +
                "Provide detailed, engaging, respectful, and visually well-formatted answers with clear sections."
            )))
        }
        payload.put("systemInstruction", systemInstruction)

        val response = callGeminiApi(payload)
        if (response != null) return response

        // Fallback response for offline or unconfigured API keys
        return getMockChatResponse(prompt)
    }

    /**
     * Generates a beautiful cultural reflection from a travel journal entry
     */
    suspend fun getJournalReflection(title: String, content: String, location: String): String {
        val prompt = "Analyze this travel journal entry and provide a thoughtful, educational cultural reflection, explanation of local customs mentioned, or suggestions on cultural items to explore in $location.\n" +
                "Journal Title: $title\n" +
                "Journal Content: $content\n" +
                "Location: $location"

        val payload = JSONObject().apply {
            put("contents", JSONArray().put(JSONObject().put("parts", JSONArray().put(JSONObject().put("text", prompt)))))
            put("systemInstruction", JSONObject().put("parts", JSONArray().put(JSONObject().put("text", 
                "You are an anthropologist and cultural guide. Write a brief, inspiring, 2-3 paragraph reflection. Highlight local traditions, linguistic insights, and historical context based on the user's entry."
            ))))
        }

        val response = callGeminiApi(payload)
        if (response != null) return response

        return "This is a beautiful journey in $location! Here is a cultural note: Journaling about local places helps deepen our respect for local heritage. Exploring $location further will reveal centuries-old traditions, from craft making to native architecture. Keep engaging with local guides and trying authentic regional delicacies to fully immerse yourself in their heritage!"
    }

    /**
     * Generates a structured cultural travel itinerary in JSON
     */
    suspend fun getItinerary(destination: String, days: Int, focus: String): String {
        val prompt = "Create a detailed day-by-day cultural itinerary for a $days-day trip to $destination, focusing on '$focus'. " +
                "You MUST output raw JSON following this exact structure, with NO extra markdown formatting outside of the JSON block:\n" +
                "{\n" +
                "  \"destination\": \"$destination\",\n" +
                "  \"days\": [\n" +
                "    {\n" +
                "      \"day\": 1,\n" +
                "      \"theme\": \"Daily Main Theme Name\",\n" +
                "      \"activities\": [\n" +
                "        {\n" +
                "          \"time\": \"Morning\",\n" +
                "          \"title\": \"Activity Title\",\n" +
                "          \"description\": \"Describe cultural value, custom, and why it is significant.\",\n" +
                "          \"etiquette\": \"Specific local custom or behavior rule for this place (e.g. remove shoes, bow, avoid loud talking).\"\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}"

        val payload = JSONObject().apply {
            put("contents", JSONArray().put(JSONObject().put("parts", JSONArray().put(JSONObject().put("text", prompt)))))
            val config = JSONObject().apply {
                val format = JSONObject().apply {
                    put("mimeType", "application/json")
                }
                put("responseFormat", format)
            }
            put("generationConfig", config)
        }

        val response = callGeminiApi(payload)
        if (!response.isNullOrEmpty()) {
            try {
                // Validate if it is parseable JSON
                JSONObject(response)
                return response
            } catch (e: Exception) {
                Log.e(TAG, "Generated itinerary is not valid JSON, returning fallback JSON", e)
            }
        }

        return getMockItineraryJson(destination, days, focus)
    }

    // --- MOCK FALLBACKS ---

    private fun getMockChatResponse(prompt: String): String {
        val lower = prompt.lowercase()
        return when {
            lower.contains("japan") || lower.contains("kyoto") || lower.contains("tokyo") -> {
                "**CultureVerse AI [Local Insight - Japan]**\n\n" +
                "Thank you for asking about Japan! Japan's culture beautifully blends ancient traditions with futuristic innovations.\n\n" +
                "### 🏯 Key Cultural Etiquette\n" +
                "* **Bowing (Rei):** It signifies respect, gratitude, or apology. A simple tilt of the head is common for casual greetings.\n" +
                "* **Shoe Rules (Uwabaki):** Always remove your shoes at the entrance of homes, temples, and traditional ryokan guesthouses. Slip on provided guest slippers.\n" +
                "* **Table Manners:** Never stand chopsticks upright in a bowl of rice (resembles funeral rites). Slurping noodles, however, is a compliment to the chef!\n\n" +
                "### 🍡 Authentic Cuisine\n" +
                "* **Kaiseki Ryori:** A multi-course traditional Japanese dinner balancing taste, texture, and visual aesthetics.\n" +
                "* **Yatsuhashi:** Kyoto's famous cinnamon-infused sweet rice-flour snack filled with red bean paste."
            }
            lower.contains("italy") || lower.contains("rome") || lower.contains("food") -> {
                "**CultureVerse AI [Local Insight - Italy]**\n\n" +
                "In Italy, culture revolves heavily around family, art, architecture, and food.\n\n" +
                "### 🍝 Culinary Etiquettes\n" +
                "* **No Cappuccino after 11 AM:** Cappuccino is considered a breakfast drink. Ordering it post-lunch is considered heavy for digestion.\n" +
                "* **Pasta Precision:** Never use a spoon to twist spaghetti, and never, ever cut your pasta with a knife.\n" +
                "* **Riposo:** Many shops close between 1:30 PM and 4:00 PM for afternoon rest. Plan your explorations accordingly!"
            }
            else -> {
                "**Welcome to CultureVerse AI Companion!**\n\n" +
                "I am ready to help you explore global heritages, custom etiquettes, and cultural landmarks! To unlock my real-time AI capabilities powered by **Gemini 3.5 Flash**, please add your `GEMINI_API_KEY` to the **Secrets panel** in Google AI Studio.\n\n" +
                "### 🌍 Recommended Cultural Tips for Travel:\n" +
                "1. **Learn 5 Basic Words:** *Hello, Thank You, Please, Excuse Me*, and *Do you speak English?* in the local language opens doors and hearts.\n" +
                "2. **Dress Code Sensitivity:** Always research clothing standards for religious sites (e.g., covering shoulders and knees in temples, churches, or mosques).\n" +
                "3. **Tipping Customs:** Tipping varies massively. It is mandatory in the US, appreciated but rare in Europe, and considered rude in Japan."
            }
        }
    }

    private fun getMockItineraryJson(destination: String, days: Int, focus: String): String {
        return """
        {
          "destination": "$destination",
          "days": [
            {
              "day": 1,
              "theme": "Historical Heritage & Sacred Sanctuaries",
              "activities": [
                {
                  "time": "09:00 AM",
                  "title": "Ancient Temple Guided Tour",
                  "description": "Visit the central heritage landmark. Admire centuries-old wood carpentry and sacred sculptures detailing original foundation stories.",
                  "etiquette": "Please speak in hushed tones, walk calmly, and never point with your finger. Photography may be restricted in inner shrines."
                },
                {
                  "time": "01:00 PM",
                  "title": "Heritage Lunch Experience",
                  "description": "Savor a traditional regional meal prepared using seasonal local ingredients sourced from nearby farming villages.",
                  "etiquette": "Express gratitude with a local phrase before and after eating."
                },
                {
                  "time": "04:00 PM",
                  "title": "Traditional Craft Workshop",
                  "description": "Engage with a veteran artisan master to create a small keepsake using clay, paper, or textiles following generational methods.",
                  "etiquette": "Listen carefully to instructions and handle artisan tools with deep respect."
                }
              ]
            }
          ]
        }
        """.trimIndent()
    }

    /**
     * Retrieves nearby attractions from simulated Google Places API and filters them using Gemini to highlight only hidden gems.
     */
    suspend fun getNearbyHiddenGemsMap(city: String): String {
        val prompt = """
            We are simulating a Google Places API nearby search for "$city" and applying a Gemini filter to extract ONLY true cultural "hidden gems" (authentic, lesser-known, non-commercial spots).
            Generate a JSON object containing 7-8 places in or near "$city".
            At least 3 must be mainstream/famous landmarks (e.g. isHiddenGem = false), and at least 3-4 must be genuine hidden gems (e.g. isHiddenGem = true).
            Provide realistic latitude and longitude coordinates for each, close to each other so they fit on a single local map screen.
            
            Return a raw JSON object following this exact schema (no markdown blocks, no enclosing quotes, just raw parseable JSON):
            {
              "cityName": "$city",
              "centerLat": 26.9124, 
              "centerLng": 75.7873,
              "pins": [
                {
                  "name": "Panna Meena ka Kund",
                  "latitude": 26.9850,
                  "longitude": 75.8507,
                  "rating": 4.6,
                  "reviews": 1280,
                  "isHiddenGem": true,
                  "type": "Historical Stepwell",
                  "description": "An ancient 16th-century geometric stepwell with stunning interlocking symmetrical stairways.",
                  "hiddenGemReason": "Rarely visited by mainstream tours; offers absolute serene architectural photography opportunities."
                }
              ]
            }
            Ensure the coordinates are authentic and accurate for the region.
        """.trimIndent()

        val payload = JSONObject().apply {
            put("contents", JSONArray().put(JSONObject().put("parts", JSONArray().put(JSONObject().put("text", prompt)))))
            val config = JSONObject().apply {
                val format = JSONObject().apply {
                    put("mimeType", "application/json")
                }
                put("responseFormat", format)
            }
            put("generationConfig", config)
        }

        val response = callGeminiApi(payload)
        if (!response.isNullOrEmpty()) {
            try {
                JSONObject(response) // Validate it is parseable JSON
                return response
            } catch (e: Exception) {
                Log.e(TAG, "Failed to parse Gemini map response, falling back", e)
            }
        }
        return getMockHiddenGemsMap(city)
    }

    fun getMockHiddenGemsMap(city: String): String {
        return when (city.lowercase().trim()) {
            "jaipur" -> """
                {
                  "cityName": "Jaipur",
                  "centerLat": 26.9124, 
                  "centerLng": 75.7873,
                  "pins": [
                    {
                      "name": "Panna Meena ka Kund",
                      "latitude": 26.9850,
                      "longitude": 75.8507,
                      "rating": 4.6,
                      "reviews": 1280,
                      "isHiddenGem": true,
                      "type": "Historical Stepwell",
                      "description": "An ancient 16th-century geometric stepwell with stunning interlocking symmetrical stairways.",
                      "hiddenGemReason": "Rarely visited by mainstream tours; offers absolute serene architectural photography opportunities."
                    },
                    {
                      "name": "Galta Ji Temple (Monkey Temple)",
                      "latitude": 26.9174,
                      "longitude": 75.8584,
                      "rating": 4.5,
                      "reviews": 3200,
                      "isHiddenGem": true,
                      "type": "Sacred Temple",
                      "description": "A historic sacred pilgrimage site nestled in a narrow mountain pass, famous for its natural water springs and resident monkeys.",
                      "hiddenGemReason": "Tucked away in the hills; provides a spiritual escape with panoramic valley views."
                    },
                    {
                      "name": "Anokhi Museum of Hand Printing",
                      "latitude": 26.9821,
                      "longitude": 75.8485,
                      "rating": 4.4,
                      "reviews": 450,
                      "isHiddenGem": true,
                      "type": "Art Museum",
                      "description": "A museum dedicated to preserving traditional hand-block woodcut printing and indigenous textiles.",
                      "hiddenGemReason": "Deeply cultural and peaceful, located in a beautifully restored historic mansion."
                    },
                    {
                      "name": "Amer Fort",
                      "latitude": 26.9855,
                      "longitude": 75.8513,
                      "rating": 4.8,
                      "reviews": 45000,
                      "isHiddenGem": false,
                      "type": "Fortress Palace",
                      "description": "A magnificent hilltop fort built of red sandstone and marble, famous for its artistic style elements.",
                      "hiddenGemReason": "Highly mainstream attraction. Listed for geographical contrast."
                    },
                    {
                      "name": "Hawa Mahal",
                      "latitude": 26.9239,
                      "longitude": 75.8267,
                      "rating": 4.7,
                      "reviews": 38000,
                      "isHiddenGem": false,
                      "type": "Royal Palace",
                      "description": "A stunning five-story exterior facade resembling a honeycomb, built for royal ladies to view the street life.",
                      "hiddenGemReason": "The ultimate mainstream photo-stop in Jaipur."
                    },
                    {
                      "name": "City Palace",
                      "latitude": 26.9258,
                      "longitude": 75.8236,
                      "rating": 4.6,
                      "reviews": 29000,
                      "isHiddenGem": false,
                      "type": "Royal Museum",
                      "description": "The regal administrative seat of the Maharaja of Jaipur, housing galleries of royal costumes and weaponry.",
                      "hiddenGemReason": "Mainstream historical hub."
                    }
                  ]
                }
            """.trimIndent()
            "goa" -> """
                {
                  "cityName": "Goa",
                  "centerLat": 15.4909, 
                  "centerLng": 73.8278,
                  "pins": [
                    {
                      "name": "Netravali Bubbling Lake",
                      "latitude": 15.0882,
                      "longitude": 74.2234,
                      "rating": 4.3,
                      "reviews": 320,
                      "isHiddenGem": true,
                      "type": "Natural Spring",
                      "description": "A unique volcanic water spring pool that bubbles continuously when you clap your hands near it.",
                      "hiddenGemReason": "Deep inside a sanctuary, offering a silent mystical connection with nature."
                    },
                    {
                      "name": "Harvalem Cave Ruins",
                      "latitude": 15.5512,
                      "longitude": 74.0253,
                      "rating": 4.1,
                      "reviews": 180,
                      "isHiddenGem": true,
                      "type": "Ancient Caves",
                      "description": "Ancient 6th-century rock-cut cave monuments associated with the Pandava legends.",
                      "hiddenGemReason": "Fascinating historical cave-dwelling ruins surrounded by lush, peaceful greenery."
                    },
                    {
                      "name": "Butterfly Beach",
                      "latitude": 15.0042,
                      "longitude": 73.9873,
                      "rating": 4.4,
                      "reviews": 850,
                      "isHiddenGem": true,
                      "type": "Secluded Beach",
                      "description": "A pristine, semicircular hidden beach cove accessed mainly by boat or jungle trek.",
                      "hiddenGemReason": "Isolated from commercial beach shacks, a perfect secret sunset sanctuary."
                    },
                    {
                      "name": "Baga Beach",
                      "latitude": 15.5552,
                      "longitude": 73.7517,
                      "rating": 4.5,
                      "reviews": 65000,
                      "isHiddenGem": false,
                      "type": "Popular Beach",
                      "description": "One of the most famous and crowded beaches in North Goa, packed with shacks and water sports.",
                      "hiddenGemReason": "Mainstream resort beach."
                    },
                    {
                      "name": "Basilica of Bom Jesus",
                      "latitude": 15.5009,
                      "longitude": 73.9116,
                      "rating": 4.7,
                      "reviews": 25000,
                      "isHiddenGem": false,
                      "type": "Baroque Basilica",
                      "description": "A UNESCO World Heritage church holding the sacred mortal remains of St. Francis Xavier.",
                      "hiddenGemReason": "Mainstream religious site."
                    }
                  ]
                }
            """.trimIndent()
            "varanasi" -> """
                {
                  "cityName": "Varanasi",
                  "centerLat": 25.3176, 
                  "centerLng": 82.9739,
                  "pins": [
                    {
                      "name": "Lalita Ghat Nepali Temple",
                      "latitude": 25.3106,
                      "longitude": 83.0135,
                      "rating": 4.5,
                      "reviews": 450,
                      "isHiddenGem": true,
                      "type": "Wooden Pagoda",
                      "description": "A stunning pagoda-style shrine built of terracotta, stone, and wood, carved by Nepalese craftsmen.",
                      "hiddenGemReason": "Often overlooked by fast tourists; showcase of unique Nepalese architecture on the Ganges."
                    },
                    {
                      "name": "Sarnath Deer Park Ruins",
                      "latitude": 25.3761,
                      "longitude": 83.0228,
                      "rating": 4.6,
                      "reviews": 12000,
                      "isHiddenGem": true,
                      "type": "Buddhist Heritage",
                      "description": "The sacred ancient ruins where Gautama Buddha first preached the Dharma to his disciples.",
                      "hiddenGemReason": "Extremely peaceful monastic park away from Varanasi's chaotic traffic."
                    },
                    {
                      "name": "Chunar Fort",
                      "latitude": 25.1228,
                      "longitude": 82.8794,
                      "rating": 4.2,
                      "reviews": 920,
                      "isHiddenGem": true,
                      "type": "Sandstone Citadel",
                      "description": "An ancient fortress looking over a dramatic loop of the Ganges, steeped in medieval legends.",
                      "hiddenGemReason": "A 40km retreat; completely untamed historical ramparts with dramatic views."
                    },
                    {
                      "name": "Dashashwamedh Ghat",
                      "latitude": 25.3078,
                      "longitude": 83.0101,
                      "rating": 4.8,
                      "reviews": 54000,
                      "isHiddenGem": false,
                      "type": "Sacred River Ghat",
                      "description": "The spectacular main ghat on the Ganges, home to the daily evening Ganga Aarti fire ceremony.",
                      "hiddenGemReason": "Highly mainstream spiritual hub."
                    },
                    {
                      "name": "Kashi Vishwanath Temple",
                      "latitude": 25.3108,
                      "longitude": 83.0104,
                      "rating": 4.7,
                      "reviews": 48000,
                      "isHiddenGem": false,
                      "type": "Golden Temple",
                      "description": "One of India's most famous and sacred temples, dedicated to Lord Shiva, capped with pure gold.",
                      "hiddenGemReason": "Mainstream temple."
                    }
                  ]
                }
            """.trimIndent()
            "kerala" -> """
                {
                  "cityName": "Kerala",
                  "centerLat": 10.8505, 
                  "centerLng": 76.2711,
                  "pins": [
                    {
                      "name": "Vagamon Pine Forests",
                      "latitude": 9.6874,
                      "longitude": 76.9048,
                      "rating": 4.4,
                      "reviews": 890,
                      "isHiddenGem": true,
                      "type": "Pine forest valley",
                      "description": "Misty, whispering pine tree slopes overlooking deep valleys, entirely off the regular tour bus routes.",
                      "hiddenGemReason": "Secluded hill-country sanctuary that feels completely removed from the tropical plains."
                    },
                    {
                      "name": "Muzhappilangad Drive-In Beach",
                      "latitude": 11.7924,
                      "longitude": 75.4373,
                      "rating": 4.6,
                      "reviews": 1100,
                      "isHiddenGem": true,
                      "type": "Drive-In Beach",
                      "description": "Asia's longest drive-in sandy beach, allowing you to drive directly along 4km of gorgeous coast.",
                      "hiddenGemReason": "Rarely known to international tourists; a massive local favorite with great sunset vibes."
                    },
                    {
                      "name": "Gavi Eco-Tourism Reserve",
                      "latitude": 9.4374,
                      "longitude": 77.1633,
                      "rating": 4.5,
                      "reviews": 580,
                      "isHiddenGem": true,
                      "type": "Forest Eco-Reserve",
                      "description": "A pristine, lush evergreen forest reserve hosting rich wildlife, waterfalls, and mist-veiled tea estates.",
                      "hiddenGemReason": "Quiet, entry-regulated wilderness that guarantees zero commercial noise."
                    },
                    {
                      "name": "Alleppey Backwaters",
                      "latitude": 9.4981,
                      "longitude": 76.3388,
                      "rating": 4.8,
                      "reviews": 32000,
                      "isHiddenGem": false,
                      "type": "Houseboat Canals",
                      "description": "The world-famous system of interconnected palm-fringed rivers, lakes, and backwaters.",
                      "hiddenGemReason": "The major mainstream attraction of Kerala."
                    },
                    {
                      "name": "Munnar Tea Hills",
                      "latitude": 10.0889,
                      "longitude": 77.0595,
                      "rating": 4.7,
                      "reviews": 28000,
                      "isHiddenGem": false,
                      "type": "Hill Station",
                      "description": "Rolling hills blanketed by green tea plantations, standard for almost every South India traveler.",
                      "hiddenGemReason": "Mainstream tourist hill station."
                    }
                  ]
                }
            """.trimIndent()
            else -> """
                {
                  "cityName": "$city",
                  "centerLat": 20.0000, 
                  "centerLng": 77.0000,
                  "pins": [
                    {
                      "name": "Secret Heritage Altar of $city",
                      "latitude": 20.0120,
                      "longitude": 77.0150,
                      "rating": 4.5,
                      "reviews": 45,
                      "isHiddenGem": true,
                      "type": "Sacred Ruins",
                      "description": "An untouched ancient altar covered in roots and local wildflowers, known only to elder villagers.",
                      "hiddenGemReason": "Provides raw archaeological atmosphere and absolute quietude."
                    },
                    {
                      "name": "Traditional Artisans Corner",
                      "latitude": 19.9850,
                      "longitude": 76.9920,
                      "rating": 4.4,
                      "reviews": 32,
                      "isHiddenGem": true,
                      "type": "Village Workshop",
                      "description": "Local family cooperative keeping the ancient pottery and weaving heritage alive in their backyard.",
                      "hiddenGemReason": "Not listed in commercial guides; genuine direct interaction with culture creators."
                    },
                    {
                      "name": "Main Street Bazaar",
                      "latitude": 20.0000,
                      "longitude": 77.0000,
                      "rating": 4.6,
                      "reviews": 4500,
                      "isHiddenGem": false,
                      "type": "Shopping Hub",
                      "description": "The central bustling market street packed with modern shops and generic souvenirs.",
                      "hiddenGemReason": "Mainstream shopping center."
                    }
                  ]
                }
            """.trimIndent()
        }
    }
}
