package withoutaname.mods.immersivesignals.modules.signalcontroller.network;

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
				() -> "1.0",
				"1.0"::equals,
				"1.0"::equals);

		INSTANCE.messageBuilder(PatternModifyPacket.class, nextID(), NetworkDirection.PLAY_TO_SERVER)
				.encoder(PatternModifyPacket::toBytes)
				.decoder(PatternModifyPacket::new)
				.consumer(PatternModifyPacket::handle)
				.add();
		INSTANCE.messageBuilder(OpenMultiPredicateScreenPacket.class, nextID(), NetworkDirection.PLAY_TO_CLIENT)
				.encoder(OpenMultiPredicateScreenPacket::toBytes)
				.decoder(OpenMultiPredicateScreenPacket::new)
				.consumer(OpenMultiPredicateScreenPacket::handle)
				.add();
	}

	public static void sendToClient(Object packet, ServerPlayerEntity player) {
		INSTANCE.sendTo(packet, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);
	}

	public static void sendToServer(Object packet) {
		INSTANCE.sendToServer(packet);
	}

}
