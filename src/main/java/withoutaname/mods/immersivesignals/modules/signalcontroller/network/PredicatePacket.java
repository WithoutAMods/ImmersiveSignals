package withoutaname.mods.immersivesignals.modules.signalcontroller.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterScreen;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;

import java.util.function.Supplier;

public class PredicatePacket {

	private final BasePredicate<?> predicate;

	public PredicatePacket(BasePredicate<?> predicate) {
		this.predicate = predicate;
	}

	public PredicatePacket(PacketBuffer packetBuffer) {
		this.predicate = BasePredicate.getInstance(packetBuffer.readInt()).fromBytes(packetBuffer);
	}

	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
				Screen currentScreen = Minecraft.getInstance().screen;
				if (currentScreen instanceof PredicateAdapterScreen) {
					PredicateAdapterScreen screen = (PredicateAdapterScreen) currentScreen;
					screen.setPredicate(predicate);
				} else {
					PredicateAdapterScreen.setNextPredicate(predicate);
				}

			} else {
				Container container = ctx.get().getSender().containerMenu;
				if (container instanceof PredicateAdapterContainer) {
					((PredicateAdapterContainer<?>) container).onPredicateModified(predicate);
				}
			}
		});
		return true;
	}

	public void toBytes(PacketBuffer packetBuffer) {
		packetBuffer.writeInt(predicate.getId());
		predicate.toBytes(packetBuffer);
	}

}
