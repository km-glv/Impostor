package com.example.myapplication

import android.content.Context
import android.content.SharedPreferences

class PlayerPreferences(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("impostor_prefs", Context.MODE_PRIVATE)
    
    companion object {
        private const val KEY_TOTAL_PLAYERS = "total_players"
        private const val KEY_TOTAL_IMPOSTORS = "total_impostors"
        private const val KEY_GIVE_CLUE = "give_clue"
        private const val KEY_MUSIC_THEME = "music_theme"
    }
    
    fun saveConfig(config: GameConfig) {
        prefs.edit().apply {
            putInt(KEY_TOTAL_PLAYERS, config.totalPlayers)
            putInt(KEY_TOTAL_IMPOSTORS, config.totalImpostors)
            putBoolean(KEY_GIVE_CLUE, config.giveClueToImpostor)
            config.musicTheme?.let { putString(KEY_MUSIC_THEME, it.name) }
            apply()
        }
    }
    
    fun loadConfig(): GameConfig {
        return GameConfig(
            totalPlayers = prefs.getInt(KEY_TOTAL_PLAYERS, 3),
            totalImpostors = prefs.getInt(KEY_TOTAL_IMPOSTORS, 1),
            giveClueToImpostor = prefs.getBoolean(KEY_GIVE_CLUE, true),
            musicTheme = prefs.getString(KEY_MUSIC_THEME, null)?.let { 
                try {
                    MusicTheme.valueOf(it)
                } catch (e: Exception) {
                    null
                }
            }
        )
    }
}
