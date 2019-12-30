package net.cerulan.aetherflow.container

import io.github.cottonmc.cotton.gui.CottonCraftingController
import io.github.cottonmc.cotton.gui.widget.WGridPanel
import io.github.cottonmc.cotton.gui.widget.WItemSlot
import io.github.cottonmc.cotton.gui.widget.WLabel
import net.cerulan.aetherflow.recipe.AetherflowRecipeTypes
import net.minecraft.container.BlockContext
import net.minecraft.container.Slot
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.ItemStack
import net.minecraft.text.TranslatableText

class AetherFurnaceController(syncId: Int, playerInv: PlayerInventory, context: BlockContext): CottonCraftingController(AetherflowRecipeTypes.AETHER_FURNACE, syncId, playerInv, getBlockInventory(context), getBlockPropertyDelegate(context)) {
    private val inputSlot: WItemSlot
    private val outputSlot: WItemSlot
    init {
        val rootPanel = getRootPanel() as WGridPanel
        rootPanel.add(WLabel(TranslatableText("block.aetherflow.aether_furnace"), WLabel.DEFAULT_TEXT_COLOR), 0, 0)
        inputSlot = WItemSlot.of(blockInventory, 0)
        outputSlot = WItemSlot.of(blockInventory, 1)

        rootPanel.add(inputSlot, 2, 1)
        rootPanel.add(outputSlot, 6, 1)

        rootPanel.add(createPlayerInventoryPanel(), 0, 3)
        rootPanel.validate(this)
    }

    override fun getCraftingResultSlotIndex(): Int = -1
}