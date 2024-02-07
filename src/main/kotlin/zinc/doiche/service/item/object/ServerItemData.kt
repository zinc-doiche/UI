package zinc.doiche.service.item.`object`

import zinc.doiche.lib.repository.Repository
import zinc.doiche.service.item.collection.ServerItemDataCollection
import zinc.doiche.util.toObject

data class ServerItemData(
    val id: String,
    val data: ItemData
) {
    companion object : Repository<String, ServerItemData> {
        private val items = HashMap<String, ServerItemData>()

        override fun get(key: String): ServerItemData? {
            return items[key]
        }

        override fun remove(key: String) {
            items.remove(key)
        }

        fun loadAll() {
            ServerItemDataCollection.findAll().map {
                it.toObject(ServerItemData::class.java)
            }.forEach {
                items[it.id] = it
            }
        }
    }
}
