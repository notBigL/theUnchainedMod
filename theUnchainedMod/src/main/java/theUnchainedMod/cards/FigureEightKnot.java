package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.LoseRelayedDamageAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.KnotPower;
import theUnchainedMod.powers.RelayPower;

import static com.megacrit.cardcrawl.core.CardCrawlGame.languagePack;
import static theUnchainedMod.DefaultMod.makeCardPath;

public class FigureEightKnot extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(FigureEightKnot.class.getSimpleName());
    public static final String IMG = makeCardPath("FigureEightKnot.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;


    private static final int COST = 2;
    private static final int MAGIC_NUMBER = 9;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 3;

    public FigureEightKnot() {
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
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RelayPower(p, p, magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new LoseRelayedDamageAction(magicNumber));
    }
}
