package com.company.bt_service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Messenger
import android.util.Log

private const val TAG = "SERVICE_LOG"

class BtService : Service() {
	private val incomingHandler = object : Handler(Looper.getMainLooper()) {
		override fun handleMessage(msg: Message) {
			when (msg.what) {
				1 -> {
					val input = msg.arg1
					Log.wtf(TAG, "PROCES :test_proc PŘIJAL: $input")
					val result = input * 2
					val replyTo = msg.replyTo
					replyTo?.send(Message.obtain(null, 2, result, 0))
				}
			}
		}
	}

	private val messenger = Messenger(incomingHandler)

	override fun onBind(intent: Intent?): IBinder? {
		Log.wtf(TAG, "onBind zavolán - vracím Messenger")
		return messenger.binder
	}
}