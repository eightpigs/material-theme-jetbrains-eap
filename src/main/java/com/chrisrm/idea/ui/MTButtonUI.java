//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.chrisrm.idea.ui;

import com.intellij.icons.AllIcons;
import com.intellij.ide.ui.laf.darcula.DarculaLaf;
import com.intellij.ide.ui.laf.darcula.ui.DarculaButtonUI;
import com.intellij.openapi.ui.GraphicsConfig;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.util.ObjectUtils;
import com.intellij.util.ui.GraphicsUtil;
import com.intellij.util.ui.JBUI;
import com.intellij.util.ui.UIUtil;
import sun.swing.SwingUtilities2;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.UIResource;
import java.awt.*;

public class MTButtonUI extends DarculaButtonUI {
    @SuppressWarnings("MethodOverridesStaticMethodOfSuperclass")
    public static ComponentUI createUI(JComponent c) {
        return new MTButtonUI();
    }

    public static boolean isSquare(Component c) {
        return c instanceof JButton && "square".equals(((JButton)c).getClientProperty("JButton.buttonType"));
    }

    public static boolean isDefaultButton(JComponent c) {
        return c instanceof JButton && ((JButton)c).isDefaultButton();
    }

    public static boolean isHelpButton(JComponent button) {
        return (SystemInfo.isMac || UIUtil.isUnderDarcula() || UIUtil.isUnderWin10LookAndFeel())
                && button instanceof JButton
                && "help".equals(button.getClientProperty("JButton.buttonType"));
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        if (paintDecorations((Graphics2D) g, c)) {
            super.paint(g, c);
        }
    }

    @Override
    public void update(Graphics g, JComponent c) {
        super.update(g, c);
        if (isDefaultButton(c)) {
            setupDefaultButton((JButton) c);
        }
    }

    /**
     * Paints additional buttons decorations
     *
     * @param g Graphics
     * @param c button component
     * @return <code>true</code> if it is allowed to continue painting,
     * <code>false</code> if painting should be stopped
     */
    protected boolean paintDecorations(Graphics2D g, JComponent c) {
        int w = c.getWidth();
        int h = c.getHeight();
        if (isHelpButton(c)) {
            g.setPaint(UIUtil.getGradientPaint(0, 0, getButtonColor1(), 0, h, getButtonColor2()));
            int off = JBUI.scale(22);
            int x = (w - off) / 2;
            int y = (h - off) / 2;
            g.fillOval(x, y, off, off);
            AllIcons.Actions.Help.paintIcon(c, g, x + JBUI.scale(3), y + JBUI.scale(3));
            return false;
        } else {
            final Border border = c.getBorder();
            final GraphicsConfig config = GraphicsUtil.setupAAPainting(g);
            final boolean square = isSquare(c);
            if (c.isEnabled() && border != null) {
                final Insets ins = border.getBorderInsets(c);
                final int yOff = (ins.top + ins.bottom) / 4;
                if (!square) {
                    if (isDefaultButton(c)) {
                        g.setPaint(UIUtil.getGradientPaint(0, 0, getSelectedButtonColor1(), 0, h, getSelectedButtonColor2()));
                    } else {
                        g.setPaint(UIUtil.getGradientPaint(0, 0, getButtonColor1(), 0, h, getButtonColor2()));
                    }
                }
                int rad = JBUI.scale(square ? 3 : 5);
                g.fillRoundRect(JBUI.scale(square ? 2 : 4), yOff, w - 2 * JBUI.scale(4), h - 2 * yOff, rad, rad);
            }
            config.restore();
            return true;
        }
    }

    protected void paintText(Graphics g, JComponent c, Rectangle textRect, String text) {
        if (isHelpButton(c)) {
            return;
        }

        AbstractButton button = (AbstractButton) c;
        ButtonModel model = button.getModel();
        Color fg = button.getForeground();
        if (fg instanceof UIResource && isDefaultButton(button)) {
            final Color selectedFg = UIManager.getColor("Button.darcula.selectedButtonForeground");
            if (selectedFg != null) {
                fg = selectedFg;
            }
        }
        g.setColor(fg);

        //UISettings.setupAntialiasing(g);

        FontMetrics metrics = SwingUtilities2.getFontMetrics(c, g);
        int mnemonicIndex = DarculaLaf.isAltPressed() ? button.getDisplayedMnemonicIndex() : -1;
        if (model.isEnabled()) {

            SwingUtilities2.drawStringUnderlineCharAt(c, g, text, mnemonicIndex,
                    textRect.x + getTextShiftOffset(),
                    textRect.y + metrics.getAscent() + getTextShiftOffset());
        } else {
            paintDisabledText(g, text, c, textRect, metrics);
        }
    }

    protected void paintDisabledText(Graphics g, String text, JComponent c, Rectangle textRect, FontMetrics metrics) {
        g.setColor(UIManager.getColor("Button.darcula.disabledText.shadow"));
        SwingUtilities2.drawStringUnderlineCharAt(c, g, text, -1,
                textRect.x + getTextShiftOffset() + 1,
                textRect.y + metrics.getAscent() + getTextShiftOffset() + 1);
        g.setColor(UIManager.getColor("Button.disabledText"));
        SwingUtilities2.drawStringUnderlineCharAt(c, g, text, -1,
                textRect.x + getTextShiftOffset(),
                textRect.y + metrics.getAscent() + getTextShiftOffset());
    }

    @Override
    protected void paintIcon(Graphics g, JComponent c, Rectangle iconRect) {
        Border border = c.getBorder();
        if (border != null && isSquare(c)) {
            int xOff = 1;
            Insets ins = border.getBorderInsets(c);
            int yOff = (ins.top + ins.bottom) / 4;
            Rectangle iconRect2 = new Rectangle(iconRect);
            iconRect2.x += xOff;
            iconRect2.y += yOff;
            super.paintIcon(g, c, iconRect2);
        } else {
            super.paintIcon(g, c, iconRect);
        }
    }

    protected void setupDefaultButton(JButton button) {
        if (!SystemInfo.isMac) {
            if (!button.getFont().isBold()) {
                button.setFont(new FontUIResource(button.getFont().deriveFont(Font.BOLD)));
            }
        }
    }

    protected Color getButtonColor1() {
        return ObjectUtils.notNull(UIManager.getColor("Button.darcula.color1"), new ColorUIResource(0x555a5c));
    }

    protected Color getButtonColor2() {
        return ObjectUtils.notNull(UIManager.getColor("Button.darcula.color2"), new ColorUIResource(0x414648));
    }

    protected Color getSelectedButtonColor1() {
        return ObjectUtils.notNull(UIManager.getColor("Button.darcula.selection.color1"), new ColorUIResource(0x384f6b));
    }

    protected Color getSelectedButtonColor2() {
        return ObjectUtils.notNull(UIManager.getColor("Button.darcula.selection.color2"), new ColorUIResource(0x233143));
    }
}