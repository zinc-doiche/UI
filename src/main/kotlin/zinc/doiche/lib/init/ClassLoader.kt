package zinc.doiche.lib.init

import org.bukkit.event.Listener
import zinc.doiche.plugin
import java.io.File
import java.nio.file.Paths
import java.util.jar.JarFile

class ClassLoader {
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

    fun <T> processBySuper(superClass: Class<T>, process: (Class<*>) -> Unit) {
        forClasses { path ->
            try {
                val clazz = Class.forName(path.substring(0, path.lastIndexOf('.')))

                if (superClass.isAssignableFrom(clazz)) {
                    process.invoke(clazz)
                }
            } catch (e: ClassNotFoundException) {
                throw RuntimeException(e);
            }
        }
    }

    fun forClasses(consumer: (String) -> Unit) {
        val folder = File(Paths.get("./plugins/").toUri());
        val files = folder.listFiles() ?: return;
        for (file in files) {
            if(!file.name.contains("UI") || !file.name.endsWith(".jar")) {
                continue;
            }
            JarFile(file).use { jarFile ->
                jarFile.versionedStream().forEach { jarEntry ->
                    val path = jarEntry.toString().replace('/', '.')
                    if(path.startsWith("zinc.doiche") && path.endsWith(".class")) {
                        consumer.invoke(path)
                    }
                }
            }
        }
    }

    fun processListeners() {
        processBySuper(Listener::class.java) { clazz ->
            val constructor = clazz.getDeclaredConstructor()
            val listener = constructor.newInstance() as Listener
            plugin.register(listener)
        }
    }
}