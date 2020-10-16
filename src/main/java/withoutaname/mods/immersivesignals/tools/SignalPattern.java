package withoutaname.mods.immersivesignals.tools;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import withoutaname.mods.immersivesignals.modules.signal.SignalRegistration;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock.SignalMainPattern;

public class SignalPattern {
	
	private int zs3vNumber;
	
	private SignalMainPattern mainPattern;
	private boolean white0;
	private boolean white1;
	private boolean white2;
	private boolean zs7;
	
	private int zs3Number;
	
	public static final SignalPattern DEFAULT_SIGNAL_PATTERN = new SignalPattern(0, SignalMainPattern.MODE_NONE, false, false, false, false, 0);
	
	public SignalPattern(int zs3vNumber,
			SignalMainPattern mainPattern, boolean white0, boolean white1, boolean white2, boolean zs7,
			int zs3Number) {
		setZs3vNumber(zs3vNumber);
		setMainPattern(mainPattern);
		setWhite0(white0);
		setWhite1(white1);
		setWhite2(white2);
		setZs7(zs7);
		setZs3Number(zs3Number);
	}
	
	public BlockState getZs3vBlockState(World world, BlockPos pos) {
		if(world.getBlockState(pos).getBlock() == SignalRegistration.SIGNAL_ZS3V.get()) {
			return world.getBlockState(pos)
					.with(BaseSignalBlock.SIGNAL_NUMBER, zs3vNumber);
		}
		return null;
	}
	
	public BlockState getMainBlockState(World world, BlockPos pos) {
		if(world.getBlockState(pos).getBlock() == SignalRegistration.SIGNAL_MAIN.get()) {
			return world.getBlockState(pos)
					.with(BaseSignalBlock.SIGNAL_MAIN_PATTERN, mainPattern)
					.with(BaseSignalBlock.SIGNAL_WHITE0, white0)
					.with(BaseSignalBlock.SIGNAL_WHITE1, white1)
					.with(BaseSignalBlock.SIGNAL_WHITE2, white2)
					.with(BaseSignalBlock.SIGNAL_ZS7, zs7);
		}
		return null;
	}
	
	public BlockState getZs3BlockState(World world, BlockPos pos) {
		if(world.getBlockState(pos).getBlock() == SignalRegistration.SIGNAL_ZS3.get()) {
			return world.getBlockState(pos)
					.with(BaseSignalBlock.SIGNAL_NUMBER, zs3Number);
		}
		return null;
	}

	public int getZs3vNumber() {
		return zs3vNumber;
	}

	public void setZs3vNumber(int zs3vNumber) {
		if(0 <= zs3vNumber && zs3vNumber <= 15) {
			this.zs3vNumber = zs3vNumber;
		} else {
			this.zs3vNumber = 0;
		}
	}

	public SignalMainPattern getMainPattern() {
		return mainPattern;
	}

	public void setMainPattern(SignalMainPattern mainPattern) {
		this.mainPattern = mainPattern;
	}

	public boolean isWhite0() {
		return white0;
	}

	public void setWhite0(boolean white0) {
		this.white0 = white0;
	}

	public boolean isWhite1() {
		return white1;
	}

	public void setWhite1(boolean white1) {
		this.white1 = white1;
	}

	public boolean isWhite2() {
		return white2;
	}

	public void setWhite2(boolean white2) {
		this.white2 = white2;
	}

	public boolean isZs7() {
		return zs7;
	}

	public void setZs7(boolean zs7) {
		this.zs7 = zs7;
	}

	public int getZs3Number() {
		return zs3Number;
	}

	public void setZs3Number(int zs3Number) {
		if(0 <= zs3Number && zs3Number <= 15) {
			this.zs3Number = zs3Number;
		} else {
			this.zs3Number = 0;
		}
	}
	
}
