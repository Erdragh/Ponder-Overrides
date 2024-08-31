package dev.erdragh.ponder_overrides.mixin;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.foundation.ponder.ui.PonderUI;

import dev.erdragh.ponder_overrides.PonderOverrides;
import dev.erdragh.ponder_overrides.kube.PonderOverrideEventJS;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

@Mixin(PonderUI.class)
public class PonderUIMixin {
	private static void nbt_ponders$invalidOverrideMessage(ResourceLocation override) {
		var player = Minecraft.getInstance().player;
		assert player != null;
		player.displayClientMessage(new TranslatableComponent("ponderoverrides.invalid_override", override.toString()).withStyle(ChatFormatting.RED), false);
	}

	@Inject(method = "of(Lnet/minecraft/world/item/ItemStack;)Lcom/simibubi/create/foundation/ponder/ui/PonderUI;", at = @At("HEAD"), cancellable = true)
	private static void nbt_ponders$of(ItemStack item, CallbackInfoReturnable<PonderUI> cir) {
		PonderOverrides.LOGGER.debug("pondering {}", item);
		var event = new PonderOverrideEventJS(item);
		event.post();
		event.getOverride().ifPresent((override) -> {
			if (PonderRegistry.ALL.containsKey(override)) {
				cir.setReturnValue(PonderUI.of(override));
			} else nbt_ponders$invalidOverrideMessage(override);
		});
	}

	@Inject(method = "of(Lnet/minecraft/world/item/ItemStack;Lcom/simibubi/create/foundation/ponder/PonderTag;)Lcom/simibubi/create/foundation/ponder/ui/PonderUI;", at = @At("HEAD"), cancellable = true)
	private static void nbt_ponders$of(ItemStack item, PonderTag tag, CallbackInfoReturnable<PonderUI> cir) {
		PonderOverrides.LOGGER.debug("pondering {} with tag {}", item, tag);
		var event = new PonderOverrideEventJS(item);
		event.post();
		event.getOverride().ifPresent((override) -> {
			if (PonderRegistry.ALL.containsKey(override)) {
				var ui = PonderUI.of(override);
				((PonderUIAccessorMixin) ui).setReferredToByTag(tag);
				cir.setReturnValue(ui);
			} else nbt_ponders$invalidOverrideMessage(override);
		});
	}
}
