package zinc.doiche.service.gui.`object`

import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent

interface GUIClickEvent {
    fun onClick(event: InventoryClickEvent)
}

interface GUICloseEvent {
    fun onClose(event: InventoryCloseEvent)
}

interface GUIOpenEvent {
    fun onOpen(event: InventoryOpenEvent)
}

interface GUIDragEvent {
    fun onDrag(event: InventoryDragEvent)
}

