package theUnchainedMod.patches;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;

import java.util.Arrays;

@SpirePatch(clz = FontHelper.class, method = "renderRotatedText")
public class KeywordColorChangePatch {
    private static final String[] keywordList = {
            "Chain"
    };/*
    @SpirePrefixPatch
    public static void ChangeKeywordColor(FontHelper instance, SpriteBatch sb, BitmapFont font, String msg, float x, float y, float offsetX, float offsetY, float angle, boolean roundY, @ByRef Color[] c) {
        if (Arrays.asList(keywordList).contains(msg)) {

            if (c[0].toString().equals(Settings.GOLD_COLOR.toString())) {
                c[0] = Color.PINK;
            }
        }
    }*/
}
