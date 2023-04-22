package theUnchainedMod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import theUnchainedMod.TheUnchainedMod;

public class ArcaneLinkPower extends AbstractChainPower {
    public AbstractCreature source;

    public static final String POWER_ID = TheUnchainedMod.makeID("ArcaneLinkPower");
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;
    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public final int arcaneMastery;

    public ArcaneLinkPower(AbstractCreature owner, int amount, int arcaneMasteryAmount, AbstractCard.CardType cardType) {
        super(POWER_ID, owner, amount, new ApplyPowerAction(owner, owner, new ArcaneMasteryPower(owner, arcaneMasteryAmount)), cardType);
        this.name = NAME;
        this.type = POWER_TYPE;
        this.arcaneMastery = arcaneMasteryAmount;
        this.updateDescription();
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.arcaneMastery + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2] + this.amount + DESCRIPTIONS[3] + this.arcaneMastery + DESCRIPTIONS[1];
        }
    }
}
