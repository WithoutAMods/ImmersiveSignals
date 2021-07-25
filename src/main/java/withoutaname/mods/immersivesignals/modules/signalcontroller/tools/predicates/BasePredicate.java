package withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.PredicateWidget;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public abstract class BasePredicate<T extends BasePredicate<T>> {
	
	@Nullable
	public static BasePredicate<?> getInstance(int id) {
		switch (id & 0xf) {
			case 0:
				return new RedstonePredicate();
			case 1:
				MultiPredicate<?> multiPredicate = new MultiPredicate<>();
				multiPredicate.setSubInstance(getInstance(id >>> 4));
				return multiPredicate;
		}
		return null;
	}
	
	@OnlyIn(Dist.CLIENT)
	public PredicateWidget createWidget(Consumer<AbstractWidget> buttonConsumer, int x, int y) {
		return new PredicateWidget(x, y, TextComponent.EMPTY);
	}
	
	public abstract boolean test(Level level, BlockPos pos);
	
	public abstract T fromBytes(FriendlyByteBuf buffer);
	
	public abstract T fromTag(Tag tag);
	
	public abstract void toBytes(FriendlyByteBuf buffer);
	
	public abstract Tag toTag();
	
	public abstract int getId();
}
