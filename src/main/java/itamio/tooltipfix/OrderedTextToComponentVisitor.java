package itamio.tooltipfix;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.FormattedCharSink;

/**
 * Converts a FormattedCharSequence back into a Component, ported from
 * ToolTipFix by kyrptonaught (https://github.com/kyrptonaught/tooltipfix,
 * MIT license). All credit for the original concept, design, and logic
 * goes to kyrptonaught; this is an unofficial port by Itamio to
 * Minecraft 26.3 (Fabric). See NOTICE for licensing details.
 */
public class OrderedTextToComponentVisitor implements FormattedCharSink {
    private final MutableComponent text = Component.empty();

    @Override
    public boolean accept(int index, Style style, int codePoint) {
        String car = new String(Character.toChars(codePoint));
        text.append(Component.literal(car).setStyle(style));
        return true;
    }

    public Component getText() {
        return text;
    }

    public static Component get(FormattedCharSequence text) {
        OrderedTextToComponentVisitor visitor = new OrderedTextToComponentVisitor();
        text.accept(visitor);
        return visitor.getText();
    }
}
