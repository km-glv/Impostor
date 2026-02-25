package com.example.myapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class GameNotificationHelper(private val context: Context) {
    
    companion object {
        private const val CHANNEL_ID = "impostor_game_info"
        private const val NOTIFICATION_ID = 1001
    }
    
    init {
        createNotificationChannel()
    }
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Información del Juego"
            val descriptionText = "Notificaciones con la palabra e impostores del juego"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
                setShowBadge(false)
            }
            
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    fun sendGameInfoNotification(word: String, impostorIds: List<Int>) {
        val impostorsList = if (impostorIds.size == 1) {
            "Impostor: Jugador ${impostorIds[0]}"
        } else {
            "Impostores: ${impostorIds.joinToString(", ") { "Jugador $it" }}"
        }
        
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("🎮 Información del Juego")
            .setContentText("Palabra: $word")
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("📝 Palabra: $word\n\n🎭 $impostorsList"))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOngoing(true) // No se puede deslizar para cerrar
            .setAutoCancel(false)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE) // Oculta en pantalla de bloqueo
            .build()
        
        try {
            NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
        } catch (e: SecurityException) {
            // Permiso no concedido
        }
    }
    
    fun cancelGameInfoNotification() {
        NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID)
    }
}
