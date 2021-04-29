package withoutaname.mods.immersivesignals.modules.signalcontroller.network;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import withoutaname.mods.immersivesignals.ImmersiveSignals;

public class SignalControllerNetworking {
	
	private static SimpleChannel INSTANCE;
	
	private static int ID = 0;
	
	private static int nextID() {
		return ID++;
	}
	
	public static void registerMessages() {
		INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(ImmersiveSignals.MODID, "immersivesignals"),
				() -> "1.1",
				"1.1"::equals,
				"1.1"::equals);
		
		INSTANCE.messageBuilder(PatternModifyPacket.class, nextID(), NetworkDirection.PLAY_TO_SERVER)
				.encoder(PatternModifyPacket::toBytes)
				.decoder(PatternModifyPacket::new)
				.consumer(PatternModifyPacket::handle)
				.add();
		INSTANCE.messageBuilder(PredicatePacket.class, nextID())
				.encoder(PredicatePacket::toBytes)
				.decoder(PredicatePacket::new)
				.consumer(PredicatePacket::handle)
				.add();
	}
	
	public static void sendToClient(Object packet, @Nonnull ServerPlayerEntity player) {
		INSTANCE.sendTo(packet, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
	}
	
	public static void sendToServer(Object packet) {
		INSTANCE.sendToServer(packet);
	}
	
}
