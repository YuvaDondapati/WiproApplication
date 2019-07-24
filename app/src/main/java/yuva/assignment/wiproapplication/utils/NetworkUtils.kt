package yuva.assignment.wiproapplication.utils

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils(private var context: Context) {

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =  context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}