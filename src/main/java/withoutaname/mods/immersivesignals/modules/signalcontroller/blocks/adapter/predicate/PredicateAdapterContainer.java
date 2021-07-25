package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate;

import javax.annotation.Nonnull;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.BaseSignalPatternContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.PatternModifyPacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.PredicatePacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.SignalControllerNetworking;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;

public class PredicateAdapterContainer<T extends BasePredicate<T>> extends BaseSignalPatternContainer {
	
	private final Player player;
	private PredicateAdapterTile<T> tile;
	
	private int predicateID;
	private Runnable onPredicateIDChanged = () -> {};
	
	protected int predicatePatternsSize;
	protected int currentPredicatePatternID = 0;
	protected SignalPattern currentPattern = new SignalPattern();
	protected Runnable onPredicatePatternsSizeChanged = () -> {};
	protected Runnable onCurrentPredicatePatternIDChanged = () -> {};
	protected Runnable onCurrentPatternChanged = () -> {};
	
	public PredicateAdapterContainer(MenuType<PredicateAdapterContainer<T>> type, int id, Level world, BlockPos pos, Player player) {
		super(type, id);
		this.player = player;
		BlockEntity te = world.getBlockEntity(pos);
		if (te instanceof PredicateAdapterTile) {
			tile = (PredicateAdapterTile<T>) te;
			addDataSlot(new DataSlot() {
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
			addDataSlot(new DataSlot() {
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
			addDataSlot(new DataSlot() {
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
			addDataSlot(new DataSlot() {
				@Override
				public int get() {
					if (tile.predicatePatterns.size() > 0) {
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
			if (!world.isClientSide) {
				sendPredicatePacket();
			}
		}
	}
	
	@Override
	public boolean clickMenuButton(@Nonnull Player playerIn, int id) {
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
			SignalControllerNetworking.sendToClient(new PredicatePacket(tile.predicatePatterns.get(currentPredicatePatternID).getFirst()), (ServerPlayer) this.player);
		}
	}
	
	public void onPredicateModified(BasePredicate<?> predicate) {
		tile.predicatePatterns.set(currentPredicatePatternID, new Pair<>((T) predicate, tile.predicatePatterns.get(currentPredicatePatternID).getSecond()));
	}
	
	@Override
	public boolean stillValid(@Nonnull Player playerIn) {
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
	public void onPatternModify(@Nonnull PatternModifyPacket packet) {
		super.onPatternModify(packet);
		tile.update();
	}
	
}
