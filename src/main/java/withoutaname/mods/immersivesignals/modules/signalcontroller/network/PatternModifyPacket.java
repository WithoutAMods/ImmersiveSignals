package withoutaname.mods.immersivesignals.modules.signalcontroller.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.BaseSignalPatternContainer;

import javax.annotation.Nonnull;
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
	
	public PatternModifyPacket(@Nonnull FriendlyByteBuf packetBuffer) {
		this(packetBuffer.readByte(), packetBuffer.readByte());
	}
	
	public void toBytes(@Nonnull FriendlyByteBuf packetBuffer) {
		packetBuffer.writeByte(buttonID);
		packetBuffer.writeByte(value);
	}
	
	public boolean handle(@Nonnull Supplier<NetworkEvent.Context> ctx) {
		ctx.get().enqueueWork(() -> {
			ServerPlayer sender = ctx.get().getSender();
			assert sender != null;
			final AbstractContainerMenu container = sender.containerMenu;
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
