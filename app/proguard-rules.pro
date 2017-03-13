-dontoptimize
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
#-dontpreverify
-verbose
-dontwarn
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes InnerClasses,LineNumberTable

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.app.View
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference

-keep class android.net.http.SslError
-keep class android.webkit.**{*;}
-keep class com.mob.**{*;}
-keep class org.apache.poi.**{*;}
-keep class org.apache.xmlbeans.**{*;}
-keep class org.repackage.**{*;}
-keep class org.schemaorg_apache_xmlbeans.**{*;}

-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}

-keepclasseswithmembernames class * {
    native <methods>;
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keep class **.R$* {
    *;
}

-keepattributes EnclosingMethod

-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
