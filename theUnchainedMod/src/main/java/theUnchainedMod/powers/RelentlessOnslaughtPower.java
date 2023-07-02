package theUnchainedMod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.RelentlessOnslaughtAction;

public class RelentlessOnslaughtPower extends AbstractChainPower {
    public AbstractCreature source;

    public static final String POWER_ID = TheUnchainedMod.makeID("RelentlessOnslaughtPower");
    private static final PowerStrings powerstrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerstrings.NAME;
    public static final String[] DESCRIPTIONS = powerstrings.DESCRIPTIONS;
    public static AbstractPower.PowerType POWER_TYPE = PowerType.BUFF;

    private final int damageAmount;
    private final int attackAmount;


    public RelentlessOnslaughtPower(AbstractCreature owner, int amount, int damageAmount, int attackAmount, AbstractMonster monster, DamageInfo info, AbstractCard.CardType cardType) {
        super(POWER_ID, owner, amount, new RelentlessOnslaughtAction(attackAmount, monster, info, damageAmount), cardType);
        this.name = NAME;
        this.type = POWER_TYPE;
        this.damageAmount = damageAmount;
        this.attackAmount = attackAmount;
        this.updateDescription();
    }

    public RelentlessOnslaughtPower(AbstractCreature owner, int amount, AbstractGameAction finishedChainAction, int damageAmount, int attackAmount, AbstractCard.CardType cardType) {
        super(POWER_ID, owner, amount, finishedChainAction, cardType);
        this.name = NAME;
        this.type = POWER_TYPE;
        this.damageAmount = damageAmount;
        this.attackAmount = attackAmount;
        this.updateDescription();
    }

    public void updateDescription() {
            this.description = DESCRIPTIONS[0] + this.damageAmount + DESCRIPTIONS[1] + this.attackAmount + DESCRIPTIONS[2];
    }
}
