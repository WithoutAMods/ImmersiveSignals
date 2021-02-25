package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Direction;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.tools.SignalPattern;

public class RedstoneAdapterTile extends BaseAdapterTile {

	private static final Logger LOGGER = LogManager.getLogger();

	public RedstoneAdapterTile() {
		super(SignalControllerRegistration.REDSTONE_SIGNAL_CONTROLLER_TILE.get());
	}

	public void updateSignalPattern() {
		if(world != null && !world.isRemote()){
			int powerNorth = getPowerOnSide(world, Direction.NORTH);
			int powerEast = getPowerOnSide(world, Direction.EAST);
			int powerSouth = getPowerOnSide(world, Direction.SOUTH);
			int powerWest = getPowerOnSide(world, Direction.WEST);
			SignalPattern signalPattern = getSignalPattern(powerNorth, powerEast, powerSouth, powerWest);
			RedstoneAdapterBlock.setSignalPattern(world, pos, signalPattern);
			LOGGER.debug("powerNorth: " + powerNorth + "; powerEast: " + powerEast + "; powerSouth: " + powerSouth + "; powerWest: " + powerWest);
		}
	}

	private SignalPattern getSignalPattern(int powerNorth, int powerEast, int powerSouth, int powerWest) {
		return new SignalPattern(powerEast, BaseSignalBlock.SignalMainPattern.values()[powerNorth / 4], powerSouth % 16 > 7, powerSouth % 8 > 3, powerSouth % 4 > 1, powerSouth % 2 > 0, powerWest);
	}

	protected int getPowerOnSide(IWorldReader worldIn, Direction side) {
		BlockPos blockPos = this.pos.offset(side);
		BlockState blockstate = worldIn.getBlockState(blockPos);
		Block block = blockstate.getBlock();
		if (blockstate.canProvidePower()) {
			if (block == Blocks.REDSTONE_BLOCK) {
				return 15;
			} else {
				return block == Blocks.REDSTONE_WIRE ? blockstate.get(RedstoneWireBlock.POWER) : worldIn.getStrongPower(blockPos, side);
			}
		} else {
			return 0;
		}
	}

}
