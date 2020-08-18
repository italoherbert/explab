package italo.explab.inter.leitura.result;

import italo.explab.codigo.Codigo;
import italo.explab.msg.Erro;
import java.util.List;
import italo.explab.AnaliseOuInterResult;
import italo.explab.arvore.instrucao.Instrucao;
import italo.explab.inter.InterResult;

public class LeituraParamsECorpoFuncInterResult extends InterResult {
    
    private String[] params;
    private String[] exceptionClasses;
    private Instrucao[] instrucoes;
        
    public LeituraParamsECorpoFuncInterResult() {}

    public LeituraParamsECorpoFuncInterResult( String[] params, String[] exceptionClasses, Instrucao[] instrucoes, int j ) {
        super( j );
        this.params = params;
        this.exceptionClasses = exceptionClasses;
        this.instrucoes = instrucoes;
    }

    public LeituraParamsECorpoFuncInterResult( Erro erro ) {
        super( erro );
    }

    public LeituraParamsECorpoFuncInterResult( AnaliseOuInterResult result ) {
        super( result );
    }

    public String[] getParams() {
        return params;
    }

    public String[] getExceptionClasses() {
        return exceptionClasses;
    }

    public Instrucao[] getInstrucoes() {
        return instrucoes;
    }

        
}
