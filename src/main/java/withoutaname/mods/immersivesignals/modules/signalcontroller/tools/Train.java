package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

public class Train {

	private final Line line;
	private final int id;

	public Train(Line line, int id) {
		this.line = line;
		this.id = id;
		for (SubSection subSection : line.getAllSubSections()) {
			subSection.addReserve(this);
		}
	}

	public int getID() {
		return id;
	}

	public Line getLine() {
		return line;
	}
}
