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
        
        if ( codigo.isCHValido( i+cont ) ) {
            char ch = codigo.getCH( i+cont );
            switch( ch ) {
                case ' ':
                case '\r':
                case '\t':
                case '\n':
                case ';':
                    return new AnaliseResult( cont );
                default:
                    return new AnaliseResult();
            }
        }
                
        return new AnaliseResult( cont );
    }
   
}
