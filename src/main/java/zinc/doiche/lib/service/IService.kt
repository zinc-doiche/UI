package zinc.doiche.lib.service

import zinc.doiche.lib.init.ClassLoader

interface IService {
    fun enable()
    fun disable()
    fun register()
    fun load()
}

internal fun processAll(): List<IService> {
    val loader = ClassLoader()
    val services = HashMap<IService, Int>()
    val sortedServices = ArrayList<IService>()
    loader.process(Service::class.java) { clazz ->
        val serviceAnnotation = clazz.getAnnotation(Service::class.java) ?: return@process
        val priority = serviceAnnotation.priority
        val constructor = clazz.getConstructor()
        val service = constructor.newInstance()
        if(service !is IService || clazz.isInterface) {
            return@process
        }
        services[service] = priority
    }
    services.entries.sortedBy { entry -> entry.value }.forEach { entry ->
        val service = entry.key;
        service.load()
        service.register()
        service.enable()
        sortedServices.add(service)
    }
    return sortedServices
}