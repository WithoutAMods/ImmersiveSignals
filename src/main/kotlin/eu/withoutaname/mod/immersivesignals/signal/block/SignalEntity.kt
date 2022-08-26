package eu.withoutaname.mod.immersivesignals.signal.block

import eu.withoutaname.mod.immersivesignals.setup.Registration
import eu.withoutaname.mod.immersivesignals.signal.util.SignalBlockConfiguration
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

    var signalConfiguration = SignalBlockConfiguration(.75f)

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
        signalConfiguration = SignalBlockConfiguration.fromNBT(tileData.get("config"))
    }

    override fun save(nbt: CompoundNBT): CompoundNBT {
        tileData.put("config", signalConfiguration.toNBT())
        return super.save(nbt)
    }
}
