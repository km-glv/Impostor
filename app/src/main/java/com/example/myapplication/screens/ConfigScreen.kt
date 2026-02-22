package com.example.myapplication.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.GameConfig

@Composable
fun ConfigScreen(
    currentConfig: GameConfig,
    onConfigUpdate: (GameConfig) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    var totalPlayers by remember { mutableStateOf(currentConfig.totalPlayers) }
    var totalImpostors by remember { mutableStateOf(currentConfig.totalImpostors) }
    var giveClue by remember { mutableStateOf(currentConfig.giveClueToImpostor) }
    var showError by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        
        Text(
            text = "🎭 EL IMPOSTOR 🎭",
            fontSize = 36.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Configuración del Juego",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                
                // Número de jugadores
                Text(
                    text = "Número de Jugadores: $totalPlayers",
                    fontSize = 16.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { if (totalPlayers > 3) totalPlayers-- },
                        enabled = totalPlayers > 3
                    ) {
                        Text("-")
                    }
                    Text(
                        text = "$totalPlayers jugadores",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = { if (totalPlayers < 20) totalPlayers++ }
                    ) {
                        Text("+")
                    }
                }
                
                Divider()
                
                // Número de impostores
                Text(
                    text = "Número de Impostores: $totalImpostors",
                    fontSize = 16.sp
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { if (totalImpostors > 1) totalImpostors-- },
                        enabled = totalImpostors > 1
                    ) {
                        Text("-")
                    }
                    Text(
                        text = "$totalImpostors ${if (totalImpostors == 1) "impostor" else "impostores"}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Button(
                        onClick = { if (totalImpostors < totalPlayers - 1) totalImpostors++ },
                        enabled = totalImpostors < totalPlayers - 1
                    ) {
                        Text("+")
                    }
                }
                
                Divider()
                
                // Dar pista al impostor
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "¿Dar pista al impostor?",
                        fontSize = 16.sp
                    )
                    Switch(
                        checked = giveClue,
                        onCheckedChange = { giveClue = it }
                    )
                }
                
                if (giveClue) {
                    Text(
                        text = "ℹ️ Los impostores verán una pista del tema",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
        
        if (showError) {
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            ) {
                Text(
                    text = "⚠️ El número de impostores no puede ser mayor o igual al número de jugadores",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.onErrorContainer
                )
            }
        }
        
        Button(
            onClick = {
                if (totalImpostors >= totalPlayers) {
                    showError = true
                } else {
                    showError = false
                    onConfigUpdate(
                        GameConfig(
                            totalPlayers = totalPlayers,
                            totalImpostors = totalImpostors,
                            giveClueToImpostor = giveClue
                        )
                    )
                    onNext()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Continuar →", fontSize = 18.sp)
        }
        
        Spacer(modifier = Modifier.height(20.dp))
    }
}
