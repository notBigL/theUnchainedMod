package theUnchainedMod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.characters.TheUnchained;
import theUnchainedMod.patches.CustomTags;
import theUnchainedMod.powers.BrassKnucklePower;
import theUnchainedMod.powers.WhiplashPower;
import theUnchainedMod.relics.Churros;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class LashOut extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(LashOut.class.getSimpleName());
    public static final String IMG = makeCardPath("LashOut.png");
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheUnchained.Enums.COLOR_ORANGE;

    private static final int COST = 2;
    private static final int DAMAGE = 13;
    private static final int UPGRADE_PLUS_DMG = 4;
    private static final int CHAIN_LENGTH = 1;
    private static final int MAGIC_NUMBER = 1;

    public LashOut() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = damage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        tags.add(CustomTags.CHAIN);
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
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY, false, true));
        CardCrawlGame.sound.play("normalChainAttack");
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new BrassKnucklePower(p, CHAIN_LENGTH, new GainEnergyAction(baseMagicNumber), TYPE)));
        if (p.hasRelic(Churros.ID)) {
            Churros churros = (Churros) p.getRelic(Churros.ID);
            if (!churros.isEaten()) {
                churros.eat();
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new BrassKnucklePower(p, CHAIN_LENGTH, new GainEnergyAction(baseMagicNumber), TYPE)));
            }
        }
    }
}
