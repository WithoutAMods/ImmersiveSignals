package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.sections;

import net.minecraft.tileentity.TileEntity;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SubSection;

public class SubSectionMarkerTile extends TileEntity {

	private MarkerType markerType = MarkerType.END;

	private SubSection subSection;

	public SubSectionMarkerTile() {
		super(SignalControllerRegistration.SUB_SECTION_MARKER_TILE.get());
	}

	public enum MarkerType { // More may be added (START / PRE_SIGNAL)
		END("end");

		private final String name;

		MarkerType(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return name;
		}
	}
}
