package com.company.bt

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel

private const val TAG = "TEST_TEST"

class MainActivity : ComponentActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			Scaffold {
				Column(
					modifier = Modifier.padding(it)
				) {
					Button(onClick = {
						sendData(10)
					}) {
						Text("Send")
					}
				}
			}
		}
	}
}

class VM : ViewModel() {
	private var serviceMessenger: Messenger? = null
	private var isBound = false

	private val clientHandler = object : Handler(Looper.getMainLooper()) {
		override fun handleMessage(msg: Message) {
			if (msg.what == 2) {

			}
		}
	}

	private fun sendData(value: Int) {
		if (!isBound) return
		val msg = Message.obtain(null, 1, value, 0)
		msg.replyTo = Messenger(clientHandler)
		serviceMessenger?.send(msg)
	}

	private val connection = object : ServiceConnection {
		override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
			serviceMessenger = Messenger(service)
			isBound = true
			Log.wtf(TAG, "Connected to the service")
		}

		override fun onServiceDisconnected(name: ComponentName?) {
			serviceMessenger = null
			isBound = false
		}
	}


	fun onStart() {
		val intent = Intent()
		intent.component = ComponentName(
			"com.company.bt_service",
			"com.company.bt_service.BtService"
		)
		bindService(intent, connection, BIND_AUTO_CREATE)
	}
}