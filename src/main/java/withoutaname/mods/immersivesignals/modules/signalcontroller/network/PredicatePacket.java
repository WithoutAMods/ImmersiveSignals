package withoutaname.mods.immersivesignals.modules.signalcontroller.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.adapter.predicate.PredicateAdapterScreen;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates.BasePredicate;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class PredicatePacket {
	
	private final BasePredicate<?> predicate;
	
	public PredicatePacket(BasePredicate<?> predicate) {
		this.predicate = predicate;
	}
	
	public PredicatePacket(@Nonnull FriendlyByteBuf packetBuffer) {
		BasePredicate<?> instance = BasePredicate.getInstance(packetBuffer.readInt());
		assert instance != null;
		this.predicate = instance.fromBytes(packetBuffer);
	}
	
	public boolean handle(@Nonnull Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
				Screen currentScreen = Minecraft.getInstance().screen;
				if (currentScreen instanceof PredicateAdapterScreen screen) {
					screen.setPredicate(predicate);
				} else {
					PredicateAdapterScreen.setNextPredicate(predicate);
				}
				
			} else {
				ServerPlayer sender = ctx.get().getSender();
				assert sender != null;
				AbstractContainerMenu container = sender.containerMenu;
				if (container instanceof PredicateAdapterContainer) {
					((PredicateAdapterContainer<?>) container).onPredicateModified(predicate);
				}
			}
		});
		return true;
	}
	
	public void toBytes(@Nonnull FriendlyByteBuf packetBuffer) {
		packetBuffer.writeInt(predicate.getId());
		predicate.toBytes(packetBuffer);
	}
	
}
