Android OpenX Widget
====================

This code implements a widget for Android applications to display ads with
the help of OpenX Ad Server. It was successfully tested with OpenX Community
Edition (a.k.a. OpenX Source) version 2.8.8-rc6 (the most recent at the
moment).

It basically wraps a WebView inside ViewGroup and provides an interface to
set ad delivery parameters and to load ads.

To use this widget just include it in your layout.xml file as any other View
component. For example:

```xml
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:ox="http://denivip.ru/schemas/android/openx/0.1"
            android:orientation="vertical"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
  <ru.denivip.android.widgets.OpenxAdView
            android:id="@+id/adview"
            android:layout_width="88px"
            android:layout_height="31px" 
            ox:delivery_url="@string/openxDeliveryUrl"
            ox:zone_id="3" />
  <ListView android:layout_width="match_parent"
            android:id="@+id/contactList"
            android:layout_height="wrap_content"
            android:layout_weight="1"/>
</LinearLayout>
```

The widget supports the following parameters, which can be set both in layout
file (as values or links to resources) or using accessors provided by class.

 * delivery_url The path to server and directory containing OpenX delivery
   scripts in the form servername/path. For example:
   openx.example.com/delivery.
 * js_tag_url The name of OpenX script that serves ad code for simple
   JavaScript type tag. Default: ajs.php.
 * zone_id The ID of OpenX zone from which ads should be selected to display
   inside the widget.
 * has_https Set this to true if ads should be served over HTTPS protocol.
   Default: false.
 * source This parameter is optional. It can be used to target ads by its
   value.

