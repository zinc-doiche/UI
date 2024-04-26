package zinc.doiche.service.item.collection

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.bson.Document
import zinc.doiche.lib.database.MongoDB
import zinc.doiche.lib.database.collection.KeyedCollection
import zinc.doiche.service.item.`object`.ServerItemData
import zinc.doiche.util.toDocument
import zinc.doiche.util.toObject

object ServerItemDataCollection: KeyedCollection<String, ServerItemData>() {
    override val name: String = "ServerItem"
    override val collection: MongoCollection<Document> = MongoDB[name]
    override val keyName: String = "id"

    override fun save(value: ServerItemData) {
        collection.insertOne(value.toDocument())
    }

    override fun findByKey(key: String): ServerItemData? {
        return collection.find(eq(keyName, key)).first()?.toObject(ServerItemData::class.java)
    }
}