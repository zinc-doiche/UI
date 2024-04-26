package zinc.doiche.service.hud.`object`

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import org.bukkit.entity.Player
import zinc.doiche.Main.Companion.plugin
import zinc.doiche.lib.repository.LocalRepository

class HUD(
    val name: String,
    private val componentString: String
) {
    lateinit var component: Component

    fun init() {
        component = MiniMessage.miniMessage().deserialize(componentString)
    }

    fun show(player: Player) {
        val bossbar = BossBar.bossBar(component, 1f, BossBar.Color.YELLOW, BossBar.Overlay.PROGRESS)
        player.showBossBar(bossbar)
    }

    companion object: LocalRepository<String, HUD> {
        override val filePath = "hud.json"
        private val huds = HashMap<String, HUD>()

        override operator fun get(key: String): HUD? {
            return huds[key]
        }

        override fun contains(key: String): Boolean {
            return huds.containsKey(key)
        }

        override fun remove(key: String) {
            huds.remove(key)
        }

        override fun read() {
            huds.clear()
            plugin.openFile(filePath) { file ->
                val json = plugin.gson.fromJson(file.readText(), Array<HUD>::class.java)
                json.forEach { hud ->
                    hud.init()
                    huds[hud.name] = hud
                }
            }
        }
    }
}