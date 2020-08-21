package italo.explab.msg;

import italo.explab.codigo.Codigo;
import italo.explab.var.ObjetoVar;

public class ExceptionErro extends CodigoMSGErro {
   
    private ObjetoVar exceptionObj;
    private FuncOuConstrutor funcOuConstrutor;
    
    public ExceptionErro( Class classe, FuncOuConstrutor funcOuConstrutor, ObjetoVar exceptionObj, 
            Codigo codigo, int pos, String mensagem ) {
        super( classe, codigo, pos, mensagem );
        this.exceptionObj = exceptionObj;
        this.funcOuConstrutor = funcOuConstrutor;
    }

    public ObjetoVar getExceptionObj() {
        return exceptionObj;
    }

    public void setExceptionObj(ObjetoVar exceptionObj) {
        this.exceptionObj = exceptionObj;
    }        

    public FuncOuConstrutor getFuncOuConstrutor() {
        return funcOuConstrutor;
    }

    public void setFuncOuConstrutor(FuncOuConstrutor funcOuConstrutor) {
        this.funcOuConstrutor = funcOuConstrutor;
    }
    
}
