package zinc.doiche;

import com.google.gson.Gson
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import zinc.doiche.lib.database.MongoDB
import zinc.doiche.lib.service.IService
import zinc.doiche.lib.service.processAll
import java.io.File

internal lateinit var plugin: Main
    private set

class Main: JavaPlugin() {
    lateinit var services: List<IService>
        private set

    val gson = Gson()

    override fun onEnable() {
        plugin = this
        services = processAll()

        MongoDB.register()
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