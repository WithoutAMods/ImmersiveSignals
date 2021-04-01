package withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.MultiPredicateScreen;

import java.util.ArrayList;
import java.util.List;

public class MultiPredicate<T extends BasePredicate<T>> extends ScreenPredicate<MultiPredicate<T>> {

	private T subInstance;
	private final List<T> predicates = new ArrayList<>();

	public MultiPredicate() {
	}

	public MultiPredicate(T subInstance) {
		this.subInstance = subInstance;
	}

	@Override
	public boolean test(World world, BlockPos pos) {
		for (T predicate : predicates) {
			if (predicate.test(world, pos)) {
				return true;
			}
		}
		return false;
	}

	public void addPredicate(BasePredicate<?> predicate) {
		try {
			predicates.add((T) predicate);
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
	}

	public T getSubInstance() {
		return subInstance;
	}

	public void setSubInstance(BasePredicate<?> instance) {
		subInstance = (T) instance;
	}

	public List<T> getPredicates() {
		return predicates;
	}

	@Override
	public int getId() {
		return 1 + (subInstance.getId() << 4);
	}

	@Override
	public MultiPredicate<T> fromBytes(PacketBuffer buffer) {
		MultiPredicate<T> multiPredicate = new MultiPredicate<>();
		multiPredicate.subInstance = (T) BasePredicate.getInstance(buffer.readInt());
		int size = buffer.readInt();
		for (int i = 0; i < size; i++) {
			multiPredicate.predicates.add(multiPredicate.subInstance.fromBytes(buffer));
		}
		return multiPredicate;
	}

	@Override
	public MultiPredicate<T> fromNBT(INBT inbt) {
		final MultiPredicate<T> multiPredicate = new MultiPredicate<>();
		if (inbt instanceof CompoundNBT) {
			CompoundNBT compound = (CompoundNBT) inbt;
			multiPredicate.subInstance = (T) BasePredicate.getInstance(compound.getInt("subInstance"));
			final INBT predicates = compound.get("predicates");
			if (predicates instanceof ListNBT) {
				for (INBT nbt : (ListNBT) predicates) {
					multiPredicate.addPredicate(subInstance.fromNBT(nbt));
				}
			}
		}
		return multiPredicate;
	}

	@Override
	public void toBytes(PacketBuffer buffer) {
		buffer.writeInt(subInstance.getId());
		buffer.writeInt(predicates.size());
		for (T predicate : predicates) {
			predicate.toBytes(buffer);
		}
	}

	public INBT toNBT() {
		CompoundNBT compoundNBT = new CompoundNBT();
		compoundNBT.putInt("subInstance", subInstance.getId());
		final ListNBT listNBT = new ListNBT();
		for (T predicate : predicates) {
			listNBT.add(predicate.toNBT());
		}
		compoundNBT.put("predicates", listNBT);
		return compoundNBT;
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void openScreen() {
		MultiPredicateScreen.open(this);
	}
}
