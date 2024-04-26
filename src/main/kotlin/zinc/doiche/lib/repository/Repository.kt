package zinc.doiche.lib.repository


interface Repository<K, V> {
    operator fun get(key: K): V?

    operator fun contains(key: K): Boolean

    fun remove(key: K)
}