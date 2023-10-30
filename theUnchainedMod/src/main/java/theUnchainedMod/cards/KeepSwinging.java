package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.SwingingPower;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class KeepSwinging extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(KeepSwinging.class.getSimpleName());
    public static final String IMG = makeCardPath("KeepSwinging.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.POWER;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 4;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 2;

    public KeepSwinging() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new SwingingPower(p, p, this.magicNumber)));
    }
}
