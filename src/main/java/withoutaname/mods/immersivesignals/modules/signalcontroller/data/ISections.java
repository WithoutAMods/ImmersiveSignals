package withoutaname.mods.immersivesignals.modules.signalcontroller.data;

import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.Line;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.Section;

import java.util.List;

public interface ISections {

	List<Line> getLines();

	List<Section> getSections();
}
