package italo.explab.inter.exp.string;

import italo.explab.InterAplic;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.string.node.StrValor;
import italo.explab.codigo.Codigo;
import italo.explab.inter.Inter;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.util.InterUtil;
import italo.explab.var.StringVar;

public class StringInter extends Inter {
                
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {                                
        AnalisadorSintaticoManager asmanager = aplic.getAnalisadorSintaticoManager();
        
        AnaliseResult result = asmanager.getStringAnalisador().analisa( codigo, i );
        if ( result.getJ() == 0 ) {
            return new InterResult( result );
        } else {        
            int j = result.getJ();
            
            InterUtil interUtil = aplic.getInterUtil();
            
            String valor = interUtil.formataCHsEspeciais( codigo, i+1, i+j-1 );            
            
            StrValor strValor = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoStrValor( i, no, codigo );
            strValor.setValor( new StringVar( valor ) );
                        
            return new InterResult( strValor, j );            
        }        
    }
        
}
