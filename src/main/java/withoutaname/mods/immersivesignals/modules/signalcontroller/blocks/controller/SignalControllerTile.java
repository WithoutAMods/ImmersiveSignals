package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SignalControllerTile extends TileEntity implements ITickableTileEntity {

	private SignalPattern defaultPattern = new SignalPattern(this::updateSignal);
	private boolean override = false;
	private SignalPattern overridePattern = new SignalPattern(this::updateSignal);

	public static final int BLINK_TIME = 15; // in ticks
	private boolean white2Blinking;
	private boolean ks1Blinking;
	private int blinkCounter = 0;

	public SignalControllerTile() {
		super(SignalControllerRegistration.SIGNAL_CONTROLLER_TILE.get());
	}

	public SignalPattern getDefaultPattern() {
		return defaultPattern;
	}

	public void setDefaultPattern(SignalPattern defaultPattern) {
		this.defaultPattern = defaultPattern.copy();
		this.defaultPattern.setOnChanged(this::updateSignal);
		this.updateSignal();
	}

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
		this.updateSignal();
	}

	public SignalPattern getOverridePattern() {
		return overridePattern;
	}

	@Override
	public void tick() {
		if (white2Blinking || ks1Blinking) {
			if (++blinkCounter == BLINK_TIME) {
				assert level != null;
				BlockPos posMain = getSignalBlockPos(SignalRegistration.SIGNAL_MAIN.get());
				if (posMain != null) {
					if (white2Blinking) {
						level.setBlockAndUpdate(posMain,
								level.getBlockState(posMain)
										.setValue(BaseSignalBlock.SIGNAL_WHITE2, true));

					}
					if (ks1Blinking) {
						level.setBlockAndUpdate(posMain,
								level.getBlockState(posMain)
										.setValue(BaseSignalBlock.SIGNAL_MAIN_PATTERN, BaseSignalBlock.SignalMainPattern.KS1));
					}
				}
			} else if (blinkCounter >= BLINK_TIME * 2) {
				assert level != null;
				BlockPos posMain = getSignalBlockPos(SignalRegistration.SIGNAL_MAIN.get());
				if (posMain != null) {
					if (white2Blinking) {
						level.setBlockAndUpdate(posMain,
								level.getBlockState(posMain)
										.setValue(BaseSignalBlock.SIGNAL_WHITE2, false));

					}
					if (ks1Blinking) {
						level.setBlockAndUpdate(posMain,
								level.getBlockState(posMain)
										.setValue(BaseSignalBlock.SIGNAL_MAIN_PATTERN, BaseSignalBlock.SignalMainPattern.NONE));
					}
				}
				blinkCounter = 0;
			}
		}
	}

	protected void updateSignal() {
		assert level != null;
		SignalPattern pattern = override ? overridePattern : defaultPattern;
		BlockPos posZs3 = getSignalBlockPos(SignalRegistration.SIGNAL_ZS3.get());
		BlockPos posMain = getSignalBlockPos(SignalRegistration.SIGNAL_MAIN.get());
		BlockPos posZs3v = getSignalBlockPos(SignalRegistration.SIGNAL_ZS3V.get());
		if (posZs3 != null) {
			level.setBlockAndUpdate(posZs3,
					level.getBlockState(posZs3)
							.setValue(BaseSignalBlock.SIGNAL_NUMBER, pattern.getZs3()));
		}
		if (posMain != null) {
			ks1Blinking = pattern.getZs3v() != 0;
			white2Blinking = pattern.isZs1();
			level.setBlockAndUpdate(posMain,
					level.getBlockState(posMain)
							.setValue(BaseSignalBlock.SIGNAL_MAIN_PATTERN, pattern.getZs3v() == 0 ? pattern.getMainPattern() : BaseSignalBlock.SignalMainPattern.NONE)
							.setValue(BaseSignalBlock.SIGNAL_WHITE0, pattern.isShortenedBrakingDistance() || pattern.isMarkerLight())
							.setValue(BaseSignalBlock.SIGNAL_WHITE1, pattern.isSh1())
							.setValue(BaseSignalBlock.SIGNAL_WHITE2, pattern.isApproachSignalRepeater() || pattern.isSh1())
							.setValue(BaseSignalBlock.SIGNAL_ZS7, pattern.isZs7()));
		}
		if (posZs3v != null) {
			level.setBlockAndUpdate(posZs3v,
					level.getBlockState(posZs3v)
							.setValue(BaseSignalBlock.SIGNAL_NUMBER, pattern.getZs3v()));
		}
	}

	@Nullable
	public BlockPos getSignalBlockPos(BaseSignalBlock block) {
		assert level != null;
		BlockPos blockPos = null;
		for (int i = 1; level.getBlockState(worldPosition.above(i)).getBlock() instanceof BaseSignalBlock && (level.getBlockState(worldPosition.above(i)).getBlock() != SignalRegistration.SIGNAL_FOUNDATION.get() || i == 1); i++) {
			if (level.getBlockState(worldPosition.above(i)).getBlock() == block) {
				blockPos = worldPosition.above(i);
				break;
			}
		}
		return blockPos;
	}

	public boolean hasSignalBlock(BaseSignalBlock block) {
		return getSignalBlockPos(block) != null;
	}

	@Override
	public void load(@Nonnull BlockState state, @Nonnull CompoundNBT nbt) {
		super.load(state, nbt);
		defaultPattern = SignalPattern.fromNBT(nbt.getCompound("defaultPattern"));
		defaultPattern.setOnChanged(this::updateSignal);
		override = nbt.getBoolean("override");
		overridePattern = SignalPattern.fromNBT(nbt.getCompound("overridePattern"));
		overridePattern.setOnChanged(this::updateSignal);
		ks1Blinking = nbt.getBoolean("ks1Blinking");
		white2Blinking = nbt.getBoolean("white2Blinking");
	}

	@Override
	@Nonnull
	public CompoundNBT save(@Nonnull CompoundNBT compound) {
		compound.put("defaultPattern", defaultPattern.toNBT());
		compound.putBoolean("override", override);
		compound.put("overridePattern", overridePattern.toNBT());
		compound.putBoolean("ks1Blinking", ks1Blinking);
		compound.putBoolean("white2Blinking", white2Blinking);
		return super.save(compound);
	}

}
