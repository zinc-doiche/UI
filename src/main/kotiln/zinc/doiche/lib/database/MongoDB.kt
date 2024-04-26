package zinc.doiche.lib.database

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import zinc.doiche.Main.Companion.plugin
import java.io.FileReader

object MongoDB {
    private const val DB_CONFIG_FILE = "database.json"
    lateinit var client: MongoClient
        private set

    lateinit var database: MongoDatabase
        private set

    operator fun get(name: String): MongoCollection<Document> {
        return database.getCollection(name)
    }

    fun register() {
        plugin.openFile(DB_CONFIG_FILE) {
            FileReader(it).use { reader ->
                val config: MongoConfig = plugin.gson.fromJson(reader, MongoConfig::class.java)
                val url: String = config.getURL()
                setupClient(url)
                setupDatabase(config)
            }
        }
    }

    private fun setupClient(atlasString: String) {
        val connectionString = ConnectionString(atlasString)
        val settings = MongoClientSettings.builder().applyConnectionString(connectionString).build()
        client = MongoClients.create(settings)
    }

    private fun setupDatabase(config: MongoConfig) {
        database = client.getDatabase(config.database)
    }

}