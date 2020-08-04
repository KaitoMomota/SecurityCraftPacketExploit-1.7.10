package net.geforcemods.securitycraft.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.config.GuiButtonExt;
import net.minecraft.client.Minecraft;

/**
 * This class is blatantly stolen from iChunUtils with permission.
 * 
 * and blatantly edited by bl4ckscor3 to fit SC's needs
 *
 * @author iChun
 */
public class GuiSlider extends GuiButtonExt
{
    /** The value of this slider control. */
    public double sliderValue;

    public String dispString = "";

    /** Is this slider control being dragged. */
    public boolean dragging = false;
    public boolean showDecimal = true;

    public double minValue = 0.0D;
    public double maxValue = 5.0D;
    public int precision = 1;

    public ISlider parent = null;

    public String suffix = "";

    public boolean drawString = true;
    
    private String blockName;
    
    public GuiSlider(int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr)
    {
        this("whyareyoudoingthis", "seriouslywhy", id, xPos, yPos, width, height, prefix, suf, minVal, maxVal, currentVal, showDec, drawStr, null);
    }

    public GuiSlider(String initialString, String bN, int id, int xPos, int yPos, int width, int height, String prefix, String suf, double minVal, double maxVal, double currentVal, boolean showDec, boolean drawStr, ISlider par)
    {
        super(id, xPos, yPos, width, height, prefix);
        minValue = minVal;
        maxValue = maxVal;
        dispString = prefix;
        parent = par;
        suffix = suf;
        showDecimal = showDec;
        blockName = bN;
        String val;
        sliderValue = (currentVal - minVal) / (maxVal - minVal);

        if (showDecimal)
        {
            val = Double.toString(getValue());
            precision = Math.min(val.substring(val.indexOf(".") + 1).length(), 4);
        }
        else
        {
            val = Integer.toString(getValueInt());
            precision = 0;
        }

        displayString = initialString;

        drawString = drawStr;
        if(!drawString)
        {
            displayString = "";
        }
    }

    public GuiSlider(int id, int xPos, int yPos, String displayStr, double minVal, double maxVal, double currentVal, ISlider par)
    {
        this("whyareyoudoingthis", "seriouslywhy", id, xPos, yPos, 150, 20, displayStr, "", minVal, maxVal, currentVal, true, true, par);
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    @Override
    public int getHoverState(boolean par1)
    {
        return 0;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    @Override
    protected void mouseDragged(Minecraft par1Minecraft, int par2, int par3)
    {
        if (this.visible)
        {
            if (this.dragging)
            {
                this.sliderValue = (par2 - (this.xPosition + 4)) / (double)(this.width - 8);
                updateSlider();
            }
            
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int)(this.sliderValue * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }
    
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    @Override
    public boolean mousePressed(Minecraft par1Minecraft, int par2, int par3)
    {
        if (super.mousePressed(par1Minecraft, par2, par3))
        {
            this.sliderValue = (double)(par2 - (this.xPosition + 4)) / (double)(this.width - 8);
            updateSlider();
            this.dragging = true;
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public void updateSlider()
    {
        if (this.sliderValue < 0.0F)
        {
            this.sliderValue = 0.0F;
        }

        if (this.sliderValue > 1.0F)
        {
            this.sliderValue = 1.0F;
        }

        String val;

        if (showDecimal)
        {
            val = Double.toString(getValue());

            if (val.substring(val.indexOf(".") + 1).length() > precision)
            {
                val = val.substring(0, val.indexOf(".") + precision + 1);

                if (val.endsWith("."))
                {
                    val = val.substring(0, val.indexOf(".") + precision);
                }
            }
            else
            {
                while (val.substring(val.indexOf(".") + 1).length() < precision)
                {
                    val = val + "0";
                }
            }
        }
        else
        {
            val = Integer.toString(getValueInt());
        }

        if (parent != null)
        {
            parent.onChangeSliderValue(this, blockName, id);
        }
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    @Override
    public void mouseReleased(int par1, int par2)
    {
        this.dragging = false;
    }

    public int getValueInt()
    {
        return (int)Math.round(getValue());
    }

    public double getValue()
    {
        return sliderValue * (maxValue - minValue) + minValue;
    }

    public void setValue(double d)
    {
        this.sliderValue = (d - minValue) / (maxValue - minValue);
    }

    public static interface ISlider
    {
        void onChangeSliderValue(GuiSlider slider, String blockName, int id);
    }
}
