package com.example.myapplication.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.Player
import kotlin.math.abs
import kotlin.math.roundToInt

@Composable
fun GameScreen(
    currentPlayer: Player,
    currentPlayerIndex: Int,
    totalPlayers: Int,
    onNextPlayer: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isCardRevealed by remember { mutableStateOf(false) }
    var offsetX by remember { mutableStateOf(0f) }
    var showNextButton by remember { mutableStateOf(false) }
    
    // Resetear el estado cuando cambia el jugador
    LaunchedEffect(currentPlayerIndex) {
        isCardRevealed = false
        offsetX = 0f
        showNextButton = false
    }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "JUGADOR ${currentPlayer.id}",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            LinearProgressIndicator(
                progress = (currentPlayerIndex + 1).toFloat() / totalPlayers,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
            
            Text(
                text = "Jugador ${currentPlayerIndex + 1} de $totalPlayers",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        // Card con la palabra
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (!isCardRevealed) {
                // Instrucciones
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = "👀",
                            fontSize = 48.sp
                        )
                        Text(
                            text = "¡Asegúrate de que nadie más pueda ver la pantalla!",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Desliza la tarjeta hacia la izquierda para ver tu palabra",
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                }
                
                // Tarjeta deslizable
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                        .offset { IntOffset(offsetX.roundToInt(), 0) }
                        .pointerInput(Unit) {
                            detectHorizontalDragGestures(
                                onDragEnd = {
                                    if (offsetX < -300f) {
                                        // Deslizó suficiente, revelar
                                        isCardRevealed = true
                                        showNextButton = true
                                    } else {
                                        // Volver a posición original
                                        offsetX = 0f
                                    }
                                },
                                onHorizontalDrag = { _, dragAmount ->
                                    val newOffset = offsetX + dragAmount
                                    if (newOffset < 0) {
                                        offsetX = newOffset
                                    }
                                }
                            )
                        }
                        .alpha(1f - abs(offsetX) / 1000f),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "← Desliza",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            } else {
                // Mostrar la palabra
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (currentPlayer.isImpostor) {
                            MaterialTheme.colorScheme.errorContainer
                        } else {
                            MaterialTheme.colorScheme.primaryContainer
                        }
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        if (currentPlayer.isImpostor) {
                            Text(
                                text = "🎭",
                                fontSize = 60.sp
                            )
                            Text(
                                text = "¡ERES EL IMPOSTOR!",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Center
                            )
                        } else {
                            Text(
                                text = "✅",
                                fontSize = 60.sp
                            )
                        }
                        
                        Text(
                            text = currentPlayer.word,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            color = if (currentPlayer.isImpostor) {
                                MaterialTheme.colorScheme.onErrorContainer
                            } else {
                                MaterialTheme.colorScheme.onPrimaryContainer
                            }
                        )
                        
                        if (currentPlayer.isImpostor) {
                            Text(
                                text = "Debes descubrir cuál es la palabra secreta sin que te descubran",
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        } else {
                            Text(
                                text = "Memoriza esta palabra y trata de identificar al impostor",
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
        }
        
        // Botón siguiente jugador
        if (showNextButton) {
            Button(
                onClick = onNextPlayer,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = if (currentPlayerIndex < totalPlayers - 1) {
                        "Siguiente Jugador →"
                    } else {
                        "Iniciar Votación 🗳️"
                    },
                    fontSize = 18.sp
                )
            }
        } else {
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}
