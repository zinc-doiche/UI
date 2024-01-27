package zinc.doiche;

import org.bukkit.plugin.java.JavaPlugin
import zinc.doiche.service.processAll

class Main: JavaPlugin() {
    override fun onEnable() {
        plugin = this
        processAll()
    }
}

internal lateinit var plugin: Main
    private set