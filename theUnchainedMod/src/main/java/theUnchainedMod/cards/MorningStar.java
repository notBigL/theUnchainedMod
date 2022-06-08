package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheDefault;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class MorningStar extends AbstractDynamicCard {
    public static final String ID = DefaultMod.makeID(MorningStar.class.getSimpleName());
    public static final String IMG = makeCardPath("MorningStar.png");
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 4;
    private static final int MAGIC_NUMBER = 40;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 10;

    public MorningStar() {
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
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.cost = 4;
    }

    @Override
    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        if (c.cost == 2 && !c.uuid.equals(this.uuid)) {
            this.updateCost(-1);
        }
    }
}
