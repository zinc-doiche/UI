package zinc.doiche.service.user.`object`

import zinc.doiche.lib.repository.Repository
import zinc.doiche.service.user.collection.UserCollection
import java.util.*
import kotlin.collections.HashMap

data class User(
    val uuid: UUID
) {
    companion object : Repository<UUID, User> {
        private val users = HashMap<UUID, User>()

        override fun get(key: UUID): User? {
            return users[key]
        }

        override fun contains(key: UUID): Boolean {
            return users.containsKey(key)
        }

        override fun remove(key: UUID) {
            users.remove(key)
        }

        fun register(uuid: UUID) {
            UserCollection.findByKey(uuid)?.let {
                users[uuid] = it
            } ?: {
                val user = User(uuid)
                users[uuid] = user
                UserCollection.save(user)
            }
        }

        fun unregister(uuid: UUID) {
            User[uuid]?.let {
                UserCollection.save(it)
                remove(uuid)
            }
        }
    }
}