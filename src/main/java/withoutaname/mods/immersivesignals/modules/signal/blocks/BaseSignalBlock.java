package withoutaname.mods.immersivesignals.modules.signal.blocks;

import javax.annotation.Nonnull;

import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;

public class BaseSignalBlock extends Block {
	
	public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
	
	public static final EnumProperty<SignalMastsignMode> SIGNAL_MASTSIGN = EnumProperty.create("mastsign", SignalMastsignMode.class);
	
	public static final EnumProperty<SignalMainPattern> SIGNAL_MAIN_PATTERN = EnumProperty.create("signal_pattern", SignalMainPattern.class);
	public static final BooleanProperty SIGNAL_WHITE0 = BooleanProperty.create("signal_white0");
	public static final BooleanProperty SIGNAL_WHITE1 = BooleanProperty.create("signal_white1");
	public static final BooleanProperty SIGNAL_WHITE2 = BooleanProperty.create("signal_white2");
	public static final BooleanProperty SIGNAL_ZS7 = BooleanProperty.create("signal_zs7");
	
	public static final IntegerProperty SIGNAL_NUMBER = IntegerProperty.create("signal_number", 0, 15); //0: black; 1-15: x * 10 km/h
	protected VoxelShape shape = Shapes.box(.25, 0, .25, .75, 1, .75);
	
	public BaseSignalBlock() {
		super(Properties.of(Material.METAL)
				.sound(SoundType.METAL)
				.strength(1.5F, 6.0F));
	}
	
	public static boolean createSignal(Level level, BlockPos pos, int rotation, int mainHeight, boolean withZS3, boolean withZS3V) {
		boolean enoughSpace = true;
		int height = (withZS3 ? mainHeight : mainHeight + 1);
		for (int i = 0; i < height; i++) {
			if (!level.getBlockState(pos.offset(0, i, 0)).getMaterial().isReplaceable()) {
				enoughSpace = false;
				break;
			}
		}
		
		if (enoughSpace) {
			level.setBlockAndUpdate(pos, SignalRegistration.SIGNAL_FOUNDATION.get().defaultBlockState()
					.setValue(ROTATION, rotation));
			
			int numberOfPosts = (withZS3V ? mainHeight - 3 : mainHeight - 2);
			for (int i = 0; i < numberOfPosts; i++) {
				if (i == (numberOfPosts - 1) / 2.0D) {
					level.setBlockAndUpdate(pos.offset(0, i + 1, 0), SignalRegistration.SIGNAL_POST.get().defaultBlockState()
							.setValue(ROTATION, rotation)
							.setValue(SIGNAL_MASTSIGN, SignalMastsignMode.MODE_BOTH));
				} else if (i == (numberOfPosts - 2) / 2.0D) {
					level.setBlockAndUpdate(pos.offset(0, i + 1, 0), SignalRegistration.SIGNAL_POST.get().defaultBlockState()
							.setValue(ROTATION, rotation)
							.setValue(SIGNAL_MASTSIGN, SignalMastsignMode.MODE_Y));
				} else if (i == (numberOfPosts) / 2.0D) {
					level.setBlockAndUpdate(pos.offset(0, i + 1, 0), SignalRegistration.SIGNAL_POST.get().defaultBlockState()
							.setValue(ROTATION, rotation)
							.setValue(SIGNAL_MASTSIGN, SignalMastsignMode.MODE_WRW));
				} else {
					level.setBlockAndUpdate(pos.offset(0, i + 1, 0), SignalRegistration.SIGNAL_POST.get().defaultBlockState()
							.setValue(ROTATION, rotation)
							.setValue(SIGNAL_MASTSIGN, SignalMastsignMode.MODE_NONE));
				}
			}
			
			if (withZS3V) {
				level.setBlockAndUpdate(pos.offset(0, mainHeight - 2, 0), SignalRegistration.SIGNAL_ZS3V.get().defaultBlockState()
						.setValue(ROTATION, rotation)
						.setValue(SIGNAL_NUMBER, 0));
			}
			level.setBlockAndUpdate(pos.offset(0, mainHeight - 1, 0), SignalRegistration.SIGNAL_MAIN.get().defaultBlockState()
					.setValue(ROTATION, rotation)
					.setValue(SIGNAL_MAIN_PATTERN, SignalMainPattern.NONE)
					.setValue(SIGNAL_WHITE0, false)
					.setValue(SIGNAL_WHITE1, false)
					.setValue(SIGNAL_WHITE2, false)
					.setValue(SIGNAL_ZS7, false));
			if (withZS3V) {
				level.setBlockAndUpdate(pos.offset(0, mainHeight, 0), SignalRegistration.SIGNAL_ZS3.get().defaultBlockState()
						.setValue(ROTATION, rotation)
						.setValue(SIGNAL_NUMBER, 0));
			}
		}
		
		return enoughSpace;
	}
	
	@Override
	public ItemStack getPickBlock(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		return new ItemStack(SignalRegistration.SIGNAL_ITEM.get());
	}
	
	@Nonnull
	@Override
	public VoxelShape getShape(@Nonnull BlockState state, @Nonnull BlockGetter worldIn, @Nonnull BlockPos pos, @Nonnull CollisionContext context) {
		return shape;
	}
	
	@Override
	public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
		if (!level.isClientSide) {
			removeSignal(level, pos);
		}
		super.onBlockExploded(state, level, pos, explosion);
	}
	
	@Override
	public void playerWillDestroy(Level level, @Nonnull BlockPos pos, @Nonnull BlockState state, @Nonnull Player player) {
		if (!level.isClientSide) {
			removeSignal(level, pos);
		}
		super.playerWillDestroy(level, pos, state, player);
	}
	
	public void removeSignal(@Nonnull Level level, BlockPos pos) {
		if (!(level.getBlockState(pos).getBlock() == SignalRegistration.SIGNAL_FOUNDATION.get())) {
			boolean end;
			for (int i = 1; level.getBlockState(pos.below(i)).getBlock() instanceof BaseSignalBlock; i++) {
				end = level.getBlockState(pos.below(i)).getBlock() == SignalRegistration.SIGNAL_FOUNDATION.get();
				level.setBlockAndUpdate(pos.below(i), Blocks.AIR.defaultBlockState());
				if (end) {
					break;
				}
			}
		}
		if (!(level.getBlockState(pos.above()).getBlock() == SignalRegistration.SIGNAL_FOUNDATION.get())) {
			for (int i = 1; level.getBlockState(pos.above(i)).getBlock() instanceof BaseSignalBlock; i++) {
				if (level.getBlockState(pos.above(i)).getBlock() == SignalRegistration.SIGNAL_FOUNDATION.get()) {
					break;
				}
				level.setBlockAndUpdate(pos.above(i), Blocks.AIR.defaultBlockState());
			}
		}
	}
	
	public enum SignalMastsignMode implements StringRepresentable {
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
		
		@Nonnull
		@Override
		public String getSerializedName() {
			return name;
		}
	}
	
	public enum SignalMainPattern implements StringRepresentable {
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
		
		@Nonnull
		@Override
		public String getSerializedName() {
			return name;
		}
		
	}
	
}
