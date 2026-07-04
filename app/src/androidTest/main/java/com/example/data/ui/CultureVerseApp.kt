package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Festival
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Museum
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TravelExplore
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.R
import com.example.data.Itinerary
import com.example.data.JournalEntry
import org.json.JSONArray
import org.json.JSONObject

// Presets for avatar choice
val AVATAR_EMOJIS = listOf("🗺️", "🏯", "🏺", "🦁")
val REGIONS = listOf("East Asia", "Europe", "South Asia", "Latin America", "Middle East", "Africa")

// PRESET CULTURAL DESTINATIONS FOR THE EXPLORE SCREEN
data class CulturalItem(
    val title: String,
    val subtitle: String,
    val description: String,
    val location: String,
    val imageRes: Int,
    val customTag: String,
    val etiquette: String
)

data class BadgeInfo(
    val id: String,
    val title: String,
    val city: String,
    val description: String,
    val emoji: String,
    val color: Color
)

val PRESET_HERITAGES = listOf(
    CulturalItem(
        "Kinkaku-ji (Kyoto Pagoda)", "Japan's Golden Pavilion",
        "A spectacular Zen Buddhist temple in northern Kyoto. The top two floors are completely covered in gold leaf, standing beautifully over a mirror-like pond.",
        "Kyoto, Japan", R.drawable.img_app_icon_1783146022193, "Sacred Site",
        "Keep your voice whisper-quiet. Remove shoes when stepping into traditional floor mats."
    ),
    CulturalItem(
        "The Colosseum", "Flavian Amphitheater",
        "An iconic symbol of Rome and imperial power. Built in the 1st century AD under Flavian emperors, hosting grand gladatorial games and dramatic spectacles.",
        "Rome, Italy", R.drawable.img_hero_banner_1783146035851, "Roman Heritage",
        "Avoid climbing historical stones or writing on original ruins to preserve global history."
    ),
    CulturalItem(
        "Taj Mahal", "Monument of Love",
        "An ivory-white marble mausoleum on the south bank of Yamuna river. Commissioned by Emperor Shah Jahan in memory of his beloved wife Mumtaz Mahal.",
        "Agra, India", R.drawable.img_hero_banner_1783146035851, "Wonder of the World",
        "Shoes must be removed or covered with booties before entering the main inner tomb chamber."
    ),
    CulturalItem(
        "Hawa Mahal", "Palace of Winds",
        "A magnificent pink sandstone palace with 953 small windows decorated with intricate latticework, designed to allow royal ladies to observe street life unseen.",
        "Jaipur, India", R.drawable.img_jaipur_1783147585676, "Royal Heritage",
        "Maintain respectful silence inside the historic halls and do not lean or sit on the delicate architectural railings."
    ),
    CulturalItem(
        "Ganges Ghats", "River of Life",
        "The sacred riverfront stone steps where pilgrims perform ritual ablutions. Varanasi is one of the oldest continuously inhabited cities on earth and the spiritual heart of Hinduism.",
        "Varanasi, India", R.drawable.img_varanasi_1783147611733, "Sacred Site",
        "Photography is strictly prohibited at the cremation ghats. Dress conservatively and speak in quiet tones."
    ),
    CulturalItem(
        "Basilica of Bom Jesus", "Goan Portuguese Baroque",
        "A historic Roman Catholic basilica in Goa holding the sacred remains of St. Francis Xavier. It is a landmark of baroque architecture and a UNESCO World Heritage site.",
        "Goa, India", R.drawable.img_goa_1783147598633, "Christian Heritage",
        "Remove hats, maintain absolute silence inside the sanctuary, and ensure shoulders and knees are covered."
    ),
    CulturalItem(
        "Kerala Houseboat Backwaters", "Vembanad Backwater Serenity",
        "Traditional houseboats made of wooden planks tied with coconut ropes, gliding peacefully through the extensive, scenic, palm-fringed backwater canals.",
        "Kerala, India", R.drawable.img_kerala_1783147625772, "Nature Heritage",
        "Dispose of waste responsibly to preserve the delicate aquatic ecosystem and avoid making loud, disruptive noises."
    )
)

val PRESET_FESTIVALS = listOf(
    CulturalItem(
        "Diwali", "Festival of Lights",
        "India's largest national celebration, symbolizing the spiritual victory of light over darkness, good over evil, and knowledge over ignorance.",
        "India", R.drawable.img_app_icon_1783146022193, "Festival",
        "Always join in decorating with small oil clay lamps (diyas) and share traditional sweets with neighbors."
    ),
    CulturalItem(
        "Hanami", "Cherry Blossom Season",
        "The ancient Japanese tradition of welcoming spring by viewing and picnicking underneath full blooming cherry blossom trees.",
        "Japan", R.drawable.img_hero_banner_1783146035851, "Seasonal Heritage",
        "Never pull or break cherry blossom branches off the trees; leave their beauty intact."
    ),
    CulturalItem(
        "Dia de los Muertos", "Day of the Dead",
        "A colorful Mexican holiday combining indigenous Aztec rituals and Catholic feasts to honor and joyfully remember departed ancestors.",
        "Mexico", R.drawable.img_app_icon_1783146022193, "Tradition",
        "Treat ofrenda shrines with quiet respect; they are deeply personal family family tributes."
    )
)

