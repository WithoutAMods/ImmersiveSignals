package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IntReferenceHolder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.BaseSignalPatternContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.OpenMultiPredicateScreenPacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.PatternModifyPacket;
import withoutaname.mods.immersivesignals.modules.signalcontroller.network.SignalControllerNetworking;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

public class PredicateAdapterContainer<T extends BasePredicate<T>> extends BaseSignalPatternContainer {

	private final PlayerEntity player;
	private PredicateAdapterTile<T> tile;

	protected int currentPredicatePatternID = 0;
	protected boolean hasMultiplePredicatePatterns = false;
	protected SignalPattern currentPattern = new SignalPattern();
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
					return (tile.predicatePatterns.size() > 0 ? 1 : 0) +
							currentPredicatePatternID << 1;
				}
				@Override
				public void set(int value) {
					hasMultiplePredicatePatterns = (value & 0b1) == 1;
					currentPredicatePatternID = value >>> 1;
				}
			});
			trackInt(new IntReferenceHolder() {
				@Override
				public int get() {
					return tile.predicatePatterns.get(currentPredicatePatternID).getSecond().toInt();
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
				SignalControllerNetworking.sendToClient(
						new OpenMultiPredicateScreenPacket(tile.predicatePatterns.get(currentPredicatePatternID).getFirst()),
						(ServerPlayerEntity) player);
				break;
		}
		return false;
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn) {
		return true;
	}

	public void setOnCurrentPatternChanged(Runnable onCurrentPatternChanged) {
		this.onCurrentPatternChanged = onCurrentPatternChanged;
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
