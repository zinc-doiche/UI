package zinc.doiche.service.user.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import zinc.doiche.service.user.collection.UserCollection
import zinc.doiche.service.user.`object`.User

class UserListener: Listener {
    @EventHandler
    fun onPreLogin(event: AsyncPlayerPreLoginEvent) {
        val uuid = event.uniqueId
        User.register(uuid)
    }

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {

    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val uuid = event.player.uniqueId
        User.unregister(uuid)
    }
}