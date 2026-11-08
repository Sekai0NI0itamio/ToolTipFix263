package itamio.tooltipfix.mixin;

import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.util.FormattedCharSequence;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Accessor for ClientTextTooltip's text, ported from ToolTipFix by
 * kyrptonaught (https://github.com/kyrptonaught/tooltipfix, MIT license).
 * All credit for the original concept, design, and logic goes to
 * kyrptonaught; this is an unofficial port by Itamio to Minecraft 26.3
 * (Fabric). See NOTICE for licensing details.
 */
@Mixin(ClientTextTooltip.class)
public interface ClientTextTooltipAccessor {

    @Accessor("text")
    FormattedCharSequence getText();

}
