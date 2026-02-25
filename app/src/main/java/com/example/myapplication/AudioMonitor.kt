package com.example.myapplication

import android.content.Context
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.speech.tts.TextToSpeech
import kotlinx.coroutines.*
import java.util.Locale
import kotlin.math.abs

class AudioMonitor(private val context: Context) {
    private var audioRecord: AudioRecord? = null
    private var isMonitoring = false
    private var monitoringJob: Job? = null
    private var tts: TextToSpeech? = null
    private var lastShushTime = 0L
    private val shushCooldown = 3000L // 3 segundos entre cada "SHHH"
    
    // Configuración de audio
    private val sampleRate = 44100
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
    
    // Umbral de ruido (ajustable)
    private val noiseThreshold = 5000
    
    init {
        // Inicializar Text-to-Speech
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                tts?.language = Locale("es", "ES")
            }
        }
    }
    
    fun startMonitoring() {
        if (isMonitoring) return
        
        try {
            audioRecord = AudioRecord(
                MediaRecorder.AudioSource.MIC,
                sampleRate,
                channelConfig,
                audioFormat,
                bufferSize
            )
            
            audioRecord?.startRecording()
            isMonitoring = true
            
            monitoringJob = CoroutineScope(Dispatchers.IO).launch {
                val buffer = ShortArray(bufferSize)
                
                while (isMonitoring) {
                    val readResult = audioRecord?.read(buffer, 0, bufferSize) ?: 0
                    
                    if (readResult > 0) {
                        // Calcular amplitud promedio
                        var sum = 0.0
                        for (i in 0 until readResult) {
                            sum += abs(buffer[i].toDouble())
                        }
                        val amplitude = sum / readResult
                        
                        // Si supera el umbral, reproducir SHHH
                        if (amplitude > noiseThreshold) {
                            val currentTime = System.currentTimeMillis()
                            if (currentTime - lastShushTime > shushCooldown) {
                                withContext(Dispatchers.Main) {
                                    playShush()
                                }
                                lastShushTime = currentTime
                            }
                        }
                    }
                    
                    delay(100) // Revisar cada 100ms
                }
            }
        } catch (e: SecurityException) {
            // Permiso no concedido
            isMonitoring = false
        } catch (e: Exception) {
            isMonitoring = false
        }
    }
    
    fun stopMonitoring() {
        isMonitoring = false
        monitoringJob?.cancel()
        audioRecord?.stop()
        audioRecord?.release()
        audioRecord = null
    }
    
    private fun playShush() {
        tts?.speak("SHHH", TextToSpeech.QUEUE_FLUSH, null, null)
    }
    
    fun cleanup() {
        stopMonitoring()
        tts?.stop()
        tts?.shutdown()
    }
}
