package net.cerulan.luminality.block.lumus

import alexiil.mc.lib.attributes.AttributeList
import alexiil.mc.lib.attributes.AttributeProvider
import net.cerulan.luminality.block.entity.lumus.LumusRegulator
import net.fabricmc.fabric.api.block.FabricBlockSettings
import net.minecraft.block.*
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.StateManager
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World

object LumusRegulatorBlock: AbstractGlassBlock (
    FabricBlockSettings.of(Material.GLASS).nonOpaque().breakByHand(true).strength(
        0.5f,
        10f
    ).sounds(BlockSoundGroup.METAL).build()
),
    AttributeProvider,
    BlockEntityProvider {

    init {
        defaultState = stateManager.defaultState.with(LumusPumpBlock.Props.input, Direction.DOWN).with(
            LumusPumpBlock.Props.valid, false)
    }

    override fun addAllAttributes(world: World, pos: BlockPos, state: BlockState, list: AttributeList<*>) {
        val be = world.getBlockEntity(pos)
        if (be is LumusRegulator && list.searchDirection == be.inputDirection) {
            list.offer(be.inputSink)
        }
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(LumusPumpBlock.Props.input, LumusPumpBlock.Props.valid)
    }

    @SuppressWarnings("deprecation")
    override fun onBlockRemoved(
        state: BlockState,
        world: World,
        pos: BlockPos,
        newState: BlockState,
        moved: Boolean
    ) {
        if (newState.block == this || world.isClient) return
        val be = world.getBlockEntity(pos)
        if (be is LumusRegulator) {
            be.onBroken()
        }
        super.onBlockRemoved(state, world, pos, newState, moved)
    }

    override fun createBlockEntity(view: BlockView) =
        LumusRegulator()

}