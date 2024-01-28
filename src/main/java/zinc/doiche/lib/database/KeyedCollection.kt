package zinc.doiche.lib.database

import com.mongodb.client.model.Filters.eq
import org.bson.Document
import org.bson.types.ObjectId

abstract class KeyedCollection<K> : CrudCollection {
    abstract val keyName: String

    fun findByKey(key: K): Document? {
        return collection.find(eq(keyName, key)).first()
    }

    fun deleteByKey(key: K) {
        collection.deleteOne(eq(keyName, key))
    }

    fun updateByKey(key: K, document: Document) {
        collection.updateOne(Document(keyName, key), Document("\$set", document))
    }

    override fun save(document: Document) {
        collection.insertOne(document)
    }

    override fun findAll(): List<Document> {
        return collection.find().toList()
    }

    override fun findById(id: ObjectId): Document? {
        return collection.find(eq("_id", id)).first()
    }

    override fun update(document: Document) {
        collection.updateOne(Document("_id", document.getObjectId("_id")), Document("\$set", document))
    }

    override fun deleteById(id: ObjectId) {
        collection.deleteOne(Document("_id", id))
    }
}