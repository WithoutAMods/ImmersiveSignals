package withoutaname.mods.immersivesignals.modules.signalcontroller.data;

import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.Line;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.Section;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SubSection;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class CapabilitySections {

	@CapabilityInject(ISections.class)
	public static Capability<ISections> SECTIONS_CAPABILITY = null;

	public static void register() {
		CapabilityManager.INSTANCE.register(ISections.class, new Storage(), DefaultSections::new);
	}

	public static class Storage implements Capability.IStorage<ISections> {

		@Nullable
		@Override
		public INBT writeNBT(Capability<ISections> capability, ISections instance, Direction side) {
			ListNBT sectionsNBT = new ListNBT();
			for (Section section : instance.getSections()) {
				sectionsNBT.add(section.serialize());
			}
			ListNBT linesNBT = new ListNBT();
			for (Line line : instance.getLines()) {
				linesNBT.add(line.serialize());
			}

			CompoundNBT nbt = new CompoundNBT();
			nbt.put("sections", sectionsNBT);
			nbt.put("lines", linesNBT);
			return nbt;
		}

		@Override
		public void readNBT(Capability<ISections> capability, ISections instance, Direction side, INBT nbt) {
			if (nbt instanceof CompoundNBT) {
				CompoundNBT compoundNBT = (CompoundNBT) nbt;
				INBT sectionsNBT = compoundNBT.get("sections");
				INBT linesNBT = compoundNBT.get("lines");

				List<Pair<SubSection, INBT>> subSectionINBTPairs = new ArrayList<>();
				Consumer<Pair<SubSection, INBT>> subSectionINBTPairConsumer = subSectionINBTPairs::add;
				if (sectionsNBT instanceof ListNBT) {
					for (INBT sectionNBT : ((ListNBT) sectionsNBT)) {
						instance.getSections().add(Section.deserialize(sectionNBT, subSectionINBTPairConsumer));
					}
				}
				if (linesNBT instanceof ListNBT) {
					for (INBT lineNBT : ((ListNBT) linesNBT)) {
						instance.getLines().add(Line.deserialize(lineNBT));
					}
				}
				for (Pair<SubSection, INBT> subSectionINBTPair : subSectionINBTPairs) {
					subSectionINBTPair.getFirst().deserialize(subSectionINBTPair.getSecond(), instance.getLines());
				}
			}
		}
	}
}
