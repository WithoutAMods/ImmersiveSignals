package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.PatternModifyPacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

public abstract class BaseSignalPatternContainer extends AbstractContainerMenu {
	
	protected BaseSignalPatternContainer(@Nullable MenuType<?> type, int id) {
		super(type, id);
	}
	
	protected abstract SignalPattern getModifiablePattern();
	
	public void onPatternModify(@Nonnull PatternModifyPacket packet) {
		switch (packet.getButtonID()) {
			case 0 -> getModifiablePattern().setMainPattern(BaseSignalBlock.SignalMainPattern.values()[packet.getValue()]);
			case 1 -> getModifiablePattern().setZs3(packet.getValue());
			case 2 -> getModifiablePattern().setZs3v(packet.getValue());
			case 3 -> getModifiablePattern().setShortenedBrakingDistance(!getModifiablePattern().isShortenedBrakingDistance());
			case 4 -> getModifiablePattern().setApproachSignalRepeater(!getModifiablePattern().isApproachSignalRepeater());
			case 5 -> getModifiablePattern().setZs7(!getModifiablePattern().isZs7());
			case 6 -> getModifiablePattern().setSh1(!getModifiablePattern().isSh1());
			case 7 -> getModifiablePattern().setZs1(!getModifiablePattern().isZs1());
			case 8 -> getModifiablePattern().setMarkerLight(!getModifiablePattern().isMarkerLight());
		}
	}
	
}
