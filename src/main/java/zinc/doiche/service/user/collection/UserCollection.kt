package zinc.doiche.service.user.collection

import com.mongodb.client.MongoCollection
import org.bson.Document
import zinc.doiche.lib.database.KeyedCollection

object UserCollection : KeyedCollection<String>() {
    override val name: String = "User"
    override val collection: MongoCollection<Document> =
    override val keyName: String
}