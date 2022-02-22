package net.hennessen.androidbatteryapitest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import net.hennessen.androidbatteryapitest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // Button to check power state explicitly
        val checkChargeButton = findViewById<Button>(R.id.check_charge_button)
        checkChargeButton.setOnClickListener {
            updateLabel(this)
        }

        updateLabel(this)

        // Start the service listening for power connected/disconnected actions in foreground
        Intent(this, BatteryService::class.java).also { intent ->
            startForegroundService(intent)
        }

        val localReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                    context?.let {
                        updateLabel(context)
                    }
            }
        }

        // Listen for updates from the service via local broadcast manager
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        LocalBroadcastManager.getInstance(this).registerReceiver(localReceiver, intentFilter)
    }

    fun updateLabel(context: Context){
        val isCharging = BatteryMonitor().checkIsCharging(context)
        val isChargingLabel = findViewById<TextView>(R.id.is_charging_label)
        isChargingLabel.setText(if (isCharging) "Power connected" else "Power disconnected")
    }

}