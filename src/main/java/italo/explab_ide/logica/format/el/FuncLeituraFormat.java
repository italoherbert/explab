package italo.explab_ide.logica.format.el;

import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.util.ContadorUtil;
import italo.explab_ide.ExpLabIDEAplic;

public class FuncLeituraFormat extends ClasseOuFuncFormat {

    private PalavraReservadaFormat prformat;
    
    public FuncLeituraFormat(ExpLabIDEAplic aplic, PalavraReservadaFormat prformat ) {
        super(aplic);
        this.prformat = prformat;
    }        
    
    @Override
    public AnaliseResult analisa(Codigo codigo, int i) {
        ContadorUtil contUtil = aplic.getContadorUtil();
        
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.FUNC );
        
        if ( cont == 0 )
            return new AnaliseResult();
        
        boolean ok = ( i == 0 ) ;
        if ( !ok ) {
            char ch = codigo.getSEGCH( i-1 );
            if ( !Character.isLetterOrDigit( ch ) )                                             
                ok = true;                         
        }

        if ( ok ) {
            char ch = codigo.getSEGCH( i+cont );
            if ( !Character.isLetterOrDigit( ch ) )               
                return new AnaliseResult( cont );               
        }      
        
        return new AnaliseResult();                
    }
   
}
