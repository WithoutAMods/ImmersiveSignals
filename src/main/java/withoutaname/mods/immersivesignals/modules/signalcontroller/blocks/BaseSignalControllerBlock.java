package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.setup.ModSetup;
import withoutaname.mods.immersivesignals.tools.SignalPattern;

public class BaseSignalControllerBlock extends Block {

	public BaseSignalControllerBlock() {
		super(Properties.create(Material.IRON)
				.sound(SoundType.METAL)
				.hardnessAndResistance(1.5F, 6.0F));
	}

	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		event.getRegistry().register(new RedstoneSignalControllerBlock());
	}

	public static void registerItems(RegistryEvent.Register<Item> event) {
		Item.Properties properties = new Item.Properties()
				.group(ModSetup.defaultItemGroup)
				.maxStackSize(64);
		event.getRegistry().register(new BlockItem(SignalControllerRegistration.REDSTONE_SIGNAL_CONTROLLER.get(), properties).setRegistryName("redstonesignalcontroller"));
	}

	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}

	protected static BlockPos getSignalBlockPos(World world, BlockPos pos, BaseSignalBlock block) {
		BlockPos blockPos = null;
		for(int i = 1; world.getBlockState(pos.up(i)).getBlock() instanceof BaseSignalBlock && (world.getBlockState(pos.up(i)).getBlock() != SignalRegistration.SIGNAL_FOUNDATION.get() || i == 1); i++) {
			if(world.getBlockState(pos.up(i)).getBlock() == block) {
				blockPos = pos.up(i);
				break;
			}
		}
		return blockPos;
	}
	
	protected static boolean hasSignalBlock(World world, BlockPos pos, BaseSignalBlock block) {
		return getSignalBlockPos(world, pos, block) != null;
	}
	
	protected static SignalPattern getSignalPattern(World world, BlockPos pos) {
		SignalPattern signalPattern = SignalPattern.DEFAULT_SIGNAL_PATTERN;
		if(hasSignalBlock(world, pos, SignalRegistration.SIGNAL_ZS3V.get())) {
			BlockState blockState = world.getBlockState(getSignalBlockPos(world, pos, SignalRegistration.SIGNAL_ZS3V.get()));
			signalPattern.setZs3vNumber(blockState.get(BaseSignalBlock.SIGNAL_NUMBER));
		}
		if(hasSignalBlock(world, pos, SignalRegistration.SIGNAL_MAIN.get())) {
			BlockState blockState = world.getBlockState(getSignalBlockPos(world, pos, SignalRegistration.SIGNAL_MAIN.get()));
			signalPattern.setMainPattern(blockState.get(BaseSignalBlock.SIGNAL_MAIN_PATTERN));
			signalPattern.setWhite0(blockState.get(BaseSignalBlock.SIGNAL_WHITE0));
			signalPattern.setWhite1(blockState.get(BaseSignalBlock.SIGNAL_WHITE1));
			signalPattern.setWhite2(blockState.get(BaseSignalBlock.SIGNAL_WHITE2));
			signalPattern.setZs7(blockState.get(BaseSignalBlock.SIGNAL_ZS7));
		}
		if(hasSignalBlock(world, pos, SignalRegistration.SIGNAL_ZS3.get())) {
			BlockState blockState = world.getBlockState(getSignalBlockPos(world, pos, SignalRegistration.SIGNAL_ZS3.get()));
			signalPattern.setZs3Number(blockState.get(BaseSignalBlock.SIGNAL_NUMBER));
		}
		return signalPattern;
	}
	
	protected static void setSignalPattern(World world, BlockPos pos, SignalPattern pattern) {
		LOGGER.info("setSignalPattern at " + pos.getX() + "; " + pos.getY() + "; " + pos.getZ());
		BlockPos posZs3v = getSignalBlockPos(world, pos, SignalRegistration.SIGNAL_ZS3V.get());
		BlockPos posMain = getSignalBlockPos(world, pos, SignalRegistration.SIGNAL_MAIN.get());
		BlockPos posZs3 = getSignalBlockPos(world, pos, SignalRegistration.SIGNAL_ZS3.get());
		if(posZs3v != null) {
			world.setBlockState(posZs3v, pattern.getZs3vBlockState(world, posZs3v));
		}
		if(posMain != null) {
			world.setBlockState(posMain, pattern.getMainBlockState(world, posMain));
		}
		if(posZs3 != null) {
			world.setBlockState(posZs3, pattern.getZs3BlockState(world, posZs3));
		}
	}

}
