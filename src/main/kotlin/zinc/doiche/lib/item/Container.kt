package zinc.doiche.lib.item

import org.bukkit.inventory.ItemStack
import zinc.doiche.service.item.`object`.ItemData
import zinc.doiche.service.item.`object`.ItemDataStack
import zinc.doiche.util.toItemData
import zinc.doiche.util.toItemDataStack
import java.util.Stack

class Container(val size: Int) {
    val items = Array<ItemDataStack?>(size) { null }

    operator fun get(index: Int): ItemStack? {
        return items[index]?.get()
    }

    fun getDataStack(index: Int): ItemDataStack? {
        return items[index]
    }

    operator fun set(index: Int, item: ItemStack?) {
        items[index] = item?.toItemDataStack()
    }

    fun setDataStack(index: Int, item: ItemDataStack?) {
        items[index] = item
    }

    fun count(): Int = items.count { it != null }
    fun countAmount(): Int = items.sumOf { it?.amount ?: 0 }

    fun addItem(vararg items: ItemStack): List<ItemStack> {
        val exceeds = ArrayList<ItemStack>()
        val iterator = items.iterator()
        var firstNullIndex = -1
        while (iterator.hasNext()) {
            val item = iterator.next()
            val itemDataStack = item.toItemDataStack()
            repeat(size) { i ->
                getDataStack(i)?.let { containerData ->
                    if(!containerData.isSimilar(itemDataStack) || containerData.isFull) {
                        return@let
                    }
                    val remainCapacity = containerData.remainCapacity
                    if(remainCapacity >= itemDataStack.amount) {
                        containerData.amount += itemDataStack.amount
                        return@repeat
                    } else {
                        containerData.amount += remainCapacity
                        itemDataStack.amount -= remainCapacity
                        return@let
                    }
                } ?: run {
                    if(firstNullIndex == -1) {
                        firstNullIndex = i
                    }
                }
            }
            if(firstNullIndex != -1) {
                setDataStack(firstNullIndex, itemDataStack)
                continue
            }
            exceeds.add(item)
        }
        return exceeds
    }
}