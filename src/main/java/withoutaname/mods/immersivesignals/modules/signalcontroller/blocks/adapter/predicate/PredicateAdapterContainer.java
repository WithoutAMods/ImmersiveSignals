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
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.PatternModifyPacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.PredicatePacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.SignalControllerNetworking;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

public class PredicateAdapterContainer<T extends BasePredicate<T>> extends BaseSignalPatternContainer {

	private final PlayerEntity player;
	private PredicateAdapterTile<T> tile;

	private int predicateID;
	private Runnable onPredicateIDChanged = () -> {};

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
					return tile.predicateInstance.getId();
				}
				@Override
				public void set(int value) {
					predicateID = value;
					onPredicateIDChanged.run();
				}
			});
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
			if (!world.isRemote) {
				sendPredicatePacket();
			}
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
				if (currentPredicatePatternID >= 0 && currentPredicatePatternID < tile.predicatePatterns.size()) {
					tile.predicatePatterns.remove(currentPredicatePatternID);
					if (currentPredicatePatternID >= tile.predicatePatterns.size()) {
						currentPredicatePatternID--;
					}
				}
				break;
			case 3:
				tile.predicatePatterns.add(new Pair<>((T) BasePredicate.getInstance(tile.predicateInstance.getId()), new SignalPattern()));
				currentPredicatePatternID = tile.predicatePatterns.size() - 1;
				break;
		}
		sendPredicatePacket();
		return true;
	}

	private void sendPredicatePacket() {
		if (currentPredicatePatternID >= 0 && currentPredicatePatternID < tile.predicatePatterns.size()) {
			SignalControllerNetworking.sendToClient(new PredicatePacket(tile.predicatePatterns.get(currentPredicatePatternID).getFirst()), (ServerPlayerEntity) this.player);
		}
	}

	public void onPredicateModified(BasePredicate<?> predicate) {
		tile.predicatePatterns.set(currentPredicatePatternID, new Pair<>((T) predicate, tile.predicatePatterns.get(currentPredicatePatternID).getSecond()));
	}

	@Override
	public boolean canInteractWith(@NotNull PlayerEntity playerIn) {
		return true;
	}

	public void setOnPredicateIDChanged(Runnable onPredicateIDChanged) {
		this.onPredicateIDChanged = onPredicateIDChanged;
	}

	public int getPredicateID() {
		return predicateID;
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
