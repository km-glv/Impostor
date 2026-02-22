package com.example.myapplication

// Enum para los diferentes temas
enum class MusicTheme(val displayName: String) {
    MUSIC("Música"),
    CHARACTERS("Personajes"),
    MOVIES("Películas"),
    GAMES("Videojuegos"),
    ANIME("Anime")
}

// Configuración del juego
data class GameConfig(
    val totalPlayers: Int = 3,
    val totalImpostors: Int = 1,
    val giveClueToImpostor: Boolean = false,
    val musicTheme: MusicTheme? = null
)

// Estado del jugador
data class Player(
    val id: Int,
    val isImpostor: Boolean,
    val word: String
)

// Resultado de votación
data class VoteResult(
    val playerId: Int,
    val votes: Int
)
