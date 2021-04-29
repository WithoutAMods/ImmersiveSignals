package withoutaname.mods.immersivesignals.modules.signalcontroller.network;

import java.util.function.Supplier;
import javax.annotation.Nonnull;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterScreen;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;

public class PredicatePacket {
	
	private final BasePredicate<?> predicate;
	
	public PredicatePacket(BasePredicate<?> predicate) {
		this.predicate = predicate;
	}
	
	public PredicatePacket(@Nonnull PacketBuffer packetBuffer) {
		BasePredicate<?> instance = BasePredicate.getInstance(packetBuffer.readInt());
		assert instance != null;
		this.predicate = instance.fromBytes(packetBuffer);
	}
	
	public boolean handle(@Nonnull Supplier<NetworkEvent.Context> ctx) {
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
				ServerPlayerEntity sender = ctx.get().getSender();
				assert sender != null;
				Container container = sender.containerMenu;
				if (container instanceof PredicateAdapterContainer) {
					((PredicateAdapterContainer<?>) container).onPredicateModified(predicate);
				}
			}
		});
		return true;
	}
	
	public void toBytes(@Nonnull PacketBuffer packetBuffer) {
		packetBuffer.writeInt(predicate.getId());
		predicate.toBytes(packetBuffer);
	}
	
}
