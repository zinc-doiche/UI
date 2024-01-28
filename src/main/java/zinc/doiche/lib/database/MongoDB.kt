package zinc.doiche.lib.database

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import java.io.File
import java.io.FileReader

object MongoDB {
    fun registerMongoDB() {
        val file: File =
            File(AnimalFarmPlugin.getInstance().getDataFolder(), com.animalfarm.database.DBConnector.DB_CONFIG_FILE)
        if (!file.exists()) {
            AnimalFarmPlugin.getInstance().saveResource(com.animalfarm.database.DBConnector.DB_CONFIG_FILE, false)
        }
        try {
            FileReader(file).use { reader ->
                val config: MongoConfig = FileUtil.DEFAULT_GSON.fromJson(reader, MongoConfig::class.java)
                val url: String = config.getURL()
                setupClient(url)
                setupDatabase(config)
            }
        } catch (e: Exception) {
            AnimalFarmPlugin.getSlf4jLogger().error("Failed to Connect to the DB.", e)
        }
    }

    private fun setupClient(atlasString: String) {
        val connectionString = ConnectionString(atlasString)
        val settings = MongoClientSettings.builder().applyConnectionString(connectionString).build()
        com.animalfarm.database.DBConnector.client = MongoClients.create(settings)
    }

    private fun setupDatabase(config: MongoConfig) {
        com.animalfarm.database.DBConnector.database =
            com.animalfarm.database.DBConnector.client.getDatabase(config.getDatabase())
    }

}