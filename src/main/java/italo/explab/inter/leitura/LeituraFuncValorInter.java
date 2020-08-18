package italo.explab.inter.leitura;

import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.analisador.sintatico.AnalisadorSintaticoManager;
import italo.explab.analisador.sintatico.AnaliseResult;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.exp.variavel.func.FuncVarExp;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.func.UsuarioFunc;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterResult;
import italo.explab.inter.InterTO;
import italo.explab.inter.leitura.result.LeituraParamsECorpoFuncInterResult;
import italo.explab.util.ContadorUtil;

public class LeituraFuncValorInter extends Inter {
        
    @Override
    public InterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {        
        AnalisadorSintaticoManager asManager = aplic.getAnalisadorSintaticoManager();
                
        AnaliseResult aresult = asManager.getLeituraFuncValorAnalisador().analisa( codigo, i );        
        if ( aresult.getJ() == 0 )
            return new InterResult( aresult );             
                
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();                 
                
        FuncVarExp fvarexp = aplic.getExecutor().getExecManager().getExecNoFactory().getExpFactory().novoFuncVarExp( i, no, codigo );                                                                                        
                        
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.FUNC );
        if ( cont == 0 )
            return new InterResult();
        
        int j = cont;
        j += contUtil.contaEsps( codigo, i+j );
                        
        LeituraParamsECorpoFuncInterResult result = (LeituraParamsECorpoFuncInterResult)manager.getLeituraParamsECorpoInter().interpreta( no, base, aplic, codigo, i+j, i2 );
        if ( result.getJ() == 0 )
            return result;
        
        j += result.getJ();
        
        String[] params = result.getParams();
        String[] exceptionClasses = result.getExceptionClasses();
        Instrucao[] instrucoes = result.getInstrucoes();
        
        String nome = String.valueOf( System.currentTimeMillis() );
                     
        fvarexp.setUsuarioFunc( new UsuarioFunc( nome, params, exceptionClasses, instrucoes ) );                
        return new InterResult( fvarexp, j );
    }
    
}
