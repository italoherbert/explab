package italo.explab_ide.logica.format.el;

import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab_ide.ExpLabIDEAplic;

public class ClasseLeituraFormat extends ClasseOuFuncFormat {

    public ClasseLeituraFormat(ExpLabIDEAplic aplic) {
        super(aplic);
    }        
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int cont = aplic.getContadorUtil().contaTextoValor( codigo, i, PalavrasReservadas.CLASSE );
        
        if ( cont == 0 )
            return new AnaliseResult();
        
        if ( i > 0 ) {
            char ch = codigo.getSEGCH( i-1 );                
            if ( Character.isLetterOrDigit( ch ) || ch == '_' )
                return new AnaliseResult();                    
        }
        char ch = codigo.getSEGCH( i+cont );
        if ( Character.isLetterOrDigit( ch ) || ch == '_' )
            return new AnaliseResult();

        return new AnaliseResult( cont );                            
    }
   
}
