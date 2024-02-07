package zinc.doiche.service.item.`object`

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import zinc.doiche.util.getTag
import zinc.doiche.util.isServerItem
import zinc.doiche.util.item
import zinc.doiche.util.toItemData

data class ItemData(
    val material: Material,
    val displayName: String,
    val lore: List<String>,
    val tags: Map<String, Any>
) {
    val maxAmount: Int
        get() = material.maxStackSize

    fun get(amount: Int = 1): ItemStack {
       return item(material, getDisplayName(), getLore())
    }

    fun getDisplayName(): Component {
        return MiniMessage.miniMessage().deserialize(displayName)
    }

    fun getLore(): List<Component> {
        val miniMessage = MiniMessage.miniMessage()
        return lore.map { miniMessage.deserialize(it) }
    }

    fun toStack(amount: Int = 1): ItemDataStack {
        return ItemDataStack(this, amount)
    }

    fun isServerItem(): Boolean {
        return tags.containsKey("id")
    }

    fun toServerItem(): ServerItemData {
        return if(isServerItem())
            ServerItemData(tags["id"] as String, this)
        else
            throw RuntimeException("서버 아이템이 아닙니다.")
    }

    fun isSimilar(item: ItemStack): Boolean {
        if(item.isServerItem() && this.isServerItem()) {
            return item.getTag("id") == tags["id"]
        }
        return item.toItemData() == this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ItemData

        if (material != other.material) return false
        if (displayName != other.displayName) return false
        if (lore != other.lore) return false
        if (tags != other.tags) return false

        return true
    }

    override fun hashCode(): Int {
        var result = material.hashCode()
        result = 31 * result + displayName.hashCode()
        result = 31 * result + lore.hashCode()
        result = 31 * result + tags.hashCode()
        return result
    }
}
