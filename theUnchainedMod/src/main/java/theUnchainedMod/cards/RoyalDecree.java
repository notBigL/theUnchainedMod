package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.util.UtilityClass;


import static theUnchainedMod.DefaultMod.makeCardPath;

public class RoyalDecree extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(RoyalDecree.class.getSimpleName());
    public static final String IMG = makeCardPath("RoyalDecree.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 0;

    public RoyalDecree() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        this.exhaust = true;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = UtilityClass.returnTrulyRandomTwoCostCardInCombat();
        c.setCostForTurn(magicNumber);
        this.addToBot(new MakeTempCardInHandAction(c, true));
    }
}
