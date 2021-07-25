package withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.PredicateWidget;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.RedstonePredicateWidget;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RedstonePredicate extends BasePredicate<RedstonePredicate> {
	
	private Direction side;
	private int power;
	
	public RedstonePredicate() {
		this.side = Direction.NORTH;
		this.power = 0;
	}
	
	public RedstonePredicate(Direction side, int power) {
		this.side = side;
		this.power = power;
	}
	
	@Override
	public boolean test(Level world, BlockPos pos) {
		return power == getPowerOnSide(world, pos, side);
	}
	
	@Override
	public RedstonePredicate fromBytes(@Nonnull FriendlyByteBuf buffer) {
		final byte b = buffer.readByte();
		return new RedstonePredicate(Direction.values()[b >>> 4], b & 0xf);
	}
	
	@Override
	public RedstonePredicate fromTag(Tag tag) {
		if (tag instanceof CompoundTag nbt) {
			return new RedstonePredicate(Direction.values()[nbt.getInt("side")], nbt.getInt("power"));
		}
		return new RedstonePredicate();
	}
	
	@Override
	public void toBytes(@Nonnull FriendlyByteBuf buffer) {
		buffer.writeByte((side.ordinal() << 4) + power);
	}
	
	@Override
	public Tag toTag() {
		final CompoundTag nbt = new CompoundTag();
		nbt.putInt("side", side.ordinal());
		nbt.putInt("power", power);
		return nbt;
	}
	
	@Override
	public int getId() {
		return 0;
	}
	
	@OnlyIn(Dist.CLIENT)
	@Override
	public PredicateWidget createWidget(Consumer<AbstractWidget> buttonConsumer, int x, int y) {
		return new RedstonePredicateWidget(this, buttonConsumer, x, y);
	}
	
	public int getPower() {
		return power;
	}
	
	public void setPower(int power) {
		if (power >= 0 && power <= 15) {
			this.power = power;
		}
	}
	
	public Direction getSide() {
		return side;
	}
	
	public void setSide(Direction side) {
		this.side = side;
	}
	
	private int getPowerOnSide(@Nonnull Level level, @Nonnull BlockPos pos, Direction side) {
		BlockPos blockPos = pos.relative(side);
		BlockState blockstate = level.getBlockState(blockPos);
		Block block = blockstate.getBlock();
		if (blockstate.isSignalSource()) {
			if (block == Blocks.REDSTONE_BLOCK) {
				return 15;
			} else {
				return block == Blocks.REDSTONE_WIRE ? blockstate.getValue(RedStoneWireBlock.POWER) : level.getDirectSignal(blockPos, side);
			}
		} else {
			return 0;
		}
	}
	
}
