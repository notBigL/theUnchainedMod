package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.InvisiblePower;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.ApplyFullSpinsAction;
import theUnchainedMod.actions.RevertSwirlToBaseValuesAction;
import theUnchainedMod.util.TextureLoader;

public class ChainsFinishedThisTurnPower extends AbstractPower implements InvisiblePower
{
    public static final String POWER_ID = DefaultMod.makeID("ChainsFinishedThisTurnPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = "Chains Finished This Turn";
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/FullSpinPower_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/FullSpinPower_power128.png");

    public ChainsFinishedThisTurnPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        type = PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(texture128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(texture48, 0, 0, 48, 48);

        updateDescription();
    }

    public ChainsFinishedThisTurnPower(final AbstractCreature owner) {
        this(owner, 1);
    }

    public void updateDescription() {
        this.description = this.amount + " Chains finished this turn.";
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.amount = 0;
        }
    }
}
