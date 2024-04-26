package zinc.doiche.service.gui

import zinc.doiche.Main.Companion.plugin
import zinc.doiche.lib.service.IService
import zinc.doiche.service.gui.listener.GUIListener

class GUIService: IService {
    override fun enable() {
        TODO("Not yet implemented")
        
    }

    override fun disable() {
        TODO("Not yet implemented")
    }

    override fun register() {
        plugin.register(
            GUIListener()
        )
    }

    override fun load() {
        TODO("Not yet implemented")
    }
}