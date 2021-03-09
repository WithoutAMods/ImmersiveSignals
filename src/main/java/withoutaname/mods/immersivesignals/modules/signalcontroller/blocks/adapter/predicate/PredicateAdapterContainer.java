package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate;

import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.BaseSignalPatternContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.MultiPredicateModifiedPacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.OpenMultiPredicateScreenPacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.PatternModifyPacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.SignalControllerNetworking;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.MultiPredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

public class PredicateAdapterContainer<T extends BasePredicate<T>> extends BaseSignalPatternContainer {

	private final PlayerEntity player;
	private PredicateAdapterTile<T> tile;

	protected int predicatePatternsSize;
	protected int currentPredicatePatternID = 0;
	protected SignalPattern currentPattern = new SignalPattern();
	protected Runnable onPredicatePatternsSizeChanged = () -> {};
	protected Runnable onCurrentPredicatePatternIDChanged = () -> {};
	protected Runnable onCurrentPatternChanged = () -> {};

	public PredicateAdapterContainer(ContainerType<PredicateAdapterContainer<T>> type, int id, World world, BlockPos pos, PlayerEntity player) {
		super(type, id);
		this.player = player;
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof PredicateAdapterTile) {
			tile = (PredicateAdapterTile<T>) te;
			trackInt(new IntReferenceHolder() {
				@Override
				public int get() {
					return tile.predicatePatterns.size();
				}
				@Override
				public void set(int value) {
					predicatePatternsSize = value;
					onPredicatePatternsSizeChanged.run();
				}
			});
			trackInt(new IntReferenceHolder() {
				@Override
				public int get() {
					return currentPredicatePatternID;
				}
				@Override
				public void set(int value) {
					currentPredicatePatternID = value;
					onCurrentPredicatePatternIDChanged.run();
				}
			});
			trackInt(new IntReferenceHolder() {
				@Override
				public int get() {
					if (tile.predicatePatterns.size() > 0){
						if (tile.predicatePatterns.size() <= currentPredicatePatternID) {
							currentPredicatePatternID = tile.predicatePatterns.size() - 1;
						}
						return tile.predicatePatterns.get(currentPredicatePatternID).getSecond().toInt();
					} else {
						currentPredicatePatternID = -1;
						return 0;
					}
				}
				@Override
				public void set(int value) {
					currentPattern = SignalPattern.fromInt(value);
					onCurrentPatternChanged.run();
				}
			});
		}
	}

	@Override
	public boolean enchantItem(PlayerEntity playerIn, int id) {
		switch (id) {
			case 0:
				if (currentPredicatePatternID > 0) {
					currentPredicatePatternID--;
				}
				break;
			case 1:
				if (currentPredicatePatternID < tile.predicatePatterns.size() - 1) {
					currentPredicatePatternID++;
				}
				break;
			case 2:
				SignalControllerNetworking.sendToClient(
						new OpenMultiPredicateScreenPacket(tile.predicateInstance.toInt(), tile.predicatePatterns.get(currentPredicatePatternID).getFirst()),
						(ServerPlayerEntity) player);
				break;
			case 3:
				if (currentPredicatePatternID >= 0 && currentPredicatePatternID < tile.predicatePatterns.size()) {
					tile.predicatePatterns.remove(currentPredicatePatternID);
					if (currentPredicatePatternID >= tile.predicatePatterns.size()) {
						currentPredicatePatternID--;
					}
				}
				break;
			case 4:
				tile.predicatePatterns.add(new Pair<>(new MultiPredicate<>(), new SignalPattern()));
				currentPredicatePatternID = tile.predicatePatterns.size() - 1;
				break;
		}
		return false;
	}

	public void onPredicateModified(MultiPredicateModifiedPacket multiPredicateModifiedPacket) {
		final MultiPredicate<?> multiPredicate = multiPredicateModifiedPacket.getMultiPredicate();
		tile.predicatePatterns.set(currentPredicatePatternID, new Pair<>((MultiPredicate<T>) multiPredicate, tile.predicatePatterns.get(currentPredicatePatternID).getSecond()));
	}

	@Override
	public boolean canInteractWith(@NotNull PlayerEntity playerIn) {
		return true;
	}

	@OnlyIn(Dist.CLIENT)
	public void setOnPredicatePatternsSizeChanged(Runnable onPredicatePatternsSizeChanged) {
		this.onPredicatePatternsSizeChanged = onPredicatePatternsSizeChanged;
	}

	@OnlyIn(Dist.CLIENT)
	public void setOnCurrentPredicatePatternIDChanged(Runnable onCurrentPredicatePatternIDChanged) {
		this.onCurrentPredicatePatternIDChanged = onCurrentPredicatePatternIDChanged;
	}

	@OnlyIn(Dist.CLIENT)
	public void setOnCurrentPatternChanged(Runnable onCurrentPatternChanged) {
		this.onCurrentPatternChanged = onCurrentPatternChanged;
	}

	@OnlyIn(Dist.CLIENT)
	public int getPredicatePatternsSize() {
		return predicatePatternsSize;
	}

	@OnlyIn(Dist.CLIENT)
	public int getCurrentPredicatePatternID() {
		return currentPredicatePatternID;
	}

	@OnlyIn(Dist.CLIENT)
	public SignalPattern getCurrentPattern() {
		return currentPattern;
	}

	@Override
	protected SignalPattern getModifiablePattern() {
		return tile.predicatePatterns.get(currentPredicatePatternID).getSecond();
	}

	@Override
	public void onPatternModify(PatternModifyPacket packet) {
		super.onPatternModify(packet);
		tile.update();
	}
}
