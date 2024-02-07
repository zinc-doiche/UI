package zinc.doiche.lib.repository

import java.util.*

interface Repository<K, V> {
    operator fun get(key: K): V?

    fun remove(key: K)
}