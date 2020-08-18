package italo.explab_ide.logica.format.el;

import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.util.ContadorUtil;
import italo.explab_ide.ExpLabIDEAplic;

public class FuncLeituraFormat extends ClasseOuFuncFormat {

    public FuncLeituraFormat(ExpLabIDEAplic aplic) {
        super(aplic);
    }        
    
    @Override
    public AnaliseResult analisa(Codigo codigo, int i) {
        ContadorUtil contUtil = aplic.getContadorUtil();
        
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.FUNC );
                
        int j = 0;
        if ( cont == 0 )
            return new AnaliseResult();
        
        j += cont;
        j += contUtil.contaEsps( codigo, i+j );
        j += contUtil.contaComentariosTam( codigo, i+j );
        j += contUtil.contaEsps( codigo, i+j );
        
        if ( codigo.getSEGCH( i+j ) == '(' )
            return new AnaliseResult();
        
        return new AnaliseResult( cont );
    }
   
}
