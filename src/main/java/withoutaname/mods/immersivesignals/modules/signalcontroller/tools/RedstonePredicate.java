package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RedstoneWireBlock;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.PredicateWidget;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.RedstonePredicateWidget;

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
	public boolean test(PredicateAdapterTile<RedstonePredicate> tile) {
		final World world = tile.getWorld();
		assert world != null;
		return power == getPowerOnSide(world, tile.getPos(), side);
	}

	public static RedstonePredicate fromInt(int i) {
		if ((i & 0xf) != 0) {
			return null;
		}
		i = i >>> 4;
		return new RedstonePredicate(Direction.values()[i & 0xf], i >>> 4);
	}

	@Override
	public RedstonePredicate fromNBT(INBT inbt) {
		if (inbt instanceof CompoundNBT) {
			CompoundNBT nbt = (CompoundNBT) inbt;
			return new RedstonePredicate(Direction.values()[nbt.getInt("side")], nbt.getInt("power"));
		}
		return new RedstonePredicate();
	}

	@Override
	public int toInt() {
		int i = side.ordinal() + (power << 4);
		return i << 4;
	}

	@Override
	public INBT toNBT() {
		final CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("side", side.ordinal());
		nbt.putInt("power", power);
		return nbt;
	}

	@Override
	public PredicateWidget createWidget(Consumer<Widget> buttonConsumer, int x, int y) {
		return new RedstonePredicateWidget(this, buttonConsumer, x, y);
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		if (power >= 0 && power <= 15){
			this.power = power;
		}
	}

	public Direction getSide() {
		return side;
	}

	public void setSide(Direction side) {
		this.side = side;
	}

	private int getPowerOnSide(World world, BlockPos pos, Direction side) {
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
