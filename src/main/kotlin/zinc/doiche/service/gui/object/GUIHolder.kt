package zinc.doiche.service.gui.`object`

import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.InventoryHolder
import java.util.UUID

interface GUIHolder: InventoryHolder {
    val uuid: UUID
    val gui: GUI

    fun open()

    enum class EventType {
        CLICK,
        CLOSE,
        OPEN,
        DRAG
    }
}