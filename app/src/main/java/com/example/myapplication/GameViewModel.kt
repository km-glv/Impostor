package com.example.myapplication

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    var gameConfig by mutableStateOf(GameConfig())
        private set
    
    var players by mutableStateOf<List<Player>>(emptyList())
        private set
    
    var currentPlayerIndex by mutableStateOf(0)
        private set
    
    var isGameStarted by mutableStateOf(false)
        private set
    
    var showVotingScreen by mutableStateOf(false)
        private set
    
    var votes by mutableStateOf<Map<Int, Int>>(emptyMap())
        private set
    
    // Palabras para el juego según el tema
    private val themeWords = mapOf(
        MusicTheme.MUSIC to listOf(
            // Rap Chileno
            "Bubaseta", "Chystemc", "Portavoz", "Jonas Sanche", "Jota Mayúscula",
            "Camilo Masilla", "Drefquila", "Pablo Chill-E", "Polimá Westcoast", "Harry Nach",
            // Rap Internacional
            "Eminem", "Tupac", "Kendrick Lamar", "Drake", "J Cole",
            "Canserbero", "Residente", "Nicki Jam", "Bad Bunny", "Daddy Yankee",
            // Pop/Rock
            "Michael Jackson", "Queen", "Beatles", "Coldplay", "Imagine Dragons",
            "Bruno Mars", "Ed Sheeran", "Taylor Swift", "Ariana Grande", "The Weeknd",
            // Reggaeton/Latino
            "Ozuna", "Maluma", "Shakira", "Karol G", "J Balvin",
            // Canciones famosas
            "Bohemian Rhapsody", "Thriller", "Despacito", "Shape of You", "Blinding Lights",
            "Hotel California", "Stairway to Heaven", "Smells Like Teen Spirit"
        ),
        MusicTheme.CHARACTERS to listOf(
            // Marvel
            "Spider-Man", "Iron Man", "Capitán América", "Thor", "Hulk",
            "Viuda Negra", "Doctor Strange", "Black Panther", "Wolverine", "Deadpool",
            "Thanos", "Loki", "Scarlet Witch", "Venom", "Groot",
            // DC
            "Batman", "Superman", "Wonder Woman", "Flash", "Aquaman",
            "Joker", "Harley Quinn", "Cyborg", "Green Lantern", "Lex Luthor",
            // Harry Potter
            "Harry Potter", "Hermione Granger", "Ron Weasley", "Dumbledore", "Voldemort",
            "Severus Snape", "Hagrid", "Draco Malfoy", "Sirius Black", "Dobby",
            // Actores famosos
            "Leonardo DiCaprio", "Tom Hanks", "Denzel Washington", "Morgan Freeman", "Robert Downey Jr",
            "Keanu Reeves", "Will Smith", "Brad Pitt", "Johnny Depp", "Al Pacino",
            // Personajes históricos
            "Albert Einstein", "Nikola Tesla", "Steve Jobs", "Pablo Escobar", "Che Guevara",
            "Nelson Mandela", "Martin Luther King", "Gandhi", "Cleopatra", "Napoleón Bonaparte",
            // Otros
            "Augusto Pinochet", "Salvador Allende", "Arturo Vidal", "Alexis Sánchez", "Gabriela Mistral"
        ),
        MusicTheme.MOVIES to listOf(
            // Acción
            "John Wick", "Matrix", "Die Hard", "Mad Max", "Terminator",
            "Rambo", "Jason Bourne", "Mission Impossible", "Fast and Furious", "The Equalizer",
            // Aventura
            "Indiana Jones", "Piratas del Caribe", "Jurassic Park", "Avatar", "El Señor de los Anillos",
            "Star Wars", "Gladiador", "Braveheart", "Troya", "300",
            // Superhéroes
            "Avengers", "The Dark Knight", "Man of Steel", "Guardianes de la Galaxia", "Logan",
            "Black Panther", "Spider-Man No Way Home", "Deadpool", "Joker", "Wonder Woman",
            // Clásicos
            "El Padrino", "Titanic", "Forrest Gump", "Pulp Fiction", "Shawshank Redemption",
            "El Club de la Pelea", "Inception", "Interstellar", "El Origen", "Parasite",
            // Terror/Suspenso
            "El Conjuro", "El Exorcista", "Halloween", "El Silencio de los Inocentes", "Psicosis",
            "It", "El Resplandor", "Scream", "Saw", "Hereditary",
            // Comedia
            "Mi Pobre Angelito", "Scary Movie", "Superbad", "The Hangover", "Ted"
        ),
        MusicTheme.GAMES to listOf(
            // Acción/Aventura
            "God of War", "The Last of Us", "Uncharted", "Red Dead Redemption", "GTA",
            "Assassins Creed", "Batman Arkham", "Tomb Raider", "Horizon Zero Dawn", "Ghost of Tsushima",
            // Shooter
            "Call of Duty", "Battlefield", "Halo", "Destiny", "Counter Strike",
            "Valorant", "Overwatch", "Apex Legends", "Fortnite", "PUBG",
            // RPG
            "The Witcher", "Skyrim", "Dark Souls", "Elden Ring", "Final Fantasy",
            "Dragon Age", "Mass Effect", "Cyberpunk 2077", "Persona", "Bloodborne",
            // Deportes
            "FIFA", "NBA 2K", "Madden NFL", "WWE 2K", "Rocket League",
            // Survival/Horror
            "Resident Evil", "Silent Hill", "Dead Space", "The Evil Within", "Outlast",
            // Plataformas/Aventura
            "Super Mario", "Zelda", "Crash Bandicoot", "Sonic", "Spyro",
            "Ratchet and Clank", "Sackboy", "Astro Bot", "Donkey Kong", "Kirby",
            // Online/Competitivo
            "League of Legends", "Dota 2", "Minecraft", "Roblox", "Among Us",
            "Fall Guys", "Fortnite", "World of Warcraft", "Genshin Impact", "Warzone"
        ),
        MusicTheme.ANIME to listOf(
            // Shonen populares
            "Naruto", "One Piece", "Bleach", "Dragon Ball", "My Hero Academia",
            "Demon Slayer", "Kimetsu no Yaiba", "Jujutsu Kaisen", "Chainsaw Man", "Black Clover",
            "One Punch Man", "Mob Psycho", "Hunter x Hunter", "Fullmetal Alchemist", "Attack on Titan",
            // Seinen
            "Berserk", "Tokyo Ghoul", "Parasyte", "Death Note", "Code Geass",
            "Steins Gate", "Cowboy Bebop", "Samurai Champloo", "Vinland Saga", "Monster",
            // Deportes
            "Haikyuu", "Kuroko no Basket", "Captain Tsubasa", "Slam Dunk", "Hajime no Ippo",
            // Slice of Life/Romance
            "Your Name", "Kimi no Na wa", "Weathering with You", "A Silent Voice", "Violet Evergarden",
            "Toradora", "Clannad", "Your Lie in April", "Fruits Basket", "Horimiya",
            // Isekai
            "Sword Art Online", "Re:Zero", "Overlord", "Konosuba", "That Time I Got Reincarnated as a Slime",
            // Mecha
            "Neon Genesis Evangelion", "Gundam", "Code Geass", "Darling in the Franxx", "Gurren Lagann",
            // Personajes icónicos
            "Goku", "Luffy", "Naruto Uzumaki", "Ichigo", "Tanjiro",
            "Saitama", "Eren Yeager", "Light Yagami", "Levi Ackerman", "Kakashi"
        )
    )
    
    // Pistas para impostores según el tema
    private val themeClues = mapOf(
        MusicTheme.MUSIC to "Relacionado con música, artistas o canciones famosas",
        MusicTheme.CHARACTERS to "Relacionado con personajes de películas, cómics, historia o famosos",
        MusicTheme.MOVIES to "Relacionado con películas famosas de cualquier género",
        MusicTheme.GAMES to "Relacionado con videojuegos populares",
        MusicTheme.ANIME to "Relacionado con anime, manga o personajes japoneses"
    )
    
    fun updateConfig(config: GameConfig) {
        gameConfig = config
    }
    
    fun startGame() {
        val theme = gameConfig.musicTheme ?: MusicTheme.MUSIC
        val words = themeWords[theme] ?: themeWords[MusicTheme.MUSIC]!!
        val selectedWord = words.random()
        
        // Crear lista de jugadores
        val playerList = mutableListOf<Player>()
        val impostorIndices = (0 until gameConfig.totalPlayers)
            .shuffled()
            .take(gameConfig.totalImpostors)
            .toSet()
        
        for (i in 0 until gameConfig.totalPlayers) {
            val isImpostor = i in impostorIndices
            val word = if (isImpostor) {
                if (gameConfig.giveClueToImpostor) {
                    themeClues[theme] ?: "Adivina la palabra"
                } else {
                    "IMPOSTOR"
                }
            } else {
                selectedWord
            }
            playerList.add(Player(i + 1, isImpostor, word))
        }
        
        players = playerList
        currentPlayerIndex = 0
        isGameStarted = true
        showVotingScreen = false
        votes = emptyMap()
    }
    
    fun nextPlayer() {
        if (currentPlayerIndex < players.size - 1) {
            currentPlayerIndex++
        } else {
            // Todos vieron su palabra, iniciar votación
            showVotingScreen = true
        }
    }
    
    fun vote(playerId: Int) {
        votes = votes.toMutableMap().apply {
            this[playerId] = (this[playerId] ?: 0) + 1
        }
    }
    
    fun getVoteResults(): List<VoteResult> {
        return votes.map { (playerId, voteCount) ->
            VoteResult(playerId, voteCount)
        }.sortedByDescending { it.votes }
    }
    
    fun resetGame() {
        gameConfig = GameConfig()
        players = emptyList()
        currentPlayerIndex = 0
        isGameStarted = false
        showVotingScreen = false
        votes = emptyMap()
    }
}
