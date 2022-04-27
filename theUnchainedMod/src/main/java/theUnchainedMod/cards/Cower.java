package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.AllEnemiesVulnerableAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.RelayPower;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class Cower extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Cower.class.getSimpleName());
    public static final String IMG = makeCardPath("Cower.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int FRAIL = 2;
    private static final int MAGIC_NUMBER = 6;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 3;
    private static final int SECOND_MAGIC_NUMBER = 1;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = 1;

    public Cower() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RelayPower(p, p, magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, FRAIL, false)));
        AbstractDungeon.actionManager.addToBottom(new AllEnemiesVulnerableAction(defaultSecondMagicNumber));
    }
}
