package zinc.doiche.service.item.`object`

import org.bukkit.inventory.ItemStack
import zinc.doiche.lib.repository.Repository
import zinc.doiche.service.item.collection.ServerItemDataCollection
import zinc.doiche.util.toObject

data class ServerItemData(
    val id: String,
    val data: ItemData
) {
    fun get(amount: Int = 1): ItemStack {
        return data.get(amount)
    }

    companion object : Repository<String, ServerItemData> {
        private val items = HashMap<String, ServerItemData>()

        val idList: List<String>
            get() = items.keys.toList()

        override fun get(key: String): ServerItemData? {
            return items[key]
        }

        override fun contains(key: String): Boolean {
            return items.containsKey(key)
        }

        override fun remove(key: String) {
            items.remove(key)
        }

        fun loadAll() {
            items.clear()
            ServerItemDataCollection.findAll().map {
                it.toObject(ServerItemData::class.java)
            }.forEach {
                items[it.id] = it
            }
        }
    }
}
