package zinc.doiche.service.user.collection

import com.mongodb.client.model.Filters
import org.bson.Document
import zinc.doiche.lib.database.collection.KeyedCollection
import zinc.doiche.lib.database.MongoDB
import zinc.doiche.service.user.`object`.User
import zinc.doiche.util.toObject
import java.util.UUID

object UserCollection : KeyedCollection<UUID, User>() {
    override val name: String = "User"
    override val collection = MongoDB[name]
    override val keyName: String = "uuid"

    override fun findByKey(key: UUID): User? {
        val document = collection.find(Filters.eq(keyName, key)).first()
        return document?.toObject(User::class.java)
    }

    override fun deleteByKey(key: UUID) {
        collection.deleteOne(Filters.eq(keyName, key.toString()))
    }

    override fun updateByKey(key: UUID, document: Document) {
        collection.updateOne(Document(keyName, key.toString()), Document("\$set", document))
    }
}