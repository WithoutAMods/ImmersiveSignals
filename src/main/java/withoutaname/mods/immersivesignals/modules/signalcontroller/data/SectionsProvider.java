package withoutaname.mods.immersivesignals.modules.signalcontroller.data;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class SectionsProvider implements ICapabilitySerializable<CompoundNBT> {

	private final DefaultSections sections = new DefaultSections();
	private final LazyOptional<ISections> sectionsOptional = LazyOptional.of(() -> sections);

	public void invalidate() {
		sectionsOptional.invalidate();
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		return sectionsOptional.cast();
	}

	@Override
	public CompoundNBT serializeNBT() {
		if (CapabilitySections.SECTIONS_CAPABILITY == null) {
			return new CompoundNBT();
		} else {
			return (CompoundNBT) CapabilitySections.SECTIONS_CAPABILITY.writeNBT(sections, null);
		}
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		if (CapabilitySections.SECTIONS_CAPABILITY != null) {
			CapabilitySections.SECTIONS_CAPABILITY.readNBT(sections, null, nbt);
		}
	}
}
