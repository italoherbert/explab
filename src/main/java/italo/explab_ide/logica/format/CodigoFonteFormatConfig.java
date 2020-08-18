package italo.explab_ide.logica.format;

import java.awt.Color;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

public class CodigoFonteFormatConfig {        
    
    private SimpleAttributeSet palavraReservadaStyle;
    private SimpleAttributeSet classeOuFuncNomeStyle;
    
    private SimpleAttributeSet valorStringStyle;
    private SimpleAttributeSet boolExpStyle;
    private SimpleAttributeSet realExpStyle;

    private SimpleAttributeSet comentarioStyle;
    
    private SimpleAttributeSet varNativaStyle;
    private SimpleAttributeSet funcNativaStyle;
    private SimpleAttributeSet normalStyle;
    
    public CodigoFonteFormatConfig() {
        normalStyle = new SimpleAttributeSet();
        
        palavraReservadaStyle = new SimpleAttributeSet();
        palavraReservadaStyle.addAttribute( StyleConstants.Foreground, Color.BLUE );
        
        classeOuFuncNomeStyle = new SimpleAttributeSet();
        classeOuFuncNomeStyle.addAttribute( StyleConstants.Foreground, Color.BLACK ); 
        classeOuFuncNomeStyle.addAttribute( StyleConstants.Bold, Boolean.TRUE ); 
        
        valorStringStyle = new SimpleAttributeSet();
        valorStringStyle.addAttribute( StyleConstants.Foreground, new Color( 150, 120, 50 ) );
        
        boolExpStyle = new SimpleAttributeSet();
        boolExpStyle.addAttribute( StyleConstants.Foreground, new Color( 108, 208, 255 ) );
                
        realExpStyle = new SimpleAttributeSet();
        realExpStyle.addAttribute( StyleConstants.Foreground, new Color( 60, 60, 60) );
        
        comentarioStyle = new SimpleAttributeSet();
        comentarioStyle.addAttribute( StyleConstants.Foreground, new Color( 100, 100, 100 ) );
        
        varNativaStyle = new SimpleAttributeSet();
        varNativaStyle.addAttribute( StyleConstants.Foreground, new Color( 30, 100, 50 ) ); 
        
        funcNativaStyle = new SimpleAttributeSet();
        funcNativaStyle.addAttribute( StyleConstants.Foreground, new Color( 120, 120, 200 ) ); 
    }

    public SimpleAttributeSet getNormalStyle() {
        return normalStyle;
    }

    public void setNormalStyle(SimpleAttributeSet normalStyle) {
        this.normalStyle = normalStyle;
    }

    public SimpleAttributeSet getPalavraReservadaStyle() {
        return palavraReservadaStyle;
    }

    public void setPalavraReservadaStyle(SimpleAttributeSet palavraReservadaStyle) {
        this.palavraReservadaStyle = palavraReservadaStyle;
    }

    public SimpleAttributeSet getClasseOuFuncNomeStyle() {
        return classeOuFuncNomeStyle;
    }

    public void setClasseOuFuncNomeStyle(SimpleAttributeSet classeOuFuncNomeStyle) {
        this.classeOuFuncNomeStyle = classeOuFuncNomeStyle;
    }

    public SimpleAttributeSet getValorStringStyle() {
        return valorStringStyle;
    }

    public void setValorStringStyle(SimpleAttributeSet valorStringStyle) {
        this.valorStringStyle = valorStringStyle;
    }

    public SimpleAttributeSet getBoolExpStyle() {
        return boolExpStyle;
    }

    public void setBoolExpStyle(SimpleAttributeSet boolExpStyle) {
        this.boolExpStyle = boolExpStyle;
    }

    public SimpleAttributeSet getRealExpStyle() {
        return realExpStyle;
    }

    public void setRealExpStyle(SimpleAttributeSet realExpStyle) {
        this.realExpStyle = realExpStyle;
    }

    public SimpleAttributeSet getComentarioStyle() {
        return comentarioStyle;
    }

    public void setComentarioStyle(SimpleAttributeSet comentarioStyle) {
        this.comentarioStyle = comentarioStyle;
    }

    public SimpleAttributeSet getVarNativaStyle() {
        return varNativaStyle;
    }

    public void setVarNativaStyle(SimpleAttributeSet varNativaStyle) {
        this.varNativaStyle = varNativaStyle;
    }

    public SimpleAttributeSet getFuncNativaStyle() {
        return funcNativaStyle;
    }

    public void setFuncNativaStyle(SimpleAttributeSet funcNativaStyle) {
        this.funcNativaStyle = funcNativaStyle;
    }
    
}
