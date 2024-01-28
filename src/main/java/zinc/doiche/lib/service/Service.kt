package zinc.doiche.lib.service

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Service(val priority: Int)
