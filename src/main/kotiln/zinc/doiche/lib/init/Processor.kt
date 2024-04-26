package zinc.doiche.lib.init

class Processor(
    val preProcess: (MutableMap<String, Any>) -> Unit,
    val process: (Class<*>) -> Unit,
    val postProcess: (Map<String, Any>) -> Unit
) {

}