val PRESET_FOODS = listOf(
    CulturalItem(
        "Traditional Sushi", "Edomae Artistry",
        "Masterfully sliced raw fish served over vinegared sushi rice. Originating as street food in Edo (Tokyo), it has evolved into a global culinary art form.",
        "Tokyo, Japan", R.drawable.img_app_icon_1783146022193, "Gastronomy",
        "Eat nigiri in one clean bite. Dip the fish-side lightly into soy sauce, never the rice-side."
    ),
    CulturalItem(
        "Neapolitan Pizza", "UNESCO Heritage Food",
        "Crafted with simple, fresh ingredients: basic dough, raw San Marzano tomatoes, fresh mozzarella, fresh basil, and extra virgin olive oil.",
        "Naples, Italy", R.drawable.img_hero_banner_1783146035851, "Cuisine",
        "Neapolitan pizza is soft and folded; it is traditionally eaten with a fork and knife, or folded 'a portafoglio' (wallet style)."
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CultureVerseApp(viewModel: CultureVerseViewModel) {
    val navController = rememberNavController()
    val isOnboarded by viewModel.isOnboarded.collectAsState()
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = isOnboarded && currentRoute != "onboarding",
        drawerContent = {
            if (isOnboarded && currentRoute != "onboarding") {
                ModalDrawerSheet(
                    modifier = Modifier.fillMaxHeight(),
                    drawerContainerColor = MaterialTheme.colorScheme.surface
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 12.dp)
                    ) {
                        Text(
                            text = "CultureVerse AI",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = "Your Anthropologist Guide",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                    HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                    
                    val drawerItems = listOf(
                        Triple("home", "Home", Icons.Default.Home),
                        Triple("explore", "Explore", Icons.Default.Explore),
                        Triple("planner", "Trip Planner", Icons.Default.CalendarMonth),
                        Triple("map", "Map", Icons.Default.Map),
                        Triple("food", "Food", Icons.Default.Fastfood),
                        Triple("stories", "Stories", Icons.Default.Book),
                        Triple("festivals", "Festivals", Icons.Default.Festival),
                        Triple("chat", "Chat", Icons.Default.Chat),
                        Triple("journal", "Journal", Icons.Default.Book),
                        Triple("profile", "Profile", Icons.Default.Person)
                    )
                    
                    drawerItems.forEach { (route, label, icon) ->
                        val isSelected = currentRoute == route
                        NavigationDrawerItem(
                            icon = { Icon(icon, contentDescription = label) },
                            label = { Text(label, fontWeight = FontWeight.Bold) },
                            selected = isSelected,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navController.navigate(route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            modifier = Modifier
                                .padding(horizontal = 12.dp, vertical = 2.dp)
                                .testTag("drawer_item_$route"),
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        )
                    }
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(modifier = Modifier.weight(1f)) {
                    NavHost(
                        navController = navController,
                        startDestination = if (isOnboarded) "home" else "onboarding"
                    ) {
                        composable("onboarding") {
                            OnboardingScreen(
                                viewModel = viewModel,
                                onComplete = {
                                    navController.navigate("home") {
                                        popUpTo("onboarding") { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable("home") {
                            HomeScreen(
                                viewModel = viewModel, 
                                onNavigateToExplore = {
                                    navController.navigate("explore") {
                                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                onShowProfile = {
                                    navController.navigate("profile") {
                                        launchSingleTop = true
                                    }
                                },
                                onOpenDrawer = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                        composable("explore") {
                            ExploreScreen(
                                viewModel = viewModel,
                                onOpenDrawer = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                        composable("planner") {
                            PlannerScreen(
                                viewModel = viewModel,
                                onOpenDrawer = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                        composable("map") {
                            MapScreen(
                                viewModel = viewModel,
                                onOpenDrawer = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                        composable("food") {
                            FoodScreen(
                                viewModel = viewModel,
                                onOpenDrawer = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                        composable("stories") {
                            StoriesScreen(
                                viewModel = viewModel,
                                onOpenDrawer = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                        composable("festivals") {
                            FestivalsScreen(
                                viewModel = viewModel,
                                onOpenDrawer = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                        composable("chat") {
                            ChatScreen(
                                viewModel = viewModel,
                                onOpenDrawer = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                        composable("journal") {
                            JournalScreen(
                                viewModel = viewModel,
                                onOpenDrawer = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                        composable("profile") {
                            ProfileScreen(
                                viewModel = viewModel,
                                onOpenDrawer = {
                                    scope.launch { drawerState.open() }
                                }
                            )
                        }
                    }
                }

                if (isOnboarded && currentRoute != "onboarding") {
                    NavigationBar(
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 8.dp,
                        modifier = Modifier.testTag("navbar")
                    ) {
                        val items = listOf(
                            Triple("home", "Home", Icons.Default.Home),
                            Triple("explore", "Explore", Icons.Default.Explore),
                            Triple("planner", "Planner", Icons.Default.CalendarMonth),
                            Triple("chat", "Chat", Icons.Default.Chat),
                            Triple("journal", "Journal", Icons.Default.Book)
                        )

                        items.forEach { (route, label, icon) ->
                            NavigationBarItem(
                                selected = currentRoute == route,
                                onClick = {
                                    navController.navigate(route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = label,
                                        modifier = Modifier.size(24.dp)
                                    )
                                },
                                label = { Text(label, fontSize = 11.sp, fontWeight = FontWeight.Medium) },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = MaterialTheme.colorScheme.primary,
                                    selectedTextColor = MaterialTheme.colorScheme.primary,
                                    unselectedIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    unselectedTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                ),
                                modifier = Modifier.testTag("nav_item_$route")
                            )
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// ONBOARDING & LOGIN SCREEN
// ==========================================
@Composable
fun OnboardingScreen(viewModel: CultureVerseViewModel, onComplete: () -> Unit) {
    var nameInput by remember { mutableStateOf("") }
    var emailInput by remember { mutableStateOf("") }
    var selectedAvatarIdx by remember { mutableIntStateOf(0) }
    var selectedRegion by remember { mutableStateOf(REGIONS[0]) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primary)
                .border(2.dp, MaterialTheme.colorScheme.secondary, RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("🌍", fontSize = 48.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "CultureVerse AI",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif
        )

        Text(
            text = "Your AI companion for respectful travel, local custom etiquette, and cultural heritage journaling.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Explorer Information",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Explorer Name") },
                    placeholder = { Text("Enter your name...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("username_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = emailInput,
                    onValueChange = { emailInput = it },
                    label = { Text("Email Address") },
                    placeholder = { Text("Enter your email...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("email_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Choose your Heritage Emblem",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AVATAR_EMOJIS.forEachIndexed { idx, emoji ->
                        val isSelected = selectedAvatarIdx == idx
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .border(
                                    width = if (isSelected) 3.dp else 1.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = CircleShape
                                )
                                .clickable { selectedAvatarIdx = idx }
                                .testTag("avatar_choice_$idx"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(emoji, fontSize = 28.sp)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Select Preferred Region",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.height(12.dp))

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(REGIONS) { region ->
                        val isSelected = selectedRegion == region
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.secondary
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .clickable { selectedRegion = region }
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .testTag("region_tag_$region"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = region,
                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                viewModel.completeOnboarding(nameInput, emailInput, selectedAvatarIdx, selectedRegion)
                onComplete()
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("submit_button"),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Begin Journey", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
    }
}

// ==========================================
// HOME SCREEN
// ==========================================
@Composable
fun HomeScreen(
    viewModel: CultureVerseViewModel, 
    onNavigateToExplore: () -> Unit,
    onShowProfile: () -> Unit,
    onOpenDrawer: () -> Unit = {}
) {
    val explorerName by viewModel.username.collectAsState()
    val avatarIdx by viewModel.avatarIndex.collectAsState()
    val region by viewModel.favoriteRegion.collectAsState()

    val culturalFacts = listOf(
        "In Kyoto, walking while eating is considered poor manners. Enjoy food sitting down!",
        "Dia de los Muertos is not a sad day, but a vibrant feast to celebrate the cycle of life.",
        "When entering a Hindu temple in Agra, always go around the shrines in a clockwise direction.",
        "In Naples, traditional pizzas are strictly fired inside a bell-shaped stone wood oven."
    )

    var currentFactIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onOpenDrawer,
                modifier = Modifier.testTag("home_drawer_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open Drawer",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
                    .clickable { onShowProfile() },
                contentAlignment = Alignment.Center
            ) {
                Text(AVATAR_EMOJIS.getOrElse(avatarIdx) { "🗺️" }, fontSize = 28.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.clickable { onShowProfile() }
            ) {
                Text(
                    text = "Welcome back,",
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
                Text(
                    text = explorerName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = onShowProfile,
                modifier = Modifier.testTag("home_profile_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "View profile details",
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.width(4.dp))

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "📍 $region",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.img_hero_banner_1783146035851),
                    contentDescription = "Scenic World Heritages",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                            )
                        )
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Celebrate Diverse Heritages",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Learn local rules, map historic stories, and log custom memories.",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clickable {
                    currentFactIndex = (currentFactIndex + 1) % culturalFacts.size
                },
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("💡", fontSize = 28.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Cultural Custom of the Day",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = culturalFacts[currentFactIndex],
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Tap for another local custom rule ➔",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Explore Categories",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(110.dp)
                    .clickable { onNavigateToExplore() },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.TravelExplore,
                            contentDescription = "Explore heritages",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column {
                        Text("Heritages", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Traditional landmarks", fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }

            Card(
                modifier = Modifier
                    .weight(1f)
                    .height(110.dp)
                    .clickable { onNavigateToExplore() },
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fastfood,
                            contentDescription = "Explore traditional cuisines",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                    }
                    Column {
                        Text("Cuisines", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Local gastronomy arts", fontSize = 11.sp, color = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (!viewModel.isApiKeyValid) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFBBF24))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = "Warning icon",
                        tint = Color(0xFFD97706)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Note: Gemini AI is currently running in local-insight backup mode. Configure your GEMINI_API_KEY inside the Secrets panel to activate full real-time cultural synthesis!",
                        fontSize = 11.sp,
                        color = Color(0xFF78350F)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}

// ==========================================
// EXPLORE SCREEN (Cultures, Cuisines, Festivals)
// ==========================================
data class CityDetail(
    val name: String,
    val rating: Int,
    val imageRes: Int,
    val hiddenGems: List<String>,
    val food: List<String>,
    val culture: List<String>,
    val festivals: List<String>,
    val mapCoordinates: String,
    val nearbyAttractions: List<String>
)

val CITY_DETAILS = mapOf(
    "Jaipur" to CityDetail(
        name = "JAIPUR",
        rating = 5,
        imageRes = R.drawable.img_jaipur_1783147585676,
        hiddenGems = listOf(
            "💎 Panna Meena ka Kund (Scenic ancient 16th-century geometric stepwell with interlocking stairs)",
            "💎 Galta Ji Temple (The historic sacred Monkey Temple nestled in a dramatic mountain pass)",
            "💎 Anokhi Museum (Dedicated to preserving the traditional art of hand-block printing)"
        ),
        food = listOf(
            "🍛 Pyaaz Kachori (Famous crisp, golden onion-stuffed pastries at Rawat Mishtan Bhandar)",
            "🍛 Dal Baati Churma (Baked wheat balls served with spiced lentil curry & sweetened crushed wheat)",
            "🍛 Royal Laal Maas (A slow-cooked, rich and fiery lamb curry prepared with Mathania chilies)",
            "🍛 Lassiwala Lassi (Creamy, thick sweet yogurt served in traditional earthen clay cups)"
        ),
        culture = listOf(
            "🎭 Kathputli Puppetry (Ancient Rajasthani string puppet theater showcasing historical legends)",
            "🎨 Hand-Block Printing (Centuries-old indigo & vegetable dye printing using carved wooden blocks)",
            "🏺 Blue Pottery (A famous Persian-influenced pottery craft using quartz stone instead of clay)",
            "💍 Meenakari & Kundan (Intricate gemstone enameling and traditional gold-setting artwork)"
        ),
        festivals = listOf(
            "🏮 Teej Festival (Monsoon celebration honoring Goddess Parvati with colorful processions and swings)",
            "🏮 Gangaur Festival (Spring festival celebrating marital fidelity, adorned in royal, traditional garments)",
            "🏮 Kite Festival (Makar Sankranti sky-filled celebration with kite battles and lanterns)",
            "🏮 Jaipur Literature Festival (JLF - The world's largest free literary festival hosting global minds)"
        ),
        mapCoordinates = "📍 Latitude: 26.9124° N, Longitude: 75.7873° E\n• The historic Pink City, capital of Rajasthan, India.\n• Part of the popular Golden Triangle tourist circuit.",
        nearbyAttractions = listOf(
            "🏰 Amer Fort (Stunning hilltop fortress with intricate mirror palaces - 11 km)",
            "🏰 Nahargarh Fort (Hilltop fort offering panoramic sunset views of the pink city - 15 km)",
            "🌾 Chokhi Dhani (An ethnic mock-village resort showcasing traditional Rajasthani dance, food & arts - 20 km)",
            "🔭 Jantar Mantar (A UNESCO World Heritage site featuring the world's largest stone sundial - 1 km)"
        )
    ),
    "Goa" to CityDetail(
        name = "GOA",
        rating = 5,
        imageRes = R.drawable.img_goa_1783147598633,
        hiddenGems = listOf(
            "💎 Netravali Bubbling Lake (A mysterious volcanic spring lake that bubbles upon clapping)",
            "💎 Harvalem Cave Ruins (Ancient 6th-century rock-cut caves surrounded by historic legends)",
            "💎 Butterfly Beach (A secluded, tranquil cove accessible only by water boat or jungle trek)"
        ),
        food = listOf(
            "🍛 Spicy Goan Fish Curry (Traditional cod/pomfret simmered in hot coconut-coriander gravy)",
            "🍛 Bebinca (A rich, Portuguese-influenced 7-to-16 layered traditional warm egg dessert)",
            "🍛 Pork Vindaloo (Fiery local dish marinated in garlic, wine vinegar, and spicy Kashmiri chilies)",
            "🍛 Cashew Feni (Goa's official geographical indication traditional distilled cashew beverage)"
        ),
        culture = listOf(
            "💃 Fugdi & Dhalo Folk Dances (Vibrant, joyous traditional Konkani women's circle dance steps)",
            "🏡 Indo-Portuguese Architecture (Stunning 17th-century pastel mansions with oyster-shell windows)",
            "🏺 Terracotta Clay Pottery (Traditional Goan red-clay handcrafted utensils and water pots)"
        ),
        festivals = listOf(
            "🏮 Shigmo Festival (The spectacular Goan folk spring festival filled with colorful temple floats)",
            "🏮 Goa Carnival (A legendary Portuguese-origin street parade celebrating music, food and dance)",
            "🏮 Feast of St. Francis Xavier (Deeply religious annual pilgrimage honoring the saint's remains)"
        ),
        mapCoordinates = "📍 Latitude: 15.2993° N, Longitude: 73.8110° E\n• Scenic southwestern coastal strip overlooking the clear Arabian Sea.",
        nearbyAttractions = listOf(
            "🌊 Dudhsagar Waterfalls (A majestic four-tiered milk-like waterfall deep in Bhagwan Mahavir sanctuary - 60 km)",
            "🛍️ Anjuna Flea Market (A historic beachfront market for local handicrafts, clothes & jewelry - 8 km)",
            "🎨 Fontainhas Latin Quarter (The gorgeous, colorful heritage precinct of Panaji reflecting colonial history - 2 km)"
        )
    ),
    "Varanasi" to CityDetail(
        name = "VARANASI",
        rating = 5,
        imageRes = R.drawable.img_varanasi_1783147611733,
        hiddenGems = listOf(
            "💎 Lalita Ghat Wooden Temple (Intricately carved Nepalese pagoda-style wooden temple)",
            "💎 Sarnath Deer Park Ruins (Where Lord Buddha delivered his historic first sermon)",
            "💎 Chunar Fort (Ancient sandstone citadel overlooking the winding Ganges river)"
        ),
        food = listOf(
            "🍛 Tamatar Chaat (Spicy, tangy mashed tomato delicacy prepared with ghee and sugar syrup)",
            "🍛 Banarasi Sweet Paan (The world-famous refreshing betel leaf treat with sweet spices)",
            "🍛 Malaiyyo (A delicate, winter-special saffron-flavored milk froth garnished with pistachios)",
            "🍛 Kachori Sabzi (Deep-fried lentil-stuffed flatbread served with hot, spicy potato curry)"
        ),
        culture = listOf(
            "🕯️ Ganga Aarti (The spectacular daily evening oil lamp offering ritual performed on the ghats)",
            "🧵 Banarasi Silk Weaving (Masterful gold and silver brocade handloom sarees passed through generations)",
            "📖 Sanskrit & Vedic Studies (Ancient visual ashrams and universities teaching traditional scriptures)",
            "🎵 Dhrupad Music Heritage (Classical Hindustani vocal & instrumental spiritual sound systems)"
        ),
        festivals = listOf(
            "🏮 Dev Deepawali (The stunning 'Festival of Gods' where millions of clay diyas light up all 84 ghats)",
            "🏮 Maha Shivratri (Grand celebration with spiritual processions honoring Lord Shiva's holy marriage)",
            "🏮 Ganga Mahotsav (Five-day cultural festival showcasing classical Indian classical dances)"
        ),
        mapCoordinates = "📍 Latitude: 25.3176° N, Longitude: 82.9739° E\n• Located on the crescent-shaped banks of the sacred Ganges river in Uttar Pradesh.",
        nearbyAttractions = listOf(
            "🏯 Sarnath Buddhist Stupa (Ancient monumental ruins and museum housing India's Ashoka Lion Capital - 10 km)",
            "🏰 Ramnagar Fort (The 18th-century cream-sandstone royal palace on the eastern bank of Ganges - 14 km)",
            "🔱 Kashi Vishwanath Temple (One of the most sacred golden-spired temples dedicated to Lord Shiva - 0.5 km)"
        )
    ),
    "Kerala" to CityDetail(
        name = "KERALA",
        rating = 5,
        imageRes = R.drawable.img_kerala_1783147625772,
        hiddenGems = listOf(
            "💎 Vagamon Pine Forests (Secluded, peaceful rolling pine valleys and misty green hilltops)",
            "💎 Muzhappilangad Beach (The longest sandy drive-in beach in Asia with gentle sea breezes)",
            "💎 Gavi Eco-tourism (A pristine, undisturbed tropical evergreen forest reserve filled with wildlife)"
        ),
        food = listOf(
            "🍛 Appam with Vegetable Stew (Soft, lacy rice-fermented pancakes served with rich spiced coconut stew)",
            "🍛 Karimeen Pollichathu (Pearl spot fish marinated in spicy local herbs, baked inside a banana leaf)",
            "🍛 Malabar Parotta with Curry (Crispy, layered flaky flatbread served with spicy coconut gravy)",
            "🍛 Traditional Sadya (A grand vegetarian feast of 24+ dishes served on a green banana leaf)"
        ),
        culture = listOf(
            "🎭 Kathakali (Highly stylized classical dance-drama known for heavy makeup and expressive gestures)",
            "⚔️ Kalaripayattu (One of the oldest martial art forms in the world, combining moves and weapon arts)",
            "🥥 Coir Hand-Weaving (Traditional village art of spinning coconut husk fiber into durable ropes & mats)",
            "🌿 Ayurvedic Medicine (Ancient herbal wellness, holistic body oil massage and detoxification traditions)"
        ),
        festivals = listOf(
            "🏮 Onam (The spectacular national harvest festival featuring grand feasts and traditional snake boat races)",
            "🏮 Thrissur Pooram (The most magnificent temple festival showcasing decorated elephants and percussion drums)",
            "🏮 Nehru Trophy Boat Race (Exciting, high-energy synchronized rowing competition on Punnamada Lake)"
        ),
        mapCoordinates = "📍 Latitude: 10.8505° N, Longitude: 76.2711° E\n• Nestled between the Lakshadweep Sea and the lush Western Ghats.",
        nearbyAttractions = listOf(
            "⛰️ Munnar Tea Gardens (Breathtaking misty green mountain valleys carpeted with emerald tea shrubs - 110 km)",
            "🚣 Alleppey Houseboat Canals (Gliding through palm-fringed backwater channels and local villages - 0 km)",
            "🐅 Wayanad Sanctuary (Scenic hill station hosting tigers, elephants, and ancient prehistoric caves - 150 km)"
        )
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityDetailDashboard(city: String, viewModel: CultureVerseViewModel, onClear: () -> Unit) {
    val detail = CITY_DETAILS[city] ?: return
    val categories = listOf(
        Triple("Hidden Gems", "💎", detail.hiddenGems),
        Triple("Food", "🍲", detail.food),
        Triple("Culture", "🏺", detail.culture),
        Triple("Festivals", "🏮", detail.festivals),
        Triple("Map", "🗺️", listOf(detail.mapCoordinates)),
        Triple("Nearby Attractions", "📍", detail.nearbyAttractions)
    )

    var userRating by remember { mutableIntStateOf(detail.rating) }
    var ratingFeedback by remember { mutableStateOf("") }
    
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var showTripPlannerDialog by remember { mutableStateOf(false) }
    
    // Planner input states
    var daysCount by remember { mutableIntStateOf(3) }
    var selectedInterest by remember { mutableStateOf("Architecture & History") }
    var selectedBudgetPlan by remember { mutableStateOf("$$ Moderate") }
    var isPlanningTrip by remember { mutableStateOf(false) }
    var planSuccessMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
            .testTag("city_dashboard_${city}")
    ) {
        // Hero Card with Image & Title
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = detail.imageRes),
                    contentDescription = detail.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Gradient scrim
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f))
                            )
                        )
                )
                
                // Content over hero
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = detail.name,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        fontFamily = FontFamily.Serif,
                        letterSpacing = 2.sp
                    )
                    
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 4.dp)
                    ) {
                        repeat(5) { index ->
                            val starNum = index + 1
                            Text(
                                text = if (starNum <= userRating) "⭐" else "☆",
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .clickable {
                                        userRating = starNum
                                        ratingFeedback = "Thank you! You rated ${detail.name} $starNum stars."
                                    }
                                    .padding(end = 4.dp)
                            )
                        }
                        Text(
                            text = " (5.0 based on local guides)",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.8f),
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                
                // Back/Close Button
                IconButton(
                    onClick = onClear,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(Color.Black.copy(alpha = 0.5f), CircleShape)
                        .size(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Go back",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
        
        if (ratingFeedback.isNotEmpty()) {
            Text(
                text = ratingFeedback,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Badge Status Banner
        val unlockedBadges by viewModel.unlockedBadges.collectAsState()
        val badgeId = when(city) {
            "Jaipur" -> "Jaipur_Cultural"
            "Varanasi" -> "Varanasi_Spiritual"
            "Goa" -> "Goa_Beach"
            else -> null
        }
        if (badgeId != null) {
            val isBadgeEarned = unlockedBadges.contains(badgeId)
            val badgeTitle = when(city) {
                "Jaipur" -> "Cultural Badge"
                "Varanasi" -> "Spiritual Badge"
                "Goa" -> "Beach Explorer"
                else -> ""
            }
            val badgeEmoji = when(city) {
                "Jaipur" -> "👑"
                "Varanasi" -> "🪔"
                "Goa" -> "🌊"
                else -> "🏆"
            }
            val badgeColor = when(city) {
                "Jaipur" -> Color(0xFFFF9800)
                "Varanasi" -> Color(0xFFE91E63)
                "Goa" -> Color(0xFF03A9F4)
                else -> MaterialTheme.colorScheme.primary
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .clickable { viewModel.toggleBadge(badgeId) }
                    .testTag("city_badge_banner_${city}"),
                colors = CardDefaults.cardColors(
                    containerColor = if (isBadgeEarned) badgeColor.copy(alpha = 0.08f) else Color.Gray.copy(alpha = 0.05f)
                ),
                border = androidx.compose.foundation.BorderStroke(
                    width = 1.dp,
                    color = if (isBadgeEarned) badgeColor.copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.2f)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(if (isBadgeEarned) badgeColor.copy(alpha = 0.2f) else Color.Gray.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = badgeEmoji, fontSize = 20.sp, modifier = Modifier.alpha(if (isBadgeEarned) 1f else 0.4f))
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = badgeTitle,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isBadgeEarned) badgeColor else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (isBadgeEarned) "✓ Verified explorer badge active!" else "○ Tap to unlock this explorer badge",
                            fontSize = 11.sp,
                            color = Color.Gray
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isBadgeEarned) badgeColor.copy(alpha = 0.15f) else Color.Gray.copy(alpha = 0.1f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = if (isBadgeEarned) "ACTIVE" else "LOCKED",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isBadgeEarned) badgeColor else Color.Gray
                        )
                    }
                }
            }
        }

        // Direct Call To Action: Generate AI Trip
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showTripPlannerDialog = true }
                .testTag("generate_ai_trip_card"),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
            ),
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🚀", fontSize = 24.sp)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Generate AI Trip",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Let Gemini AI build a custom, immersive travel itinerary for ${detail.name} instantly.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Explore & Learn",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface,
            fontFamily = FontFamily.Serif
        )
        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            for (i in categories.indices step 2) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Item 1
                    val (title1, emoji1, list1) = categories[i]
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .height(85.dp)
                            .clickable { selectedCategory = title1 }
                            .testTag("explore_cat_${title1.replace(" ", "_")}"),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(emoji1, fontSize = 20.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = title1,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Discover ${list1.size} items",
                                fontSize = 10.sp,
                                color = Color.Gray,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Item 2
                    if (i + 1 < categories.size) {
                        val (title2, emoji2, list2) = categories[i + 1]
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(85.dp)
                                .clickable { selectedCategory = title2 }
                                .testTag("explore_cat_${title2.replace(" ", "_")}"),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(emoji2, fontSize = 20.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = title2,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = "Discover ${list2.size} items",
                                    fontSize = 10.sp,
                                    color = Color.Gray,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    } else {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }

    // Modal Dialog for Category Details
    selectedCategory?.let { category ->
        if (category == "Map") {
            Dialog(onDismissRequest = { selectedCategory = null }) {
                InteractiveHiddenGemsMap(
                    city = city,
                    viewModel = viewModel,
                    onDismiss = { selectedCategory = null }
                )
            }
        } else if (category == "Food") {
            Dialog(onDismissRequest = { selectedCategory = null }) {
                LocalCuisineExplorer(
                    city = city,
                    onDismiss = { selectedCategory = null }
                )
            }
        } else {
            val matchingData = categories.first { it.first == category }
            Dialog(onDismissRequest = { selectedCategory = null }) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surface,
                    tonalElevation = 8.dp
                ) {
                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(matchingData.second, fontSize = 28.sp)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = category,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            IconButton(onClick = { selectedCategory = null }) {
                                Icon(Icons.Default.Close, contentDescription = "Close popup")
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))

                        for (textItem in matchingData.third) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 6.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Text(
                                    text = "•",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    text = textItem,
                                    fontSize = 14.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Button(
                            onClick = { selectedCategory = null },
                            modifier = Modifier.align(Alignment.End),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Done", color = Color.White)
                        }
                    }
                }
            }
        }
    }

    // Modal Dialog for Planning AI Trip
    if (showTripPlannerDialog) {
        Dialog(onDismissRequest = { if (!isPlanningTrip) showTripPlannerDialog = false }) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        text = "Plan AI Trip to ${detail.name} 🚀",
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Customize your immersive cultural adventure and save it to your Planner database.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))

                    if (isPlanningTrip) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Crafting your custom ${daysCount}-day itinerary...",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    } else if (planSuccessMessage.isNotEmpty()) {
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🎉", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Trip Successfully Created!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = planSuccessMessage,
                                textAlign = TextAlign.Center,
                                fontSize = 13.sp,
                                color = Color.Gray
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    planSuccessMessage = ""
                                    showTripPlannerDialog = false
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Great! View in Planner tab")
                            }
                        }
                    } else {
                        // Days Selector
                        Text("Duration (Days): $daysCount", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf(2, 3, 5, 7).forEach { day ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (daysCount == day) MaterialTheme.colorScheme.primary
                                            else MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                        )
                                        .clickable { daysCount = day }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "$day Days",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (daysCount == day) Color.White else MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Interest Selector
                        Text("Focus Area:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        val interestsOpts = listOf("Architecture & History", "Culinary", "Traditional Arts", "Spiritual Heritage")
                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            interestsOpts.forEach { interest ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { selectedInterest = interest }
                                        .padding(vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(16.dp)
                                            .clip(CircleShape)
                                            .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                            .background(
                                                if (selectedInterest == interest) MaterialTheme.colorScheme.primary
                                                else Color.Transparent
                                            )
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(interest, fontSize = 13.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Budget Selector
                        Text("Budget Plan:", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("$ Economy", "$$ Moderate", "$$$ Luxury").forEach { budget ->
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (selectedBudgetPlan == budget) MaterialTheme.colorScheme.primary
                                            else MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                        )
                                        .clickable { selectedBudgetPlan = budget }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = budget,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = if (selectedBudgetPlan == budget) Color.White else MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            OutlinedButton(
                                onClick = { showTripPlannerDialog = false },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Text("Cancel")
                            }
                            Button(
                                onClick = {
                                    isPlanningTrip = true
                                    viewModel.generateCulturalItinerary(
                                        destination = detail.name,
                                        days = daysCount,
                                        budget = selectedBudgetPlan,
                                        interests = selectedInterest,
                                        startDate = "2026-08-10",
                                        onComplete = { success ->
                                            isPlanningTrip = false
                                            if (success) {
                                                planSuccessMessage = "${daysCount}-day $selectedInterest trip to ${detail.name} successfully created!"
                                            } else {
                                                showTripPlannerDialog = false
                                            }
                                        }
                                    )
                                }
                            ) {
                                Text("Generate Trip", color = Color.White)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExploreScreen(viewModel: CultureVerseViewModel, onOpenDrawer: () -> Unit = {}) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredHeritages = PRESET_HERITAGES.filter {
        it.title.contains(searchQuery, ignoreCase = true) || it.location.contains(searchQuery, ignoreCase = true)
    }
    val filteredFestivals = PRESET_FESTIVALS.filter {
        it.title.contains(searchQuery, ignoreCase = true) || it.location.contains(searchQuery, ignoreCase = true)
    }
    val filteredFoods = PRESET_FOODS.filter {
        it.title.contains(searchQuery, ignoreCase = true) || it.location.contains(searchQuery, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.03f))
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onOpenDrawer,
                    modifier = Modifier.testTag("explore_drawer_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Open Drawer",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                Text(
                    text = "CultureVerse AI",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f),
                    fontFamily = FontFamily.Serif
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Search Destination",
                fontSize = 24.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = searchQuery,
                onValueChange = { 
                    searchQuery = it 
                    if (it.trim().length > 3) {
                        viewModel.addSearchQuery("Searched: $it")
                    }
                },
                placeholder = { Text("Search countries, sights, food...") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Close, contentDescription = "Clear search")
                        }
                    } else {
                        Icon(Icons.Default.Search, contentDescription = "Search icon")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("search_bar"),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
                ),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Trending",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val trendingDestinations = listOf("Jaipur", "Goa", "Varanasi", "Kerala")
                trendingDestinations.forEach { city ->
                    val isSelected = searchQuery.equals(city, ignoreCase = true)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                            )
                            .clickable {
                                searchQuery = city
                                selectedTab = 0 // Heritages
                                viewModel.addSearchQuery("Searched: $city")
                            }
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                            .testTag("trending_chip_$city")
                    ) {
                        Text(
                            text = "📍 $city",
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        val matchingCity = listOf("Jaipur", "Goa", "Varanasi", "Kerala").firstOrNull {
            it.equals(searchQuery.trim(), ignoreCase = true)
        }

        if (matchingCity != null) {
            Box(modifier = Modifier.weight(1f)) {
                CityDetailDashboard(
                    city = matchingCity,
                    viewModel = viewModel,
                    onClear = { searchQuery = "" }
                )
            }
        } else {
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("🏯 Sacred Sites", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("🏮 Festivals", fontWeight = FontWeight.Bold) }
                )
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    text = { Text("🍜 Cuisine", fontWeight = FontWeight.Bold) }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                val itemsToShow = when (selectedTab) {
                    0 -> filteredHeritages
                    1 -> filteredFestivals
                    else -> filteredFoods
                }

                if (itemsToShow.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("🏜️", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No heritages found matching '$searchQuery'",
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    items(itemsToShow) { item ->
                        CulturalCard(item = item, viewModel = viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CulturalCard(item: CulturalItem, viewModel: CultureVerseViewModel) {
    val favoritedList by viewModel.favouritePlaces.collectAsState()
    val isFavorited = favoritedList.contains(item.title)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("cultural_card_${item.title.replace(" ", "_")}"),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                
                // Bookmark heart overlay
                IconButton(
                    onClick = { viewModel.toggleFavoritePlace(item.title) },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(Color.White.copy(alpha = 0.85f), CircleShape)
                        .size(36.dp)
                        .testTag("favorite_${item.title.replace(" ", "_")}")
                ) {
                    Icon(
                        imageVector = if (isFavorited) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Favorite bookmark",
                        tint = if (isFavorited) Color.Red else Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(item.customTag, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "📍 " + item.location,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.padding(vertical = 2.dp)
                )
                Text(
                    text = item.description,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    modifier = Modifier.padding(vertical = 4.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f))
                        .border(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("🤝", fontSize = 16.sp)
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Respectful Local Etiquette",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.etiquette,
                            fontSize = 12.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// PLANNER SCREEN (Itinerary Builder with Room)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlannerScreen(viewModel: CultureVerseViewModel, onOpenDrawer: () -> Unit = {}) {
    val itineraries by viewModel.itineraries.collectAsState()
    val isGenerating by viewModel.isGeneratingItinerary.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }
    var selectedItineraryForDetail by remember { mutableStateOf<Itinerary?>(null) }

    var destInput by remember { mutableStateOf("") }
    var daysInput by remember { mutableStateOf("3") }
    var selectedFocus by remember { mutableStateOf("Culinary") }
    var selectedBudget by remember { mutableStateOf("$$ Moderate") }
    var startInput by remember { mutableStateOf("2026-08-10") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cultural Itinerary Planner", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer, modifier = Modifier.testTag("planner_drawer_button")) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Open navigation drawer", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                modifier = Modifier.testTag("add_itinerary_fab")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create travel itinerary")
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (isGenerating) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Consulting local guides...\nGemini AI is crafting your cultural itinerary!",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else if (itineraries.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🗺️", fontSize = 64.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No Cultural Trips Planned",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap the '+' button below to request an AI-crafted cultural itinerary containing historical milestones and sacred behavior etiquettes.",
                            textAlign = TextAlign.Center,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(itineraries) { trip ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedItineraryForDetail = trip }
                                    .testTag("itinerary_item_${trip.id}"),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            when (trip.interests) {
                                                "Culinary" -> "🍛"
                                                "Traditional Arts" -> "🏺"
                                                "Architecture & History" -> "🏯"
                                                else -> "⛩️"
                                            },
                                            fontSize = 24.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = trip.destination,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 18.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                        Text(
                                            text = "${trip.days} Days • Budget: ${trip.budget}",
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.SemiBold,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                        Text(
                                            text = "Interests: ${trip.interests}",
                                            fontSize = 12.sp,
                                            color = Color.Gray
                                        )
                                        // Dynamic formatted timestamp
                                        val formattedDate = try {
                                            java.text.SimpleDateFormat("MMM dd, yyyy HH:mm", java.util.Locale.getDefault()).format(java.util.Date(trip.timestamp))
                                        } catch (e: Exception) {
                                            "Recent"
                                        }
                                        Text(
                                            text = "Planned at: $formattedDate",
                                            fontSize = 10.sp,
                                            color = Color.Gray.copy(alpha = 0.7f)
                                        )
                                    }

                                    IconButton(
                                        onClick = { viewModel.deleteItinerary(trip.id) },
                                        modifier = Modifier.testTag("delete_itinerary_${trip.id}")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete itinerary",
                                            tint = Color.Red.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // DIALOG: CREATE NEW ITINERARY
                if (showCreateDialog) {
                    Dialog(onDismissRequest = { showCreateDialog = false }) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text(
                                    text = "Plan Cultural Itinerary",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = FontFamily.Serif
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = destInput,
                                    onValueChange = { destInput = it },
                                    label = { Text("Destination (e.g., Kyoto, Japan)") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("dialog_destination_input"),
                                    singleLine = true
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = daysInput,
                                    onValueChange = { daysInput = it },
                                    label = { Text("Trip Duration (Days)") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("dialog_duration_input"),
                                    singleLine = true
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = startInput,
                                    onValueChange = { startInput = it },
                                    label = { Text("Start Date (YYYY-MM-DD)") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("dialog_date_input"),
                                    singleLine = true
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text("Select Cultural Focus:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    val focusPresets = listOf("Culinary", "Traditional Arts", "Architecture & History")
                                    focusPresets.forEach { item ->
                                        val isSelected = selectedFocus == item
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(
                                                    if (isSelected) MaterialTheme.colorScheme.primary
                                                    else MaterialTheme.colorScheme.surfaceVariant
                                                )
                                                .clickable { selectedFocus = item }
                                                .padding(6.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                item,
                                                fontSize = 11.sp,
                                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                                textAlign = TextAlign.Center,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                Text("Select Trip Budget:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    val budgetPresets = listOf("$ Economy", "$$ Moderate", "$$$ Luxury")
                                    budgetPresets.forEach { item ->
                                        val isSelected = selectedBudget == item
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(
                                                    if (isSelected) MaterialTheme.colorScheme.primary
                                                    else MaterialTheme.colorScheme.surfaceVariant
                                                )
                                                .clickable { selectedBudget = item }
                                                .padding(6.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                item,
                                                fontSize = 11.sp,
                                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                                textAlign = TextAlign.Center,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    OutlinedButton(
                                        onClick = { showCreateDialog = false },
                                        modifier = Modifier.testTag("dialog_cancel")
                                    ) {
                                        Text("Cancel")
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            showCreateDialog = false
                                            viewModel.generateCulturalItinerary(
                                                destination = destInput,
                                                days = daysInput.toIntOrNull() ?: 3,
                                                budget = selectedBudget,
                                                interests = selectedFocus,
                                                startDate = startInput,
                                                onComplete = {}
                                            )
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                        modifier = Modifier.testTag("dialog_generate_button")
                                    ) {
                                        Text("Ask AI to Plan", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }

                // SHEET/DIALOG: ITINERARY DETAILS
                selectedItineraryForDetail?.let { trip ->
                    Dialog(onDismissRequest = { selectedItineraryForDetail = null }) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.85f)
                                .padding(vertical = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = trip.destination,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontFamily = FontFamily.Serif
                                    )
                                    IconButton(onClick = { selectedItineraryForDetail = null }) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = "Close detailed view")
                                    }
                                }

                                Text(
                                    text = "Interests: ${trip.interests} • Budget: ${trip.budget}",
                                    fontSize = 12.sp,
                                    color = Color.Gray
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                val daysList = parseItineraryJson(trip.activitiesJson)

                                LazyColumn(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    items(daysList) { dayObj ->
                                        Column {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(8.dp))
                                                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f))
                                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                                            ) {
                                                Text(
                                                    "Day ${dayObj.dayNumber}: ${dayObj.theme}",
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(8.dp))

                                            dayObj.activities.forEach { act ->
                                                Card(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(bottom = 8.dp),
                                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                                ) {
                                                    Column(modifier = Modifier.padding(12.dp)) {
                                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                                            Text(
                                                                text = "⏰ " + act.time,
                                                                fontWeight = FontWeight.Bold,
                                                                fontSize = 12.sp,
                                                                color = MaterialTheme.colorScheme.primary
                                                            )
                                                            Spacer(modifier = Modifier.width(8.dp))
                                                            Text(
                                                                text = act.title,
                                                                fontWeight = FontWeight.Bold,
                                                                fontSize = 14.sp
                                                            )
                                                        }
                                                        Spacer(modifier = Modifier.height(4.dp))
                                                        Text(
                                                            text = act.description,
                                                            fontSize = 12.sp,
                                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                                        )
                                                        
                                                        if (act.etiquette.isNotEmpty()) {
                                                            Spacer(modifier = Modifier.height(6.dp))
                                                            Row(
                                                                modifier = Modifier
                                                                    .fillMaxWidth()
                                                                    .clip(RoundedCornerShape(4.dp))
                                                                    .background(Color(0xFFFEF3C7))
                                                                    .padding(6.dp),
                                                                verticalAlignment = Alignment.CenterVertically
                                                            ) {
                                                                Text("🤝 ", fontSize = 11.sp)
                                                                Text(
                                                                    text = "Etiquette: " + act.etiquette,
                                                                    fontSize = 11.sp,
                                                                    color = Color(0xFF92400E),
                                                                    fontWeight = FontWeight.SemiBold
                                                                )
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

// Model helper classes for JSON parsing
data class ParsedDay(val dayNumber: Int, val theme: String, val activities: List<ParsedActivity>)
data class ParsedActivity(val time: String, val title: String, val description: String, val etiquette: String)

fun parseItineraryJson(jsonStr: String): List<ParsedDay> {
    val list = mutableListOf<ParsedDay>()
    try {
        val root = JSONObject(jsonStr)
        val daysArray = root.optJSONArray("days") ?: JSONArray()
        for (i in 0 until daysArray.length()) {
            val dayObj = daysArray.getJSONObject(i)
            val dayNum = dayObj.optInt("day", i + 1)
            val theme = dayObj.optString("theme", "Explore Day")
            val actArray = dayObj.optJSONArray("activities") ?: JSONArray()
            val actsList = mutableListOf<ParsedActivity>()
            for (j in 0 until actArray.length()) {
                val actObj = actArray.getJSONObject(j)
                actsList.add(
                    ParsedActivity(
                        time = actObj.optString("time", "All Day"),
                        title = actObj.optString("title", "Sights Visit"),
                        description = actObj.optString("description", ""),
                        etiquette = actObj.optString("etiquette", "")
                    )
                )
            }
            list.add(ParsedDay(dayNum, theme, actsList))
        }
    } catch (e: Exception) {
        list.add(ParsedDay(1, "Scenic Day", listOf(ParsedActivity("Morning", "Explore Heritage Landmarks", "Walk around town to learn historical milestones.", "Speak softly inside shrines."))))
    }
    return list
}

// ==========================================
// CHAT SCREEN (AI Chatbot)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(viewModel: CultureVerseViewModel, onOpenDrawer: () -> Unit = {}) {
    val messages by viewModel.chatMessages.collectAsState()
    val isChatLoading by viewModel.isChatLoading.collectAsState()
    var inputMessage by remember { mutableStateOf("") }

    val presetPrompts = listOf(
        "Japan Etiquettes",
        "Italy Pasta Rules",
        "Diwali Celebration Details",
        "Polite Greetings in French"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = onOpenDrawer, modifier = Modifier.testTag("chat_drawer_button")) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = "Open navigation drawer", tint = Color.White)
                }
            },
            title = {
                Column {
                    Text("CultureVerse AI Guide", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                    Text(
                        text = if (isChatLoading) "AI is typing custom insights..." else "Ready to help with global manners",
                        fontSize = 11.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            },
            actions = {
                if (viewModel.isApiKeyValid) {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFD1FAE5))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Gemini Live", color = Color(0xFF065F46), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                } else {
                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFFFEF3C7))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text("Local Mode", color = Color(0xFF92400E), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages) { msg ->
                ChatBubble(message = msg)
            }

            if (isChatLoading) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 2.5.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            "AI guide is researching heritages...",
                            fontSize = 13.sp,
                            color = Color.Gray,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }

        if (messages.size <= 2) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)) {
                Text(
                    "Suggested Cultural Queries:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(presetPrompts) { prompt ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                                .clickable {
                                    inputMessage = "Tell me about $prompt"
                                }
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(prompt, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
                        }
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputMessage,
                onValueChange = { inputMessage = it },
                placeholder = { Text("Ask about customs, greetings, recipes...") },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input"),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
                ),
                maxLines = 3
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary)
                    .clickable {
                        if (inputMessage.trim().isNotEmpty()) {
                            viewModel.sendMessage(inputMessage)
                            inputMessage = ""
                        }
                    }
                    .testTag("send_button"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = "Send message",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleColor = if (message.isUser) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
    val align = if (message.isUser) Alignment.End else Alignment.Start
    val textColor = if (message.isUser) Color.White else MaterialTheme.colorScheme.onSurface

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = align
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (message.isUser) 16.dp else 4.dp,
                        bottomEnd = if (message.isUser) 4.dp else 16.dp
                    )
                )
                .background(bubbleColor)
                .padding(12.dp)
                .widthIn(max = 280.dp)
        ) {
            Text(
                text = message.text,
                fontSize = 14.sp,
                color = textColor,
                lineHeight = 20.sp
            )
        }
    }
}

// ==========================================
// JOURNAL SCREEN (Cultural Journal & AI Reflection with Room)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JournalScreen(viewModel: CultureVerseViewModel, onOpenDrawer: () -> Unit = {}) {
    val journals by viewModel.journals.collectAsState()
    val isSaving by viewModel.isSavingJournal.collectAsState()

    var showCreateDialog by remember { mutableStateOf(false) }
    var selectedJournalDetail by remember { mutableStateOf<JournalEntry?>(null) }

    var titleInput by remember { mutableStateOf("") }
    var locationInput by remember { mutableStateOf("") }
    var categorySelection by remember { mutableStateOf("Festival") }
    var contentInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Cultural Journal", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer, modifier = Modifier.testTag("journal_drawer_button")) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Open navigation drawer", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                modifier = Modifier.testTag("add_journal_fab")
            ) {
                Icon(Icons.Default.Add, contentDescription = "Create journal entry")
            }
        },
        content = { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                if (isSaving) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Inviting anthropologist AI...\nGenerating customized cultural heritage reflection!",
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else if (journals.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📓", fontSize = 64.sp)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No Cultural Journals Logged",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Tap '+' below to log an experience. Gemini AI will automatically add a respectful, anthropological analysis of the regional customs you observed!",
                            textAlign = TextAlign.Center,
                            color = Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(journals) { entry ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { selectedJournalDetail = entry }
                                    .testTag("journal_item_${entry.id}"),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                            ) {
                                Row(
                                    modifier = Modifier.padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(50.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            when (entry.category) {
                                                "Festival" -> "🏮"
                                                "Food" -> "🍜"
                                                "History" -> "🏺"
                                                "Reflection" -> "💭"
                                                else -> "✈️"
                                            },
                                            fontSize = 24.sp
                                        )
                                    }

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = entry.title,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.primary,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Text(
                                            text = "📍 " + entry.location,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                        Text(
                                            text = entry.content,
                                            fontSize = 12.sp,
                                            color = Color.Gray,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }

                                    IconButton(
                                        onClick = { viewModel.deleteJournal(entry.id) },
                                        modifier = Modifier.testTag("delete_journal_${entry.id}")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = "Delete journal entry",
                                            tint = Color.Red.copy(alpha = 0.7f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                if (showCreateDialog) {
                    Dialog(onDismissRequest = { showCreateDialog = false }) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Text(
                                    text = "Log Heritage Entry",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontFamily = FontFamily.Serif
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                OutlinedTextField(
                                    value = titleInput,
                                    onValueChange = { titleInput = it },
                                    label = { Text("Journal Title (e.g. Kyoto Tea Ceremony)") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("journal_title_input"),
                                    singleLine = true
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = locationInput,
                                    onValueChange = { locationInput = it },
                                    label = { Text("Location (e.g. Kyoto, Japan)") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .testTag("journal_location_input"),
                                    singleLine = true
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text("Select Category:", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    val cats = listOf("Festival", "Food", "History", "Reflection")
                                    cats.forEach { item ->
                                        val isSelected = categorySelection == item
                                        Box(
                                            modifier = Modifier
                                                .weight(1f)
                                                .clip(RoundedCornerShape(8.dp))
                                                .background(
                                                    if (isSelected) MaterialTheme.colorScheme.primary
                                                    else MaterialTheme.colorScheme.surfaceVariant
                                                )
                                                .clickable { categorySelection = item }
                                                .padding(6.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                item,
                                                fontSize = 11.sp,
                                                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                OutlinedTextField(
                                    value = contentInput,
                                    onValueChange = { contentInput = it },
                                    label = { Text("Describe your cultural observations...") },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
                                        .testTag("journal_content_input"),
                                    maxLines = 5
                                )

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    OutlinedButton(
                                        onClick = { showCreateDialog = false },
                                        modifier = Modifier.testTag("journal_cancel")
                                    ) {
                                        Text("Cancel")
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = {
                                            showCreateDialog = false
                                            viewModel.addJournalEntry(
                                                title = titleInput,
                                                content = contentInput,
                                                location = locationInput,
                                                category = categorySelection,
                                                onComplete = {
                                                    titleInput = ""
                                                    locationInput = ""
                                                    contentInput = ""
                                                }
                                            )
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                                        modifier = Modifier.testTag("journal_save_button")
                                    ) {
                                        Text("Save with AI", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                }

                selectedJournalDetail?.let { entry ->
                    Dialog(onDismissRequest = { selectedJournalDetail = null }) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.85f)
                                .padding(vertical = 16.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = entry.title,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontFamily = FontFamily.Serif
                                    )
                                    IconButton(onClick = { selectedJournalDetail = null }) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = "Close detailed view")
                                    }
                                }

                                Text(
                                    text = "📍 ${entry.location} • Category: ${entry.category}",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Text(
                                    text = "My Entry",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                                ) {
                                    Text(
                                        text = entry.content,
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(12.dp),
                                        lineHeight = 20.sp
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                Text(
                                    text = "✨ Gemini Anthropologist Reflection",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                                        .border(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f), RoundedCornerShape(8.dp))
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        text = entry.culturalReflection ?: "Analyzing local customs...",
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        lineHeight = 20.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileDialog(viewModel: CultureVerseViewModel, onDismiss: () -> Unit) {
    val username by viewModel.username.collectAsState()
    val email by viewModel.userEmail.collectAsState()
    val avatarIdx by viewModel.avatarIndex.collectAsState()
    val region by viewModel.favoriteRegion.collectAsState()
    val savedTripsList by viewModel.savedTrips.collectAsState()
    val favouritePlacesList by viewModel.favouritePlaces.collectAsState()
    val historyList by viewModel.history.collectAsState()

    var isEditMode by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(username) }
    var editEmail by remember { mutableStateOf(email) }
    var editAvatarIdx by remember { mutableIntStateOf(avatarIdx) }

    var selectedTabSection by remember { mutableStateOf("Trips") }
    var selectedProfileItineraryForDetail by remember { mutableStateOf<Itinerary?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .padding(vertical = 12.dp)
                .testTag("profile_dialog_card"),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (isEditMode) "Edit Profile" else "Explorer Profile",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily.Serif
                    )
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_profile_button")
                    ) {
                        Icon(Icons.Default.Close, contentDescription = "Close Profile")
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (isEditMode) {
                        // Editable Avatar Choice
                        Text(
                            text = "Select Heritage Emblem:",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            AVATAR_EMOJIS.forEachIndexed { idx, emoji ->
                                val isSelected = editAvatarIdx == idx
                                Box(
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                                            else MaterialTheme.colorScheme.surfaceVariant
                                        )
                                        .border(
                                            width = if (isSelected) 2.dp else 0.dp,
                                            color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                            shape = CircleShape
                                        )
                                        .clickable { editAvatarIdx = idx }
                                        .testTag("edit_avatar_$idx"),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(emoji, fontSize = 24.sp)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        OutlinedTextField(
                            value = editName,
                            onValueChange = { editName = it },
                            label = { Text("Name") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("edit_profile_name_input"),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        OutlinedTextField(
                            value = editEmail,
                            onValueChange = { editEmail = it },
                            label = { Text("Email") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("edit_profile_email_input"),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { isEditMode = false },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("cancel_edit_profile_button")
                            ) {
                                Text("Cancel")
                            }
                            Button(
                                onClick = {
                                    viewModel.updateProfile(editName, editEmail, editAvatarIdx, region)
                                    isEditMode = false
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("save_edit_profile_button"),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("Save", color = Color.White)
                            }
                        }
                    } else {
                        // View Profile Mode
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(AVATAR_EMOJIS.getOrElse(avatarIdx) { "🗺️" }, fontSize = 48.sp)
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = username,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.testTag("profile_username_text")
                        )

                        if (email.isNotEmpty()) {
                            Text(
                                text = email,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Gray,
                                modifier = Modifier.testTag("profile_email_text")
                            )
                        }

                        Text(
                            text = "Region Interest: $region",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(top = 4.dp)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Button(
                            onClick = {
                                editName = username
                                editEmail = email
                                editAvatarIdx = avatarIdx
                                isEditMode = true
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f), contentColor = MaterialTheme.colorScheme.primary),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("edit_profile_trigger_button")
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit Profile Icon", modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Edit Profile Details")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Spacer(modifier = Modifier.height(16.dp))

                        // Stats Summary Row (Tabs)
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.04f)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.12f))
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                // Trips Tab
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (selectedTabSection == "Trips") MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                            else Color.Transparent
                                        )
                                        .clickable { selectedTabSection = "Trips" }
                                        .padding(vertical = 8.dp, horizontal = 4.dp)
                                        .testTag("tab_trips")
                                ) {
                                    Text(
                                        text = "${savedTripsList.size}",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = if (selectedTabSection == "Trips") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Saved Trips",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = if (selectedTabSection == "Trips") FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedTabSection == "Trips") MaterialTheme.colorScheme.primary else Color.Gray,
                                        fontSize = 11.sp
                                    )
                                }

                                // Favorites Tab
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (selectedTabSection == "Favorites") MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                            else Color.Transparent
                                        )
                                        .clickable { selectedTabSection = "Favorites" }
                                        .padding(vertical = 8.dp, horizontal = 4.dp)
                                        .testTag("tab_favorites")
                                ) {
                                    Text(
                                        text = "${favouritePlacesList.size}",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = if (selectedTabSection == "Favorites") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "Favorites",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = if (selectedTabSection == "Favorites") FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedTabSection == "Favorites") MaterialTheme.colorScheme.primary else Color.Gray,
                                        fontSize = 11.sp
                                    )
                                }

                                // History Tab
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            if (selectedTabSection == "History") MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                                            else Color.Transparent
                                        )
                                        .clickable { selectedTabSection = "History" }
                                        .padding(vertical = 8.dp, horizontal = 4.dp)
                                        .testTag("tab_history")
                                ) {
                                    Text(
                                        text = "${historyList.size}",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleLarge,
                                        color = if (selectedTabSection == "History") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                    )
                                    Text(
                                        text = "History Logs",
                                        style = MaterialTheme.typography.bodySmall,
                                        fontWeight = if (selectedTabSection == "History") FontWeight.Bold else FontWeight.Normal,
                                        color = if (selectedTabSection == "History") MaterialTheme.colorScheme.primary else Color.Gray,
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Dynamic Tab Content Area
                        when (selectedTabSection) {
                            "Trips" -> {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "✈️ Saved Cultural Itineraries",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontFamily = FontFamily.Serif
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
                                    val itineraries by viewModel.itineraries.collectAsState()
                                    val userSavedTrips = itineraries.filter { it.tripId in savedTripsList }
                                    
                                    if (userSavedTrips.isEmpty()) {
                                        Box(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 24.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                Text("🗺️", fontSize = 32.sp)
                                                Spacer(modifier = Modifier.height(8.dp))
                                                Text(
                                                    text = "No saved itineraries found.",
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Color.Gray
                                                )
                                                Text(
                                                    text = "Generate and save a trip in the Planner tab!",
                                                    fontSize = 10.sp,
                                                    color = Color.Gray.copy(alpha = 0.8f)
                                                )
                                            }
                                        }
                                    } else {
                                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                            userSavedTrips.forEach { trip ->
                                                Card(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clickable { selectedProfileItineraryForDetail = trip }
                                                        .testTag("profile_trip_card_${trip.id}"),
                                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                                                    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.12f))
                                                ) {
                                                    Row(
                                                        modifier = Modifier.padding(12.dp),
                                                        verticalAlignment = Alignment.CenterVertically
                                                    ) {
                                                        Box(
                                                            modifier = Modifier
                                                                .size(40.dp)
                                                                .clip(RoundedCornerShape(8.dp))
                                                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
                                                            contentAlignment = Alignment.Center
                                                        ) {
                                                            Text(
                                                                when (trip.interests) {
                                                                    "Culinary" -> "🍛"
                                                                    "Traditional Arts" -> "🏺"
                                                                    "Architecture & History" -> "🏯"
                                                                    else -> "✈️"
                                                                },
                                                                fontSize = 20.sp
                                                            )
                                                        }
                                                        Spacer(modifier = Modifier.width(12.dp))
                                                        Column(modifier = Modifier.weight(1f)) {
                                                            Text(
                                                                text = trip.destination,
                                                                fontWeight = FontWeight.Bold,
                                                                fontSize = 14.sp,
                                                                color = MaterialTheme.colorScheme.onSurface
                                                            )
                                                            Text(
                                                                text = "${trip.days} Days • ${trip.budget}",
                                                                fontSize = 11.sp,
                                                                color = Color.Gray
                                                            )
                                                        }
                                                        
                                                        // View details button
                                                        IconButton(
                                                            onClick = { selectedProfileItineraryForDetail = trip },
                                                            modifier = Modifier.size(32.dp).testTag("view_profile_trip_${trip.id}")
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Default.Visibility,
                                                                contentDescription = "View Itinerary",
                                                                tint = MaterialTheme.colorScheme.primary,
                                                                modifier = Modifier.size(18.dp)
                                                            )
                                                        }
                                                        
                                                        Spacer(modifier = Modifier.width(4.dp))
                                                        
                                                        // Delete button
                                                        IconButton(
                                                            onClick = { viewModel.deleteItinerary(trip.id) },
                                                            modifier = Modifier.size(32.dp).testTag("delete_profile_trip_${trip.id}")
                                                        ) {
                                                            Icon(
                                                                imageVector = Icons.Default.Delete,
                                                                contentDescription = "Delete Itinerary",
                                                                tint = Color.Red.copy(alpha = 0.6f),
                                                                modifier = Modifier.size(18.dp)
                                                            )
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            "Favorites" -> {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "⭐ Favorite Sights",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontFamily = FontFamily.Serif
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    if (favouritePlacesList.isEmpty()) {
                                        Text(
                                            text = "No bookmarked sights yet. Explore and heart sites!",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray
                                        )
                                    } else {
                                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                            favouritePlacesList.forEach { place ->
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text("🏛️", fontSize = 16.sp)
                                                        Spacer(modifier = Modifier.width(8.dp))
                                                        Text(place, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold)
                                                    }
                                                    IconButton(
                                                        onClick = { viewModel.toggleFavoritePlace(place) },
                                                        modifier = Modifier
                                                            .size(32.dp)
                                                            .testTag("delete_favorite_$place")
                                                    ) {
                                                        Icon(Icons.Default.Delete, contentDescription = "Delete Favorite", tint = Color.Red.copy(alpha = 0.6f), modifier = Modifier.size(18.dp))
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            "History" -> {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "📜 Travel History Logs",
                                            fontWeight = FontWeight.Bold,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = MaterialTheme.colorScheme.primary,
                                            fontFamily = FontFamily.Serif
                                        )
                                        if (historyList.isNotEmpty()) {
                                            Text(
                                                text = "Clear All",
                                                style = MaterialTheme.typography.bodySmall,
                                                color = Color.Red.copy(alpha = 0.8f),
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier
                                                    .clickable { viewModel.clearSearchHistory() }
                                                    .padding(4.dp)
                                                    .testTag("clear_all_history_button")
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    if (historyList.isEmpty()) {
                                        Text(
                                            text = "No recent logs.",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray
                                        )
                                    } else {
                                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                            historyList.takeLast(10).reversed().forEach { logItem ->
                                                Row(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                                    horizontalArrangement = Arrangement.SpaceBetween,
                                                    verticalAlignment = Alignment.CenterVertically
                                                ) {
                                                    Text(
                                                        text = logItem,
                                                        style = MaterialTheme.typography.bodySmall,
                                                        fontSize = 11.sp,
                                                        color = Color.DarkGray,
                                                        modifier = Modifier.weight(1f)
                                                    )
                                                    IconButton(
                                                        onClick = { viewModel.deleteSearchHistory(logItem) },
                                                        modifier = Modifier.size(24.dp).testTag("delete_history_${logItem.hashCode()}")
                                                    ) {
                                                        Icon(
                                                            imageVector = Icons.Default.Close,
                                                            contentDescription = "Delete history item",
                                                            tint = Color.Gray,
                                                            modifier = Modifier.size(14.dp)
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // Logout Button
                        Button(
                            onClick = {
                                viewModel.logout()
                                onDismiss()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.08f), contentColor = Color.Red),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("logout_profile_button")
                        ) {
                            Icon(Icons.Default.ExitToApp, contentDescription = "Logout Icon", modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Reset & Logout Profile", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }

    selectedProfileItineraryForDetail?.let { trip ->
        Dialog(onDismissRequest = { selectedProfileItineraryForDetail = null }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.85f)
                    .padding(vertical = 16.dp)
                    .testTag("profile_itinerary_detail_dialog"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = trip.destination,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = FontFamily.Serif
                        )
                        IconButton(onClick = { selectedProfileItineraryForDetail = null }) {
                            Icon(Icons.Default.Close, contentDescription = "Close detailed view")
                        }
                    }

                    Text(
                        text = "Interests: ${trip.interests} • Budget: ${trip.budget}",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    val daysList = parseItineraryJson(trip.activitiesJson)

                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(daysList) { dayObj ->
                            Column {
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f))
                                        .padding(horizontal = 12.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        "Day ${dayObj.dayNumber}: ${dayObj.theme}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                dayObj.activities.forEach { act ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 8.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                                    ) {
                                        Column(modifier = Modifier.padding(12.dp)) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Text(
                                                    text = "⏰ " + act.time,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 12.sp,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Spacer(modifier = Modifier.width(8.dp))
                                                Text(
                                                    text = act.title,
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 14.sp
                                                )
                                            }

                                            Spacer(modifier = Modifier.height(6.dp))

                                            Text(
                                                text = act.description,
                                                fontSize = 13.sp,
                                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                            )

                                            if (act.etiquette.isNotEmpty()) {
                                                Spacer(modifier = Modifier.height(6.dp))
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clip(RoundedCornerShape(4.dp))
                                                        .background(MaterialTheme.colorScheme.error.copy(alpha = 0.05f))
                                                        .padding(8.dp)
                                                ) {
                                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                                        Text("💡", fontSize = 12.sp)
                                                        Spacer(modifier = Modifier.width(6.dp))
                                                        Text(
                                                            text = act.etiquette,
                                                            fontSize = 11.sp,
                                                            color = MaterialTheme.colorScheme.error,
                                                            fontWeight = FontWeight.Medium
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InteractiveHiddenGemsMap(
    city: String,
    viewModel: CultureVerseViewModel,
    onDismiss: () -> Unit
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val mapCityName by viewModel.mapCityName.collectAsState()
    val centerLat by viewModel.mapCenterLat.collectAsState()
    val centerLng by viewModel.mapCenterLng.collectAsState()
    val pins by viewModel.mapPins.collectAsState()
    val isMapLoading by viewModel.isMapLoading.collectAsState()
    val searchStep by viewModel.mapSearchStep.collectAsState()

    var showOnlyHiddenGems by remember { mutableStateOf(true) }
    var selectedPin by remember { mutableStateOf<MapPin?>(null) }
    var zoomScale by remember { mutableStateOf(2000.0) } 

    // Load map coordinates and places on mount
    androidx.compose.runtime.LaunchedEffect(city) {
        viewModel.loadHiddenGemsMap(city)
    }

    // Auto-select first hidden gem when loaded
    androidx.compose.runtime.LaunchedEffect(pins) {
        if (pins.isNotEmpty()) {
            selectedPin = pins.firstOrNull { it.isHiddenGem } ?: pins.firstOrNull()
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .padding(4.dp)
            .testTag("interactive_map_dialog"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "🗺️ Hidden Gems Map",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "Places API + Gemini Intelligence for $city",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_map_button")) {
                    Icon(Icons.Default.Close, contentDescription = "Close Map")
                }
            }

            // Progress Stepper Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.02f)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Step 1: Google Places API
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(
                                    if (searchStep >= 1) MaterialTheme.colorScheme.primary
                                    else Color.Gray.copy(alpha = 0.3f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (searchStep > 1) {
                                Text("✓", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            } else {
                                Text("1", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Places API",
                            fontSize = 11.sp,
                            fontWeight = if (searchStep == 1) FontWeight.Bold else FontWeight.Normal,
                            color = if (searchStep >= 1) MaterialTheme.colorScheme.primary else Color.Gray
                        )
                    }

                    // Line
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(Color.Gray.copy(alpha = 0.3f)).padding(horizontal = 4.dp))

                    // Step 2: Gemini Filtering
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(
                                    if (searchStep >= 2) MaterialTheme.colorScheme.secondary
                                    else Color.Gray.copy(alpha = 0.3f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (searchStep > 2) {
                                Text("✓", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            } else {
                                Text("2", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Gemini Filter",
                            fontSize = 11.sp,
                            fontWeight = if (searchStep == 2) FontWeight.Bold else FontWeight.Normal,
                            color = if (searchStep >= 2) MaterialTheme.colorScheme.secondary else Color.Gray
                        )
                    }

                    // Line
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(Color.Gray.copy(alpha = 0.3f)).padding(horizontal = 4.dp))

                    // Step 3: Interactive Plotting
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(
                                    if (searchStep >= 3) Color(0xFF4CAF50)
                                    else Color.Gray.copy(alpha = 0.3f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("3", color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            "Map Plotted",
                            fontSize = 11.sp,
                            fontWeight = if (searchStep == 3) FontWeight.Bold else FontWeight.Normal,
                            color = if (searchStep >= 3) Color(0xFF4CAF50) else Color.Gray
                        )
                    }
                }
            }

            if (isMapLoading) {
                // Animated Loading State
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary, strokeWidth = 3.dp)
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = when (searchStep) {
                            1 -> "📡 Connecting to Google Places API nearby search..."
                            2 -> "💎 Gemini AI analyzing & filtering hidden gems..."
                            else -> "Scanning cultural landmarks..."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 32.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Excluding tourist traps and crowded malls.",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            } else {
                // Interactive Map Canvas Area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(Color(0xFFE5F1F6)) 
                ) {
                    // Custom Draw Behind (Grid roads, green areas, waterbodies)
                    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
                        val w = size.width
                        val h = size.height

                        // Draw Green Areas (Parks)
                        drawRoundRect(
                            color = Color(0xFFD0EDD4),
                            topLeft = androidx.compose.ui.geometry.Offset(w * 0.15f, h * 0.1f),
                            size = androidx.compose.ui.geometry.Size(w * 0.3f, h * 0.25f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
                        )
                        drawRoundRect(
                            color = Color(0xFFD0EDD4),
                            topLeft = androidx.compose.ui.geometry.Offset(w * 0.65f, h * 0.5f),
                            size = androidx.compose.ui.geometry.Size(w * 0.25f, h * 0.35f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(16f, 16f)
                        )

                        // Draw Blue Rivers / Water canals
                        val waterPath = androidx.compose.ui.graphics.Path().apply {
                            moveTo(0f, h * 0.75f)
                            cubicTo(
                                w * 0.3f, h * 0.7f,
                                w * 0.6f, h * 0.95f,
                                w, h * 0.85f
                            )
                            lineTo(w, h * 0.92f)
                            cubicTo(
                                w * 0.6f, h * 0.99f,
                                w * 0.3f, h * 0.78f,
                                0f, h * 0.82f
                            )
                            close()
                        }
                        drawPath(waterPath, color = Color(0xFFAAD3DF))

                        // Draw Roads (Grid network)
                        // Horizontals
                        drawLine(color = Color.White, strokeWidth = 12f, start = androidx.compose.ui.geometry.Offset(0f, h * 0.3f), end = androidx.compose.ui.geometry.Offset(w, h * 0.3f))
                        drawLine(color = Color.White, strokeWidth = 8f, start = androidx.compose.ui.geometry.Offset(0f, h * 0.6f), end = androidx.compose.ui.geometry.Offset(w, h * 0.6f))
                        
                        // Verticals
                        drawLine(color = Color.White, strokeWidth = 12f, start = androidx.compose.ui.geometry.Offset(w * 0.4f, 0f), end = androidx.compose.ui.geometry.Offset(w * 0.4f, h))
                        drawLine(color = Color.White, strokeWidth = 8f, start = androidx.compose.ui.geometry.Offset(w * 0.75f, 0f), end = androidx.compose.ui.geometry.Offset(w * 0.75f, h))

                        // Draw diagonal highways
                        drawLine(color = Color(0xFFFCDD99), strokeWidth = 14f, start = androidx.compose.ui.geometry.Offset(0f, 0f), end = androidx.compose.ui.geometry.Offset(w, h))
                    }

                    // Render Map Pins
                    val visiblePins = if (showOnlyHiddenGems) pins.filter { it.isHiddenGem } else pins

                    androidx.compose.foundation.layout.BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                        val containerWidth = maxWidth.value
                        val containerHeight = maxHeight.value

                        visiblePins.forEach { pin ->
                            // Linear mapping relative to center coordinates
                            val deltaLat = pin.latitude - centerLat
                            val deltaLng = pin.longitude - centerLng

                            val pxX = (containerWidth / 2) + (deltaLng * zoomScale)
                            val pxY = (containerHeight / 2) - (deltaLat * zoomScale)

                            // Keep pins in bounds of container with padding
                            val finalX = pxX.coerceIn(24.0, (containerWidth - 24).toDouble()).dp
                            val finalY = pxY.coerceIn(24.0, (containerHeight - 24).toDouble()).dp

                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .align(Alignment.TopStart)
                                    .padding(start = finalX - 22.dp, top = finalY - 22.dp)
                                    .clickable { selectedPin = pin }
                                    .testTag("map_pin_${pin.name.replace(" ", "_")}"),
                                contentAlignment = Alignment.Center
                            ) {
                                // Glowing pulsator ring for hidden gems
                                if (pin.isHiddenGem) {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .border(2.dp, Color(0xFF3F51B5), CircleShape)
                                            .clip(CircleShape)
                                            .background(Color(0xFFE8EAF6).copy(alpha = 0.6f))
                                    )
                                    Text("💎", fontSize = 20.sp)
                                } else {
                                    // Mainstream site pin
                                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                        Text("📍", fontSize = 24.sp)
                                    }
                                }

                                // Selection Halo
                                if (selectedPin == pin) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                    )
                                }
                            }
                        }
                    }

                    // Zoom Scale Controls & Gem Toggle
                    Column(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                    ) {
                        // Zoom in
                        IconButton(
                            onClick = { zoomScale *= 1.3 },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .testTag("zoom_in_button")
                        ) {
                            Text("+", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        // Zoom out
                        IconButton(
                            onClick = { zoomScale /= 1.3 },
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White)
                                .testTag("zoom_out_button")
                        ) {
                            Text("-", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                        }
                    }

                    // Filter Toggle Chip at Top-Left
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable { showOnlyHiddenGems = !showOnlyHiddenGems }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                            .testTag("gem_filter_toggle")
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                if (showOnlyHiddenGems) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = if (showOnlyHiddenGems) "Gemini Filter Active: Hidden Gems" else "All Attractions (Raw Places API)",
                                color = Color.White,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Selected Spot details panel
                selectedPin?.let { pin ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(16.dp)
                            .testTag("pin_details_panel")
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = pin.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    if (pin.isHiddenGem) {
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Box(
                                            modifier = Modifier
                                                .clip(RoundedCornerShape(4.dp))
                                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                                .padding(horizontal = 4.dp, vertical = 2.dp)
                                        ) {
                                            Text("HIDDEN GEM", fontSize = 8.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                                Text(pin.type, fontSize = 12.sp, color = Color.Gray)
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("⭐", fontSize = 12.sp)
                                Spacer(modifier = Modifier.width(2.dp))
                                Text(
                                    "${pin.rating} (${pin.reviews})",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))
                        Text(pin.description, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))

                        if (pin.isHiddenGem && pin.hiddenGemReason.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("🧠", fontSize = 14.sp)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text("Gemini Scouter Insight", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(pin.hiddenGemReason, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f))
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    val gmmIntentUri = android.net.Uri.parse("geo:${pin.latitude},${pin.longitude}?q=${android.net.Uri.encode(pin.name)}")
                                    val mapIntent = android.content.Intent(android.content.Intent.ACTION_VIEW, gmmIntentUri).apply {
                                        setPackage("com.google.android.apps.maps")
                                    }
                                    try {
                                        context.startActivity(mapIntent)
                                    } catch (e: Exception) {
                                        val webIntent = android.content.Intent(android.content.Intent.ACTION_VIEW, android.net.Uri.parse("https://www.google.com/maps/search/?api=1&query=${android.net.Uri.encode(pin.name)}"))
                                        context.startActivity(webIntent)
                                    }
                                },
                                modifier = Modifier.weight(1f).testTag("open_in_google_maps_button"),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                            ) {
                                Text("📍 Show on Google Map", color = Color.White, fontSize = 13.sp)
                            }

                            OutlinedButton(
                                onClick = {
                                    viewModel.toggleFavoritePlace(pin.name)
                                },
                                modifier = Modifier.weight(1f).testTag("add_favorite_place_button"),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text("❤️ Add Favorite", fontSize = 13.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

data class LocalDishDetail(
    val name: String,
    val price: String,
    val description: String,
    val nearbySpot: String,
    val bestTime: String
)

val CITY_DISH_GUIDE = mapOf(
    "Jaipur" to listOf(
        LocalDishDetail(
            name = "Pyaaz Kachori",
            price = "₹45",
            description = "Crispy golden-fried pastry stuffed with a rich, spiced onion filling. A legendary breakfast specialty.",
            nearbySpot = "Rawat Mishtan Bhandar, Sindhi Camp",
            bestTime = "Breakfast (8:00 AM - 11:00 AM)"
        ),
        LocalDishDetail(
            name = "Dal Baati Churma",
            price = "₹220",
            description = "Traditional baked wheat dumplings drenched in pure ghee, served with spicy mixed lentils and sweetened crushed wheat.",
            nearbySpot = "LMB Restaurant, Johri Bazar",
            bestTime = "Lunch (12:30 PM - 3:00 PM)"
        ),
        LocalDishDetail(
            name = "Mawa Kachori",
            price = "₹75",
            description = "A sweet variation of kachori stuffed with rich sweetened milk solids (khoya) and dry fruits, bathed in saffron sugar syrup.",
            nearbySpot = "Sodhani Sweets, C-Scheme",
            bestTime = "Evening Dessert (4:00 PM - 6:30 PM)"
        ),
        LocalDishDetail(
            name = "Lassiwala Sweet Lassi",
            price = "₹70",
            description = "Thick, creamy yogurt lassi served in traditional earthenware clay cups (kulhad), topped with thick clotted cream.",
            nearbySpot = "Lassiwala, MI Road (Original since 1944)",
            bestTime = "Noon Refreshment (11:00 AM - 2:00 PM)"
        ),
        LocalDishDetail(
            name = "Mirchi Bada",
            price = "₹35",
            description = "Large green chilies stuffed with seasoned potato mash, coated in gram flour batter, and deep-fried to crisp perfection.",
            nearbySpot = "Rawat Mishtan Bhandar, Sindhi Camp",
            bestTime = "Evening Tea Snack (4:00 PM - 7:00 PM)"
        )
    ),
    "Goa" to listOf(
        LocalDishDetail(
            name = "Spicy Goan Fish Curry",
            price = "₹320",
            description = "Fresh pomfret or kingfish simmered in a warm, spicy coconut gravy infused with red chilies and tangy tamarind.",
            nearbySpot = "Ritz Classic, Panaji",
            bestTime = "Lunch (1:00 PM - 3:00 PM)"
        ),
        LocalDishDetail(
            name = "Bebinca",
            price = "₹120",
            description = "A delicious multi-layered traditional Indo-Portuguese pudding made from coconut milk, egg yolks, and nutmeg.",
            nearbySpot = "Viva Panjim, Fontainhas Quarter",
            bestTime = "Dessert (Anytime)"
        ),
        LocalDishDetail(
            name = "Pork Vindaloo",
            price = "₹350",
            description = "A classic fiery pork dish slow-cooked with fresh garlic, wine vinegar, ginger, and hot Kashmiri red chilies.",
            nearbySpot = "Mum's Kitchen, Panaji",
            bestTime = "Dinner (7:30 PM - 10:30 PM)"
        ),
        LocalDishDetail(
            name = "Chicken Cafreal",
            price = "₹280",
            description = "Succulent chicken drumsticks marinated in a vibrant green paste of fresh coriander, mint, green chilies, and toddy vinegar.",
            nearbySpot = "Florentine, Saligao",
            bestTime = "Dinner (8:00 PM - 11:00 PM)"
        )
    ),
    "Varanasi" to listOf(
        LocalDishDetail(
            name = "Kachori Sabzi",
            price = "₹40",
            description = "Deep-fried lentil-stuffed kachoris served with a spicy, aromatic potato curry and a touch of pickle.",
            nearbySpot = "Ram Bhandar, Chowk",
            bestTime = "Breakfast (7:00 AM - 10:00 AM)"
        ),
        LocalDishDetail(
            name = "Tamatar Chaat",
            price = "₹50",
            description = "A tangy mashed tomato specialty cooked with ghee, savory spices, and finished with a rich sugar-cardamom syrup.",
            nearbySpot = "Kashi Chat Bhandar, Godowlia Crossing",
            bestTime = "Evening Street Snack (4:30 PM - 8:30 PM)"
        ),
        LocalDishDetail(
            name = "Malaiyyo",
            price = "₹60",
            description = "A winter-special airy, saffron-flavored milk froth garnished with sliced almonds, pistachios, and silver leaf.",
            nearbySpot = "Markandey, Chowk",
            bestTime = "Morning (7:30 AM - 11:00 AM)"
        ),
        LocalDishDetail(
            name = "Banarasi Sweet Paan",
            price = "₹25",
            description = "The world-famous betel leaf preparation containing rose petal jam (gulkand), sweet spices, and refreshing mouth fresheners.",
            nearbySpot = "Keshav Tambul, Lanka",
            bestTime = "Post-Meals Digestif (Anytime)"
        )
    ),
    "Kerala" to listOf(
        LocalDishDetail(
            name = "Appam with Vegetable Stew",
            price = "₹90",
            description = "Light, lacy fermented rice pancakes with a soft pillowy center, served with a mild, fragrant spiced coconut milk stew.",
            nearbySpot = "Kashi Art Cafe, Fort Kochi",
            bestTime = "Breakfast (8:00 AM - 10:30 AM)"
        ),
        LocalDishDetail(
            name = "Karimeen Pollichathu",
            price = "₹380",
            description = "Pearl Spot fish marinated in spicy shallot-tomato masala, wrapped carefully in a banana leaf, and pan-seared to perfection.",
            nearbySpot = "Paragon Restaurant, Kochi",
            bestTime = "Lunch (12:30 PM - 3:00 PM)"
        ),
        LocalDishDetail(
            name = "Malabar Parotta with Veg/Beef Fry",
            price = "₹160",
            description = "Multi-layered, flaky flatbread served with a slow-roasted local spiced dry gravy garnished with fried coconut slivers.",
            nearbySpot = "Paragon Restaurant, Kozhikode",
            bestTime = "Dinner (7:30 PM - 10:30 PM)"
        ),
        LocalDishDetail(
            name = "Traditional Kerala Sadya",
            price = "₹280",
            description = "A lavish, festive vegetarian banquet consisting of 24+ traditional dishes served ceremoniously on a banana leaf.",
            nearbySpot = "Mothers Veg Plaza, Thiruvananthapuram",
            bestTime = "Lunch (12:00 PM - 2:30 PM)"
        )
    )
)

@Composable
fun LocalCuisineExplorer(
    city: String,
    onDismiss: () -> Unit
) {
    val cityName = city.split(",").first().trim().lowercase().replaceFirstChar { it.uppercase() }
    val dishes = CITY_DISH_GUIDE[cityName] ?: CITY_DISH_GUIDE["Jaipur"] ?: emptyList()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
            .padding(4.dp)
            .testTag("local_cuisine_explorer_dialog"),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.05f))
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "🍛 Local Cuisine Explorer",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        fontFamily = FontFamily.Serif
                    )
                    Text(
                        text = "Authentic specialties & street food for $cityName",
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
                IconButton(onClick = onDismiss, modifier = Modifier.testTag("close_cuisine_button")) {
                    Icon(Icons.Default.Close, contentDescription = "Close Cuisine Explorer")
                }
            }

            // Budget Indicator Banner
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("💰", fontSize = 24.sp)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = "Budget-Friendly Street Food Guard: Under ₹500",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = "Every handpicked item below is highly authentic and costs well under the ₹500 travel budget limit.",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            // Dish List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(dishes) { dish ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("dish_card_${dish.name.replace(" ", "_")}"),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Top Row: Name and Price Chip
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = "🍲",
                                        fontSize = 20.sp,
                                        modifier = Modifier.padding(end = 8.dp)
                                    )
                                    Text(
                                        text = dish.name,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primary)
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = dish.price,
                                        color = Color.White,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Description
                            Text(
                                text = dish.description,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                lineHeight = 18.sp
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            // Details block (Spot and Time)
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.03f))
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("📍", fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Best Spot: ${dish.nearbySpot}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text("⏰", fontSize = 12.sp)
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Best Time: ${dish.bestTime}",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
                
                // Bottom padding inside the list
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// ==========================================
// MAP SCREEN (Offline Historical Cartography Map)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(viewModel: CultureVerseViewModel, onOpenDrawer: () -> Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }
    
    // Five spectacular global spots
    val locations = listOf(
        Triple("Kyoto, Japan", Offset(150f, 120f), "Sacred shrine district, remove shoes before entering mats."),
        Triple("Rome, Italy", Offset(280f, 180f), "Historic monument area, do not climb or write on ancient stone walls."),
        Triple("Agra, India", Offset(200f, 210f), "Mausoleum site, dress modestly & walk clockwise."),
        Triple("Naples, Italy", Offset(300f, 230f), "Traditional UNESCO pizzerias, eat with fork and knife."),
        Triple("Cusco, Peru", Offset(110f, 320f), "Andean citadel, do not touch or lean against Inca stone carvings.")
    )

    var selectedLocation by remember { mutableStateOf(locations[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Historical Custom Map", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer, modifier = Modifier.testTag("map_drawer_button")) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Open navigation drawer", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("map_search_field"),
                placeholder = { Text("Search historical spots, cities...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search icon") },
                shape = RoundedCornerShape(12.dp)
            )

            // Dynamic Map Canvas
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    val lineColor = MaterialTheme.colorScheme.primary
                    val accentColor = MaterialTheme.colorScheme.secondary

                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                // Cycle through locations on click
                                val nextIndex = (locations.indexOf(selectedLocation) + 1) % locations.size
                                selectedLocation = locations[nextIndex]
                            }
                            .testTag("map_canvas")
                    ) {
                        val width = size.width
                        val height = size.height

                        // Draw background custom grid representing latitude/longitude guidelines
                        for (i in 1..8) {
                            drawLine(
                                color = lineColor.copy(alpha = 0.05f),
                                start = Offset(0f, height * i / 9),
                                end = Offset(width, height * i / 9),
                                strokeWidth = 2f
                            )
                            drawLine(
                                color = lineColor.copy(alpha = 0.05f),
                                start = Offset(width * i / 9, 0f),
                                end = Offset(width * i / 9, height),
                                strokeWidth = 2f
                            )
                        }

                        // Draw visual connections between all landmarks (routes of historical significance)
                        for (i in 0 until locations.size - 1) {
                            drawLine(
                                color = lineColor.copy(alpha = 0.15f),
                                start = locations[i].second,
                                end = locations[i + 1].second,
                                strokeWidth = 4f
                            )
                        }

                        // Draw active routing path from user's current location (representing center of canvas)
                        val center = Offset(width / 2, height / 2)
                        drawLine(
                            color = accentColor,
                            start = center,
                            end = selectedLocation.second,
                            strokeWidth = 6f,
                            pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(15f, 15f), 0f)
                        )

                        // Draw User Location (Center)
                        drawCircle(
                            color = accentColor,
                            radius = 16f,
                            center = center
                        )
                        drawCircle(
                            color = Color.White,
                            radius = 6f,
                            center = center
                        )

                        // Draw Landmark Markers
                        locations.forEach { loc ->
                            val isSelected = loc == selectedLocation
                            drawCircle(
                                color = if (isSelected) lineColor else lineColor.copy(alpha = 0.5f),
                                radius = if (isSelected) 24f else 14f,
                                center = loc.second
                            )
                            drawCircle(
                                color = Color.White,
                                radius = if (isSelected) 10f else 6f,
                                center = loc.second
                            )
                        }
                    }

                    // Map Overlay info labels
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(12.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.85f))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Cartography Mode: Offline", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .padding(12.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f))
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text("Tap Map to Navigate", color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Detailed information about Selected Location
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("map_details_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("📍", fontSize = 20.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = selectedLocation.first,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text("Active Sights", color = MaterialTheme.colorScheme.primary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Cultural Safety Protocol:",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Text(
                        text = selectedLocation.third,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        lineHeight = 18.sp,
                        modifier = Modifier.padding(top = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Estimated Distance", fontSize = 11.sp, color = Color.Gray)
                            Text("6,240 km", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface)
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("Anthropological Rating", fontSize = 11.sp, color = Color.Gray)
                            Text("⭐⭐⭐⭐⭐ (5.0)", fontWeight = FontWeight.Bold, fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }
    }
}

// ==========================================
// FOOD SCREEN (Gastronomy & Cuisine Etiquette Hub)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodScreen(viewModel: CultureVerseViewModel, onOpenDrawer: () -> Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedRegion by remember { mutableStateOf("All") }

    val cuisinesList = listOf(
        Triple("Sushi Nigiri", "Japan", "Eat nigiri in one clean bite. Dip the fish-side lightly into soy sauce, never the rice-side to avoid breaking the delicate grains."),
        Triple("Neapolitan Pizza", "Italy", "Traditionally soft and folded. It is eaten using a knife and fork, or folded 'a portafoglio' (wallet style) to prevent toppings from sliding."),
        Triple("Chicken Tikka Masala", "India", "Typically consumed by ripping naan or flatbread with your right hand only and scooping up the rich tomato-gravy."),
        Triple("Tacos al Pastor", "Mexico", "Support the soft corn tortilla fully with thumb and two fingers, eating from the side with a slight tilt to avoid dripping salsas."),
        Triple("Croissant & Cafe", "France", "Dip pastries modestly or tear off bite-sized pieces with fingers, never slice croissants in half with a serrated knife.")
    )

    val regions = listOf("All", "Japan", "Italy", "India", "Mexico", "France")

    val filteredCuisines = cuisinesList.filter {
        (selectedRegion == "All" || it.second == selectedRegion) &&
        (it.first.contains(searchQuery, ignoreCase = true) || it.second.contains(searchQuery, ignoreCase = true))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gastronomy & Etiquette", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer, modifier = Modifier.testTag("food_drawer_button")) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Open navigation drawer", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("food_search_field"),
                placeholder = { Text("Search regional cuisines, rules...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search icon") },
                shape = RoundedCornerShape(12.dp)
            )

            // Horizontal Region filter chips
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(regions) { r ->
                    val isSelected = r == selectedRegion
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.08f))
                            .clickable { selectedRegion = r }
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                            .testTag("food_chip_$r"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = r,
                            color = if (isSelected) Color.White else MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            // Cuisine list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (filteredCuisines.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No gastronomy records found.", color = Color.Gray)
                        }
                    }
                } else {
                    items(filteredCuisines) { cuisine ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("cuisine_card_${cuisine.first.replace(" ", "_")}"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("🍲", fontSize = 22.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = cuisine.first,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.onSurface
                                            )
                                            Text(
                                                text = cuisine.second,
                                                fontSize = 11.sp,
                                                color = Color.Gray,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                    }
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(8.dp))
                                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f))
                                            .padding(horizontal = 8.dp, vertical = 4.dp)
                                    ) {
                                        Text("Etiquette", color = MaterialTheme.colorScheme.secondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = cuisine.third,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    lineHeight = 18.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.03f))
                                        .padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = "Warning",
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "Avoid blowing on hot food in this culinary tradition.",
                                        fontSize = 11.sp,
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// ==========================================
// STORIES SCREEN (Mythologies & AI Custom Tales)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoriesScreen(viewModel: CultureVerseViewModel, onOpenDrawer: () -> Unit = {}) {
    var customPrompt by remember { mutableStateOf("") }
    var generatedStory by remember { mutableStateOf("") }
    var isGenerating by remember { mutableStateOf(false) }

    val presetStories = listOf(
        Triple("The Bamboo Cutter", "Japan Folktale", "A legendary 10th-century story about Princess Kaguya, discovered inside a shining stalk of bamboo, who turns out to be from the Moon. It details honor, letting go, and divine roots."),
        Triple("The Legend of Ashoka", "India Folklore", "The moral awakening of Emperor Ashoka who witnessed battlefield horrors, embraced Zen Buddhist philosophy, and set up monumental stone pillars advocating nonviolence."),
        Triple("Romulus & Remus", "Rome Myth", "The classic founding legend of Rome. Two orphan brothers breastfed by a she-wolf, who would rise to lay the foundational stone bricks of an empire.")
    )

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Folklore & AI Storyteller", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer, modifier = Modifier.testTag("stories_drawer_button")) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Open navigation drawer", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState())
        ) {
            // Heading section
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Classic Folklore tales",
                    style = MaterialTheme.typography.titleLarge,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Immerse yourself in legendary world lore histories.",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }

            // Presets row
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                presetStories.forEach { story ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("story_preset_${story.first.replace(" ", "_")}"),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = story.first,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(text = story.second, color = MaterialTheme.colorScheme.secondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = story.third,
                                fontSize = 13.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                lineHeight = 18.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // AI Storyteller
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("ai_storyteller_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.03f)),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.15f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("✨", fontSize = 22.sp)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Gemini AI Storyteller",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Generate custom mythology folklore on demand.",
                                fontSize = 11.sp,
                                color = Color.Gray
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = customPrompt,
                        onValueChange = { customPrompt = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("storyteller_prompt_field"),
                        placeholder = { Text("e.g., A tale of a brave samurai in ancient Kyoto...") },
                        maxLines = 3,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            if (customPrompt.isNotBlank() && !isGenerating) {
                                isGenerating = true
                                scope.launch {
                                    try {
                                        val response = com.example.data.Repository.askGemini(
                                            "You are a master folklore storyteller. Write a short, highly descriptive mythological legend or traditional folklore tale based on this prompt: $customPrompt. Keep it beautifully paced, starting with Once upon a time, and ending with a traditional cultural moral insight.",
                                            emptyList()
                                        )
                                        generatedStory = response
                                    } catch (e: Exception) {
                                        generatedStory = "Failed to connect to storyteller. Check API key status. Morals speak of patience!"
                                    } finally {
                                        isGenerating = false
                                    }
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("generate_story_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                    ) {
                        if (isGenerating) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                        } else {
                            Text("Generate Folklore", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }

                    if (generatedStory.isNotBlank()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f))
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Generated Folklore Tale:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            text = generatedStory,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            lineHeight = 18.sp,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

// ==========================================
// FESTIVALS SCREEN (Event Calendar & Traditional Customs)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FestivalsScreen(viewModel: CultureVerseViewModel, onOpenDrawer: () -> Unit = {}) {
    var searchQuery by remember { mutableStateOf("") }

    val festivalList = listOf(
        Triple("Hanami (Cherry Blossom)", "Kyoto, Japan (April)", "The traditional custom of enjoying the transient beauty of cherry blossom flowers. Families picnic under blooming canopies, writing short waka poems, and eating sakura mochi."),
        Triple("Diwali (Festival of Lights)", "Agra, India (November)", "The legendary victory of light over dark. Homes are lit with oil lamps, streets decorated with colorful chalk rangoli arrays, and sweet treats shared with neighbors."),
        Triple("Day of the Dead", "Oaxaca, Mexico (November)", "A beautiful celebration honoring deceased ancestors. Intricate altars are decorated with orange marigold petals, favorite foods of the passed, and sugar skulls."),
        Triple("Carnival of Venice", "Venice, Italy (February)", "Famed for its elegant, elaborate porcelain masks. Originating in medieval times to erase social class differences for a week of street festivals and grand balls.")
    )

    val filteredFestivals = festivalList.filter {
        it.first.contains(searchQuery, ignoreCase = true) || it.third.contains(searchQuery, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Festivals & Customs", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer, modifier = Modifier.testTag("festivals_drawer_button")) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Open navigation drawer", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("festival_search_field"),
                placeholder = { Text("Search global custom celebrations...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search icon") },
                shape = RoundedCornerShape(12.dp)
            )

            // Festival List
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (filteredFestivals.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("No matching celebrations found.", color = Color.Gray)
                        }
                    }
                } else {
                    items(filteredFestivals) { festival ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("festival_card_${festival.first.replace(" ", "_")}"),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("🎉", fontSize = 22.sp)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                text = festival.first,
                                                style = MaterialTheme.typography.titleMedium,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                            Text(
                                                text = festival.second,
                                                fontSize = 11.sp,
                                                color = MaterialTheme.colorScheme.secondary,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.height(10.dp))

                                Text(
                                    text = festival.third,
                                    fontSize = 13.sp,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f),
                                    lineHeight = 18.sp
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Visual Traditional Attire and Custom Tips
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.03f))
                                        .padding(8.dp),
                                    verticalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("👘", fontSize = 12.sp)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Traditional Attire: Wear bright or context-appropriate attire.",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("🎯", fontSize = 12.sp)
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Text(
                                            text = "Traditional Game: Traditional folk dances and street assemblies.",
                                            fontSize = 11.sp,
                                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

// ==========================================
// PROFILE SCREEN (Full Screen Hub with Tabs: Trips, Favorites, History)
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: CultureVerseViewModel, onOpenDrawer: () -> Unit = {}) {
    val username by viewModel.username.collectAsState()
    val email by viewModel.userEmail.collectAsState()
    val avatarIdx by viewModel.avatarIndex.collectAsState()
    val region by viewModel.favoriteRegion.collectAsState()

    val itineraries by viewModel.itineraries.collectAsState()
    val history by viewModel.history.collectAsState()

    var activeTab by remember { mutableIntStateOf(0) }
    var selectedItineraryForDetail by remember { mutableStateOf<Itinerary?>(null) }

    var isEditing by remember { mutableStateOf(false) }
    var editName by remember { mutableStateOf(username) }
    var editEmail by remember { mutableStateOf(email) }
    var editRegion by remember { mutableStateOf(region) }
    var editAvatarIdx by remember { mutableIntStateOf(avatarIdx) }

    val scope = rememberCoroutineScope()

    if (selectedItineraryForDetail != null) {
        ProfileItineraryDetailDialog(
            itinerary = selectedItineraryForDetail!!,
            onDismiss = { selectedItineraryForDetail = null }
        )
    }

    if (isEditing) {
        Dialog(onDismissRequest = { isEditing = false }) {
            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .testTag("profile_edit_dialog")
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Edit Profile Info",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Serif
                    )

                    // Avatar Row selector
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AVATAR_EMOJIS.forEachIndexed { idx, emoji ->
                            val isSelected = editAvatarIdx == idx
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f))
                                    .clickable { editAvatarIdx = idx }
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = emoji, fontSize = 24.sp)
                            }
                        }
                    }

                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Explorer Name") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_username_field"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = editEmail,
                        onValueChange = { editEmail = it },
                        label = { Text("Explorer Email") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_email_field"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    // Region Selector Dropdown Mock
                    OutlinedTextField(
                        value = editRegion,
                        onValueChange = { editRegion = it },
                        label = { Text("Primary Region Focus") },
                        modifier = Modifier.fillMaxWidth().testTag("edit_region_field"),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = { isEditing = false },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Cancel")
                        }
                        Button(
                            onClick = {
                                viewModel.updateProfile(name = editName, email = editEmail, avatarIdx = editAvatarIdx, region = editRegion)
                                isEditing = false
                            },
                            modifier = Modifier.weight(1f).testTag("save_profile_button"),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                        ) {
                            Text("Save", color = Color.White)
                        }
                    }
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Explorer Profile", fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onOpenDrawer, modifier = Modifier.testTag("profile_drawer_button")) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Open navigation drawer", tint = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = {
                        editName = username
                        editEmail = email
                        editRegion = region
                        editAvatarIdx = avatarIdx
                        isEditing = true
                    }, modifier = Modifier.testTag("edit_profile_action")) {
                        Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit Profile details", tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Header Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = AVATAR_EMOJIS.getOrNull(avatarIdx) ?: "🗺️",
                            fontSize = 32.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = username,
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontFamily = FontFamily.Serif
                        )
                        Text(
                            text = email,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.White.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.White.copy(alpha = 0.15f))
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(text = region, color = Color.White, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Tab Rows: 0: Trips, 1: Favorites, 2: Badges, 3: History
            TabRow(
                selectedTabIndex = activeTab,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[activeTab]),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            ) {
                Tab(
                    selected = activeTab == 0,
                    onClick = { activeTab = 0 },
                    text = { Text("Trips", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("tab_trips")
                )
                Tab(
                    selected = activeTab == 1,
                    onClick = { activeTab = 1 },
                    text = { Text("Favorites", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("tab_favorites")
                )
                Tab(
                    selected = activeTab == 2,
                    onClick = { activeTab = 2 },
                    text = { Text("Badges", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("tab_badges")
                )
                Tab(
                    selected = activeTab == 3,
                    onClick = { activeTab = 3 },
                    text = { Text("History", fontWeight = FontWeight.Bold) },
                    modifier = Modifier.testTag("tab_history")
                )
            }

            // Tab Content
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                when (activeTab) {
                    0 -> { // Trips Tab
                        if (itineraries.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No saved cultural trips yet.", color = Color.Gray, fontSize = 14.sp)
                            }
                        } else {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                items(itineraries) { iti ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { selectedItineraryForDetail = iti }
                                            .testTag("trip_card_${iti.id}"),
                                        shape = RoundedCornerShape(12.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.15f))
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Column {
                                                Text(
                                                    text = "✈️ ${iti.destination}",
                                                    fontWeight = FontWeight.Bold,
                                                    style = MaterialTheme.typography.titleMedium,
                                                    color = MaterialTheme.colorScheme.primary
                                                )
                                                Text(
                                                    text = "${iti.days} Days • ${iti.budget}",
                                                    fontSize = 12.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                            IconButton(onClick = { viewModel.deleteItinerary(iti.id) }, modifier = Modifier.testTag("delete_trip_${iti.id}")) {
                                                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete Trip", tint = Color.Red.copy(alpha = 0.7f))
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    1 -> { // Favorites Tab (Dynamic)
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                Text("💖 Sights of Interest", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                Text("No marked sights yet. Search on Explore and toggle heart icons!", color = Color.Gray, fontSize = 12.sp)
                            }
                        }
                    }
                    2 -> { // Badges Tab
                        val unlockedBadges by viewModel.unlockedBadges.collectAsState()
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text("🏆 Explorer Badges", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.primary, fontFamily = FontFamily.Serif)
                                    Text("Earned by exploring cultural hot spots", fontSize = 11.sp, color = Color.Gray)
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                        .padding(horizontal = 8.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = "${unlockedBadges.size} / 3 Earned",
                                        color = MaterialTheme.colorScheme.primary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            LazyColumn(
                                modifier = Modifier.fillMaxSize(),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                val badgesData = listOf(
                                    BadgeInfo(
                                        id = "Jaipur_Cultural",
                                        title = "Cultural Badge",
                                        city = "Jaipur",
                                        description = "Deeply engaged with Rajasthani heritage, geometric stepwells, Amber Palace, and authentic textile crafts.",
                                        emoji = "👑",
                                        color = Color(0xFFFF9800)
                                    ),
                                    BadgeInfo(
                                        id = "Varanasi_Spiritual",
                                        title = "Spiritual Badge",
                                        city = "Varanasi",
                                        description = "Sought spiritual enlightenment on the holy riverfront stone ghats and observed evening aarti rituals.",
                                        emoji = "🪔",
                                        color = Color(0xFFE91E63)
                                    ),
                                    BadgeInfo(
                                        id = "Goa_Beach",
                                        title = "Beach Explorer",
                                        city = "Goa",
                                        description = "Mapped majestic golden sand shores, historical Portuguese cathedrals, and coastal cuisine cultures.",
                                        emoji = "🌊",
                                        color = Color(0xFF03A9F4)
                                    )
                                )

                                items(badgesData) { badge ->
                                    val isEarned = unlockedBadges.contains(badge.id)
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { viewModel.toggleBadge(badge.id) }
                                            .testTag("badge_card_${badge.id}"),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = if (isEarned) badge.color.copy(alpha = 0.08f) else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
                                        ),
                                        border = androidx.compose.foundation.BorderStroke(
                                            width = if (isEarned) 1.5.dp else 1.dp,
                                            color = if (isEarned) badge.color.copy(alpha = 0.4f) else MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
                                        )
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .size(56.dp)
                                                    .clip(CircleShape)
                                                    .background(
                                                        if (isEarned) badge.color.copy(alpha = 0.2f) else Color.Gray.copy(alpha = 0.1f)
                                                    ),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    text = badge.emoji,
                                                    fontSize = 28.sp,
                                                    modifier = Modifier.alpha(if (isEarned) 1f else 0.4f)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(16.dp))
                                            Column(modifier = Modifier.weight(1f)) {
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                                ) {
                                                    Text(
                                                        text = badge.city,
                                                        fontWeight = FontWeight.ExtraBold,
                                                        fontSize = 12.sp,
                                                        color = if (isEarned) badge.color else Color.Gray
                                                    )
                                                    Text(
                                                        text = "•",
                                                        fontSize = 12.sp,
                                                        color = Color.Gray
                                                    )
                                                    Text(
                                                        text = badge.title,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 12.sp,
                                                        color = if (isEarned) MaterialTheme.colorScheme.onSurface else Color.Gray
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(4.dp))
                                                Text(
                                                    text = badge.description,
                                                    fontSize = 11.sp,
                                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = if (isEarned) 0.8f else 0.5f),
                                                    lineHeight = 15.sp
                                                )
                                                Spacer(modifier = Modifier.height(6.dp))
                                                Row(
                                                    verticalAlignment = Alignment.CenterVertically,
                                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                                ) {
                                                    Text(
                                                        text = if (isEarned) "✓ Earned" else "○ Tap to Unlock",
                                                        fontSize = 11.sp,
                                                        fontWeight = FontWeight.Bold,
                                                        color = if (isEarned) badge.color else Color.Gray
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    3 -> { // History Tab (Searches and Chat logs)
                        if (history.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Search logs empty.", color = Color.Gray, fontSize = 14.sp)
                            }
                        } else {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text("Recent Search Logs", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.secondary)
                                    Text(
                                        text = "Clear All",
                                        color = Color.Red.copy(alpha = 0.8f),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .clickable { viewModel.clearSearchHistory() }
                                            .testTag("clear_history_action")
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                LazyColumn(
                                    modifier = Modifier.weight(1f),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(history) { log ->
                                        Card(
                                            modifier = Modifier.fillMaxWidth(),
                                            shape = RoundedCornerShape(8.dp),
                                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(12.dp),
                                                horizontalArrangement = Arrangement.SpaceBetween,
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                                                    Text("🔍", fontSize = 12.sp)
                                                    Spacer(modifier = Modifier.width(8.dp))
                                                    Text(text = log, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface)
                                                }
                                                IconButton(onClick = { viewModel.deleteSearchHistory(log) }, modifier = Modifier.size(24.dp)) {
                                                    Icon(imageVector = Icons.Default.Close, contentDescription = "Delete item", tint = Color.Gray, modifier = Modifier.size(16.dp))
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// Dialog helper to render Itinerary details cleanly in full screen or overlay
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileItineraryDetailDialog(itinerary: Itinerary, onDismiss: () -> Unit) {
    val daysList = remember(itinerary.generatedItinerary) { parseItineraryJson(itinerary.generatedItinerary) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.85f)
                .padding(8.dp)
                .testTag("profile_itinerary_detail_dialog")
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                // Header row
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = itinerary.destination,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontFamily = FontFamily.Serif
                            )
                            Text(
                                text = "Cultural Focus: ${itinerary.culturalFocus}",
                                fontSize = 11.sp,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                        }
                        IconButton(onClick = onDismiss) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Close dialog", tint = Color.White)
                        }
                    }
                }

                // Daily breakdown list
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(daysList) { day ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.03f)),
                            border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "Day ${day.dayNumber}: ${day.theme}",
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                day.activities.forEach { act ->
                                    Column(modifier = Modifier.padding(bottom = 12.dp)) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Box(
                                                modifier = Modifier
                                                    .clip(RoundedCornerShape(4.dp))
                                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(text = act.time, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                            }
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Text(text = act.title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurface)
                                        }
                                        if (act.description.isNotBlank()) {
                                            Text(
                                                text = act.description,
                                                fontSize = 12.sp,
                                                color = Color.Gray,
                                                modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                                            )
                                        }
                                        if (act.etiquette.isNotBlank()) {
                                            Box(
                                                modifier = Modifier
                                                    .padding(start = 4.dp, top = 4.dp)
                                                    .clip(RoundedCornerShape(6.dp))
                                                    .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.08f))
                                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                                            ) {
                                                Text(text = "💡 Etiquette: ${act.etiquette}", fontSize = 11.sp, color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Medium)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
