package zinc.doiche;

import com.google.gson.Gson
import io.github.monun.kommand.kommand
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import zinc.doiche.lib.database.MongoDB
import zinc.doiche.lib.service.IService
import zinc.doiche.lib.service.processAll
import zinc.doiche.service.user.listener.UserListener
import java.io.File

class Main: JavaPlugin() {
    val services: MutableList<IService> = ArrayList()
    val gson = Gson()

    override fun onEnable() {
        plugin = this
        MongoDB.register()
        services.addAll(processAll())
    }

    override fun onDisable() {
        services.forEach(IService::disable)
    }

    fun register(vararg listeners: Listener) {
        listeners.forEach {
            server.pluginManager.registerEvents(it, this)
        }
    }

    fun openFile(path: String, config: (File) -> Unit) {
        val file = File(dataFolder, path)
        if(!file.exists()) {
            saveResource(path, false)
        }
        config(file)
    }
}

internal lateinit var plugin: Main
    private set

internal fun info(msg: Any?) {
    plugin.slF4JLogger.info(msg?.toString() ?: "null")
}

internal fun info(format: String, vararg msg: Any?) {
    plugin.slF4JLogger.info(format, msg.map { it?.toString() ?: "null" })
}