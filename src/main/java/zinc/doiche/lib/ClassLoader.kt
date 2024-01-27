package zinc.doiche.lib

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

    fun forClasses(consumer: (String) -> Unit) {
        val folder = File(Paths.get("./plugins/").toUri());
        val files = folder.listFiles() ?: return;
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
                    consumer.invoke(path)
                }
            }
        }
    }
}