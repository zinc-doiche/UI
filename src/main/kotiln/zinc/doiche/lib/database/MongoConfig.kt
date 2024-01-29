package zinc.doiche.lib.database

data class MongoConfig(
    val host: String,
    val port: Int,
    val database: String,
    val username: String,
    val password: String,
    val options: Map<String, String>
) {
    fun getURL(): String {
        // mongodb://[username:password@]host1[:port1],...hostN[:portN]][/[defaultauthdb][?options]]
        val optionString = if (options.isEmpty())
            ""
        else
            options.entries.joinToString("&") { entry -> entry.key + "=" + entry.value }
        return if (username.isEmpty() || password.isEmpty())
            "mongodb://$host:$port/$database$optionString"
        else
            "mongodb://$username:$password@$host:$port/$database$optionString"
    }
}