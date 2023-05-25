package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.powers.AbstractPower;

public abstract class NewTwoAmountPower extends AbstractPower {
    public int amount2 = 0;
    public boolean canGoNegative2 = false;
    protected Color redColor2;
    protected Color greenColor2;

    public NewTwoAmountPower() {
        this.redColor2 = Color.RED.cpy();
        this.greenColor2 = Color.GREEN.cpy();
    }

    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {
        renderAmount1(sb, x, y, c);

        if (this.amount2 >= 0) {
            if (!this.isTurnBased) {
                this.greenColor2.a = c.a;
                c = this.greenColor2;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
        } else if (this.amount2 < 0 && this.canGoNegative2) {
            this.redColor2.a = c.a;
            c = this.redColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount2), x, y + 15.0F * Settings.scale, this.fontScale, c);
        }

    }
    // override the abstract power render amount so we can display a 0
    public void renderAmount1(SpriteBatch sb, float x, float y, Color c) {
        if (this.amount >= 0) {
            if (!this.isTurnBased) {
                this.greenColor2.a = c.a;
                c = this.greenColor2;
            }

            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount), x, y, this.fontScale, c);
        } else if (this.amount < 0 && this.canGoNegative) {
            this.redColor2.a = c.a;
            c = this.redColor2;
            FontHelper.renderFontRightTopAligned(sb, FontHelper.powerAmountFont, Integer.toString(this.amount), x, y, this.fontScale, c);
        }

    }
}
