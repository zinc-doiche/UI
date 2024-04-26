package zinc.doiche.lib.repository

interface LocalRepository<K, V> : Repository<K, V> {
    val filePath: String

    fun read()
}