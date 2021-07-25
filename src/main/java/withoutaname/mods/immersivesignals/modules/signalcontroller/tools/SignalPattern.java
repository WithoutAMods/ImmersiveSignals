package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import javax.annotation.Nonnull;

import net.minecraft.nbt.CompoundTag;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock.SignalMainPattern;

public class SignalPattern {
	
	private SignalMainPattern mainPattern = SignalMainPattern.NONE;
	private int zs3 = 0;
	private int zs3v = 0;
	private boolean shortenedBrakingDistance = false;
	private boolean approachSignalRepeater = false;
	private boolean zs7 = false;
	private boolean sh1 = false;
	private boolean zs1 = false;
	private boolean markerLight = false;
	
	private Runnable onChanged = () -> {};
	
	public SignalPattern() {
	}
	
	public SignalPattern(Runnable onChanged) {
		this.onChanged = onChanged;
	}
	
	public SignalPattern(SignalMainPattern mainPattern, int zs3, int zs3v, boolean shortenedBrakingDistance, boolean approachSignalRepeater, boolean zs7, boolean sh1, boolean zs1, boolean markerLight) {
		setMainPattern(mainPattern);
		setZs3(zs3);
		setZs3v(zs3v);
		setShortenedBrakingDistance(shortenedBrakingDistance);
		setApproachSignalRepeater(approachSignalRepeater);
		setZs7(zs7);
		setSh1(sh1);
		setZs1(zs1);
		setMarkerLight(markerLight);
	}
	
	public SignalPattern(SignalMainPattern mainPattern, int zs3, int zs3v, boolean shortenedBrakingDistance, boolean approachSignalRepeater, boolean zs7, boolean sh1, boolean zs1, boolean markerLight, Runnable onChanged) {
		this(mainPattern, zs3, zs3v, shortenedBrakingDistance, approachSignalRepeater, zs7, sh1, zs1, markerLight);
		this.onChanged = onChanged;
	}
	
	public void setOnChanged(Runnable onChanged) {
		this.onChanged = onChanged;
	}
	
	public SignalPattern copy() {
		return new SignalPattern(mainPattern, zs3, zs3v, shortenedBrakingDistance, approachSignalRepeater, zs7, sh1, zs1, markerLight);
	}
	
