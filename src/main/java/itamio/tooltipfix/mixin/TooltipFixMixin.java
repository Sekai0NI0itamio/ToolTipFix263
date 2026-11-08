package itamio.tooltipfix.mixin;

import itamio.tooltipfix.Helper;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Tooltip overflow fix mixin, ported from ToolTipFix by kyrptonaught
 * (https://github.com/kyrptonaught/tooltipfix, MIT license).
 * <p>
 * <p>
 * All credit for the original concept, design, and logic goes to
 * kyrptonaught. This is an unofficial port by Itamio to Minecraft 26.3 (Fabric)
 * because the original mod has not been updated to this version. Itamio does
 * not take credit for any of the original assets or logic. See NOTICE for
 * licensing details.
 */
@Mixin(GuiGraphicsExtractor.class)
public abstract class TooltipFixMixin {

    private static final String TOOLTIP_METHOD = "tooltip(Lnet/minecraft/client/gui/Font;Ljava/util/List;IILnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;Lnet/minecraft/resources/Identifier;)V";

    @Shadow
    public abstract int guiWidth();

    @ModifyVariable(method = TOOLTIP_METHOD, at = @At(value = "HEAD"), index = 2, argsOnly = true)
    public List<ClientTooltipComponent> tooltipfix$makeListMutable(List<ClientTooltipComponent> value) {
        return new ArrayList<>(value);
    }

    @Inject(method = TOOLTIP_METHOD, at = @At(value = "HEAD"))
    public void tooltipfix$fix(Font font, List<ClientTooltipComponent> components, int x, int y, ClientTooltipPositioner positioner, Identifier id, CallbackInfo ci) {
        Helper.newFix(components, font, x, guiWidth());
    }

    @ModifyArg(method = TOOLTIP_METHOD,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/tooltip/ClientTooltipPositioner;positionTooltip(IIIIII)Lorg/joml/Vector2ic;"),
            index = 2)
    public int tooltipfix$shiftMouseX(int mouseX) {
        return Helper.shiftedMouseX(mouseX);
    }
}
