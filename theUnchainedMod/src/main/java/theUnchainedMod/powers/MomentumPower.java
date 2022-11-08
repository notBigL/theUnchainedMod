package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.cards.Swirl;
import theUnchainedMod.util.TextureLoader;

public class MomentumPower extends AbstractPower {

    public static final String POWER_ID = DefaultMod.makeID("MomentumPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int momentumRequired;

    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MomentumPower_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MomentumPower_power128.png");

    public MomentumPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
        momentumRequired = 3;
        this.amount = amount;
        type = AbstractPower.PowerType.BUFF;
        isTurnBased = false;

        this.region128 = new TextureAtlas.AtlasRegion(texture128, 0, 0, 128, 128);
        this.region48 = new TextureAtlas.AtlasRegion(texture48, 0, 0, 48, 48);

        updateDescription();
    }

    public MomentumPower(final AbstractCreature owner) {
        this(owner, 1);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.momentumRequired + DESCRIPTIONS[1];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount = checkForMomentumRequired(this.amount + stackAmount);
    }

    @Override
    public void onInitialApplication() {
        this.amount = checkForMomentumRequired(this.amount);
    }

    private int checkForMomentumRequired(int amount) {
        Swirl card = new Swirl();
        int amountOfSwirls = 0;
        if (this.owner.hasPower(FullSpinPower.POWER_ID)) {
            card.fullSpinApply(this.owner.getPower(FullSpinPower.POWER_ID).amount);
        }
        while (amount >= momentumRequired) {
            amountOfSwirls++;
            amount -= momentumRequired;
        }
        for (int i = amountOfSwirls; i > 0; i--) {
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1, false));
        }
        if (amount <= 0) {
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner, owner, this));
        }
        return amount;
    }


    //public void setMomentumRequired(int newMomentumRequired) { this.momentumRequired = newMomentumRequired;}
}