	public static SignalPattern fromInt(int i) {
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
	
	@Nonnull
	public static SignalPattern fromNBT(@Nonnull CompoundTag compoundNBT) {
		SignalPattern signalPattern = new SignalPattern();
		signalPattern.setMainPattern(SignalMainPattern.fromString(compoundNBT.getString("main")));
		signalPattern.setZs3(compoundNBT.getInt("zs3"));
		signalPattern.setZs3v(compoundNBT.getInt("zs3v"));
		signalPattern.setShortenedBrakingDistance(compoundNBT.getBoolean("shortenedBrakingDistance"));
		signalPattern.setApproachSignalRepeater(compoundNBT.getBoolean("approachSignalRepeater"));
		signalPattern.setZs7(compoundNBT.getBoolean("zs7"));
		signalPattern.setSh1(compoundNBT.getBoolean("sh1"));
		signalPattern.setZs1(compoundNBT.getBoolean("zs1"));
		signalPattern.setMarkerLight(compoundNBT.getBoolean("markerLight"));
		return signalPattern;
	}
	
	public int toInt() {
		int i = 0;
		i += (mainPattern.ordinal() & 0b11) << 14;
		i += (zs3 & 0b1111) << 10;
		i += (zs3v & 0b1111) << 6;
		i += ((shortenedBrakingDistance ? 1 : 0) & 0b1) << 5;
		i += ((approachSignalRepeater ? 1 : 0) & 0b1) << 4;
		i += ((zs7 ? 1 : 0) & 0b1) << 3;
		i += ((sh1 ? 1 : 0) & 0b1) << 2;
		i += ((zs1 ? 1 : 0) & 0b1) << 1;
		i += ((markerLight ? 1 : 0) & 0b1);
		return i;
	}
	
	public CompoundTag toTag() {
		CompoundTag compoundNBT = new CompoundTag();
		compoundNBT.putString("main", mainPattern.toString());
		compoundNBT.putInt("zs3", zs3);
		compoundNBT.putInt("zs3v", zs3v);
		compoundNBT.putBoolean("shortenedBrakingDistance", shortenedBrakingDistance);
		compoundNBT.putBoolean("approachSignalRepeater", approachSignalRepeater);
		compoundNBT.putBoolean("zs7", zs7);
		compoundNBT.putBoolean("sh1", sh1);
		compoundNBT.putBoolean("zs1", zs1);
		compoundNBT.putBoolean("markerLight", markerLight);
		return compoundNBT;
	}
	
	public SignalMainPattern getMainPattern() {
		return mainPattern;
	}
	
	public void setMainPattern(SignalMainPattern mainPattern) {
		this.mainPattern = mainPattern;
		if (!isZs3Possible()) {
			zs3 = 0;
		}
		if (!isZs3vPossible()) {
			zs3v = 0;
		}
		if (!isShortenedBrakingDistancePossible()) {
			shortenedBrakingDistance = false;
		}
		if (!isApproachSignalRepeaterPossible()) {
			approachSignalRepeater = false;
		}
		if (!isZs7Possible()) {
			zs7 = false;
		}
		if (!isSh1Possible()) {
			sh1 = false;
		}
		if (!isZs1Possible()) {
			zs1 = false;
		}
		if (!isMarkerLightPossible()) {
			markerLight = false;
		}
		onChanged.run();
	}
	
	public boolean isZs3Possible() {
		return (mainPattern == SignalMainPattern.KS1 || mainPattern == SignalMainPattern.KS2) && !markerLight;
	}
	
	public int getZs3() {
		return zs3;
	}
	
	public void setZs3(int zs3) {
		if (isZs3Possible() && zs3 >= 0 && zs3 <= 15) {
			this.zs3 = zs3;
			onChanged.run();
		}
	}
	
	public boolean isZs3vPossible() {
		return mainPattern == SignalMainPattern.KS1 && !markerLight;
	}
	
	public int getZs3v() {
		return zs3v;
	}
	
	public void setZs3v(int zs3v) {
		if (isZs3vPossible() && zs3v >= 0 && zs3v <= 15) {
			this.zs3v = zs3v;
			onChanged.run();
		}
	}
	
	public boolean isShortenedBrakingDistancePossible() {
		return !approachSignalRepeater && ((mainPattern == SignalMainPattern.KS1 && zs3v != 0) || mainPattern == SignalMainPattern.KS2) && !markerLight;
	}
	
	public boolean isShortenedBrakingDistance() {
		return shortenedBrakingDistance;
	}
	
	public void setShortenedBrakingDistance(boolean shortenedBrakingDistance) {
		if (isShortenedBrakingDistancePossible()) {
			this.shortenedBrakingDistance = shortenedBrakingDistance;
			onChanged.run();
		}
	}
	
	public boolean isApproachSignalRepeaterPossible() {
		return !shortenedBrakingDistance && ((mainPattern == SignalMainPattern.KS1 && zs3v != 0) || mainPattern == SignalMainPattern.KS2) && !markerLight;
	}
	
	public boolean isApproachSignalRepeater() {
		return approachSignalRepeater;
	}
	
	public void setApproachSignalRepeater(boolean approachSignalRepeater) {
		if (isApproachSignalRepeaterPossible()) {
			this.approachSignalRepeater = approachSignalRepeater;
			onChanged.run();
		}
	}
	
	public boolean isZs7Possible() {
		return !sh1 && !markerLight;
	}
	
	public boolean isZs7() {
		return zs7;
	}
	
	public void setZs7(boolean zs7) {
		if (isZs7Possible()) {
			this.zs7 = zs7;
			onChanged.run();
		}
	}
	
	public boolean isSh1Possible() {
		return mainPattern == SignalMainPattern.HP0 && !zs7 && !markerLight;
	}
	
	public boolean isSh1() {
		return sh1;
	}
	
	public void setSh1(boolean sh1) {
		if (isSh1Possible()) {
			this.sh1 = sh1;
			onChanged.run();
		}
	}
	
	public boolean isZs1Possible() {
		return true;
	}
	
	public boolean isZs1() {
		return zs1;
	}
	
	public void setZs1(boolean zs1) {
		if (isZs1Possible()) {
			this.zs1 = zs1;
			onChanged.run();
		}
	}
	
	public boolean isMarkerLightPossible() {
		return mainPattern == SignalMainPattern.NONE && zs3 == 0 && zs3v == 0
				&& !shortenedBrakingDistance && !approachSignalRepeater && !zs7 && !sh1;
	}
	
	public boolean isMarkerLight() {
		return markerLight;
	}
	
	public void setMarkerLight(boolean markerLight) {
		if (isMarkerLightPossible()) {
			this.markerLight = markerLight;
			onChanged.run();
		}
	}
	
}
