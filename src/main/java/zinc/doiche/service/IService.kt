package zinc.doiche.service

import zinc.doiche.Main
import zinc.doiche.lib.ClassLoader

interface IService {
    fun enable()
    fun disable()
    fun register()
    fun load()
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Service

internal fun Main.processAll() {
    val loader = ClassLoader()
    loader.process(Service::class.java) { clazz ->
        val constructor = clazz.getConstructor()
        val service = constructor.newInstance()
        if(service !is IService) {
            return@process
        }
        service.register()
        service.load()
        service.enable()
    }
}