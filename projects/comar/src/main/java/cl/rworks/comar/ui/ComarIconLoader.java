/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cl.rworks.comar.ui;

import com.pepperonas.fxiconics.FxIconicsLabel;
import com.pepperonas.fxiconics.awf.FxFontAwesome;
import com.pepperonas.fxiconics.gmd.FxFontGoogleMaterial;
import javafx.scene.control.Label;

/**
 *
 * @author rgonzalez
 */
public final class ComarIconLoader {

    public static final FxFontAwesome.Icons HOME = FxFontAwesome.Icons.faw_home;
    public static final FxFontAwesome.Icons BACK = FxFontAwesome.Icons.faw_arrow_left;
    public static final FxFontGoogleMaterial.Icons POS = FxFontGoogleMaterial.Icons.gmd_local_grocery_store;
    public static final FxFontGoogleMaterial.Icons ADM = FxFontGoogleMaterial.Icons.gmd_local_convenience_store;

    public static Label getIcon(FxFontAwesome.Icons icon, int size) {
        return (FxIconicsLabel) new FxIconicsLabel.Builder(icon).size(size).color("337AB7").build();
    }
    
    public static Label getIcon(FxFontGoogleMaterial.Icons icon, int size) {
        return (FxIconicsLabel) new FxIconicsLabel.Builder(icon).size(size).color("337AB7").build();
    }

}
