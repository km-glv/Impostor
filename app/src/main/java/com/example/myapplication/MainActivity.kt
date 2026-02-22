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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.screens.*
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
    var currentScreen by remember { mutableStateOf(Screen.CONFIG) }
    var showResults by remember { mutableStateOf(false) }
    
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
                onNextPlayer = { viewModel.nextPlayer() }
            )
        }
        else -> {
            when (currentScreen) {
                Screen.CONFIG -> {
                    ConfigScreen(
                        currentConfig = viewModel.gameConfig,
                        onConfigUpdate = { config -> viewModel.updateConfig(config) },
                        onNext = { currentScreen = Screen.THEME }
                    )
                }
                Screen.THEME -> {
                    ThemeSelectionScreen(
                        currentConfig = viewModel.gameConfig,
                        onThemeSelected = { theme ->
                            viewModel.updateConfig(
                                viewModel.gameConfig.copy(musicTheme = theme)
                            )
                            viewModel.startGame()
                        }
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