package zinc.doiche.lib.init

import java.io.File
import java.nio.file.Paths
import java.util.jar.JarFile

class ClassLoader {
    private val processors = ArrayList<Processor>()

    fun add(processorFactory: (ProcessorFactory) -> ProcessorFactory): ClassLoader {
        val factory = processorFactory.invoke(ProcessorFactory.factory())
        return add(factory.create())
    }

    fun add(processor: Processor): ClassLoader {
        processors.add(processor)
        return this
    }

    fun process() {
        val map = mutableMapOf<String, Any>()
        val pathes = getAllPath()
        pathes.forEach { _ -> processors.forEach { it.preProcess.invoke(map) } }
        pathes.forEach { _ -> processors.forEach { it.target } }
        processors.forEach { it.process.invoke() }
        processors.forEach { it.preProcess.invoke(map) }

    }

    fun process(annotation: Class<out Annotation>, process: (Class<*>) -> Unit) {
        forClasses { path ->
            try {
                val clazz = Class.forName(path.substring(0, path.lastIndexOf('.')))
                if (clazz.isAnnotationPresent(annotation)) {
                    process.invoke(clazz)
                }
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e);
            }
        }
    }

    fun <S> processSuper(superClass: Class<S>, process: (Class<S>) -> Unit) {
        forClasses { path ->
            try {
                val clazz = Class.forName(path.substring(0, path.lastIndexOf('.')))
                if (superClass.isAssignableFrom(clazz)) {
                    process.invoke(clazz as Class<S>)
                }
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e);
            }
        }
    }

    fun forClasses(consumer: (String) -> Unit) {
        getAllPath().forEach(consumer)
    }

    fun getAllPath(): List<String> {
        val list = mutableListOf<String>()
        val folder = File(Paths.get("./plugins/").toUri());
        val files = folder.listFiles() ?: return list;
        for (file in files) {
            if(!file.name.contains("AnimalFarm") || !file.name.endsWith(".jar")) {
                continue;
            }
            JarFile(file).use { jarFile ->
                jarFile.versionedStream().forEach { jarEntry ->
                    val path = jarEntry.toString().replace('/', '.')
                    if(!(path.startsWith("com.animalfarm") || path.startsWith("anif")) || !path.endsWith(".class")) {
                        return@forEach
                    }
                    list.add(path)
                }
            }
        }
        return list
    }
}