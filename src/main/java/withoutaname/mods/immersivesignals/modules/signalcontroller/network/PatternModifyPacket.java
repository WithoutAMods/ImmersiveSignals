package withoutaname.mods.immersivesignals.modules.signalcontroller.network;

import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class PatternModifyPacket {

	private final byte buttonID;
	private final byte value;

	public PatternModifyPacket(int buttonID) {
		this((byte) buttonID);
	}

	public PatternModifyPacket(byte buttonID) {
		this(buttonID, (byte) 0);
	}

	public PatternModifyPacket(int buttonID, int value) {
		this((byte) buttonID, (byte) value);
	}

	public PatternModifyPacket(byte buttonID, byte value) {
		this.buttonID = buttonID;
		this.value = value;
	}

	public PatternModifyPacket(PacketBuffer packetBuffer) {
		this(packetBuffer.readByte(), packetBuffer.readByte());
	}

	public void toBytes(PacketBuffer packetBuffer) {
		packetBuffer.writeByte(buttonID);
		packetBuffer.writeByte(value);
	}

	public boolean handle(Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			final Container container = ctx.get().getSender().openContainer;
			if (container instanceof IPatternModifyHandler) {
				((IPatternModifyHandler) container).onPatternModify(this);
			}
		});
		return true;
	}

	public byte getButtonID() {
		return buttonID;
	}

	public byte getValue() {
		return value;
	}

}
