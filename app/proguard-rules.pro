# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in C:\Users\owner\AppData\Local\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.print_web_view {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class	org.slf4j.*
-keep class	ch.qos.logback.core.net.*
-keep class	android.** { *; }
-keep class	butterknife.*
-keep class	com.bumptech.*
-keep class	com.github.*
-keep class	com.google.*
-keep class	com.hbb20.*
-keep class	com.kaopiz.*
-keep class	com.mikepenz.*
-keep class com.shockwave.**
-keepclassmembers class com.shockwave.** { *; }
-keep class	com.tom_roush.*
-keep class	io.*
-keep class	java.*
-keep class	org.androidannotations.*
-keep class com.valdesekamdem.*
-keep class com.google.android.gms.** { *; }
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}

-dontwarn org.slf4j.**
-dontwarn ch.qos.logback.core.net.**
-dontwarn com.google.android.gms.**
-dontwarn	android.**
-dontwarn	butterknife.*
-dontwarn	com.bumptech.*
-dontwarn	com.github.*
-dontwarn	com.google.*
-dontwarn	com.hbb20.*
-dontwarn	com.kaopiz.*
-dontwarn	com.mikepenz.*
-dontwarn	com.shockwave.**
-dontwarn	com.tom_roush.*
-dontwarn	io.*
-dontwarn	java.*
-dontwarn	org.androidannotations.*
-dontwarn   com.valdesekamdem.*
-keep class	android.util.FloatMath
-keep class	java.beans.ConstructorProperties
-keep class	java.beans.Transient
-keep class	java.lang.management.ManagementFactory
-keep class	java.lang.management.RuntimeMXBean
-keep class	java.lang.management.ThreadMXBean
-keep class	javax.naming.Binding
-keep class	javax.naming.Binding
-keep class	javax.naming.Context
-keep class	javax.naming.directory.Attribute
-keep class	javax.naming.directory.Attributes
-keep class	javax.naming.directory.DirContext
-keep class	javax.naming.directory.InitialDirContext
-keep class	javax.naming.directory.SearchControls
-keep class	javax.naming.directory.SearchResult
-keep class	javax.naming.NamingEnumeration
-keep class	javax.naming.NamingException
-keep class	javax.xml.stream.XMLStreamException
-keep class	javax.xml.stream.XMLStreamWriter
-keep class	junit.framework.TestCase
-keep class	junit.framework.TestSuite
-keep class	junit.runner.BaseTestRunner
-keep class	org.w3c.dom.bootstrap.DOMImplementationRegistry

-keepclasseswithmembers class **.R$* {
    public static final int define_*;
}

-dontwarn	android.util.FloatMath
-dontwarn	java.beans.ConstructorProperties
-dontwarn	java.beans.Transient
-dontwarn	java.lang.management.ManagementFactory
-dontwarn	java.lang.management.RuntimeMXBean
-dontwarn	java.lang.management.ThreadMXBean
-dontwarn	javax.naming.Binding
-dontwarn	javax.naming.Context
-dontwarn	javax.naming.directory.Attribute
-dontwarn	javax.naming.directory.Attributes
-dontwarn	javax.naming.directory.DirContext
-dontwarn	javax.naming.directory.InitialDirContext
-dontwarn	javax.naming.directory.SearchControls
-dontwarn	javax.naming.directory.SearchResult
-dontwarn	javax.naming.NamingEnumeration
-dontwarn	javax.naming.NamingException
-dontwarn	javax.xml.stream.XMLStreamException
-dontwarn	javax.xml.stream.XMLStreamWriter
-dontwarn	junit.framework.TestCase
-dontwarn	junit.framework.TestSuite
-dontwarn	junit.runner.BaseTestRunner
-dontwarn	org.w3c.dom.bootstrap.DOMImplementationRegistry
