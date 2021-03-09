package withoutaname.mods.immersivesignals.modules.signalcontroller.network;

import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.BaseSignalPatternContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.MultiPredicate;

import java.util.function.Supplier;

public class MultiPredicateModifiedPacket {

	private final MultiPredicate<?> multiPredicate;

	public MultiPredicateModifiedPacket(MultiPredicate<?> multiPredicate) {
		this.multiPredicate = multiPredicate;
	}

	public MultiPredicateModifiedPacket(PacketBuffer packetBuffer) {
		multiPredicate = new MultiPredicate<>();
		while (packetBuffer.readableBytes() > 3) {
			multiPredicate.addPredicate(BasePredicate.fromInt(packetBuffer.readInt()));
		}
	}

	public void toBytes(PacketBuffer packetBuffer) {
		for (BasePredicate<?> predicate : multiPredicate.getPredicates()) {
			packetBuffer.writeInt(predicate.toInt());
		}
	}

	public  boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			final Container container = ctx.get().getSender().openContainer;
			if (container instanceof PredicateAdapterContainer) {
				((PredicateAdapterContainer<?>) container).onPredicateModified(this);
			}
		});
		return true;
	}

	public MultiPredicate<?> getMultiPredicate() {
		return multiPredicate;
	}
}
