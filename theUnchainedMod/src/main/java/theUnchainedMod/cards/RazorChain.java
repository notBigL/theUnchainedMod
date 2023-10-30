package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.powers.HeavyHitPower;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class RazorChain extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(RazorChain.class.getSimpleName());
    public static final String IMG = makeCardPath("RazorChain.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 1;
    private static final int DAMAGE = 9;
    private static final int UPGRADE_PLUS_DMG = 4;

    public RazorChain() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        RazorChainHit(p, m);
        if(m.currentBlock > 0) RazorChainHit(p, m);
    }
    public void RazorChainHit(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        if (p.hasPower(HeavyHitPower.POWER_ID)) {
            HeavyHitPower hhP = (HeavyHitPower) p.getPower(HeavyHitPower.POWER_ID);
            hhP.triggerForRazorChain(m);
        }
    }
}
