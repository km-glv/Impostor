package com.example.myapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Player
import com.example.myapplication.VoteResult

@Composable
fun VotingScreen(
    players: List<Player>,
    onVote: (Int) -> Unit,
    onShowResults: () -> Unit,
    modifier: Modifier = Modifier
) {
    var votesCount by remember { mutableStateOf(0) }
    var hasVoted by remember { mutableStateOf(false) }
    var selectedPlayerId by remember { mutableStateOf<Int?>(null) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🗳️ VOTACIÓN 🗳️",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "¿Quién crees que es el impostor?",
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(players) { player ->
                PlayerVoteCard(
                    player = player,
                    isSelected = selectedPlayerId == player.id,
                    onClick = {
                        selectedPlayerId = player.id
                    }
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        if (selectedPlayerId != null && !hasVoted) {
            Button(
                onClick = {
                    selectedPlayerId?.let { playerId ->
                        onVote(playerId)
                        hasVoted = true
                        selectedPlayerId = null
                        votesCount++
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Confirmar Voto ✓", fontSize = 18.sp)
            }
        } else if (hasVoted) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "✅ Voto registrado",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Esperando a que todos voten...",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = onShowResults,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Ver Resultados 📊", fontSize = 18.sp)
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun PlayerVoteCard(
    player: Player,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = if (isSelected) {
            BorderStroke(3.dp, MaterialTheme.colorScheme.primary)
        } else {
            BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
        },
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isSelected) 6.dp else 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "👤",
                    fontSize = 32.sp
                )
                Text(
                    text = "Jugador ${player.id}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) {
                        MaterialTheme.colorScheme.onPrimaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                )
            }
            
            if (isSelected) {
                Text(
                    text = "✓",
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
fun ResultsScreen(
    players: List<Player>,
    voteResults: List<VoteResult>,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val impostors = players.filter { it.isImpostor }
    val mostVotedPlayerId = voteResults.maxByOrNull { it.votes }?.playerId
    val mostVotedPlayer = players.find { it.id == mostVotedPlayerId }
    
    val success = mostVotedPlayer?.isImpostor == true
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        
        Text(
            text = if (success) "🎉 ¡VICTORIA! 🎉" else "😢 DERROTA 😢",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = if (success) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        )
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = if (success) {
                    MaterialTheme.colorScheme.primaryContainer
                } else {
                    MaterialTheme.colorScheme.errorContainer
                }
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = if (success) {
                        "¡Atraparon al impostor!"
                    } else {
                        "El impostor escapó..."
                    },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                
                Divider()
                
                Text(
                    text = "El impostor era:",
                    fontSize = 16.sp
                )
                
                impostors.forEach { impostor ->
                    Text(
                        text = "🎭 Jugador ${impostor.id}",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                
                if (players.first { !it.isImpostor }.word.isNotEmpty()) {
                    Divider()
                    Text(
                        text = "La palabra era:",
                        fontSize = 16.sp
                    )
                    Text(
                        text = players.first { !it.isImpostor }.word,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "📊 Resultados de la Votación",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Divider()
                
                if (voteResults.isEmpty()) {
                    Text(
                        text = "No hubo votos",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                } else {
                    voteResults.forEach { result ->
                        val player = players.find { it.id == result.playerId }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Jugador ${result.playerId}",
                                    fontSize = 18.sp,
                                    fontWeight = if (result.playerId == mostVotedPlayerId) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    }
                                )
                                if (player?.isImpostor == true) {
                                    Text(text = "🎭", fontSize = 18.sp)
                                }
                            }
                            Text(
                                text = "${result.votes} ${if (result.votes == 1) "voto" else "votos"}",
                                fontSize = 18.sp,
                                fontWeight = if (result.playerId == mostVotedPlayerId) {
                                    FontWeight.Bold
                                } else {
                                    FontWeight.Normal
                                },
                                color = if (result.playerId == mostVotedPlayerId) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                }
                            )
                        }
                    }
                }
            }
        }
        
        Button(
            onClick = onPlayAgain,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Jugar de Nuevo 🔄", fontSize = 18.sp)
        }
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}
