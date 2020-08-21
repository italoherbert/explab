package italo.explab.arvore.cmd.node;

import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.string.node.StringExp;
import italo.explab.arvore.instrucao.Instrucao;

public class Liste extends Instrucao {

    private StringExp tipoExp;
    private StringExp termoExp;
    private StringExp classeNomeExp;

    @Override
    public Instrucao novo( ExecNo parent ) {
        Liste liste = new Liste();
        super.carrega( liste, parent );
        
        if ( tipoExp != null )
            liste.setTipoExp( (StringExp)tipoExp.novo( liste ) );
        if ( termoExp != null )
            liste.setTermoExp( (StringExp)termoExp.novo( liste ) );
        if ( classeNomeExp != null )
            liste.setClasseNomeExp( (StringExp)classeNomeExp.novo( liste ) );
        
        return liste;
    }

    @Override
    public void setBaseParamsParente( ExecNo parent ) {
        if ( tipoExp != null )
            tipoExp.setBaseParamsParente( parent );
        if ( termoExp != null )
            termoExp.setBaseParamsParente( parent );
        if ( classeNomeExp != null )
            classeNomeExp.setBaseParamsParente( parent ); 
    }
    
    public StringExp getTipoExp() {
        return tipoExp;
    }

    public void setTipoExp(StringExp tipoExp) {
        this.tipoExp = tipoExp;
    }

    public StringExp getTermoExp() {
        return termoExp;
    }

    public void setTermoExp(StringExp termoExp) {
        this.termoExp = termoExp;
    }

    public StringExp getClasseNomeExp() {
        return classeNomeExp;
    }

    public void setClasseNomeExp(StringExp classeNomeExp) {
        this.classeNomeExp = classeNomeExp;
    }
    
}
