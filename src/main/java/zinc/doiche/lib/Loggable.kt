package zinc.doiche.lib

import zinc.doiche.plugin

abstract class Loggable {
    fun info(message: Any?, vararg args: Any) {
        plugin.slF4JLogger.info(message?.toString() ?: "null", *args)
    }

    fun warn(message: Any?, vararg args: Any) {
        plugin.slF4JLogger.warn(message?.toString() ?: "null", *args)
    }

    fun error(ex: Exception, message: String) {
        plugin.slF4JLogger.error(message, ex)
    }
}