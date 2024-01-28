package zinc.doiche.util

import org.bson.Document
import org.bson.conversions.Bson
import zinc.doiche.plugin

fun <T> Document.toObject(clazz: Class<T>): T =
    plugin.gson.fromJson(this.toJson(), clazz)

fun <T> List<Document>.toObjects(clazz: Class<T>): List<T> =
    map { it.toObject(clazz) }

fun Any.toDocument(): Document =
    Document.parse(plugin.gson.toJson(this))

fun set(document: Document): Bson =
    Document("\$set", document)

fun documentOf(vararg pairs: Pair<String, Any?>): Document =
    Document(pairs.toMap())
