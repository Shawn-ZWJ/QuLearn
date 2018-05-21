package com.viaviapp.hdvideo;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.example.util.AnalyticsTrackers;
import com.example.util.TypefaceUtil;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.StandardExceptionParser;
import com.google.android.gms.analytics.Tracker;
import com.onesignal.OneSignal;

public class MyApplication extends Application {

  public MyApplication() {
  }

  @Override
  public void onCreate() {
    super.onCreate();
    OneSignal.startInit(this).init();
    AnalyticsTrackers.initialize(this);
    AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);
    TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "myfonts/Roboto-Regular.ttf");
  }

  public synchronized Tracker getGoogleAnalyticsTracker() {
    AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
    return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
  }
  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    MultiDex.install(this);
  }


  public void trackScreenView(String screenName) {
    Tracker t = getGoogleAnalyticsTracker();

    // Set screen name.
    t.setScreenName(screenName);

    // Send a screen view.
    t.send(new HitBuilders.ScreenViewBuilder().build());

    GoogleAnalytics.getInstance(this).dispatchLocalHits();
  }


  public void trackException(Exception e) {
    if (e != null) {
      Tracker t = getGoogleAnalyticsTracker();

      t.send(new HitBuilders.ExceptionBuilder()
              .setDescription(
                      new StandardExceptionParser(this, null)
                              .getDescription(Thread.currentThread().getName(), e))
              .setFatal(false)
              .build()
      );
    }
  }

  public void trackEvent(String category, String action, String label) {
    Tracker t = getGoogleAnalyticsTracker();

    // Build and send an Event.
    t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
  }
}