package com.kimdo.myrunningapp.di

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.kimdo.myrunningapp.R
import com.kimdo.myrunningapp.other.Constants
import com.kimdo.myrunningapp.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ServiceComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ServiceScoped


@Module
@InstallIn(ServiceComponent::class)
object ServiceModule {

    @ServiceScoped
    @Provides
    fun provideFusedLocationProviderClient(@ApplicationContext context: Context) = FusedLocationProviderClient(context)


    @ServiceScoped
    @Provides
    fun providePendingIntent( @ApplicationContext context: Context): PendingIntent {
        return PendingIntent.getActivity(context,0,
            Intent(context, MainActivity::class.java).also {
                it.action = Constants.ACTION_SHOW_TRACKING_FRAGMENT
            },
            PendingIntent.FLAG_UPDATE_CURRENT  or PendingIntent.FLAG_MUTABLE
        )
    }

    @ServiceScoped
    @Provides
    fun provideNotificationBuilder( @ApplicationContext context: Context, pendingIntent: PendingIntent) : NotificationCompat.Builder {
        return NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setAutoCancel(false)
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_directions_run_black_24dp)
            .setContentTitle("Running My App")
            .setContentText("00:00:00")
            .setContentIntent( pendingIntent )
    }
}