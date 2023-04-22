package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.ApplyCrushedArmorAction;
import theUnchainedMod.characters.TheUnchained;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class Cripple extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(Cripple.class.getSimpleName());
    public static final String IMG = makeCardPath("Cripple.png");
    private static final AbstractCard.CardRarity RARITY = AbstractCard.CardRarity.UNCOMMON;
    private static final AbstractCard.CardTarget TARGET = AbstractCard.CardTarget.ENEMY;
    private static final AbstractCard.CardType TYPE = AbstractCard.CardType.SKILL;
    public static final AbstractCard.CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;

    private static final int COST = 1;
    private static final int MAGIC_NUMBER = 4;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 2;
    private static final int SECOND_MAGIC_NUMBER = 2;
    private static final int UPGRADE_PLUS_SECOND_MAGIC_NUMBER = 1;



    public Cripple() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
        this.exhaust = true;
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
        AbstractDungeon.actionManager.addToBottom(new ApplyCrushedArmorAction(m, magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, defaultSecondMagicNumber, false)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, defaultSecondMagicNumber, false)));
    }
}
