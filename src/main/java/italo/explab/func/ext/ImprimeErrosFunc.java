package italo.explab.func.ext;

import italo.explab.MetodoParam;
import italo.explab.arvore.Executor;
import italo.explab.arvore.exp.func.FuncExp;
import italo.explab.codigo.Codigo;
import italo.explab.func.AbstractFunc;
import italo.explab.func.Func;
import italo.explab.func.FuncFactory;
import italo.explab.func.FuncResult;

public class ImprimeErrosFunc extends AbstractFunc {

    private final String NOME = FuncFactory.IMP_ERROS;
    private final int QUANT_PARAMS = 0;

    @Override
    public FuncResult exec( FuncExp fno, Executor executor, Codigo codigo, MetodoParam[] params ) {        
        executor.getAplic().getSessaoManager().getMSGController().enviaErros();                
        return new FuncResult( null, this );
    }

    @Override
    public Func nova() {
        return new ImprimeErrosFunc();
    }

    @Override
    public String getNome() {
        return NOME;
    }

    @Override
    public int getQuantParametros() {
        return QUANT_PARAMS;
    }
    
}
