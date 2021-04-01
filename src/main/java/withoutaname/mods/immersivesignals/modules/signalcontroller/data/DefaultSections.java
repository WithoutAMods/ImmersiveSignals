package withoutaname.mods.immersivesignals.modules.signalcontroller.data;

import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.Line;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.Section;

import java.util.ArrayList;
import java.util.List;

public class DefaultSections implements ISections {

	private List<Line> lines = new ArrayList<>();

	private List<Section> sections = new ArrayList<>();

	@Override
	public List<Line> getLines() {
		return lines;
	}

	@Override
	public List<Section> getSections() {
		return sections;
	}
}
