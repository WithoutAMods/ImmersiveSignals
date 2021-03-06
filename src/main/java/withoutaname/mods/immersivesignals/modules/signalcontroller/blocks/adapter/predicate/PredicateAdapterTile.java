package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate;

import com.mojang.datafixers.util.Pair;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntityType;
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.BaseAdapterTile;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.MultiPredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

import java.util.ArrayList;
import java.util.List;

public class PredicateAdapterTile<T extends BasePredicate<T>> extends BaseAdapterTile {

	public final T predicateInstance;

	protected List<Pair<MultiPredicate<T>, SignalPattern>> predicatePatterns = new ArrayList<>();

	public PredicateAdapterTile(TileEntityType<? extends PredicateAdapterTile> tileEntityType, T predicateInstance) {
		super(tileEntityType);
		this.predicateInstance = predicateInstance;
	}

	@Override
	public void update() {
		for (Pair<MultiPredicate<T>, SignalPattern> predicatePattern : predicatePatterns) {
			if (predicatePattern.getFirst().test(this)) {
				setPattern(predicatePattern.getSecond());
				break;
			}
		}
	}

	@Override
	public void read(@NotNull BlockState state, @NotNull CompoundNBT nbt) {
		super.read(state, nbt);
		INBT predicatePatternsList = nbt.get("predicatePatterns");
		if (predicatePatternsList instanceof ListNBT) {
			predicatePatterns = new ArrayList<>();
			for (INBT inbt : (ListNBT) predicatePatternsList) {
				if (inbt instanceof CompoundNBT) {
					final CompoundNBT compoundNBT = (CompoundNBT) inbt;
					predicatePatterns.add(new Pair<>(
							MultiPredicate.fromNBT(predicateInstance, compoundNBT.get("predicate")),
							SignalPattern.fromNBT(compoundNBT.getCompound("pattern"))));
				}
			}
		}
	}

	@Override
	@NotNull
	public CompoundNBT write(@NotNull CompoundNBT compound) {
		final ListNBT predicatePatternsList = new ListNBT();
		for (Pair<MultiPredicate<T>, SignalPattern> predicatePattern : predicatePatterns) {
			final CompoundNBT predicatePatternNBT = new CompoundNBT();
			predicatePatternNBT.put("predicate", predicatePattern.getFirst().toNBT());
			predicatePatternNBT.put("pattern", predicatePattern.getSecond().toNBT());
			predicatePatternsList.add(predicatePatternNBT);
		}
		compound.put("predicatePatterns", predicatePatternsList);
		return super.write(compound);
	}

}
