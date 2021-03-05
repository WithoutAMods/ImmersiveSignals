package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.IPatternModifyHandler;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.PatternModifyPacket;
import withoutaname.mods.immersivesignals.tools.SignalPattern;

public class SignalControllerContainer extends Container implements IPatternModifyHandler {

	private SignalControllerTile tile;

	private SignalPattern defaultPattern = new SignalPattern();
	private Runnable onDefaultPatternChanged = () -> {};
	private boolean override = false;
	private Runnable onOverrideChanged = () -> {};
	private SignalPattern overridePattern = new SignalPattern();
	private Runnable onOverridePatternChanged = () -> {};

	public SignalControllerContainer(int id, World world, BlockPos pos) {
		super(SignalControllerRegistration.SIGNAL_CONTROLLER_CONTAINER.get(), id);
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof SignalControllerTile) {
			tile = (SignalControllerTile) te;
			trackInt(new IntReferenceHolder() {
				@Override
				public int get() {
					return writeSignalPattern(tile.getDefaultPattern());
				}
				@Override
				public void set(int value) {
					defaultPattern = readSignalPattern(value);
					onDefaultPatternChanged.run();
				}
			});
			trackInt(new IntReferenceHolder() {
				@Override
				public int get() {
					return tile.isOverride() ? 1 : 0;
				}
				@Override
				public void set(int value) {
					override = value == 1;
					onOverrideChanged.run();
				}
			});
			trackInt(new IntReferenceHolder() {
				@Override
				public int get() {
					return writeSignalPattern(tile.getOverridePattern());
				}
				@Override
				public void set(int value) {
					overridePattern = readSignalPattern(value);
					onOverridePatternChanged.run();
				}
			});
		}
	}

	private SignalPattern readSignalPattern(int i) {
		return new SignalPattern(
				BaseSignalBlock.SignalMainPattern.values()[(i >>> 14) & 0b11],
				(i >>> 10) & 0b1111,
				(i >>> 6) & 0b1111,
				((i >>> 5) & 0b1) == 1,
				((i >>> 4) & 0b1) == 1,
				((i >>> 3) & 0b1) == 1,
				((i >>> 2) & 0b1) == 1,
				((i >>> 1) & 0b1) == 1,
				((i) & 0b1) == 1);
	}

	private int writeSignalPattern(SignalPattern signalPattern) {
		int i = 0;
		i += (signalPattern.getMainPattern().ordinal() & 0b11) << 14;
		i += (signalPattern.getZs3() & 0b1111) << 10;
		i += (signalPattern.getZs3v() & 0b1111) << 6;
		i += ((signalPattern.isShortenedBrakingDistance() ? 1 : 0) & 0b1) << 5;
		i += ((signalPattern.isApproachSignalRepeater() ? 1 : 0) & 0b1) << 4;
		i += ((signalPattern.isZs7() ? 1 : 0) & 0b1) << 3;
		i += ((signalPattern.isSh1() ? 1 : 0) & 0b1) << 2;
		i += ((signalPattern.isZs1() ? 1 : 0) & 0b1) << 1;
		i += ((signalPattern.isMarkerLight() ? 1 : 0) & 0b1);
		return i;
	}

	@Override
	public boolean enchantItem(@NotNull PlayerEntity playerIn, int id) {
		if (id == 0) {
			tile.setOverride(!tile.isOverride());
			return true;
		}
		return false;
	}

	@Override
	public void onPatternModify(PatternModifyPacket packet) {
		switch (packet.getButtonID()) {
			case 0:
				tile.getOverridePattern().setMainPattern(BaseSignalBlock.SignalMainPattern.values()[packet.getValue()]);
				break;
			case 1:
				tile.getOverridePattern().setZs3(packet.getValue());
				break;
			case 2:
				tile.getOverridePattern().setZs3v(packet.getValue());
				break;
			case 3:
				tile.getOverridePattern().setShortenedBrakingDistance(!tile.getOverridePattern().isShortenedBrakingDistance());
				break;
			case 4:
				tile.getOverridePattern().setApproachSignalRepeater(!tile.getOverridePattern().isApproachSignalRepeater());
				break;
			case 5:
				tile.getOverridePattern().setZs7(!tile.getOverridePattern().isZs7());
				break;
			case 6:
				tile.getOverridePattern().setSh1(!tile.getOverridePattern().isSh1());
				break;
			case 7:
				tile.getOverridePattern().setZs1(!tile.getOverridePattern().isZs1());
				break;
			case 8:
				tile.getOverridePattern().setMarkerLight(!tile.getOverridePattern().isMarkerLight());
				break;
		}
	}

	@OnlyIn(Dist.CLIENT)
	public SignalPattern getDefaultPattern() {
		return defaultPattern;
	}

	@OnlyIn(Dist.CLIENT)
	public void setOnDefaultPatternChanged(Runnable onDefaultPatternChanged) {
		this.onDefaultPatternChanged = onDefaultPatternChanged;
	}

	@OnlyIn(Dist.CLIENT)
	public boolean isOverride() {
		return override;
	}

	@OnlyIn(Dist.CLIENT)
	public void setOnOverrideChanged(Runnable onOverrideChanged) {
		this.onOverrideChanged = onOverrideChanged;
	}

	@OnlyIn(Dist.CLIENT)
	public SignalPattern getOverridePattern() {
		return overridePattern;
	}

	@OnlyIn(Dist.CLIENT)
	public void setOnOverridePatternChanged(Runnable onOverridePatternChanged) {
		this.onOverridePatternChanged = onOverridePatternChanged;
	}

	@Override
	public boolean canInteractWith(@NotNull PlayerEntity playerIn) {
		return true;
	}

}
