package withoutaname.mods.immersivesignals.modules.signalcontroller.network;

import java.util.function.Supplier;
import javax.annotation.Nonnull;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.BaseSignalPatternContainer;

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
	
	public PatternModifyPacket(@Nonnull PacketBuffer packetBuffer) {
		this(packetBuffer.readByte(), packetBuffer.readByte());
	}
	
	public void toBytes(@Nonnull PacketBuffer packetBuffer) {
		packetBuffer.writeByte(buttonID);
		packetBuffer.writeByte(value);
	}
	
	public boolean handle(@Nonnull Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity sender = ctx.get().getSender();
			assert sender != null;
			final Container container = sender.containerMenu;
			if (container instanceof BaseSignalPatternContainer) {
				((BaseSignalPatternContainer) container).onPatternModify(this);
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
