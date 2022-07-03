package theUnchainedMod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.PoeticJusticeAction;
import theUnchainedMod.characters.TheDefault;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class PoeticJustice extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(PoeticJustice.class.getSimpleName());
    public static final String IMG = makeCardPath("PoeticJustice.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 10;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 7;

    public PoeticJustice() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new PoeticJusticeAction(magicNumber));
    }
}
