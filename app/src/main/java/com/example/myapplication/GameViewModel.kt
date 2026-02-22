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
        MusicTheme.CANSERBERO to listOf(
            "Microfonía", "Hip Hop", "Freestyle", "Venezuela", "Poesía",
            "Conciencia", "Lírica", "Tirone", "Maquiavélico", "Es Épico"
        ),
        MusicTheme.EPIC to listOf(
            "Orquesta", "Cinematográfico", "Heroico", "Épica", "Batalla",
            "Triunfo", "Gloria", "Aventura", "Destino", "Leyenda"
        ),
        MusicTheme.GOD_OF_WAR to listOf(
            "Kratos", "Esparta", "Olimpo", "Venganza", "Dioses",
            "Caos", "Guerra", "Titán", "Furia", "Leviatan"
        )
    )
    
    // Pistas para impostores según el tema
    private val themeClues = mapOf(
        MusicTheme.CANSERBERO to "Relacionado con música urbana venezolana",
        MusicTheme.EPIC to "Relacionado con música épica y heroica",
        MusicTheme.GOD_OF_WAR to "Relacionado con mitología griega y guerreros"
    )
    
    fun updateConfig(config: GameConfig) {
        gameConfig = config
    }
    
    fun startGame() {
        val theme = gameConfig.musicTheme ?: MusicTheme.CANSERBERO
        val words = themeWords[theme] ?: themeWords[MusicTheme.CANSERBERO]!!
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
