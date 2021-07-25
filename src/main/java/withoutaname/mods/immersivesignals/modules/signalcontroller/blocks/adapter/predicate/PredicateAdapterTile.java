package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.BaseAdapterEntity;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class PredicateAdapterTile<T extends BasePredicate<T>> extends BaseAdapterEntity {
	
	public final T predicateInstance;
	
	protected List<Pair<T, SignalPattern>> predicatePatterns = new ArrayList<>();
	
	public PredicateAdapterTile(BlockEntityType<? extends PredicateAdapterTile> tileEntityType, T predicateInstance, @Nonnull BlockPos pos, @Nonnull BlockState state) {
		super(tileEntityType, pos, state);
		this.predicateInstance = predicateInstance;
	}
	
	@Override
	public void update() {
		for (Pair<T, SignalPattern> predicatePattern : predicatePatterns) {
			if (predicatePattern.getFirst().test(getLevel(), getBlockPos())) {
				setPattern(predicatePattern.getSecond());
				break;
			}
		}
	}
	
	@Override
	public void load(@Nonnull CompoundTag nbt) {
		super.load(nbt);
		Tag predicatePatternsList = nbt.get("predicatePatterns");
		if (predicatePatternsList instanceof ListTag) {
			predicatePatterns = new ArrayList<>();
			for (Tag inbt : (ListTag) predicatePatternsList) {
				if (inbt instanceof final CompoundTag compoundNBT) {
					predicatePatterns.add(new Pair<>(
							predicateInstance.fromTag(compoundNBT.get("predicate")),
							SignalPattern.fromNBT(compoundNBT.getCompound("pattern"))));
				}
			}
		}
	}
	
	@Override
	@Nonnull
	public CompoundTag save(@Nonnull CompoundTag compound) {
		final ListTag predicatePatternsList = new ListTag();
		for (Pair<T, SignalPattern> predicatePattern : predicatePatterns) {
			final CompoundTag predicatePatternNBT = new CompoundTag();
			predicatePatternNBT.put("predicate", predicatePattern.getFirst().toTag());
			predicatePatternNBT.put("pattern", predicatePattern.getSecond().toTag());
			predicatePatternsList.add(predicatePatternNBT);
		}
		compound.put("predicatePatterns", predicatePatternsList);
		return super.save(compound);
	}
	
}
