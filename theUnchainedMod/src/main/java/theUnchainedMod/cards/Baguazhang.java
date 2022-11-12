package theUnchainedMod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theUnchainedMod.DefaultMod;
import theUnchainedMod.actions.BaguazhangAction;
import theUnchainedMod.characters.TheDefault;

import java.util.Iterator;

import static theUnchainedMod.DefaultMod.makeCardPath;

public class Baguazhang extends AbstractDynamicCard {

    public static final String ID = DefaultMod.makeID(Baguazhang.class.getSimpleName());
    public static final String IMG = makeCardPath("Baguazhang.png");
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheDefault.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 15;
    private static final int UPGRADE_PLUS_BLOCK = 5;
    private static final int MAGIC_NUMBER = 10;

    public Baguazhang() {
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
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();

        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (m != null && !m.isDead && (m.getIntentDmg() < magicNumber || m.intent == AbstractMonster.Intent.BUFF ||
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
        AbstractDungeon.actionManager.addToBottom(new BaguazhangAction(p, m, block, magicNumber));
    }
}
