package cainwong.vegan.util

import android.util.Log

inline fun Any.log(msg: () -> String) = logD(msg)

inline fun Any.logD(msg: () -> String) {
    Log.d(this::class.java.simpleName, msg())
}

inline fun Any.logI(msg: () -> String) {
    Log.i(this::class.java.simpleName, msg())
}

inline fun Any.logW(msg: () -> String, throwable: Throwable?) {
    Log.w(this::class.java.simpleName, msg(), throwable)
}

inline fun Any.logE(throwable: Throwable? = null, msg: () -> String) {
    Log.e(this::class.java.simpleName, msg(), throwable)
}

inline fun Any.logWTF(throwable: Throwable? = null, msg: () -> String) {
    Log.wtf(this::class.java.simpleName, msg(), throwable)
}
