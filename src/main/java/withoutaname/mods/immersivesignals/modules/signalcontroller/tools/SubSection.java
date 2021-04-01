package withoutaname.mods.immersivesignals.modules.signalcontroller.tools;

import net.minecraft.nbt.*;

import java.util.ArrayList;
import java.util.List;

public class SubSection {

	private final List<Train> reserves = new ArrayList<>();

	public boolean isReserved() {
		return reserves.size() > 0;
	}

	public List<Train> getReserves() {
		return reserves;
	}

	public void addReserve(Train reserve) {
		reserves.add(reserve);
	}

	public SubSection deserialize(INBT nbt, List<Line> lines) {
		// TODO
		return null;
	}

	public INBT serialize() {// TODO
		ListNBT reservesNBT = new ListNBT();
		CompoundNBT reserveNBT;
		for (Train reserve : reserves) {
			reserveNBT = new CompoundNBT();
			reserveNBT.putString("line", reserve.getLine().getName());
			reserveNBT.putInt("id", reserve.getID());
			reservesNBT.add(reserveNBT);
		}
		return reservesNBT;
	}
}
