package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.util.TextureLoader;

public class GlimpseIntoFuturePower extends AbstractPower {

    public static final String POWER_ID = DefaultMod.makeID("GlimpseIntoFuturePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/GlimpseIntoFuturePower_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/GlimpseIntoFuturePower_power128.png");

    private boolean energyDepleted;

    public GlimpseIntoFuturePower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;
        energyDepleted = false;

        this.region128 = new TextureAtlas.AtlasRegion(texture128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(texture48, 0, 0, 48, 48);

        updateDescription();
    }


    public void updateDescription() {
        if (energyDepleted) {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            if (EnergyPanel.getCurrentEnergy() >= 1) {
                energyDepleted = true;
                updateDescription();
            }
        }
    }

    @Override
    public void atStartOfTurn() {
        if (energyDepleted) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
    }
}
