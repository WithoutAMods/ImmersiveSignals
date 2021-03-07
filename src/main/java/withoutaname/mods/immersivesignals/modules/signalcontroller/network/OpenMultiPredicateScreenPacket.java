package withoutaname.mods.immersivesignals.modules.signalcontroller.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.MultiPredicateScreen;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.BasePredicate;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.MultiPredicate;

import java.util.function.Supplier;

public class OpenMultiPredicateScreenPacket {

	private final MultiPredicate<?> multiPredicate;

	public OpenMultiPredicateScreenPacket(MultiPredicate<?> multiPredicate) {
		this.multiPredicate = multiPredicate;
	}

	public OpenMultiPredicateScreenPacket(PacketBuffer packetBuffer) {
		multiPredicate = new MultiPredicate<>();
		while (packetBuffer.readableBytes() > 3) {
			multiPredicate.addPredicate(BasePredicate.fromInt(packetBuffer.readInt()));
		}
	}

	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			Minecraft.getInstance().displayGuiScreen(new MultiPredicateScreen(Minecraft.getInstance().currentScreen, multiPredicate));
		});
		return true;
	}

	public void toBytes(PacketBuffer packetBuffer) {
		for (BasePredicate<?> predicate : multiPredicate.getPredicates()) {
			packetBuffer.writeInt(predicate.toInt());
		}
	}

}
