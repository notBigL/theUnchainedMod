package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.AllEnemiesGainBlockAction;
import theUnchainedMod.characters.TheDefault;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class KeepDistance extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(KeepDistance.class.getSimpleName());
    public static final String IMG = makeCardPath("KeepDistance.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 13;
    private static final int UPGRADE_PLUS_BLOCK = 4;
    private static final int MAGIC_NUMBER = 7;

    public KeepDistance() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, this.block));
        AbstractDungeon.actionManager.addToBottom(new AllEnemiesGainBlockAction(this.magicNumber));
    }
}
