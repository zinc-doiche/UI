package zinc.doiche;

import org.bukkit.plugin.java.JavaPlugin
import zinc.doiche.lib.service.IService
import zinc.doiche.service.processAll

class Main: JavaPlugin() {
    lateinit var services: List<IService>
        private set

    override fun onEnable() {
        plugin = this
        services = processAll()
    }

    override fun onDisable() {
        services.forEach(IService::disable)
    }
}

internal lateinit var plugin: Main
    private set