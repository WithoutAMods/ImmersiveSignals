package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks;

import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import org.jetbrains.annotations.Nullable;
import withoutaname.mods.immersivesignals.modules.signal.blocks.BaseSignalBlock;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.PatternModifyPacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

public abstract class BaseSignalPatternContainer extends Container {

	protected BaseSignalPatternContainer(@Nullable ContainerType<?> type, int id) {
		super(type, id);
	}

	protected abstract SignalPattern getModifiablePattern();

	public void onPatternModify(PatternModifyPacket packet) {
		switch (packet.getButtonID()) {
			case 0:
				getModifiablePattern().setMainPattern(BaseSignalBlock.SignalMainPattern.values()[packet.getValue()]);
				break;
			case 1:
				getModifiablePattern().setZs3(packet.getValue());
				break;
			case 2:
				getModifiablePattern().setZs3v(packet.getValue());
				break;
			case 3:
				getModifiablePattern().setShortenedBrakingDistance(!getModifiablePattern().isShortenedBrakingDistance());
				break;
			case 4:
				getModifiablePattern().setApproachSignalRepeater(!getModifiablePattern().isApproachSignalRepeater());
				break;
			case 5:
				getModifiablePattern().setZs7(!getModifiablePattern().isZs7());
				break;
			case 6:
				getModifiablePattern().setSh1(!getModifiablePattern().isSh1());
				break;
			case 7:
				getModifiablePattern().setZs1(!getModifiablePattern().isZs1());
				break;
			case 8:
				getModifiablePattern().setMarkerLight(!getModifiablePattern().isMarkerLight());
				break;
		}
	}

}
