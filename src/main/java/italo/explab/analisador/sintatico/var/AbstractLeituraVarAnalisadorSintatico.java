package italo.explab.analisador.sintatico.var;

import italo.explab.ErroMSGs;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.codigo.Codigo;
import italo.explab.msg.CodigoErro;

public abstract class AbstractLeituraVarAnalisadorSintatico implements AnalisadorSintatico {
    
    protected final AnalisadorSintaticoManager manager;
    
    public AbstractLeituraVarAnalisadorSintatico( AnalisadorSintaticoManager manager ) {
        this.manager = manager;
    }
    
    public abstract AnaliseResult analisaValor( Codigo codigo, int i );
    
    @Override
    public AnaliseResult analisa( Codigo codigo, int i ) {
        int j = 0;
        
        AnaliseResult result = manager.getVarOuChamadaFuncAnalisador().analisa( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;        
        
        j += result.getJ();        
        j += manager.getContUtil().contaEsps( codigo, i+j );
        
        if ( codigo.getSEGCH( i+j ) == '[' ) {
            if ( codigo.getSEGCH( i+j+1 ) != ']' ) {
                CodigoErro erro = new CodigoErro( this.getClass(), codigo, i+j+1, ErroMSGs.FECHA_COLCHETES_ESPERADO );
                return new AnaliseResult( erro );
            }
            j += 2;
        } else {
            switch ( codigo.getSEGCH( i+j ) ) {
                case '+':
                case '-':
                case '*':
                case '/':
                case '%':
                    j++;        
                    break;
            }
        }
        
        j += manager.getContUtil().contaEsps( codigo, i+j );        
        
        if ( codigo.getSEGCH( i+j ) != '=' )
            return new AnaliseResult();        
                
        j++;
        j+= manager.getContUtil().contaEsps( codigo, i+j );
        
        result = this.analisaValor( codigo, i+j );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
                
        return new AnaliseResult( j );
    }
    
}
