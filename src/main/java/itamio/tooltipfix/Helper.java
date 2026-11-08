package itamio.tooltipfix;

import itamio.tooltipfix.mixin.ClientTextTooltipAccessor;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTextTooltip;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

/**
 * Tooltip overflow fix logic, ported from ToolTipFix by kyrptonaught
 * (https://github.com/kyrptonaught/tooltipfix, MIT license).
 * <p>
 * <p>
 * All credit for the original concept, design, and logic goes to
 * kyrptonaught. This is an unofficial port by Itamio to Minecraft 1.20.1 (Fabric)
 * because the original mod has not been updated to this version. Itamio does
 * not take credit for any of the original assets or logic. See NOTICE for
 * licensing details.
 */
public class Helper {

    // Set by newFix(); consumed by shiftedMouseX() when the tooltip is
    // flipped to the left of the cursor. Client rendering happens on a
    // single thread, so static state is safe here.
    private static boolean shouldFlip = false;
    private static int flippedMaxWidth = 0;

    public static void newFix(List<ClientTooltipComponent> components, Font font, int x, int width) {
        shouldFlip = false;

        int forcedWidth = 0;
        for (ClientTooltipComponent component : components) {
            if (!(component instanceof ClientTextTooltip)) {
                int width2 = component.getWidth(font);
                if (width2 > forcedWidth)
                    forcedWidth = width2;
            }
        }

        int maxWidth = width - 20 - x;
        if (forcedWidth > maxWidth || maxWidth < 100) {
            shouldFlip = true;
            maxWidth = x - 28;
        }
        if (maxWidth < 1)
            maxWidth = 1;

        wrapNewLines(components);
        wrapLongLines(components, font, maxWidth);

        flippedMaxWidth = 0;
        for (ClientTooltipComponent component : components) {
            int newWidth = component.getWidth(font);
            if (newWidth > flippedMaxWidth)
                flippedMaxWidth = newWidth;
        }
    }

    /**
     * Shifts the mouse X passed to the tooltip positioner leftwards when the
     * tooltip was flipped, mirroring the original mod's renderX shift
     * (x + 12 - 28 - maxWidth in the pre-26.3 DrawContext implementation).
     */
    public static int shiftedMouseX(int mouseX) {
        if (!shouldFlip)
            return mouseX;
        return mouseX - 28 - flippedMaxWidth;
    }

    private static void wrapLongLines(List<ClientTooltipComponent> components, Font font, int maxSize) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof ClientTextTooltip) {
                Component text = OrderedTextToComponentVisitor.get(((ClientTextTooltipAccessor) components.get(i)).getText());
                if (text.getSiblings().isEmpty()) continue;

                List<ClientTooltipComponent> wrapped = font.split(text, maxSize).stream().map(ClientTooltipComponent::create).toList();
                components.remove(i);
                components.addAll(i, wrapped);
            }
        }
    }

    private static void wrapNewLines(List<ClientTooltipComponent> components) {
        for (int i = 0; i < components.size(); i++) {
            if (components.get(i) instanceof ClientTextTooltip) {
                Component text = OrderedTextToComponentVisitor.get(((ClientTextTooltipAccessor) components.get(i)).getText());

                List<Component> children = text.getSiblings();
                for (int j = 0; j < children.size() - 1; j++) {
                    String code = children.get(j).getString() + children.get(j + 1).getString();
                    if (code.equals("\n")) {
                        components.set(i, ClientTooltipComponent.create(textWithChildren(children, 0, j).getVisualOrderText()));
                        components.add(i + 1, ClientTooltipComponent.create(textWithChildren(children, j + 2, children.size()).getVisualOrderText()));
                        break;
                    }
                }
            }
        }
    }

    private static MutableComponent textWithChildren(List<Component> children, int from, int end) {
        MutableComponent text = Component.empty();
        for (int i = from; i < end; i++)
            text.append(children.get(i));
        return text;
    }
}
