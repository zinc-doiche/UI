package zinc.doiche.service.user

import zinc.doiche.lib.service.IService
import zinc.doiche.lib.service.Service
import zinc.doiche.plugin
import zinc.doiche.service.hud.`object`.HUD
import zinc.doiche.service.user.listener.UserListener

@Service
class UserService : IService {
    override fun enable() {
        TODO("Not yet implemented")
    }

    override fun disable() {
        TODO("Not yet implemented")
    }

    override fun register() {
        TODO("Not yet implemented")
    }

    override fun load() {
        HUD.read()
    }
}