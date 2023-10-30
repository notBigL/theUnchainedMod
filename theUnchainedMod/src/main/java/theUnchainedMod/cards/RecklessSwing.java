package theUnchainedMod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.ApplyRelayedDamageAction;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class RecklessSwing extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(RecklessSwing.class.getSimpleName());
    public static final String IMG = makeCardPath("RecklessSwing.png");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF_AND_ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 0;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DMG = 3;
    private static final int MAGIC_NUMBER = 8;

    public RecklessSwing() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        tags.add(CustomTags.SWING);
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
        CardCrawlGame.sound.playA("swingAttack", MathUtils.random(-0.2F, 0.2F));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, false, true));
        AbstractDungeon.actionManager.addToBottom(new ApplyRelayedDamageAction(p, magicNumber));
    }
}
