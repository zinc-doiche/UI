package zinc.doiche.lib.service

import org.bukkit.Bukkit
import zinc.doiche.lib.command.CommandFactory
import zinc.doiche.lib.init.ClassLoader
import java.lang.annotation.Inherited
import java.util.*

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Inherited
annotation class Service(val priority: Int = 0)

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Command

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Listener

internal val loader = ClassLoader()

internal fun processServices(): List<IService> {
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

internal fun processCommands() {
    val commandMap = Bukkit.getCommandMap()
    loader.processSuper(CommandFactory::class.java) { clazz ->
        val constructor = clazz.getConstructor()
        val commandFactory = constructor.newInstance() as CommandFactory
        val command = commandFactory.create()
        commandMap.register("ui", command)
    }
}

internal fun processListeners(): List<IService> {
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
