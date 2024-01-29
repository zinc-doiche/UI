package zinc.doiche.util

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.translation.GlobalTranslator
import net.minecraft.nbt.DoubleTag
import net.minecraft.nbt.IntTag
import net.minecraft.nbt.StringTag
import net.minecraft.nbt.Tag
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import zinc.doiche.service.item.`object`.ItemData
import zinc.doiche.service.item.`object`.ItemDataStack
import java.util.*

fun item(
    material: Material = Material.PAPER,
    displayName: Component? = null,
    lore: List<Component>? = null,
    vararg tags: Pair<String, Any>
) = ItemStack(material).apply {
    editMeta { meta ->
        meta.displayName(displayName)
        meta.lore(lore)
        tags.forEach { (key, value) ->
            when(key) {
                "model" -> {
                    if(value is Int) meta.setCustomModelData(value)
                    return@editMeta
                }

            }
        }
    }
}

fun ItemStack.toItemDataStack(): ItemDataStack =
    ItemDataStack(this.toItemData(), amount)

fun ItemStack.toItemData(): ItemData {
    val miniMessage = MiniMessage.miniMessage()
    val displayNameString = itemMeta.displayName()?.let {
        miniMessage.serialize(it)
    } ?: type.itemTranslationKey?.let {
        (GlobalTranslator.translator()
            .translate(Component.translatable(it), Locale.KOREAN) as TextComponent)
                .content()
    } ?: type.name
    val lore = itemMeta.lore()?.map {
        miniMessage.serialize(it)
    } ?: listOf()
    val tags = getTags().mapValues { (key, tag) ->
        when(tag.type) {
            StringTag.TYPE -> tag.asString
            IntTag.TYPE -> (tag as IntTag).asInt
            DoubleTag.TYPE -> (tag as DoubleTag).asDouble
            else -> key
        }
    }
    return ItemData(type, displayNameString, lore, tags)
}

fun ItemStack.isServerItem(): Boolean =
    getTag("id") != null

fun ItemStack.addTag(key: String, tag: Tag) {
    this as CraftItemStack
    this.handle.addTagElement(key, tag)
}

fun ItemStack.getTag(key: String): Tag? {
    this as CraftItemStack
    return this.handle.getTagElement(key)
}

fun ItemStack.getTags(): MutableMap<String, Tag> {
    this as CraftItemStack
    return this.handle.tag?.tags ?: mutableMapOf()
}

fun ItemStack.removeTag(key: String) {
    this as CraftItemStack
    this.handle.removeTagKey(key)
}

