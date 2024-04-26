package zinc.doiche.service.user.listener

import io.papermc.paper.chat.ChatRenderer
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
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

    @EventHandler
    fun onChat(event: AsyncChatEvent) {
        event.renderer(ChatRenderer.viewerUnaware { source, _, message ->
            Component.text("<${source.name}> ").append(message.color(NamedTextColor.RED))
        })
    }
}