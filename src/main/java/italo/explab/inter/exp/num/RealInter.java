package italo.explab.inter.exp.num;

import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.num.node.NumRealValor;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.var.num.NumeroRealVar;
import italo.explab.var.num.RealVar;

public class RealInter extends Inter {
    
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                                                
        AnalisadorSintaticoManager asmanager = aplic.getAnalisadorSintaticoManager();
        
        AnaliseResult result = asmanager.getNumRealAnalisador().analisa( codigo, i );
        if ( result.getJ() == 0 ) {
            return new InterResult( result );
        } else {        
            int j = result.getJ();
            String numero = codigo.getCodigo().substring( i, i+j );
            double n = Double.parseDouble( numero );             
            RealVar var = new NumeroRealVar( n ); 
            
            NumRealValor nv = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoNumRealValor( i, no, codigo );
            nv.setValor( var );            
            
            return new InterResult( nv, j );
        }
    }

}
