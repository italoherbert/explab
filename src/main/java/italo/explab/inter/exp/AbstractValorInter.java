package italo.explab.inter.exp;

import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintatico;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.valornull.NullValor;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.util.ContadorUtil;

public abstract class AbstractValorInter extends Inter {
    
    public abstract AnalisadorSintatico getValorAnalisador( AnalisadorSintaticoManager asManager );

    protected abstract InterResult interpretaEXPs( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 );            
    
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil =  aplic.getContUtil();
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();                                      
               
        AnaliseResult aresult = this.getValorAnalisador( asManager ).analisa( codigo, i ); 
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );     
                         
        InterResult result = manager.getEsteObjVarInter().interpreta( no, base, aplic, codigo, i, i2 );
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;
        
        result = manager.getAtribVarInter().interpreta( no, base, aplic, codigo, i, i2 );
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;
                                
        result = this.interpretaEXPs( no, base, aplic, codigo, to, i, i2 );
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;        
                
        result = manager.getMatrizInter().interpreta( no, base, aplic, codigo, i, i2 );       
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;                                

        result = manager.getLeituraFuncValorInter().interpreta( no, base, aplic, codigo, i, i2 );
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;        
        
        result = manager.getOOVarOuMatELOuFuncInter().interpreta( no, base, aplic, codigo, i, i2 );                            
        if ( result.getJ() > 0 || result.getErro() != null )
            return result;                
                             
        int j = contUtil.contaEsps( codigo, i );                        
        int cont = contUtil.contaTextoValor( codigo, i+j, PalavrasReservadas.NULL );
        if ( cont > 0 ) {
            NullValor nullValor = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNullValor( i, no, codigo );
            return new InterResult( nullValor, j+cont );                
        }
                        
        return new InterResult();
    }
    
}
