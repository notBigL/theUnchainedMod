package theUnchainedMod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;

public class TelekineticPulsePower extends AbstractChainPower {
    public AbstractCreature source;

    public static final String POWER_ID = TheUnchainedMod.makeID("TelekineticPulsePower");
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;
    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static AbstractPower.PowerType POWER_TYPE = PowerType.BUFF;
    private final int damageAmount;
    private final int blockAmount;

    public TelekineticPulsePower(AbstractCreature owner, int amount, AbstractGameAction finishedChainAction, int damageAmount, int blockAmount, AbstractCard.CardType cardType) {
        super(POWER_ID, owner, amount, finishedChainAction, cardType);
        this.name = NAME;
        this.type = POWER_TYPE;
        this.damageAmount = damageAmount;
        this.blockAmount = blockAmount;
        this.updateDescription();
    }

    public void updateDescription()
    {
        this.description = DESCRIPTIONS[0] + this.blockAmount + DESCRIPTIONS[1] + this.damageAmount + DESCRIPTIONS[2];
    }
}
