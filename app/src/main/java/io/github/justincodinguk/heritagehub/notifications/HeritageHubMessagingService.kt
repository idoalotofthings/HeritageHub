package io.github.justincodinguk.heritagehub.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import io.github.justincodinguk.core.data.repository.AuthRepository
import io.github.justincodinguk.core.data.repository.UserRepository
import io.github.justincodinguk.heritagehub.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HeritageHubMessagingService @Inject constructor(
    //private val userRepository: UserRepository,
    //private val authRepository: AuthRepository
) : FirebaseMessagingService() {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("TOKEN", token)
        coroutineScope.launch {
            //val currentUser = authRepository.getCurrentUser()!!
            //userRepository.updateUser(currentUser.copy(tokenId = token))
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        val intent = Intent(this, MainActivity::class.java)
            .putExtra("postId", message.data["postId"])
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val notif = NotificationCompat.Builder(this, "heritagehub")
            .setContentIntent(pendingIntent)
            .build()

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(9, notif)
    }

}
