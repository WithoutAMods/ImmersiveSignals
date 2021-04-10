package withoutaname.mods.immersivesignals.modules.signal.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;

public class BaseSignalBlock extends Block{
	
	public static final EnumProperty<SignalMastsignMode> SIGNAL_MASTSIGN = EnumProperty.create("mastsign", SignalMastsignMode.class);

	public static final EnumProperty<SignalMainPattern> SIGNAL_MAIN_PATTERN = EnumProperty.create("signal_pattern", SignalMainPattern.class);
	public static final BooleanProperty SIGNAL_WHITE0 = BooleanProperty.create("signal_white0");
	public static final BooleanProperty SIGNAL_WHITE1 = BooleanProperty.create("signal_white1");
	public static final BooleanProperty SIGNAL_WHITE2 = BooleanProperty.create("signal_white2");
	public static final BooleanProperty SIGNAL_ZS7 = BooleanProperty.create("signal_zs7");
	
	public static final IntegerProperty SIGNAL_NUMBER = IntegerProperty.create("signal_number", 0, 15); //0: black; 1-15: x * 10 km/h
	protected  VoxelShape shape = VoxelShapes.box(.25, 0, .25, .75, 1, .75);

	public BaseSignalBlock() {
		super(Properties.of(Material.METAL)
				.sound(SoundType.METAL)
				.strength(1.5F, 6.0F));
	}

	@Override
	public ItemStack getPickBlock(BlockState state, RayTraceResult target, IBlockReader world, BlockPos pos, PlayerEntity player) {
		return new ItemStack(SignalRegistration.SIGNAL_ITEM.get());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return shape;
	}
	
	@Override
	public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
		if(!world.isClientSide) {
			removeSignal(world, pos);
		}
		super.onBlockExploded(state, world, pos, explosion);
	}
	
	@Override
	public void playerWillDestroy(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
		if(!worldIn.isClientSide) {
			removeSignal(worldIn, pos);
		}
		super.playerWillDestroy(worldIn, pos, state, player);
	}
	
	public static boolean createSignal(World world, BlockPos pos, Direction facing, int mainHeight, boolean withZS3, boolean withZS3V) {
		boolean enoughSpace = true;
		int height = (withZS3 ? mainHeight : mainHeight + 1);
		for(int i = 0; i < height; i++) {
			if(!world.getBlockState(pos.offset(0, i, 0)).getMaterial().isReplaceable()) {
				enoughSpace = false;
				break;
			}
		}
		
		if(enoughSpace) {
			world.setBlockAndUpdate(pos, SignalRegistration.SIGNAL_FOUNDATION.get().defaultBlockState()
					.setValue(BlockStateProperties.HORIZONTAL_FACING, facing));
			
			int numberOfPosts = (withZS3V ? mainHeight - 3 : mainHeight - 2);
			for(int i = 0; i < numberOfPosts; i++) {
				if(i == (numberOfPosts - 1) / 2.0D) { 
					world.setBlockAndUpdate(pos.offset(0, i + 1, 0), SignalRegistration.SIGNAL_POST.get().defaultBlockState()
							.setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
							.setValue(SIGNAL_MASTSIGN, SignalMastsignMode.MODE_BOTH));
				} else if(i == (numberOfPosts - 2) / 2.0D) { 
					world.setBlockAndUpdate(pos.offset(0, i + 1, 0), SignalRegistration.SIGNAL_POST.get().defaultBlockState()
							.setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
							.setValue(SIGNAL_MASTSIGN, SignalMastsignMode.MODE_Y));
				} else if(i == (numberOfPosts) / 2.0D) { 
					world.setBlockAndUpdate(pos.offset(0, i + 1, 0), SignalRegistration.SIGNAL_POST.get().defaultBlockState()
							.setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
							.setValue(SIGNAL_MASTSIGN, SignalMastsignMode.MODE_WRW));
				} else { 
					world.setBlockAndUpdate(pos.offset(0, i + 1, 0), SignalRegistration.SIGNAL_POST.get().defaultBlockState()
							.setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
							.setValue(SIGNAL_MASTSIGN, SignalMastsignMode.MODE_NONE));
				}
			}
			
			if(withZS3V) {
				world.setBlockAndUpdate(pos.offset(0, mainHeight - 2, 0), SignalRegistration.SIGNAL_ZS3V.get().defaultBlockState()
						.setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
						.setValue(SIGNAL_NUMBER, 0));
			}
			world.setBlockAndUpdate(pos.offset(0, mainHeight - 1, 0), SignalRegistration.SIGNAL_MAIN.get().defaultBlockState()
					.setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
					.setValue(SIGNAL_MAIN_PATTERN, SignalMainPattern.NONE)
					.setValue(SIGNAL_WHITE0, false)
					.setValue(SIGNAL_WHITE1, false)
					.setValue(SIGNAL_WHITE2, false)
					.setValue(SIGNAL_ZS7, false));
			if(withZS3V) {
				world.setBlockAndUpdate(pos.offset(0, mainHeight, 0), SignalRegistration.SIGNAL_ZS3.get().defaultBlockState()
						.setValue(BlockStateProperties.HORIZONTAL_FACING, facing)
						.setValue(SIGNAL_NUMBER, 0));
			}
		}
		
		return enoughSpace;
	}
	
	public void removeSignal(World world, BlockPos pos) {
		if(!(world.getBlockState(pos).getBlock() == SignalRegistration.SIGNAL_FOUNDATION.get())) {
			boolean end;
			for(int i = 1; world.getBlockState(pos.below(i)).getBlock() instanceof BaseSignalBlock; i++) {
				end = world.getBlockState(pos.below(i)).getBlock() == SignalRegistration.SIGNAL_FOUNDATION.get();
				world.setBlockAndUpdate(pos.below(i), Blocks.AIR.defaultBlockState());
				if(end) {
					break;
				}
			}
		}
		if(!(world.getBlockState(pos.above()).getBlock() == SignalRegistration.SIGNAL_FOUNDATION.get())) {
			for(int i = 1; world.getBlockState(pos.above(i)).getBlock() instanceof BaseSignalBlock; i++) {
				if(world.getBlockState(pos.above(i)).getBlock() == SignalRegistration.SIGNAL_FOUNDATION.get()) {
					break;
				}
				world.setBlockAndUpdate(pos.above(i), Blocks.AIR.defaultBlockState());
			}
		}
	}
	
	public enum SignalMastsignMode implements IStringSerializable {
		MODE_NONE("none"),
		MODE_WRW("wrw"),
		MODE_BOTH("both"),
		MODE_Y("y");
		
		private final String name;

		SignalMastsignMode(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}
	}
	
	public enum SignalMainPattern implements IStringSerializable {
		NONE("none"),
		HP0("hp0"),
		KS1("ks1"),
		KS2("ks2");
		
		private final String name;

		SignalMainPattern(String name) {
			this.name = name;
		}

		public static SignalMainPattern fromString(String name) {
			if ("hp0".equalsIgnoreCase(name)) {
				return HP0;
			} else if ("ks1".equalsIgnoreCase(name)) {
				return KS1;
			} else if ("ks2".equalsIgnoreCase(name)) {
				return KS2;
			} else {
				return NONE;
			}
		}

		@Override
		public String toString() {
			return name;
		}

		@Override
		public String getSerializedName() {
			return name;
		}
		
	}

}
