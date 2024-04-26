package zinc.doiche.service.item.`object`

import org.bukkit.inventory.ItemStack

data class ItemDataStack(
    val itemData: ItemData,
    var amount: Int
) {
    val isFull: Boolean
        get() = itemData.maxAmount == amount

    val remainCapacity: Int
        get() {
            val difference = itemData.maxAmount - amount
            return if(difference <= 0) 0 else difference
        }

    fun get(): ItemStack {
        return itemData.get(amount)
    }

    fun isSimilar(itemDataStack: ItemDataStack): Boolean {
        return itemDataStack.itemData == itemData
    }
}