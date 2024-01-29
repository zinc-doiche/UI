package zinc.doiche.service.gui.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import zinc.doiche.service.gui.`object`.GUIHolder

class GUIListener: Listener {
    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val holder = event.inventory.holder
        if(holder is GUIHolder) {
            holder.onEvent(event, GUIHolder.EventType.CLICK)
        }
    }

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        val holder = event.inventory.holder
        if(holder is GUIHolder) {
            holder.onEvent(event, GUIHolder.EventType.CLOSE)
        }
    }

    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        val holder = event.inventory.holder
        if(holder is GUIHolder) {
            holder.onEvent(event, GUIHolder.EventType.OPEN)
        }
    }

    @EventHandler
    fun onInventoryDrag(event: InventoryDragEvent) {
        val holder = event.inventory.holder
        if(holder is GUIHolder) {
            holder.onEvent(event, GUIHolder.EventType.DRAG)
        }
    }
}