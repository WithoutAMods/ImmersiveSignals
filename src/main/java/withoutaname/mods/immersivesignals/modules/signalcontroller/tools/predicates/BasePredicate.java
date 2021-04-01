package withoutaname.mods.immersivesignals.modules.signalcontroller.tools.predicates;

import net.minecraft.client.gui.widget.Widget;
import net.minecraft.nbt.INBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import withoutaname.mods.immersivesignals.modules.signalcontroller.gui.PredicateWidget;

import java.util.function.Consumer;

public abstract class BasePredicate<T extends BasePredicate<T>> {

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
	public PredicateWidget createWidget(Consumer<Widget> buttonConsumer, int x, int y) {
		return new PredicateWidget(x, y, StringTextComponent.EMPTY);
	}

	public abstract boolean test(World world, BlockPos pos);

	public abstract T fromBytes(PacketBuffer buffer);

	public abstract T fromNBT(INBT inbt);

	public abstract void toBytes(PacketBuffer buffer);

	public abstract INBT toNBT();

	public abstract int getId();
}
