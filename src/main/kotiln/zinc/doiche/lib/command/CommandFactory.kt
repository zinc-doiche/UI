package zinc.doiche.lib.command

import org.bukkit.command.Command
import zinc.doiche.lib.init.ProcessorFactory
import zinc.doiche.lib.init.ProcessorFactoryImpl

interface CommandFactory {
    fun create(): Command
}