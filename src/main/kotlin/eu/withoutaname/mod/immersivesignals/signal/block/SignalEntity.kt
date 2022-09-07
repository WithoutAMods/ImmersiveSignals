package eu.withoutaname.mod.immersivesignals.signal.block

import eu.withoutaname.mod.immersivesignals.ImmersiveSignals
import eu.withoutaname.mod.immersivesignals.nbt.getSerializable
import eu.withoutaname.mod.immersivesignals.nbt.putSerializable
import eu.withoutaname.mod.immersivesignals.setup.Registration
import eu.withoutaname.mod.immersivesignals.signal.util.SignalBlockConfiguration
import kotlinx.serialization.SerializationException
import net.minecraft.block.BlockState
import net.minecraft.nbt.CompoundNBT
import net.minecraft.network.NetworkManager
import net.minecraft.network.play.server.SUpdateTileEntityPacket
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.client.model.ModelDataManager
import net.minecraftforge.client.model.data.ModelDataMap
import net.minecraftforge.client.model.data.ModelProperty
import net.minecraftforge.common.util.Constants

class SignalEntity : TileEntity(Registration.signalEntityType) {
    companion object {
        val config = ModelProperty<SignalBlockConfiguration>()
    }

    var signalConfiguration = SignalBlockConfiguration(0)
        set(value) {
            field = value
            setChanged()
            requestModelDataUpdate()
        }

    override fun getModelData(): ModelDataMap = ModelDataMap.Builder()
        .withInitial(config, signalConfiguration)
        .build()

    override fun onDataPacket(net: NetworkManager, pkt: SUpdateTileEntityPacket) = handleUpdateTag(blockState, pkt.tag)

    override fun getUpdatePacket() = SUpdateTileEntityPacket(blockPos, 1, updateTag)

    override fun handleUpdateTag(state: BlockState, tag: CompoundNBT) {
        load(state, tag)
        ModelDataManager.requestModelDataRefresh(this)
        level?.sendBlockUpdated(
            worldPosition,
            blockState,
            blockState,
            Constants.BlockFlags.BLOCK_UPDATE + Constants.BlockFlags.NOTIFY_NEIGHBORS
        )
    }

    override fun getUpdateTag() = save(CompoundNBT())

    override fun load(state: BlockState, nbt: CompoundNBT) {
        super.load(state, nbt)
        if (tileData.contains("config"))
            try {
                signalConfiguration = tileData.getSerializable("config")!!
            } catch (e: SerializationException) {
                ImmersiveSignals.logger.error("Error while loading signal block entity!", e)
            }
    }

    override fun save(nbt: CompoundNBT): CompoundNBT {
        try {
            tileData.putSerializable("config", signalConfiguration)
        } catch (e: SerializationException) {
            ImmersiveSignals.logger.error("Error while saving signal block entity!", e)
        }
        return super.save(nbt)
    }
}
