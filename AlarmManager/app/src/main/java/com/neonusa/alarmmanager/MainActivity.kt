package com.neonusa.alarmmanager

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.neonusa.alarmmanager.databinding.ActivityMainBinding

/*
    Catatan pneting
    meski kita mengubah waktu alarm
    misal dari 21.20 (sudah tertrigger notifikasi muncul)
    lalu kita ubah atau set ulang alarm menjadi 21.25
    maka notifikasi tidak akan muncul di 21.25 melainkan 21.20 besok hari
    cara untuk mengubah waktu alarm adalah dengan mengcancel alarm service yang sudah berjalan terlebih dahulu

    KESIMPULAN URUTAN
    1.SET ALARM
    2.CANCEL
    3.RESCHEDULE (SET ALARM ULANG)
 */



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var alarmReceiver: AlarmReceiver

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Notifications permission granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications permission rejected", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alarmReceiver = AlarmReceiver()

        if (Build.VERSION.SDK_INT >= 33) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }

        binding.btnSetAlarm.setOnClickListener {
            if (binding.edtTime.text.isNullOrEmpty()){
                binding.edtTime.setError("Harap mengisi waktu terlebih dahulu")
                return@setOnClickListener
            }
            val time = binding.edtTime.text.toString()
            val message = if(binding.edtMessage.text.isNullOrEmpty()) "Default message" else binding.edtMessage.text
            alarmReceiver.setRepeatingAlarm(
                this, AlarmReceiver.TYPE_REPEATING,
                time, "message"
            )
        }

    }
}