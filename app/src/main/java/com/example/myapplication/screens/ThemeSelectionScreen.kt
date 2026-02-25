package com.example.myapplication.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
import com.example.myapplication.GameConfig
import com.example.myapplication.MusicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeSelectionScreen(
    currentConfig: GameConfig,
    onThemeSelected: (MusicTheme) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTheme by remember { mutableStateOf(currentConfig.musicTheme) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Header
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "🎵 Selecciona el Tema 🎵",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "El tema define las palabras del juego",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        
        // Temas en grid scrolleable
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
        ThemeCard(
            theme = MusicTheme.MUSIC,
            icon = "🎵",
            description = "Música",
            subtitle = "Artistas, canciones y géneros",
            isSelected = selectedTheme == MusicTheme.MUSIC,
            onClick = { selectedTheme = MusicTheme.MUSIC }
        )
        
        // Tarjeta de Personajes
        ThemeCard(
            theme = MusicTheme.CHARACTERS,
            icon = "🎭",
            description = "Personajes",
            subtitle = "Superhéroes, actores, históricos",
            isSelected = selectedTheme == MusicTheme.CHARACTERS,
            onClick = { selectedTheme = MusicTheme.CHARACTERS }
        )
        
        // Tarjeta de Películas
        ThemeCard(
            theme = MusicTheme.MOVIES,
            icon = "🎬",
            description = "Películas",
            subtitle = "Acción, terror, comedia y cine",
            isSelected = selectedTheme == MusicTheme.MOVIES,
            onClick = { selectedTheme = MusicTheme.MOVIES }
        )
        
        // Tarjeta de Videojuegos
        ThemeCard(
            theme = MusicTheme.GAMES,
            icon = "🎮",
            description = "Videojuegos",
            subtitle = "Títulos populares",
            isSelected = selectedTheme == MusicTheme.GAMES,
            onClick = { selectedTheme = MusicTheme.GAMES }
        )
        
        // Tarjeta de Anime
        ThemeCard(
            theme = MusicTheme.ANIME,
            icon = "⚔️",
            description = "Anime",
            subtitle = "Series y personajes japoneses",
            isSelected = selectedTheme == MusicTheme.ANIME,
            onClick = { selectedTheme = MusicTheme.ANIME }
        )
        
        // Tarjeta de Chile
        ThemeCard(
            theme = MusicTheme.CHILE,
            icon = "🇨🇱",
            description = "Chile",
            subtitle = "Cultura y tradiciones chilenas",
            isSelected = selectedTheme == MusicTheme.CHILE,
            onClick = { selectedTheme = MusicTheme.CHILE }
        )
        }
        
        // Botones siempre visibles
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = {
                    selectedTheme?.let { onThemeSelected(it) }
                },
                enabled = selectedTheme != null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("Comenzar Juego 🎮", fontSize = 18.sp)
            }
            
            OutlinedButton(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("← Volver a Configuración", fontSize = 16.sp)
            }
        }
    }
}

@Composable
private fun ThemeCard(
    theme: MusicTheme,
    icon: String,
    description: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp),
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
            defaultElevation = if (isSelected) 8.dp else 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = icon,
                fontSize = 40.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                color = if (isSelected) {
                    MaterialTheme.colorScheme.onPrimaryContainer
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                textAlign = TextAlign.Center
            )
        }
    }
}
