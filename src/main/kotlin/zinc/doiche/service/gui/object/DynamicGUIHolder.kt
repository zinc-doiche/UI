package zinc.doiche.service.gui.`object`

import net.minecraft.network.chat.Component
import net.minecraft.network.protocol.game.ClientboundOpenScreenPacket
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftPlayer
import org.bukkit.entity.Player

abstract class DynamicGUIHolder: GUIHolder {
    fun update(player: Player, title: Component) {
        player as CraftPlayer
        val serverPlayer = player.handle
        val currentMenu = serverPlayer.containerMenu
        val packet = ClientboundOpenScreenPacket(currentMenu.containerId, currentMenu.type, title)
        serverPlayer.connection.send(packet, null)
        serverPlayer.initMenu(currentMenu)
    }
}