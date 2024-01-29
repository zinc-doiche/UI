package zinc.doiche.lib.database.collection

import com.mongodb.client.MongoCollection
import com.mongodb.client.model.Filters.eq
import org.bson.Document

abstract class KeyedCollection<K, E> {
    abstract val name: String
    abstract val collection: MongoCollection<Document>
    abstract val keyName: String

    abstract fun findByKey(key: K): E?

    open fun deleteByKey(key: K) {
        collection.deleteOne(eq(keyName, key))
    }

    open fun updateByKey(key: K, document: Document) {
        collection.updateOne(Document(keyName, key), Document("\$set", document))
    }

    open fun save(document: Document) {
        collection.insertOne(document)
    }

    abstract fun save(value: E)

    open fun findAll(): List<Document> {
        return collection.find().toList()
    }

    open fun bulkWrite() {
        //collection.bulkWrite()
    }
}