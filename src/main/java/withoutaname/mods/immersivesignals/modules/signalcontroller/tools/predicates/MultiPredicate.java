package withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.MultiPredicateScreen;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MultiPredicate<T extends BasePredicate<T>> extends ScreenPredicate<MultiPredicate<T>> {
	
	private final List<T> predicates = new ArrayList<>();
	private T subInstance;
	
	public MultiPredicate() {
	}
	
	public MultiPredicate(T subInstance) {
		this.subInstance = subInstance;
	}
	
	@Override
	public boolean test(Level world, BlockPos pos) {
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
	public MultiPredicate<T> fromBytes(@Nonnull FriendlyByteBuf buffer) {
		MultiPredicate<T> multiPredicate = new MultiPredicate<>();
		multiPredicate.subInstance = (T) BasePredicate.getInstance(buffer.readInt());
		int size = buffer.readInt();
		for (int i = 0; i < size; i++) {
			multiPredicate.predicates.add(multiPredicate.subInstance.fromBytes(buffer));
		}
		return multiPredicate;
	}
	
	@Override
	public MultiPredicate<T> fromTag(Tag tag) {
		final MultiPredicate<T> multiPredicate = new MultiPredicate<>();
		if (tag instanceof CompoundTag) {
			CompoundTag compound = (CompoundTag) tag;
			multiPredicate.subInstance = (T) BasePredicate.getInstance(compound.getInt("subInstance"));
			final Tag predicates = compound.get("predicates");
			if (predicates instanceof ListTag) {
				for (Tag nbt : (ListTag) predicates) {
					multiPredicate.addPredicate(subInstance.fromTag(nbt));
				}
			}
		}
		return multiPredicate;
	}
	
	@Override
	public void toBytes(@Nonnull FriendlyByteBuf buffer) {
		buffer.writeInt(subInstance.getId());
		buffer.writeInt(predicates.size());
		for (T predicate : predicates) {
			predicate.toBytes(buffer);
		}
	}
	
	public Tag toTag() {
		CompoundTag compoundNBT = new CompoundTag();
		compoundNBT.putInt("subInstance", subInstance.getId());
		final ListTag listNBT = new ListTag();
		for (T predicate : predicates) {
			listNBT.add(predicate.toTag());
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
