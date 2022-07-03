package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheDefault;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.patches.RelayedDmgSum;
import theUnchainedMod.powers.RelayedDamagePower;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class RecklessSwing extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(RecklessSwing.class.getSimpleName());
    public static final String IMG = makeCardPath("RecklessSwing.png");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 0;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC_NUMBER = 4;

    public RecklessSwing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
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
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RelayedDamagePower(p, p, magicNumber)));
        GameActionManager actionManager = AbstractDungeon.actionManager;
        RelayedDmgSum.relayedDamageSum.set(actionManager, RelayedDmgSum.relayedDamageSum.get(actionManager) + magicNumber);
    }
}
