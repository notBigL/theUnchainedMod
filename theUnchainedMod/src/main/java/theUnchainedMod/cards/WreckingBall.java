package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.AllEnemiesGainBlockAction;
import theUnchainedMod.actions.AllEnemiesLoseHPAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.WreckingBallPower;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class WreckingBall extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(WreckingBall.class.getSimpleName());
    public static final String IMG = makeCardPath("WreckingBall.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int CHAIN_LENGTH = 2;
    private static final int MAGIC_NUMBER = 25;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 5;
    private static final int SECOND_MAGIC_NUMBER = 6;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = -1;

    public WreckingBall() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
        this.isMultiDamage = true;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
            upgradeDefaultSecondMagicNumber(UPGRADE_PLUS_SECOND_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new AllEnemiesGainBlockAction(defaultSecondMagicNumber));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new WreckingBallPower(p, CHAIN_LENGTH, new AllEnemiesLoseHPAction(magicNumber), magicNumber, TYPE)));
    }
}
