package theUnchainedMod.cards;

import basemod.AutoAdd;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.powers.BlindSwingPower;

import static theUnchainedMod.DefaultMod.makeCardPath;

@AutoAdd.Ignore
public class BlindSwing extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(BlindSwing.class.getSimpleName());
    public static final String IMG = makeCardPath("BlindSwing.png");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int MAGIC_NUMBER = 8;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 3;
    private static final int SECOND_MAGIC_NUMBER = 2;
    private static final int CHAIN_LENGTH = 1;

    public BlindSwing() {
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
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.magicNumber, DamageInfo.DamageType.HP_LOSS)));
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new BlindSwingPower(p, CHAIN_LENGTH, defaultSecondMagicNumber, TYPE)));
    }
}
