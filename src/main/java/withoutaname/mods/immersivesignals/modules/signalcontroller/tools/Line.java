package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import net.minecraft.nbt.*;

import java.util.ArrayList;
import java.util.List;

public class Line {

	private int nextTrainID = Integer.MIN_VALUE;
	public int nextTrainID() {
		if (nextTrainID == Integer.MAX_VALUE) {
			nextTrainID = Integer.MIN_VALUE;
		}
		return nextTrainID++;
	}

	private String name;
	private final List<Section> sections;
	private final List<Train> trains = new ArrayList<>();

	public Line(String name, List<Section> sections) {
		this.name = name;
		this.sections = sections;
	}

	public String getName() {
		return name;
	}

	public List<Section> getSections() {
		return sections;
	}

	public List<SubSection> getAllSubSections() {
		List<SubSection> subSections = new ArrayList<>();
		for (Section section : sections) {
			subSections.addAll(section.getSubSections());
		}
		return subSections;
	}

	public static Line deserialize(INBT nbt) {
		// TODO
	}

	public INBT serialize() {
		ListNBT sectionsNBT = new ListNBT();
		for (Section section : sections) {
			sectionsNBT.add(StringNBT.valueOf(section.getName()));
		}
		ListNBT trainsNBT = new ListNBT();
		for (Train train : trains) {
			trainsNBT.add(IntNBT.valueOf(train.getID()));
		}

		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("nextTrainID", nextTrainID);
		nbt.putString("name", name);
		nbt.put("sections", sectionsNBT);
		nbt.put("trains", trainsNBT);
		return nbt;
	}
}
