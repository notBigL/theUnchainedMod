package theUnchainedMod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theUnchainedMod.TheUnchainedMod;
import theUnchainedMod.actions.BaguazhangAction;
import theUnchainedMod.characters.TheUnchained;

import java.util.Iterator;

import static theUnchainedMod.TheUnchainedMod.makeCardPath;

public class Baguazhang extends AbstractDynamicCard {

    public static final String ID = TheUnchainedMod.makeID(Baguazhang.class.getSimpleName());
    public static final String IMG = makeCardPath("Baguazhang.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheUnchained.Enums.UNCHAINED_COLOR;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int MAGIC_NUMBER = 10;
    private static final int UPGRADE_PLUS_MAGIC_NUMBER = 5;
    private static final int SECOND_MAGIC_NUMBER = 10;

    public Baguazhang() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseBlock = block = BLOCK;
        baseMagicNumber = magicNumber = MAGIC_NUMBER;
        defaultBaseSecondMagicNumber = defaultSecondMagicNumber = SECOND_MAGIC_NUMBER;
    }

    @Override
    public void upgrade() {
        if(!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_NUMBER);
        }
    }

    @Override
    protected void applyPowersToBlock() {
        super.applyPowersToBlock();
        this.isMagicNumberModified = false;
        float tmp = (float)this.baseMagicNumber;

        Iterator var2;
        AbstractPower p;
        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlock(tmp, this)) {
            p = (AbstractPower)var2.next();
        }

        for(var2 = AbstractDungeon.player.powers.iterator(); var2.hasNext(); tmp = p.modifyBlockLast(tmp)) {
            p = (AbstractPower)var2.next();
        }

        if (this.baseMagicNumber != MathUtils.floor(tmp)) {
            this.isMagicNumberModified = true;
        }

        if (tmp < 0.0F) {
            tmp = 0.0F;
        }

        this.magicNumber = MathUtils.floor(tmp);
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (m != null && !m.isDeadOrEscaped() && (m.getIntentDmg() < defaultSecondMagicNumber || m.intent == AbstractMonster.Intent.BUFF ||
                    m.intent == AbstractMonster.Intent.DEBUFF || m.intent == AbstractMonster.Intent.STRONG_DEBUFF ||
                    m.intent == AbstractMonster.Intent.DEBUG || m.intent == AbstractMonster.Intent.DEFEND ||
                    m.intent == AbstractMonster.Intent.DEFEND_DEBUFF || m.intent == AbstractMonster.Intent.DEFEND_BUFF ||
                    m.intent == AbstractMonster.Intent.ESCAPE || m.intent == AbstractMonster.Intent.MAGIC ||
                    m.intent == AbstractMonster.Intent.NONE || m.intent == AbstractMonster.Intent.SLEEP ||
                    m.intent == AbstractMonster.Intent.STUN || m.intent == AbstractMonster.Intent.UNKNOWN)) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                break;
            }
        }

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, block));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, 5));
        AbstractDungeon.actionManager.addToBottom(new BaguazhangAction(p, m, magicNumber, defaultSecondMagicNumber));
    }
}
