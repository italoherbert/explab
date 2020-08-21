package italo.explab.inter.leitura;

import italo.explab.InterAplic;
import italo.explab.PalavrasReservadas;
import italo.explab.arvore.ExecNo;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.codigo.Codigo;
import italo.explab.construtor.Construtor;
import italo.explab.inter.Inter;
import italo.explab.inter.InterManager;
import italo.explab.inter.InterTO;
import italo.explab.inter.leitura.result.LeituraConstrutorInterResult;
import italo.explab.inter.leitura.result.LeituraParamsECorpoFuncInterResult;
import italo.explab.util.ContadorUtil;

public class LeituraConstrutorInter extends Inter {

    @Override
    public LeituraConstrutorInterResult interpreta( ExecNo no, ExecNo base, InterAplic aplic, Codigo codigo, InterTO to, int i, int i2 ) {
        InterManager manager = aplic.getInterManager();
        ContadorUtil contUtil = aplic.getContUtil();
                    
        int cont = contUtil.contaTextoValor( codigo, i, PalavrasReservadas.CONSTRUTOR );
        if ( cont == 0  )
            return new LeituraConstrutorInterResult();
                                                                        
        int j = cont;
        j += contUtil.contaEsps( codigo, i+j );
        
        LeituraParamsECorpoFuncInterResult leituraParsECorpoResult = 
                manager.getLeituraParamsECorpoInter().interpreta( no, base, aplic, codigo, to, i+j, i2 );
        
        if ( leituraParsECorpoResult.getErro() != null )
            return new LeituraConstrutorInterResult( leituraParsECorpoResult.getErro() );
        
        j += leituraParsECorpoResult.getJ();
                            
        String[] params = leituraParsECorpoResult.getParams();
        String[] exceptionClasses = leituraParsECorpoResult.getExceptionClasses();
        Instrucao[] instrucoes = leituraParsECorpoResult.getInstrucoes();                
                
        Construtor construtor = new Construtor( params, exceptionClasses, instrucoes );
        return new LeituraConstrutorInterResult( construtor, j+1 );
    }
    
}
