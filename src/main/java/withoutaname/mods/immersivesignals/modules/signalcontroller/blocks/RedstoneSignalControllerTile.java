package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.util.Direction;
import net.minecraft.world.World;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.tools.SignalPattern;

public class RedstoneSignalControllerTile extends BaseSignalControllerTile{

	private static final Logger LOGGER = LogManager.getLogger();

	public RedstoneSignalControllerTile(World world, BlockState state) {
		super(SignalControllerRegistration.REDSTONE_SIGNAL_CONTROLLER_TILE.get(), world, state);
	}

	public void updateSignalPattern() {
		if(world != null && !world.isRemote){
			int powerNorth = world.getRedstonePower(getPos().north(), Direction.NORTH);
			int powerEast = world.getRedstonePower(getPos().east(), Direction.EAST);
			int powerSouth = world.getRedstonePower(getPos().south(), Direction.SOUTH);
			int powerWest = world.getRedstonePower(getPos().west(), Direction.WEST);
			SignalPattern signalPattern = getSignalPattern(powerNorth, powerEast, powerSouth, powerWest);
			RedstoneSignalControllerBlock.setSignalPattern(world, pos, signalPattern);
			LOGGER.info("powerNorth: " + powerNorth + "; powerEast: " + powerEast + "; powerSouth: " + powerSouth + "; powerWest: " + powerWest);
		}
	}

	private SignalPattern getSignalPattern(int powerNorth, int powerEast, int powerSouth, int powerWest) {
		return new SignalPattern(new Random().nextInt(16), BaseSignalBlock.SignalMainPattern.MODE_HP0, true, true, true, true, 15);
	}
}
