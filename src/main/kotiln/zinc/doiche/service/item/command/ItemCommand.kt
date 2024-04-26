package zinc.doiche.service.item.command

import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import zinc.doiche.Main.Companion.plugin
import zinc.doiche.service.item.`object`.ServerItemData

object ItemCommand {
    fun register() {
        plugin.kommand {
            register("serveritem") {
                then("reload") {
                    executes {
                        ServerItemData.loadAll()
                    }
                }
                then("get") {
                    then("id" to string().apply {
                        suggests {
                            suggest(ServerItemData.idList)
                        }
                    }) {
                        executes {
                            val id: String by it
                            if(id in ServerItemData) {
                                val itemData = ServerItemData[id]!!
                                player.inventory.addItem(itemData.get())
                            } else {
                                player.sendMessage("존재하지 않는 아이템입니다.")
                            }
                        }
                    }
                }
            }
        }
    }
}
