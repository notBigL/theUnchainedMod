package theUnchainedMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.abstracts.TwoAmountPower;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.cards.Swirl;
import theUnchainedMod.patches.SwirlsGeneratedThisCombat;
import theUnchainedMod.relics.BalletShoes;
import theUnchainedMod.util.TextureLoader;

import java.util.Iterator;

public class MomentumPower extends NewTwoAmountPower {

    public static final String POWER_ID = TheUnchainedMod.makeID("MomentumPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private int standardMomentumRequired;

    private static final Texture texture48 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MomentumPower_power48.png");
    private static final Texture texture128 = TextureLoader.getTexture("theUnchainedModResources/images/powers/MomentumPower_power128.png");

    public MomentumPower(final AbstractCreature owner, final int amount) {
        name = NAME;
        ID = POWER_ID;
        this.owner = owner;
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
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount2 + DESCRIPTIONS[2];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount = checkForMomentumRequired(this.amount + stackAmount);
    }

    @Override
    public void atStartOfTurn() {
        int costOfNextSwirl = standardMomentumRequired;
        Iterator var1 = AbstractDungeon.player.hand.group.iterator();

        while (var1.hasNext()) {
            AbstractCard c = (AbstractCard) var1.next();
            if (c instanceof Swirl) {
                costOfNextSwirl++;
            }
        }
        amount2 = costOfNextSwirl;
        this.amount = checkForMomentumRequired(this.amount);
        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        if (AbstractDungeon.player.hasRelic(BalletShoes.ID)) {
            amount2 = 1;
            standardMomentumRequired = 1;
        } else {
            amount2 = 2;
            standardMomentumRequired = 2;
        }
        this.amount = checkForMomentumRequired(this.amount);
        updateDescription();
    }

    private int checkForMomentumRequired(int amount) {
        Swirl card = new Swirl();
        int amountOfSwirls = 0;
        if (this.owner.hasPower(FullSpinPower.POWER_ID)) {
            card.fullSpinApply(this.owner.getPower(FullSpinPower.POWER_ID).amount);
        }
        while (amount >= amount2) {
            amountOfSwirls++;
            amount -= amount2;
            amount2++;
        }
        for (int i = amountOfSwirls; i > 0; i--) {
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(card, 1, false));
            SwirlsGeneratedThisCombat.IncreaseSwirlsGeneratedThisCombat(1);
        }
        return amount;
    }


    //public void setMomentumRequired(int newMomentumRequired) { this.momentumRequired = newMomentumRequired;}
}
