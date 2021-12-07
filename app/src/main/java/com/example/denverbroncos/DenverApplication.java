package com.example.denverbroncos;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.Region;

public class DenverApplication extends Application implements MonitorNotifier {

    public static final Region denverStadiumRegion = new Region(DenverStadiumConstants.DENVER_STADIUM_UUID, null, null, null);

    @Override
    public void onCreate() {
        super.onCreate();
        BeaconManager beaconManager = org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this);
        beaconManager.getBeaconParsers().clear();
        beaconManager.getBeaconParsers().add(new BeaconParser().
        setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        beaconManager.addMonitorNotifier(this);
        for (Region region: beaconManager.getMonitoredRegions()) {
            beaconManager.stopMonitoring(region);
        }

        beaconManager.startMonitoring(denverStadiumRegion);
    }

    @Override
    public void didEnterRegion(Region region) {
        sendNotification("Welcome to Empower Field at Mile High", "Head to your ticketed section. Exciting offers waiting for you. Enjoy and game. Go Broncos!!!!");
        org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this).startRangingBeacons(region);
    }

    @Override
    public void didExitRegion(Region region) {
        sendNotification("Thank you for visiting the Stadium", "Hope you had wonderful time. See you soon.");
        org.altbeacon.beacon.BeaconManager.getInstanceForApplication(this).stopRangingBeacons(region);
    }

    @Override
    public void didDetermineStateForRegion(int state, Region region) {

    }


    private void sendNotification(final String message, final String description) {
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Beacon Reference Notifications",
                    "Beacon Reference Notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
            notificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(this, channel.getId());
        }
        else {
            builder = new Notification.Builder(this);
            builder.setPriority(Notification.PRIORITY_HIGH);
        }

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(new Intent(this, HomeScreen.class));
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setContentTitle(message);
        builder.setContentText(description);
        builder.setContentIntent(resultPendingIntent);
        notificationManager.notify(1, builder.build());
    }
}
