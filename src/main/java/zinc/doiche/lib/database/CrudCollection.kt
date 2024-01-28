package zinc.doiche.lib.database

import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bson.types.ObjectId

interface CrudCollection {
    val name: String
    val collection: MongoCollection<Document>

    fun save(document: Document)
    fun findAll(): List<Document>
    fun findById(id: ObjectId): Document?
    fun update(document: Document)
    fun deleteById(id: ObjectId)
}
