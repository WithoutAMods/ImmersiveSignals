package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Section {

	private final String name;
	private final List<SubSection> subSections = new ArrayList<>();

	public Section(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public List<SubSection> getSubSections() {
		return subSections;
	}

	public static Section deserialize(INBT nbt, Consumer<Pair<SubSection, INBT>> subSectionINBTPairConsumer) {
		if (nbt instanceof CompoundNBT) {
			CompoundNBT compoundNBT = (CompoundNBT) nbt;
			String name = compoundNBT.getString("name");
			INBT subSectionsNBT = compoundNBT.get("subSections");

			Section section = new Section(name);
			if (subSectionsNBT instanceof ListNBT) {
				for (INBT subSectionNBT : (ListNBT) subSectionsNBT) {
					SubSection subSection = new SubSection();
					subSectionINBTPairConsumer.accept(new Pair<>(subSection, subSectionNBT));
					section.subSections.add(subSection);
				}
			}
			return section;
		}
		return null;
	}

	public INBT serialize() {
		ListNBT subSectionsNBT = new ListNBT();
		for (SubSection subSection : subSections) {
			subSectionsNBT.add(subSection.serialize());
		}

		CompoundNBT nbt = new CompoundNBT();
		nbt.putString("name", name);
		nbt.put("subSections", subSectionsNBT);
		return nbt;
	}
}
