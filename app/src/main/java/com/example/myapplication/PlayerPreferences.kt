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
        private const val KEY_AUDIO_MONITORING = "audio_monitoring"
        private const val KEY_SEND_TO_WATCH = "send_to_watch"
    }
    
    fun saveConfig(config: GameConfig) {
        prefs.edit().apply {
            putInt(KEY_TOTAL_PLAYERS, config.totalPlayers)
            putInt(KEY_TOTAL_IMPOSTORS, config.totalImpostors)
            putBoolean(KEY_GIVE_CLUE, config.giveClueToImpostor)
            putBoolean(KEY_AUDIO_MONITORING, config.enableAudioMonitoring)
            putBoolean(KEY_SEND_TO_WATCH, config.sendInfoToWatch)
            config.musicTheme?.let { putString(KEY_MUSIC_THEME, it.name) }
            apply()
        }
    }
    
    fun loadConfig(): GameConfig {
        return GameConfig(
            totalPlayers = prefs.getInt(KEY_TOTAL_PLAYERS, 3),
            totalImpostors = prefs.getInt(KEY_TOTAL_IMPOSTORS, 1),
            giveClueToImpostor = prefs.getBoolean(KEY_GIVE_CLUE, true),
            enableAudioMonitoring = prefs.getBoolean(KEY_AUDIO_MONITORING, false),
            sendInfoToWatch = prefs.getBoolean(KEY_SEND_TO_WATCH, false),
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
