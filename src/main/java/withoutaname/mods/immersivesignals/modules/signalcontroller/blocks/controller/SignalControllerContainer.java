package withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.controller;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import withoutaname.mods.immersivesignals.modules.signalcontroller.SignalControllerRegistration;
import withoutaname.mods.immersivesignals.modules.signalcontroller.blocks.BaseSignalPatternContainer;
import withoutaname.mods.immersivesignals.modules.signalcontroller.tools.SignalPattern;

import javax.annotation.Nonnull;

public class SignalControllerContainer extends BaseSignalPatternContainer {
	
	private SignalControllerEntity tile;
	
	private SignalPattern defaultPattern = new SignalPattern();
	private Runnable onDefaultPatternChanged = () -> {};
	private boolean override = false;
	private Runnable onOverrideChanged = () -> {};
	private SignalPattern overridePattern = new SignalPattern();
	private Runnable onOverridePatternChanged = () -> {};
	
	public SignalControllerContainer(int id, @Nonnull Level world, BlockPos pos) {
		super(SignalControllerRegistration.SIGNAL_CONTROLLER_CONTAINER.get(), id);
		BlockEntity entity = world.getBlockEntity(pos);
		if (entity instanceof SignalControllerEntity) {
			tile = (SignalControllerEntity) entity;
			
			addDataSlot(new DataSlot() {
				@Override
				public int get() {
					return tile.getDefaultPattern().toInt();
				}
				
				@Override
				public void set(int value) {
					defaultPattern = SignalPattern.fromInt(value);
					onDefaultPatternChanged.run();
				}
			});
			addDataSlot(new DataSlot() {
				@Override
				public int get() {
					return tile.isOverride() ? 1 : 0;
				}
				
				@Override
				public void set(int value) {
					override = value == 1;
					onOverrideChanged.run();
				}
			});
			addDataSlot(new DataSlot() {
				@Override
				public int get() {
					return tile.getOverridePattern().toInt();
				}
				
				@Override
				public void set(int value) {
					overridePattern = SignalPattern.fromInt(value);
					onOverridePatternChanged.run();
				}
			});
		}
	}
	
	@Override
	protected SignalPattern getModifiablePattern() {
		return tile.getOverridePattern();
	}
	
	@Override
	public boolean clickMenuButton(@Nonnull Player pPlayer, int pId) {
		if (pId == 0) {
			tile.setOverride(!tile.isOverride());
			return true;
		}
		return false;
	}
	
	@OnlyIn(Dist.CLIENT)
	public SignalPattern getDefaultPattern() {
		return defaultPattern;
	}
	
	@OnlyIn(Dist.CLIENT)
	public void setOnDefaultPatternChanged(Runnable onDefaultPatternChanged) {
		this.onDefaultPatternChanged = onDefaultPatternChanged;
	}
	
	@OnlyIn(Dist.CLIENT)
	public boolean isOverride() {
		return override;
	}
	
	@OnlyIn(Dist.CLIENT)
	public void setOnOverrideChanged(Runnable onOverrideChanged) {
		this.onOverrideChanged = onOverrideChanged;
	}
	
	@OnlyIn(Dist.CLIENT)
	public SignalPattern getOverridePattern() {
		return overridePattern;
	}
	
	@OnlyIn(Dist.CLIENT)
	public void setOnOverridePatternChanged(Runnable onOverridePatternChanged) {
		this.onOverridePatternChanged = onOverridePatternChanged;
	}
	
	@Override
	public boolean stillValid(@Nonnull Player playerIn) {
		return true;
	}
	
}
