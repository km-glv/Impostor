package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.screens.*
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Ocultar barras del sistema y notificaciones
        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.hide(WindowInsetsCompat.Type.statusBars())
        controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ImpostorGame()
                }
            }
        }
    }
}

@Composable
fun ImpostorGame() {
    val viewModel: GameViewModel = viewModel()
    val context = androidx.compose.ui.platform.LocalContext.current
    val prefs = remember { PlayerPreferences(context) }
    val audioMonitor = remember { AudioMonitor(context) }
    var currentScreen by remember { mutableStateOf(Screen.CONFIG) }
    var showResults by remember { mutableStateOf(false) }
    
    // Cargar configuración guardada al inicio
    LaunchedEffect(Unit) {
        val savedConfig = prefs.loadConfig()
        viewModel.updateConfig(savedConfig)
    }
    
    // Limpiar recursos al salir
    DisposableEffect(Unit) {
        onDispose {
            audioMonitor.cleanup()
        }
    }
    
    when {
        showResults -> {
            ResultsScreen(
                players = viewModel.players,
                voteResults = viewModel.getVoteResults(),
                onPlayAgain = {
                    viewModel.resetGame()
                    currentScreen = Screen.CONFIG
                    showResults = false
                }
            )
        }
        viewModel.showStartingPlayerScreen -> {
            StartingPlayerScreen(
                startingPlayerId = viewModel.startingPlayerId,
                onContinue = { viewModel.startVoting() }
            )
        }
        viewModel.showVotingScreen -> {
            VotingScreen(
                players = viewModel.players,
                onVote = { playerId -> viewModel.vote(playerId) },
                onShowResults = { showResults = true }
            )
        }
        viewModel.isGameStarted -> {
            GameScreen(
                currentPlayer = viewModel.players[viewModel.currentPlayerIndex],
                currentPlayerIndex = viewModel.currentPlayerIndex,
                totalPlayers = viewModel.players.size,
                onNextPlayer = { viewModel.nextPlayer() },
                onExitGame = {
                    audioMonitor.stopMonitoring()
                    viewModel.exitGame()
                    currentScreen = Screen.CONFIG
                },
                audioMonitor = if (viewModel.gameConfig.enableAudioMonitoring) audioMonitor else null
            )
        }
        else -> {
            when (currentScreen) {
                Screen.CONFIG -> {
                    ConfigScreen(
                        currentConfig = viewModel.gameConfig,
                        onConfigUpdate = { config -> 
                            viewModel.updateConfig(config)
                            prefs.saveConfig(config)
                        },
                        onNext = { currentScreen = Screen.THEME }
                    )
                }
                Screen.THEME -> {
                    ThemeSelectionScreen(
                        currentConfig = viewModel.gameConfig,
                        onThemeSelected = { theme ->
                            val updatedConfig = viewModel.gameConfig.copy(musicTheme = theme)
                            viewModel.updateConfig(updatedConfig)
                            prefs.saveConfig(updatedConfig)
                            viewModel.startGame()
                        },
                        onBack = { currentScreen = Screen.CONFIG }
                    )
                }
            }
        }
    }
}

enum class Screen {
    CONFIG,
    THEME
}