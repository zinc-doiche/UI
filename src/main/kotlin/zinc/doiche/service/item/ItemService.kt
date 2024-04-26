package zinc.doiche.service.item

import zinc.doiche.lib.service.IService
import zinc.doiche.service.item.command.ItemCommand
import zinc.doiche.service.item.`object`.ServerItemData

class ItemService: IService {
    override fun enable() {
        TODO()
    }

    override fun disable() {
        TODO()
    }

    override fun register() {
        ItemCommand.register()
    }

    override fun load() {
        ServerItemData.loadAll()
    }
}