package zinc.doiche.service.gui.`object`

import org.bukkit.event.inventory.InventoryEvent
import org.bukkit.inventory.InventoryHolder
import java.util.UUID

interface GUIHolder: InventoryHolder {
    val uuid: UUID

    fun open()
    fun onEvent(event: InventoryEvent, eventType: EventType)

    enum class EventType {
        CLICK,
        CLOSE,
        OPEN,
        DRAG
    }
}