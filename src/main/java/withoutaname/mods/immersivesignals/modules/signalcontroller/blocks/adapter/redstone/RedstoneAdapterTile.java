package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.redstone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Direction;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.PredicateAdapterTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.RedstonePredicate;

public class RedstoneAdapterTile extends PredicateAdapterTile<RedstonePredicate> {

	private static final Logger LOGGER = LogManager.getLogger();

	public RedstoneAdapterTile() {
		super(SignalControllerRegistration.REDSTONE_ADAPTER_TILE.get());
	}

	public int getPowerOnSide(Direction side) {
		assert world != null;
		BlockPos blockPos = pos.offset(side);
		BlockState blockstate = world.getBlockState(blockPos);
		Block block = blockstate.getBlock();
		if (blockstate.canProvidePower()) {
			if (block == Blocks.REDSTONE_BLOCK) {
				return 15;
			} else {
				return block == Blocks.REDSTONE_WIRE ? blockstate.get(RedstoneWireBlock.POWER) : world.getStrongPower(blockPos, side);
			}
		} else {
			return 0;
		}
	}

}
