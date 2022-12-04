package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.AllEnemiesLoseStrengthAction;
import theUnchainedMod.characters.TheUnchained;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class Glare extends AbstractDynamicCard{

    public static final String ID = DefaultMod.makeID(Glare.class.getSimpleName());
    public static final String IMG = makeCardPath("Glare.png");
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.SELF;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int MAGIC_NUMBER = 1;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 1;



    public Glare() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        this.exhaust = true;
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
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
        AbstractDungeon.actionManager.addToBottom(new AllEnemiesLoseStrengthAction(magicNumber));
    }
}
