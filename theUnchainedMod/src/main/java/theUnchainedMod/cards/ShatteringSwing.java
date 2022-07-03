package theUnchainedMod.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.ShatteringSwingAction;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.patches.CustomTags;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class ShatteringSwing extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(ShatteringSwing.class.getSimpleName());
    public static final String IMG = makeCardPath("ShatteringSwing.png");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    public ShatteringSwing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        tags.add(CustomTags.SWING);
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ShatteringSwingAction(damage, m, p));
    }
}
