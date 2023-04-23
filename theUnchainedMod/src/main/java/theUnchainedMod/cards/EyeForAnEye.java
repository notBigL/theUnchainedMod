package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.EyeForAnEyeAction;
import theUnchainedMod.characters.TheUnchained;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class EyeForAnEye extends AbstractDynamicCard {
    public static final String ID = TheUnchainedMod.makeID(EyeForAnEye.class.getSimpleName());
    public static final String IMG = makeCardPath("EyeForAnEye.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 10;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 5;
    private static final int SECOND_MAGIC_NUMBER = 5;

    public EyeForAnEye() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VigorPower(p, defaultSecondMagicNumber)));
        AbstractDungeon.actionManager.addToBottom(new EyeForAnEyeAction(magicNumber, p));
    }
}
